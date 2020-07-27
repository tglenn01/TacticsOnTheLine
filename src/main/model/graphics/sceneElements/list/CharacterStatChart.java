package main.model.graphics.sceneElements.list;

import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.graphics.DefaultScene;
import main.model.jobSystem.Job;

import static main.model.characterSystem.StatSheet.SCALE_REFERENCE;

public class CharacterStatChart extends Pane {
    private BarChart<Number, String> statChart;
    private XYChart.Series<Number, String> series;


    public CharacterStatChart(CharacterUnit unit, int minXSize, int minYSize,
                              Axis<Number> numberAxis, Axis<String> stringAxis) {
        statChart = new BarChart<>(numberAxis, stringAxis);

        NumberAxis xAxis = (NumberAxis) numberAxis;
        CategoryAxis yAxis = (CategoryAxis) stringAxis;

        statChart.setLegendVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(SCALE_REFERENCE);
        yAxis.setAnimated(false);

        series = initializeJobStatsChart(unit);

        DefaultScene.centreRegionOnPane(this, statChart);

        statChart.getData().add(series);
        statChart.setMinSize(minXSize, minYSize);
        this.setMinSize(minXSize, minYSize);
        this.getChildren().add(statChart);
    }

    public XYChart.Series<Number, String> initializeJobStatsChart(CharacterUnit activeCharacter) {
        Job characterJob = activeCharacter.getCharacterJob();
        StatSheet statSheet = activeCharacter.getCharacterStatSheet();

        XYChart.Series<Number, String> newSeries = new XYChart.Series<>();
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleDexterity(characterJob.getJobDexterity()), "Dexterity"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleSpeed(characterJob.getJobSpeed()), "Speed"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleResistance(characterJob.getJobResistance()), "Resistance"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleArmour(characterJob.getJobArmour()), "Armour"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleMagic(characterJob.getJobMagic()), "Magic"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleStrength(characterJob.getJobStrength()), "Strength"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleMana(characterJob.getJobMana()), "Mana"));
        newSeries.getData().add(new XYChart.Data<>(statSheet.getSimpleHealth(characterJob.getJobHealth()), "Health"));
        return newSeries;
    }

    public void updateData(CharacterUnit activeCharacter) {
        Job characterJob = activeCharacter.getCharacterJob();
        StatSheet statSheet = activeCharacter.getCharacterStatSheet();

        series.getData().get(0).setXValue(statSheet.getSimpleDexterity(characterJob.getJobDexterity()));
        series.getData().get(1).setXValue(statSheet.getSimpleSpeed(characterJob.getJobSpeed()));
        series.getData().get(2).setXValue(statSheet.getSimpleResistance(characterJob.getJobResistance()));
        series.getData().get(3).setXValue(statSheet.getSimpleArmour(characterJob.getJobArmour()));
        series.getData().get(4).setXValue(statSheet.getSimpleMagic(characterJob.getJobMagic()));
        series.getData().get(5).setXValue(statSheet.getSimpleStrength(characterJob.getJobStrength()));
        series.getData().get(6).setXValue(statSheet.getSimpleMana(characterJob.getJobMana()));
        series.getData().get(7).setXValue(statSheet.getSimpleHealth(characterJob.getJobHealth()));
    }
}
