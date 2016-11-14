package bombatromba.main;

import bombatromba.config.GameConfig;
import bombatromba.game.GameCharacter;
import bombatromba.game.GameCharacterType;
import bombatromba.game.GameEngine;
import bombatromba.game.GameMap;
import bombatromba.game.ai.AIAgent;
import bombatromba.game.ai.AStarAlgorithm;
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
		enemy1.setTarget(player);
		
		AIAgent agent1 = new AIAgent(new AStarAlgorithm(enemy1, gameMap), enemy1, gameMap);
		
		enemy1.attachAgent(agent1);
		
		gameEngine.addCharacter(1, 1, player);
		gameEngine.addCharacter(11, 21, enemy1);
		
		/*
		 * Game starts here
		 */
		if (!gameEngine.showGameWindow())
			return;
	}
}
