package org.example;

import javafx.fxml.FXML;

public class Controller {
    @FXML
    public void initialize() {
        BlackjackServer server = new BlackjackServer();
        server.start();
    }
}

//Designed for 2 players.
//Server waits to recieve two messages "READY".
//Once recieved, the server sends "START".

//Need a failsafe, so if one player hits ready and the other player has not hit ready yet.
//But, the player who alreayd has pressed ready presses hit, nothing is generated.
//Both players have to be ready in order for the game to start.

//Player presses ready.
//Server