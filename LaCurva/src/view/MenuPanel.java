package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle.Control;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.CurvaGame;

public class MenuPanel extends JPanel implements KeyListener {
	
	public static class PlayerKeyMapping{
		public PlayerKeyMapping(String name,char left,char right,Color c) {
			CONTROL_LEFT = left;
			CONTROL_RIGHT = right;
			COLOR = c;
			IN_USE = false;
			NAME = name;
		}
		public char CONTROL_LEFT;
		public char CONTROL_RIGHT;
		public Color COLOR;
		public boolean IN_USE;
		public String NAME;
	}
	private CurvaGame _game;
	private PlayerKeyMapping[] _playerKeyMapping;
	
	private boolean[] _typedKeys = new boolean[256];
	
	public MenuPanel(CurvaGame game) {
		this.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.requestFocus();
		this.setVisible(true);
		this.addKeyListener(this);
		_game = game;
		{
			_playerKeyMapping = new PlayerKeyMapping[6];
			_playerKeyMapping[0] = new PlayerKeyMapping("Player 1",'1','2',Color.BLUE);
			_playerKeyMapping[1] = new PlayerKeyMapping("Player 1",'x','c',Color.RED);
			_playerKeyMapping[2] = new PlayerKeyMapping("Player 1",'m',',',Color.GREEN);
			_playerKeyMapping[3] = new PlayerKeyMapping("Player 1",'ü','+',Color.YELLOW);
			_playerKeyMapping[4] = new PlayerKeyMapping("Player 1",'0','ß',Color.WHITE);
			_playerKeyMapping[5] = new PlayerKeyMapping("Player 1",'7','9',Color.CYAN);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(e.getKeyChar());
		_typedKeys[e.getKeyChar()]= true;
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			_game.startGame();
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.PLAIN, 50));
		g.drawString("La Curva", GamePanel.WIDTH/2 - 100, 150);
		
		g.setFont(new Font("Verdana", Font.PLAIN, 20));
		g.drawString("Press controls to get ready and hit Enter to start the game!", 100, 300);
		
		int stringPos = 350;
		for(int i=0; i< _playerKeyMapping.length;i++){
			PlayerKeyMapping currMapping = _playerKeyMapping[i];
			g.setColor(currMapping.COLOR);
			g.setFont(new Font("Verdana", Font.PLAIN, 15));
		
			if(_typedKeys[currMapping.CONTROL_LEFT] && _typedKeys[currMapping.CONTROL_RIGHT]){
				currMapping.IN_USE = true;
				g.setFont(new Font("Verdana", Font.BOLD, 15));
				g.drawString("Player "+(i+1)+" : "+currMapping.CONTROL_LEFT+" "+currMapping.CONTROL_RIGHT, 100, stringPos);
			}else{
				g.drawString("Player "+(i+1)+" : "+currMapping.CONTROL_LEFT+" "+currMapping.CONTROL_RIGHT, 100, stringPos);
			}
			stringPos +=30;
		}
//	
	}
	public PlayerKeyMapping[] get_playerKeyMapping() {
		return _playerKeyMapping;
	}
	
}
