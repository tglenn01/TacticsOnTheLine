package main.model.graphics.sceneElements.images;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.ui.TacticBaseBattle;

import static main.model.graphics.DefaultScene.CSS_FILE;

public class HealthBar {
    private Stage healthBarWindow;

    public HealthBar(double maxHealthPoints, double oldHealthPoints, double newHealthPoints) {
        initializeGraphics(maxHealthPoints, oldHealthPoints, newHealthPoints);
    }


    private void initializeGraphics(double maxHealthPoints, double oldHealthPoints, double newHealthPoints) {
        ProgressBar healthBar = new ProgressBar();
        healthBar.setPrefSize(200, 60);

        healthBar.setProgress(oldHealthPoints / maxHealthPoints);
        Label healthPoints = new Label((int) oldHealthPoints + "/" + (int) maxHealthPoints);
        healthPoints.setId("normalNode");


        BorderPane layout = new BorderPane();
        layout.setLeft(healthBar);
        layout.setCenter(healthPoints);


        Scene healthBarScene = new Scene(layout, 300, 75);
        healthBarScene.getStylesheets().add(CSS_FILE);


        healthBarWindow = new Stage();
        healthBarWindow.setScene(healthBarScene);
        healthBarWindow.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        healthBarWindow.initStyle(StageStyle.UTILITY);

        AnimationTimer depleteHealth = depleteHealthAnimationTimer(maxHealthPoints, oldHealthPoints, newHealthPoints, healthBar, healthPoints);
        AnimationTimer fadeInAnimation = fadeInAnimation(depleteHealth);
        healthBarWindow.setOnShown(e -> fadeInAnimation.start());
    }

    private AnimationTimer fadeInAnimation(AnimationTimer depleteHealth) {
        AnimationTimer fadeInAnimation = new AnimationTimer() {
            private double opacity = 0;
            private long delay = 4_000_000;
            private long prevTime = 0;

            @Override
            public void handle(long now) {

                if ((now - prevTime) >= delay) {
                    opacity += 0.01;
                    healthBarWindow.opacityProperty().set(opacity);
                }

                prevTime = now;

                if (opacity >= 1) {
                    stop();
                    depleteHealth.start();
                }
            }
        };
        return fadeInAnimation;
    }

    private AnimationTimer depleteHealthAnimationTimer(double maxHealthPoints, double oldHealthPoints, double newHealthPoints, ProgressBar healthBar, Label healthPoints) {
        return new AnimationTimer() {
                private long delay = 16_500_000;
                private long prevTime = 0;
                private double currentHealthPoints = oldHealthPoints;

                @Override
                public void handle(long now) {

                    if ((now - prevTime) >= delay) {
                        currentHealthPoints--;
                        healthBar.setProgress(currentHealthPoints / maxHealthPoints);
                        healthPoints.setText((int) currentHealthPoints + "/" + (int) maxHealthPoints);
                    }

                    prevTime = now;

                    if (currentHealthPoints <= newHealthPoints || currentHealthPoints <= 0) {
                        stop();
                        healthBarWindow.close();
                    }
                }
            };
    }

    public void showAndWait() {
        healthBarWindow.showAndWait();
    }
}
