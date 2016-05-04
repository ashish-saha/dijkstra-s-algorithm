import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.util.*;

public class DijktraSSS {

	int numNodes;
	int sourceNode;
	int minNode;
	int currentNode;
	int newCost;
	int [][] costMatrix;
	int [] fatherAry;
	int [] markedAry;
	int [] bestCostAry;


	void loadCostMatrix(int n, String inFileName) {
		numNodes = n+1;
		costMatrix = new int  [numNodes][numNodes];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j)		costMatrix[i][j] = 0;
				else			costMatrix[i][j] = 99999;
			}
		}
		Scanner inFile = null;
		try {
			inFile = new Scanner (new FileReader (inFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		int x = inFile.nextInt();
		while (inFile.hasNext()) {
			
			int row = inFile.nextInt();
			int col = inFile.nextInt();		
			costMatrix[row][col] = inFile.nextInt();
		}
		inFile.close();
	}

	void loadBestCostAry(int x) {
		sourceNode = x;
		bestCostAry = new int[numNodes];
		for (int i = 1; i < numNodes; i++)
			bestCostAry [i] = costMatrix[x][i];

	}

	void loadFatherAry(int x) {
		sourceNode = x;
		fatherAry = new int[numNodes];
		for (int i = 1; i < numNodes; i++)
			fatherAry[i] = x;
	}

	void loadMarkedAry() {
		markedAry = new int[numNodes];
		for (int i = 1; i < numNodes; i++)
			markedAry[i] = 0;
	}

	int computeCost(int minNode, int currentNode) {
		return (bestCostAry[minNode] + costMatrix[minNode][currentNode]);
	}

	void markMinNode(int minNode) {
		markedAry[minNode] = 1;
		
	}
	void changeFather(int node, int minNode) {
		fatherAry[node] = minNode;
	}
	void changeCost(int node, int newCost) {
		bestCostAry[node] = newCost;
	}
	int findMinNode() {
		int min = 99999;
		for (int i = 1; i < numNodes; i++) {
/*			if (bestCostAry[i] == 0 || bestCostAry[i] == 99999)		continue;
			else*/ if (bestCostAry[i] < min && markedAry[i] == 0) {
				min = bestCostAry[i];
			}
		}
		for (int i = 1; i < numNodes; i++) {
			if (bestCostAry[i] == min  && markedAry[i] == 0) {
				return i;
			}
		}
		return 0;
	}
	int findUnMarked(int x) {

		for (int i = x; i < numNodes; i++) {
			if (markedAry[i] == 0)		return i;
		}
		return 99999;
	}

	int bestCost(int currentNode) {
		return bestCostAry[currentNode];
	}
	boolean markChecker() {
		for (int i = 1; i < numNodes; i++) {
			if (markedAry[i] == 0)		return true;
		}
		return false;
	}

	

	void debugPrint(PrintWriter file, int node) {
		file.write( "The sourcenode is: " + node + "\n");
		file.write ( "The fatheAry: ");
		for (int i = 1; i < numNodes; i++) 
			file.write( fatherAry[i] + " ");
		file.write("\n");

		file.write( "The bestCostAry: ");
		for (int i = 1; i < numNodes; i++)
			file.write( bestCostAry[i] + " ");
		file.write("\n");

		file.write( "The markedAry: ");
		for (int i = 1; i < numNodes; i++)
			file.write( markedAry[i] + " ");
		file.write("\n");
		file.write("-----------------------" + "\n");

	}


	public static void main (String [] args) {
	
		FileWriter writer1 = null;
		FileWriter writer2 = null;
		PrintWriter printWriter1 = null;
		PrintWriter printWriter2 = null;

		
		try {
			writer1 = new FileWriter(args[1]);
			printWriter1 = new PrintWriter(writer1);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			writer2 = new FileWriter(args[2]);
			printWriter2 = new PrintWriter(writer2);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner inFile = null;
		try {
			inFile = new Scanner (new FileReader (args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		int size = inFile.nextInt();
		inFile.close();

		String inFileName = new String (args[0]);
		
		printWriter1.write( "===============================" + "\n");
		printWriter1.write( "There are " + size + " nodes in the input graph." + "\n");
		printWriter1.write( "===============================" + "\n");


		for (int j = 1; j <= size; j++) {

		DijktraSSS tree = new DijktraSSS();
		tree.loadCostMatrix(size, inFileName);

		int sourceNode = j;
		printWriter1.write( "The source node = " + sourceNode + "\n");
		printWriter1.write( "The shortest paths  from node " + sourceNode + " are:" + "\n" + "\n");
		tree.loadBestCostAry(sourceNode);
		tree.loadFatherAry(sourceNode);
		tree.loadMarkedAry();
		tree.markMinNode(sourceNode);


		while (tree.markChecker() == true) {
			int minNode = tree.findMinNode();
			tree.markMinNode(minNode);
			tree.debugPrint(printWriter2, sourceNode);

			for (int i = 1; i <= size; i++) {
				int currentNode = tree.findUnMarked(i);
				i = currentNode;
				if (currentNode == 99999)		continue;
				int newCost = tree.computeCost(minNode, currentNode);
				if (newCost < tree.bestCost(currentNode)) {
					tree.changeFather(currentNode, minNode);
					tree.changeCost(currentNode, newCost);
					tree.debugPrint(printWriter2, sourceNode);
				}
			}
		}


		for (int i = 1; i <= size; i++) {
			printWriter1.write( "The path from " + sourceNode + " to " + i + " : cost = " + tree.bestCost(i) + "\n");
		}
		printWriter1.write( "===============================" + "\n");
	}

	printWriter1.close();
	printWriter2.close();	
	
	}
}
