//Can you tell me why my diagonal checks are not working?
//a "Right Diagonal" is like /
//a "Left Diagonal" is like \

import java.util.*;

public class Connect4 {
    private char[][] board;
    static final char YELLOW = 'X';
    static final char RED = 'O';
    static final char space = ' ';

    private char currentPlayer;
    private boolean done = false;

    static final char a = 'A';

    public Connect4() { // the constructor that initializes the instance variables
        board = new char[6][7];

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                board[i][j] = space;
            }
        }

        currentPlayer = RED;
    }

    public char[][] getBoard() { // this method should return a copy of the current board, that is the 2 dimensional array holding the game board
        char[][] copy = new char[this.board.length][this.board[0].length];

        for(int i = 0; i < this.board.length; i++) {
            for(int j = 0; j < this.board[0].length; j++) {
                copy[i][j] = this.board[i][j];
            }
        }

        return copy;
    }

    /**
     * This method inserts a piece of a particular color in the particular column. The color and column
     * are passed as arguments to the method. Remember that we don't need the row as an
     * argument because the piece always goes to the lowermost row as it would happen in the real
     * Connect4. game
     * @param column
     * @param color
     * @return row where piece has been put
     */

    public int putPiece(int column, char color) {

        for(int i = this.board.length - 1; i >= 0; i--) {
            if(this.board[i][column] == ' ') {
                this.board[i][column] = color;
                return i;
            }
        }

        return -1;
    }

    public char checkVertical(int row, int column) {
        int count = 1;
        char current = this.board[row][column];

        //up
        for(int i = row - 1; i >= 0; i--) {
            if(this.board[i][column] == current) {
                count++;
            }
            else {
                break;
            }
        }

        //down
        for(int i = row + 1; i < this.board.length; i++) {
            if(this.board[i][column] == current) {
                count++;
            }
            else {
                break;
            }
        }

        if(count >= 4) {
            return current;
        }

        return space;
    }

    public char checkHorizontal(int row, int column) {
        int count = 1;
        char current = this.board[row][column];

        //left
        for(int i = column - 1; i >= 0; i--) {
            if(this.board[row][i] == current) {
                count++;
            }
            else {
                break;
            }
        }

        //right
        for(int i = column + 1; i < this.board[row].length; i++) {
            if(this.board[row][i] == current) {
                count++;
            }
            else {
                break;
            }
        }

        if(count >= 4) {
            return current;
        }

        return space;
    }

    public char checkRightDiagonal(int row, int column) {
        int count = 1;
        char current = this.board[row][column];

        //up-right

        int i = 1;
        while(row - i >= 0 && column + i <= this.board[row].length - 1) {

            if(this.board[row - i][column + i] == current) {
                count++;
                i++;
            }
            else {
                break;
            }

        }

        //down-left

        i = 1;
        while(row + i <= this.board.length - 1 && column - i >= 0) {

            if(this.board[row + i][column - i] == current) {
                count++;
                i++;
            }
            else {
                break;
            }
        }


        if(count >= 4) {
            return current;
        }

        return space;
    }

    public char checkLeftDiagonal(int row, int column) {
        int count = 1;
        char current = this.board[row][column];

        //up-left
        int i = 1;
        while(row - i >= 0 && column - i >= 0) {

            if(this.board[row - i][column - i] == current) {
                count++;
                i++;
            }
            else {
                break;
            }
        }


        //down-right
        i = 1;
        while(row + i <= this.board.length - 1 && column + i <= this.board[row].length - 1) {

            if(this.board[row + i][column + i] == current) {
                count++;
                i++;
            }
            else {
                break;
            }
        }


        if(count >= 4) {
            return current;
        }

        return space;
    }

    public char checkAlignment(int row, int column) {

        char horizontal = checkHorizontal(row,column);
        if(horizontal != space) {
            return horizontal;
        }

        char vertical = checkVertical(row,column);
        if(vertical != space) {
            return vertical;
        }

        char right = checkRightDiagonal(row,column);
        if(right != space) {
            return right;
        }

        char left = checkLeftDiagonal(row,column);
        if(left != space) {
            return left;
        }

        return space;
    }

    public void printScreen() {
        System.out.println();
        System.out.print("  ");

        for(int i = 0; i < this.board[0].length; i++) {
            System.out.printf("   %d", i);
        }

        for(int i = 0; i < this.board.length; i++) {
            System.out.println("\n   -----------------------------");
            System.out.print((char) ('A' + i));
            System.out.print("  |");
            for(int j = 0; j < this.board[0].length; j++) {
                System.out.printf(" %c |", this.board[i][j]);
            }
        }

        System.out.println("\n   -----------------------------\n");

    }

    public void changePlayer() {
        if (this.currentPlayer == RED) {
            this.currentPlayer = YELLOW;
        }
        else {
            this.currentPlayer = RED;
        }
    }

    public void play() {

        Scanner s = new Scanner(System.in);

        while(!done) {

            printScreen();

            System.out.printf("Current Player: \'%c\'\n", this.currentPlayer);
            System.out.print("Choose a column: ");

            int column;
            column = s.nextInt();

            int row = putPiece(column, this.currentPlayer);

            if (row != -1) {

                if (checkAlignment(row, column) == space) {
                    changePlayer();
                    done = false;
                } else {
                    printScreen();
                    System.out.printf("!!! Winner is Player \'%c\' !!!", this.currentPlayer);
                    done = true;
                }
            }
        }
    }
}
