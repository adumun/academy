module cl.adumun.academy.timecapsule {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;

    opens cl.adumun.academy.timecapsule to javafx.fxml;
    exports cl.adumun.academy.timecapsule;
}
