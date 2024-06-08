package com.example.tutorialfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("VideoPlayer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 857, 472);
        VideoPlayerController controller = fxmlLoader.getController();
        //VideoPlayerController videoPlayerController = new VideoPlayerController();
        controller.setPrimaryStage(stage);

        scene.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.F11){
                if(stage.isFullScreen()==false){
                    controller.fullScreenSize();
                    stage.setFullScreen(true);
                }
                else{
                    controller.WindowSize();
                    stage.setFullScreen(false);
                }
            }
        });

        stage.getIcons().add(new Image("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\windows-media-player.png"));

        stage.setTitle("Video Player");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}