package Players.DOMINOS;
//import Interface.Coordinate;

import Interface.Coordinate;

import java.util.*;

/**
 * Created by Melissa Laskowski  on 3/29/2015.
 *
 * The graph of the board a node connection 9x9
 */
public class Board {

    public int max_side; //max length
    Map<Node,HashSet<Node>> board = new HashMap<Node, HashSet<Node>>(); //the board





    /**
     * Constructor
     * Creating a board where everything is connected
     * @param size - the size of the board
     */
    public Board (int size){
        this.max_side = size;


        //create all the coordinates and put them in the graph
        for (int row = 0; row <=max_side; row++){

            for (int col =0; col <=max_side; col ++){
                Node temp= new Node(row, col);
                HashSet<Node> tt = getNeigh(temp);
                board.put(temp,tt);

            }
        }

        //^^^set up all the coordinates ..now connect them so they can be unconnected
        for(Node c : board.keySet()){ //go through all the coordinates in the board

            for( Node cc :board.get(c)){ //for all their neighbors
                c.addNeighbor(cc);              // add em as neighbors
            }
        }


        //name_count =0;  //reuse name count to go through all the points and figure out who their neighbor is
        //connect neighbors

    }



    public HashSet<Coordinate> getCoordinate (  ){
        HashSet<Coordinate> coo = new HashSet<Coordinate>();
        for(Node c : board.keySet()){
            coo.add(new Coordinate(c.row, c.col));
        }
        return coo;
    }

    public HashSet<Node> getNeighbor ( Node b){
        HashSet<Node> re = new HashSet<Node>();
//        LinkedList < Node > ggrat = b.getNeighbors();
//        for(Node s : ggrat){
//            re.add(s);
//        }

        re = board.get(b);

        return re;

    }

    /**
     * When a wall goes up remove their connections in the graph and change the coordinates neighbor list
     *
     * ccheck the coordinates connect
     * @param start - the start coordinate of the board
     * @param finish - the end coordinate of the board
     */
    public void wallWentUp ( Node start , Node finish){




        if(start.col == finish.col){
            Node first = new Node (start.row, start.col-1);
            board.get(start).remove(first);
            board.get(first).remove(start);
            first = new Node (finish.row-1, finish.col-1);
            Node fakefinish = new Node (finish.row-1, finish.col);
            board.get(fakefinish).remove(first);
            board.get(first).remove(fakefinish);
        }else{
            Node first = new Node(start.row-1, start.col);
            board.get(start).remove(first);
            board.get(first).remove(start);
            first = new Node (finish.row-1, finish.col-1);
            Node fakefinish = new Node(finish.row, finish.col-1);
            board.get(fakefinish).remove(first);
            board.get(first).remove(fakefinish);
        }


    }

    public void wallWentDown ( Node start , Node finish){




        if(start.col == finish.col){
            Node first = new Node (start.row, start.col-1);
            board.get(start).add(first);
            board.get(first).add(start);
            first = new Node (finish.row-1, finish.col-1);
            Node fakefinish = new Node (finish.row-1, finish.col);
            board.get(fakefinish).add(first);
            board.get(first).add(fakefinish);
        }else{
            Node first = new Node(start.row-1, start.col);
            board.get(start).add(first);
            board.get(first).add(start);
            first = new Node (finish.row-1, finish.col-1);
            Node fakefinish = new Node(finish.row, finish.col-1);
            board.get(fakefinish).add(first);
            board.get(first).add(fakefinish);
        }


    }


    /**
     * This might be helpful
     * To help with the validity of a a move
     * @param point1 - starting point
     * @param point2 - ending point
     * @return
     */
    public boolean isconnected(Coordinate point1, Coordinate point2){
        return false;
    }

    /**
     * Check the graph to see if there's a connection
     * @param point1 - the first coordinate
     * @param point2 - the second coordinate
     * @return
     */
    public boolean isNeigh(Coordinate point1, Coordinate point2){
        return false;
    }

    public HashSet<Node> getNeigh( Node c){
        HashSet<Node> neighbor = new HashSet<Node>();
        int col = c.getCol();
        int row = c.getRow();
        Node up ;
        Node down;
        Node left;
        Node right;
        //check four corner


        if(col >=max_side-1){
            right = null; //because it would hit the edge
            left = new Node(row,col-1);
        }
        else if(col<=0){
            right = new Node(row,col+1);
            left= null; //would hit the edge
        }else {
            left = new Node(row , col-1);
            right = new Node(row, col+1);
        }

        if(row >=max_side-1){
            down = null; //because it would hit the edge
            up = new Node(row-1,col);
        }
        else if(row<=0){
            down = new Node(row+1,col);
            up = null; //would hit the edge
        }else {
            up = new Node(row + 1, col);
            down = new Node(row - 1, col);
        }

        if(up != null){
            neighbor.add(up);
        }
        if(down != null){
            neighbor.add(down);
        }if(left!= null){
            neighbor.add(left);
        }if(right != null){
            neighbor.add(right);
        }
//        neighbor.add(up);
//        neighbor.add(down);
//        neighbor.add(left);
//        neighbor.add(right);
//        neighbor.remove(null);
        return neighbor;
    }



}
