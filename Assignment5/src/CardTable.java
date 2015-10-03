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
   
      
   private static class GUICard
   {
      private static Icon[][] iconCards = new ImageIcon[14][4]; 
      private static Icon iconBack;
      static boolean iconsLoaded = false;
      

      static void loadCardIcons()
      {
         char[] value = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
                         'J', 'Q', 'K', 'X'};
         char[] suit = {'C', 'D', 'H', 'S'};
         String fileName = "";
         
         for (int i = 0; i < 4; i++)
            for (int j = 0; j < 14; j++)
            {
               fileName = "";
               fileName += String.valueOf(value[j]) + String.valueOf(suit[i])
                  + ".gif";
               iconCards[i][j] = new ImageIcon("images/" + fileName);
            }
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;
      }
    }
}