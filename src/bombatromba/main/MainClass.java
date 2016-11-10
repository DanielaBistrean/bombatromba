package bombatromba.main;

import bombatromba.config.GameConfig;
import bombatromba.game.GameCharacter;
import bombatromba.game.GameCharacterType;
import bombatromba.game.GameEngine;
import bombatromba.game.GameMap;
import bombatromba.graphics.layout.GameLayout;
import bombatromba.graphics.ui.MainForm;

public class MainClass {

	public static void main(String[] args) {
		
		MainForm mainForm = new MainForm();
		if (!mainForm.initialize())
			return;
		
		GameLayout gameLayout = new GameLayout(GameConfig.ROWS, GameConfig.COLS);
		if (!gameLayout.initialize(mainForm))
			return;
		
		GameMap gameMap = GameMap.generateMap(GameConfig.ROWS, GameConfig.COLS, 10);
		gameMap.addLayoutManager(gameLayout);
		
		GameEngine gameEngine = new GameEngine();
		if (!gameEngine.initialize(mainForm, gameLayout, gameMap))
			return;
		
		GameCharacter player = new GameCharacter(GameCharacterType.PLAYER);
		GameCharacter enemy1 = new GameCharacter(GameCharacterType.ENEMY);
		GameCharacter enemy2 = new GameCharacter(GameCharacterType.ENEMY);
		
		gameEngine.addCharacter(1, 1, player);
		gameEngine.addCharacter(10, 20, enemy1);
		gameEngine.addCharacter(15, 7, enemy2);
		
		/*
		 * Game starts here
		 */
		if (!gameEngine.showGameWindow())
			return;
	}
}
