package bombatromba.game;

import bombatromba.game.mechanics.PlayerController;
import bombatromba.graphics.assets.GameTileType;
import bombatromba.graphics.layout.GameLayout;
import bombatromba.graphics.ui.MainForm;

public class GameEngine implements CharacterManager{
	
	/*
	 * Private properties
	 */
	private GameLayout _layout;
	private MainForm _view;
	private GameMap _map;
	private GameCharacter _player;
	private boolean _initialized;
	
	/*
	 * Public methods
	 */
	public GameEngine() {
		this._layout = null;
		this._view = null;
		this._map = null;
		this._initialized = false;
	}
	
	public boolean initialize(MainForm gameView, GameLayout gameLayout, GameMap gameMap) {
		if (gameView == null || gameLayout == null || gameMap == null)
			return false;
		
		this._layout = gameLayout;
		this._view = gameView;
		this._map = gameMap;
		
		this._view.addKeyListener(new PlayerController(this));
		if (!this._map.updateMap()) {
			System.out.println("error updating map for the first time");
			return false;
		}
		
		this._initialized = true;
		return true;
	}
	
	public boolean addCharacter(int posX, int posY, GameCharacter character) {
		if (!this._initialized)
			return false;
		
		if (character.getType() == GameCharacterType.PLAYER)
			this._player = character;
		
		return character.place(this._map.getNode(posX, posY));
	}
	
	public boolean showGameWindow() {
		if (!this._initialized)
			return false;
		
		return this._view.showGameWindow();
	}

	@Override
	public void notifyAction(MoveDirection move) {
		if (!this._initialized || this._player == null)
			return;
		
		Position current = this._player.getPosition();
		GameMapNode next = this._map.getNextNode(current, move);
		if (next.isAccesible()) {
			this._player.move(next);
		}
		
	}
}
