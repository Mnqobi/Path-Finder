/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.stdbnk.pathfinder;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Mnqobi
 */
public class PathFinderMain {
    public static void main(String[] args){
        boolean prettyWay = false; //Using JFileChooser
        File file = null ;
        final double COST_PER_MOVE = 1.0;
        
        try {
            if(prettyWay){
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
                int option = chooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                }
            }else{
                //Hardcode your file location here
                file  = new File("C:\\Users\\Mnqobi\\Downloads\\map.txt");
            };

            PathFinder pathFinder = new PathFinder();
            Path solution = pathFinder.findPath(file, COST_PER_MOVE);
            
            System.out.println("Map\n");
            System.out.println(Path.printMap(solution.getMap()));
            System.out.println("Map - Solved\n");
            System.out.println(Path.printMap(solution.getSolutionMap()));
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PathFinderMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
