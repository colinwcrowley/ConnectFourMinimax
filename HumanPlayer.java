import java.util.Scanner;

public class HumanPlayer implements Player {
    private Scanner s = new Scanner(System.in);
    private int index;
    public int makeMove(Board b) {
      System.out.println("Type in the index from 0-6 of where you wish to drop your piece");
      index = s.nextInt();
      if(index<0 || index>6)
          System.out.println("Man fuck you!");
      return index;
    }
}
