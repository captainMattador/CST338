import javax.swing.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Assignment5B
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 

   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // CREATE LABELS ----------------------------------------------------
      playedCardLabels[0] = new JLabel(GUICard.getIcon(generateRandomCard()));
      playedCardLabels[1] = new JLabel(GUICard.getIcon(generateRandomCard()));
      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("Player", JLabel.CENTER);
      
      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         humanLabels[k] = new JLabel(GUICard.getIcon(generateRandomCard()));
      }
      
      // ADD LABELS TO PANELS -----------------------------------------
      //code goes here ...
      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         myCardTable.pnlComputerHand.add(computerLabels[k]); 
         myCardTable.pnlHumanHand.add(humanLabels[k]);  
      }
     
      // and two random cards in the play region (simulating a computer/hum ply)
      //code goes here ...    
      for(k = 0; k < NUM_PLAYERS; k++)
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      
      for(k = 0; k < NUM_PLAYERS; k++)
         myCardTable.pnlPlayArea.add(playLabelText[k]);
      
      // show everything to the user
      myCardTable.setVisible(true);
      
   }//end main
   
   static Card generateRandomCard()
   {
      int suit = (int)(Math.random() * 4);
      int value = (int)(Math.random() * 14);

      return new Card(Card.cardValue[value], Card.Suit.HEARTS);
   }

}

/*------------------------------------------------------
 * CardTable
 *---------------------------------------------------- */

class CardTable extends JFrame
{
   public static int MAX_CARDS_PER_HAND = 56;
   public static int MAX_PLAYERS = 2;
   
   private static String[] pnlTitle = {"Computer Hand", "Playing Area",
      "Player Hand"};
   private int numCardsPerHand, numPlayers;
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super();
      setTitle(title);
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      setLayout(new BorderLayout());
      
      this.pnlComputerHand = new JPanel();
      this.pnlComputerHand.setBorder(
            BorderFactory.createTitledBorder(pnlTitle[0]));
      this.pnlComputerHand.setLayout(new GridLayout(1, 7));
      
      this.pnlPlayArea = new JPanel();
      this.pnlPlayArea.setBorder(
            BorderFactory.createTitledBorder(pnlTitle[1]));
      this.pnlPlayArea.setLayout(new GridLayout(2, 2));
      
      this.pnlHumanHand = new JPanel();
      this.pnlHumanHand.setBorder(
            BorderFactory.createTitledBorder(pnlTitle[2]));
      this.pnlHumanHand.setLayout(new GridLayout(1, 7));
      

      add(pnlComputerHand, BorderLayout.NORTH);
      add(pnlPlayArea, BorderLayout.CENTER);
      add(pnlHumanHand, BorderLayout.SOUTH);
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
/*------------------------------------------------------
 * end of CardTable
 *---------------------------------------------------- */


/*------------------------------------------------------
 * GUICard
 *---------------------------------------------------- */
class GUICard
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
      
      for (int i = 0; i < suit.length; i++)
         for (int j = 0; j < value.length; j++)
         {
            fileName = "";
            fileName += String.valueOf(value[j]) + String.valueOf(suit[i])
               + ".gif";
            iconCards[j][i] = new ImageIcon("images/" + fileName);
         }
      iconBack = new ImageIcon("images/BK.gif");
      iconsLoaded = true;
   }
  
   static public Icon getIcon(Card card)
   {

      if (!iconsLoaded)
         loadCardIcons();
      
      int cardValue = valueAsInt(card.getValue());
      int suitValue = suitAsInt(card.getSuit());
      
      return iconCards[cardValue][suitValue];  
   }//end method getIcon
   
   static public Icon getBackCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();
      
      return iconBack;
   }
   
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
      if (suit.equals(Card.Suit.CLUBS))
         return 0;
      else if (suit.equals(Card.Suit.DIAMONDS))
         return 1;
      else if (suit.equals(Card.Suit.HEARTS))
         return 2;
      else
         return 3;
   }//end method suitAsInt
   
}
/*------------------------------------------------------
 * end GUICard
 *---------------------------------------------------- */


/*------------------------------------------------------
 * Card
 *---------------------------------------------------- */
class Card
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
      return (this.suit.equals(card.getSuit()) && 
            this.value == card.getValue());

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
   
}
/*------------------------------------------------------
 * end of Card
 *---------------------------------------------------- */

/*------------------------------------------------------
 * Hand Class
 *---------------------------------------------------- */

class Hand
{
   
   // max number of cards allowed in hand
   public static final int MAX_CARDS = 100;
   
   private Card[] myCards = new Card[MAX_CARDS];
   private int numCards;
   
   // default constructor
   public Hand()
   {
      this.numCards = 0;
   }  
   
   /*
    * resetHand takes no parameters and fills the array with null
    * then sets the numCards back to 0
    * */
   public void resetHand()
   {
      
      Arrays.fill(myCards, null);
      numCards = 0;
      
   }
   
   /*
    * takeCard takes a Card and adds it to hand
    * returns true if successful. (makes new copy of card)
    * */
   public boolean takeCard(Card card)
   {
    
      if(numCards < MAX_CARDS)
      {
         Card addCard = new Card(card.getValue(), card.getSuit());
         myCards[numCards] = addCard;
         numCards++;
         return true;
      }
      
      return false;
   }
   
   /*
    * playCard plays card on top of the deck
    * and returns that card to the caller
    * */
   public Card playCard()
   {
      
      if(numCards == 0)
         return null;
      
      Card card = new Card(myCards[numCards -1].getValue(),
            myCards[numCards -1].getSuit());
      myCards[numCards -1] = null;
      numCards--;
      
      return card;
      
   }
   
