package astarjava;

import java.awt.Point;

/**
 *
 * @author Matt
 */
public class Pathfinder {
    
    MapBlock firstBlock;
    MapBlock lastBlock;
    MapBlock openBlock;
    
    float diagDist = (float)Math.sqrt(2.0f);
    
    public Pathfinder()
    {
        firstBlock = null;
        lastBlock = null;
        openBlock = null;
    }
    
    public void findPath(Map map)
    {
        firstBlock = map.blocks[0][0];
        lastBlock = map.blocks[map.width - 1][map.height - 1];
        setFirstLastBlocks();
        MapBlock curBlock;
        do
        {
            curBlock = openBlock;
            spreadPath(curBlock, map);
        }while(openBlock != null && (lastBlock.possibleTrip > openBlock.possibleTrip));
        tracePath();
    }
    
    public void spreadPath(MapBlock curBlock, Map map)
    {
        openBlock = openBlock.nextDistBlock;
        MapBlock testBlock = openBlock;
        while(testBlock != null)
        {
            testBlock = testBlock.nextDistBlock;
        }
        if(curBlock.position.x == 0)
        {
            if(curBlock.position.y > 0)
            {
                visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y - 1]);
                visitBlock(curBlock, true, map.blocks[curBlock.position.x + 1][curBlock.position.y - 1]);
            }

            visitBlock(curBlock, false, map.blocks[curBlock.position.x + 1][curBlock.position.y]);
                
            if(curBlock.position.y < map.height - 1)
            {
                visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y + 1]);
                visitBlock(curBlock, true, map.blocks[curBlock.position.x + 1][curBlock.position.y + 1]);
            }
        }
        else if(curBlock.position.x == map.width - 1)
        {
            if(curBlock.position.y > 0)
            {
                visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y - 1]);
                visitBlock(curBlock, true, map.blocks[curBlock.position.x - 1][curBlock.position.y - 1]);
            }

            visitBlock(curBlock, false, map.blocks[curBlock.position.x - 1][curBlock.position.y]);
                
            if(curBlock.position.y < map.height - 1)
            {
                visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y + 1]);
                visitBlock(curBlock, true, map.blocks[curBlock.position.x - 1][curBlock.position.y + 1]);
            }
        }
        else
        {
            if(curBlock.position.y > 0)
            {
                visitBlock(curBlock, true, map.blocks[curBlock.position.x - 1][curBlock.position.y - 1]);
                visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y - 1]);
                visitBlock(curBlock, true, map.blocks[curBlock.position.x + 1][curBlock.position.y - 1]);
            }

            visitBlock(curBlock, false, map.blocks[curBlock.position.x - 1][curBlock.position.y]);
            visitBlock(curBlock, false, map.blocks[curBlock.position.x + 1][curBlock.position.y]);
                
            if(curBlock.position.y < map.height - 1)
            {
                visitBlock(curBlock, true, map.blocks[curBlock.position.x - 1][curBlock.position.y + 1]);
                visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y + 1]);
                visitBlock(curBlock, true, map.blocks[curBlock.position.x + 1][curBlock.position.y + 1]);
            }
        }
    }
    
    public void setFirstLastBlocks()
    {
        firstBlock.visited = true;
        firstBlock.prevPathBlock = null;
        firstBlock.distance = 0;
        firstBlock.isPath = true;
        firstBlock.pathType = MapBlock.PATH_START;
        Point vectorTo = new Point(firstBlock.position.x - lastBlock.position.x, firstBlock.position.y - lastBlock.position.y);
        vectorTo.x = Math.abs(vectorTo.x);
        vectorTo.y = Math.abs(vectorTo.y);
        float distanceTo = (float)Math.sqrt((vectorTo.x * vectorTo.x) + (vectorTo.y * vectorTo.y));
        firstBlock.possibleTrip = firstBlock.distance + distanceTo;
        addBlockOpen(firstBlock);
        
        lastBlock.isPath = true;
        lastBlock.pathType = MapBlock.PATH_END;
        lastBlock.possibleTrip = lastBlock.position.x * lastBlock.position.y;
    }
    
    public void visitBlock(MapBlock prevBlock, boolean diagonal, MapBlock curBlock)
    {
        float distance = (diagonal ? (curBlock.terrainType + prevBlock.terrainType) * diagDist : curBlock.terrainType + prevBlock.terrainType);
        distance = prevBlock.distance + (distance / 2);
        if(curBlock.visited == false || distance < curBlock.distance)
        {
            curBlock.prevPathBlock = prevBlock;
            curBlock.distance = distance;
            Point vectorTo = new Point(curBlock.position.x - lastBlock.position.x, curBlock.position.y - lastBlock.position.y);
            vectorTo.x = Math.abs(vectorTo.x);
            vectorTo.y = Math.abs(vectorTo.y);
            float possibleDiagonals = Math.min(vectorTo.x, vectorTo.y);
            float extraSteps = Math.max(vectorTo.x, vectorTo.y) - possibleDiagonals;
            float distanceTo = (possibleDiagonals * diagDist) + extraSteps;
            curBlock.possibleTrip = curBlock.distance + distanceTo;
            if(curBlock.terrainType != MapBlock.TER_WATER)
            {
                if(curBlock.visited == true)
                {
                    removeBlock(curBlock);
                }
                addBlockOpen(curBlock);
            }
            curBlock.visited = true;
        }
    }
    
    public void removeBlock(MapBlock curBlock)
    {
        MapBlock prevBlock = null;
        MapBlock nextBlock = openBlock;
        boolean continueSearch = true;
        while(nextBlock != null && continueSearch)
        {
            if(curBlock == nextBlock)
                continueSearch = false;
            else
            {
                prevBlock = nextBlock;
                nextBlock = nextBlock.nextDistBlock;
            }
        }
        if(nextBlock != null)
        {
            if(prevBlock != null)
                prevBlock.nextDistBlock = nextBlock.nextDistBlock;
            else
                openBlock = nextBlock.nextDistBlock;
        }
    }
    
    public void addBlockOpen(MapBlock curBlock)
    {
        MapBlock prevBlock = null;
        MapBlock nextBlock = openBlock;
        boolean continueSearch = true;
        while(nextBlock != null && continueSearch)
        {
            if(curBlock.possibleTrip < nextBlock.possibleTrip)
                continueSearch = false;
            else
            {
                prevBlock = nextBlock;
                nextBlock = nextBlock.nextDistBlock;
            }
        }
        if(prevBlock == null)
            openBlock = curBlock;
        else
            prevBlock.nextDistBlock = curBlock;
        curBlock.nextDistBlock = nextBlock;
    }
    
    public void tracePath()
    {
        if(lastBlock.prevPathBlock == null)
        {
            System.out.println("No path found.");
        }
        else
        {
            MapBlock curBlock = lastBlock.prevPathBlock;

            while(curBlock.prevPathBlock != null)
            {
                curBlock.pathType = MapBlock.PATH_PATH;
                curBlock.isPath = true;
                curBlock = curBlock.prevPathBlock;
            }
        }
    }
    
}
