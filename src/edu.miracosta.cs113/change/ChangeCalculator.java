package edu.miracosta.cs113.change;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

/**
 * ChangeCalculator : Class containing the recursive method calculateChange, which determines and prints all
 * possible coin combinations representing a given monetary value in cents.
 *
 * Problem derived from Koffman & Wolfgang's Data Structures: Abstraction and Design Using Java (2nd ed.):
 * Ch. 5, Programming Project #7, pg. 291.
 *
 * NOTE: An additional method, printCombinationsToFile(int), has been added for the equivalent tester file to
 * verify that all given coin combinations are unique.
 */
public class ChangeCalculator {

    private static TreeSet<String> combinations = new TreeSet<>() ; //Set doesn't allow duplicates

    /**
     * Wrapper method for determining all possible unique combinations of quarters, dimes, nickels, and pennies that
     * equal the given monetary value in cents.
     *
     * In addition to returning the number of unique combinations, this method will print out each combination to the
     * console. The format of naming each combination is up to the user, as long as they adhere to the expectation
     * that the coins are listed in descending order of their value (quarters, dimes, nickels, then pennies). Examples
     * include "1Q 2D 3N 4P", and "[1, 2, 3, 4]".
     *
     * @param cents a monetary value in cents
     * @return the total number of unique combinations of coins of which the given value is comprised
     */
    public static int calculateChange(int cents) {
        combinations.clear();  //fresh start
        return makeChange(0, 0, 0, cents);   //return the recursive function makeChange
    }


    /**
     * Wrapper method for determining all possible unique combinations of quarters, dimes, nickels, and pennies that
     * equal the given monetary value in cents.
     *
     * In addition to returning the number of unique combinations, this method will print out each combination to the
     * console. The format of naming each combination is up to the user, as long as they adhere to the expectation
     * that the coins are listed in descending order of their value (quarters, dimes, nickels, then pennies). Examples
     * include "1Q 2D 3N 4P", and "[1, 2, 3, 4]".
     *
     * @param quarters The number of quarters represented in this combination
     * @param dimes The number of dimes represented in this combination
     * @param nickels The number of nickels represented in this combination
     * @param pennys The number of pennys represented in this combination
     * @return (1 if the combination isn't already present in the combinations set) + call makeChange
     *                                                          with one more quarter but 25 less pennys +
     *                                                          with one more dime but 10 less pennys +
     *                                                          with one more nickel but 5 less pennys
     *
     */
    public static int makeChange(int quarters, int dimes, int nickels, int pennys)
    {
        //base case (this is the end of the line for this combination thread)
        if (pennys < 0)
        {
            return 0;
        }
        //attempt to add this combination to the treeset (doesn't allow duplicates)
        String aCombination = "[" + quarters + ", " + dimes + ", " + nickels + ", " + pennys + "]" ;

        if(combinations.add(aCombination)) //the add function returns false if the object is already in the set
        {
            //recursively call this same function with an incriment to each parameter (if valid)
                    //return 1 + if this combinations wasn't in the set already
            return (1 + makeChange(quarters + 1, dimes, nickels, pennys - 25)) +
                    makeChange(quarters, dimes + 1, nickels, pennys - 10) +
                    makeChange(quarters, dimes, nickels + 1, pennys - 5)
                    ;
        }
        else        //otherwise return just the recursive calls (designating this combination was not unique)
            return (makeChange(quarters + 1, dimes, nickels, pennys - 25)) +
                    makeChange(quarters, dimes + 1, nickels, pennys - 10) +
                    makeChange(quarters, dimes, nickels + 1, pennys - 5) ;
    }

    /**
     * Calls upon calculateChange(int) to calculate and print all possible unique combinations of quarters, dimes,
     * nickels, and pennies that equal the given value in cents.
     *
     * Similar to calculateChange's function in printing each combination to the console, this method will also
     * produce a text file named "CoinCombinations.txt", writing each combination to separate lines.
     *
     * @param cents a monetary value in cents
     */
    public static void printCombinationsToFile(int cents) {
        calculateChange(cents) ;    //calls calculateChange with this functnions parameter cents to update the combinations set
        try
        {
            // Statement opens/or creates a file at the destination pathname parameter
            File file = new File("C:\\Users\\beave\\Documents\\GitHub\\RecursionTrees\\src\\edu.miracosta.cs113\\change\\CoinCombinations.txt") ;
            //PrintWriter facilitates streaming to a file in textform
            PrintWriter pw = new PrintWriter(new FileWriter(file)) ;
            for (String combination : combinations)     //write to the file every string in the combinations set
            {
                pw.println(combination);
            }
            pw.close(); //close the resource
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


} // End of class ChangeCalculator