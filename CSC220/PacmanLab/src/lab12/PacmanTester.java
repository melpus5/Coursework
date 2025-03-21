package lab12;

public class PacmanTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pacman testMaze1 = new Pacman("mazes/demoMaze.txt", "out.txt");
		testMaze1.writeOutput();
		testMaze1.solveDFS();
		
		Pacman testMaze2 = new Pacman("mazes/mediumMaze.txt", "out.txt");
		testMaze2.writeOutput();
		testMaze2.solveBFS();
	}

}
