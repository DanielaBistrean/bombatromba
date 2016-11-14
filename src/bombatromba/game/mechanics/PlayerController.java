package bombatromba.game.mechanics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import bombatromba.game.CharacterManager;
import bombatromba.game.MoveDirection;

public class PlayerController implements KeyListener {

	private CharacterManager _manager;
	
	public PlayerController(CharacterManager manager) {
		this._manager = manager;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("pressed up");
			this._manager.notifyAction(MoveDirection.UP);
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("pressed down");
			this._manager.notifyAction(MoveDirection.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			this._manager.notifyAction(MoveDirection.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			this._manager.notifyAction(MoveDirection.RIGHT);
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("sppace");
			this._manager.notifyBomb();
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
