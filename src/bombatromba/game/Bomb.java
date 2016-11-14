package bombatromba.game;

import java.util.Timer;
import java.util.TimerTask;

import bombatromba.graphics.assets.GameTileType;

/*
 * Clasa care reprezinta o bomba pe harta
 * Aceasta clasa extinde TimerTask pentru a simula explozia bombei dupa un timp
 */
public class Bomb extends TimerTask {
	
	// Pozitia bombei pe harta
	private GameMapNode _position;
	
	// Referinta la intreaga harta pentru explozie
	private GameMap _map;
	
	// Timer pentru a detona bomba
	private Timer _timer;
	
	// Clasa auxiliara similara cu cea de bomba doar ca pentru curatarea exploziei
	private class Cleaner extends TimerTask {

		private GameMapNode _position;
		private GameMap _map;
		
		public Cleaner (GameMapNode n, GameMap m) {
			this._map = m;
			this._position = n;
		}
		
		// Functie care este executata dupa explozie pentru a curata spatiul exploziei
		// pentru detalii vezi functia de explozie
		@Override
		public void run() {
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
	
	// Constructorul care atribuie membrilor valorile initiale si amoreaza bomba
	public Bomb(GameMapNode position, GameMap map) {
		this._position = position;
		this._map = map;
		
		// Indica vizual faptul ca s-a amplasat o bomba
		this._position.updateType(GameTileType.BOMB);
		
		// Porneste un timer
		this._timer = new Timer();
		
		// Seteaza timer-ul pentru a detona bomba in 2 secunde si pentru a face clean-up in 3 secunde
		this._timer.schedule(this, 2000);
		this._timer.schedule(new Cleaner(position, map), 3000);
		
	}

	// Functie care detoneaza bomba
	@Override
	public void run() {
		// Marcheaza pozitia bombei ca explozie vizual
		this._position.updateType(GameTileType.EXPLOSION);
		
		// Indicatori care ne ajuta sa ne dam seama cand explozia a lovit un obstacol in cele patru
		// directii posibile
		boolean up, down, left, right;
		
		// initial explozia poate sa continue in toate cele 4 directii
		up = down = left = right = true;
		
		// Laum pozitia actuala (x, y)
		Position p = this._position.getPosition();
		
		// Pentru fiecare nivel al exploziei (4 nivele maxim)
		for (int i = 1; i < 4; i++) {
			
			// calculam al i-lea bloc vizual in toate directiile
			GameMapNode n = this._map.getNode(p.x, p.y + i);
			// daca am reusit sa obtinem blocul (daca exista) si daca inca mai putem continua
			// explozia (daca inca nu a intalnit un obstacol) si daca nu cumva el este un obstacol
			if (right && n != null && n.getType() != GameTileType.OBSTACLE)
				// atunci marcheaza blocul vizual ca explozie
				n.updateType(GameTileType.EXPLOSION);
			else
				// altfel tine minte ca pe directia curenta am intalnit deja un obstacol si
				// explozia nu va mai putea continua
				right = false;
			
			// la fel pt restul
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
