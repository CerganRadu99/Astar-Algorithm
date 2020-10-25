package searchpractice;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import robocode.Robot;
public class routeBot extends Robot {
	public static class Tile{
		int h = 0;
		int g = 0;
		int f = 0;
		boolean closed = false;
		int x, y;
		Tile parent;
		
		Tile(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	public void run()
	{
		int NumTileRows = 10;
		int NumTileCols = 10;
		Tile[][] battlefield = new Tile[NumTileRows][NumTileCols];
		for(int i = 0; i < NumTileRows; ++i)
		{
			for(int j = 0; j < NumTileCols; ++j)
			{
				battlefield[i][j] = new Tile(i, j);
			}
		}
		
		List<Integer> remainingPossiblePositions = generateAllPositions(NumTileRows * NumTileCols);
		double SittingDuckPercentage = 0.30;
		int NumObstacles = (int)(NumTileRows * NumTileCols * SittingDuckPercentage);
		
		Random randomGenerator = new Random();
		randomGenerator.setSeed(69);
		int idx1;
		int position1;
		int InitialTileRow1;
	    int InitialTileCol1;
		for(int it = 0; it < NumObstacles; ++it) {
			idx1 = randomGenerator.nextInt(remainingPossiblePositions.size());
			position1 = remainingPossiblePositions.remove(idx1);
			
		    InitialTileRow1 = (int)(position1 % NumTileRows);
			InitialTileCol1 = (int)(position1 / NumTileRows);
			setBlocked(battlefield, InitialTileRow1, InitialTileCol1);
		}
		Random randomGenerator1 = new Random(69);
		int idx = randomGenerator1.nextInt(remainingPossiblePositions.size());
		int position = remainingPossiblePositions.remove(idx);
			
		int InitialTileRow = (int)(position % NumTileRows);
		int InitialTileCol =(int)(position / NumTileRows);
		startX = InitialTileRow;
		startY = InitialTileCol;
		out.println("x : " + InitialTileRow + "y : " + InitialTileCol);
		idx = randomGenerator1.nextInt(remainingPossiblePositions.size());
		position = remainingPossiblePositions.remove(idx);
			
		int InitialTileRow2 = (int)(position % NumTileRows);
		int InitialTileCol2 = (int)(position / NumTileRows);
		endX = InitialTileRow2;
		endY = InitialTileCol2;
		out.println("x : " + InitialTileRow2 + "y : " + InitialTileCol2);
		Stack<Integer> aux = AStar(battlefield, startX, startY, endX, endY);
		if(!aux.empty())
		{
			
			while(!aux.empty())
			{
				Integer it = aux.pop();	
				if(it == 0)
				{
					if(getHeading() == 90)
					{
						turnLeft(90);
						ahead(64);
					}
					else if(getHeading() == 0)
					{
						ahead(64);
					}
					else if(getHeading() == 180)
					{
						turnLeft(180);
						ahead(64);
					}
					else if(getHeading() == 270)
					{
						turnRight(90);
						ahead(64);
					}
				}
				else if(it == 1)
				{
					if(getHeading() == 90)
					{
						ahead(64);
					}
					else if(getHeading() == 0)
					{
						turnRight(90);
						ahead(64);
					}
					else if(getHeading() == 180)
					{
						turnLeft(90);
						ahead(64);
					}
					else if(getHeading() == 270)
					{
						turnRight(180);
						ahead(64);
					}
	
				}
				else if(it == 2)
				{
					if(getHeading() == 90)
					{
						turnRight(90);
						ahead(64);
					}
					else if(getHeading() == 0)
					{
						turnRight(180);
						ahead(64);
					}
					else if(getHeading() == 180)
					{
						ahead(64);
					}
					else if(getHeading() == 270)
					{
						turnLeft(90);
						ahead(64);
					}
				}
				else if(it == 3)
				{

					if(getHeading() == 90)
					{
						turnRight(180);
						ahead(64);
					}
					else if(getHeading() == 0)
					{
						turnLeft(90);
						ahead(64);
					}
					else if(getHeading() == 180)
					{
						turnRight(90);
						ahead(64);
					}
					else if(getHeading() == 270)
					{
						ahead(64);
					}	
				}	
			}
		}
		else
		{
			out.println("No path found!");
			
		}
	}
	public static List<Integer> generateAllPositions(int n){
		List<Integer> list = new ArrayList<Integer>(n);
		for(int x = 0; x < n; x++){
			list.add(x);
		}
		return list;
	}
	public static void setBlocked(Tile[][] battlefield, int x, int y){
		battlefield[x][y] = null;
	}
	public static int MovementCost = 1;
	public static PriorityQueue<Tile> openSet;
	
	static int startX, startY;
	static int endX, endY;
	
	public static int ManhattanDistance(Tile tile) {
		return(Math.abs(tile.x - endX) + Math.abs(tile.y - endY));
	}
	public static void updateCost(Tile currentTile, Tile nextTile) {
		if(nextTile == null || nextTile.closed)
			return;
		nextTile.h = ManhattanDistance(nextTile);
		int nextTile_f = nextTile.h + currentTile.g + MovementCost;
		boolean inOpenSet = openSet.contains(nextTile);
		if(!inOpenSet || nextTile_f < nextTile.f){
			nextTile.f = nextTile_f;
			nextTile.g = currentTile.g + MovementCost;
			nextTile.parent = currentTile;
			if(!inOpenSet)
				openSet.add(nextTile);
		}
		
	}
	public static Stack<Integer> AStar(Tile[][] battlefield, int startX, int startY, int endX, int endY) {
		openSet = new PriorityQueue<Tile>((Tile t1, Tile t2) -> { if(t1.f < t2.f) return -1;
		else if (t1.f > t2.f) return 1;
		else return 0;});
		
		openSet.add(new Tile(startX, startY));
		Tile current;
		while(true) {
			current = openSet.poll();
			if(current == null)
			{
				break;
			}
			current.closed = true;
			if(current.x == endX && current.y == endY) {
				break;
			}
			Tile next;
			if(current.x - 1 >= 0) {
				next = battlefield[current.x - 1][current.y];
				updateCost(current, next);
			}
			if(current.x + 1 < battlefield.length) {
				next = battlefield[current.x + 1][current.y];
				updateCost(current, next);
			}
			if(current.y + 1 < battlefield[0].length) {
				next = battlefield[current.x][current.y + 1];
				updateCost(current, next);
			}
			if(current.y - 1 >= 0){
	            next = battlefield[current.x][current.y - 1];
	            updateCost(current, next); 
	       }       
		}
		Stack<Integer> path = new Stack<Integer>();
		if(battlefield[endX][endY].closed == true) {
			Tile current1 = battlefield[endX][endY];
			while(current1.parent != null) {
				if(current1.y > current1.parent.y)
					path.push(0);
				else if(current1.x > current1.parent.x)
					path.push(1);
				else if(current1.y < current1.parent.y)
					path.push(2);
				else 
					path.push(3);
				current1 = current1.parent;
			}
			return path;
		}
		else
			return  null;
	}
}