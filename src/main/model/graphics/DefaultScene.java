package main.model.graphics;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import main.model.battleSystem.TacticBaseBattle;

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
        ImageView leafImage = new ImageView();
        leafImage.setImage(setRandomLeaf());
        leafImage.setFitHeight(50.0);
        leafImage.setPreserveRatio(true);
        pane.getChildren().add(leafImage);

        Bounds bounds = mainPane.localToScene(mainPane.getBoundsInLocal(), true);


        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(6));
        rotateTransition.setAutoReverse(true);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setByAngle(45f);
        rotateTransition.setNode(leafImage);

        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        Path path = new Path();
        CubicCurveTo cubicCurveTo = new CubicCurveTo(-(sceneWidth / 4), -(sceneHeight / 4) - 100,
                ((sceneWidth * 3)/ 4), ((sceneHeight *3) / 4) + 100,
                ((sceneWidth / 2) + leafImage.getFitWidth()),
                ((sceneHeight / 2) + leafImage.getFitHeight()));
        MoveTo moveTo = new MoveTo(-((sceneWidth / 2)+ leafImage.getFitWidth()), -((sceneHeight / 2) + leafImage.getFitHeight()));
        path.getElements().addAll(moveTo, cubicCurveTo);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(20));
        pathTransition.setNode(leafImage);
        pathTransition.setPath(path);

        ParallelTransition sequentialTransition = new ParallelTransition(rotateTransition, pathTransition);
        sequentialTransition.playFromStart();
        sequentialTransition.setOnFinished(e -> {
            leafImage.setImage(setRandomLeaf());
            sequentialTransition.playFromStart();
        });
    }

    private Image setRandomLeaf() {
        double random = Math.random();
        int leafNumber;
        if (random < 0.50) leafNumber = 0;
        else leafNumber = 1;
        return new Image("resources/ExtraImages/AnimatedLeaf" + leafNumber + ".png");
    }
}
