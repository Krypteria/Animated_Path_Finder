package path_finder.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import path_finder.controller.Controller;


@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	//CONSTRAINTS
	private static final int RESET = 1;
	private static final int NO_RESET = 0;
	private static final int DIAGONALS = 1;
	private static final int NO_DIAGONALS = 0;
	
	private Controller ctrl;
	private GridPanel canvas;
	
	private JButton bfsSolve;
	private JButton a_starSolve;
	private JButton reset;
	private JButton help;
	
	private JLabel delayLabel;
	private JSpinner delay;
	
	private JCheckBox maintainWalls;
	private JCheckBox diagonals;

	public MainWindow(Controller c) {
		this.ctrl = c;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		
		this.canvas = new GridPanel(this.ctrl);
		
		this.delayLabel = new JLabel("Delay: ");
		
		this.maintainWalls = new JCheckBox("Maintain walls");
		this.diagonals = new JCheckBox("Diagonals");
		
		this.delay = new JSpinner(new SpinnerNumberModel(1,1,101,1));
		
		initializeButtons();
		
		JSeparator sep1 = new JSeparator(SwingConstants.VERTICAL);
		sep1.setBackground(Color.black);
		sep1.setPreferredSize(new Dimension(5,30));
		
		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		sep2.setBackground(Color.black);
		sep2.setPreferredSize(new Dimension(5,30));
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new FlowLayout());
		
		optionPanel.add(this.bfsSolve);
		optionPanel.add(this.a_starSolve);
		optionPanel.add(this.diagonals);
		optionPanel.add(Box.createRigidArea(new Dimension(5,5)));
		optionPanel.add(this.delayLabel);
		optionPanel.add(this.delay);
		optionPanel.add(Box.createRigidArea(new Dimension(85,5)));
		optionPanel.add(this.reset);
		optionPanel.add(this.maintainWalls);
		optionPanel.add(Box.createRigidArea(new Dimension(105,5)));
		optionPanel.add(help);
		
		addSpace();
		
		this.add(canvas, BorderLayout.CENTER);
		this.add(optionPanel, BorderLayout.PAGE_END);
		this.setTitle("Path Finder");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(this.canvas.getScreenHeight(), this.canvas.getScreenWidth()));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	private void initializeButtons() {
		this.bfsSolve = new JButton("BFS");
		bfsSolve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setDelay((int)delay.getValue());
				if(diagonals.isSelected()) {
					try {
						setButtonsEnable(false);
						ctrl.bfsSolve(DIAGONALS);
					} catch (IOException e1) {
						setButtonsEnable(true);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
						
					}					
				}
				else {
					try {
						setButtonsEnable(false);
						ctrl.bfsSolve(NO_DIAGONALS);
					} catch (IOException e1) {
						setButtonsEnable(true);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		this.a_starSolve = new JButton("A*");
		a_starSolve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setDelay((int)delay.getValue());
				if(diagonals.isSelected()) {
					try {
						setButtonsEnable(false);
						ctrl.a_starSolve(DIAGONALS);
					} catch (IOException e1) {
						setButtonsEnable(true);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
						
					}					
				}
				else {
					try {
						setButtonsEnable(false);
						ctrl.a_starSolve(NO_DIAGONALS);
					} catch (IOException e1) {
						setButtonsEnable(true);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		this.reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setButtonsEnable(true);
				if(maintainWalls.isSelected()) {
					ctrl.reset(NO_RESET);
					canvas.reset(NO_RESET);
				}
				else {
					ctrl.reset(RESET);
					canvas.reset(RESET);
				}
			}
			
		});
		
		this.help = new JButton("Help");
		this.help.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HelpDialog dialog = new HelpDialog((MainWindow) getParent());
				dialog.setLocationRelativeTo(getParent());
				dialog.setVisible(true);
			}
			
		});
	}
	
	private void setButtonsEnable(boolean mode) {
		this.a_starSolve.setEnabled(mode);
		this.bfsSolve.setEnabled(mode);
	}
	
	//Auxiliar rudimentary method to add space in the sides of the GridPanel
	private void addSpace() {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		p1.setMaximumSize(new Dimension(5,5));
		p2.setMaximumSize(new Dimension(5,5));
		p3.setMaximumSize(new Dimension(5,5));
		

		this.add(p1, BorderLayout.PAGE_START);
		this.add(p2, BorderLayout.LINE_START);
		this.add(p3, BorderLayout.LINE_END);
	}
}
