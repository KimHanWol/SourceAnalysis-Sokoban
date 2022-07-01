package frames;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public interface FrameButton {
	public JButton getButton(int i);
	
	public void setButton(JButton[] buttons, ActionListener actionListener );
	
	public void addButton(JPanel panel);
}
