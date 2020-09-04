package main.model.graphics.menus;

import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.model.boardSystem.landTypes.WaterLandType;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.ExperiencePoints;
import main.model.battleSystem.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class LevelUpMenu {
    private final int TRANSITION_DELAY_IN_MS = 2000; // delay of transition in ms
    private List<ExperiencePoints.LevelUpButton> selectedLevels = new ArrayList<>();
    public static boolean isDisplaying;

    public LevelUpMenu(CharacterUnit activeUnit, List<ExperiencePoints.LevelUpButton> levelUpButtons) {
        Stage window = new Stage();
        window.setOnCloseRequest(Event::consume); // only way to close the window is to choose 3 stat points

        Pane spritePane = setCharacterSpritePane(activeUnit);
        Pane characterInformationPane = setCharacterInformationPane(activeUnit);


        GridPane levelInformationPane = new GridPane();
        levelInformationPane.add(spritePane, 0, 0);
        levelInformationPane.add(characterInformationPane, 1, 0);

        BorderPane chosenBonusStatPane = new BorderPane();
        Label nameLabel = new Label(activeUnit.getCharacterName());
        Label buttonsLeftLabel = new Label("Choose " + (3 - selectedLevels.size()) + " More");
        chosenBonusStatPane.setPadding(new Insets(10));

        HBox levelUpButtonPane = setLevelUpButtonsPane(window, activeUnit, levelUpButtons, buttonsLeftLabel);


        chosenBonusStatPane.setTop(nameLabel);
        chosenBonusStatPane.setCenter(levelUpButtonPane);
        chosenBonusStatPane.setBottom(buttonsLeftLabel);
        BorderPane.setAlignment(nameLabel, Pos.TOP_CENTER);
        BorderPane.setAlignment(buttonsLeftLabel, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(levelUpButtonPane, Pos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(levelInformationPane);
        stackPane.setPadding(new Insets(10));

        Scene levelUpScene = new Scene(stackPane);
        window.setScene(levelUpScene);
        window.setResizable(false);
        window.setWidth(600);
        window.setHeight(250);
        window.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.show();

        doFadeIn(stackPane, levelInformationPane, chosenBonusStatPane);
        isDisplaying = true;
    }

    // fade from levelInformationPane to levelButtonPane after TRANSITION_DELAY_IN_MS
    private void doFadeIn(Pane stackPane, Pane levelInformationPane, Pane chooseBonusStatPAne) {
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000));
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(1000));

        fadeOutTransition.setDelay(Duration.millis(TRANSITION_DELAY_IN_MS));
        fadeOutTransition.setNode(levelInformationPane);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.setOnFinished(e -> {
            stackPane.getChildren().remove(levelInformationPane);
            stackPane.getChildren().add(chooseBonusStatPAne);
            fadeInTransition.play();
        });

        fadeInTransition.setNode(chooseBonusStatPAne);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);

        fadeOutTransition.play();
    }


    private Pane setCharacterInformationPane(CharacterUnit activeUnit) {
        Pane informationPane = new GridPane();
        Label levelUpLabel = new Label("Level UP!");
        Label newLevelLabel = new Label(Integer.toString(activeUnit.getLevel()));
        VBox vBox = new VBox();
        vBox.getChildren().addAll(levelUpLabel, newLevelLabel);

        vBox.setPrefSize(450, 250);
        vBox.setAlignment(Pos.CENTER);
        informationPane.getChildren().addAll(vBox);
        return vBox;
    }

    private Pane setCharacterSpritePane(CharacterUnit activeUnit) {
        Pane spritePane = new Pane();
        spritePane.setBackground(new WaterLandType().getTileColour());
        Image image = activeUnit.getCharacterSprite().getStillImage();
        ImageView sprite = new ImageView(image);
        sprite.setFitHeight(200);
        sprite.setPreserveRatio(true);
        spritePane.getChildren().add(sprite);
        return spritePane;
    }

    private HBox setLevelUpButtonsPane(Stage window, CharacterUnit activeUnit, List<ExperiencePoints.LevelUpButton> levelUpButtons, Label buttonsLeftLabel) {
        for (ExperiencePoints.LevelUpButton levelUpButton : levelUpButtons) {
            levelUpButton.setOnMouseClicked(e -> {
                if (selectedLevels.contains(levelUpButton)) {
                    selectedLevels.remove(levelUpButton);
                    levelUpButton.setOpacity(1.00);
                } else {
                    selectedLevels.add(levelUpButton);
                    levelUpButton.setOpacity(0.50);
                }
                buttonsLeftLabel.setText("Choose " + (3 - selectedLevels.size()) + " More");
                if (selectedLevels.size() == 3) applyLevels(window, activeUnit);
            });
            levelUpButton.setPrefSize(120, 120);
        }

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20.00);
        hBox.getChildren().addAll(levelUpButtons);

        return hBox;
    }

    private void applyLevels(Stage window, CharacterUnit activeUnit) {
        for (ExperiencePoints.LevelUpButton levelUpButton : selectedLevels) {
            levelUpButton.addStat(activeUnit.getCharacterStatSheet());
        }
        window.close();
        isDisplaying = false;
        if (activeUnit.getExperiencePoints().getCurrentExperience() >= 100) {
            activeUnit.getExperiencePoints().levelUp(activeUnit);
        } else {
            if (activeUnit.getActionTokens() <= 0 && !activeUnit.getMovementToken())
                TacticBaseBattle.getInstance().getBattle().endTurn();
            else activeUnit.takeNextAction();
        }
    }
}
