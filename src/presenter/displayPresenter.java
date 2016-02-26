package presenter;

import javafx.stage.Screen;

/**
 * Presenter to manage multiple display application
 * @author John Falcon
 *
 */
public class displayPresenter {

    int width   = 800;
    int height  = 600;

    private Screen primaryScreen;
    private Screen secondaryScreen;

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
        Screen secondaryScreen;
        if (allScreens.size() <= 1) {
            System.out.println("Only one screen detected");
            this.secondaryScreen = primaryScreen;
        } else {

            if (allScreens.get(0).equals(primaryScreen)) {
                this.secondaryScreen = allScreens.get(1);
            } else {
                this.secondaryScreen = allScreens.get(0);
            }
        }
    }

}
