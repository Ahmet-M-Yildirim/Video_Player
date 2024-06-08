module com.example.tutorialfx {
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires javafx.controls;


    opens com.example.tutorialfx to javafx.fxml;
    exports com.example.tutorialfx;
    exports tutorialfx;
    opens tutorialfx to javafx.fxml;

}