package controllers;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import controllers.mediaController;


/**
 * Media Controller class
 * @author John Falcon for TDC
 * @version 0.1.1
 */
public class mediaController {

    private static final String Media_Url = "";

    public mediaController(){

    }

    /**
     *
     * @param String file_url
     * @param Boolean autoplay
     */
    public MediaView loadVideo( String file_url, Object container, Boolean autoplay, Boolean loop){

        Media newMedia      = new Media(file_url);
        MediaPlayer thePlayer = new MediaPlayer(newMedia);
        final MediaView theView = new MediaView(thePlayer);

        thePlayer.setAutoPlay(autoplay);
        if(loop)
            thePlayer.setCycleCount(MediaPlayer.INDEFINITE);

        return theView;
    }



}
