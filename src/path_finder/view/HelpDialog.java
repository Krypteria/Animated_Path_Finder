package path_finder.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class HelpDialog extends JDialog{

	private JTextArea info;
	
	public HelpDialog(MainWindow mw) {
		super(mw, true);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		this.info = new JTextArea();
		this.info.setEditable(false);
		this.info.setBackground(null);
		
		this.info.setText(getInfoText());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		centerPanel.add(this.info);
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.setVisible(false);
		this.setTitle("Help");
		this.setSize(new Dimension(400,200));
		this.setResizable(false);
	}
	
	private String getInfoText() {
		String info;
		
		info = "Controls" + "\n" + "\n" + "Right click: paint wall" + "\n" + "Left click: remove wall" + "\n" +
				"Right click + S: setup start point" + "\n" +  "Right click + E: setup end point" + "\n" + 
				"Delay supported values: numbers between 0 and 100" + "\n";
		
		return info;
	}
}
