package cs21120.assignment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main program to run a competition, you don't need to change this class
 * @author ncm
 */
public class CompetitionManager {
    /** The manager in use */
    IManager manager;
    
    /** Constructor for a CompetitionManager
     * 
     * @param manager the manager decides the rules of the competition (playing order)
     */
    public CompetitionManager(IManager manager) {
        this.manager = manager;
    }
    
    /** Runs the competition, printing the tree before, then pairs of teams and waiting for input of 
     * the results between each pair. The tree is printed again at the end of the competition.
     * 
     * @param listFileName the name of the file contain the list of teams
     * @throws FileNotFoundException thrown if the list of players can't be found
     */
    public void runCompetition(String listFileName) throws FileNotFoundException {
        ArrayList<String> competitors = readPlayers(listFileName);
        
        Scanner in = new Scanner(System.in);
        manager.setPlayers(competitors);
        
        TreePrinter.print(manager.getCompetitionTree());
        
        while(manager.hasNextMatch()) {
            Match match = manager.nextMatch();
            System.out.println("Player 1: " + match.getPlayer1());
            System.out.println("Player 2: " + match.getPlayer2());
            boolean notValidInput = true;
            boolean draw=true;
            int p1score=0, p2score=0;
            while (draw) {
                while (notValidInput) {
                    if (in.hasNextInt()) {
                        notValidInput = false;
                    } else if (in.hasNext()) {
                        String str = in.next();
                        System.out.println(str + " is not a valid input, please enter a number");
                    }
                }
                p1score = in.nextInt();
                notValidInput = true;
                while (notValidInput) {
                    if (in.hasNextInt()) {
                        notValidInput = false;
                    } else if (in.hasNext()) {
                        String str = in.next();
                        System.out.println(str + " is not a valid input, please enter a number");
                    }
                }
                p2score = in.nextInt();
                if (p1score == p2score) {
                    System.out.println("We need a result, not a draw!  Please have a rematch!");
                } else {
                    draw = false;
                }
            }
            manager.setMatchScore(p1score, p2score);
        }
   
        System.out.println("Winner is: " + manager.getPosition(0));
        
        TreePrinter.print(manager.getCompetitionTree());
    }
    
    
    /** Reads a list of players / teams from the named file, should be one per line
     * 
     * @param filename the name of the file to open and read from
     * @return returns an arraylist of the player/team names
     * @throws FileNotFoundException thrown if the file can't be found
     */
    public static ArrayList<String> readPlayers(String filename) throws FileNotFoundException {
        FileReader fr = new FileReader(filename);
        Scanner sc = new Scanner(fr);
        ArrayList<String> players = new ArrayList<>();
        while (sc.hasNextLine()) {
            players.add(sc.nextLine());
        }
        return players;
    }
    
}