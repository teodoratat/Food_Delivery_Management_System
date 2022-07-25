module com.teo.cateringteo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teo.cateringteo to javafx.fxml;
    exports com.teo.cateringteo;

    opens com.teo.cateringteo.controller to javafx.fxml;
    exports com.teo.cateringteo.controller;
    exports com.teo.cateringteo.BussinessLogic;
    opens com.teo.cateringteo.BussinessLogic to javafx.fxml;
}