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

/*
 * clasa principala
 */
public class MainClass {

	// functia main
	public static void main(String[] args) {
		
		// facem o intefata grafica
		MainForm mainForm = new MainForm();
		if (!mainForm.initialize())
			return;
		
		// facem un layout
		GameLayout gameLayout = new GameLayout(GameConfig.ROWS, GameConfig.COLS);
		if (!gameLayout.initialize(mainForm))
			return;
		
		// generam o harta
		GameMap gameMap = GameMap.generateMap(GameConfig.ROWS, GameConfig.COLS, 10);
		
		// adaugam layout ca manager pentru harta
		gameMap.addLayoutManager(gameLayout);
		
		// creeam un motor de joc
		GameEngine gameEngine = new GameEngine();
		if (!gameEngine.initialize(mainForm, gameLayout, gameMap))
			return;
		
		// facem doua caractere, un jucator si un dusman
		GameCharacter player = new GameCharacter(GameCharacterType.PLAYER);
		GameCharacter enemy1 = new GameCharacter(GameCharacterType.ENEMY);
		
		// stabilim tinta dusmanului ca fiind jucatorul
		enemy1.setTarget(player);
		
		// facem un agent inteligent bazat pe a star
		AIAgent agent1 = new AIAgent(new AStarAlgorithm(enemy1, gameMap), enemy1, gameMap);
		
		// atasam agentul jucatorului
		enemy1.attachAgent(agent1);
		
		// adaugam caracterele pe harta
		// jucatorul la pozitia 1 1 iar dusmanul la 11 21
		gameEngine.addCharacter(1, 1, player);
		gameEngine.addCharacter(11, 21, enemy1);
		
		/*
		 * Game starts here
		 */
		if (!gameEngine.showGameWindow())
			return;
	}
}
