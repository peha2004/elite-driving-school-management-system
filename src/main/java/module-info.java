module org.example.elite_driving_school_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires static lombok;

    requires java.base;
    requires java.naming;
    requires bcrypt;

    opens org.example.elite_driving_school_management_system.entity to org.hibernate.orm.core, javafx.base;

    opens org.example.elite_driving_school_management_system.controller to javafx.fxml;
    opens org.example.elite_driving_school_management_system.dto to javafx.base;

    exports org.example.elite_driving_school_management_system;
}