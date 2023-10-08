module bader.ode5_client {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens bader.ode5_client to javafx.fxml;
    exports bader.ode5_client;
}