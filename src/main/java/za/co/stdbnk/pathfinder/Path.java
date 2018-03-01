/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.stdbnk.pathfinder;

import java.util.Arrays;

/**
 *
 * @author Mnqobi
 */
public class Path{
    private String[][] map;
    private String[][] solutionMap;        

    public String[][] getMap() {
        return map;
    }

    public void setMap(String[][] map) {
        this.map = map;
    }

    public String[][] getSolutionMap() {
        return solutionMap;
    }

    public void setSolutionMap(String[][] solutionMap) {
        this.solutionMap = solutionMap;
    }
    
    public static String printMap(String[][] mappedGrid){
        StringBuilder builder = new StringBuilder();
        Arrays.stream(mappedGrid).parallel().forEachOrdered((row) -> {
            Arrays.stream(row).parallel().forEachOrdered((column) -> {
                builder.append(column).append(" ");
            });
            builder.append("\n");
        });
        return builder.toString();
    }
}
