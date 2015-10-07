/*
* Student
* Assignment 5A
* 
* The idea here is to get the application to read the carsd and assign
* the correct icon to it for the GUI 
*
* */

import javax.swing.*;
import java.awt.*;

public class Assignment5A
{
   //Number of cards assigned to a constant variable
   public static final int NUM_CARD_IMAGES = 57;
   //An array to hold the cards image
   public static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   
   //method to build file names 
   static void loadCardIcons()
   {
	  //card values stored in array 
      char[] value = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
                      'J', 'Q', 'K', 'X'};
      //card suit stored in array
      char[] suit = {'C', 'D', 'H', 'S'};
      String fileName = "";
      int cardCount = 0;
      
      /*for loop to iterate through the four characters that represent
       * part of file name of card image
       */
      for (int i = 0; i < 4; i++)
    	 /*for loop to iterate through the 13 charaters that represent 
    	  * part of file name of card image
    	  */
         for (int j = 0; j < 14; j++, cardCount++)
         {
            fileName = "";
            fileName += String.valueOf(value[j]) + String.valueOf(suit[i])
               + ".gif";
            icon[cardCount] = new ImageIcon("images/" + fileName);
         }
      
      icon[cardCount] = new ImageIcon("images/BK.gif");
         
   }
   
   //main method to create JLabel, JFrame, and FlowLayout objects
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
