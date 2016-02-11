class BoardRunner {
  public static void main(String[] args) {
    Board b = new Board(7,6,4,"x","o");
    Player p2 = new DreamTeamOld();
    Player p1 = new HumanPlayer();
    Player[] players = {p1, p2};
    int col;
    int currPlayer = 0;
    System.out.print(b);
    while(!b.isOver()) {
      long s = System.currentTimeMillis();
      col = players[currPlayer].makeMove(b);
      long e = System.currentTimeMillis();
      System.out.println("time: "+(e-s));
      b = b.moveInCol(col);
      b.animateLastMove(col);
      currPlayer = 1 - currPlayer;
    }
    System.out.println("The Winner is: Player " + (1-currPlayer));
  }
}
