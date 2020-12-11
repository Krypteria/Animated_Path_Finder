package path_finder.launcher;

import java.util.Locale;

import javax.swing.SwingUtilities;

import path_finder.controller.Controller;
import path_finder.view.MainWindow;

public class Main {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.ENGLISH);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(new Controller());
			}
		});
	}

}
