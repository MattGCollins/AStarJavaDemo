/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarjava;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Matt
 */
public class PathfinderTest {
    
    public PathfinderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findPath method, of class Pathfinder.
     */
    @Test
    public void testFindPath() {
        System.out.println("findPath");
        Map map = new Map(10,10);
        map.generateMap(10, 30, 0);
        Pathfinder instance = new Pathfinder();
        instance.findPath(map);
        assertTrue(instance.lastBlock.visited);
    }

    /**
     * Test of visitBlock method, of class Pathfinder.
     */
    @Test
    public void testVisitBlock() {
        System.out.println("visitBlock");
        Map map = new Map(10,10);
        map.generateMap(0, 0, 0);
        MapBlock prevBlock = map.blocks[0][0];
        boolean diagonal = true;
        MapBlock curBlock = map.blocks[1][1];
        Pathfinder instance = new Pathfinder();
        instance.lastBlock = map.blocks[9][9];
        instance.visitBlock(prevBlock, diagonal, curBlock);
        assertEquals(curBlock.distance, instance.diagDist, 0.001);
        assertTrue(curBlock.visited);
    }

    /**
     * Test of removeBlock method, of class Pathfinder.
     */
    @Test
    public void testRemoveBlock() {
        System.out.println("removeBlock");
        MapBlock curBlock = new MapBlock();
        Pathfinder instance = new Pathfinder();
        instance.openBlock = curBlock;
        instance.removeBlock(curBlock);
        assertEquals(instance.openBlock, null);
    }

    /**
     * Test of addBlockOpen method, of class Pathfinder.
     */
    @Test
    public void testAddBlockOpen() {
        System.out.println("addBlockOpen");
        MapBlock curBlock = new MapBlock();
        Pathfinder instance = new Pathfinder();
        instance.addBlockOpen(curBlock);
        assertEquals(instance.openBlock, curBlock);
    }

    /**
     * Test of tracePath method, of class Pathfinder.
     */
    @Test
    public void testTracePath() {
        System.out.println("tracePath");
        Map map = new Map(10, 10);
        map.generateMap(0, 0, 0);
        for(int x = 1; x < 10; ++x)
            map.blocks[x][x].prevPathBlock = map.blocks[x - 1][x - 1];
        Pathfinder instance = new Pathfinder();
        instance.lastBlock = map.blocks[9][9];
        instance.tracePath();
        for(int x = 1; x < 9; ++x)
            assertEquals(map.blocks[x][x].pathType, MapBlock.PATH_PATH);
    }
}
