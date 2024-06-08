package tutorialfx;

import com.example.tutorialfx.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertiesController{

    @FXML
    Label titleLabel;
    @FXML
    Label titleTextLabel;
    @FXML
    Label subtitleLabel;
    @FXML
    Label subTextLabel;
    @FXML
    Label resLabel;
    @FXML
    Label resTextLabel;
    @FXML
    Label filePathTextLabel;
    @FXML
    Label filePathLabel;
    @FXML
    Label fileLabel;
    @FXML
    Label fileTextLabel;
    @FXML
    Label propLabel;

    public Stage stage;

    public void setStage(Stage stage){
        this.stage = null;

    }



}
