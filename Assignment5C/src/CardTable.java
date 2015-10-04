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
   
   static class GUICard
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
     
      static public Icon getIcon(Card card)
      {

         if (!iconsLoaded)
         {
            loadCardIcons();
         }
         
         int cardValue = valueAsInt(card.getValue());
         int suitValue = suitAsInt(card.getSuit());
         
         return iconCards[cardValue][suitValue];  
      }//end method getIcon
      
      static int valueAsInt(char cardValue)
      {
         int value = 0;
         
         for (int i = 0; i < Card.cardValue.length; i++)
            if (cardValue == Card.cardValue[i])
            {
               value = i;
               break;
            }
         
         return value;               
      }//end method valueAsInt
      
      static int suitAsInt(Card.Suit suit)
      {
         if (suit.equals("CLUBS"))
            return 0;
         else if (suit.equals("DIAMONDS"))
            return 1;
         else if (suit.equals("HEARTS"))
            return 2;
         else
            return 4;
      }//end method suitAsInt
      
      
   }//end class GUICard
   
   static class Card
   {
      // mappings for valid cards
      public enum Suit{CLUBS, DIAMONDS, HEARTS, SPADES, X};
      public static final char[] cardValue = {'A', '2', '3', '4', '5', '6', '7', 
         '8', '9', 'T', 'J', 'Q', 'K', 'X'};
      public static char[] valuRanks = {'A', '2', '3', '4', '5', '6', '7', 
            '8', '9', 'T', 'J', 'Q', 'K', 'X'};
      
      private char value;
      private Suit suit;
      private boolean errorFlag;
      
      /*
       * constructor with parameters takes a value and Suit
       * */
      public Card(char value, Suit suit)
      {
         set(value, suit);
      }
      
      /*
       * default constructor sets value to "A" and suit to spades
       * */
      public Card()
      {
         set('A', Suit.SPADES);
      }
      
      /*
       * public method to return value and suit as a string
       * */
      public String toString()
      {
         if (errorFlag == true)
            return("[invalid]");
         return String.valueOf(value) + " of " + suit;
      }
      
      /* 
       * isValid takes a char value and Suit and
       * returns true if card value is valid
       */
      private boolean isValid(char value, Suit suit)
      {

         for (int i = 0; i < cardValue.length; i++)
            if (value == cardValue[i])
               return true;
         
         return false;
      }
      
      /*
       * set takes a value and Suit, assigns them to a Card and
       * returns true if successful 
       */
      public boolean set(char value, Suit suit)
      {
         if (isValid(value, suit))
         {
            this.value = value;
            this.suit = suit;
            errorFlag = false;
            return true;
         }
         else
         {
            errorFlag = true;
            return false;
         }
      }
      
      /*
       * getValue returns a Card's value
       * */
      public char getValue()
      {
         return value;
      }
      
      /*
       * getSuit returns a Card's suit
       * */
      public Suit getSuit()
      {
         return suit;
      }
      
      /*
       * getErrorFlag returns errorFlag
       * */
      public boolean getErrorFlag()
      {
         return errorFlag;
      }
      
      /*
       *equals takes a Card and returns true if it matches the current 
       *value and Suit
       */
      public boolean equals(Card card)
      {
         if (card == this)
            return true;
         return false;
      }//end method equal
      
      public static void arraySort(Card[] cardArray, int arraySize)
      {
         Card tempCard;
         boolean flag = true;
         
         while (flag)
         {
            flag = false;
            for (int i = 0; i < arraySize; i++)
            {
               if (GUICard.valueAsInt(cardArray[i].getValue()) >
               GUICard.valueAsInt(cardArray[i + 1].getValue()))
               {
                  tempCard = cardArray[i + 1];
                  cardArray[i + 1] = cardArray[i];
                  cardArray[i] = tempCard;
                  flag = true;
               }
            }
         }
      }//end method arraySort
   }//end class Card
}//end class CardTable



