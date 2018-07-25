package gamePackage.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	private boolean up, down, w, s;
	
	public KeyManager()
	{
		keys = new boolean [256];
	}
	
	public void tick()
	{
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		w = keys[KeyEvent.VK_W];
		s = keys[KeyEvent.VK_S];
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
	public boolean getUp()
	{
		return up;
	}
	public boolean getDown()
	{
		return down;
	}
	public boolean getW()
	{
		return w;
	}
	public boolean getS()
	{
		return s;
	}

}
