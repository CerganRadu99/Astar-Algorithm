package searchpractice;
import java.util.List;
import java.util.Random;
import robocode.control.*;
public class RouteFinder {
	public static void main(String[] args) {
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/robocode"));
		engine.setVisible(true);
		
		BattlefieldSpecification battlefield1 = new BattlefieldSpecification(640, 640);
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		int TileSize = 64;
		int halfTile = (TileSize / 2);
		int NumTileRows = (int)(640 / TileSize);
		int NumTileCols = (int)(640 / TileSize);
		int VerticalOffset = 640 % 64;
		
		List<Integer> remainingPossiblePositions = routeBot.generateAllPositions(NumTileRows * NumTileCols);
		double SittingDuckPercentage = 0.30;
		int NumObstacles = (int)(NumTileRows * NumTileCols * SittingDuckPercentage);
		RobotSpecification[] modelRobots = engine.getLocalRepository("sample.SittingDuck, searchpractice.routeBot*");
		RobotSpecification[] existingRobots = new RobotSpecification[NumObstacles + 1];
		RobotSetup[] robotSetups = new RobotSetup[NumObstacles + 1];
		Random randomGenerator = new Random();
		randomGenerator.setSeed(69);
		for(int it = 0; it < NumObstacles; ++it) {
			int idx1 = randomGenerator.nextInt(remainingPossiblePositions.size());
			int position1 = remainingPossiblePositions.remove(idx1);
			
		    int InitialTileRow1 = (int)(position1 % NumTileRows);
			int InitialTileCol1 = (int)(position1 / NumTileRows);
			double InitialObstacleRow = (double)(InitialTileRow1 * TileSize + halfTile);
			double InitialObstacleCol = (double)(InitialTileCol1 * TileSize + halfTile + VerticalOffset);
			
			robotSetups[it] = new RobotSetup(InitialObstacleRow, InitialObstacleCol, 0.0);
			existingRobots[it] = modelRobots[0];
		}
		Random randomGenerator1 = new Random(69);
		existingRobots[NumObstacles] = modelRobots[1];
		// Generating the initial position of the agent
		int idx = randomGenerator1.nextInt(remainingPossiblePositions.size());
		int position = remainingPossiblePositions.remove(idx);
			
		int InitialTileRow = position % NumTileRows;
		int InitialTileCol = position / NumTileRows;

		double InitialAgentRow = InitialTileRow * TileSize + halfTile;
		double InitialAgentCol = InitialTileCol * TileSize + halfTile + VerticalOffset;
		
		robotSetups[NumObstacles] = new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0);
		
		routeBot.startX = InitialTileRow;
		routeBot.startY = InitialTileCol;
		System.out.println("x : " + InitialTileRow + "y : " + InitialTileCol);
		idx = randomGenerator1.nextInt(remainingPossiblePositions.size());
		position = remainingPossiblePositions.remove(idx);
			
		InitialTileRow = position % NumTileRows;
		InitialTileCol = position / NumTileRows;
		
		System.out.println("x : " + InitialTileRow + "y : " + InitialTileCol);
		
		routeBot.endX = InitialTileRow;
		routeBot.endY = InitialTileCol;
		BattleSpecification battleSpec = new BattleSpecification(battlefield1, numberOfRounds, inactivityTime, gunCoolingRate, sentryBorderSize, hideEnemyNames, existingRobots, robotSetups);
		
		engine.runBattle(battleSpec, true);
		engine.close();
		System.exit(0);
	}

	}

