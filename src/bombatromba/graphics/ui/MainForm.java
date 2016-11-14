package bombatromba.graphics.ui;

import java.awt.GridLayout;

import javax.swing.JFrame;

import bombatromba.config.GameConfig;

/*
 * clasa ce reprezinta interfata grafica
 */
public class MainForm extends JFrame {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4516473387223625531L;
	
	/*
	 * Private properties
	 */
	private boolean _initialized;
	
	/*
	 * Public methods
	 */
	public MainForm() {		
		// TODO set fixed size of the frame, disallow resize operations
		// TODO add custom icon
		// TODO add menu bar with options for the game
		this._initialized = false;
	}

	// metoda care initializeaza interfata grafica
	public boolean initialize() {
		this.setTitle("BombaTromba");
		this.setSize(800, 600);
		
		// setam layout ca fiind un layout grid
		this.setLayout(new GridLayout(GameConfig.ROWS + 2, GameConfig.COLS + 2));
		
		this._initialized = true;
		return true;
	}
	
	// metoda care afiseaza interfata grafica
	public boolean showGameWindow() {
		if (!this._initialized)
			return false;
		
		this.setVisible(true);
		return true;
	}
}
