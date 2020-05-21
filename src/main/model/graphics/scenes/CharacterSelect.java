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
import main.model.jobSystem.jobs.*;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

import static main.model.characterSystem.CharacterPortrait.*;
import static main.model.characterSystem.StatSheet.SCALE_REFERENCE;

public class CharacterSelect extends DefaultScene implements EventHandler<ActionEvent> {
    private final int PARTY_SIZE = 4;
    private List<CharacterUnit> characterUnitList;
    private CharacterUnit activeCharacter;
    private List<JobButton> jobButtonList;
    private Button advanceButton;
    private Button previousButton;
    private BarChart<Number, String> statChart;
    private HBox abilities;
    private ImageView portrait;
    private Label characterName;
    private int characterCursor = 0;

    public CharacterSelect() {
        characterUnitList = new ArrayList<>();
        jobButtonList = new ArrayList<>();
        initializeCharacterList();
        initializeGraphics();
    }

    private void initializeCharacterList() {
        CharacterUnit joshua = new PlayableCharacterUnit(new Thief(), "Joshua");
        joshua.setCharacterPortrait(JOSHUA_PORTRAIT);
        CharacterUnit estelle = new PlayableCharacterUnit(new Lancer(), "Estelle");
        estelle.setCharacterPortrait(ESTELLE_PORTRAIT);
        CharacterUnit kloe = new PlayableCharacterUnit(new Cleric(), "Kloe");
        kloe.setCharacterPortrait(KLOE_PORTRAIT);
        CharacterUnit cassius = new PlayableCharacterUnit(new Noble(), "Cassius");
        cassius.setCharacterPortrait(CASSIUS_PORTRAIT);
        characterUnitList.add(joshua);
        characterUnitList.add(estelle);
        characterUnitList.add(kloe);
        characterUnitList.add(cassius);
        activeCharacter = characterUnitList.get(characterCursor);
    }

    protected void initializeGraphics() {
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        HBox jobs = initializeJobButtons();
        this.portrait = characterPortrait();
        this.abilities = abilityIcons();
        this.statChart = statChart();
        this.characterName = characterName();
        this.advanceButton = advanceButton();
        this.previousButton = previousButton();
        grid.add(jobs, 0, 8, 10, 2);
        grid.add(portrait, 0, 0, 4, 8);
        grid.add(abilities, 4, 6, 6, 2);
        grid.add(statChart, 4, 2, 6, 4);
        grid.add(characterName, 4, 0, 6, 2);
        grid.add(advanceButton, 0, 9, 10, 2);
        grid.add(previousButton, 0, 9, 10, 2);
        GridPane.setValignment(portrait, VPos.TOP);
        GridPane.setHalignment(advanceButton, HPos.RIGHT);
        GridPane.setValignment(advanceButton, VPos.BOTTOM);
        GridPane.setHalignment(previousButton, HPos.LEFT);
        GridPane.setValignment(previousButton, VPos.BOTTOM);
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
        CharacterPortrait characterPortrait = activeCharacter.getCharacterPortrait();
        ImageView portrait = characterPortrait.getPortrait();
        portrait.setFitWidth(400);
        portrait.setFitHeight(550);
        //portrait.setPreserveRatio(true);
        return portrait;
    }

    private HBox abilityIcons() {
        HBox hBox = new HBox();
        for (Ability ability : activeCharacter.getCharacterJob().getJobAbilityList()) {
            if (ability.isUnique()) {
                Label icon = new Label(ability.getAbilityName());
                hBox.getChildren().add(icon);
            }
        }
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefSize(600, 120);
        return hBox;
    }

    private BarChart<Number, String> statChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        BarChart<Number,String> statChart = new BarChart<>(xAxis, yAxis);

        statChart.setLegendVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(SCALE_REFERENCE);

        XYChart.Series<Number, String> series1;
        StatSheet statSheet = activeCharacter.getCharacterStatSheet();
        series1 = activeCharacter.getCharacterJob().getJobStatSimpleData(statSheet);
        statChart.getData().add(series1);
        statChart.setPrefSize(600, 360);
        this.statChart = statChart;

        return statChart;
    }

    private Label characterName() {
        Label characterName = new Label(activeCharacter.getCharacterName());
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

    private Button previousButton() {
        this.previousButton = new Button("Previous");
        previousButton.setAlignment(Pos.BOTTOM_RIGHT);
        previousButton.setOnAction(this);
        return previousButton;
    }

    @Override
    public void handle(ActionEvent event) {
        for (JobButton jobButton : jobButtonList) {
            if (event.getSource() == jobButton) {
                activeCharacter.setJob(jobButton.getJob());
                updateData();
            }
        }
        if (event.getSource() == previousButton) {
            if (characterCursor == 0) new MainMenu();
            else {
                characterCursor--;
                nextCharacter(characterCursor);
            }
        }
        if (event.getSource() == advanceButton) {

            if (characterCursor == (PARTY_SIZE - 1)) new ScenarioSelectScreen();
            else {
                characterCursor++;
                nextCharacter(characterCursor);
            }
        }
    }

    private void nextCharacter(int newCharacter) {
        activeCharacter = characterUnitList.get(newCharacter);
        updateData();
        characterName.setText(activeCharacter.getCharacterName());
        portrait.setImage(activeCharacter.getCharacterPortrait().getImage());
    }

    private void updateData() {
        //XYChart.Series<Number, String> graph = activeCharacter.getCharacterJob().getJobStatSimpleData(activeCharacter.getCharacterStatSheet());
       // statChart.setAnimated(false);
        //statChart.getData().clear();
       // statChart.setAnimated(true);
        //statChart.getData().add(graph);
        activeCharacter.getCharacterJob().getJobStatSimpleData(statChart.getData().get(0), activeCharacter.getCharacterStatSheet());
        abilities.getChildren().clear();
        abilities.getChildren().add(abilityIcons());
    }
}
