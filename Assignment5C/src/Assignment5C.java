
/*
* Assignment 5C
*
* This is the full game of High Card
* Builds upon the previous assignments to build a functional card game
* in a GUI interface.
*
* */

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class Assignment5C
{
   static int MAX_CARDS_PER_HAND = 56;
   static int NUM_PLAYERS = 2;
   
   public static void main(String[] args)
   {
      
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      int numOfPlayers = 2;
      int cardsPerHand = 7;
      Card[] unusedCardsPerPack = null;
      
      CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            numOfPlayers, cardsPerHand);
      
      GameModel model = new GameModel(highCardGame, "Computer", "Player");
      GameView view = new GameView();
      
      GameControl game = new GameControl(model, view);
   }

}


/*------------------------------------------------------
 * class GameModel
 *---------------------------------------------------- */

class GameModel
{
   private CardGameFramework highCardGame;
   private int cpuPlayer, humanPlayer;
   private int cpuScore, humanScore;
   private String cpuName, humanName;
   private int firstPlayer;
   private int cardsLeft;
   private Card cpuPlayedCard, humanPlayedCard;
   
   public GameModel()
   {
      this.highCardGame = null;
      cpuName = "";
      cpuPlayer = 0;
      cpuScore = 0;
      humanName = "";
      humanPlayer = 0;
      humanScore = 0;
      cardsLeft = 0;
   }
   
   public GameModel(CardGameFramework highCardGame, String cpuName, 
         String humanName)
   {
      this.highCardGame = highCardGame;
      this.cpuName = cpuName;
      cpuPlayer = 0;
      cpuScore = 0;
      this.humanName = humanName;
      humanPlayer = 1;
      humanScore = 0;
      cardsLeft = highCardGame.geNumCardsPerHand();
   }
   
   public void resetGame()
   {
      highCardGame.newGame();
      cpuScore = 0;
      humanScore = 0;
      cardsLeft = highCardGame.geNumCardsPerHand();
   }
   
   public void dealCards()
   {
      highCardGame.deal();
      highCardGame.sortHands();
   }
   
   public int getCardsLeft()
   {
      return cardsLeft;
   }
   
   public boolean setCardsLeft()
   {
      if(cardsLeft - 1 < 0)
         return false;
      
      cardsLeft--;
      return true;
   }
   
   public Hand getCpuHand()
   {
      return highCardGame.getHand(cpuPlayer);
   }
   
   public int getCpuScore()
   {
      return cpuScore;
   }
   
   public boolean setCpuScore()
   {
      cpuScore++;
      return true;
   }
   
   public Hand getHumanHand()
   {
      return highCardGame.getHand(humanPlayer);
   }
   
   public int getHumanScore()
   {
      return humanScore;
   }
   
   public boolean setHumanScore()
   {
      humanScore++;
      return true;
   }
   
   public int getNumCardsPerHand()
   {
      return highCardGame.geNumCardsPerHand();
   }
   
   public boolean setFirstPlayer(int playerIndex)
   {
      
      firstPlayer = playerIndex;
      return true;
   }
   
   public int getFirstPlayer()
   {     
      return firstPlayer;
   }
   
   public String getCpuName()
   {
      return cpuName;
   }
   
   public String getHumanName()
   {
      return humanName;
   }
   
   public Card getHumanPlayedCard()
   {
      return humanPlayedCard;
   }
   
   public boolean setHumanPlayedCard(Card card)
   {
      if(card == null)
         return false;
      
      humanPlayedCard = new Card(card.getValue(), card.getSuit());
      return true;
   }
   
   public Card getCpuPlayedCard()
   {
      return cpuPlayedCard;
   }
   
   public boolean setCpuPlayedCard(Card card)
   {
      if(card == null)
         return false;
      
      cpuPlayedCard = new Card(card.getValue(), card.getSuit());
      return true;
   }
}

/*------------------------------------------------------
 * end of GameModel
 *---------------------------------------------------- */

/*------------------------------------------------------
 * class GameView
 *---------------------------------------------------- */
class GameView extends JFrame
{

   private static String[] pnlTitles = {"High Card", 
      "Computer Hand", "Player Hand", "Playing Area", "Time Played"};
   
