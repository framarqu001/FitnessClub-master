module fitnessclubgui.fitnessclub {
    requires javafx.controls;
    requires javafx.fxml;


    opens fitnessclubgui.fitnessclub to javafx.fxml;
    exports fitnessclubgui.fitnessclub;
}