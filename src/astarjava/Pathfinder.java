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
    
    private static final int VISIT_LEFT = 0;
    private static final int VISIT_CENTER = 1;
    private static final int VISIT_RIGHT = 2;
    
    float diagDist = (float)Math.sqrt(2.0f);
    
    public Pathfinder()
    {
        firstBlock = null;
        lastBlock = null;
        openBlock = null;
    }
    
    public void findPath(Map map)
    {
        setLastBlock(map);
        setFirstBlock(map);
        MapBlock curBlock;
        do
        {
            curBlock = openBlock;
            spreadPath(curBlock, map);
        }while(openBlock != null && (lastBlock.possibleTrip > openBlock.possibleTrip));
        tracePath();
    }
    
    private void setFirstBlock(Map map)
    {
        firstBlock = map.blocks[0][0];
        firstBlock.visited = true;
        firstBlock.prevPathBlock = null;
        firstBlock.distance = 0;
        firstBlock.isPath = true;
        firstBlock.pathType = MapBlock.PATH_START;
        Point vectorTo = new Point(firstBlock.position.x - lastBlock.position.x, firstBlock.position.y - lastBlock.position.y);
        vectorTo.x = Math.abs(vectorTo.x); vectorTo.y = Math.abs(vectorTo.y);
        firstBlock.possibleTrip = firstBlock.distance + (float)Math.hypot(vectorTo.x, vectorTo.y);
        addBlockOpen(firstBlock);
    }
    
    private void setLastBlock(Map map)
    {
        lastBlock = map.blocks[map.width - 1][map.height - 1];
        lastBlock.isPath = true;
        lastBlock.pathType = MapBlock.PATH_END;
        lastBlock.possibleTrip = lastBlock.position.x * lastBlock.position.y;
    }
    
    private void spreadPath(MapBlock curBlock, Map map)
    {
        openBlock = openBlock.nextDistBlock;
        if(curBlock.position.x == 0)
            visitBlockLeft(curBlock, map);
        else if(curBlock.position.x == map.width - 1)
            visitBlockRight(curBlock, map);
        else
            visitBlockCenter(curBlock, map);
    }
    
    private void visitBlockLeft(MapBlock curBlock, Map map)
    {
        if(curBlock.position.y > 0)
            visitBlockTop(curBlock, map, VISIT_LEFT);
        visitBlockMiddle(curBlock, map, VISIT_LEFT);
        if(curBlock.position.y < map.height - 1)
            visitBlockBottom(curBlock, map, VISIT_LEFT);
    }
    
    private void visitBlockRight(MapBlock curBlock, Map map)
    {
        if(curBlock.position.y > 0)
            visitBlockTop(curBlock, map, VISIT_RIGHT);
        visitBlockMiddle(curBlock, map, VISIT_RIGHT);
        if(curBlock.position.y < map.height - 1)
            visitBlockBottom(curBlock, map, VISIT_RIGHT);
    }
    
    private void visitBlockCenter(MapBlock curBlock, Map map)
    {
        if(curBlock.position.y > 0)
            visitBlockTop(curBlock, map, VISIT_CENTER);
        visitBlockMiddle(curBlock, map, VISIT_CENTER);
        if(curBlock.position.y < map.height - 1)
            visitBlockBottom(curBlock, map, VISIT_CENTER);
    }
    
    private void visitBlockTop(MapBlock curBlock, Map map, int leftCenterRight)
    {
        if(!(leftCenterRight == VISIT_LEFT))
            visitBlock(curBlock, true, map.blocks[curBlock.position.x - 1][curBlock.position.y - 1]);
        visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y - 1]);
        if(!(leftCenterRight == VISIT_RIGHT))
            visitBlock(curBlock, true, map.blocks[curBlock.position.x + 1][curBlock.position.y - 1]);
    }
    
    private void visitBlockMiddle(MapBlock curBlock, Map map, int leftCenterRight)
    {
        if(!(leftCenterRight == VISIT_LEFT))
            visitBlock(curBlock, false, map.blocks[curBlock.position.x - 1][curBlock.position.y]);
        if(!(leftCenterRight == VISIT_RIGHT))
            visitBlock(curBlock, false, map.blocks[curBlock.position.x + 1][curBlock.position.y]);
    }
    
    private void visitBlockBottom(MapBlock curBlock, Map map, int leftCenterRight)
    {
        if(!(leftCenterRight == VISIT_LEFT))
            visitBlock(curBlock, true, map.blocks[curBlock.position.x - 1][curBlock.position.y + 1]);
        visitBlock(curBlock, false, map.blocks[curBlock.position.x][curBlock.position.y + 1]);
        if(!(leftCenterRight == VISIT_RIGHT))
            visitBlock(curBlock, true, map.blocks[curBlock.position.x + 1][curBlock.position.y + 1]);
    }
    
    public void visitBlock(MapBlock prevBlock, boolean diagonal, MapBlock curBlock)
    {
        float distance = (diagonal ? (curBlock.terrainType + prevBlock.terrainType) * diagDist : curBlock.terrainType + prevBlock.terrainType);
        distance = prevBlock.distance + (distance / 2);
        if(curBlock.visited == false || distance < curBlock.distance){
            curBlock.prevPathBlock = prevBlock;
            curBlock.distance = distance;
            curBlock.possibleTrip = findPossibleTrip(curBlock);
            if(curBlock.terrainType != MapBlock.TER_WATER){
                if(curBlock.visited == true){
                    removeBlock(curBlock);
                }
                addBlockOpen(curBlock);
            }
            curBlock.visited = true;
        }
    }
    
    private float findPossibleTrip(MapBlock curBlock)
    {
        Point vectorTo = new Point(curBlock.position.x - lastBlock.position.x, curBlock.position.y - lastBlock.position.y);
        vectorTo.x = Math.abs(vectorTo.x);
        vectorTo.y = Math.abs(vectorTo.y);
        float possibleDiagonals = Math.min(vectorTo.x, vectorTo.y);
        float extraSteps = Math.max(vectorTo.x, vectorTo.y) - possibleDiagonals;
        float distanceTo = (possibleDiagonals * diagDist) + extraSteps;
        return curBlock.distance + distanceTo;
    }
    
    public void removeBlock(MapBlock curBlock)
    {
        MapBlock prevBlock = null;
        MapBlock nextBlock = openBlock;
        boolean continueSearch = true;
        while(nextBlock != null && continueSearch){
            if(curBlock == nextBlock)
                continueSearch = false;
            else{
                prevBlock = nextBlock;
                nextBlock = nextBlock.nextDistBlock;
            }
        }
        if(nextBlock != null){
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
        while(nextBlock != null && continueSearch){
            if(curBlock.possibleTrip < nextBlock.possibleTrip)
                continueSearch = false;
            else{
                prevBlock = nextBlock;
                nextBlock = nextBlock.nextDistBlock;
            }
        }
        if(prevBlock == null) openBlock = curBlock;
        else prevBlock.nextDistBlock = curBlock;
        curBlock.nextDistBlock = nextBlock;
    }
    
    public void tracePath()
    {
        if(lastBlock.prevPathBlock == null){
            System.out.println("No path found.");
        }
        else{
            MapBlock curBlock = lastBlock.prevPathBlock;

            while(curBlock.prevPathBlock != null){
                curBlock.pathType = MapBlock.PATH_PATH;
                curBlock.isPath = true;
                curBlock = curBlock.prevPathBlock;
            }
        }
    }
    
}