   private JButton endGameBtn, playAgainBtn, nextRoundBtn;
   
   private JPanel pnlCpuHand, pnlHumanHand, pnlTimer, pnlOutput, 
   pnlPlayArea, pnlCardsPlayed, pnlCpuCardPlayed, pnlHumanCardPlayed;
   
   private ArrayList<JButton> playersCardsBtns = new ArrayList<JButton>();
   
   // default constructor
   // sets up the initial view, which is really
   // the table and it's main holders
   public GameView()
   {
      super();
      setTitle(pnlTitles[0]);
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      setLayout(new BorderLayout());
      
      endGameBtn = new JButton("End Game");
      playAgainBtn = new JButton("Play Again");
      
      // timer panel
      pnlTimer = new JPanel();
      
      // cpu played Card
      pnlCpuCardPlayed = new JPanel(new GridLayout(2, 1));
      
      // human played Card
      pnlHumanCardPlayed = new JPanel(new GridLayout(2, 1));
      
      // area for cards played
      pnlCardsPlayed = new JPanel(new GridLayout(1, 2));
      pnlCardsPlayed.add(pnlCpuCardPlayed);
      pnlCardsPlayed.add(pnlHumanCardPlayed);
      
      // computers cards panel
      pnlCpuHand = new JPanel();
      pnlCpuHand.setLayout(new GridLayout(1, 7));
      
      // players cards panel
      pnlHumanHand = new JPanel();
      pnlHumanHand.setLayout(new GridLayout(1, 7));
      
      // output area
      pnlOutput = new JPanel(new GridLayout(3, 1));
      
      // playing area
      pnlPlayArea = new JPanel(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      pnlPlayArea.add(pnlCpuHand, c);
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 1.0;
      pnlPlayArea.add(pnlCardsPlayed, c);
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 2;
      pnlPlayArea.add(pnlOutput, c);
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 3;
      pnlPlayArea.add(pnlHumanHand, c);
      
      
      // add all the major components to the screen
      this.add(pnlTimer, BorderLayout.NORTH);
      this.add(pnlPlayArea,BorderLayout.CENTER);
      this.setSize(800, 600);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
   }
   
   public void playRoundScreen(String message, Hand cpuHand, 
         Hand humanHand, int maxHandSize)
   {
      if(cpuHand == null || humanHand == null || 
            maxHandSize == 0)
         return;
      
      JLabel textOutput = new JLabel(message);
      textOutput.setHorizontalAlignment(JLabel.CENTER);
      pnlOutput.add(textOutput);
      showCpuHand(cpuHand, maxHandSize);
      showHumanHandAsButtons(humanHand, maxHandSize);
      
   }
   
   public void roundOutcomeScreen(String message, Hand cpuHand, Card cpuCard,
         String cpuName, Hand humanHand, Card humanCard, String humanName, 
         int maxHandSize)
   {
      if(cpuHand == null || humanHand == null || 
            maxHandSize == 0)
         return;
      
      JLabel textOutput = new JLabel(message);
      textOutput.setHorizontalAlignment(JLabel.CENTER);
      nextRoundBtn = new JButton("Next Round");
      pnlOutput.add(textOutput);
      pnlOutput.add(nextRoundBtn);
      showCpuPlayedCard(cpuCard, cpuName);
      showHumanPlayedCard(humanCard, humanName);
      showCpuHand(cpuHand, maxHandSize);
      showHumanHand(humanHand, maxHandSize);
      
   }
   
   public void gameOutcomeScreen(String message)
   {
      if(message == null)
         return;
      
      JLabel textOutput = new JLabel(message);
      textOutput.setHorizontalAlignment(JLabel.CENTER);
      pnlOutput.add(textOutput);
      pnlOutput.add(playAgainBtn);
      pnlOutput.add(endGameBtn);
      
   }
   
   /*
    * private helper method to repaint the UI
    * */
   public void clearUI()
   {
      pnlCpuHand.removeAll();
      pnlCpuCardPlayed.removeAll();
      pnlHumanHand.removeAll();
      pnlHumanCardPlayed.removeAll();
      pnlOutput.removeAll();
      this.getContentPane().repaint();
   }
   
   /*
    * private helper method to repaint the UI
    * */
   public void rePaintUI()
   {
      this.getContentPane().validate();
      this.getContentPane().repaint();
   }
   
   private void showCpuHand(Hand hand, int maxHandSize)
   {  
      
      for(int k = 0; k < maxHandSize; k++)
      {
         if(k > hand.getNumCards() - 1)
            pnlCpuHand.add(new JLabel(GUICard.getBlankCardIcon()));
         else
            pnlCpuHand.add(new JLabel(GUICard.getBackCardIcon()));
      }
      
   }
   
   private void showHumanHand(Hand hand, int maxHandSize)
   {
      
      for(int k = 0; k < maxHandSize; k++)
      {
         if(k > hand.getNumCards() - 1)
            pnlHumanHand.add(new JLabel(GUICard.getBlankCardIcon()));
         else
            pnlHumanHand.add(new JLabel(GUICard
                  .getIcon(hand.inspectCard(k))));
      }
   }
   
   private void showHumanHandAsButtons(Hand hand, int maxHandSize)
   {     
      playersCardsBtns.clear();
      for(int k = 0; k < maxHandSize; k++)
      {
         if(k > hand.getNumCards() - 1)
         {
            pnlHumanHand.add(new JLabel(GUICard.getBlankCardIcon()));  
         }
         else
         {
            JButton playCardBtn = new JButton(
                  GUICard.getIcon(hand.inspectCard(k)));
            playCardBtn.setBorder(BorderFactory.createEmptyBorder());
            playCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            playersCardsBtns.add(playCardBtn);
            
            pnlHumanHand.add(playersCardsBtns.get(k));
         }
      }
   }
   
   public void showMessageg(String title, String message)
   {  
      JOptionPane.showMessageDialog(
            this,
            message,
            title,
            JOptionPane.PLAIN_MESSAGE);
   }
   
   public int showOptionDialog(String title, String message, String[] options)
   {  
      int option = JOptionPane.showOptionDialog(
            this, 
            message,
            title, 
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
      
      return option;
   }
   
   public void showCpuPlayedCard(Card card, String name)
   {
      pnlCpuCardPlayed.add(new JLabel(GUICard.getIcon(card)));
      pnlCpuCardPlayed.add(new JLabel(name));
   }
   
   public void showHumanPlayedCard(Card card, String name)
   {
      pnlHumanCardPlayed.add(new JLabel(GUICard.getIcon(card)));
      pnlHumanCardPlayed.add(new JLabel(name));
   }
   
   public void playersCardActionListener(int index, ActionListener l)
   {
      playersCardsBtns.get(index).setCursor(new Cursor(Cursor.HAND_CURSOR));
      playersCardsBtns.get(index).addActionListener(l);
   }
   
   public void nextRoundActionListener(ActionListener l)
   {
      nextRoundBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
      nextRoundBtn.addActionListener(l);
   }
   
   public void endActionListener(ActionListener l)
   {
      endGameBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
      endGameBtn.addActionListener(l);
   }
   
   public void playAgainActionListener(ActionListener l)
   {
      playAgainBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
      playAgainBtn.addActionListener(l);
   }
}
/*------------------------------------------------------
 * end of GameView
 *---------------------------------------------------- */

/*------------------------------------------------------
 * Class GameControl
 *---------------------------------------------------- */
class GameControl
{
   GameModel model;
   GameView view;

   public GameControl()
   {
      this.model = null;
      this.view = null;
   }
   
   public GameControl(GameModel model, GameView view)
   {
      this.model = model;
      this.view = view;
      
      String[] options = {"Flip"};
      String message = "";
      int firstPlayer;
      
      this.view.showOptionDialog(
            "Get Ready to Play!",
            "Flip a Coin to Start the Game:", 
            options);
      
      firstPlayer = flipCoin();
      this.model.setFirstPlayer(firstPlayer);
      
      message = (firstPlayer == 0) ? this.model.getCpuName() : 
         this.model.getHumanName();
      message += " goes first!";
         
      this.view.showMessageg("First Player", message);
      
      this.model.dealCards();
      
      progressToPlayRound(firstPlayer);
   }
   
   public boolean setModel(GameModel model)
   {
      if(model == null)
         return false;
               
      this.model = model;
      return true;
      
   }
   
   public boolean setView(GameView view)
   {
      if(view == null)
         return false;
               
      this.view = view;
      return true;
      
   }
   
   private Card cpuPlayCard(Card playersCard)
   {
      
      if(playersCard == null)
         return null;
      
      Hand cpuHand = model.getCpuHand();
      int cardsInHand = cpuHand.getNumCards();
      int cpuCardToPlay = -1;
            
      for (int i = 0; i < cardsInHand; i++)
      {
         if(cpuHand.inspectCard(i).getValue() > playersCard.getValue())
            cpuCardToPlay = i;   
      }
      
      if(cpuCardToPlay == -1)
         cpuCardToPlay = 0;
      
      return cpuHand.playCard(cpuCardToPlay);
      
   }
   
   private Card cpuPlayCard()
   {
      
      Hand cpuHand = model.getCpuHand();   
      return cpuHand.playCard(); 
      
   }
   
   private Card humanPlayCard(int index)
   {
      
      Hand humanHand = model.getHumanHand();   
      return humanHand.playCard(index); 
      
   }
   
   private int flipCoin()
   { 
      return (int)(Math.random() * (2 - 0)); 
   }
   
   private void progressToPlayRound(int firstPlayer)
   {
      view.clearUI();
      
      Hand cpuHand = model.getCpuHand();
      Hand humanHand = model.getHumanHand();
      
      if(firstPlayer == 0)
      {
         Card card = cpuPlayCard();
         model.setCpuPlayedCard(card);
         view.showCpuPlayedCard(card, this.model.getCpuName());
      }
      
      view.playRoundScreen("Select your card.", cpuHand, 
            humanHand, this.model.getNumCardsPerHand());
      
      for(int i = 0; i < humanHand.getNumCards(); i++)
         view.playersCardActionListener(i, new PlayCarControlListener(i));
      
      view.rePaintUI();
   }
   
   private void progressToShowPlayedCards(String message)
   {
      view.clearUI();

      view.roundOutcomeScreen(
            message, 
            model.getCpuHand(),
            model.getCpuPlayedCard(),
            model.getCpuName(),
            model.getHumanHand(), 
            model.getHumanPlayedCard(),
            model.getHumanName(),
            model.getNumCardsPerHand());
      
      view.nextRoundActionListener(new NextControlListener());
      
      view.rePaintUI();
   }
   
   private void progressToEndScreen()
   {
      view.clearUI();
      
      String message = "";
      int cpuScore = model.getCpuScore();
      int humanScore = model.getHumanScore();
      
      if(cpuScore > humanScore)
         message = "Sorry, you lost, the computer won more rounds.";
      else if(cpuScore == humanScore)
         message = "You Tied!";
      else
         message = "Congratulations you won more rounds than the computer!";
      
      view.gameOutcomeScreen(message);
      view.endActionListener(new EndControlListener());
      view.playAgainActionListener(new PlayAgainControlListener());
      
      view.rePaintUI();
   }
   
   private String roundEvaluation()
   {
      String message = "";
      Card cpuPlayedCard = model.getCpuPlayedCard();
      Card humanPlayedCard = model.getHumanPlayedCard();
      
      if(cpuPlayedCard == null || humanPlayedCard == null)
         return null;
      
      int cpuPlayedCardValue = GUICard.valueAsInt(cpuPlayedCard.getValue());
      int playerCardValue = GUICard.valueAsInt(humanPlayedCard.getValue());
      
      if( cpuPlayedCardValue > playerCardValue)
      {
         message = "Sorry you lost this round.";
         model.setFirstPlayer(0);
         model.setCpuScore();
      }else if(cpuPlayedCardValue == playerCardValue)
      {
         message = "This round was a wash!";
      }else
      {
         message = "Congratulations! You won this round.";
         model.setFirstPlayer(1);
         model.setHumanScore();
      }
      
      model.setCardsLeft();
      return message;
   }
   
   private class PlayCarControlListener implements ActionListener
   {
      
      int index;
      
      public PlayCarControlListener(int index)
      {
         this.index = index;
      }
      
      @Override
      public void actionPerformed(ActionEvent e)
      {
         
         if(model.getFirstPlayer() == 1)
         {
            Card card = cpuPlayCard();
            model.setCpuPlayedCard(card);
         }
         
         Card card = humanPlayCard(index);
         model.setHumanPlayedCard(card);
         
         String message = roundEvaluation();
         progressToShowPlayedCards(message);
      }
      
   }
   
   private class NextControlListener implements ActionListener
   {
      
      @Override
      public void actionPerformed(ActionEvent e)
      {
         if(model.getCardsLeft() == 0)
            progressToEndScreen();
         else
            progressToPlayRound(model.getFirstPlayer());
      }
      
   }
   
   private class EndControlListener implements ActionListener
   {
      
      @Override
      public void actionPerformed(ActionEvent e)
      {
         System.exit(0);
      }
      
   }
   
   private class PlayAgainControlListener implements ActionListener
   {
      
      @Override
      public void actionPerformed(ActionEvent e)
      {
         int cpuFinalScore = model.getCpuScore();
         int humanFinalScore = model.getHumanScore();
         
         if(cpuFinalScore > humanFinalScore || 
               cpuFinalScore == humanFinalScore)
            model.setFirstPlayer(0);
         else
            model.setFirstPlayer(1);
         
         model.resetGame();
         model.dealCards();
         progressToPlayRound(model.getFirstPlayer());
      }
      
   }
   
}
/*------------------------------------------------------
 * end of GameControl
// *---------------------------------------------------- */


/*------------------------------------------------------
 * GUICard
 *---------------------------------------------------- */
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;
   private static Icon iconBlank;
   static boolean iconsLoaded = false;


   /*
    * loads the  Icons for each card value
    * */
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

   /*
    * getter to get an Icon for a requested card
    * */
   static public Icon getIcon(Card card)
   {

      if (!iconsLoaded)
         loadCardIcons();

      int cardValue = valueAsInt(card.getValue());
      int suitValue = suitAsInt(card.getSuit());

      return iconCards[cardValue][suitValue];
   }//end method getIcon

   /*
    * getter to get a back of card icon
    * */
   static public Icon getBackCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();

      return iconBack;
   }

