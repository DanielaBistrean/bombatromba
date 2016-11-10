package bombatromba.game;

public class Position {
	public int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

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
	
	@Override
	public int hashCode() {
		// Prime number
		return this.x * 4051 + this.y;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
