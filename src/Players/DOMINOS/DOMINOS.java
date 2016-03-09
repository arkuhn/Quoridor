package Players.DOMINOS;

import Engine.Logger;
import Interface.Coordinate;
import Interface.PlayerModule;
import Interface.PlayerMove;

import java.util.*;

/**
 * Created by Melissa Laskowski on 3/28/2015.
 */
public class DOMINOS implements PlayerModule {


    private Logger logger; //logger class
    private int playerId; // player's id #
    private int numWalls; //how many walls the player has
    private Map<Integer,Coordinate> playerLocation; //location of other players
    private Board bo;  //the game board
    private PlayerMove m;
    private HashSet<Wall> AllWall = new HashSet<Wall>(); //Global set of every possible wall placement
    private int check =4;
    private HashMap<Integer, Integer> walls = new HashMap<Integer, Integer>();
    public  HashSet<Wall> PlacedWalls = new HashSet<Wall>(); //Global set of placed walls on the board
    public int numPlayers;
    private List<Coordinate> myEndpoints = new ArrayList<>();
    private List<Coordinate> actionList = new ArrayList<>();
    private List<Coordinate> redZone = new ArrayList<>();
    private int shiller =0; //check for moves
    private PlayerMove shillerwall; //shller wall
    private int myEnemy;

    /**
     *
     * Where getwalls works in
     *
     * Don't need to check for
     * Notifies you that a move was just made.
     * USe this function to update your given board state acordingly
     * Al moves are given to you in the order that they are made
     * @param playerMove - the move
     */
    @Override
    public void lastMove(PlayerMove playerMove) {
        //if you added a wall , remove a wall from your current amoount
        if(playerMove.isMove() ==false) {
            if(playerMove.getPlayerId() == this.playerId){
                this.numWalls-=1;
            }
            int wallN = walls.get(playerMove.getPlayerId())-1;
            Node start = new Node(playerMove.getStart().getRow(), playerMove.getStart().getCol());
            Node finish = new Node(playerMove.getEnd().getRow(), playerMove.getEnd().getCol());
            bo.wallWentUp(start,finish);
            walls.remove(playerMove.getPlayerId());
            walls.put(playerMove.getPlayerId(), wallN);
            //Now that a wall is up remove the connections

            //Everytime a wall goes up add it to the global "Placed Walls" set
            Coordinate startHere = new Coordinate(start.getRow(), start.getCol());
            Coordinate endHere = new Coordinate(finish.getRow(), finish.getCol());
            Wall here = new Wall(startHere, endHere);
            PlacedWalls.add(here);

        }else{
            playerLocation.put(playerMove.getPlayerId(), new Coordinate(playerMove.getEndRow(),playerMove.getEndCol()));

        }

        //What was given to us
        System.out.println("in lastMove ..." + playerMove);
    }

