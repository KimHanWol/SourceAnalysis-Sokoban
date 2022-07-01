package frames;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScreenFrame extends JFrame implements FrameButton {

	protected JButton[] buttons;

	ScreenFrame() {
		setTitle("sokoban");

		setSize(720, 480);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
	}

	@Override
	public JButton getButton(int i) {
		// TODO Auto-generated method stub
		return buttons[i];
	}

	@Override
	public void setButton(JButton[] buttons, ActionListener actionListener) {
		// TODO Auto-generated method stub
		this.buttons = buttons;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].addActionListener(actionListener);
		}
	}

	@Override
	public void addButton(JPanel panel) {
		// TODO Auto-generated method stub
		if (buttons.length == 0)
			return;

		for (int i = 0; i < buttons.length; i++) {
			panel.add(buttons[i]);
		}
	}
}
