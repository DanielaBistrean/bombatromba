package bombatromba.graphics.layout;

import bombatromba.graphics.assets.GameTileType;

public interface GameLayoutInterface {
	public GameTileType getTileType(int row, int col);
	public boolean setTileType(int row, int col, GameTileType newType);
}
