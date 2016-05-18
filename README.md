# Quoridor
Dijkstra's algorithm, pruning, minimal AI
In this project, I initlized the Board (Board.java) and worked in the Dominos player file (DOMINOS.java) 
The biggest obstacle was maintaing a data structuer of valid player moves and searching said data structure for the "optimal"
move. To understand the "Shiller strategy" I implemented for wall placement, check out:

https://quoridorstrats.wordpress.com/2014/09/25/openings-the-shiller/

The basic idea is that wall placement is intended to force the other player to commit to a path easily blocked off. In addition to creating
this wall-placement strategy, I was also in control of finding the shortest path. If I were to redo this project I'd replace the monolithic walls
of condotionals with design patterns to maximize code reuse.

The QuoridorClient is the main class to run and the well documented configuration file can let you toggle things like the GUI and 
the number of games to play.
