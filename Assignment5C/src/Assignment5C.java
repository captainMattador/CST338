import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class Assignment5C
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2; 

   public static void main(String[] args)
   {
      
      Player humanPlayer;
      ComputerPlayer computerPlayer;
      int numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack;
      Card[] unusedCardsPerPack;
     
      numPacksPerDeck = 1;
      numJokersPerPack = 0;
      numUnusedCardsPerPack = 0;
      unusedCardsPerPack = null;

      CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      highCardGame.deal();
      highCardGame.sortHands();
      computerPlayer = new ComputerPlayer(highCardGame.getHand(0), "Computer");
      humanPlayer = new Player(highCardGame.getHand(1), "Player 1");
      
      HighCard playGame = new HighCard(computerPlayer, humanPlayer,
            NUM_CARDS_PER_HAND, NUM_PLAYERS);
      
      playGame.initGame();

      
   }//end main

}

/*------------------------------------------------------
 * High Card Game
 *---------------------------------------------------- */

class HighCard
{
   private CardTable myCardTable;

   private Player humanPlayer;
   private ComputerPlayer computerPlayer;
   private Card playersPlayedCard;
   private Card cpuPlayedCard;
   private int numOfPlayers;
   private int cardsPerHand;
   private int cardsLeft;
   private boolean cpuPlaysFirst;
   
   public HighCard()
   {
      this.computerPlayer = null;
      this.humanPlayer = null;
      this.cardsPerHand = 0;
      this.cardsLeft = 0;
      this.cpuPlaysFirst = false;
      this.cpuPlayedCard = null;
      this.playersPlayedCard = null;
   }
   
   public HighCard(ComputerPlayer computerPlayer, Player humanPlayer, 
         int cardsPerHand, int numOfPlayers)
   {
      this.computerPlayer = computerPlayer;
      this.humanPlayer = humanPlayer;
      this.cardsPerHand = cardsPerHand;
      this.cardsLeft = cardsPerHand;
      this.numOfPlayers = numOfPlayers;
      this.cpuPlaysFirst = true;
      this.cpuPlayedCard = null;
      this.playersPlayedCard = null;
   }
   
   public int getCardsLeft()
   {
      return cardsLeft;
   }
   
   public void initGame()
   {
      if(computerPlayer == null || humanPlayer == null)
         return;
      
      createBoard(cardsPerHand, 2);
      playRoundUI();
   }
   
   
   /*
    * Helper validation method for each round
    * */
   private void validateRound()
   {
      String message = "";
      int cpuPlayedCardValue = GUICard.valueAsInt(cpuPlayedCard.getValue());
      int playerCardValue = GUICard.valueAsInt(playersPlayedCard.getValue());
      
      if( cpuPlayedCardValue > playerCardValue)
      {
         message = "Sorry you lost this round.";
         computerPlayer.updateWinnings(cpuPlayedCard, playersPlayedCard);
         cpuPlaysFirst = true;
      }else if(cpuPlayedCardValue == playerCardValue)
      {
         message = "This round was a wash!";
      }else
      {
         message = "Congratulations! You won this round.";
         humanPlayer.updateWinnings(cpuPlayedCard, playersPlayedCard);
         cpuPlaysFirst = false;
      }
      
      cardsLeft--;
      roundOutcomeUI(message);
   }
   
