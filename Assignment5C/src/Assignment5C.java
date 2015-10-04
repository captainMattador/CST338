import javax.swing.*;
import java.awt.*;

public class Assignment5C
{
   public static final int NUM_CARD_IMAGES = 57;
   public static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

   static void loadCardIcons()
   {
      char[] value = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
                      'J', 'Q', 'K', 'X'};
      char[] suit = {'C', 'D', 'H', 'S'};
      String fileName = "";
      int cardCount = 0;

      for (int i = 0; i < 4; i++)
         for (int j = 0; j < 14; j++, cardCount++)
         {
            fileName = "";
            fileName += String.valueOf(value[j]) + String.valueOf(suit[i])
               + ".gif";
            icon[cardCount] = new ImageIcon("images/" + fileName);
         }

      icon[cardCount] = new ImageIcon("images/BK.gif");

   }

   public static void main(String[] args)
   {
      JLabel[] cardLabel = new JLabel[NUM_CARD_IMAGES];

      loadCardIcons();

      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      frmMyWindow.setLayout(layout);

      for (int i = 0; i < NUM_CARD_IMAGES; i++)
         cardLabel[i] = new JLabel(icon[i]);

      for (int i = 0; i < NUM_CARD_IMAGES; i++)
         frmMyWindow.add(cardLabel[i]);

      frmMyWindow.setVisible(true);
   }//end main

}
