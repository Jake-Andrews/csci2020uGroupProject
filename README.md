# csci2020uGroupProject

1. Project Information 
Scope of the project - Create a blackjack game that two players can play. 
Rules - If the dealer and the player have the same score, the dealer wins. The dealer and player can only hit 3 times. 
This project is based on a 2 player Blackjack game that incorporates a multi-threaded server with a client connection having a individual UI for each player.  This UI contains several key parts of the game including a complete visual display of the players cards and a HIT and STAND Button.  BOTH players must click the READY button as well to start the game,  The STAND button shares a similar feature where it will wait for the other player to also STAND before moving onto the next state of the game.  Each player will be dealt a card as well as the dealer, you are able to see the dealers cards from each players individual UI.  The Dealers cards are displayed at the top of the screen while the players cards are on the bottom. As standard blackjack rules go Once your card count = 21 you WIN! (It is still possible to go over 21 and lose). Warning, the server must be restarted at the end of each game if you wish to play again.

![Alt Text](src/resources/window.png)

2. How to run. 
Git clone the repository. 
Open the Client and Server folders separately. Open another client folder so you can run two clients. 

First, run the server. Then, run both clients. 
The server and clients can be run from the command line using gradle run or through a IDE that has support for gradle, IntelliJ was used in the creation of this project. 

3. Other resources:
Refrenced TA Michael Valdron's lab 10 demo code for the server. 

Need to add. Pictures. Maybe link to a google drive with the demo?
Add zip file with project. 
