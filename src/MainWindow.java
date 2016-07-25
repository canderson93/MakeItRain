import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainWindow {
	JFrame frame;
	JLabel label;
	
	public MainWindow(){
		//Main window
		frame = new JFrame("Make it rain");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 100);
		
		JPanel panel = new JPanel();
		
		JLabel title = new JLabel("Making it rain...");
		label = new JLabel();
		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		
		panel.setLayout(layout);
		
		panel.add(Box.createVerticalGlue());
		panel.add(title);
		panel.add(label);
		panel.add(Box.createVerticalGlue());
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		//display it
		panel.setVisible(true);
		frame.setVisible(true);
	}
	
	public void updateText(int amount){
		label.setText("$" + amount);
	}
}
