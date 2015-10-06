import java.util.*;

/*
*
* Matthew Bozelka
* Assignment 3 - Write a Java program: Decks of Cards
* 
* Purpose: The purpose of this assignment is to create a Hand, Card, and 
* Deck class that will interact with each other to create a program that
* is a card game.
*
* */

public class Assignment3
{  
   public static void main(String[] args)
   {
 
      /*----------------------------------------------------------------- 
       * Phase 1: The Card Class
       *-----------------------------------------------------------------*/
      System.out.println("----------Card Class Tests----------\n");
      
      Card cardClassCard1 = new Card('3', Card.Suit.CLUBS);
      Card cardClassCard2 = new Card('T', Card.Suit.CLUBS);
      Card cardClassCard3 = new Card('e', Card.Suit.HEARTS);
      
      System.out.println(cardClassCard1.toString());
      System.out.println(cardClassCard2.toString());
      System.out.println(cardClassCard3.toString());
      
      cardClassCard1.set('e', Card.Suit.CLUBS);
      cardClassCard3.set('A', Card.Suit.DIAMONDS);
      
      System.out.println("");
      System.out.println(cardClassCard1.toString());
      System.out.println(cardClassCard2.toString());
      System.out.println(cardClassCard3.toString());
      
      /*----------------------------------------------------------------- 
       * end of Phase 1
       *-----------------------------------------------------------------*/
      
      
      /*----------------------------------------------------------------- 
       * Phase 2: The Hand Class
       *-----------------------------------------------------------------*/
      System.out.print("\n\n----------Hand Class Tests----------\n");
      
      Card card1 = new Card('3', Card.Suit.CLUBS);
      Card card2 = new Card('T', Card.Suit.CLUBS);
      Card card3 = new Card('9', Card.Suit.HEARTS);
      Hand hand = new Hand();
      int counter = 0;
      
      // add max number of cards to hand
      for (int i = 0; i < Hand.MAX_CARDS; i++)
      {
         ++counter;
         switch(counter)
         {
            case 2:
               hand.takeCard(card2);
               break;
            case 3:
               hand.takeCard(card3);
               counter = 0;
               break;
            default:
               hand.takeCard(card1);
               break;
               
         }
      }
      
      // print out the entire hand
      System.out.println("Hand full");
      System.out.println("After deal:");
      System.out.println("Hand = " + hand.toString());
      System.out.println("");
      
      //examine card and show it
      System.out.println("Testing inspectCard():");
      Card testCard = hand.inspectCard(2);
      Card testCard2 = hand.inspectCard(101);
      System.out.println(testCard.toString());
      System.out.println(testCard2.toString());
      System.out.println("");
      
      // play every card in hand
      System.out.println("Play cards in Hand:");
      for(int i = hand.getNumCards(); i > 0; i--)
      {
         Card playedCard = hand.playCard();
         System.out.println(playedCard.toString());
      }
      
      // print out empty hand
      System.out.println("");
      System.out.println("After playing all cards:");
      System.out.println("Hand = " + hand.toString());
      
      /*----------------------------------------------------------------- 
       * end of Phase 2
       *-----------------------------------------------------------------*/
      
      
      /*----------------------------------------------------------------- 
       * Phase 3: The Deck Class
       *-----------------------------------------------------------------*/
      
      System.out.print("\n\n----------Deck Class Tests----------\n");
      
      Deck deck = new Deck(2);
      int testCardCount = deck.getTopCard();
      
      System.out.println(testCardCount + " Not Shuffled:");
      for(int i = 0; i < testCardCount; i++)
      {
         Card card = deck.dealCard();
         System.out.println(card.toString());
      }
      
      deck.init(2);
      deck.shuffle();
      testCardCount = deck.getTopCard();
      System.out.println("");
      System.out.println(testCardCount + " Shuffled:");
      for(int i = 0; i < testCardCount; i++)
      {
         Card card = deck.dealCard();
         System.out.println(card.toString());
      }
      
      deck.init(1);
      testCardCount = deck.getTopCard();
      System.out.println("");
      System.out.println(testCardCount + " Not Shuffled:");
      for(int i = 0; i < testCardCount; i++)
      {
         Card card = deck.dealCard();
         System.out.println(card.toString());
      }
      
      deck.init(1);
      deck.shuffle();
      testCardCount = deck.getTopCard();
      System.out.println("");
      System.out.println(testCardCount + " Shuffled:");
      for(int i = 0; i < testCardCount; i++)
      {
         Card card = deck.dealCard();
         System.out.println(card.toString());
      }
      /*----------------------------------------------------------------- 
       * end of Phase 3
       *-----------------------------------------------------------------*/
      

      /*----------------------------------------------------------------- 
       * Phase 4: The Deck and Hand Classes
       *-----------------------------------------------------------------*/
      
      System.out.print("\n\n----------Phase 4 Integration----------\n");
      Scanner keyboard = new Scanner(System.in);
      int numPlayers, cardCount;
      Deck singlePakcDeck;
      Hand players[];
      
      // ask for number of players
      do
      {
         
         System.out.println("Please Select number of hands.  (1-10)");
         numPlayers = keyboard.nextInt();
         keyboard.nextLine();
         
      }while(numPlayers <= 0 || numPlayers > 10);
      
      // close the resource
      keyboard.close();
     
      // init deck and number of hands
      singlePakcDeck = new Deck(1);
      players = new Hand[numPlayers];
      
      // initiate all players hands
      for (int i = 0; i < players.length; i++)
         players[i] = new Hand();
      
      //deal cards to players
      cardCount = singlePakcDeck.getTopCard();
      for (int i = 0; i < cardCount; i++)
      {
         Card card = singlePakcDeck.dealCard();
         if(!card.getErrorFlag())
            players[i % numPlayers].takeCard( card );
      }
      
      
      // display all card in each players hand
      for (int i = 0; i < numPlayers; i++)
      {
         System.out.print("Player " + (i + 1) + "'s hand: ");
         System.out.print(players[i].toString() + "\n\n");
      }

      
      System.out.print("\n\nHere are our hands, from SHUFFLED deck:\n");
      
      //reset the deck and shuffle the deck
      singlePakcDeck.init(1);
      singlePakcDeck.shuffle();
      
      //reset the payer hands
      for (int i = 0; i < players.length; i++)
         players[i].resetHand();
      
      //deal cards to players
      cardCount = singlePakcDeck.getTopCard();
      for (int i = 0; i < cardCount; i++)
      {
         Card card = singlePakcDeck.dealCard();
         if(!card.getErrorFlag())
            players[i % numPlayers].takeCard( card );
      }
     
      // display shuffled hands
      for (int i = 0; i < numPlayers; i++)
      {
         System.out.print("Player " + (i + 1) + "'s hand: ");
         System.out.print(players[i].toString() + "\n\n");
      }
      
      /*----------------------------------------------------------------- 
       * end of Phase 4
       *-----------------------------------------------------------------*/
      
   }
}//end class assignment3


