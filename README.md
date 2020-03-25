# **Scrabble** - Software Engineering II COMP20050
## Group: 'gooses' Conor Knowles, Finbar Ó Deaghaidh, Peter O'Donnell


# Assignment 1
## Approach:
1. Drew out Sprint Plan in diagram to get a clear picture of how the Classes interact.
2. Defined test plan
3. Allocated jobs to each component (Scrum Master for Assignment 1: Finbar Ó Deaghaidh).
4. Implemented tests.
5. Implement components.
6. Tested program.
7. Code review, refactor and refining.
8. Submit executable 1.

## Executable Instructions:
1. Launch .jar file (java -jar scrabble-assignment1.jar)
2. Enter name
  * Can be First name & Second name, A-Z a-z characters allowed only.
3. Program prints out player name and frame, which is filled with random tiles from pool.

## Class Diagram:
![assignment_one_diagram](https://github.com/UCD-COMP20050/gooses/blob/master/images/assignment_one_diagram.png)


# Assignment 2
## Approach:
1. Appointed new Scrum Master for sprint (Peter O'Donnell) and allocated tasks.
2. Implemented unit testing methods for all classes before writing the classes themselves.
3. Implemented components with multiple iterations and improvements after reviewing.
4. Introduced any missing unit tests that were required.
5. Wrote Main class to take user input and to give a visual representation of our implementation.
6. Created execuatable Jar and submitted executable 2.

## Executable Instructions:
1. Launch .jar file (java -jar scrabble-assignment2.jar)
2. Enter name, pick tile from frame. Places tile in centre of board.
3. Pick tile to move again, pick whether you wish to move vertically or horizontally. This limits your moves to the direction chosen relative to the axis. Pick the coordinates in that direction for your respective tile.
4. Continue with placing tiles until your frame is empty, turns aren't implemented as of yet.
5. After each move the updated board will be displayed along with the frame.

## Class Diagram:
![assignment_two_diagram](https://github.com/UCD-COMP20050/gooses/blob/master/images/assignment_two_diagram.PNG)


# Assignment 3
## Approach:
1. Assigned a new Scrum Master (Conor Knowles) for the assignment and designated tasks to team members.
2. Implemented Scrabble and Move Classes which handle 2 human players playing a game and scoring.
3. Created a UI in AWT at first but then rewrote to use JavaFX to avoid future incompatabilities with the bot competition.
4. Rewrote Main class to run the UI class and create a game instance, not taking user input itself.
5. Tested all Classes and Methods introduced in this Sprint.
6. Created execuatable Jar and submitted executable 3.

## Executable Instructions:
1. Launch .jar file (java -jar scrabble-assignment3.jar)
2. Enter names of both players.
3. Console wil now display the command format for placing tiles and all other options such as "help","exit" and "undo"
4. Continue with placing tiles game has ended, or you exit yourself.
5. After each move the updated board will be displayed along with the frame of the player who's move it is and their respective score.

## Class Diagram:
//TODO






