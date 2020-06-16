package main.model.graphics.menus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.graphics.sceneElements.images.CharacterNameLabel;
import main.model.graphics.sceneElements.list.StatusEffectList;
import main.ui.TacticBaseBattle;

import static main.model.characterSystem.StatSheet.SCALE_REFERENCE;

public class CharacterStatsMenu {
    private static boolean isDisplaying;
    private static Stage window;

    public static void display(CharacterUnit unit) {
        window = new Stage();
        window.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        window.setTitle("Ability Menu");

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Pane portrait = characterPortrait(unit);
        Pane sprite = characterSprite(unit);
        BarChart<Number, String> statChart = statChart(unit);
        Label characterName = new CharacterNameLabel(unit, 140, 60);
        Label characterJob = characterJob(unit);
        VBox statusEffectBar = new StatusEffectList(unit);
        Label healthBar = healthBar(unit);
        Label manaBar = manaBar(unit);
        Button closeButton = new Button("close");
        closeButton.setOnAction(event -> window.close());

        grid.add(portrait, 0, 0, 4, 8);
        grid.add(sprite, 0, 8, 2, 2);
        grid.add(characterName, 4, 0, 1, 2);
        grid.add(characterJob, 4, 1, 1, 2);
        grid.add(healthBar, 6, 0, 1, 2);
        grid.add(manaBar, 6, 1, 1, 2);
        grid.add(statChart, 2, 4, 4, 6);
        grid.add(statusEffectBar, 8, 2,2, 7);
        grid.add(closeButton, 9, 9, 1, 1);


        GridPane.setValignment(portrait, VPos.TOP);
        GridPane.setValignment(characterName, VPos.CENTER);

        Scene scene = new Scene(grid, 800, 800);
        window.setOnCloseRequest(e -> isDisplaying = false);
        window.setOnHidden(e -> isDisplaying = false);
        window.setOnShown(e -> isDisplaying = true);
        window.setScene(scene);
        window.show();
    }

    private static Pane characterPortrait(CharacterUnit unit) {
        Pane window = new Pane();
        ImageView portrait = unit.getCharacterPortrait();
        portrait.fitWidthProperty().bind(window.widthProperty());
        portrait.fitHeightProperty().bind(window.heightProperty());
        portrait.setPreserveRatio(false);
        window.getChildren().add(portrait);
        window.setPrefSize(300, 640);
        return window;
    }

    private static Pane characterSprite(CharacterUnit unit) {
        Pane window = new Pane();
        ImageView sprite = unit.getSprite();
        sprite.fitWidthProperty().bind(window.widthProperty());
        sprite.fitHeightProperty().bind(window.heightProperty());
        sprite.setPreserveRatio(false);
        window.getChildren().add(sprite);
        window.setPrefSize(140, 140);
        return window;
    }

    private static BarChart<Number, String> statChart(CharacterUnit unit) {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        BarChart<Number,String> statChart = new BarChart<>(xAxis, yAxis);

        statChart.setLegendVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(SCALE_REFERENCE);

        XYChart.Series<Number, String> series1;
        StatSheet statSheet = unit.getCharacterStatSheet();
        series1 = unit.getCharacterJob().getRawJobStatData(statSheet);
        statChart.getData().add(series1);
        statChart.setMinSize(460, 300);

        return statChart;
    }

    private static Label characterName(CharacterUnit unit) {
        Label characterName = new Label(unit.getCharacterName());

        //characterName.setMinSize(600, 120);
        characterName.setAlignment(Pos.CENTER);
        return characterName;
    }

    private static Label characterJob(CharacterUnit unit) {
        Label characterJob = new Label(unit.getCharacterJob().getJobTitle());
        characterJob.setPrefSize(140, 60);

        characterJob.setAlignment(Pos.CENTER);
        return characterJob;
    }

    private static Label healthBar(CharacterUnit unit) {
        StatSheet statSheet = unit.getCharacterStatSheet();
        int currentHealth = statSheet.getHealth();
        int maxHealth = statSheet.getMaxHealth();
        Label healthLabel = new Label(currentHealth + "/" + maxHealth);
        healthLabel.setPrefSize(140, 60);
        return healthLabel;
    }

    private static Label manaBar(CharacterUnit unit) {
        StatSheet statSheet = unit.getCharacterStatSheet();
        int currentMana = statSheet.getMana();
        int maxMana = statSheet.getMaxMana();

        Label manaLabel = new Label(currentMana + "/" + maxMana);
        manaLabel.setPrefSize(140, 60);
        return manaLabel;
    }

    public static boolean isDisplaying() {
        return isDisplaying;
    }

    public static void closeWindow() {
        window.close();
    }
}
