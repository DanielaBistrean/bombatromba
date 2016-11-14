package bombatromba.game;

import java.util.Timer;
import java.util.TimerTask;

import bombatromba.graphics.assets.GameTileType;

public class Bomb extends TimerTask {
	private GameMapNode _position;
	private GameMap _map;
	private Timer _timer;
	private GameTileType _cleanup;
	
	private class Cleaner extends TimerTask {

		private GameMapNode _position;
		private GameMap _map;
		
		public Cleaner (GameMapNode n, GameMap m) {
			this._map = m;
			this._position = n;
		}
		
		@Override
		public void run() {
			System.out.println("bomb run wutg " + GameTileType.EMPTY);
			this._position.updateType(GameTileType.EMPTY);
			
			boolean up, down, left, right;
			
			up = down = left = right = true;
			Position p = this._position.getPosition();
			for (int i = 1; i < 4; i++) {
				GameMapNode n = this._map.getNode(p.x, p.y + i);
				if (right && n != null && n.getType() != GameTileType.OBSTACLE)
					n.updateType(GameTileType.EMPTY);
				else
					right = false;
				
				n = this._map.getNode(p.x, p.y - i);
				if (left && n != null && n.getType() != GameTileType.OBSTACLE)
					n.updateType(GameTileType.EMPTY);
				else
					left = false;
				
				n = this._map.getNode(p.x - i, p.y);
				if (up && n != null && n.getType() != GameTileType.OBSTACLE)
					n.updateType(GameTileType.EMPTY);
				else
					up = false;
				
				n = this._map.getNode(p.x + i, p.y);
				if (down && n != null && n.getType() != GameTileType.OBSTACLE)
					n.updateType(GameTileType.EMPTY);
				else
					down = false;
			}
			
		}
		
	}
	
	public Bomb(GameMapNode position, GameMap map) {
		this._position = position;
		this._map = map;
		
		this._position.updateType(GameTileType.BOMB);
		System.out.println("planted bomb at " + this._position.getPosition());
		
		this._timer = new Timer();
		this._timer.schedule(this, 2000);
		this._timer.schedule(new Cleaner(position, map), 3000);
		
	}

	@Override
	public void run() {
		System.out.println("bomb run wutg " + this._cleanup);
		this._position.updateType(GameTileType.EXPLOSION);
		
		boolean up, down, left, right;
		
		up = down = left = right = true;
		Position p = this._position.getPosition();
		for (int i = 1; i < 4; i++) {
			GameMapNode n = this._map.getNode(p.x, p.y + i);
			if (right && n != null && n.getType() != GameTileType.OBSTACLE)
				n.updateType(GameTileType.EXPLOSION);
			else
				right = false;
			
			n = this._map.getNode(p.x, p.y - i);
			if (left && n != null && n.getType() != GameTileType.OBSTACLE)
				n.updateType(GameTileType.EXPLOSION);
			else
				left = false;
			
			n = this._map.getNode(p.x - i, p.y);
			if (up && n != null && n.getType() != GameTileType.OBSTACLE)
				n.updateType(GameTileType.EXPLOSION);
			else
				up = false;
			
			n = this._map.getNode(p.x + i, p.y);
			if (down && n != null && n.getType() != GameTileType.OBSTACLE)
				n.updateType(GameTileType.EXPLOSION);
			else
				down = false;
		}
		
	}
	
	
}
