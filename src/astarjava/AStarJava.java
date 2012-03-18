package astarjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Matt
 */
public class AStarJava {

    public static void main(String[] args) {
        
        Map map = new Map(10, 10);
        map.generateMap(10, 30, 20);
        
        map.renderMap();
        
        Pathfinder scout = new Pathfinder();
        System.out.println("Scouting paths...");
        scout.findPath(map);
        
        map.renderMapPaths();
        
        Questioner questioner = new Questioner();
        
        do
        {
            questioner.questionUser();
            if(questioner.again)
            {
                map = new Map(questioner.height, questioner.width);
                map.generateMap(questioner.forestChance, questioner.mountainChance, questioner.waterChance);

                map.renderMap();

                scout = new Pathfinder();
                System.out.println("Scouting paths...");
                scout.findPath(map);

                map.renderMapPaths();
            }
        }while(questioner.again);
        System.out.println("Goodbye!");
        
    }
}
