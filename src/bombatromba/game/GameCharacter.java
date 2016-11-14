package bombatromba.game;

import java.util.Timer;

import bombatromba.game.ai.AIAgent;
import bombatromba.graphics.assets.GameTile;
import bombatromba.graphics.assets.GameTileType;

public class GameCharacter {
	
	/*
	 * Private properties
	 */
	private GameMapNode _position;
	private GameCharacterType _type;
	private GameCharacter _target;
	private AIAgent _agent;
	private Timer _timer;
	
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
		System.out.println("new position is " + this._position.getPosition());
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
	
	public GameMapNode getNode() {
		return this._position;
	}
	
	public Position getPosition() {
		return this._position.getPosition();
	}
	
	public void setTarget(GameCharacter player) {
		this._target = player;
	}

	public GameCharacter getTarget() {
		return this._target;
	}
	
	public void attachAgent(AIAgent agent) {
		this._agent = agent;
		this._timer = new Timer();
		this._timer.scheduleAtFixedRate(this._agent, 1000, 1000);
//		this._timer.schedule(this._agent, 1000);
	}
}
