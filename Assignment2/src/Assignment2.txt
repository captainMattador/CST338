import java.util.*;
import java.lang.Math;

/*
*
* Matthew Bozelka
* Assignment 2 - Casino with Methods and a Class
* 
* Purpose: The purpose of this assignment is to get familiar with using
* methods and classes including static methods and static/static final
* properties
* 
* The program itself mimics a Casino slot machine game.
*
* */


/*
 * main class that houses the client logic and the main method
 * */
public class Assignment2
{

   private static final String CHERRIES = "cherries";
   private static final String BAR = "BAR";
   private static final String SPACE = "space";
   private static final String SEVEN = "7";
   
   private static Scanner keyboard = new Scanner(System.in);
   
   public static void main(String[] args)
   {
      
      TripleString pullString = null;
      int bet, multiplier, winnings, totalWinnings = 0, pullCount = 0;
      
      do
      {
         
         // exit if counter is greater than the max number of pulls allowed
         if(pullCount >= TripleString.MAX_PULLS)
            break;
         
         // get bet amount and end if it is 0
         bet = getBet();
         if(bet == 0)
            break;
         
         pullString = pull();
         multiplier = getPayMultiplier(pullString);
         winnings = bet * multiplier;
         TripleString.saveWinnings(winnings);
         display(pullString, winnings);
         pullCount++;
         totalWinnings += winnings;
         
      } while (bet != 0);
      
      // final output after user decides to end the game
      System.out.println("\nThanks for playing at the Casino!");
      System.out.println("Your individual winnings were:");
      System.out.println(TripleString.displayWinnings());
      System.out.println("Your total winnings were: $" + totalWinnings);    
      keyboard.close();
      System.exit(0);
            
   }
   
   /*
    * public static method that that asks the user how much they would
    * like to bet and returns that amount as an integer. Takes no parameters
    * */
   public static int getBet()
   {
      
      final int MIN_BET = 0;
      final int MAX_BET = 100;
      
      int bet = -1;
      
      while( !(bet >= MIN_BET && bet <= MAX_BET) )
      {
         
         System.out.println("How much would you like to bet (1 - 100) or "
               + "0 to quit?");
         bet = keyboard.nextInt();
         keyboard.nextLine();

      }
            
      return bet;
      
   }
   
   /*
    * public static method that acts as the pull on a slot machine.
    * returns a new TripleString object with three random
    * strings. Takes no parameters
    * */
   public static TripleString pull()
   {
      
      TripleString tripleString = new TripleString();
      
      tripleString.setString1(randString());
      tripleString.setString2(randString());
      tripleString.setString3(randString());
            
      return tripleString;
      
   }
   
   /*
    * static method that takes a TripleString object and an integer that
    * represents a winning amount. Then displays to the user the random
    * String and if they won and how much.
    * */
   public static void display (TripleString thePull, int winnings )
   {
      
      System.out.println("whirrrrrr .... and your pull is ...");
      System.out.println(thePull.toString());
      
      if(winnings == 0)
         System.out.println("sorry, you lose.");
      else
         System.out.println("congratulations, you win: " + winnings);
      
      System.out.println("");
      
   }
   
   /*
    * static method that determines how much to multiply the winnings by
    * based on the order and value of the random strings from a TripleString
    * object. Takes a TripleString object and returns an integer that is the
    * amount to multiply the winning by.
    * */
   private static int getPayMultiplier(TripleString thePull)
   {
      
      String string1, string2, string3;
      
      string1 = thePull.getString1();
      string2 = thePull.getString2();
      string3 = thePull.getString3();
      
      if( string1.equals(CHERRIES) && !string2.equals(CHERRIES) )
         return 5;
      else if( string1.equals(CHERRIES) && 
            string2.equals(CHERRIES) && !string3.equals(CHERRIES) )
         return 15;
      else if( string1.equals(CHERRIES) && 
            string2.equals(CHERRIES) && string3.equals(CHERRIES) )
         return 30;
      else if( string1.equals(BAR) && 
            string2.equals(BAR) && string3.equals(BAR) )
         return 50;
      else if( string1.equals(SEVEN) && 
            string2.equals(SEVEN) && string3.equals(SEVEN) )
         return 100;
      else
         return 0;
      
   }
   
