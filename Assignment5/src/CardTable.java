import javax.swing.*;
import java.awt.*;

public class CardTable extends JFrame
{
   public static int MAX_CARDS_PER_HAND = 56;
   public static int MAX_PLAYERS = 2;
   
   private int numCardsPerHand, numPlayers;
   
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super();
      setTitle(title);
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      this.pnlComputerHand = new JPanel();
      pnlComputerHand.setLayout(new GridLayout(1,6));
      this.pnlHumanHand = new JPanel();
      pnlHumanHand.setLayout(new GridLayout(1,6));
      this.pnlPlayArea = new JPanel();
      pnlPlayArea.setLayout(new GridLayout(1,2));
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;
      add(pnlComputerHand);
      add(pnlPlayArea);
      add(pnlHumanHand);
   }
   
   //accessors
   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }
   
   public int getNumPlayers()
   {
      return numPlayers;
   }//end accessors
}