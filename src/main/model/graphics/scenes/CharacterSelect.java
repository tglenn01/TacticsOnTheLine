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
import javafx.scene.layout.Pane;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.characterSystem.characterList.Cassius;
import main.model.characterSystem.characterList.Estelle;
import main.model.characterSystem.characterList.Joshua;
import main.model.characterSystem.characterList.Kloe;
import main.model.graphics.DefaultScene;
import main.model.graphics.JobButton;
import main.model.graphics.icons.AbilityIcons;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

import static main.model.characterSystem.StatSheet.SCALE_REFERENCE;

public class CharacterSelect extends DefaultScene implements EventHandler<ActionEvent> {
    private final int PARTY_SIZE = 4;
    private List<CharacterUnit> partyMemberList;
    private CharacterUnit activeCharacter;
    private List<JobButton> jobButtonList;
    private Button advanceButton;
    private Button previousButton;
    private BarChart<Number, String> statChart;
    private AbilityIcons abilities;
    private Pane portrait;
    private Label characterName;
    private int characterCursor = 0;

    public CharacterSelect() {
        partyMemberList = new ArrayList<>();
        jobButtonList = new ArrayList<>();
        initializeCharacterList();
        initializeGraphics();
    }

    private void initializeCharacterList() {
        CharacterUnit joshua = new Joshua();
        CharacterUnit estelle = new Estelle();
        CharacterUnit kloe = new Kloe();
        CharacterUnit cassius = new Cassius();
        partyMemberList.add(joshua);
        partyMemberList.add(estelle);
        partyMemberList.add(kloe);
        partyMemberList.add(cassius);
        activeCharacter = partyMemberList.get(characterCursor);
    }

    protected void initializeGraphics() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Pane jobs = initializeJobButtons();
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
        Scene scene = new Scene(grid, FINAL_WIDTH, FINAL_HEIGHT);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }

    private HBox initializeJobButtons() {
        List<Job> jobList = TacticBaseBattle.getInstance().getAvailableJobs();
        jobButtonList = new ArrayList<>();
        for (Job job : jobList) {
            JobButton jobButton = new JobButton(job.getJobTitle(), job);
            jobButton.setOnAction(this);
            jobButton.setMinSize(100, 100);
            jobButtonList.add(jobButton);
        }

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinSize(1000, 160);
        hBox.getChildren().addAll(jobButtonList);

        return hBox;
    }

    private Pane characterPortrait() {
        Pane window = new Pane();
        ImageView portrait = activeCharacter.getCharacterPortrait();
        portrait.fitWidthProperty().bind(window.widthProperty());
        portrait.fitHeightProperty().bind(window.heightProperty());
        portrait.setPreserveRatio(false);
        window.getChildren().add(portrait);
        window.setPrefSize(400, 550);
        //portrait.setFitWidth(400);
        //portrait.setFitHeight(550);
        return window;
    }

    private AbilityIcons abilityIcons() {
        AbilityIcons icons = new AbilityIcons(activeCharacter.getCharacterJob().getJobAbilityList());
        return icons;
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
        //activeCharacter.getCharacterJob().getJobStatSimpleData(statChart.getData().get(0), activeCharacter.getCharacterStatSheet());
        statChart.getData().add(series1);
        statChart.setMinSize(600, 360);
        this.statChart = statChart;

        return statChart;
    }

    private Label characterName() {
        Label characterName = new Label(activeCharacter.getCharacterName());

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
            if (characterCursor == (PARTY_SIZE - 1)) {
                TacticBaseBattle.getInstance().setPartyMemberList(partyMemberList);
                TacticBaseBattle.getInstance().scenarioSelect();
            }
            else {
                characterCursor++;
                nextCharacter(characterCursor);
            }
        }
    }

    private void nextCharacter(int newCharacter) {
        activeCharacter = partyMemberList.get(newCharacter);
        updateData();
        characterName.setText(activeCharacter.getCharacterName());
        portrait.getChildren().clear();
        portrait.getChildren().add(this.characterPortrait());
    }

    private void updateData() {
        activeCharacter.getCharacterJob().getJobStatSimpleData(statChart.getData().get(0), activeCharacter.getCharacterStatSheet());
        abilities.getChildren().clear();
        abilities.getChildren().add(abilityIcons());
    }
}
