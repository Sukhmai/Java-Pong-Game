package gamePackage;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import gamePackage.gfx.Assets;
import gamePackage.input.KeyManager;

public class Game implements Runnable {

	private Display display;
	public int width, height;
	public String title;
	
	public double y1 = 150;
	public double x1 = 0;
	
	public double y2 =150;
	public double x2 =484;
	
	public double y3 = 236;
	public double x3 = 236;
	
	public double ballXspeed=8;
	public double ballYspeed=(Math.random()-0.5)*10;
	public double paddlespeed=12;
	
	public int collisionTime=0;
	
	public int p1score=0;
	public int p2score=0;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//Input
	private KeyManager keyManager;
	
	public Game(String title, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.title=title;
		keyManager = new KeyManager();
		
	}
	private void init()
	{
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		Assets.init();
		
	}
	private void tick()
	{
		x3+=ballXspeed;
		y3+=ballYspeed;
		if(collisionTime==0)
		{
			if(x3+28>=x2)
			{
				if(y3>y2-28&&y3<y2+Assets.getPaddleSize())
				{
				ballXspeed*=-1;
				ballYspeed=(((y3+14-y2)/Assets.getPaddleSize())-0.5)*25;
				collisionTime=3;
				}
			}
			if(x3+28>=width)
			{
				x3=width/2;
				y3=height/2;
				collisionTime=3;
				p1score++;
			}
			
			if(x3<=0)
			{
				x3=width/2;
				y3=height/2;
				collisionTime=3;
				p2score++;
			}
			
			if(x3<=x1+17)
			{
				if(y3>y1-28&&y3<y1+Assets.getPaddleSize())
				{
				ballXspeed*=-1;
				ballYspeed=(((y3+14-y1)/Assets.getPaddleSize())-0.5)*25;
				collisionTime=3;
				}
			}
			if(y3<=0)
			{
				ballYspeed*=-1;
				collisionTime=3;
			}
			if(y3>=472)
			{
				ballYspeed*=-1;
				collisionTime=3;
			}
		}
		if(collisionTime>0)
		{
			collisionTime--;
		}
		keyManager.tick();
		if(keyManager.getUp()&&y2>0)
		{
			y2-=paddlespeed;
		}
		if(keyManager.getDown()&&y2<500-Assets.getPaddleSize())
		{
			y2+=paddlespeed;
		}
		if(keyManager.getW()&&y1>0)
		{
			y1-=paddlespeed;
		}
		if(keyManager.getS()&&y1<500-Assets.getPaddleSize())
		{
			y1+=paddlespeed;
		}
		
	}
	
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		
		//Draw Stuff
		g.drawImage(Assets.ball, (int) x3, (int) y3, null);
		g.drawImage(Assets.paddle, (int) x1, (int) y1, null);
		g.drawImage(Assets.paddle, (int) x2, (int) y2, null);
		
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.drawString(""+ p1score, 40, 25);
		g.drawString(""+ p2score, 460, 25);
		
		
		//End Drawing
		bs.show();
		g.dispose();
	}
	public void run()
	{
		init();
		
		int fps=60;
		double timePerTick = 1e9 /fps;
		double delta=0;
		long now;
		long lastTime = System.nanoTime();
		long timer=0;
		int ticks =0;
		
		while(running)
		{
			now=System.nanoTime();
			delta+=(now-lastTime)/ timePerTick;
			timer += now - lastTime;
			lastTime=now;
			if(delta >=1)
			{
			tick();
			render();
			ticks++;
			delta--;
			}
			if(timer >= 1e9)
			{
				System.out.println("FPS: " + ticks);
				ticks=0;
				timer=0;
			}
		}
		
		stop();
	}
	
	public synchronized void start()
	{
		if(running)
		{
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(running==false)
		{
			return;
		}
		running =false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
