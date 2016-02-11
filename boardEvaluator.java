
public class boardEvaluator {
    public static boolean CurIsOver=false;
    public static boolean NextIsOver=false;
    public static int getState(Board b){
        String[][]board=b.getGrid();
        String[][][]newBoard=new String[6][7][5];
        //the first char in the string at each coordinate is the player's string
        //the second, third, fourth char in the string in each location is the number of horizontal, vertical, and diagonal
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                String curVal=board[i][j];
                if(curVal != null){
                    if(board[i][j].equals(b.currentPlayer())){
                        putValue(b,curVal,board,newBoard,i,j,b.currentPlayer());
                    }
                    else if(board[i][j].equals(b.nextPlayer())){
                        putValue(b,curVal,board,newBoard,i,j,b.nextPlayer());
                    }
                    if(NextIsOver==true){
                        return -100;
                    }
                    if(CurIsOver==true){
                        return 100;
                    }
                }
            }
        }
        return answer(newBoard,b);
    }
    public static int answer(String[][][]newBoard, Board b){
        int curPlayerCount=0;
        int nextPlayerCount=0;
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                if(b.currentPlayer().equals(newBoard[i][j][0])){
                    adder(curPlayerCount,newBoard,i,j);
                }else if(b.nextPlayer().equals(newBoard[i][j][0])){
                    adder(curPlayerCount,newBoard,i,j);
                }
            }
        }
        return (curPlayerCount-nextPlayerCount);
    }
    public static void adder(int count, String[][][]newBoard, int i, int j){
        int curVal=0;
        for(int k=1;k<5;k++){
            try{
                curVal=Integer.parseInt(newBoard[i][j][k]);
            }catch(Exception e){
                //print(newBoard);
                //System.exit(1);
            }
            count+=curVal;
            if(curVal==2){
                count-=1;
            }
            if(curVal==3){
                count-=2;
            }
        }
        count+=curVal;
    }
    public static void print(String[][][]newBoard){
        for(int i=0;i<newBoard.length;i++){
            for(int j=0;j<newBoard[0].length;j++){
                System.out.print("{ ");
                for(int k=0;k<newBoard[0][0].length;k++){
                    System.out.print(newBoard[i][j][k]+", ");
                }
                System.out.print("}, ");
            }
            System.out.println();
        }
    }
    //I need to change the hard-coded 3 to something different so that the program does not dynamically call upon it and think that there are three pieces there
    private static void putValue(Board b,String curVal, String[][] board,
            String[][][] newBoard, int i, int j, String player) {
        // TODO Auto-generated method stub
        newBoard[i][j][0]=player;
        if(i!=0){
            if(newBoard[i-1][j][1] == null) newBoard[i][j][1] = "0";
            if(newBoard[i-1][j][1] != null){
                if(newBoard[i-1][j][0].equals(player)){

                    int curInt=(Integer.parseInt(newBoard[i-1][j][1])+1);
                    newBoard[i][j][1]=curInt+"";
                    System.out.println(curInt);
                    if(curInt==4){

                        if(b.currentPlayer().equals(player)){
                            CurIsOver=true;
                        }
                        if(b.nextPlayer().equals(player)){
                            NextIsOver=true;
                        }
                    }
                    if(Integer.parseInt(newBoard[i][j][1])==1 && i-3>=0){
                        if(newBoard[i-3][j][1]!=null && Integer.parseInt(newBoard[i-3][j][1])>=2){
                            newBoard[i][j][1]="3";
                        }
                    }
                    if(Integer.parseInt(newBoard[i][j][1])==2){
                        if(newBoard[i-2][j][1]!=null && Integer.parseInt(newBoard[i-2][j][1])>=1){
                            newBoard[i][j][1]="3";
                        }
                    }
                }
            }
        }else{
            newBoard[i][j][1]="0";
        }
        if(j!=0){
            newBoard[i][j][0]=player;
            if(newBoard[i][j-1][2] == null) newBoard[i][j][1] = "0";
            if(newBoard[i][j-1][2] != null){
                if(newBoard[i][j-1][0].equals(player)){
                    int curInt=(Integer.parseInt(newBoard[i][j-1][2])+1);
                    newBoard[i][j][2]=curInt+"";
                    System.out.println(curInt);
                    if(curInt==4){
                        System.out.println("HERE");
                        if(b.currentPlayer().equals(player)){
                            CurIsOver=true;
                        }
                        if(b.nextPlayer().equals(player)){
                            NextIsOver=true;
                        }
                    }
                }
            }
            //on the vertical case you do not need to check if there is a two pieces and then one because gravity
        }else{
            newBoard[i][j][2]="0";
        }
        if(i!=0 && j!=0){
            if(newBoard[i-1][j-1][3] == null) newBoard[i][j][1] = "0";
            if(newBoard[i-1][j-1][3] != null){
                if(newBoard[i-1][j-1][0].equals(player)){
                    int curInt=(Integer.parseInt(newBoard[i-1][j-1][3])+1);
                    newBoard[i][j][3]=curInt+"";
                    System.out.println(curInt);
                    if(curInt==4){
                        if(b.currentPlayer().equals(player)){
                            CurIsOver=true;
                        }
                        if(b.nextPlayer().equals(player)){
                            NextIsOver=true;
                        }
                    }

                    if(Integer.parseInt(newBoard[i][j][3])==1 && i-3>=0 && j-3>=0){
                        if(newBoard[i-2][j-2][3]!=null && Integer.parseInt(newBoard[i-2][j-2][3])>=2){
                            newBoard[i][j][3]="3";
                        }
                    }
                    if(Integer.parseInt(newBoard[i][j][3])==2 && i-2>=0 && j-2>=0){
                        if(newBoard[i-2][j-2][3]!=null && Integer.parseInt(newBoard[i-2][j-2][3])>=1){
                            newBoard[i][j][3]="3";
                        }
                    }
                }
            }
        }else{
            newBoard[i][j][3]="0";
        }
        if(i!=5 && j!=0){
            if(newBoard[i+1][j-1][4] == null) newBoard[i][j][1] = "0";
            if(newBoard[i+1][j-1][4] != null){
                if(newBoard[i+1][j-1][0].equals(player)){
                    int curInt=(Integer.parseInt(newBoard[i+1][j-1][4])+1);
                    newBoard[i][j][4]=curInt+"";
                    System.out.println(curInt);
                    if(curInt==4){
                        if(b.currentPlayer().equals(player)){
                            CurIsOver=true;
                        }
                        if(b.nextPlayer().equals(player)){
                            NextIsOver=true;
                        }
                    }
                    if(Integer.parseInt(newBoard[i][j][4])==1 && i+3<=5 && j-3>=0){
                        if(newBoard[i+2][j-2][1]!=null && Integer.parseInt(newBoard[i+2][j-2][3])>=2){
                            newBoard[i][j][1]="3";
                        }
                    }
                    if(Integer.parseInt(newBoard[i][j][4])==2 && i+2<=5 && j-2>=0){
                        if(newBoard[i+2][j-2][1]!=null && Integer.parseInt(newBoard[i+2][j-2][3])>=1){
                            newBoard[i][j][1]="3";
                        }
                    }
                }
            }
        }else{
            newBoard[i][j][4]="0";
        }
    }
}