    /**
     * Basically the constructor
     * Initializes your player module.
     * In this method: set up data structures and pre-populate w the starting board configuration
     * All state should be stored in your player class
     * @param logger - reference to the logger class
     * @param i - the id of this player (1-4)
     * @param i1 - number of walls this player has (max 10/start value)
     * @param map- locations of other players (null coordinate means invalid player; 1-based indexing)
     */
    @Override
    public void init(Logger logger, int i, int i1, Map<Integer, Coordinate> map) {

        this.logger = logger;
        this.playerId = i;
        this.numWalls = i1;
        this.playerLocation = map;
        this.bo = new Board(9);

        if(i1==5) { //4 players
            for (int x = 1; x < 5; x++) {
                walls.put(x, 5);
            }
            this.numPlayers = 4;
        }else{   //2 players
            for (int x =1; x<3; x++){
                walls.put(x,10);
            }
            this.numPlayers = 2;
        }

        int row;
        int col;
        Coordinate start;
        Coordinate end;


        //Iterate through the board
        for (row = 1; row <= 8; row++){ //Add all possible horizontal walls to a list
            for (col = 0; col <= 7; col++) {
                start = new Coordinate(row, col);
                end = new Coordinate(row, col + 2);
                Wall here = new Wall(start, end);
                AllWall.add(here);
            }
        }

        //Iterate through the board
        for (row = 0; row <= 7; row++){ //Add all possible vertical walls to a list
            for (col = 1; col <= 8; col++) {
                start = new Coordinate(row, col);
                end = new Coordinate(row+2, col);
                Wall here = new Wall(start, end);
                AllWall.add(here);
            }
        }

        //Generate a list of endpoints dependent on the player ID
        Coordinate finish;
        Coordinate action;

        if (this.getID() == 1){
            row = 0;
            for (col = 0; col <= 8; col++) {
                finish = new Coordinate(row, col);
                myEndpoints.add(finish);
            }
            row = 6;
            for(col = 0; col <=8; col++){
                action = new Coordinate(row, col);
                actionList.add(action);
            }
            row = 7;
            for(col = 0; col <=8; col++){
                action = new Coordinate(row, col);
                redZone.add(action);
            }

            shillerwall = new PlayerMove(1,false,new Coordinate(7,5),new Coordinate(9,5));
            myEnemy =2;
        }
        else if (this.getID() == 2){
            row = 8;
            for (col = 0; col <= 8; col++) {
                finish = new Coordinate(row, col);
                myEndpoints.add(finish);
            }

            row = 2;
            for(col = 0; col <=8; col++){
                action = new Coordinate(row, col);
                actionList.add(action);
            }
            row = 1;
            for(col = 0; col <=8; col++){
                action = new Coordinate(row, col);
                redZone.add(action);
            }

            myEnemy =1;
            shillerwall = new PlayerMove(2,false,new Coordinate(0,5), new Coordinate(2,5));
        }
        else if (this.getID() == 3){
            col = 8;
            for (row = 0; row <= 8; row++) {
                finish = new Coordinate(row, col);
                myEndpoints.add(finish);
            }

            col = 2;
            for(row = 0; row <=8; row++){
                action = new Coordinate(row, col);
                actionList.add(action);
            }

            col = 1;
            for(row = 0; row <=8; row++){
                action = new Coordinate(row, col);
                redZone.add(action);
            }
            myEnemy = 4;
            shillerwall = new PlayerMove(3,false, new Coordinate(5,0), new Coordinate(5,2));

        }
        else if (this.getID() == 4){
            col = 0;
            for (row = 0; row <= 8; row++) {
                finish = new Coordinate(row, col);
                myEndpoints.add(finish);
            }

            col = 6;
            for(row = 0; row <=8; row++){
                action = new Coordinate(row, col);
                actionList.add(action);
            }

            col = 7;
            for(row = 0; row <=8; row++){
                action = new Coordinate(row, col);
                redZone.add(action);
            }

            myEnemy = 3;
            shillerwall = new PlayerMove(4, false, new Coordinate(5,7),new Coordinate(5,9));
        }



    }

    /**
     * Not for part1
     *
     * notifies you that an oppenent player made a bad move and has been invalidated.
     * When called, update your state and remove the invalidated opponent from the board.
     * @param i - the id of the invalid player
     */
    @Override
    public void playerInvalidated(int i) {
        playerLocation.put(i,null); //the player's location is now null

    }

