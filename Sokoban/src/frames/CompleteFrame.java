package frames;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CompleteFrame extends ScreenFrame{
	
	private int OFFSET = 20;

	private JButton menuButton = new JButton("Back To Menu");
	
	public CompleteFrame(ActionListener actionListener, int pathNum, long time, String Title){
		super.setButton(new JButton[] {menuButton}, actionListener);
		
		setSize(300, 300);
		
		JPanel contentPane = new JPanel(new GridLayout(5, 1, OFFSET, OFFSET));
		
		contentPane.setBorder(BorderFactory.createEmptyBorder(OFFSET, OFFSET, OFFSET, OFFSET));	
		
		contentPane.setVisible(true);
		
		add(contentPane);
		
		JLabel completeLabel = new JLabel(Title);
		completeLabel.setFont(new Font("Serif", Font.BOLD, 30));
		completeLabel.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(completeLabel);
		
		JLabel scoreLabel = new JLabel("이동횟수 : " + String.valueOf(pathNum));
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(scoreLabel);
		addButton(contentPane);
		
		System.out.println(time);
		JLabel timeLabel = new JLabel("걸린시간 : " + String.valueOf(time));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(timeLabel);
		addButton(contentPane);
		
		setLocationRelativeTo(null);
	}
	
}
