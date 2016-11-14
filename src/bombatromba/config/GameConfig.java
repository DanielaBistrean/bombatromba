package bombatromba.config;

public final class GameConfig {
	/*
	 * Variabile de configurare utilizate in tot codul
	 */
	
	// Sprite-urile jocului
	public static final String BOMBSPRITE_FILE = "assets/sprites/bomb.png";
	public static final String PLAYERSPRITE_FILE = "assets/sprites/player.png";
	public static final String ENEMYSPRITE_FILE = "assets/sprites/enemy.png";
	public static final String EMPTYSPRITE_FILE = "assets/sprites/empty.png";
	public static final String OBSTACLESPRITE_FILE = "assets/sprites/obstacle.png";
	public static final String BRICKSPRITE_FILE = "assets/sprites/brick.png";
	public static final String EXPLOSIONSPRITE_FILE = "assets/sprites/explosion.png";
	
	// Dimensiunea spatiului de joc
	public static final int ROWS = 30;
	public static final int COLS = 40;
	
	// Distributia spatiilor 5 - maxim de spatii libere, 0 - nici un spatiu liber
	public static final int SPACESDIST = 3;
}