/*------------------------------------------------------
 * Card Class
 *---------------------------------------------------- */
class Card
{
   // mappings for valid cards
   public enum Suit{CLUBS, DIAMONDS, HEARTS, SPADES};
   public static final char[] cardValue = {'A', '2', '3', '4', '5', '6', '7', 
      '8', '9', 'T', 'J', 'Q', 'K'};
   
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
   }
}


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
   
}


/*------------------------------------------------------
 * Deck Class
 *---------------------------------------------------- */
class Deck
{
   
   //setting maximum number of packs to six
   public static final int MAX_CARDS = 6*52;
   
   private static final int NUMBER_OF_CARDS = 52;
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
         if(count == 52)
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
         if(i < 13)
            suit = Card.Suit.SPADES;
         else if(i >= 13 && i < 26)
            suit = Card.Suit.CLUBS;
         else if(i >= 26 && i < 39)
            suit = Card.Suit.HEARTS;
         else
            suit = Card.Suit.DIAMONDS;

         masterPack[i] = new Card(Card.cardValue[ i % 13 ], suit);
      }
   }
   
}//end class Deck()


/*----------------------- sample run -----------------------------------

----------Card Class Tests----------

3 of CLUBS
T of CLUBS
[invalid]

[invalid]
T of CLUBS
A of DIAMONDS


----------Hand Class Tests----------
Hand full
After deal:
Hand = (3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS, T of CLUBS, 9 of HEARTS, 3 of CLUBS)

Testing inspectCard():
9 of HEARTS
[invalid]

Play cards in Hand:
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS
9 of HEARTS
T of CLUBS
3 of CLUBS

After playing all cards:
Hand = ()


----------Deck Class Tests----------
104 Not Shuffled:
K of DIAMONDS
Q of DIAMONDS
J of DIAMONDS
T of DIAMONDS
9 of DIAMONDS
8 of DIAMONDS
7 of DIAMONDS
6 of DIAMONDS
5 of DIAMONDS
4 of DIAMONDS
3 of DIAMONDS
2 of DIAMONDS
A of DIAMONDS
K of HEARTS
Q of HEARTS
J of HEARTS
T of HEARTS
9 of HEARTS
8 of HEARTS
7 of HEARTS
6 of HEARTS
5 of HEARTS
4 of HEARTS
3 of HEARTS
2 of HEARTS
A of HEARTS
K of CLUBS
Q of CLUBS
J of CLUBS
T of CLUBS
9 of CLUBS
8 of CLUBS
7 of CLUBS
6 of CLUBS
5 of CLUBS
4 of CLUBS
3 of CLUBS
2 of CLUBS
A of CLUBS
K of SPADES
Q of SPADES
J of SPADES
T of SPADES
9 of SPADES
8 of SPADES
7 of SPADES
6 of SPADES
5 of SPADES
4 of SPADES
3 of SPADES
2 of SPADES
A of SPADES
K of DIAMONDS
Q of DIAMONDS
J of DIAMONDS
T of DIAMONDS
9 of DIAMONDS
8 of DIAMONDS
7 of DIAMONDS
6 of DIAMONDS
5 of DIAMONDS
4 of DIAMONDS
3 of DIAMONDS
2 of DIAMONDS
A of DIAMONDS
K of HEARTS
Q of HEARTS
J of HEARTS
T of HEARTS
9 of HEARTS
8 of HEARTS
7 of HEARTS
6 of HEARTS
5 of HEARTS
4 of HEARTS
3 of HEARTS
2 of HEARTS
A of HEARTS
K of CLUBS
Q of CLUBS
J of CLUBS
T of CLUBS
9 of CLUBS
8 of CLUBS
7 of CLUBS
6 of CLUBS
5 of CLUBS
4 of CLUBS
3 of CLUBS
2 of CLUBS
A of CLUBS
K of SPADES
Q of SPADES
J of SPADES
T of SPADES
9 of SPADES
8 of SPADES
7 of SPADES
6 of SPADES
5 of SPADES
4 of SPADES
3 of SPADES
2 of SPADES
A of SPADES

104 Shuffled:
Q of HEARTS
7 of HEARTS
4 of CLUBS
2 of DIAMONDS
A of DIAMONDS
5 of HEARTS
2 of HEARTS
8 of SPADES
9 of DIAMONDS
A of SPADES
8 of SPADES
2 of CLUBS
4 of DIAMONDS
A of SPADES
7 of CLUBS
J of CLUBS
Q of CLUBS
9 of SPADES
3 of CLUBS
9 of CLUBS
2 of CLUBS
J of HEARTS
7 of SPADES
K of HEARTS
8 of HEARTS
A of HEARTS
5 of SPADES
3 of HEARTS
Q of SPADES
3 of CLUBS
4 of HEARTS
J of SPADES
4 of DIAMONDS
T of SPADES
K of CLUBS
4 of SPADES
8 of DIAMONDS
7 of HEARTS
Q of SPADES
A of HEARTS
9 of HEARTS
2 of SPADES
2 of SPADES
8 of CLUBS
K of CLUBS
7 of DIAMONDS
6 of HEARTS
K of DIAMONDS
6 of CLUBS
9 of CLUBS
Q of CLUBS
5 of HEARTS
A of CLUBS
9 of DIAMONDS
6 of SPADES
6 of HEARTS
T of CLUBS
Q of DIAMONDS
8 of CLUBS
7 of SPADES
T of SPADES
7 of DIAMONDS
J of CLUBS
T of HEARTS
7 of CLUBS
A of DIAMONDS
5 of DIAMONDS
5 of DIAMONDS
9 of HEARTS
6 of CLUBS
T of HEARTS
2 of DIAMONDS
K of DIAMONDS
6 of SPADES
J of DIAMONDS
3 of DIAMONDS
3 of SPADES
4 of CLUBS
5 of SPADES
J of SPADES
Q of HEARTS
2 of HEARTS
T of DIAMONDS
8 of HEARTS
J of DIAMONDS
4 of SPADES
K of SPADES
6 of DIAMONDS
9 of SPADES
6 of DIAMONDS
K of SPADES
5 of CLUBS
8 of DIAMONDS
3 of SPADES
Q of DIAMONDS
T of DIAMONDS
5 of CLUBS
J of HEARTS
T of CLUBS
K of HEARTS
3 of HEARTS
A of CLUBS
4 of HEARTS
3 of DIAMONDS

52 Not Shuffled:
K of DIAMONDS
Q of DIAMONDS
J of DIAMONDS
T of DIAMONDS
9 of DIAMONDS
8 of DIAMONDS
7 of DIAMONDS
6 of DIAMONDS
5 of DIAMONDS
4 of DIAMONDS
3 of DIAMONDS
2 of DIAMONDS
A of DIAMONDS
K of HEARTS
Q of HEARTS
J of HEARTS
T of HEARTS
9 of HEARTS
8 of HEARTS
7 of HEARTS
6 of HEARTS
5 of HEARTS
4 of HEARTS
3 of HEARTS
2 of HEARTS
A of HEARTS
K of CLUBS
Q of CLUBS
J of CLUBS
T of CLUBS
9 of CLUBS
8 of CLUBS
7 of CLUBS
6 of CLUBS
5 of CLUBS
4 of CLUBS
3 of CLUBS
2 of CLUBS
A of CLUBS
K of SPADES
Q of SPADES
J of SPADES
T of SPADES
9 of SPADES
8 of SPADES
7 of SPADES
6 of SPADES
5 of SPADES
4 of SPADES
3 of SPADES
2 of SPADES
A of SPADES

52 Shuffled:
6 of DIAMONDS
A of HEARTS
T of SPADES
6 of SPADES
3 of HEARTS
7 of CLUBS
Q of HEARTS
4 of DIAMONDS
2 of HEARTS
Q of CLUBS
A of DIAMONDS
J of CLUBS
K of HEARTS
3 of CLUBS
2 of SPADES
5 of HEARTS
J of SPADES
5 of CLUBS
A of SPADES
9 of SPADES
K of SPADES
7 of DIAMONDS
4 of CLUBS
Q of DIAMONDS
6 of HEARTS
9 of DIAMONDS
5 of SPADES
K of CLUBS
8 of HEARTS
T of DIAMONDS
T of CLUBS
2 of DIAMONDS
7 of SPADES
T of HEARTS
9 of CLUBS
4 of SPADES
7 of HEARTS
Q of SPADES
6 of CLUBS
3 of SPADES
4 of HEARTS
K of DIAMONDS
8 of DIAMONDS
A of CLUBS
2 of CLUBS
8 of SPADES
J of DIAMONDS
5 of DIAMONDS
9 of HEARTS
8 of CLUBS
3 of DIAMONDS
J of HEARTS


----------Phase 4 Integration----------
Please Select number of hands.  (1-10)
6
Player 1's hand: (K of DIAMONDS, 7 of DIAMONDS, A of DIAMONDS, 8 of HEARTS, 2 of HEARTS, 9 of CLUBS, 3 of CLUBS, T of SPADES, 4 of SPADES)

Player 2's hand: (Q of DIAMONDS, 6 of DIAMONDS, K of HEARTS, 7 of HEARTS, A of HEARTS, 8 of CLUBS, 2 of CLUBS, 9 of SPADES, 3 of SPADES)

Player 3's hand: (J of DIAMONDS, 5 of DIAMONDS, Q of HEARTS, 6 of HEARTS, K of CLUBS, 7 of CLUBS, A of CLUBS, 8 of SPADES, 2 of SPADES)

Player 4's hand: (T of DIAMONDS, 4 of DIAMONDS, J of HEARTS, 5 of HEARTS, Q of CLUBS, 6 of CLUBS, K of SPADES, 7 of SPADES, A of SPADES)

Player 5's hand: (9 of DIAMONDS, 3 of DIAMONDS, T of HEARTS, 4 of HEARTS, J of CLUBS, 5 of CLUBS, Q of SPADES, 6 of SPADES)

Player 6's hand: (8 of DIAMONDS, 2 of DIAMONDS, 9 of HEARTS, 3 of HEARTS, T of CLUBS, 4 of CLUBS, J of SPADES, 5 of SPADES)



Here are our hands, from SHUFFLED deck:
Player 1's hand: (T of DIAMONDS, 8 of DIAMONDS, 4 of CLUBS, Q of DIAMONDS, Q of SPADES, A of HEARTS, 9 of DIAMONDS, 6 of SPADES, J of CLUBS)

Player 2's hand: (T of HEARTS, 7 of HEARTS, Q of CLUBS, 6 of DIAMONDS, K of HEARTS, J of DIAMONDS, 3 of HEARTS, 7 of DIAMONDS, 5 of SPADES)

Player 3's hand: (9 of CLUBS, 6 of HEARTS, T of CLUBS, 3 of CLUBS, J of HEARTS, 2 of CLUBS, K of SPADES, J of SPADES, 8 of CLUBS)

Player 4's hand: (8 of HEARTS, Q of HEARTS, 6 of CLUBS, 2 of HEARTS, K of DIAMONDS, 3 of SPADES, K of CLUBS, T of SPADES, A of CLUBS)

Player 5's hand: (4 of DIAMONDS, 8 of SPADES, 4 of HEARTS, 9 of SPADES, 5 of CLUBS, 5 of DIAMONDS, 7 of CLUBS, 9 of HEARTS)

Player 6's hand: (A of DIAMONDS, 4 of SPADES, 2 of DIAMONDS, 3 of DIAMONDS, 5 of HEARTS, A of SPADES, 2 of SPADES, 7 of SPADES)

----------------------- end sample run -----------------------------------*/
