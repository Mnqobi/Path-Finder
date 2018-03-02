/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.stdbnk.pathfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mnqobi Ngubane
 */
public class PathFinder {

    private Block startBlock;
    private Block destinationBlock;

    private String[][] map;
    private Block[][] blockMap;

    private PriorityQueue<Block> visitedBlocks;
    private Path solution;

    private double costPerMove;

    public Path findPath(File file, double costPerMove) {
        this.costPerMove = costPerMove;
        solution = new Path();

        //1. Read file, map it and determine the start and end destinations
        initializeMap(file);

        //2. Initialize the block map and define the distance of each
        //block from the destination
        initilizeGrid();

        //3. Determine path from Start to End
        determinePath();

        //4. Map path from Start to End
        mapPathToGrid();

        return solution;
    }

    private void initializeMap(File file) {
        try {

            //1. Read file
            this.map = Files.lines(file.toPath())
                    .map(s -> s.split("\\s+"))
                    .toArray(String[][]::new);

            //2. Determine start and end block location
            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map[0].length; y++) {
                    switch (map[x][y]) {
                        case "S":
                            startBlock = new Block(x, y);
                            break;
                        case "E":
                            destinationBlock = new Block(x, y);
                            break;
                    }
                }
            }

            //Clone Map
            String[][] mapClone = Arrays.stream(map)
                    .map((String[] row) -> row.clone())
                    .toArray((int length) -> new String[length][]);

            solution.setMap(mapClone);
        } catch (IOException ex) {
            Logger.getLogger(PathFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void determineMovement(Block current, Block surrounding) {
        /* 
            Here we inspect the current block and ensure that it only takes the
            shortest possible path, thanks to having previously calculated each
            blocks distance from the end.
         */

        double totalCostOfMovement = surrounding.getDistanceToEndBlock() + costPerMove;
        boolean visited = visitedBlocks.contains(surrounding);

        /*
            The reason why we need to know if we've processed the block is so we can 
            negate this proccess and remove it from the ordered queue 
         */
        if (!visited || totalCostOfMovement < surrounding.getTotalCostOfMovement()) {
            //Leave breadcrumb
            surrounding.setTotalCostOfMovement(totalCostOfMovement);
            surrounding.setPrevious(current);
            if (!visited) {
                visitedBlocks.add(surrounding);
            }
        }
    }

    private void initilizeGrid() {
        blockMap = new Block[map.length][map[0].length];
        /*
            Map the placement of each block into the blockMap while calculating its 
            total distance from the destination block, using the mathematical 
            distance formula.
            d = sqrt((y2-y1)^2 + (x2-x1)^2)) 
         */
        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map[0].length; ++y) {
                blockMap[x][y] = new Block(x, y);
                /* 
                    Calculate the distance from the current block
                    to the destination block using the mathematical distance formula
                    d = sqrt((y2-y1)^2 + (x2-x1)^2))
                 */
                blockMap[x][y].setDistanceToEndBlock(
                        Math.sqrt(Math.pow((destinationBlock.getY() - y), 2)
                                + Math.pow((destinationBlock.getX() - x), 2))
                );
            }
        }    
    }

    public void determinePath() {
        /*
            We need to Order the items coming into the Queue by priority
            of thier total movement cost, lowest cost on top and  highest at
            the bottom, so when we poll the queue we always get the 
            lowest cost move.
         */
        visitedBlocks = new PriorityQueue<>((Block o1, Block o2) -> {
            if (o1.getTotalCostOfMovement() == o2.getTotalCostOfMovement()) {
                return 0;
            }
            return o1.getTotalCostOfMovement() > o2.getTotalCostOfMovement() ? 1 : -1;
        });

        //Used to represent the current block being assessed against surrounding blocks        
        //Lets start, at the start block.
        Block current = blockMap[startBlock.getX()][startBlock.getY()];
        List<Block> surrounding = new ArrayList<>();

        do {
            //If the current block is the last block then we're done!
            if (current.equals(blockMap[destinationBlock.getX()][destinationBlock.getY()])) {
                return;
            } else {
                 //Else its a processed block
                map[current.getX()][current.getY()] = "\"";
            }
            
            //Now we find the neighbouring blocks
            //TOP
            if (current.getY() - 1 >= 0) 
                Optional.ofNullable(blockMap[current.getX()][current.getY() - 1]).ifPresent(surrounding::add);
            //BOTTOM
            if (current.getY() + 1 < blockMap[0].length) 
                Optional.ofNullable(blockMap[current.getX()][current.getY() + 1]).ifPresent(surrounding::add);
            //LEFT
            if (current.getX() - 1 >= 0)
                Optional.ofNullable(blockMap[current.getX() - 1][current.getY()]).ifPresent(surrounding::add);
            //RIGHT
            if (current.getX() + 1 < blockMap.length) 
                Optional.ofNullable(blockMap[current.getX() + 1][current.getY()]).ifPresent(surrounding::add);            

            //Lets not waste time processing walls or already visited blocks
            surrounding.removeIf(block -> "W,\"".contains(map[block.getX()][block.getY()]));
            
            //Lets determine our next best move
            for (Block block : surrounding) {
                determineMovement(current, block);
            }

            current = visitedBlocks.poll();
        } while (current != null);
    }

    public void mapPathToGrid() {
        //Trace back the path by iterating through the parents, staring from the
        //destination
        Block block = blockMap[destinationBlock.getX()][destinationBlock.getY()];

        //Null previous block means we're back at the starting point
        //lets map out the true path in reverse
        while (block.getPrevious() != null) {
            map[block.getPrevious().getX()][block.getPrevious().getY()] = "*";
            block = block.getPrevious();
        }
        map[startBlock.getX()][startBlock.getY()] = "S";
        solution.setSolutionMap(map);
    }
}
