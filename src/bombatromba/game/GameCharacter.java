package bombatromba.game;

import bombatromba.graphics.assets.GameTile;
import bombatromba.graphics.assets.GameTileType;

public class GameCharacter {
	
	/*
	 * Private properties
	 */
	private GameMapNode _position;
	private GameCharacterType _type;
	
	/*
	 * Public methods
	 */
	public GameCharacter(GameCharacterType type) {
		this._position = null;
		this._type = type;
	}
	
	public boolean place(GameMapNode position) {
		this._position = position;
		switch (this._type) {
		case ENEMY:
			return this._position.updateType(GameTileType.ENEMY);
		case PLAYER:
			return this._position.updateType(GameTileType.PLAYER);
		default:
			return false;
		
		}
	}
	
	public boolean move(GameMapNode position) {
		this._position.restoreTile();
		this._position = position;
		switch (this._type) {
		case ENEMY:
			return this._position.updateType(GameTileType.ENEMY);
		case PLAYER:
			return this._position.updateType(GameTileType.PLAYER);
		default:
			return false;
		
		}
	}

	public GameCharacterType getType() {
		return this._type;
	}
	
	public Position getPosition() {
		return this._position.getPosition();
	}
}
