
import java.text.DecimalFormat;
import java.util.Scanner;

/*
*
* Matthew Bozelka
* Assignment 1 - String Manipulation
* 
* Purpose: The purpose of this assignment is to use and get
* comfortable with some of Java's built-in methods from the String class
* as well as use System.in, System.out, and DecimalFormater classes
*
* */

public class Assignment1
{

   private static final int MIN_HOURS = 12;
   private static final int MAX_HOURS = 20;

   public static void main(String[] args)
   {
      
      Scanner keyboard = new Scanner(System.in);
      DecimalFormat decimalFormat = new DecimalFormat("##.0");
      String firstName, lastName, fullName;
      double hoursWorked;
      
      // get users first name
      System.out.println("Please enter your first name "
            + "(and capitalize the first letter): ");
      firstName = keyboard.nextLine();
      
      // get users last name
      System.out.println("Please enter your last name "
            + "(and capitalize the first letter): ");
      lastName = keyboard.nextLine();
      
      fullName = firstName + " " + lastName;
      
      // print full name and its length
      System.out.println("Thank you " + fullName + ". Your name is "
            + fullName.length() + " characters long.");
      
      // print name in all upper case
      System.out.println("Your name in all uppercase " 
            + fullName.toUpperCase());
      
      // print name in all lower case
      System.out.println("Your name in all lowercase " 
            + fullName.toLowerCase());
      
      // print range of hours you should work
      System.out.println("You should be working between "
            + MIN_HOURS + " and " + MAX_HOURS + " hours each week.");
      
      // ask user for the number hours worked this week
      System.out.println("How many hours did you work this week "
            + "(answer to 3 decimal points. ex: 1.111): ");
      hoursWorked = keyboard.nextDouble();
      keyboard.nextLine(); // clear the new line in the input
      
      // print number of hours worked to 1 decimal point
      System.out.println("You worked " + decimalFormat.format(hoursWorked) 
            + " hours this week.");
      
      // close system in
      keyboard.close();
      
   }

}


/************** Program output **************

*Run One*

Please enter your first name (and capitalize the first letter): 
Matthew
Please enter your last name (and capitalize the first letter): 
Bozelka
Thank you Matthew Bozelka. Your name is 15 characters long.
Your name in all uppercase MATTHEW BOZELKA
Your name in all lowercase matthew bozelka
You should be working between 12 and 20 hours each week.
How many hours did you work this week (answer to 3 decimal points. ex: 1.111): 
12.453
You worked 12.5 hours this week.

*Run Two*

Please enter your first name (and capitalize the first letter): 
Peter
Please enter your last name (and capitalize the first letter): 
Jackson
Thank you Peter Jackson. Your name is 13 characters long.
Your name in all uppercase PETER JACKSON
Your name in all lowercase peter jackson
You should be working between 12 and 20 hours each week.
How many hours did you work this week (answer to 3 decimal points. ex: 1.111): 
9.132
You worked 9.1 hours this week.

 */