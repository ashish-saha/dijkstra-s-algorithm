
#include <iostream>
#include<fstream>
using namespace std;
class DijktraSS {
public:
	int numNodes;
	int sourceNode;
	int minNode;
	int currentNode;
	int newCost;
	int** costMatrix;
	int* fatherAry;
	int* markedAry;
	int* bestCostAry;

	void loadCostMatrix(int n, ifstream &inFile) {
		numNodes = n;
		costMatrix = new int*[n];
		for (int i = 1; i<=n; i++)
			costMatrix[i] = new int[n];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j)		costMatrix[i][j] = 0;
				else			costMatrix[i][j] = 99999;
			}
		}

		int item;
		while (inFile >> item) {
			int row = item;
			inFile >> item;
			int col = item;
			inFile >> item;
			costMatrix[row][col] = item;
		}
	}

	void loadBestCostAry(int x) {
		sourceNode = x;
		bestCostAry = new int[numNodes];
		for (int i = 1; i <= numNodes; i++)
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
		for (int i = 1; i <= numNodes; i++)
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
		for (int i = 1; i <= numNodes; i++) {
/*			if (bestCostAry[i] == 0 || bestCostAry[i] == 99999)		continue;
			else*/ if (bestCostAry[i] < min && markedAry[i] == 0) {
				min = bestCostAry[i];
			}
		}
		for (int i = 1; i <= numNodes; i++) {
			if (bestCostAry[i] == min  && markedAry[i] == 0) {
				return i;
			}
		}
		return 0;
	}
	int findUnMarked(int x) {
		for (int i = x; i <= numNodes; i++) {
			if (markedAry[i] == 0)		return i;
		}
		return 99999;
	}

	int bestCost(int currentNode) {
		return bestCostAry[currentNode];
	}
	bool markChecker() {
		for (int i = 1; i <= numNodes; i++) {
			if (markedAry[i] == 0)		return true;
		}
		return false;
	}

	void print(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (costMatrix[i][j] == 99999  || costMatrix[i][j] == 0)			continue;
				cout << i << " " << j << " " << costMatrix[i][j] << endl;
			}
		}
	}



};


int main(int argc, char* argv[]) {
	
	ofstream outFile;
	outFile.open(argv[2]);



		ifstream inFile;
		inFile.open(argv[1]);
		int size;
		inFile >> size;

		DijktraSS tree = DijktraSS();
		tree.loadCostMatrix(size, inFile);

		tree.print(size);

		inFile.close();
		outFile.close();

	return 0;
}