   /*
    * toString Displays the hand as a string
    * */
   public String toString() 
   {
      
      String handOfCards = "(";
      
      for(int i = 0; i < numCards; i++)
      {
         handOfCards += (i == numCards - 1) ? myCards[i].toString() : 
            myCards[i].toString() + ", ";
      }
      
      handOfCards += ")";
      
      return handOfCards;
      
   }

   /*
    * getNumCards returns the number of cards in the hand
    * */
   public int getNumCards()
   {
      
      return numCards;
      
   }
   
   /*
    * inspectCard returns the card asked for,
    * if the card is out of bounds it returns a card with
    * errorFlag true
    * */
   public Card inspectCard(int k)
   {
      
      if(k >= 0 && k < numCards)
         return myCards[k];
      
      // send a bad card so error flag is set
      return new Card('e', Card.Suit.CLUBS);
      
   }
   
   public void sort()
   {
      Card.arraySort(myCards, numCards);
   }
   
}

/*------------------------------------------------------
 * end of hand
 *---------------------------------------------------- */



/*------------------------------------------------------
 * Deck
 *---------------------------------------------------- */
class Deck
{
   
   //setting maximum number of packs to six
   public static final int MAX_CARDS = 6*56;
   
   private static final int NUMBER_OF_CARDS = 56;
   private static Card[] masterPack = new Card[NUMBER_OF_CARDS];
   
   private Card cards[];//array of card object
   private int topCard;// index of next card to be dealt
   private int numPacks;// number of packs
     
   //constructor initialize number of packs
   public Deck()
   {            
      this.numPacks = 1;
      
      // populate masterPack
      allocateMasterPack();
      
      // create the deck
      init(numPacks);
   }
   
   /*
    * constructor populates array masterPack and 
    * assign initial values
    */
   public Deck(int numPacks)
   {      
      // check it isn't over MAX_CARD limit
      if(NUMBER_OF_CARDS * numPacks > MAX_CARDS)
         numPacks = 1;
      
      // populate masterPack
      allocateMasterPack();
      
      // create the deck
      init(numPacks);
   }
   
   /* 
    * method init re-populates array cards with 
    * new number of cards using new number of packs 
    */
   public void init(int numPacks) 
   {
      cards = new Card[NUMBER_OF_CARDS * numPacks];
      this.topCard = 0;
      this.numPacks = numPacks;
      int count = 0;
     
      for(int i = 0; i < cards.length; i++)
      { 
         cards[i] = new Card(masterPack[count].getValue(), 
               masterPack[count].getSuit());
         topCard++;
         count++;
         
         // count is 52 reset it back to 0 to start over
         if(count == NUMBER_OF_CARDS)
            count = 0;     
      }
   }
   
   //method shuffle() mixes up cards in a deck of cards
   public void shuffle() 
   {
      for (int i = 0 ; i < cards.length; i++)
      {
         Card temp;
         Random randomGenerator = new Random();
         int randomCard = randomGenerator.nextInt(NUMBER_OF_CARDS * numPacks);
         
         temp = cards[i];
         cards[i] = cards[randomCard];
         cards[randomCard] = temp;
      }
   }
   
   /*
    * method dealCard() deals number of cards
    * by checking the availability of cards 
    */
   public Card dealCard()
   {
      if(topCard == 0)
         return null;
      
      Card card = new Card(cards[topCard -1].getValue(),
            cards[topCard -1].getSuit());
      cards[topCard -1] = null;
      topCard--;
      
      return card;
   }
   
   /* 
    * accessor to get index of top card 
    * in cards array
    */
   public int getTopCard()
   {
      return topCard;
   }
   
   /* 
    * Inspects a card at k index
    * returns the card or a bad card if k is not a good index
    */
   public Card inspectCard(int k)
   {
      
      if(k < cards.length)
         return cards[k];
      
      // send a bad card so error flag is set
      return new Card('e', Card.Suit.CLUBS);
      
   }
   
   
   /* 
    * private method to allocate the master deck
    * master deck is used on all deck instances
    */
   private static void allocateMasterPack() 
   {
      // if last card in masterPack isn't null,
      // it's already been initiated so return early
      if(masterPack[NUMBER_OF_CARDS -1] != null)
         return;
      
      Card.Suit suit;
      
      for(int i = 0; i < masterPack.length; i++)
      {  
         if(i < 14)
            suit = Card.Suit.SPADES;
         else if(i >= 14 && i < 28)
            suit = Card.Suit.CLUBS;
         else if(i >= 28 && i < 42)
            suit = Card.Suit.HEARTS;
         else
            suit = Card.Suit.DIAMONDS;

         masterPack[i] = new Card(Card.cardValue[ i % 14 ], suit);
      }
   }


   public boolean removeCard(Card card)
   {
      for(int i = 0; i < topCard; i++)
      {
         if(cards[i].equals(card))
         {
            cards[i].set(cards[topCard].getValue(), cards[topCard].getSuit());
            cards[topCard] = null;
            topCard--;
            return true;
         }
      }
      
      return false;
   }
   
   public boolean addCard(Card card)
   {
      int numOfInstances = 0;
      for(int i = 0; i < topCard; i++)
      {
         if(cards[i].equals(card))
            numOfInstances++;
      }
      
      if(numOfInstances >= numPacks)
         return false;
      
      cards[topCard].set(card.getValue(), card.getSuit());
      topCard++;
      
      return true;
   }
   
   public int getNumCards()
   {
      return topCard;
   }
   
   public void sort()
   {
      Card.arraySort(cards, topCard);
   }
   
}

/*------------------------------------------------------
 * end of Deck
 *---------------------------------------------------- */
