import java.util.Scanner;

class TestRunner {
   public static void main(String[] args) {
      Player p = new DreamTeam();
      Scanner s = new Scanner(System.in);
      System.out.print("player 1: ");
      String p1 = s.nextLine();
      System.out.print("player 2: ");
      String p2 = s.nextLine();

      Board b = new Board(7, 6, 4, p1, p2);

      System.out.println(b);
      while(!b.isOver()) {
         System.out.print(">>>");
         int move = s.nextInt();
         b = b.moveInCol(move);
         System.out.println(b);
         //b.animateLastMove(move);
         if(b.isOver()) {
            System.out.println("You win!!");
            System.exit(0);
         }
         move = p.makeMove(b);
         b = b.moveInCol(move);
         //b.animateLastMove(move);
         System.out.println(b);
      }
      System.out.println("It sucks to suck!");
   }
}
