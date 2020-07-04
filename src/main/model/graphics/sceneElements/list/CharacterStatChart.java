package main.model.graphics.sceneElements.list;

import javafx.scene.chart.*;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;

import static main.model.characterSystem.StatSheet.SCALE_REFERENCE;

public class CharacterStatChart extends BarChart<Number, String> {
    private NumberAxis xAxis;
    private CategoryAxis yAxis;


    public CharacterStatChart(CharacterUnit unit, int minXSize, int minYSize,
                              Axis<Number> numberAxis, Axis<String> stringAxis) {
        super(numberAxis, stringAxis);

        this.xAxis = (NumberAxis) numberAxis;
        this.yAxis = (CategoryAxis) stringAxis;

        this.setLegendVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(SCALE_REFERENCE);

        XYChart.Series<Number, String> series1;
        StatSheet statSheet = unit.getCharacterStatSheet();
        series1 = unit.getCharacterJob().getSimpleStatData(statSheet);
        this.getData().add(series1);
        this.setMinSize(minXSize, minYSize);
    }

    public void updateData(CharacterUnit activeCharacter) {
        activeCharacter.getCharacterJob().getSimpleStatData(this.getData().get(0), activeCharacter.getCharacterStatSheet());
    }
}
