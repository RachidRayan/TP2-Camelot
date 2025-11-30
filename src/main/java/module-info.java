module ca.qc.bdeb.sim.tp2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ca.qc.bdeb.sim.tp2 to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2;
    exports ca.qc.bdeb.sim.tp2.generation;
    opens ca.qc.bdeb.sim.tp2.generation to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2.gameEngine;
    opens ca.qc.bdeb.sim.tp2.gameEngine to javafx.fxml;
}