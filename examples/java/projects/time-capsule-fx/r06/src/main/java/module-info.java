module cl.adumun.academy.timecapsule {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens cl.adumun.academy.timecapsule to javafx.fxml;
    opens cl.adumun.academy.timecapsule.model to com.fasterxml.jackson.databind;

    exports cl.adumun.academy.timecapsule;
}
