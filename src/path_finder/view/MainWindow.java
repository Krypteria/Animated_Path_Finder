package path_finder.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import path_finder.controller.Controller;


@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	private Controller ctrl;
	
	private JButton solve;
	private JButton reset;
	private JLabel delayLabel;
	private JLabel delayInfo;
	private JSpinner delay;
	
	private JCheckBox maintainWalls;
	private JCheckBox diagonals;
	
	private static final int RESET = 1;
	private static final int NO_RESET = 0;
	private static final int DIAGONALS = 1;
	private static final int NO_DIAGONALS = 0;
	
	private static final int frameHeight = 850;
	private static final int frameWidth = 900;

	public MainWindow(Controller c) {
		this.ctrl = c;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		
		GridPanel canvas = new GridPanel(this.ctrl);
		
		this.delayLabel = new JLabel("Delay: ");
		this.delayInfo = new JLabel("(Supported values: 0 - 100)");
		
		this.maintainWalls = new JCheckBox("Maintain walls");
		this.diagonals = new JCheckBox("Diagonals");
		
		this.delay = new JSpinner(new SpinnerNumberModel(1,0,101,1));
		
		this.solve = new JButton("Start");
		solve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setDelay((int)delay.getValue());
				if(diagonals.isSelected()) {
					ctrl.start(DIAGONALS);					
				}
				else {
					ctrl.start(NO_DIAGONALS);
				}
			}
		});

		this.reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
		
		JSeparator sep1 = new JSeparator(SwingConstants.VERTICAL);
		sep1.setBackground(Color.black);
		sep1.setPreferredSize(new Dimension(5,30));
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new FlowLayout());
		
		
		optionPanel.add(this.solve);
		optionPanel.add(this.diagonals);
		optionPanel.add(Box.createRigidArea(new Dimension(5,5)));
		optionPanel.add(this.delayLabel);
		optionPanel.add(this.delay);
		optionPanel.add(Box.createRigidArea(new Dimension(5,5)));
		optionPanel.add(this.delayInfo);
		optionPanel.add(sep1);
		optionPanel.add(this.reset);
		optionPanel.add(this.maintainWalls);
		
		addSpace();
		
		this.add(canvas, BorderLayout.CENTER);
		this.add(optionPanel, BorderLayout.PAGE_END);
		this.setTitle("Path Finder");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(frameHeight, frameWidth));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	private void addSpace() {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		this.add(p1, BorderLayout.PAGE_START);
		this.add(p2, BorderLayout.LINE_START);
		this.add(p3, BorderLayout.LINE_END);
	}
}
