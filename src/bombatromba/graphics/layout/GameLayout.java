package bombatromba.graphics.layout;

import java.util.Random;

import javax.swing.JFrame;

import bombatromba.config.GameConfig;
import bombatromba.graphics.assets.GameTile;
import bombatromba.graphics.assets.GameTileType;

public class GameLayout implements LayoutManager {

	/*
	 * Private properties
	 */
	private GameTile[][] _tiles;
	private int _rows, _cols;
	private boolean _initialized;
	private JFrame _gameWindow;
	
	/*
	 * Public methods
	 */
	public GameLayout(int rows, int cols) {
		/*
		 * Add two more rows and columns for the borders
		 */
		this._gameWindow = null;
		this._rows = rows;
		this._cols = cols;
		this._initialized = false;
	}
	
	public boolean initialize(JFrame gameWindow) {
		
		if (gameWindow == null)
			return false;
		
		this._gameWindow = gameWindow;
		this._tiles = new GameTile[this._rows + 2][this._cols + 2];
		
		this._initialized = true;
		return true;
	}

	@Override
	public GameTileType updateTile(int row, int col, GameTileType newType) {
		if (!this._initialized)
			return null;
		
		if (this._tiles[row][col] == null) {
			this._tiles[row][col] = new GameTile();
			if (!this._tiles[row][col].initialize()) {
				System.out.println("error initializing tile (" + row + "," + col + ")");
				return null;
			}
			
			this._gameWindow.getContentPane().add(this._tiles[row][col]);
		}

		if (row < 0 || row >= this._rows + 2)
			return null;
		
		if (col < 0 || col >= this._cols + 2)
			return null;
		
		GameTileType confirm = this._tiles[row][col].setType(newType);
		
		if (confirm == null) {
			System.out.println("error setting tile type " + newType + " for tile (" + row + "," + col + ")");
			return null;
		}
		
		this._gameWindow.revalidate();
		this._gameWindow.repaint();
		return confirm;
	}
}
