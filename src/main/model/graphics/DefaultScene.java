package main.model.graphics;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import main.ui.TacticBaseBattle;

import java.io.FileInputStream;

public abstract class DefaultScene {
    public final static double FINAL_WIDTH = 1040;
    public final static double FINAL_HEIGHT = 820;
    public final static String CSS_FILE = "main/ui/tacticsOnTheLine.css";
    protected final StackPane mainPane = new StackPane();
    protected final Scene mainScene = new Scene(mainPane, FINAL_WIDTH, FINAL_HEIGHT);

    protected DefaultScene() {
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(mainScene);
        addCSS(mainScene);
        mainPane.setId("defaultBackground");
    }

    protected abstract void initializeGraphics();

    protected void addCSS(Scene scene) {
        scene.getStylesheets().add(CSS_FILE);
    }

    public static void centreRegionOnPane(Pane pane, Region region) {
        region.layoutXProperty().bind(pane.widthProperty().subtract(region.widthProperty()).divide(2));
        region.layoutYProperty().bind(pane.heightProperty().subtract(region.heightProperty()).divide(2));
    }

    protected void animateBackground(Scene scene, Pane pane) {
        try {
            FileInputStream input = new FileInputStream("D:\\CPSC\\PERSONAL PROJECTS\\TacticsOnTheLine\\src\\resources\\AnimatedLeaf.png");
            Image leafImage = new Image(input);
            ImageView leafImageView = new ImageView(leafImage);
            leafImageView.setFitHeight(50.0);
            leafImageView.setPreserveRatio(true);
            pane.getChildren().add(leafImageView);


            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(20));
            rotateTransition.setByAngle(45f);
            rotateTransition.setNode(leafImageView);

            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.seconds(20));
            pathTransition.setNode(leafImageView);
            pathTransition.setPath(new Line(getRandomNumberForStartOfPath(scene), -100, getRandomNumberForEndOfPath(scene), scene.getHeight() + 100));
            pathTransition.play();

            ParallelTransition sequentialTransition = new ParallelTransition(rotateTransition, pathTransition);
            sequentialTransition.play();
            sequentialTransition.setOnFinished(e -> {
                pathTransition.setPath(new Line(getRandomNumberForStartOfPath(scene), -100, getRandomNumberForEndOfPath(scene) / 2, scene.getHeight() + 100));
                sequentialTransition.play();
            });
        } catch (Exception e) {
            //
        }
    }

    protected void fadeMainPaneToGivenPane(Pane givenPane) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1));
        fadeIn.setNode(givenPane);
        fadeIn.setToValue(1.00);
        fadeIn.setFromValue(0.00);
        fadeIn.setOnFinished(e -> mainPane.getChildren().add(givenPane));
        fadeIn.play();
    }

    // top right
    private double getRandomNumberForStartOfPath(Scene window) {
        return Math.random() * (window.getWidth() - (window.getWidth() / 2) + 1) + (window.getWidth() / 2);
    }

    // bottom left
    private double getRandomNumberForEndOfPath(Scene window) {
        return Math.random() * (window.getWidth() / 2 + 1);
    }
}
