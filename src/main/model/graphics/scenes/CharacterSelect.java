package main.model.graphics.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private BarChart<Number, String> bc;


    public CharacterSelect() {
        jobButtonList = new ArrayList<>();
        ally = new PlayableCharacterUnit(new Noble(), "Graham");
        initializeGraphics();
    }

    protected void initializeGraphics() {
        GridPane characterSelectScreen = new GridPane();
        characterSelectScreen.setGridLinesVisible(true);
        characterSelectScreen.setPadding(new Insets(10, 10, 10, 10));
        HBox jobs = initializeJobButtons();
        HBox portrait = characterPortrait();
        HBox abilities = abilityIcons();
        BarChart<Number, String> statChart = statChart();
        HBox characterName = characterName();
        Button advanceButton = advanceButton();
        characterSelectScreen.add(jobs, 1, 5, 4, 1);
        characterSelectScreen.add(portrait, 0, 0, 2, 3);
        characterSelectScreen.add(abilities, 2, 2, 3, 1);
        characterSelectScreen.add(statChart, 2, 1, 3, 1);
        characterSelectScreen.add(characterName, 4, 0, 1, 1);
        characterSelectScreen.add(advanceButton, 5, 5, 1, 1);
        Scene scene = new Scene(characterSelectScreen, MAX_WIDTH, MAX_HEIGHT);
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
        //hBox.setPrefSize(1000, 250);
        hBox.setSpacing(10);
        hBox.setPrefSize(1000, 200);
        hBox.getChildren().addAll(jobButtonList);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private HBox characterPortrait() {
        CharacterPortrait characterPortrait = ally.getCharacterPortrait();
        ImageView portrait = characterPortrait.getPortrait();
        portrait.setFitHeight(500);
        portrait.setFitWidth(400);
        //portrait.preserveRatioProperty();
        HBox hBox = new HBox();
        hBox.getChildren().add(portrait);
        return hBox;
    }

    private HBox abilityIcons() {
        HBox hBox = new HBox();
        for (Ability ability : ally.getCharacterJob().getJobAbilityList()) {
            Label icon = new Label(ability.getAbilityName());
           // icon.setPrefSize();
            hBox.getChildren().add(icon);
        }
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
       // hBox.setPrefSize(500, 250);
        return hBox;
    }

    private BarChart<Number, String> statChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        xAxis.setLabel("Value");
        yAxis.setLabel("Stat");
        BarChart<Number,String> bc = new BarChart<>(xAxis, yAxis);
        bc.setLegendVisible(false);

        XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        StatSheet statSheet = ally.getCharacterStatSheet();
        series1 = ally.getCharacterJob().getJobStatData(statSheet);
        bc.getData().add(series1);
        //bc.setPrefSize(500, 250);
        this.bc = bc;
        return bc;
    }

    private HBox characterName() {
        HBox hBox = new HBox();
        Label characterName = new Label(ally.getCharacterName());
        hBox.getChildren().add(characterName);
        hBox.setAlignment(Pos.CENTER);
       // hBox.setPrefSize(375, 250);
        return hBox;
    }

    private Button advanceButton() {
        this.advanceButton = new Button("Advance");
        advanceButton.setOnAction(this);
        advanceButton.setAlignment(Pos.BOTTOM_RIGHT);
        return advanceButton;
    }

    @Override
    public void handle(ActionEvent event) {
        for (JobButton jobButton : jobButtonList) {
            if (event.getSource() == jobButton) {
                ally.setJob(jobButton.getJob());
                XYChart.Series<Number, String> graph = ally.getCharacterJob().getJobStatData(ally.getCharacterStatSheet());
                bc.setAnimated(false);
                bc.getData().clear();
                bc.setAnimated(true);
                bc.getData().add(graph);
            }
        }
        if (event.getSource() == advanceButton) {
            // advance to next character or choose scenario
        }
    }
}
