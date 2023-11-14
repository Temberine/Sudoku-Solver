import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuSolver {
    
    private int[][] puzzle;
    
    public SudokuSolver(int[][] puzzle) {
        this.puzzle = puzzle;
    }
    
    public boolean solve() {
        int row = 0;
        int col = 0;
        boolean isFull = true;
        
        // Find the next empty cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == 0) {
                    row = i;
                    col = j;
                    isFull = false;
                    break;
                }
            }
            if (!isFull) {
                break;
            }
        }
        
        // If the puzzle is already full, it is solved
        if (isFull) {
            return true;
        }
        
        // Try each possible value for the empty cell
        for (int i = 1; i <= 9; i++) {
            if (isValid(row, col, i)) {
                puzzle[row][col] = i;
                if (solve()) {
                    return true;
                }
                puzzle[row][col] = 0; // backtrack
            }
        }
        
        return false;
    }
    
    private boolean isValid(int row, int col, int val) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (puzzle[row][i] == val) {
                return false;
            }
        }
        
        // Check column
        for (int i = 0; i < 9; i++) {
            if (puzzle[i][col] == val) {
                return false;
            }
        }
        
        // Check subgrid
        int subRow = (row / 3) * 3;
        int subCol = (col / 3) * 3;
        for (int i = subRow; i < subRow + 3; i++) {
            for (int j = subCol; j < subCol + 3; j++) {
                if (puzzle[i][j] == val) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public void printPuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();
        
        int[][] puzzle = new int[9][9];
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    puzzle[i][j] = fileScanner.nextInt();
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        
        SudokuSolver solver = new SudokuSolver(puzzle);
        System.out.println("Initial puzzle:");
        solver.printPuzzle();
        
        if (solver.solve()) {
            System.out.println("\nSolved puzzle:");
            solver.printPuzzle();
        } else {
            System.out.println("Puzzle could not be solved!");
        }
    }
}
    