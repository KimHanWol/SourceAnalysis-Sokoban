package frames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

import ManagerPanel.MapMakingManagerPanel;

public class MapMakingFrame extends ScreenFrame {
	
	private final int OFFSET = 20;

	private JButton backToMenuButton = new JButton("돌아가기");
	private JButton startButton = new JButton("시작");
	private JButton completedButton = new JButton("완료");
	
	private MapMakingManagerPanel mapMakingManagerPanel;
	
	private JToggleButton wallButton = new JToggleButton(new ImageIcon("src/resources/wall.png"));
	private JToggleButton baggageButton = new JToggleButton(new ImageIcon("src/resources/baggage.png"));
	private JToggleButton areaButton = new JToggleButton(new ImageIcon("src/resources/area.png"));
	private JToggleButton playerButton = new JToggleButton(new ImageIcon("src/resources/sokoban.gif"));
	private JToggleButton blankButton = new JToggleButton(new ImageIcon("src/resources/blank.png"));
	
	public MapMakingFrame(ActionListener actionListener, MapMakingManagerPanel mapMakingManagerPanel){
		super.setButton(new JButton[] {backToMenuButton, startButton, completedButton}, actionListener);

		
		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		
		contentPane.setBorder(BorderFactory.createEmptyBorder(OFFSET, OFFSET, OFFSET, OFFSET));	
		
		contentPane.setVisible(true);
		
		add(contentPane);
		
		JPanel paintPane = new JPanel(new BorderLayout(10, 10));
		
		paintPane.add(addObjectButtons(), BorderLayout.NORTH);
		
		JPanel mapPane = new JPanel(new BorderLayout());
		
		paintPane.add(mapPane, BorderLayout.CENTER);
		
		mapPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.mapMakingManagerPanel = mapMakingManagerPanel;
		addMapMakingManager(mapMakingManagerPanel);
		mapPane.add(mapMakingManagerPanel, BorderLayout.CENTER);
		
		JPanel controlPane = new JPanel(new BorderLayout());
		controlPane.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		contentPane.add(paintPane, BorderLayout.CENTER);
		contentPane.add(controlPane, BorderLayout.EAST);
		
		controlPane.add(new JPanel(), BorderLayout.CENTER);
		JPanel controlButtonPane = new JPanel(new GridLayout(2, 1, 10, 10));
		controlButtonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		controlPane.add(controlButtonPane, BorderLayout.SOUTH);
		
		controlButtonPane.add(backToMenuButton);
		controlButtonPane.add(startButton);
		
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		setFocusable(true);
	}
	
	private JPanel addObjectButtons() {
		JPanel objectPane = new JPanel();
		objectPane.setBorder(new BevelBorder(BevelBorder.RAISED));
		objectPane.add(wallButton);
		objectPane.add(new JLabel("1 "));
		
		objectPane.add(baggageButton);
		objectPane.add(new JLabel("2 "));
		objectPane.add(areaButton);
		objectPane.add(new JLabel("3 "));
		objectPane.add(playerButton);
		objectPane.add(new JLabel("4 "));
		objectPane.add(blankButton);
		objectPane.add(new JLabel("5 "));
		
		return objectPane;
	}

	public void addMapMakingManager(MapMakingManagerPanel mapMakingManagerPanel) {
		this.mapMakingManagerPanel = mapMakingManagerPanel;
		this.addKeyListener(mapMakingManagerPanel.getAdapter());
	}
	
	public boolean gameStart() {
//		startButton.addKeyListener(mapMakingManager.getAdapter());
		startButton.setFocusable(true);
		return mapMakingManagerPanel.startGame(completedButton);
	}
}
