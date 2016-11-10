package bombatromba.graphics.layout;

import bombatromba.graphics.assets.GameTileType;

public interface LayoutManager {
	public GameTileType updateTile(int row, int col, GameTileType newType);
}
