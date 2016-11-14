package bombatromba.graphics.layout;

import bombatromba.graphics.assets.GameTileType;

/*
 * Interfata pentru clasele care vor sa schimbe blocuri grafice
 */
public interface LayoutManager {
	public GameTileType updateTile(int row, int col, GameTileType newType);
}
