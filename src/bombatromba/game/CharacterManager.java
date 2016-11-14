package bombatromba.game;

/*
 * Interfata pentru a prelua notificari de input de la tastatura
 */
public interface CharacterManager {
	public void notifyAction(MoveDirection move);
	public void notifyBomb();
}
