package presenter;
import controllers.mediaController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import controllers.contentDistribution;
import javafx.stage.StageStyle;
import model.Place;
import sun.misc.Launcher;

/**
 * Presenter to manage multiple display application
 * @author John Falcon
 *
 */
public class displayPresenter {

    double widthPrimary;
    double widthSecondary;
    double heightPrimary;
    double heightSecondary;

    private Screen primaryScreen;
    private Screen secondaryScreen;
    Rectangle2D primaryScreenBounds;
    Rectangle2D secondaryScreenBounds;

    //final String resources = "file:///C://Puebla/";
    final String resources = "file:///Users/johm_tdc/Puebla/";

    public displayPresenter(){
        this.detectDisplays();
    }

    /**
     * Call stage to specific display
     * @param String displayName
     * @param Stage stage
     * @param Screen targetScreen
     * @return void
     */
    public static void callStageToDisplay( final String displayName, final Stage stage, final Screen targetScreen ){


    }

    /**
     * Detect the number and dimensions of displays connected to the device
     * @return void
     */
    public void detectDisplays(){
        final Screen primaryScreen = Screen.getPrimary();
        final List<Screen> allScreens = Screen.getScreens();

        this.primaryScreen = primaryScreen;
        if (allScreens.size() <= 1) {
            System.out.println("DisplayPresenter ::: Only one screen detected, using the same output");
            this.primaryScreen      = primaryScreen;
            this.secondaryScreen    = primaryScreen;
        } else {
            System.out.println("DisplayPresenter ::: Both screens working");
            if (allScreens.get(0).equals(primaryScreen)) {
                this.secondaryScreen = allScreens.get(1);
            } else {
                this.secondaryScreen = allScreens.get(0);
            }
        }
        this.primaryScreenBounds     = this.primaryScreen.getVisualBounds();
        this.secondaryScreenBounds   = this.secondaryScreen.getVisualBounds();

        /* Set Viewport window dimensions */
        widthPrimary  = this.primaryScreenBounds.getWidth();
        heightPrimary = this.primaryScreenBounds.getHeight();

        /* Set Controller window dimensions */
        widthSecondary  = this.secondaryScreenBounds.getWidth();
        heightSecondary = this.secondaryScreenBounds.getHeight();
    }

    /**
     * Insert menu items into stage
     * @return void
     */
    public ArrayList insertMenu( String menuName, Integer group ) throws SQLException {

        contentDistribution elContent = new contentDistribution();
        ArrayList menuItems = new ArrayList();
        if(Objects.equals(menuName, "main")){
            menuItems = elContent.fetchMainMenu();
        }
        if(menuName.equals("submenu")){
            menuItems = elContent.fetchSubmenu(group);
        }
        return menuItems;
    }

    /**
     * Show stand by panoramic
     * @return Scene
     */
    public Scene showStby(String diff){
        Integer width = 800;
        Integer height = 600;
        BorderPane borderKane = new BorderPane();
        if(diff == "estado"){
            borderKane.setStyle("-fx-background-image: url('"+resources+"resources/panorama-estado.jpg'); -fx-background-repeat: stretch;");
        }else{
            borderKane.setStyle("-fx-background-image: url('"+resources+"resources/panorama.jpg'); -fx-background-repeat: stretch;");
        }

        return new Scene(borderKane, width, height) ;
    }

