package gamePackage.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage paddle, ball;
	
	private static int paddleSize =120;

	public static void init()
	{
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Pong Sprites.png"));
		paddle = sheet.crop(16, 4, 17, getPaddleSize());
		ball = sheet.crop(65, 9, 28, 28);
	}
	
	public static int getPaddleSize()
	{
		return paddleSize;
	}
	
}
