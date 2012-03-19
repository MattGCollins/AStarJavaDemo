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
        
        again = getYN("Create another map?");
        
        if(again)
            getMapInfo();
    }
    
    public void set(boolean againIn, int heightIn, int widthIn, int forestChanceIn, int mountainChanceIn, int waterChanceIn)
    {
        again = againIn;
        height = heightIn;
        width = widthIn;
        forestChance = forestChanceIn;
        mountainChance = mountainChanceIn;
        waterChance = waterChanceIn;
    }
    
    private void getMapInfo()
    {
        height = getNumberBetween(3, 100, "Enter height (3-100): ");
        width = getNumberBetween(3, 100, "Enter width (3-100): ");
        forestChance = getNumberBetween(0, 100, "Enter forest spawn % (0-100): ");
        mountainChance = getNumberBetween(0, 100, "Enter mountain spawn % (0-100): ");
        waterChance = getNumberBetween(0, 100, "Enter water spawn % (0-100): ");
    }
    
    private boolean getYN(String prompt)
    {
        String tempString; boolean returnBoolean = false;
        do
        {
            System.out.print(prompt + " (y/n): ");
            tempString = getLine();
            tempString.toLowerCase();
        }while(!tempString.equals("y") && !tempString.equals("n"));
        if(tempString.equals("y")) {returnBoolean = true;}
        return returnBoolean;
    }
    
    private int getNumberBetween(int min, int max, String prompt)
    {
        int returnInt;
        do
        {
            System.out.print(prompt);
            returnInt = getInt();
        }while(returnInt < min || returnInt > max);
        return returnInt;
    }
    
    private int getInt()
    {
        String tempString = getLine();
        int returnInt;
        try {
            returnInt = Integer.parseInt(tempString);
        } catch (NumberFormatException ioe) {
            returnInt = -1;
        }
        return returnInt;
    }
    
    private String getLine()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String returnString = null;
        try {
            returnString = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read line!");
            System.exit(1);
        }
        return returnString;
    }
}
