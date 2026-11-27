module cl.adumun.academy.timecapsule {
    requires javafx.controls;
    requires javafx.fxml;


    opens cl.adumun.academy.timecapsule to javafx.fxml;
    exports cl.adumun.academy.timecapsule;
}