   /*
    * static method that determines a random string based on probability.
    * It returns a string.
    * */
   private static String randString()
   {
      
      double randomDb = Math.random() * 100;
            
      if( randomDb > 50.0 && randomDb <= 75.0 )
         return CHERRIES;
      else if( randomDb > 75.0 && randomDb <= 87.5)
         return SPACE;
      else if( randomDb > 87.5)
         return SEVEN;
      else
         return BAR;
     
   }

}


/*
 * TripleString class
 */
class TripleString
{
   
   // public static finals to help
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   
   // private static properties
   private static int[] pullWinnings = new int[MAX_PULLS];
   private static int numPulls = 0;
   
   // private strings. main 3 strings that a TripleString object represents
   private String string1;
   private String string2;
   private String string3;
   
   /*
    * default constructor. Sets the private members to an empty string.
    * */
   public TripleString()
   {
      
      this.string1 = "";
      this.string2 = "";
      this.string3 = "";
      
   }
   
   /*
    * helper private function that tests if a string passed to it is valid.
    * String cannot be null or greater than MX_LEN. returns a boolean.
    * */
   private boolean validString( String str )
   {
      
      if(str == null || str.length() > MAX_LEN)
         return false;
      
      return true;
      
   }
   
   /*
    * public static method to save winnings. static because it saves the 
    * winnings of all TripleString objects generated by a players game.
    * Takes an integer that represents the winning amount and stores it
    * into pullWinnings array
    * */
   public static void saveWinnings(int winnings)
   {
      
      if(numPulls < MAX_PULLS)
      {
         pullWinnings[numPulls] = winnings;
         numPulls++;
      }
      
   }
   
   /*
    * public static method used to display all the winnings in the game.
    * returns a string that is a visual representation of all the winnings
    * stored in pullWinnings
    * */
   public static String displayWinnings()
   {
      
      String total = "";
      
      for(int i = 0; i < numPulls; i++)
      {
         total = (i == 0) ? total + pullWinnings[i] : total + 
               " " + pullWinnings[i];
      }
         
      return total;
      
   }
   
   /*
    * public method that returns all three private Strings as one string.
    * */
   public String toString() 
   {
      return string1 + " | " + string2 + " | " + string3;
   }
   
   /*getter functions*/
   
   /*
    * public method. returns private String1
    * */
   public String getString1()
   {
       return string1;
   }
   
   /*
    * public method. returns private String2
    * */
   public String getString2()
   {
       return string2;
   }
   
   /*
    * public method. returns private String3
    * */
   public String getString3()
   {
       return string3;
   }
   
   /*setter functions*/
   
   /*
    * public method. Takes a string and sets Private String1 to that value.
    * Returns a boolean to determine if successful.
    * */
   public boolean setString1(String str)
   {
      
      if(validString( str ))
      {
         string1 = str;
         return true;
      }
      
      return false;
      
   }
   
   /*
    * public method. Takes a string and sets Private String2 to that value.
    * Returns a boolean to determine if successful.
    * */
   public boolean setString2(String str)
   {
      
      if(validString( str ))
      {
         string2 = str;
         return true;
      }
      
      return false;
      
   }
   
   /*
    * public method. Takes a string and sets Private String3 to that value.
    * Returns a boolean to determine if successful.
    * */
   public boolean setString3(String str)
   {
      
      if(validString( str ))
      {
         string3 = str;
         return true;
      }
      
      return false;
      
   }

}



/*------------Test Run of spins 40 (MAX)------------*/