   private void endGameUI()
   {
      clearUI();
      int k;
      int cpuScore = computerPlayer.getScore() / 2;
      int playerScore = humanPlayer.getScore() / 2;
      String winner = "";
      
      // add blank card holders
      for(k = 0; k < cardsPerHand; k++)
      {
         myCardTable.pnlHumanHand.add(
               new JLabel(GUICard.getBlankCardIcon()));
         myCardTable.pnlComputerHand.add(
               new JLabel(GUICard.getBlankCardIcon()));
      }
      
      myCardTable.pnlPlayAreaMessage.setLayout(new GridLayout(5, 1));
      
      myCardTable.pnlPlayAreaMessage.add(
            new JLabel("Game Over", JLabel.CENTER));
      
      myCardTable.pnlPlayAreaMessage.add(
            new JLabel(
                  computerPlayer.getName() + " won "  + cpuScore + " rounds", 
                  JLabel.CENTER));
      myCardTable.pnlPlayAreaMessage.add(
            new JLabel(
                  humanPlayer.getName() + " won "  + playerScore + " rounds", 
                  JLabel.CENTER));
      
      if(playerScore > cpuScore)
         winner = "Congratulations! You won!";
      else if(playerScore == cpuScore)
         winner = "You tied with the the computer!";
      else
         winner = "Sorry, you lost the game.";
      
      myCardTable.pnlPlayAreaMessage.add(new JLabel(winner, JLabel.CENTER));
      
      rePaintUI();
   }
   /*
    * UI screen for playing a round
    * */
   private void playRoundUI()
   { 
      // game is over
      if(cardsLeft == 0)
      {
         endGameUI();
         return;
      }
      
      clearUI();
      int k;
      String message = "";
      Hand humanHand = humanPlayer.getHand();
      Hand cpuHand = computerPlayer.getHand();
      Border emptyBorder = BorderFactory.createEmptyBorder();
      Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
      
      // players cards as buttons
      for(k = 0; k < cardsPerHand; k++)
      {  
         if(k > cardsLeft - 1)
         {
            // blanks for played cards
            myCardTable.pnlHumanHand.add(
                  new JLabel(GUICard.getBlankCardIcon()));
         }else
         {
            JButton playCardBtn = new JButton(
                  GUICard.getIcon(humanHand.inspectCard(k)));
            playCardBtn.setBorder(emptyBorder);
            playCardBtn.setCursor(cursor);
            playCardBtn.addActionListener(new PlayCardListener(k));
            myCardTable.pnlHumanHand.add(playCardBtn);
         }
      }
      
      if(cpuPlaysFirst)
      {
         // cpu is first. play there card and show it
         cpuPlayedCard = computerPlayer.playCard();
         myCardTable.pnlPlayAreaComputer.add(
               new JLabel(GUICard.getIcon(cpuPlayedCard)));
         myCardTable.pnlPlayAreaComputer.add(
               new JLabel(computerPlayer.getName(), JLabel.CENTER));

         message = "Your turn to play. Select your card.";
      }else
         message = "You play first. Select your card.";
      
      // add the center play table message
      myCardTable.pnlPlayAreaMessage.add(new JLabel(message, JLabel.CENTER));
      
      // add cpu cards separately in case they played first
      for(k = 0; k < cardsPerHand; k++)
      {
         if(k > cpuHand.getNumCards() -1)
         {
            myCardTable.pnlComputerHand.add(
                  new JLabel(GUICard.getBlankCardIcon()));
         }else
         {
            // computers hand
            myCardTable.pnlComputerHand.add(
                  new JLabel(GUICard.getBackCardIcon()));
         }
      }
      
      rePaintUI();
   }
   
   
   /*
    * Screen that shows the outcomes of the
    * round
    * */
   private void roundOutcomeUI(String message)
   {
      clearUI();
      
      int k = 0;
      Hand humanHand = humanPlayer.getHand();
      Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
      
      for(k = 0; k < cardsPerHand; k++)
      {
         
         if(k > cardsLeft - 1)
         {
            // add blank cards as placeholders
            myCardTable.pnlComputerHand.add(
                  new JLabel(GUICard.getBlankCardIcon()));
            myCardTable.pnlHumanHand.add(
                  new JLabel(GUICard.getBlankCardIcon()));
         }else
         {
            // add computers hand
            myCardTable.pnlComputerHand.add(
                  new JLabel(GUICard.getBackCardIcon()));
            // add players hand as static
            myCardTable.pnlHumanHand.add(
                  new JLabel(GUICard.getIcon(humanHand.inspectCard(k))));
         }
      }
      
      myCardTable.pnlPlayAreaComputer.add(
            new JLabel(GUICard.getIcon(cpuPlayedCard)));
      myCardTable.pnlPlayAreaComputer.add(
            new JLabel(computerPlayer.getName(), JLabel.CENTER));
      myCardTable.pnlPlayAreaHuman.add(
            new JLabel(GUICard.getIcon(playersPlayedCard)));
      myCardTable.pnlPlayAreaHuman.add(
            new JLabel(humanPlayer.getName(), JLabel.CENTER));
      
      JButton nextRoundBtn = new JButton("Next Round");
      nextRoundBtn.addActionListener(new AdvanceRound());
      nextRoundBtn.setCursor(cursor);
      
      myCardTable.pnlPlayAreaMessage.add(new JLabel(message, JLabel.CENTER));
      myCardTable.pnlPlayAreaMessage.add(nextRoundBtn);
      
      rePaintUI();
   }
   
   
   private void clearUI()
   {
      myCardTable.pnlComputerHand.removeAll();
      myCardTable.pnlHumanHand.removeAll();
      myCardTable.pnlPlayAreaComputer.removeAll();
      myCardTable.pnlPlayAreaHuman.removeAll();
      myCardTable.pnlPlayAreaMessage.removeAll();
   }
   
