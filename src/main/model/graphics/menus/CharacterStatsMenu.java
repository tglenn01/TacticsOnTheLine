package main.model.graphics.menus;

import javafx.geometry.HPos;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.model.boardSystem.landTypes.WaterLandType;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.ExperiencePoints;
import main.model.characterSystem.StatSheet;
import main.model.graphics.sceneElements.images.CharacterJobLabel;
import main.model.graphics.sceneElements.images.CharacterNameLabel;
import main.model.graphics.sceneElements.list.StatusEffectList;
import main.ui.TacticBaseBattle;



import static main.model.graphics.DefaultScene.CSS_FILE;

public class CharacterStatsMenu {
    private static boolean isDisplaying;
    private static Stage window;

    public static void display(CharacterUnit unit) {
        window = new Stage();
        window.initOwner(TacticBaseBattle.getInstance().getPrimaryStage());
        window.setTitle("Ability Menu!");

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Pane portrait = characterPortrait(unit);
        Pane sprite = characterSprite(unit);
        BarChart<Number, String> statChart = statChart(unit);
        Pane characterName = new CharacterNameLabel(unit, 380, 60);
        Label characterJob = new CharacterJobLabel(unit.getCharacterJob(), 380, 60);
        HBox statusEffectBar = new StatusEffectList(unit);
        HBox hpAndMpLayout= healthAndManaLayout(unit);
        HBox experienceLayout = experienceLayout(unit);

        Button closeButton = new Button("close");
        closeButton.setOnAction(event -> {
            window.close();
            if (unit == TacticBaseBattle.getInstance().getBattle().getActiveCharacter())
                BattleMenu.getInstance().displayCharacterMenu(unit);
        });
        characterJob.setAlignment(Pos.CENTER);
        /*grid.add(portrait, 0, 0, 4, 8);
        grid.add(sprite, 0, 8, 2, 2);
        grid.add(characterName, 4, 0, 3, 1);
        grid.add(characterJob, 4, 1, 3, 1);
        grid.add(hpAndMpLayout, 7, 0, 3, 1);
        grid.add(experienceLayout, 7, 1, 3, 1);
        grid.add(statChart, 4, 2, 6, 6);
        grid.add(statusEffectBar, 2, 8,8, 2);
        grid.add(closeButton, 9, 9, 1, 1);*/


        GridPane.setConstraints(portrait, 0,0, 4, 8);
        GridPane.setConstraints(sprite, 0, 8, 2, 2);
        GridPane.setConstraints(characterName, 4, 0, 3, 1);
        GridPane.setConstraints(characterJob, 4, 1, 3, 1);
        GridPane.setConstraints(hpAndMpLayout, 7, 0, 3, 1);
        GridPane.setConstraints(experienceLayout, 7, 1, 3, 1);
        GridPane.setConstraints(statChart, 4, 2, 6 ,6);
        GridPane.setConstraints(statusEffectBar, 2, 8, 7 ,2);
        GridPane.setConstraints(closeButton, 9, 9, 1, 2);

        grid.getChildren().addAll(portrait, sprite, characterName, characterJob, hpAndMpLayout, experienceLayout, statChart, statusEffectBar, closeButton);














        grid.setPrefSize(1000, 800);
        GridPane.setValignment(portrait, VPos.TOP);
        GridPane.setValignment(characterName, VPos.CENTER);
        GridPane.setHalignment(characterName, HPos.CENTER);
        GridPane.setValignment(closeButton, VPos.BOTTOM);
        GridPane.setHalignment(closeButton, HPos.RIGHT);

        grid.getChildren().forEach(node -> node.setId("normalNode"));
        characterName.setId("fancyNode");
        characterJob.setId("fancyNode");
        hpAndMpLayout.setId("fancyNode");
        experienceLayout.setId("fancyNode");

        Scene scene = new Scene(grid, 1000, 800);
        scene.getStylesheets().add(CSS_FILE);

        window.setOnCloseRequest(e -> isDisplaying = false);
        window.setOnHidden(e -> isDisplaying = false);
        window.setOnShown(e -> isDisplaying = true);
        window.setScene(scene);
        window.setMinHeight(800);
        window.setMinWidth(1000);
        window.setResizable(false);
        window.show();
    }

    private static Pane characterPortrait(CharacterUnit unit) {
        Pane window = new Pane();
        ImageView portrait = unit.getCharacterPortrait();
        portrait.fitWidthProperty().bind(window.widthProperty());
        portrait.fitHeightProperty().bind(window.heightProperty());
        portrait.setPreserveRatio(false);
        window.getChildren().add(portrait);
        return window;
    }

    private static Pane characterSprite(CharacterUnit unit) {
        Pane spritePane = new Pane();
        spritePane.setBackground(new WaterLandType().getTileColour());
        Image image = unit.getCharacterSprite().getStillImage();
        ImageView sprite = new ImageView(image);
        sprite.fitHeightProperty().bind(spritePane.heightProperty());
        sprite.layoutXProperty().bind(spritePane.widthProperty().subtract(sprite.fitWidthProperty()).divide(4.5));
        sprite.setPreserveRatio(true);
        spritePane.getChildren().add(sprite);
        spritePane.setPrefSize(140, 140);
        return spritePane;
    }

    private static BarChart<Number, String> statChart(CharacterUnit unit) {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        BarChart<Number,String> statChart = new BarChart<>(xAxis, yAxis);

        statChart.setLegendVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(50);

        XYChart.Series<Number, String> series1;
        StatSheet statSheet = unit.getCharacterStatSheet();
        series1 = getCharacterStats(statSheet);
        statChart.getData().add(series1);
        statChart.setPrefSize(460, 480);

        return statChart;
    }

    private static XYChart.Series<Number, String> getCharacterStats(StatSheet statSheet) {
        XYChart.Series<Number, String> newSeries = new XYChart.Series<>();
        newSeries.getData().add(new XYChart.Data<>(statSheet.getDexterity(), "Dexterity"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSpeed(), "Speed"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getResistance(), "Resistance"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getArmour(), "Armour"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getMagic(), "Magic"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getStrength(), "Strength"));
        return newSeries;
    }

    private static HBox healthAndManaLayout(CharacterUnit unit) {
        StatSheet statSheet = unit.getCharacterStatSheet();
        int currentHealth = statSheet.getHealth();
        int maxHealth = statSheet.getMaxHealth();
        Label healthLabel = new Label("HP: " + currentHealth + "/" + maxHealth);

        int currentMana = statSheet.getMana();
        int maxMana = statSheet.getMaxMana();

        Label manaLabel = new Label("MP: " + currentMana + "/" + maxMana);

        HBox healthAndManaLabel = new HBox(healthLabel, manaLabel);

        healthAndManaLabel.setSpacing(15.0);
        healthAndManaLabel.setAlignment(Pos.CENTER);
        return healthAndManaLabel;
    }

    private static HBox experienceLayout(CharacterUnit unit) {
        ExperiencePoints unitXP = unit.getExperiencePoints();
        Label currentExperience = new Label(unitXP.getCurrentExperience() + "/100");
        Label currentLevel = new Label("Level " + unitXP.getLevel());
        HBox experienceLayout = new HBox(currentLevel, currentExperience);

        experienceLayout.setSpacing(15.0);
        experienceLayout.setAlignment(Pos.CENTER);
        return experienceLayout;
    }


    public static boolean isDisplaying() {
        return isDisplaying;
    }
}