/*
How much would you like to bet (1 - 100) or 0 to quit?
25
whirrrrrr .... and your pull is ...
BAR | cherries | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
36
whirrrrrr .... and your pull is ...
BAR | cherries | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
78
whirrrrrr .... and your pull is ...
BAR | cherries | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
908
How much would you like to bet (1 - 100) or 0 to quit?
345
How much would you like to bet (1 - 100) or 0 to quit?
234
How much would you like to bet (1 - 100) or 0 to quit?
-1
How much would you like to bet (1 - 100) or 0 to quit?
4
whirrrrrr .... and your pull is ...
7 | space | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
5
whirrrrrr .... and your pull is ...
BAR | space | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
65
whirrrrrr .... and your pull is ...
cherries | space | BAR
congratulations, you win: 325

How much would you like to bet (1 - 100) or 0 to quit?
45
whirrrrrr .... and your pull is ...
7 | BAR | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
35
whirrrrrr .... and your pull is ...
BAR | cherries | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
65
whirrrrrr .... and your pull is ...
cherries | cherries | cherries
congratulations, you win: 1950

How much would you like to bet (1 - 100) or 0 to quit?
5
whirrrrrr .... and your pull is ...
BAR | cherries | cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
2
whirrrrrr .... and your pull is ...
BAR | cherries | cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
34
whirrrrrr .... and your pull is ...
cherries | BAR | BAR
congratulations, you win: 170

How much would you like to bet (1 - 100) or 0 to quit?
5
whirrrrrr .... and your pull is ...
BAR | 7 | cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
65
whirrrrrr .... and your pull is ...
BAR | BAR | BAR
congratulations, you win: 3250

How much would you like to bet (1 - 100) or 0 to quit?
67
whirrrrrr .... and your pull is ...
BAR | cherries | cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
87
whirrrrrr .... and your pull is ...
space | cherries | cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
45
whirrrrrr .... and your pull is ...
BAR | cherries | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
65
whirrrrrr .... and your pull is ...
cherries | BAR | BAR
congratulations, you win: 325

How much would you like to bet (1 - 100) or 0 to quit?
45
whirrrrrr .... and your pull is ...
cherries | 7 | BAR
congratulations, you win: 225

How much would you like to bet (1 - 100) or 0 to quit?
56
whirrrrrr .... and your pull is ...
cherries | cherries | 7
congratulations, you win: 840

How much would you like to bet (1 - 100) or 0 to quit?
76
whirrrrrr .... and your pull is ...
cherries | 7 | cherries
congratulations, you win: 380

How much would you like to bet (1 - 100) or 0 to quit?
45
whirrrrrr .... and your pull is ...
BAR | cherries | cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
65
whirrrrrr .... and your pull is ...
space | space | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
45
whirrrrrr .... and your pull is ...
BAR | BAR | BAR
congratulations, you win: 2250

How much would you like to bet (1 - 100) or 0 to quit?
34
whirrrrrr .... and your pull is ...
cherries | BAR | BAR
congratulations, you win: 170

How much would you like to bet (1 - 100) or 0 to quit?
54
whirrrrrr .... and your pull is ...
space | cherries | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
56
whirrrrrr .... and your pull is ...
cherries | 7 | BAR
congratulations, you win: 280

How much would you like to bet (1 - 100) or 0 to quit?
76
whirrrrrr .... and your pull is ...
cherries | cherries | BAR
congratulations, you win: 1140

How much would you like to bet (1 - 100) or 0 to quit?
87
whirrrrrr .... and your pull is ...
space | cherries | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
34
whirrrrrr .... and your pull is ...
BAR | space | cherries
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
23
whirrrrrr .... and your pull is ...
space | BAR | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
100
whirrrrrr .... and your pull is ...
space | 7 | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
1
whirrrrrr .... and your pull is ...
BAR | BAR | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
434
How much would you like to bet (1 - 100) or 0 to quit?
23
whirrrrrr .... and your pull is ...
BAR | BAR | BAR
congratulations, you win: 1150

How much would you like to bet (1 - 100) or 0 to quit?
43
whirrrrrr .... and your pull is ...
space | BAR | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
45
whirrrrrr .... and your pull is ...
cherries | BAR | BAR
congratulations, you win: 225

How much would you like to bet (1 - 100) or 0 to quit?
42
whirrrrrr .... and your pull is ...
space | cherries | 7
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
2
whirrrrrr .... and your pull is ...
cherries | space | BAR
congratulations, you win: 10

How much would you like to bet (1 - 100) or 0 to quit?
6
whirrrrrr .... and your pull is ...
BAR | space | BAR
sorry, you lose.

How much would you like to bet (1 - 100) or 0 to quit?
54
whirrrrrr .... and your pull is ...
BAR | cherries | BAR
sorry, you lose.


Thanks for playing at the Casino!
Your individual winnings were:
0 0 0 0 0 325 0 0 1950 0 0 170 0 3250 0 0 0 325 225 840 380 0 0 2250 170 0 280 1140 0 0 0 0 0 1150 0 225 0 10 0 0
Your total winnings were: $12690
*/

/*------------second run - ending early ------------*/

/*
How much would you like to bet (1 - 100) or 0 to quit?
0

Thanks for playing at the Casino!
Your individual winnings were:

Your total winnings were: $0
 */