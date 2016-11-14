package bombatromba.game;

/*
 * clasa care reprezinta o pozitie pe harta
 */
public class Position {
	public int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// compararea a doua pozitii se face dupa cele doua coordoane
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj == null)
			return false;
		
		if (this.getClass() != obj.getClass())
			return false;
		
		Position other = (Position) obj;
		return (other.x == this.x && other.y == this.y);
	}
	
	// generarea unui id unic pentru pozitie se face in functie de coordonate
	@Override
	public int hashCode() {
		// Prime number
		return this.x * 4051 + this.y;
	}
	
	// afisam pozitia frumos
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
