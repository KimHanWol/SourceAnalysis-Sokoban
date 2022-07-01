package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroFrame extends ScreenFrame {

	private JPanel buttonPanel;

	private JButton startButton = new JButton("Start");
	private JButton mapMakingButton = new JButton("MapMaking");

	public IntroFrame(ActionListener actionListener) {
		super.setButton(new JButton[] {startButton, mapMakingButton}, actionListener);
		setLayout(new GridLayout(2,3));
		
		buttonPanel = new JPanel();
		PanelSetting();
	}

	protected void PanelSetting() {
		
		JPanel topPanel = new JPanel();
		JLabel title = new JLabel("Sokoban");
		topPanel.add(title);

		add(new JPanel());
		add(topPanel);
		add(new JPanel());
		add(new JPanel());
		add(buttonPanel);
		add(new JPanel());

		buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
		buttonPanel.add(new JPanel());
		buttonPanel.add(new JPanel());
		startButton.setBackground(Color.yellow);
		buttonPanel.add(startButton);
		mapMakingButton.setBackground(Color.yellow);
		buttonPanel.add(mapMakingButton);
		buttonPanel.add(new JPanel());
	}
}