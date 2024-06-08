package com.example.tutorialfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class VideoPlayerController implements Initializable{

    @FXML
    private MenuBar Menubar;
    @FXML
    private Menu FileMenu;
    @FXML
    private MenuItem OpenItem,AboutItem;
    @FXML
    private Menu EditMenu;
    @FXML
    private Menu HelpMenu;
    @FXML
    private Slider SliderTime;
    @FXML
    private HBox HBoxController;
    @FXML
    private Button PlaypauseButton;
    @FXML
    private Label VolumeLabel;
    @FXML
    private Slider VolumeSlider;
    @FXML
    private Label LabelCurrentTime;
    @FXML
    private Label LabelVideoTime;
    @FXML
    private Label labelFullScreen;
    @FXML
    private MediaView mpVideo;
    @FXML
    HBox HBoxVolume;
    @FXML
    HBox HBoxFull;
    @FXML
    MenuItem CloseItem;
    @FXML
    ComboBox<String> SpeedBox;

    @FXML
    ToggleButton LoopButton;
    @FXML
    private MenuItem subtitleItem;
    @FXML
    private VBox subtitlesContainer;
    @FXML
    private MenuItem settingsItem;
    @FXML
    private Button back10Button;
    @FXML
    private  Button next10Button;
    @FXML
    private VBox VboxController;
    @FXML
    private MenuItem propItem;




    private Media media;

    private File selectedFile;
    private File selectedSubtitle;

    private MediaPlayer mediaplayer;

    private ImageView play;
    private ImageView pause;
    private ImageView fullscreen;
    private ImageView muteIcon;
    private ImageView VolumeIcon;
    private ImageView info;
    private ImageView OutFullScreen;
    private ImageView loopImage;
    private ImageView nextImage;
    private ImageView backImage;
    private ImageView level1Volume;
    private ImageView level3Volume;
    private List<Subtitle> subtitles;
    private int currentSubtitleIndex;


    private boolean isPlaying = false;
    private boolean isMuted = true;
    private boolean atEndofVideo = true;
    private boolean isAtEndofVideo_2 = true;
    private boolean isFullScreen = false;
    private boolean isLoopEnabled = false;
    private boolean isMenuBarVisible = true;
    private ObservableList<String> list = FXCollections.observableArrayList("0.25X","0.5X","0.75X","1X","1.25X","1.5X","1.75X","2X");

    private Timeline sliderUpdateTimeLine;


    public Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

            final int V_SİZE = 25;

            FileChooser file = new FileChooser();
            file.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 Filse","*.mp4"));
            selectedFile = file.showOpenDialog(null);
            if(selectedFile!=null){
                media = new Media(selectedFile.toURI().toString());
                mediaplayer = new MediaPlayer(media);
                mpVideo.setMediaPlayer(mediaplayer);
            }


            Image ımagePlay = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\play-button.png").toURI().toString());
            play = new ImageView(ımagePlay);
            play.setFitHeight(V_SİZE);
            play.setFitWidth(V_SİZE);
            PlaypauseButton.setGraphic(play);

            Image ımagePause = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\video-pause-button.png").toURI().toString());
            pause = new ImageView(ımagePause);
            pause.setFitWidth(V_SİZE);
            pause.setFitHeight(V_SİZE);

            Image ımageFull = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\FullScreen.png").toURI().toString());
            fullscreen = new ImageView(ımageFull);
            fullscreen.setFitWidth(25);
            fullscreen.setFitHeight(25);
            labelFullScreen.setGraphic(fullscreen);

            Image imageOut= new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\outFull.png").toURI().toString());
            OutFullScreen = new ImageView(imageOut);
            OutFullScreen.setFitHeight(25);
            OutFullScreen.setFitWidth(25);

            Image infoImage = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\info.png").toURI().toString());
            info = new ImageView(infoImage);
            info.setFitWidth(V_SİZE);
            info.setFitHeight(V_SİZE);

            Image ımageLoop = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\loop.png").toURI().toString());
            loopImage = new ImageView(ımageLoop);
            loopImage.setFitWidth(V_SİZE);
            loopImage.setFitHeight(V_SİZE);
            LoopButton.setGraphic(loopImage);

            Image ımageNext = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\next (1).png").toURI().toString());
            nextImage = new ImageView(ımageNext);
            nextImage.setFitHeight(25);
            nextImage.setFitWidth(25);
            next10Button.setGraphic(nextImage);

            Image ımageBack = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\back (1).png").toURI().toString());
            backImage = new ImageView(ımageBack);
            backImage.setFitHeight(25);
            backImage.setFitWidth(25);
            back10Button.setGraphic(backImage);

            Image ımageLevel1 = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\volume-level.png").toURI().toString());
            level1Volume = new ImageView(ımageLevel1);
            level1Volume.setFitHeight(25);
            level1Volume.setFitWidth(25);

            Image ımageLevel3 = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\volume-level (1).png").toURI().toString());
            level3Volume = new ImageView(ımageLevel3);
            level3Volume.setFitWidth(25);
            level3Volume.setFitHeight(25);


            updatePlayPauseIcon();
            loadSubtitle();

            mediaplayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                updateSubtitles(newValue.toSeconds());
            });

        mpVideo.setPreserveRatio(false);


        Tooltip tooltip = new Tooltip("%" + (int) VolumeSlider.getValue());
        Tooltip.install(VolumeSlider,tooltip);




            SpeedBox.getItems().addAll(list);
            CloseItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING!!");
                    alert.setHeaderText("This is the warning message");
                    alert.setContentText("Are you sure you want to quit the app?");
                    ButtonType okButton = new ButtonType("OK");
                    ButtonType closeButton = new ButtonType("CLOSE");
                    alert.getButtonTypes().setAll(okButton,closeButton);
                    alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                        @Override
                        public void accept(ButtonType buttonType) {
                            if(buttonType==okButton){
                                System.exit(0);
                            }
                            else{
                                alert.close();
                            }
                        }
                    });

                }
            });
            AboutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("About the App");
                alert.setHeaderText("Media Player App");
                alert.setContentText("You can play any .mp4 extension files you want with the media player.");

                ButtonType Close = new ButtonType("CLOSE");
                alert.getButtonTypes().setAll(Close);
                alert.setGraphic(info);
                alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        if(buttonType==Close){
                            alert.close();
                        }
                    }
                });
            }
        });
            OpenItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 Files","*.mp4"));
                    selectedFile = file.showOpenDialog(null);
                    if(selectedFile!=null){
                        media = new Media(selectedFile.toURI().toString());
                        mediaplayer = new MediaPlayer(media);
                        mpVideo.setMediaPlayer(mediaplayer);
                    }
                }
            });

            subtitleItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser subtitleChooser = new FileChooser();
                    subtitleChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Subtitle Files" , "*.srt"));
                    selectedSubtitle = subtitleChooser.showOpenDialog(primaryStage);
                    if(selectedSubtitle!=null){

                    }
                }
            });

        settingsItem.setOnAction(event -> {
            newWindow(primaryStage);
        });

        propItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                propWindow(primaryStage);
            }
        });



        LoopButton.setOnAction(event -> {
            isLoopEnabled = !isLoopEnabled;

            if (isLoopEnabled) {
                mediaplayer.setOnEndOfMedia(() -> {
                    if (isPlaying) {
                        mediaplayer.seek(Duration.ZERO);
                        PlaypauseButton.setGraphic(pause);
                        mediaplayer.play();
                    }
                    else{
                        PlaypauseButton.setGraphic(play);
                    }
                });
            } else {
                mediaplayer.setOnEndOfMedia(null);
            }
        });


            Image ımageMute = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\mute.png").toURI().toString());
            muteIcon = new ImageView(ımageMute);
            muteIcon.setFitWidth(25);
            muteIcon.setFitHeight(25);
            VolumeLabel.setGraphic(muteIcon);

            Image ımageVolume = new Image(new File("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\Volume.png").toURI().toString());
            VolumeIcon = new ImageView(ımageVolume);
            VolumeIcon.setFitWidth(25);
            VolumeIcon.setFitHeight(25);


            //HBoxVolume.getChildren().remove(VolumeSlider);
            mediaplayer.volumeProperty().bindBidirectional(VolumeSlider.valueProperty());

            mediaplayer.volumeProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                        mediaplayer.setVolume(VolumeSlider.getValue());
                        if(VolumeSlider.getValue()==0.0){
                            VolumeLabel.setGraphic(muteIcon);
                            isMuted = true;
                        }
                        else if(VolumeSlider.getValue()<0.35){
                            VolumeLabel.setGraphic(level1Volume);
                            isMuted = false;
                        }
                        else if (VolumeSlider.getValue()<0.7&&VolumeSlider.getValue()>0.35) {
                            VolumeLabel.setGraphic(VolumeIcon);
                            isMuted = false;
                        }
                        else if(VolumeSlider.getValue()>0.7&&VolumeSlider.getValue()<=1){
                            VolumeLabel.setGraphic(level3Volume);
                            isMuted = false;
                       }
                }
            });

            VolumeLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                   if(VolumeSlider.getValue()!=0.0){
                       VolumeLabel.setGraphic(muteIcon);
                       VolumeSlider.setValue(0);
                   }
                   else{
                       VolumeLabel.setGraphic(VolumeIcon);
                       VolumeSlider.setValue(0.15);
                   }
                }
            });

            VolumeSlider.setValue(mediaplayer.getVolume()*100);
            VolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaplayer.setVolume(newValue.doubleValue());

            tooltip.setText(String.format("%.0f%%", newValue.doubleValue() * 100));
            });

        VolumeSlider.setOnMouseEntered(event -> {
                tooltip.show(VolumeSlider , event.getScreenX() , event.getScreenY() +10);
                });

        VolumeSlider.setOnDragExited(event -> {
            tooltip.hide();
        });

            mpVideo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(mediaplayer.getStatus() == MediaPlayer.Status.PLAYING){
                        mediaplayer.pause();
                        PlaypauseButton.setGraphic(play);
                    }
                    else{
                        mediaplayer.play();
                        PlaypauseButton.setGraphic(pause);
                    }
                }
            });


            SpeedBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selectedValue = SpeedBox.getValue();

                        if("0.25X".equals(selectedValue)){
                            mediaplayer.setRate(0.25);
                        }
                        else if("0.5X".equals(selectedValue)){
                        mediaplayer.setRate(0.5);
                        }
                        else if("0.75X".equals(selectedValue)){
                            mediaplayer.setRate(0.75);
                        }
                        else if("1X".equals(selectedValue)){
                            mediaplayer.setRate(1);
                        }else if("1.25X".equals(selectedValue)){
                            mediaplayer.setRate(1.25);
                        }
                        else if("1.5X".equals(selectedValue)){
                            mediaplayer.setRate(1.5);
                        }
                        else if("1.75X".equals(selectedValue)){
                            mediaplayer.setRate(1.75);
                        }
                        else{
                            mediaplayer.setRate(2);
                        }
                }
            });

            bindCurrentTimeLabel();
        PlaypauseButton.setOnAction(event -> {
            if (atEndofVideo) {
                SliderTime.setValue(0);
                atEndofVideo = false;
                isPlaying = false;
            }

            if (isPlaying) {
                mediaplayer.pause();
            } else {
                mediaplayer.play();
            }

            isPlaying = !isPlaying;
            updatePlayPauseIcon();
        });

        mediaplayer.setOnEndOfMedia(() -> {
            atEndofVideo = true;
            if (!isLoopEnabled) {
                Platform.runLater(() -> PlaypauseButton.setGraphic(play));
                isPlaying = false;
            }
        });

        next10Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Duration currentTime = mediaplayer.getCurrentTime();
                Duration newTime = currentTime.add(Duration.seconds(10));
                mediaplayer.seek(newTime);
            }
        });

        back10Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Duration currentTime  = mediaplayer.getCurrentTime();
                Duration newTime = currentTime.subtract(Duration.seconds(10));
                mediaplayer.seek(newTime);
            }
        });

            VolumeLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(HBoxVolume.lookup("#VolumeSlider")==null){
                        HBoxVolume.getChildren().add(VolumeSlider);

                    }
                }
            });
            VolumeLabel.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if(HBoxVolume.lookup("#VolumeSlider")==null){
                        HBoxVolume.getChildren().remove(VolumeSlider);
                    }
                }
            });

            labelFullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Label label = (Label) event.getSource();
                    Stage stage = (Stage) label.getScene().getWindow();
                    if(stage.isFullScreen()){
                        stage.setFullScreen(false);
                        labelFullScreen.setGraphic(fullscreen);
                        WindowSize();

                    }
                    else{
                        stage.setFullScreen(true);
                        labelFullScreen.setGraphic(OutFullScreen);
                        fullScreenSize();
                    }
                }
            });

            mediaplayer.totalDurationProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    SliderTime.setMax(newValue.toSeconds());
                    LabelVideoTime.setText(getTime(newValue));
                }
            });
            SliderTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(!newValue){
                        mediaplayer.seek(Duration.seconds(SliderTime.getValue()));
                    }
                }
            });
            SliderTime.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    double CurrentTime = mediaplayer.getCurrentTime().toSeconds();
                    if(Math.abs(CurrentTime-newValue.doubleValue())>0.5){
                        mediaplayer.seek(Duration.seconds(newValue.doubleValue()));
                    }
                    endVideo(LabelCurrentTime.getText(),LabelVideoTime.getText());
                }
            });

            mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    if(SliderTime.isValueChanging()){
                        SliderTime.setValue(newValue.toSeconds());
                    }
                    endVideo(LabelCurrentTime.getText(),LabelVideoTime.getText());
                }
            });

            mediaplayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    atEndofVideo = true;
                    if(!LabelCurrentTime.textProperty().equals(LabelVideoTime.textProperty())){
                        LabelCurrentTime.textProperty().unbind();
                        LabelCurrentTime.setText(getTime(mediaplayer.getTotalDuration())+" /"); //Sıkıntı Var Çöz
                    }
                }
            });
            sliderUpdateTimeLine = new Timeline(
                    new KeyFrame(Duration.seconds(0.1), event -> updateSlider()));
            sliderUpdateTimeLine.setCycleCount(Timeline.INDEFINITE);
            sliderUpdateTimeLine.play();

        }

    private void endVideo(String currentTime, String VideoTime) {
        for(int i = 0;i<VideoTime.length();i++){
            if(currentTime.charAt(i)!=VideoTime.charAt(i)){
                atEndofVideo= false;
                if(atEndofVideo){
                    PlaypauseButton.setGraphic(play);
                }
                else{
                    PlaypauseButton.setGraphic(pause);
                }
                break;
            }
        }
    }

    private void bindCurrentTimeLabel() {

        LabelCurrentTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mediaplayer.getCurrentTime())+"/";
            }
        },mediaplayer.currentTimeProperty()));
    }

    public String getTime(Duration currentTime) {
        int hours = (int) currentTime.toHours();
        int minutes = (int) (currentTime.toMinutes() % 60);
        int seconds = (int) (currentTime.toSeconds() % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    public void fullScreenSize(){
        mpVideo.setFitHeight(975);
        mpVideo.setFitWidth(1920);
    }
    public void WindowSize(){
        mpVideo.setFitWidth(857);
        mpVideo.setFitHeight(375);
    }
    public void updateSlider() {
        SliderTime.setValue(mediaplayer.getCurrentTime().toSeconds());
    }
    public void updatePlayPauseIcon() {
        if (isPlaying) {
            PlaypauseButton.setGraphic(pause);
        } else {
            PlaypauseButton.setGraphic(play);
        }
    }

    public void loadSubtitle(){
        subtitles = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(selectedSubtitle, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String timeLine = scanner.nextLine();
                String textLine = scanner.nextLine();

                String[] timeParts = timeLine.split(" --> ");
                if (timeParts.length == 2) {
                    String startTime = timeParts[0];
                    String endTime = timeParts[1];

                    subtitles.add(new Subtitle(startTime, endTime, textLine));
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateSubtitles(double currentTime) {
        for (int i = currentSubtitleIndex; i < subtitles.size(); i++) {
            Subtitle subtitle = subtitles.get(i);
            if (subtitle.inRange(currentTime)) {

                showSubtitle(subtitle.getText());
                currentSubtitleIndex = i;
                break;
            }
        }
    }

    public void showSubtitle(String text){
        Text subtitleText = new Text(text);
        subtitlesContainer.getChildren().setAll(subtitleText);
    }

    class Subtitle{
        double startTime;
        double endTime;
        String text;

        public Subtitle(String startTime, String endTime, String text) {
            this.startTime = parseTime(startTime);
            this.endTime = parseTime(endTime);
            this.text = text;
        }

        public String getText(){
            return text;
        }
        public boolean inRange(double time){
            return time>=startTime && time<=endTime;
        }
        public double parseTime(String time){
            String[] parts = time.split(":");
            if (parts.length == 3) {
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                String[] secondsParts = parts[2].split(",");
                int seconds = Integer.parseInt(secondsParts[0]);
                int milliseconds = Integer.parseInt(secondsParts[1]);
                return hours * 3600 + minutes * 60 + seconds + milliseconds / 1000.0;
            }
            return 0.0;
        }
    }
    public void newWindow(Window parentWindow){
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentWindow);
        stage.setTitle("Video Settings");
        stage.setResizable(false);


        VBox vbox = new VBox(10);
        Scene scene = new Scene(vbox,400,300);

        Label brigthnessLabel = new Label("Brightnes:");


        Label contrastLabel = new Label("Contrast:");

        Label saturationLabel = new Label("Saturation: ");


        Slider brigthnesslider = new Slider(-1,1,0);

        Slider ContrastSlider = new Slider(-1,1,0);

        Slider saturatipnSlider = new Slider(-1,1,0);

        Label valueBrigthnes = new Label("Değer: " + (int)brigthnesslider.getValue());

        ColorAdjust colorAdjust = new ColorAdjust();
        brigthnesslider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueBrigthnes.setText("Değer: " + (int) newValue.doubleValue());
            colorAdjust.setBrightness(newValue.doubleValue());
            mpVideo.setEffect(colorAdjust);
        });



        Label valueOpposition = new Label("Değer: " + (int)ContrastSlider.getValue());

        ContrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueOpposition.setText("Değer: " + (int) newValue.doubleValue());
            colorAdjust.setContrast(newValue.doubleValue());
            mpVideo.setEffect(colorAdjust);

        });

        Label valueSaturation = new Label("Değer: " + (int)saturatipnSlider.getValue());

        saturatipnSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueSaturation.setText("Değer: " + (int) newValue.doubleValue());
            colorAdjust.setSaturation(newValue.doubleValue());
            mpVideo.setEffect(colorAdjust);

        });


        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ContrastSlider.setValue(0);
                brigthnesslider.setValue(0);
                saturatipnSlider.setValue(0);
            }
        });

        stage.getIcons().add(new Image("C:\\Users\\ahmet\\IdeaProjects\\TutorialFX\\src\\main\\resources\\kaynaklar\\settings.png"));



       //stage.getIcons().add(new Image(getClass().getResourceAsStream("kaynaklar/settings.png")));


        vbox.getChildren().addAll(brigthnessLabel,brigthnesslider,valueBrigthnes);
        vbox.getChildren().addAll();
        vbox.getChildren().addAll(contrastLabel,ContrastSlider,valueOpposition);
        vbox.getChildren().addAll(saturationLabel,saturatipnSlider,valueSaturation);
        vbox.getChildren().addAll(reset);
        stage.setScene(scene);
        stage.show();

    }

   public void voiceWindow(Window parent){
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parent);
        stage.setTitle("Equalizer");
        stage.setResizable(false);

       Pane root = new Pane();
        Scene scene = new Scene(root,400,250);



        stage.setScene(scene);
        stage.show();
    }

    public void propWindow(Window parent) {
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setTitle("Properties");
            stage.setResizable(false);

            Pane root = new Pane();
            Scene scene = new Scene(root,508,288);


            Label titleLabel = new Label("Title");
            Label titleLabelText = new Label();
            Label propLabel = new Label("Properties");
            Label subTitle = new Label("Subtitle");
            Label subTextTitle = new Label();
            Label resLabel = new Label("Resolution");
            Label resTextLabel = new Label();
            Label fileType = new Label("File Type");
            Label fileTypeText = new Label();
            Label filePath = new Label("File Path");
            Label filePathText = new Label();

            Font font = Font.font("System" , FontWeight.BOLD,16);
            String s = selectedFile.getPath();

            propLabel.setLayoutX(31);
            propLabel.setLayoutY(22);
            propLabel.setPrefSize(125,27);
            propLabel.setFont(new Font(20));

            titleLabel.setLayoutX(37);
            titleLabel.setLayoutY(57);
            titleLabel.setPrefSize(123,14);
            titleLabel.setFont(font);

            titleLabelText.setLayoutX(37);
            titleLabelText.setLayoutY(84);
            titleLabelText.setPrefSize(123,27);
            titleLabelText.setText(selectedFile.getName());

            subTitle.setLayoutX(300);
            subTitle.setLayoutY(57);
            subTitle.setPrefSize(123,37);
            subTitle.setFont(font);

            subTextTitle.setLayoutX(300);
            subTextTitle.setLayoutY(84);
            subTextTitle.setPrefSize(123,37);

            if(selectedSubtitle!=null){
                subTextTitle.setText(selectedSubtitle.getName());
            }
            else{
                subTextTitle.setText("");
            }

            resLabel.setLayoutX(37);
            resLabel.setLayoutY(133);
            resLabel.setPrefSize(100,17);
            resLabel.setFont(font);

            resTextLabel.setLayoutX(37);
            resTextLabel.setLayoutY(154);
            resTextLabel.setPrefSize(123,37);
            resTextLabel.setText(mpVideo.getFitWidth() +" x " + mpVideo.getFitHeight());

            fileType.setLayoutX(300);
            fileType.setLayoutY(133);
            fileType.setPrefSize(100,17);
            fileType.setFont(font);

            fileTypeText.setLayoutX(300);
            fileTypeText.setLayoutY(150);
            fileTypeText.setPrefSize(123,17);
            fileTypeText.setText(s.substring(s.length()-4));

            filePath.setLayoutX(37);
            filePath.setLayoutY(208);
            filePath.setPrefSize(100,17);
            filePath.setFont(font);

            filePathText.setLayoutX(32);
            filePathText.setLayoutY(237);
            filePathText.setPrefSize(441,37);
            filePathText.setText(selectedFile.getAbsolutePath());
            filePathText.setOnMouseClicked(event -> {
                File file = new File(selectedFile.getPath());

                if(Desktop.isDesktopSupported() && file.exists()){
                    try{
                        Desktop.getDesktop().open(file.getParentFile());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            root.getChildren().addAll(titleLabelText,titleLabel,propLabel,
                    subTextTitle, subTitle,resTextLabel,resLabel , fileType,fileTypeText ,filePath,filePathText);

            stage.setScene(scene);
            stage.show();



    }




}