    /**
     * The method that is called when it is your turn to make a move in the game. The time, in seconds,
     * that you have to make a move before invalidated is in the config file  player move limit param (10sec default)
     *
     * @return the moved that you want to make, if you return an invalid move, your player will be invalidated
     *
     */
    @Override
    public PlayerMove move() {
//\\


        List<PlayerMove> moves = new LinkedList<PlayerMove>(allPossibleMoves());

        //if we are on one of the action spots
        if(shiller==0 &&actionList.contains(playerLocation.get(getID()))){
            shiller =1;
            //check to make sure another player hasnt put a wall where shiller belongs
            if(moves.contains(shillerwall)) {

                return shillerwall;
            }
        }

        //if the shiller wall has not been placed
        if(shiller ==0){
            //the list to get to the action node spot with the least amount of moves
            List<Coordinate> shortest = new ArrayList<Coordinate>();
            for(int x =0; x<100; x++){
                shortest.add(new Coordinate(0,0));
            }
            //for each of the coordinates that could be the action spot
            for(Coordinate x : actionList){
                List<Coordinate> path = getShortestPath(getPlayerLocation(getID()), x);
                //if the path is shorter than the current shortest path
                if(path.size() <  shortest.size()){
                    shortest = path;
                }
            }
            PlayerMove shilmove = new PlayerMove(getID(),true,playerLocation.get(getID()),shortest.get(1));

            if(allPossibleMoves().contains(shilmove)){
                return shilmove;
            }
        }

        for (Coordinate y:actionList){ //Check to see if the enemy is at the shiller point

            if (getPlayerLocation(myEnemy).equals(y)){ //If they are there

                if (this.getID() == 1 || this.getID() == 2){ //The shiller wall is vertical
                    if (getPlayerLocation(myEnemy).getCol() >= shillerwall.getEndCol()){ //If the enemy is in a column right of the wall

                        if (this.getID() == 1){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }

                        }
                        if (this.getID() == 2){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }
                        }

                    }
                    else{//The player is left of the wall, left align the walls to the player

                        if (this.getID() == 1){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }
                        }
                        if (this.getID() == 2){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }
                        }

                    }


                }

                else{ //If its player 3 or 4 a.k.a the shiller wall is horizontal
                    if (getPlayerLocation(myEnemy).getRow() < shillerwall.getEndRow()){ //If the enemy is in a row above the wall

                        if (this.getID() == 3){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()-1, getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol());
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                        if (this.getID() == 4){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()-1, getPlayerLocation(myEnemy).getCol()+1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                    }
                    else{//The player is below the wall left align the wall
                        if (this.getID() == 3){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+2, getPlayerLocation(myEnemy).getCol());
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                        if (this.getID() == 4){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+2, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                    }

                }
            }
        }


        for (Coordinate j:redZone){ //Check to see if the enemy is in the s part

            if (getPlayerLocation(myEnemy).equals(j)){ //If they are there

                if (this.getID() == 1 || this.getID() == 2){ //The shiller wall is vertical
                    if (getPlayerLocation(myEnemy).getCol() < shillerwall.getEndCol()){ //If the enemy is in a column left of the wall

                        if (this.getID() == 1){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }

                        }
                        if (this.getID() == 2){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }
                        }

                    }
                    else{//The player is left of the wall, left align the walls to the player

                        if (this.getID() == 1){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }
                        }
                        if (this.getID() == 2){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()-1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                            Coordinate frontStart2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd2 = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+2);
                            PlayerMove blockThem2 = new PlayerMove(getID(),false,frontStart2, frontEnd2);
                            if (moves.contains(blockThem2)){
                                return blockThem2;
                            }
                        }

                    }

                }

                else{ //If its player 3 or 4 a.k.a the shiller wall is horizontal
                    if (getPlayerLocation(myEnemy).getRow() > shillerwall.getEndRow()){ //If the enemy is in a row above the wall
                        if (this.getID() == 3){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()-1, getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol());
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                        if (this.getID() == 4){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow()-1, getPlayerLocation(myEnemy).getCol()+1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+1, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                    }
                    else{//The player is below the wall left align the wall
                        if (this.getID() == 3){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol());
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+2, getPlayerLocation(myEnemy).getCol());
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                        if (this.getID() == 4){
                            Coordinate frontStart = new Coordinate(getPlayerLocation(myEnemy).getRow(), getPlayerLocation(myEnemy).getCol()+1);
                            Coordinate frontEnd = new Coordinate(getPlayerLocation(myEnemy).getRow()+2, getPlayerLocation(myEnemy).getCol()+1);
                            PlayerMove blockThem = new PlayerMove(getID(),false,frontStart, frontEnd);
                            if (moves.contains(blockThem)){
                                return blockThem;
                            }
                        }
                    }

                }
            }
        }

        if (this.getID() == 1 && getPlayerLocation(myEnemy).equals(new Coordinate(7, 3))) {
            Coordinate enemySpot = getPlayerLocation(myEnemy);
            int h;
            for (h = 5; h <= 8; h++) {
                Coordinate enemyEnd = new Coordinate(8, h);
                List bemean = getShortestPath(enemySpot, enemyEnd);
                if (!bemean.isEmpty()){
                    PlayerMove mean = new PlayerMove(getID(),false,new Coordinate(7,4), new Coordinate(9, 4));
                    if(moves.contains(mean)){
                        return mean;
                    }
                }
            }
        }

        if (this.getID() == 2 && getPlayerLocation(myEnemy).equals( new Coordinate(1, 3))) {
            Coordinate enemySpot = getPlayerLocation(myEnemy);
            int h;
            for (h = 5; h <= 8; h++) {
                Coordinate enemyEnd = new Coordinate(0, h);
                List bemean = getShortestPath(enemySpot, enemyEnd);
                if (!bemean.isEmpty()){
                    PlayerMove mean = new PlayerMove(getID(),false,new Coordinate(0,4), new Coordinate(2, 4));
                    if(moves.contains(mean)){
                        return mean;
                    }
                }
            }
        }

        if (this.getID() == 1 && getPlayerLocation(myEnemy).getRow() >= 5){

            PlayerMove upBlock = new PlayerMove(getID(),false,new Coordinate(5,5), new Coordinate(7, 5));
            if (allPossibleMoves().contains(upBlock)){
                return upBlock;
            }

        }
        if (this.getID() == 2 && getPlayerLocation(myEnemy).getRow() <= 3){

            PlayerMove upBlock = new PlayerMove(getID(),false,new Coordinate(2,5), new Coordinate(4, 5));
            if (allPossibleMoves().contains(upBlock)){
                return upBlock;
            }

        }



        List<Coordinate> shortest = new ArrayList<Coordinate>();

        for(int x =0; x<100; x++){
            shortest.add(new Coordinate(0,0));

        }
        //for each of the coordinates that could be the action spot
        for(Coordinate x : myEndpoints){
            List<Coordinate> path = getShortestPath(getPlayerLocation(getID()), x);
            //if the path is shorter than the current shortest path
            if(path.size() <  shortest.size() && path.size()>0){
                PlayerMove nextmove = new PlayerMove(getID(), true, playerLocation.get(getID()), path.get(1));
                if(allPossibleMoves().contains(nextmove)) {
                    shortest = path;

                }
            }

        }
        //if all the shortest paths were invalid
        if (shortest.size() ==100){
            //go through all possible move
            Set<PlayerMove> all = getAllPieceMoves();
            for(PlayerMove x:all){
                //check for jumps

                    if(x.getStartCol() == x.getEndCol()+2 ||
                            x.getStartCol() == x.getEndCol()-2 ||
                            x.getStartRow() == x.getEndRow()+2  ||
                            x.getStartRow() == x.getEndRow()-2 ){
                        return x;
                    }

            }


        }



        PlayerMove nextmove = new PlayerMove(getID(), true, playerLocation.get(getID()), shortest.get(1));


        return nextmove;



    }

    /**
     * the 1-based player ID of this player
     * @return the 1-based player ID of this player
     */
    @Override
    public int getID() {
        return this.playerId;
    }

    /**
     * Returns the subset of the four adjacent cells to which a piece could move due to lack of walls
     * the system calls this to verify the implementation is correct
     * **its likely also handy for most strategy implementation
     * @param coordinate - the current location
     * @return a set of adjacent coordinates (up-down-left-right only) that are not blocked by walls
     */
    @Override
    public Set<Coordinate> getNeighbors(Coordinate coordinate) {
        Node c = new Node (coordinate.getRow(), coordinate.getCol());
        Set<Coordinate> re = new HashSet<Coordinate>();
        Set<Node> d= bo.getNeighbor(c);
        for(Node k : d){
            re.add(new Coordinate(k.row,k.col));
        }
        return re;
    }

    /**
     * BFS Search
     * the system calls this function only to verify that your implementation is correct
     * Use it to test your code
     * @param start -the start coordinate
     * @param finish - the end coordinate
     * @return any valid shortest path between two coordinates (if one exists)
     */
    @Override
    public List<Coordinate> getShortestPath(Coordinate start, Coordinate finish) {

        //just switching what they did
        //empty "queue" to put everything in
        List<Coordinate> dispenser = new LinkedList<Coordinate>();
        dispenser.add(start); //add the starting coordinate

        //construct the predecessors data
        Map <Coordinate, Coordinate> predecessors = new HashMap<Coordinate, Coordinate>();
        //put the starting node in, and just assign itself as predessor
        predecessors.put(start,start);

        //loop until either the finish node is found or the
        //dispenser is empty ( no path)
        while ( ! dispenser.isEmpty()){
            Coordinate current = dispenser.remove(0);
            if (current == finish){
                break;
            }
            //loop over all neighbors of current
            for(Coordinate tempp : getNeighbors(current)){
                //process unvisited neighbors
                if(!predecessors.containsKey(tempp)){
                    predecessors.put(tempp, current);
                    dispenser.add(tempp);
                }
            }
        }



        return constructPath(predecessors, start, finish);//if there is no good shortest path
    }

    /**
     * Helper function
     *
     * method to return a path from the starting to finishin coordinate
     * @param predecessor = map used to reconstruct the path
     * @param start = starting coordinate
     * @param finish = finishing coordinate
     * @return  a list containing the sequence of nodes comprising the path
     * empty if no path exists
     */
    public List<Coordinate> constructPath( Map<Coordinate,Coordinate> predecessor, Coordinate start, Coordinate finish){
        //use predecessor to work backwards from finish to start
        //all while dumping everything into a linked list
        List<Coordinate> path = new LinkedList<Coordinate>();

        if(predecessor.containsKey(finish)){
            Coordinate curCor = finish;
            while (curCor != start){
                path.add(0,curCor);
                curCor = predecessor.get(curCor);
            }
            path.add(0,start);
        }
        return path;
    }

    /**
     * get the remaining walls for your player
     * @param i - player id
     * @return num of walls
     */
    @Override
    public int getWallsRemaining(int i) {
        return walls.get(i);


    }

    /**
     * get the location of a given plaayer
     * @param i - 1-based player ID number
     * @return the location of a given player
     */
    @Override
    public Coordinate getPlayerLocation(int i) {
        return this.playerLocation.get(i);

    }

    /**
     * get the location of every player (1-based index)
     * @return a map representation of the location of every player
     */
    @Override
    public Map<Integer, Coordinate> getPlayerLocations() {
        return this.playerLocation;
    }

    /**
     * kkkkk
     *called by moves
     * get a set of all possible valid moves
     * @return set of PlayerMove ss
     */
    @Override
    public Set<PlayerMove> allPossibleMoves() {
        Set<PlayerMove> allPieceMoves = getAllPieceMoves();
        if(getWallsRemaining(getID()) >0) {
            allPieceMoves.addAll(getAllWallMoves());
        }
        //System.out.print("lll");
        return allPieceMoves;

    }

    /**
     *
     * Mainly for the person doing the piece moves
     * Generates and returns the set of all valid next piece
     * moves in the game.
     * @return a set of piece moves
     */
    private Set<PlayerMove> getAllPieceMoves(){
        //set that will contain all the VALID piece moves
        Set<PlayerMove> allMov = new HashSet<PlayerMove>();
        int curplayer = getID(); //the current player
        Coordinate curr = getPlayerLocation(curplayer); //the current player location
        Set<Coordinate> pos = getNeighbors(curr);  //gets the neighboring spots (doesnt check for the special case)
        Map<Integer,Coordinate> others = getPlayerLocations();
        Set<Coordinate> toAdd = new HashSet<Coordinate>();
        //get the other player locations and remove from possible locations
        //ADD in a jump to over someone
        for(int x =1; x <= playerLocation.size(); x++){
            //if there is a player blocking
            if(x!=curplayer&&pos.contains(others.get(x))){
                pos.remove(others.get(x));
                jump(curr, others.get(x), toAdd);
            }
        }

        pos.addAll(toAdd);
        for(Coordinate c :pos) {
            if(others.containsValue(c) ==false) {
                allMov.add(new PlayerMove(curplayer, true, curr, c));
            }
        }
        if(allMov.size()==0 && getWallsRemaining(curplayer)==0){
            allMov.add(new PlayerMove(curplayer, true, curr, curr));
        }
        return allMov;
    }

    /**
     * to see if you can jump over blocker
     * @param start- the current node's coordinate
     * @param blocker- the jump node's coordinate
     * @return the coordinate to the neighbor of blocker you jump to
     */
    private void jump(Coordinate start,Coordinate blocker, Set<Coordinate> pos){
        Coordinate temp;
        if(start.getCol() == blocker.getCol()){ //vertical jump
            if(start.getRow() > blocker.getRow()){ //jump up
                temp = new Coordinate(blocker.getRow()-1,blocker.getCol());
                if(getNeighbors(blocker).contains(temp)){
                    pos.add(temp);
                }//if there is all where you want to jump
                    temp = new Coordinate(blocker.getRow(), blocker.getCol()-1);//check left
                    if(getNeighbors(blocker).contains(temp)){//if the left is available to go to
                        pos.add(temp);
                    }
                    temp = new Coordinate(blocker.getRow(), blocker.getCol()+1);
                    if(getNeighbors(blocker).contains(temp)){
                        pos.add(temp);
                    }


            }else{ //jump down
                temp = new Coordinate(blocker.getRow()+1,blocker.getCol());
                if(getNeighbors(blocker).contains(temp)){
                    pos.add(temp);
                }else{
                    temp = new Coordinate(blocker.getRow(), blocker.getCol()-1);//check left
                    if(getNeighbors(blocker).contains(temp)){//if the left is available to go to
                        pos.add(temp);
                    }
                    temp = new Coordinate(blocker.getRow(), blocker.getCol()+1);
                    if(getNeighbors(blocker).contains(temp)){
                        pos.add(temp);
                    }
                }

            }

        }else{ //horizontal jump
            if(start.getCol() > blocker.getCol()){//start wants to go right to left
                temp = new Coordinate(blocker.getRow(), blocker.getCol()-1);
                if(getNeighbors(blocker).contains(temp)){
                    pos.add(temp);
                }else{
                    temp = new Coordinate(blocker.getRow()-1, blocker.getCol());//check left
                    if(getNeighbors(blocker).contains(temp)){//if the left is available to go to
                        pos.add(temp);
                    }
                    temp = new Coordinate(blocker.getRow()+1, blocker.getCol());
                    if(getNeighbors(blocker).contains(temp)){
                        pos.add(temp);
                    }
                }
            }else{ //left to right
                temp = new Coordinate(blocker.getRow(), blocker.getCol()+1);
                if(getNeighbors(blocker).contains(temp)){
                    pos.add(temp);
                }else{
                    temp = new Coordinate(blocker.getRow()-1, blocker.getCol());//check left
                    if(getNeighbors(blocker).contains(temp)){//if the left is available to go to
                        pos.add(temp);
                    }
                    temp = new Coordinate(blocker.getRow()+1, blocker.getCol());
                    if(getNeighbors(blocker).contains(temp)){
                        pos.add(temp);
                    }
                }
            }

        }

    }


    /**
     * Generates and returns the set of all valid next wall
     * moves in the game
     * @return set of wall moves
     */
    private Set<PlayerMove> getAllWallMoves(){


        //The set that will be used to return all valid wall moves
        Set<PlayerMove> validAllWall = new HashSet<PlayerMove>();


        for (Wall x:AllWall){ //For every wall in every possible wall placement, create the move and add it to the list

            PlayerMove curr = new PlayerMove(getID(), false, x.start, x.end);

            //If there are no walls on the board, populate with every valid move
            if (PlacedWalls.isEmpty()){
                validAllWall.add(curr);
            }
            else {

                //If there are walls on the board, begin weeding out through rules

                validAllWall.add(curr);

                for (Wall y : PlacedWalls) {


                    //Check if the wall already exists
                    if (y.getStart().equals(x.getStart()) && y.getEnd().equals(x.getEnd())) {
                        validAllWall.remove(curr);
                    }
                    //So long as the midpoints aren't the same
                    else if (y.getMidpoint().equals(x.getMidpoint())) {
                        validAllWall.remove(curr);

                    }
                    //Check for overlap
                    else if (x.getMidpoint().equals(y.getStart()) && x.getEnd().equals(y.getMidpoint())) {
                        validAllWall.remove(curr);

                    }
                    //Check for overlap
                    else if (x.getStart().equals(y.getMidpoint()) && x.getMidpoint().equals(y.getEnd())) {
                        validAllWall.remove(curr);

                    }


                }

/*
                Board testBoard = new Board(9);
                for (Wall q : PlacedWalls) {
                    Node here = new Node(q.getStart().getRow(), q.getStart().getCol());
                    Node there = new Node(q.getEnd().getRow(), q.getEnd().getCol());
                    testBoard.wallWentUp(here, there);
                }
                for (Node k : this.bo.board.keySet())
            }

            */

                int j;
                for (j = 1; j <= numPlayers; j++){ //For every player

                    Coordinate playerAt;
                    playerAt = getPlayerLocation(j); //Obtain their location
                    //Wall examine = new Wall(curr.getStart(), curr.getEnd());
                    //Node ex1 = new Node(curr.getStartRow(), curr.getStartCol());
                    //Node ex2 = new Node (curr.getEndRow(), curr.getEndCol());
                    //testBoard.wallWentUp(ex1, ex2);



                    if (getID() == 1){ //If its player 1
                        int counter;
                        for (counter = 0; counter <9; counter++){ //For every finish destination
                            Coordinate finish = new Coordinate(0, counter); //Generate the finish coordinate
                            List<Coordinate> path = getShortestPath(playerAt, finish); //Calculate the shortest path
                            if (!path.isEmpty()){               //If the path is every generated (AKA it finds one), break the loop.
                                break;                          //The wall cut no one off.
                            }
                            if (counter == 8){ //If its the last iteration and there is no path to be found
                                validAllWall.remove(curr);      //Remove this wall from the valid list as it would cut someone off
                            }
                        }

                    }
                    if (getID() == 2){
                        int counter;
                        for (counter = 0; counter <9; counter++){
                            Coordinate finish = new Coordinate(8, counter);
                            List<Coordinate> path = getShortestPath(playerAt, finish); //Calculate the shortest path
                            getShortestPath(playerAt, finish);
                            if (!path.isEmpty()){
                                break;
                            }
                            if (counter ==8){
                                validAllWall.remove(curr);
                            }
                        }
                    }
                    if (getID() == 3){
                        int counter;
                        for (counter = 0; counter <9; counter++){
                            Coordinate finish = new Coordinate(counter, 8);
                            List<Coordinate> path = getShortestPath(playerAt, finish); //Calculate the shortest path
                            getShortestPath(playerAt, finish);
                            if (!path.isEmpty()){
                                break;
                            }
                            if (counter ==8){
                                validAllWall.remove(curr);
                            }
                        }
                    }
                    if (getID() == 4){
                        int counter;
                        for (counter = 0; counter <9; counter++){
                            Coordinate finish = new Coordinate(counter, 0);
                            List<Coordinate> path = getShortestPath(playerAt, finish); //Calculate the shortest path
                            getShortestPath(playerAt, finish);
                            if (!path.isEmpty()){
                                break;
                            }
                            if (counter ==8){
                                validAllWall.remove(curr);
                            }
                        }
                    }

                   // bo.wallWentDown(startN, finishN);

                }

            }

        }




        return validAllWall;
    }
}
