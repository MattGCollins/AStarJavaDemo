package astarjava;

import java.util.Random;

/**
 *
 * @author Matt
 */
public class Map {
    public int width;
    public int height;
    public MapBlock[][] blocks;
    
    public Map(int widthIn, int heightIn)
    {
        width = widthIn;
        height = heightIn;
        blocks = new MapBlock[width][height];
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                blocks[x][y] = new MapBlock();
            }
        }
    }
    
    public void generateMap(int forestChance, int mountainChance, int waterChance)
    {
        mountainChance += forestChance;
        waterChance += mountainChance;
        
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                setBlock(x, y, forestChance, mountainChance, waterChance);
            }
        }
        blocks[0][0].terrainType = MapBlock.TER_PLAINS;
        blocks[width - 1][height - 1].terrainType = MapBlock.TER_PLAINS;
    }
    
    public void setBlock(int x, int y, int forestChance, int mountainChance, int waterChance)
    {
        Random numGen = new Random();
        int randomNum = numGen.nextInt(100);
        if(randomNum < forestChance)
            blocks[x][y].set(x, y, false, false, MapBlock.TER_FOREST);
        else if(randomNum < mountainChance)
            blocks[x][y].set(x, y, false, false, MapBlock.TER_MOUNTAINS);
        else if(randomNum < waterChance)
            blocks[x][y].set(x, y, false, false, MapBlock.TER_WATER);
        else
            blocks[x][y].set(x, y, false, false, MapBlock.TER_PLAINS);
    }
    
    public void renderMap()
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                System.out.print(blocks[x][y].mapCharacter());
            }
            System.out.println();
        }
    }
    
    public void renderMapPaths()
    {
        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                System.out.print(blocks[x][y].pathCharacter());
            }
            System.out.println();
        }
    }
}
