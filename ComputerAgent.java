import java.util.HashMap;

class DreamTeamOld implements Player {
    int moveNumber = 0;

    private static class ArrayEntry {
        int across;
        int down;
        int forwardDiagonal;
        int backwardDiagonal;
    }

    public int makeMove(Board b) {
        moveNumber++;
        int depth = 11+moveNumber/3;
        int move = -1;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        HashMap<String, Integer> table = new HashMap<String, Integer>();

        if(depth == 0 || b.isOver()) {
            return staticValue(b, b.currentPlayer());
        }

        String[][] grid = b.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;
        int i;
        for(i=0; i<grid[0].length; i++) {
            if(grid[0][i]!=null)
                continue;
            Board perm = b.moveInCol(i);
            int value = this.minimize(perm, alpha, beta, depth-1, table);
            //System.out.println("value at "+i+": "+value);
            if(value>alpha) {
                alpha = value;
                move = i;
            }
        }
        //System.out.println("estimated value: "+alpha);
        return move;
    }

    private int maximize(Board b, int alpha, int beta, int depth,
            HashMap<String,Integer> table) {
        if(depth == 0 || b.isOver()) {
            return staticValue(b, b.currentPlayer());
        } else if(table.containsKey(b.toString())) {
            return table.get(b.toString());
        }

        String[][] grid = b.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;
        for(int i=0; i<grid[0].length; i++) {
            if(grid[0][i]!=null)
                continue;
            Board perm = b.moveInCol(i);
            int value = this.minimize(perm, alpha, beta, depth-1, table);
            if(value>alpha)
                alpha = value;
            if(alpha>=beta) {
                table.put(b.toString(), alpha);
                return alpha;
            }
        }
        table.put(b.toString(), alpha);
        return alpha;
    }



    private int minimize(Board b, int alpha, int beta, int depth,
            HashMap<String, Integer> table) {
        if(depth == 0 || b.isOver()) {
            return staticValue(b, b.nextPlayer());
        } else if(table.containsKey(b.toString())) {

            return table.get(b.toString());
        }
        String[][] grid = b.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;
        for(int i=0; i<grid[0].length; i++) {
            if(grid[0][i]!=null)
                continue;
            Board perm = b.moveInCol(i);
            int value = this.maximize(perm, alpha, beta, depth-1, table);
            if(value<beta)
                beta = value;
            if(alpha>=beta) {
                table.put(b.toString(), beta);
                return beta;
            }
        }
        table.put(b.toString(), beta);
        return beta;
    }

    static int staticValue(Board b, String myPeice) {
        int[] values = {0,0,1,2,1000,0,0,0};
        int score = 0;
        String[][] grid = b.getGrid();
        ArrayEntry[][] answers = new ArrayEntry[grid.length][grid[0].length];
        for(int i=0; i<answers.length; i++) {
            for(int j=0; j<answers[0].length; j++) {
                answers[i][j] = new ArrayEntry();
                //across
                if(j-1<0 || grid[i][j-1]==null || grid[i][j] == null || !grid[i][j].equals(grid[i][j-1])) {
                    answers[i][j].across = 1;
                } else {
                    answers[i][j].across = answers[i][j-1].across+1;
                }
                //down
                if(i-1<0 || grid[i-1][j]==null || grid[i][j] == null || !grid[i][j].equals(grid[i-1][j])) {
                    answers[i][j].down = 1;
                } else {
                    answers[i][j].down = answers[i-1][j].down+1;
                }
                //forwardDiagonal
                if(j-1<0 || i-1<0 || grid[i-1][j-1] == null || grid[i][j] == null || !grid[i][j].equals(grid[i-1][j-1])) {
                    answers[i][j].forwardDiagonal = 1;
                } else {
                    answers[i][j].forwardDiagonal = answers[i-1][j-1].forwardDiagonal+1;
                }
                //backwardDiagonal
                if(i-1<0 || j+1 >= grid[0].length || grid[i-1][j+1] == null || grid[i][j] == null || !grid[i][j].equals(grid[i-1][j+1])) {
                    answers[i][j].backwardDiagonal = 1;
                } else {
                    answers[i][j].backwardDiagonal = answers[i-1][j+1].backwardDiagonal+1;
                }
                if(grid[i][j] != null)
                {
                    if(grid[i][j].equals(myPeice)) {
                        score += values[answers[i][j].down];
                        score += values[answers[i][j].across];
                        score += values[answers[i][j].forwardDiagonal];
                        score += values[answers[i][j].backwardDiagonal];
                    }
                    else{
                        score -= values[answers[i][j].down];
                        score -= values[answers[i][j].across];
                        score -= values[answers[i][j].forwardDiagonal];
                        score -= values[answers[i][j].backwardDiagonal];
                    }
                }
            }
        }
        return score;
    }
}
