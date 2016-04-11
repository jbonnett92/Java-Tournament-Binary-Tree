package cs21120.assignment.solution;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import cs21120.assignment.CompetitionManager;
import cs21120.assignment.IBinaryTree;
import cs21120.assignment.IManager;
import cs21120.assignment.Match;
import cs21120.assignment.NoNextMatchException;

public class BTSingleElimJAB1256 implements IManager {
	
	Queue<Node> queue;
    Stack<Node> stack;
    ArrayList<Node> treeList;
    Node currentMatchNode;
    Node rootNode;
 
    /*
     * 	Init
     */
    public BTSingleElimJAB1256() {
        queue = new LinkedList<Node>();
        stack = new Stack<Node>();
        treeList = new ArrayList<Node>();
        currentMatchNode = null;
        rootNode = null;
    }
    
    /*
     * 	Main()
     * 
     * 	Sets the program structure.
     * 
     */
    public static void main(String[] args) throws FileNotFoundException {
		IManager behaviour = new BTSingleElimJAB1256();
		CompetitionManager manager = new CompetitionManager(behaviour);
		manager.runCompetition("res/list.txt");
	}
 
    /*
     * 	setPlayers()
     * 	
     * 	Sets up the tree and starts adding.
     * 
     * 	@param    players    an ArrayList of players to add to the tree
     * 	
     */
    @Override
    public void setPlayers(ArrayList<String> players) {
        
        rootNode = new Node(); // Create root node
        treeList.add(rootNode); // Add root node to the treeList order
        queue.add(rootNode); // Add root node to the tree
        setTree(rootNode, players); // Recursive split of the arrayList, construct tree.
        
        setUpMatches(); // Now begin constructing of match order
    }
 
    /*
     * 	hasNextMatch()
     * 
     * 	Checks to see if there is a match to be played.
     * 
     * 	@return    boolean    Returns true if stack has one or more nodes
     */
    @Override
    public boolean hasNextMatch() {
        return stack.size() > 0;
    }
 
    /*
     * 	nextMatch()
     *
     *	Gets the next available match.
     *
     *	@return    Match    A match object
     *
     */
    @Override
    public Match nextMatch() throws NoNextMatchException {
        currentMatchNode = stack.pop(); // Take from stack
        // Check that node exists
        if(currentMatchNode != null) {
        	// Create and return Match object
        	return new Match(currentMatchNode.getLeft().getPlayer(), currentMatchNode.getRight().getPlayer());
        } else {
        	throw new NoNextMatchException("No maches to play."); // No matches available
        }
    }
 
    /*
     *	setMatchScore()
     * 	
     * 	Sets the current matches score.
     * 
     * 	@param    p1    Player ones score
     *  @param    p2    Player twos score
     *  
     */
    @Override
    public void setMatchScore(int p1, int p2) {
        currentMatchNode.getLeft().setScore(p1); // Sets player one score
        currentMatchNode.getRight().setScore(p2); // Sets player two score
        String player; // Create temporary player string variable
        
        // Check that who has the highest score
        if(p1 >= p2) {
            player = currentMatchNode.getLeft().getPlayer(); // Set the player string
        } else {
            player = currentMatchNode.getRight().getPlayer(); // Set the player string
        }
        
        currentMatchNode.setPlayer(player); // Set the match winner
    }
 
    /*
     * 	getPosition()
     * 	
     * 	Gets a players name from a position
     * 	in the tree.
     * 
     * 	@param    n    A int representing a tree node
     * 	@return    String    The players name of node n
     * 	
     */
    @Override
    public String getPosition(int n) {
        return treeList.get(n).getPlayer();
    }
    
    /*
     * 	getCompetitionTree()
     * 
     *	Gets the root node for tree traversal.
     *
     *	@return    Node    The root node of the tree
     *
     */
    @Override
    public Node getCompetitionTree() {
        return rootNode;
    }
 
    /*
     * 	setTree()
     * 
     * 	Adds empty match nodes, along with
     * 	leaves that are the players.
     * 
     *  @param    Node    A node that we add to the left and right
     *  @param    ArrayList    A list of remaining players to add to the tree
     * 
     */
    private void setTree(Node node, ArrayList<String> list) {
        if(list.size() > 1) {
        	// Set half of the array as the left
            ArrayList<String> left = new ArrayList<String>(list.subList(0, (int) Math.floor(list.size() / 2)));
            // Set half of the array as the right
            ArrayList<String> right = new ArrayList<String>(list.subList((int) Math.ceil(list.size() / 2), list.size()));
            
            node.setLeft(new Node()); // Create empty node for the left
            node.setRight(new Node()); // Create empty node for the right
 
            setTree(node.getLeft(), left); // Traverse 
            setTree(node.getRight(), right); // Traverse 
        } else if(list.size() == 1) {
            node.setPlayer(list.get(0)); // Set the leaf "player" node
        }
    }
 
    /*
     * 
     * 	setUpMatches()
     * 	
     * 	Finds empty nodes in the tree that are matches ready to be played.
     * 
     */
    private void setUpMatches() {
    	// Loop though every node in the queue
        while(!queue.isEmpty()) {
            Node node = queue.poll(); // Take from the queue
            treeList.add(node); // Add the node to the treeList order
            
            // Check if the node has a left and right node
            if(node.getLeft() != null && node.getRight() != null) {
                stack.add(node); // Add the node to the stack
                queue.add(node.getLeft()); // Add the nodes left to the queue
                queue.add(node.getRight()); // Add the nodes right to the queue
            }
        }
    }
	
	public class Node implements IBinaryTree {
		
		/*
		 * 	Set up variables
		 */
		private String player;
		private int score;
		private Node left;
		private Node right;
		
		/*
		 * 	Init
		 * 
		 *	Constructors with multiple params.
		 *
		 *	@param    String    Players name
		 *	@param    int    Players score
		 * 
		 */
		public Node(String player, int score) {
			this.player = player;
			this.score = score;
		}
		
		public Node(String player) {
			this.player = player;
		}
		
		public Node(int score) {
			this.score = score;
		}
		
		public Node() { }

		
		/*
		 *	getPlayer()
		 * 
		 *	@return    string    Players name
		 *
		 */
		@Override
		public String getPlayer() {
			return player;
		}
		
		/*
		 * 	setPlayer()
		 * 
		 * 	@param    string    Sets the players name
		 * 
		 */
		public void setPlayer(String player) {
			this.player = player;
		}
		
		/*
		 * 	getScore()
		 *
		 *	@return    int    Players score
		 *
		 */
		@Override
		public int getScore() {
			return score;
		}
		
		/*
		 * 	setScore()
		 *  
		 *  @param    int    Sets the players score
		 */
		public void setScore(int score) {
			this.score = score;
		}
		
		/*
		 *  setLeft()
		 * 
		 * 	@param    Node    Sets the left node of the current node object
		 * 
		 */
		public void setLeft(Node left) {
			this.left = left;
		}
		
		/*
		 * 	getLeft()
		 *
		 *	@return    Node    Returns the left node of the current node object
		 *
		 */
		@Override
		public Node getLeft() {
			return left;
		}
		
		/*
		 *  setRight()
		 * 
		 * 	@param    Node    Sets the right node of the current node object
		 * 
		 */
		public void setRight(Node right) {
			this.right = right;
		}

		/*
		 * 	getRight()
		 *
		 *	@return    Node    Returns the right node of the current node object
		 *
		 */
		@Override
		public Node getRight() {
			return right;
		}
	}

}
