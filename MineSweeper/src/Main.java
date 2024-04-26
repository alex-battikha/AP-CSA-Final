import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int BOARD_SIZE = 10;
    private static final int NUM_MINES = 10;
    private static final char UNREVEALED_CELL = '-';
    private static final char MINE_CELL = '*';
    private static final char EMPTY_CELL = ' ';

    private char[][] board;
    private boolean[][] revealed;
    private Random random;

    public Main() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        revealed = new boolean[BOARD_SIZE][BOARD_SIZE];
        random = new Random();
        initializeBoard();
        placeMines();
    }

    public void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = UNREVEALED_CELL;
                revealed[i][j] = false;
            }
        }
    }

    public void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < NUM_MINES) {
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            if (board[x][y] != MINE_CELL) {
                board[x][y] = MINE_CELL;
                minesPlaced++;
            }
        }
    }

    public void printBoard(boolean revealAll) {
        System.out.print("  ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (revealed[i][j] || revealAll) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print(UNREVEALED_CELL + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isValidCell(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public void revealCell(int x, int y) {
        if (!isValidCell(x, y) || revealed[x][y]) {
            return;
        }

        revealed[x][y] = true;

        if (board[x][y] == MINE_CELL) {
            return;
        }

        if (board[x][y] == EMPTY_CELL) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revealCell(x + i, y + j);
                }
            }
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            printBoard(false);
            System.out.print("Enter row and column (e.g., 0 1): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (!isValidCell(row, col)) {
                System.out.println("Invalid cell. Please try again.");
                continue;
            }

            if (board[row][col] == MINE_CELL) {
                System.out.println("Game over! You hit a mine.");
                printBoard(true);
                gameOver = true;
            } else {
                revealCell(row, col);
                boolean allCellsRevealed = true;
                for (int i = 0; i < BOARD_SIZE; i++) {
                    for (int j = 0; j < BOARD_SIZE; j++) {
                        if (!revealed[i][j] && board[i][j] != MINE_CELL) {
                            allCellsRevealed = false;
                            break;
                        }
                    }
                    if (!allCellsRevealed) {
                        break;
                    }
                }
                if (allCellsRevealed) {
                    System.out.println("Congratulations! You won!");
                    printBoard(true);
                    gameOver = true;
                }
            }
        }

        scanner.close();
    }


	public static void main(String[] args) {
		Main game = new Main();
	    game.playGame();
	}
}
