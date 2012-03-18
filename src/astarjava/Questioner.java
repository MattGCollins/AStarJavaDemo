/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Matt
 */
public class Questioner {
    boolean again;
    int height;
    int width;
    int forestChance;
    int mountainChance;
    int waterChance;
    
    public Questioner()
    {
        again = true;
        height = 10;
        width = 10;
        forestChance = 10;
        mountainChance = 30;
        waterChance = 20;
    }
    
    public void questionUser()
    {
        String tempString;
        do
        {
            System.out.print("Create another map? (y/n) ");
            tempString = getLine();
            tempString.toLowerCase();
        }
        while(!tempString.equals("y") && !tempString.equals("n"));
        
        again = false;
        if(tempString.equals("y"))
            again = true;
        
        if(again)
        {
            do
            {
                System.out.print("Enter height (3-100): ");
                tempString = getLine();
                try {
                    height = Integer.parseInt(tempString);
                } catch (NumberFormatException ioe) {
                    height = -1;
                }
            }while(height < 3 || height > 100);
            
            do
            {
                System.out.print("Enter width (3-100): ");
                tempString = getLine();
                try {
                    width = Integer.parseInt(tempString);
                } catch (NumberFormatException ioe) {
                    width = -1;
                }
            }while(width < 3 || width > 100);
            
            do
            {
                System.out.print("Enter forest spawn % (0-100): ");
                tempString = getLine();
                try {
                    forestChance = Integer.parseInt(tempString);
                } catch (NumberFormatException ioe) {
                    forestChance = -1;
                }
            }while(forestChance < 0 || forestChance > 100);
            
            do
            {
                System.out.print("Enter mountain spawn % (0-100): ");
                tempString = getLine();
                try {
                    mountainChance = Integer.parseInt(tempString);
                } catch (NumberFormatException ioe) {
                    mountainChance = -1;
                }
            }while(mountainChance < 0 || mountainChance > 100);
            
            do
            {
                System.out.print("Enter water spawn % (0-100): ");
                tempString = getLine();
                try {
                    waterChance = Integer.parseInt(tempString);
                } catch (NumberFormatException ioe) {
                    waterChance = -1;
                }
            }while(waterChance < 0 || waterChance > 100);
        }
    }
    
    private String getLine()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String returnString = null;
        

        //  read the username from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            returnString = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read line!");
            System.exit(1);
        }
        return returnString;
    }
}
