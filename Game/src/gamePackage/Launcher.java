package gamePackage;

public class Launcher {
	public static void main(String [] args)
	{
		Game game = new Game("Pong", 500, 500);
		game.start();
	}
}
