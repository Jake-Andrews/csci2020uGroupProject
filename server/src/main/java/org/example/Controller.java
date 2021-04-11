package org.example;

import javafx.fxml.FXML;

public class Controller {
    @FXML
    public void initialize() {
        BlackjackServer server = new BlackjackServer();
        server.start();

    }
}
