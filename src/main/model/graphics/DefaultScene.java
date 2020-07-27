package main.model.graphics;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class DefaultScene {
    public final static double FINAL_WIDTH = 1040;
    public final static double FINAL_HEIGHT = 820;
    public final static String CSS_FILE = "main/ui/tacticsOnTheLine.css";

    protected abstract void initializeGraphics();

    protected void addCSS(Scene scene) {
        scene.getStylesheets().add(CSS_FILE);
    }

    public static void centreRegionOnPane(Pane pane, Region region) {
        region.layoutXProperty().bind(pane.widthProperty().subtract(region.widthProperty()).divide(2));
        region.layoutYProperty().bind(pane.heightProperty().subtract(region.heightProperty()).divide(2));
    }
}
