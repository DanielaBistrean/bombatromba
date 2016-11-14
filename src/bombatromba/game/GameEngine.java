package bombatromba.game;

import bombatromba.game.mechanics.PlayerController;
import bombatromba.graphics.layout.GameLayout;
import bombatromba.graphics.ui.MainForm;

/*
 * Clasa principala a jocului care se ocupa de toate celelalte componente
 */
public class GameEngine implements CharacterManager{
	
	/*
	 * Private properties
	 */
	private MainForm _view;			// componenta grafica
	private GameMap _map;			// harta logica a jocului
	private GameCharacter _player;	// jucatorul controlat de la tastatura
	private boolean _initialized;	// indicator pentru initializarea clasei
	
	/*
	 * Public methods
	 */
	public GameEngine() {
		this._view = null;
		this._map = null;
		this._initialized = false;
	}
	
	// metoda care initializareaza clasa
	public boolean initialize(MainForm gameView, GameLayout gameLayout, GameMap gameMap) {
		if (gameView == null || gameLayout == null || gameMap == null)
			return false;
		
		this._view = gameView;
		this._map = gameMap;
		
		// adaugam un nou ascultator pentru input de la tastatura
		// TODO: am putea adauga dependency injection aici
		this._view.addKeyListener(new PlayerController(this));
		
		// actualizam harta pentru prima data (prima afisare dureaza cel mai mult)
		if (!this._map.updateMap()) {
			System.out.println("error updating map for the first time");
			return false;
		}
		
		this._initialized = true;
		return true;
	}
	
	// metoda care adauga un caracter pe spatiul vizual
	public boolean addCharacter(int posX, int posY, GameCharacter character) {
		if (!this._initialized)
			return false;
		
		if (character.getType() == GameCharacterType.PLAYER)
			this._player = character;
		
		return character.place(this._map.getNode(posX, posY));
	}
	
	// metoda care afiseaza fereastra jocului
	public boolean showGameWindow() {
		if (!this._initialized)
			return false;
		
		return this._view.showGameWindow();
	}

	// metoda care este apelata atunci cand o tasta de schimbare a directiei a fost apasata
	@Override
	public void notifyAction(MoveDirection move) {
		if (!this._initialized || this._player == null)
			return;
		
		// luam pozitia curenta a jucatorului
		Position current = this._player.getPosition();
		
		// calculam urmatoarea pozitie in functie de directia aferenta tastei apasate
		GameMapNode next = this._map.getNextNode(current, move);
		
		// daca urmatoarea pozitie este accesibila (valida)
		if (next.isAccesible()) {
			// mutam jucatorul acolo
			this._player.move(next);
		}
	}

	// metoda care este apelata atunci cand s-a apasat tasta space adica s-a amplasat o bomba
	@Override
	public void notifyBomb() {
		// punem o bomba in locul unde acum este jucatorul
		@SuppressWarnings("unused")
		Bomb b = new Bomb(this._player.getNode(), this._map);
	}
}
