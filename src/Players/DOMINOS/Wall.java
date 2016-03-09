package Players.DOMINOS;

import Interface.Coordinate;

/**
 * Created by Adam Kuhn on 4/27/2015.
 */
public class Wall {

    Coordinate start; //The beginning of the wall
    Coordinate end;   //The end of the wall
    Coordinate midpoint; //The midpoint of the wall

    /* General constructor
     */
    public Wall(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        this.midpoint = getMidpoint();
    }

    /*
    Start coordinate getter
     */
    public Coordinate getStart(){
        return this.start;
    }

    /*
    End coordinate getter
     */
    public Coordinate getEnd(){
        return this.end;
    }

    /*
    Midpoint coordinate getter
     */
    public Coordinate getMidpoint() {

        Coordinate mid;

        if (this.start.getRow() == this.end.getRow()) { //If the wall is horizontal
            mid = new Coordinate(this.start.getRow(), this.end.getCol() - 1);
            return mid;
        }
        else { //If the wall is vertical
            mid = new Coordinate(this.end.getRow() - 1, this.end.getCol());
            return mid;
        }
    }
}
