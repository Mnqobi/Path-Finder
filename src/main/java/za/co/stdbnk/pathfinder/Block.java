/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.stdbnk.pathfinder;

/**
 *
 * @author Mnqobi
 */
public class Block{      
    
    private double distanceToEndBlock = 0;
    private double totalCostOfMovement = 0;
    private int x;
    private int y;
    private Block previous; 

    Block(int x, int y){
        this.x = x;
        this.y = y; 
    }

    public double getDistanceToEndBlock() {
        return distanceToEndBlock;
    }

    public void setDistanceToEndBlock(double distance) {
        this.distanceToEndBlock = distance;
    }

    public double getTotalCostOfMovement() {
        return totalCostOfMovement;
    }

    public void setTotalCostOfMovement(double totalCostOfMovement) {
        this.totalCostOfMovement = totalCostOfMovement;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Block getPrevious() {
        return previous;
    }

    public void setPrevious(Block previous) {
        this.previous = previous;
    }
}