   /*
    * getter to get a blank card holder
    * */
   static public Icon getBlankCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();

      return iconBlank;
   }

   /*
    * returns a cards value as an int
    * */
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

   /*
    * returns a cards suit as an int
    * */
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



/*
 * 
 * 
 * From here down is all the classes that represent objects
 * used by the model to create it's data. Card, Hand, Deck,
 * etc.
 * 
 * 
 * */


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


   /*
    * returns the index of a given card
    */
   private static int valueIndex(Card card)
   {

      for(int i = 0; i < valuRanks.length; i++)
      {
         if(card.getValue() == valuRanks[i])
            return i;
      }

      return -1;
   }

   /*
    * bubble sort for sorting the array
    */
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
    */
   public Card playCard(int cardIndex)
   {
      if ( numCards == 0 ) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];

      numCards--;
      for(int i = cardIndex; i < numCards; i++)
      {
         myCards[i] = myCards[i+1];
      }

      myCards[numCards] = null;

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

   /*
    * sorts the hand. Uses Cards bubble sort
    */
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


   /*
    * remove card from deck. Takes a card as a value and searches to
    * remove that card
    */
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

   /*
    * add card to deck.
    */
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

   /*
    * returns the number of cards
    */
   public int getNumCards()
   {
      return topCard;
   }

   /*
    * sorts the deck. Uses Cards bubble sort
    */
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

   public int geNumCardsPerHand() { return numCardsPerHand; }
   
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

   public Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 || 
          cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);      
      }
   
      // return the card played
      return hand[playerIndex].playCard(cardIndex);
   
   }
   
   
   public boolean takeCard(int playerIndex, int cardIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
          cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         return false;      
      }
           
      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;
         
      return hand[playerIndex].takeCard(deck.dealCard());
   }
}
/*------------------------------------------------------
 * end of CardGameFramework
 *---------------------------------------------------- */

