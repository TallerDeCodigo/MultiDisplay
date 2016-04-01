package controllers;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


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
     * Load image file into ImageView Control
     * @param String file_url
     * @return ImageView
     */
    public ImageView loadImage( String file_url){
        final ImageView theImgView = new ImageView();
        Image myImage = new Image(file_url);
        theImgView.setImage(myImage);
        theImgView.setPreserveRatio(true);
        theImgView.setSmooth(true);
        theImgView.setCache(true);
        return theImgView;
    }

    /**
     * Load video file into MediaView Control
     * @param String file_url
     * @param Boolean autoplay
     */
    public MediaView loadVideo( String file_url, Object container, Boolean autoplay, Boolean loop){
        Media newMedia        = new Media(file_url);
        MediaPlayer thePlayer = new MediaPlayer(newMedia);
        final MediaView theVideoView = new MediaView(thePlayer);
        theVideoView.setPreserveRatio(true);
        thePlayer.setAutoPlay(autoplay);
        thePlayer.setMute(true);
        if(loop)
            thePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        return theVideoView;
    }



}