    /**
     * Show start panoramic scene
     * @return Scene
     */
    public void showScreensaver(Stage someStage, double width, double height){
        BorderPane borderKane = new BorderPane();
        /* Initialize media manager */
        mediaController mediaCtrlr = new mediaController();
        final MediaView theViewportView = mediaCtrlr.loadVideo( resources+"loop-transparente.mp4", borderKane, true, true );
        theViewportView.setFitWidth(width);
        theViewportView.setFitHeight(height);

        someStage.setX(getPrimaryScreenBounds().getMinX());
        someStage.setY(getPrimaryScreenBounds().getMinY());
        borderKane.getChildren().add(theViewportView);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    someStage.setScene( new Scene(borderKane, width, height) );
                }
            });
    }

    /**
     * Fetch detailed information about a place
     * @return Scene
     */
    public Scene showDetail(Integer place_id) throws SQLException {
        Integer width = 800;
        Integer height = 600;
        BorderPane borderKane = new BorderPane();
        contentDistribution elContent = new contentDistribution();
        Place pageContent = elContent.fetchDetail(place_id);
        mediaController mediaDude = new mediaController();

        // Title & subtitle
        Label titleLabel  = new Label(pageContent.getName());
            titleLabel.setStyle("-fx-background-color: transparent;" +
                    "-fx-font-size: 45px; -fx-text-fill: #004987; -fx-text-align: center; -fx-font-weight: 700; -fx-padding: 0 0 6 30;");
        Label subtitleLabel  = new Label(pageContent.getLocation());
            subtitleLabel.setStyle("-fx-background-color: transparent;" +
                    "-fx-font-size: 16px; -fx-text-fill: #0092b3; -fx-text-align: center; -fx-font-weight: 600; -fx-padding: 0 0 10 34;");

        VBox titleBox = new VBox();
            titleBox.setPadding(new Insets(66, 0, 100, 350));
            titleBox.setAlignment(Pos.TOP_LEFT);
            titleBox.getChildren().addAll(titleLabel, subtitleLabel);

        // Content
        TextArea textContent = new TextArea();
            textContent.setText(pageContent.getDescription());
            textContent.setPrefColumnCount(60);
            textContent.setMinHeight(575);
            textContent.setWrapText(true);
            textContent.setEditable(false);
            textContent.setStyle("-fx-background-color: #f4f4f4; -fx-line-spacing: 1em;" +
                                " -fx-font-size: 19px;  -fx-text-align: left; -fx-font-weight: 500; -fx-text-origin: top; -fx-padding: 20 0 0 0;");
        Label detailsLabel = new Label(pageContent.getDetails());
            detailsLabel.setStyle("-fx-background-color: #f4f4f4;" +
                                  " -fx-font-size: 16px;  -fx-text-align: left; -fx-font-weight: 700; -fx-text-origin: top; -fx-padding: 0 0 0 0;");
            detailsLabel.setPadding(new Insets(-80, 0, 0, 0));
            detailsLabel.setPrefWidth(640);

        VBox textBox = new VBox();
            textBox.setPadding(new Insets(-80, 80, 0, 0));
            textBox.setPrefWidth(620);
            textBox.setAlignment(Pos.CENTER_RIGHT);
            textBox.getChildren().addAll(textContent, detailsLabel);


        // Location information
        Label latlongLabel  = new Label(pageContent.getLatlong());
            latlongLabel.setStyle("-fx-background-color: transparent;" +
                                "-fx-font-size: 24px; -fx-text-fill: #004987; -fx-text-align: center; -fx-font-weight: 700; -fx-padding: 15 0 0 12;");
        Label addrLabel  = new Label(pageContent.getAddress());
            addrLabel.setStyle("-fx-background-color: transparent;" +
                                "-fx-font-size: 16px; -fx-text-fill: #EE7600; -fx-text-align: center; -fx-font-weight: 300; -fx-padding: 10 0 0 12;");

        VBox latlongBox = new VBox();
            latlongBox.setPadding(new Insets(30, 0, 450, 193));
            latlongBox.setAlignment(Pos.BOTTOM_LEFT);
            latlongBox.getChildren().addAll(latlongLabel, addrLabel);

        //Layout inside BorderPane
        borderKane.setRight(textBox);
        borderKane.setTop(titleBox);
        borderKane.setBottom(latlongBox);
        borderKane.setMargin(textContent, new Insets(120, 200 ,0 ,0 ));
        borderKane.setStyle("-fx-background-image: url('"+resources+"resources/bg_detail.png'); -fx-background-repeat: stretch;");

        // Insert video or media content
        if(!pageContent.getVideo().equals("")){
            MediaView theView = mediaDude.loadVideo(resources+"content/"+pageContent.getVideo(), null, true, true);
            theView.setViewport(new Rectangle2D(0,0,1080,608));
            borderKane.setCenter(theView);
            borderKane.setMargin(theView, new Insets(-50,0,18,193));
        }else{
            ImageView theView = mediaDude.loadImage(resources+"content/"+pageContent.getImage());
            theView.setViewport(new Rectangle2D(0,0,1080,608));
            borderKane.setMargin(theView, new Insets(-170,0,0,200));
            borderKane.setCenter(theView);
        }

        Scene myScene = new Scene(borderKane, width, height);
        return myScene;
    }


    public Screen getPrimaryScreen(){
        return this.primaryScreen;
    }

    public Screen getSecondaryScreen(){
        return this.secondaryScreen;
    }

    public double getWidthPrimary() {
        return widthPrimary;
    }

    public double getWidthSecondary() {
        return widthSecondary;
    }

    public double getHeightPrimary() {
        return heightPrimary;
    }

    public double getHeightSecondary() {
        return heightSecondary;
    }

    public Rectangle2D getPrimaryScreenBounds() {
        return primaryScreenBounds;
    }

    public Rectangle2D getSecondaryScreenBounds() {
        return secondaryScreenBounds;
    }

}
