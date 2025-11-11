module cs.qc.bdeb.sim.tp2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens cs.qc.bdeb.sim.tp2 to javafx.fxml;
    exports cs.qc.bdeb.sim.tp2;
    exports ca.qc.bdeb.sim.tp2;
    opens ca.qc.bdeb.sim.tp2 to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2.background;
    opens ca.qc.bdeb.sim.tp2.background to javafx.fxml;
}