import javax.swing.*;
import java.awt.*;

public class Assignment5
{
   public static final int NUM_CARD_IMAGES = 57;
   public static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   
   static void loadCardIcons()
   {
      char[] value = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
                      'J', 'Q', 'K', 'X'};
      char[] suit = {'C', 'H', 'S', 'D'};
      String fileName = "";
      int cardCount = 1;
      
      icon[0] = new ImageIcon("images/BK.gif");
      for (int i = 0; i < 4; i++)
         for (int j = 0; j < 14; j++, cardCount++)
         {
            fileName = "";
            fileName += String.valueOf(value[j]) + String.valueOf(suit[i])
               + ".gif";
            icon[cardCount] = new ImageIcon("images/" + fileName);
            System.out.println(fileName);
         }
         
   }
   
   public static void main(String[] args)
      {
        loadCardIcons();

      }//end main

}
