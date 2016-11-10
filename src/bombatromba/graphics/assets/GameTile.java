package bombatromba.graphics.assets;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import bombatromba.config.GameConfig;

public class GameTile extends JPanel {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6977657328359823073L;
	
	/*
	 * Private properties
	 */
	private GameTileType _type;
	private Image _background;
	private boolean _initialized;
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
	private static boolean _initAssets() {
		boolean result = true;
		File bombSpriteFile = new File(GameConfig.BOMBSPRITE_FILE);
		File playerSpriteFile = new File(GameConfig.PLAYERSPRITE_FILE);
		File enemySpriteFile = new File(GameConfig.ENEMYSPRITE_FILE);
		File obstacleSpriteFile = new File(GameConfig.OBSTACLESPRITE_FILE);
		File brickSpriteFile = new File(GameConfig.BRICKSPRITE_FILE);
		File explosionSpriteFile = new File(GameConfig.EXPLOSIONSPRITE_FILE);
		File emptySpriteFile = new File(GameConfig.EMPTYSPRITE_FILE);
		
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

	public GameTileType setType(GameTileType newType) {
		if (!this._initialized)
			return null;
		
		switch (newType) {
		case BOMB:
			this._background = _bombSprite;
			break;
		case BRICK:
			this._background = _brickSprite;
			break;
		case EMPTY:
			this._background = _emptySprite;
			break;
		case ENEMY:
			this._background = _enemySprite;
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
			System.out.println("attempting to restore tile");
			return this.restoreType();
		}
			
		
		this._type = newType;
		this.revalidate();
		this.repaint();
		return newType;
	}
	
	public GameTileType restoreType() {
		if (!this._initialized || this._old == null)
			return null;
		
		GameTileType res = this.setType(this._old);
		System.out.println("restoring tile to " + this._old + " exited with " + res);
		this._old = null;
		return res;
	}
}
