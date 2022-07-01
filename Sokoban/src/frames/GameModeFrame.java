package frames;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameModeFrame extends ScreenFrame{
	
	private final int OFFSET = 30;
	
	private JButton SoloModeButton = new JButton("1인용");
	private JButton Co_opModeButton = new JButton("2인용");
	
	public GameModeFrame(ActionListener actionListener){
		super.setButton(new JButton[] {SoloModeButton, Co_opModeButton}, actionListener);
		
		setSize(300, 300);
		
		JPanel contentPane = new JPanel(new GridLayout(2, 1, OFFSET, OFFSET));
		
		contentPane.setBorder(BorderFactory.createEmptyBorder(OFFSET, OFFSET, OFFSET, OFFSET));	
		
		contentPane.setVisible(true);
		
		add(contentPane);
		
		addButton(contentPane);
		
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}
