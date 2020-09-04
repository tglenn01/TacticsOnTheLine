package main.model.graphics.sceneElements.images;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.characterSystem.CharacterUnit;
import main.model.battleSystem.TacticBaseBattle;

import static main.model.graphics.DefaultScene.CSS_FILE;

public class HealthBar {
    private Stage healthBarWindow;

    public HealthBar(CharacterUnit receivingUnit, double maxHealthPoints, double oldHealthPoints, double newHealthPoints) {
        initializeGraphics(receivingUnit, maxHealthPoints, oldHealthPoints, newHealthPoints);
    }


    private void initializeGraphics(CharacterUnit receivingUnit, double maxHealthPoints, double oldHealthPoints, double newHealthPoints) {
        ProgressBar healthBar = new ProgressBar();
        healthBar.setPrefSize(200, 60);

        healthBar.setProgress(oldHealthPoints / maxHealthPoints);
        Label healthPointsLabel = new Label((int) oldHealthPoints + "/" + (int) maxHealthPoints);
        healthPointsLabel.setId("normalNode");


        BorderPane layout = new BorderPane();
        layout.setLeft(healthBar);
        layout.setCenter(healthPointsLabel);


        Scene healthBarScene = new Scene(layout, 300, 75);
        healthBarScene.getStylesheets().add(CSS_FILE);

        healthBarWindow = new Stage();
        healthBarWindow.setScene(healthBarScene);
        healthBarWindow.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        healthBarWindow.initStyle(StageStyle.UTILITY);
        healthBarWindow.setOpacity(0);

        Bounds bounds = receivingUnit.getCharacterSprite().localToScreen(receivingUnit.getCharacterSprite().getBoundsInLocal());
        healthBarWindow.setX(bounds.getMinX() - (healthBarScene.getWidth() / 4));
        healthBarWindow.setY(bounds.getMaxY());

        AnimationTimer healthBarAnimation;
        if (newHealthPoints > oldHealthPoints) healthBarAnimation = gainHealthAnimationTimer(maxHealthPoints,
                oldHealthPoints, newHealthPoints, healthBar, healthPointsLabel);
        else healthBarAnimation = depleteHealthAnimationTimer(maxHealthPoints,
                oldHealthPoints, newHealthPoints, healthBar, healthPointsLabel);
        AnimationTimer fadeInAnimation = fadeInAnimation(healthBarAnimation);
        healthBarWindow.setOnShown(e -> fadeInAnimation.start());
    }

    private AnimationTimer fadeInAnimation(AnimationTimer healthBarAnimation) {
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
                    healthBarAnimation.start();
                }
            }
        };
        return fadeInAnimation;
    }

    private AnimationTimer depleteHealthAnimationTimer(double maxHealthPoints, double oldHealthPoints,
                                                       double newHealthPoints, ProgressBar healthBar,
                                                       Label healthPointsLabel) {
        return new AnimationTimer() {
                private long delay = 16_000_000;
                private long prevTime = 0;
                private double currentHealthPoints = oldHealthPoints;

                @Override
                public void handle(long now) {

                    if ((now - prevTime) >= delay) {
                        currentHealthPoints--;
                        healthBar.setProgress(currentHealthPoints / maxHealthPoints);
                        healthPointsLabel.setText((int) currentHealthPoints + "/" + (int) maxHealthPoints);
                    }

                    prevTime = now;

                    if (currentHealthPoints <= newHealthPoints || currentHealthPoints <= 0) {
                        stop();
                        healthBarWindow.close();
                    }
                }
            };
    }

    private AnimationTimer gainHealthAnimationTimer(double maxHealthPoints, double oldHealthPoints,
                                                    double newHealthPoints, ProgressBar healthBar,
                                                    Label healthPointsLabel) {
        return new AnimationTimer() {
            private long delay = 16_000_000;
            private long prevTime = 0;
            private double currentHealthPoints = oldHealthPoints;

            @Override
            public void handle(long now) {

                if ((now - prevTime) >= delay) {
                    currentHealthPoints++;
                    healthBar.setProgress(currentHealthPoints / maxHealthPoints);
                    healthPointsLabel.setText((int) currentHealthPoints + "/" + (int) maxHealthPoints);
                }

                prevTime = now;

                if (currentHealthPoints >= newHealthPoints || currentHealthPoints >= maxHealthPoints) {
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
