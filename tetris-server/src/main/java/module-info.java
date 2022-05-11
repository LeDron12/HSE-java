module hw.tetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;

    exports hw.tetris.server;
    opens hw.tetris.server to javafx.fxml;
    exports hw.tetris.game;
    opens hw.tetris.game to javafx.fxml;
    exports hw.tetris.client;
    opens hw.tetris.client to javafx.fxml;
}