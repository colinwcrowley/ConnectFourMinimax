class Board {
private String[][] grid;
private int goal;
private String p1;
private String p2;
private String currentPlayer;

private static class ArrayEntry {
int across;
int down;
int forwardDiagonal;
int backwardDiagonal;
}

public Board(int width, int height, int goal, String p1, String p2) {
        grid = new String[height][width];
        this.goal = goal;
        this.p1 = p1;
        this.p2 = p2;
        currentPlayer = p1;
}

private Board(String[][] grid, int goal, String p1, String p2, String currentPlayer) {
        this.grid = grid;
        this.goal = goal;
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = currentPlayer;
}

public boolean isOver() {
        ArrayEntry[][] answers = new ArrayEntry[grid.length][grid[0].length];
        for(int i=0; i<answers.length; i++) {
                for(int j=0; j<answers[0].length; j++) {
                        answers[i][j] = new ArrayEntry();
                        //across
                        if(grid[i][j] == null) {
                                answers[i][j].across = 0;
                        } else if(j-1<0 || grid[i][j-1]==null /*|| grid[i][j] == null*/ || !grid[i][j].equals(grid[i][j-1])) {
                                answers[i][j].across = 1;
                        } else {
                                answers[i][j].across = answers[i][j-1].across+1;
                                if(answers[i][j].across >= 4) {
                                        return true;
                                }
                        }
                        //down
                        if(grid[i][j] == null) {
                                answers[i][j].down = 0;
                        } else if(i-1<0 || grid[i-1][j]==null /*|| grid[i][j] == null*/ || !grid[i][j].equals(grid[i-1][j])) {
                                answers[i][j].down = 1;
                        } else {
                                answers[i][j].down = answers[i-1][j].down+1;
                                if(answers[i][j].down >= 4) {
                                        return true;
                                }
                        }
                        //forwardDiagonal
                        if(grid[i][j] == null) {
                                answers[i][j].forwardDiagonal = 0;
                        } else if(j-1<0 || i-1<0 || grid[i-1][j-1] == null /*|| grid[i][j] == null*/ || !grid[i][j].equals(grid[i-1][j-1])) {
                                answers[i][j].forwardDiagonal = 1;
                        } else {
                                answers[i][j].forwardDiagonal = answers[i-1][j-1].forwardDiagonal+1;
                                if(answers[i][j].forwardDiagonal >= 4) {
                                        return true;
                                }
                        }
                        //backwardDiagonal
                        if(grid[i][j] == null) {
                                answers[i][j].backwardDiagonal = 0;
                        } else if(i-1<0 || j+1 >= grid[i].length || grid[i-1][j+1] == null /*|| grid[i][j] == null*/ || !grid[i][j].equals(grid[i-1][j+1])) {
                                answers[i][j].backwardDiagonal = 1;
                        } else {
                                answers[i][j].backwardDiagonal = answers[i-1][j+1].backwardDiagonal+1;
                                if(answers[i][j].backwardDiagonal >= 4) {
                                        return true;
                                }
                        }
                        //System.out.print(answers[i][j].backwardDiagonal+" ");
                }
                //System.out.println();
        }
        return false;
}

public String[][] getGrid() {
        return copy(grid);
}

public String currentPlayer() {
        return currentPlayer;
}
public String nextPlayer() {
        return nextPlayer(currentPlayer());
}
public String nextPlayer(String currentPlayer1) {
        if(currentPlayer1.equals(p1))
                return p2;
        else
                return p1;
}

public Board moveInCol(int col) {   //0 indexed
        String[][] gridCopy = copy(grid);
        for (int i=gridCopy.length-1; i>=0; i--) {
                if(gridCopy[i][col] == null) {
                        gridCopy[i][col] = currentPlayer;
                        break;
                }
        }
        return new Board(gridCopy, goal, p1, p2, nextPlayer(currentPlayer));
}

public Board copy() {
        return new Board(copy(grid), goal, p1, p2, currentPlayer);
}

private String[][] copy(String[][] array) {
        String[][] array2 = new String[array.length][array[0].length];
        for(int i=0; i<array.length; i++) {
                for(int j=0; j<array[0].length; j++) {
                        array2[i][j] = array[i][j];
                }
        }
        return array2;
}

public void animateLastMove(int col) {
        if(grid[grid.length-1][col]==null) {
                System.out.println("ERROR: No peices in column " + col);
        }
        String[][] copy = copy(grid);
        int topOfStackIndex = grid.length-1;
        for(int i=grid.length-2; i>=0; i--) {
                if(grid[i][col]!=null) {
                        topOfStackIndex = i;
                }
        }
        swap(copy, col, 0, topOfStackIndex);
        for(int i=0; i<topOfStackIndex; i++) {
                clearScreen();
                System.out.println(gridToString(copy));
                swap(copy, col, i, i+1);
                try {
                        Thread.sleep(500);
                } catch (Exception e) {
                        //e.printStackTrace();
                }
        }
        clearScreen();
        System.out.println(gridToString(copy));
}

private void swap(String[][] a, int col, int row1, int row2) {
        String temp = a[row1][col];
        a[row1][col] = a[row2][col];
        a[row2][col] = temp;
}

private static void clearScreen()
{
        for(int i = 0; i < 100; i++)
        {
                System.out.println();
        }
}

private String gridToString(String[][] array) {
        String str = "";
        for(int i=0; i<array[0].length; i++) {
                str += "  "+i+" ";
        }
        str += "\n";
        str += "\n";
        for(int i=0; i<array.length; i++) {
                for(int j=0; j<array[0].length; j++) {
                        if(array[i][j]==null)
                                str+="|   ";
                        else
                                str+="| "+array[i][j]+" ";
                }
                str+="|\n";
        }
        return str;
}

public String toString() {
        return gridToString(grid);
}

}
