package main.model.graphics.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.model.characterSystem.CharacterPortrait;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.PlayableCharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.Ability;
import main.model.graphics.DefaultScene;
import main.model.graphics.JobButton;
import main.model.jobSystem.Job;
import main.model.jobSystem.jobs.Noble;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class CharacterSelect extends DefaultScene implements EventHandler<ActionEvent> {
    private CharacterUnit ally;
    private List<JobButton> jobButtonList;
    private Button advanceButton;
    private BarChart<Number, String> statChart;


/*    private final double portraitWidth = FINAL_WIDTH * (0.40);
    private final double tableWidth = FINAL_WIDTH * (0.60);
    private final double abilityWidth = FINAL_WIDTH;
    private final double jobWidth = FINAL_WIDTH  * (0.80);
    private final double nameWidth = FINAL_WIDTH * (0.60);
    private final double portraitHeight = FINAL_HEIGHT * (0.80);
    private final double tableHeight = FINAL_HEIGHT * (0.50);
    private final double abilityHeight = FINAL_HEIGHT * (0.20);
    private final double jobHeight = FINAL_HEIGHT * (0.20);
    private final double nameHeight = FINAL_HEIGHT * (0.10);*/


    public CharacterSelect() {
        jobButtonList = new ArrayList<>();
        ally = new PlayableCharacterUnit(new Noble(), "Graham");
        initializeGraphics();
    }

    protected void initializeGraphics() {
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        HBox jobs = initializeJobButtons();
        ImageView portrait = characterPortrait();
        HBox abilities = abilityIcons();
        BarChart<Number, String> statChart = statChart();
        Label characterName = characterName();
        Button advanceButton = advanceButton();
        grid.add(jobs, 0, 8, 10, 2);
        grid.add(portrait, 0, 0, 4, 8);
        grid.add(abilities, 4, 6, 6, 2);
        grid.add(statChart, 4, 2, 6, 4);
        grid.add(characterName, 4, 0, 6, 2);
        grid.add(advanceButton, 0, 9, 10, 2);
        GridPane.setValignment(portrait, VPos.TOP);
        GridPane.setHalignment(advanceButton, HPos.RIGHT);
        GridPane.setValignment(advanceButton, VPos.BOTTOM);
        GridPane.setHalignment(abilities, HPos.CENTER);
        GridPane.setValignment(characterName, VPos.CENTER);
        Scene scene = new Scene(grid);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }

    private HBox initializeJobButtons() {
        List<Job> jobList = TacticBaseBattle.getInstance().getAvailableJobs();
       jobButtonList = new ArrayList<>();
        for (Job job : jobList) {
            JobButton jobButton = new JobButton(job.getJobTitle(), job);
            jobButton.setOnAction(this);
            jobButton.setPrefSize(100, 100);
            jobButtonList.add(jobButton);
        }

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinSize(1000, 160);
        hBox.getChildren().addAll(jobButtonList);
        return hBox;
    }

    private ImageView characterPortrait() {
        CharacterPortrait characterPortrait = ally.getCharacterPortrait();
        ImageView portrait = characterPortrait.getPortrait();
        portrait.setFitWidth(400);
        portrait.setFitHeight(550);
        //portrait.setPreserveRatio(true);
        return portrait;
    }

    private HBox abilityIcons() {
        HBox hBox = new HBox();
        for (Ability ability : ally.getCharacterJob().getJobAbilityList()) {
            Label icon = new Label(ability.getAbilityName());
            hBox.getChildren().add(icon);
        }
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefSize(600, 120);
        return hBox;
    }

    private BarChart<Number, String> statChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        xAxis.setLabel("Value");
        BarChart<Number,String> statChart = new BarChart<>(xAxis, yAxis);
        statChart.setLegendVisible(false);

        XYChart.Series<Number, String> series1;
        StatSheet statSheet = ally.getCharacterStatSheet();
        series1 = ally.getCharacterJob().getJobStatData(statSheet);
        statChart.getData().add(series1);
        statChart.setPrefSize(600, 360);
        this.statChart = statChart;

        return statChart;
    }

    private Label characterName() {
        Label characterName = new Label(ally.getCharacterName());
        characterName.setMaxSize(600, 120);
        characterName.setMinSize(600, 120);
        characterName.setAlignment(Pos.CENTER);
        return characterName;
    }

    private Button advanceButton() {
        this.advanceButton = new Button("Advance");
        advanceButton.setAlignment(Pos.BOTTOM_RIGHT);
        advanceButton.setOnAction(this);
        return advanceButton;
    }

    @Override
    public void handle(ActionEvent event) {
        for (JobButton jobButton : jobButtonList) {
            if (event.getSource() == jobButton) {
                ally.setJob(jobButton.getJob());
                XYChart.Series<Number, String> graph = ally.getCharacterJob().getJobStatData(ally.getCharacterStatSheet());
                statChart.setAnimated(false);
                statChart.getData().clear();
                statChart.setAnimated(true);
                statChart.getData().add(graph);
            }
        }
        if (event.getSource() == advanceButton) {
            // advance to next character or choose scenario
        }
    }
}
