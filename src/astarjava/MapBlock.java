package astarjava;

import java.awt.Point;

/**
 *
 * @author Matt
 */
public class MapBlock {
    public int terrainType;
    public MapBlock prevPathBlock;
    public MapBlock nextDistBlock;
    public boolean isPath;
    public int pathType;
    public boolean visited;
    public float distance;
    public float possibleTrip;
    public Point position;
    
    public static final int TER_PLAINS = 1;
    public static final int TER_FOREST = 2;
    public static final int TER_MOUNTAINS = 3;
    public static final int TER_WATER = 4;
    
    public static final int PATH_START = 1;
    public static final int PATH_PATH = 2;
    public static final int PATH_END = 3;
    
    public MapBlock()
    {
        terrainType = TER_PLAINS;
        prevPathBlock = null;
        nextDistBlock = null;
        isPath = false;
        pathType = PATH_START;
        visited = false;
        distance = 0.0f;
        position = new Point(0,0);
    }
    
    public char mapCharacter()
    {
        char returnChar = '+';
        switch(terrainType)
        {
            case TER_PLAINS:
                returnChar = '.';
            break;
            case TER_FOREST:
                returnChar = '*';
            break;
            case TER_MOUNTAINS:
                returnChar = '^';
            break;
            case TER_WATER:
                returnChar = '~';
            break;
        }
        return returnChar;
    }
    
    public char pathCharacter()
    {
        char returnChar = '+';
        if(isPath)
        {
            switch(pathType)
            {
                case PATH_START:
                    returnChar = '@';
                break;
                case PATH_PATH:
                    returnChar = '#';
                break;
                case PATH_END:
                    returnChar = 'X';
                break;
            }
        }
        else
        {
            switch(terrainType)
            {
                case TER_PLAINS:
                    returnChar = '.';
                break;
                case TER_FOREST:
                    returnChar = '*';
                break;
                case TER_MOUNTAINS:
                    returnChar = '^';
                break;
                case TER_WATER:
                    returnChar = '~';
                break;
            }
        }
        return returnChar;
    }
}
