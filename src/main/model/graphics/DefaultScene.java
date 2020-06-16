package main.model.graphics;

import javafx.scene.Scene;

public abstract class DefaultScene {
    public final static double FINAL_WIDTH = 1040;
    public final static double FINAL_HEIGHT = 820;
    public final static String CSS_FILE = "main/ui/tacticsOnTheLine.css";

    protected abstract void initializeGraphics();

    protected void addCSS(Scene scene) {
        scene.getStylesheets().add(CSS_FILE);
    }
}