   private void rePaintUI()
   {
      myCardTable.getContentPane().validate();
      myCardTable.getContentPane().repaint();
   }
   
   private void createBoard(int cardsPerHand, int numOfPlayers)
   {
      myCardTable 
      = new CardTable("CardTable", cardsPerHand, numOfPlayers);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myCardTable.setVisible(true);
   }
   
   private class AdvanceRound implements ActionListener
   {

      @Override
      public void actionPerformed(ActionEvent e)
      {
         playRoundUI();
      }
      
   }
   
   private class PlayCardListener implements ActionListener
   {
      private int cardIndex;
      
      public PlayCardListener(int cardIndex)
      {
         this.cardIndex = cardIndex;
      }
      
      @Override
      public void actionPerformed(ActionEvent e)
      {
         
         playersPlayedCard = humanPlayer.playCard(cardIndex);
         
         if(!cpuPlaysFirst)
            cpuPlayedCard = computerPlayer.playCard(playersPlayedCard);
         
         validateRound();
 
      }
      
   }
   
}

/*------------------------------------------------------
 * end High Card Game
 *---------------------------------------------------- */

/*------------------------------------------------------
 *class Player
 *---------------------------------------------------- */

class Player
{
   
   protected Hand hand;
   protected int score;
   protected String playerName;
   protected Card[] winnings;
   
   Player()
   {
      hand = null;
      score = 0;
      playerName = "";
      winnings = null;
   }
   
   Player(Hand hand, String name)
   {
      this.hand = hand;
      score = 0;
      setName(name);
      winnings = new Card[hand.getNumCards() * 2]; 
   }
   
   public int getScore()
   {
      return score;
   }
   
   public void updateScore()
   {
      score += 2;
   }
   
   public void updateWinnings(Card winningCardOne, Card winningCardTwo)
   {
      winnings[score] = new Card(winningCardOne.getValue(), 
            winningCardOne.getSuit());
      winnings[score + 1] = new Card(winningCardOne.getValue(), 
            winningCardOne.getSuit());
      updateScore();
   }
   
   public Card[] getWinnings()
   {
      return winnings;
   }
   
   public Hand getHand()
   {
      return hand;
   }
   
   public Card playCard(int index)
   {
      return hand.playCard(index);
   }
   
   public String getName()
   {
      return playerName;
   }
   
   public boolean setName(String name)
   {
      if (name == "")
         return false;
      playerName = name;
      return true;
   }
}

/*------------------------------------------------------
 * end class Player
 *---------------------------------------------------- */

/*------------------------------------------------------
 *class ComputerPlayer
 *---------------------------------------------------- */

class ComputerPlayer extends Player
{
   
   ComputerPlayer()
   {
      super();
   }
   
   ComputerPlayer(Hand hand, String name)
   {
      super(hand, name);
   }
   
   public Card playCard(Card opponentCard)
   {

      int winningCardIndex = 0;
      
      for (int i = 0; i < hand.getNumCards(); i++)
      {
         if (hand.inspectCard(i).getValue() > opponentCard.getValue())
            winningCardIndex = i;   
         
         if (hand.inspectCard(i).getValue() <
               hand.inspectCard(winningCardIndex).getValue() &&
               hand.inspectCard(i).getValue() > opponentCard.getValue())
            winningCardIndex = i;
      }
      
      return hand.playCard(winningCardIndex);      
   }
   
   public Card playCard()
   {
      return hand.playCard();
   }
   
   
}

