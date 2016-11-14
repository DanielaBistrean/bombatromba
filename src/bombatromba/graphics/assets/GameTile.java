package bombatromba.graphics.assets;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import bombatromba.config.GameConfig;

/*
 * clasa care reprezinta un bloc vizual pe ecran
 */
public class GameTile extends JPanel {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6977657328359823073L;
	
	/*
	 * Private properties
	 */
	
	// tipul si fundalul blocului
	private GameTileType _type;
	private Image _background;
	private boolean _initialized;
	
	// imaginile de fundal (statice)
	private static Image _bombSprite = null;
	private static Image _playerSprite = null;
	private static Image _enemySprite = null;
	private static Image _obstacleSprite = null;
	private static Image _brickSprite = null;
	private static Image _explosionSprite = null;
	private static Image _emptySprite = null;
	private GameTileType _old;
	
	/*
	 * Private methods
	 */
	
	// functie statica folosita pentru a incerca imaginile
	private static boolean _initAssets() {
		boolean result = true;
		
		// incarcam fiecare sprite din fisier
		File bombSpriteFile = new File(GameConfig.BOMBSPRITE_FILE);
		File playerSpriteFile = new File(GameConfig.PLAYERSPRITE_FILE);
		File enemySpriteFile = new File(GameConfig.ENEMYSPRITE_FILE);
		File obstacleSpriteFile = new File(GameConfig.OBSTACLESPRITE_FILE);
		File brickSpriteFile = new File(GameConfig.BRICKSPRITE_FILE);
		File explosionSpriteFile = new File(GameConfig.EXPLOSIONSPRITE_FILE);
		File emptySpriteFile = new File(GameConfig.EMPTYSPRITE_FILE);
		
		// incercam sa incarcam imaginile
		try {
			_bombSprite = ImageIO.read(bombSpriteFile);
			_playerSprite = ImageIO.read(playerSpriteFile);
			_enemySprite = ImageIO.read(enemySpriteFile);
			_obstacleSprite = ImageIO.read(obstacleSpriteFile);
			_brickSprite = ImageIO.read(brickSpriteFile);
			_explosionSprite = ImageIO.read(explosionSpriteFile);
			_emptySprite = ImageIO.read(emptySpriteFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	/*
	 * Protected methods
	 */
	// metoda apelata automat de sistem
	// ne asiguram ca pictam fundalul
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this._background, 0, 0, getWidth(), getHeight(), this);
    }
	
	/*
	 * Public methods
	 */
	public GameTile () {
		this._initialized = false;
		this._background = null;
		this._type = null;
		this._old = null;
	}
	
	// metoda care initializeaza blocul grafic cu tipul obstacol (by default)
	public boolean initialize() {
		boolean result;
		
		result = _initAssets();
		if (!result)
			return false;
		
		this._initialized = true;
		this._type = GameTileType.OBSTACLE;
		this._background = _obstacleSprite;
		return true;
	}
	
	public GameTileType getType() {
		return this._type;
	}

	
	// setam un bloc grafic
	public GameTileType setType(GameTileType newType) {
		if (!this._initialized)
			return null;
		
		switch (newType) {
		case BOMB:
			// in caz ca e bomba vrem sa ramana bomba orice ar fi
			this._background = _bombSprite;
			this._old = newType;
			break;
		case BRICK:
			this._background = _brickSprite;
			break;
		case EMPTY:
			this._background = _emptySprite;
			break;
		case ENEMY:
			// in caz e ca jucator sau disman, vrem sa ramana ce era inainte dupa ce pleaca
			this._background = _enemySprite;
			this._old = this._type;
			break;
		case EXPLOSION:
			this._background = _explosionSprite;
			break;
		case PLAYER:
			this._background = _playerSprite;
				this._old = this._type;
			break;
		case OBSTACLE:
			this._background = _obstacleSprite;
			break;
		case UNDEFINED:
		default:
			// in orice alt caz sau nedefinit, vrem sa intoarcem blocul la starea de dinainte
			System.out.println("attempting to restore tile");
			return this.restoreType();
		}
			
		// setam starea si logic
		// revalidam si pictam
		this._type = newType;
		this.revalidate();
		this.repaint();
		return newType;
	}
	
	// functie care restaureaza starea
	public GameTileType restoreType() {
		if (!this._initialized || this._old == null)
			return null;
		
		// setam starea cu cea veche
		GameTileType res = this.setType(this._old);
		System.out.println("restoring tile to " + this._old + " exited with " + res);
		this._old = null;
		return res;
	}
}
