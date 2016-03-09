package Players.DOMINOS;

import Interface.Coordinate;

import java.util.LinkedList;

/**
 * Created by Melissa on 3/31/2015.
 */
public class Node  {


    public Node (int row, int col){

        this.row = row;
        this.col = col;
    }


    public static final int BOARD_DIM =9; //how big the board will be
    Node copy;
    int row;
    int col;

    private LinkedList<Node> neighbors =new LinkedList<Node>(); //all the neighbors


    /**
     *
     * @return  the row coordinate
     * */
    public int getRow(){
        return row;
    }

    /**
     * return the column coordinate
     */
    public int getCol(){
        return col;
    }



    /**
     * equality checker
     * @param obj - the object to check against
     * @return iff obj ==this
     */
    @Override
    public boolean equals (Object obj){
        Coordinate s = new Coordinate(row , col);
        if(s.hashCode() == obj.hashCode()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * IDDDK
     * Hash code
     * @return hash
     */
    @Override
    public int hashCode(){
        Coordinate k = new Coordinate(row,col);
        return k.hashCode();
    }

    /**
     * String representation
     * @return string ring ring
     */
    @Override
    public String toString(){
        return ("Row "+this.row+ " and Column " + col);
    }

    /**
     * to connect all the nodes that are neighbors together
     * @param c1 - coordinate thats the new  neighbor
     */
    public void addNeighbor( Node c1){
        if(!this.neighbors.contains(c1)){
            neighbors.add(c1); //add the coordinate to the linked list
        }
    }

    public void removeNeighbor (Node c1){
        if(this.neighbors.contains(c1)){
            neighbors.remove(c1);  //remove the coordinate frm the linked list
        }
    }

    /**
     * returns a list of neighbots
     * help with getting stuff
     * @return  lissst
     */
    public LinkedList<Node> getNeighbors(){
        return this.neighbors;
    }
}
