package astarjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Matt
 */
public class AStarJava {
    static Map map;
    static Pathfinder scout;
    static Questioner questioner;
    
    public static void main(String[] args) {
        questioner = new Questioner();
        questioner.set(true, 10, 10, 10, 30, 20);
        scoutAndRender();
        do{
            questioner.questionUser();
            if(questioner.again){
                scoutAndRender();
            }
        }while(questioner.again);
        System.out.println("Goodbye!");
    }
    
    private static void scoutAndRender()
    {
        map = new Map(questioner.height, questioner.width);
        map.generateMap(questioner.forestChance, questioner.mountainChance, questioner.waterChance);
        map.renderMap();
        scout = new Pathfinder();
        System.out.println("Scouting paths...");
        scout.findPath(map);
        map.renderMapPaths();
    }
}
