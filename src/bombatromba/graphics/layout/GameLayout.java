package bombatromba.graphics.layout;

import javax.swing.JFrame;

import bombatromba.graphics.assets.GameTile;
import bombatromba.graphics.assets.GameTileType;

/*
 * Clasa care tine aseazarea blocurilor grafice in pagina
 */
public class GameLayout implements LayoutManager {

	/*
	 * Private properties
	 */
	private GameTile[][] _tiles;		// blocurile grafice
	private int _rows, _cols;			// dimensiunile
	private boolean _initialized;
	private JFrame _gameWindow;			// fereastra grafica
	
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
	
	// initializam layout-ul
	public boolean initialize(JFrame gameWindow) {
		
		if (gameWindow == null)
			return false;
		
		this._gameWindow = gameWindow;
		this._tiles = new GameTile[this._rows + 2][this._cols + 2];
		
		this._initialized = true;
		return true;
	}

	// metoda care schimba grafic un bloc pe ecran
	@Override
	public GameTileType updateTile(int row, int col, GameTileType newType) {
		if (!this._initialized)
			return null;
		
		// daca nu exista inca blocul, in facem
		if (this._tiles[row][col] == null) {
			this._tiles[row][col] = new GameTile();
			// de asemenea il initializam
			if (!this._tiles[row][col].initialize()) {
				System.out.println("error initializing tile (" + row + "," + col + ")");
				return null;
			}
			
			// si il adaugam la ecran
			this._gameWindow.getContentPane().add(this._tiles[row][col]);
		}

		if (row < 0 || row >= this._rows + 2)
			return null;
		
		if (col < 0 || col >= this._cols + 2)
			return null;
		
		// ii setam noul tip
		GameTileType confirm = this._tiles[row][col].setType(newType);
		
		if (confirm == null) {
			System.out.println("error setting tile type " + newType + " for tile (" + row + "," + col + ")");
			return null;
		}
		
		
		// revalidam si pictam
		this._gameWindow.revalidate();
		this._gameWindow.repaint();
		return confirm;
	}
}
