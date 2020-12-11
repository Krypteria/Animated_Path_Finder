package path_finder.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import path_finder.controller.Controller;


@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	private Controller ctrl;
	
	public MainWindow(Controller c) {
		this.ctrl = c;
		initGUI();
	}

	
	private void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		
		GridPanel canvas = new GridPanel(this.ctrl);
		
		//TODO cambiar botones a clases aparte
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.start();
			}
			
		});
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.reset();
				canvas.reset();
			}
			
		});
		
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));
		optionPanel.add(start);
		optionPanel.add(reset);
		
		this.add(canvas, BorderLayout.CENTER);
		this.add(optionPanel, BorderLayout.LINE_END);
		this.setTitle("Path Finder");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