/*------------------------------------------------------
 * end class ComputerPlayer
 *---------------------------------------------------- */

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
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, pnlPlayAreaCards, 
      pnlPlayAreaMessage, pnlPlayAreaComputer, pnlPlayAreaHuman;
   
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
      this.pnlPlayAreaCards = new JPanel();
      this.pnlPlayAreaComputer = new JPanel();
      this.pnlPlayAreaHuman = new JPanel();
      this.pnlPlayAreaMessage = new JPanel();
      
      this.pnlPlayArea.setBorder(
            BorderFactory.createTitledBorder(pnlTitle[1]));
      this.pnlPlayArea.setLayout(new BoxLayout(this.pnlPlayArea, 
            BoxLayout.Y_AXIS));
      this.pnlPlayAreaCards.setLayout(new FlowLayout());
      this.pnlPlayAreaComputer.setLayout(new BoxLayout(this.pnlPlayAreaComputer, 
            BoxLayout.Y_AXIS));
      this.pnlPlayAreaHuman.setLayout(new BoxLayout(this.pnlPlayAreaHuman, 
            BoxLayout.Y_AXIS));
      this.pnlPlayAreaCards.add(this.pnlPlayAreaComputer);
      this.pnlPlayAreaCards.add(this.pnlPlayAreaHuman);
      this.pnlPlayArea.add(this.pnlPlayAreaCards);
      this.pnlPlayArea.add(this.pnlPlayAreaMessage);
      
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
   private static Icon iconBlank;
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
      iconBlank = new ImageIcon("images/blank.gif");
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
   
   static public Icon getBlankCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();
      
      return iconBlank;
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
   public enum Suit{CLUBS, DIAMONDS, HEARTS, SPADES};
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
   
   private static int valueIndex(Card card)
   {

      for(int i = 0; i < valuRanks.length; i++)
      {
         if(card.getValue() == valuRanks[i])
            return i;
      }
      
      return -1;
   }
   public static void arraySort(Card[] cardArray, int arraySize)
   {
      Card tempCard;

      for(int i = 0; i < arraySize; i++){
         for(int j = 1; j < arraySize - i; j++){
            if(valueIndex(cardArray[j-1]) > valueIndex(cardArray[j])){
               tempCard = cardArray[j-1];
                cardArray[j-1] = cardArray[j];
                cardArray[j] = tempCard;
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
    * playCard plays card on top of the deck
    * and returns that card to the caller
    * */
   public Card playCard(int index)
   {    
      
      if(numCards == 0 || index > numCards)
         return null;
      
      Card card = new Card(myCards[index].getValue(), myCards[index].getSuit());
      
      for(int i = index; i < numCards - 1; i++)
         myCards[i].set(myCards[i + 1].getValue(), myCards[i + 1].getSuit());
      
      myCards[numCards - 1] = null;
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


/*------------------------------------------------------
 * CardGameFramework
 *---------------------------------------------------- */
class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;            // # standard 52-card packs per deck
                                  // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
                                  // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
                                      // in the game.  e.g. pinochle does not
                                      // use cards 2-8 of any suit

   public CardGameFramework( int numPacks, int numJokersPerPack,
          int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
          int numPlayers, int numCardsPerHand)
   {
       int k;
      
       // filter bad values
       if (numPacks < 1 || numPacks > 6)
          numPacks = 1;
       if (numJokersPerPack < 0 || numJokersPerPack > 4)
          numJokersPerPack = 0;
       if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
          numUnusedCardsPerPack = 0;
       if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
          numPlayers = 4;
       // one of many ways to assure at least one full deal to all players
       if  (numCardsPerHand < 1 ||
             numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
             / numPlayers )
          numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;
      
       // allocate
       this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
       this.hand = new Hand[numPlayers];
       for (k = 0; k < numPlayers; k++)
          this.hand[k] = new Hand();
       deck = new Deck(numPacks);
      
       // assign to members
       this.numPacks = numPacks;
       this.numJokersPerPack = numJokersPerPack;
       this.numUnusedCardsPerPack = numUnusedCardsPerPack;
       this.numPlayers = numPlayers;
       this.numCardsPerHand = numCardsPerHand;
       for (k = 0; k < numUnusedCardsPerPack; k++)
          this.unusedCardsPerPack[k] = unusedCardsPerPack[k];
      
       // prepare deck and shuffle
       newGame();
   }

    // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
    int k, j;

    // clear the hands
    for (k = 0; k < numPlayers; k++)
       hand[k].resetHand();

    // restock the deck
    deck.init(numPacks);

    // remove unused cards
    for (k = 0; k < numUnusedCardsPerPack; k++)
       deck.removeCard( unusedCardsPerPack[k] );

    // add jokers
    for (k = 0; k < numPacks; k++)
       for ( j = 0; j < numJokersPerPack; j++)
          deck.addCard( new Card('X', Card.Suit.values()[j]) );

    // shuffle the cards
    deck.shuffle();
   }

   public boolean deal()
   {
    // returns false if not enough cards, but deals what it can
    int k, j;
    boolean enoughCards;

    // clear all hands
    for (j = 0; j < numPlayers; j++)
       hand[j].resetHand();

    enoughCards = true;
    for (k = 0; k < numCardsPerHand && enoughCards ; k++)
    {
       for (j = 0; j < numPlayers; j++)
          if (deck.getNumCards() > 0)
             hand[j].takeCard( deck.dealCard() );
          else
          {
             enoughCards = false;
             break;
          }
    }

    return enoughCards;
   }

   public void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }
}
/*------------------------------------------------------
 * end of CardGameFramework
 *---------------------------------------------------- */