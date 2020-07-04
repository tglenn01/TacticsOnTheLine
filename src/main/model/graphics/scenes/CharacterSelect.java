package main.model.graphics.scenes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.characterList.*;
import main.model.graphics.DefaultScene;
import main.model.graphics.JobButton;
import main.model.graphics.sceneElements.images.CharacterNameLabel;
import main.model.graphics.sceneElements.list.AbilitiesList;
import main.model.graphics.sceneElements.list.CharacterStatChart;
import main.model.jobSystem.Job;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.List;

public class CharacterSelect extends DefaultScene {
    private int partySize;
    private List<CharacterUnit> partyMemberList;
    private CharacterUnit activeCharacter;
    private List<JobButton> jobButtonList;
    private CharacterStatChart statChart;
    private AbilitiesList abilities;
    private Pane portrait;
    private Label characterName;
    private int characterCursor = 0;

    // called when you open this screen for the first time
    public CharacterSelect() {
        partyMemberList = new ArrayList<>();
        jobButtonList = new ArrayList<>();
        initializeCharacterList();
        initializeGraphics();
    }

    // called when you have already picked your party
    public CharacterSelect(List<CharacterUnit> partyMemberList) {
        this.partyMemberList = partyMemberList;
        partySize = partyMemberList.size();
        activeCharacter = partyMemberList.get(partySize - 1);
        characterCursor = partySize - 1;
        initializeGraphics();
    }

    private void initializeCharacterList() {
        CharacterUnit graham = new Graham();
        CharacterUnit harry = new Harry();
        CharacterUnit willow = new Willow();
        CharacterUnit no1 = new No1();
        CharacterUnit liam = new Liam();
        partyMemberList.add(graham);
        partyMemberList.add(harry);
        partyMemberList.add(willow);
        partyMemberList.add(no1);
        partyMemberList.add(liam);
        TacticBaseBattle.getInstance().setPartyMemberList(partyMemberList);
        partySize = partyMemberList.size();
        activeCharacter = partyMemberList.get(characterCursor);
    }

    protected void initializeGraphics() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Pane jobs = initializeJobButtons();
        this.portrait = characterPortrait();
        this.abilities = new AbilitiesList(activeCharacter.getAbilityList(), 600, 120);
        this.statChart = new CharacterStatChart(activeCharacter, 600, 360,
                new NumberAxis(), new CategoryAxis());
        this.characterName = new CharacterNameLabel(activeCharacter, 600, 120);
        Button advanceButton = advanceButton();
        Button previousButton = previousButton();
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
        GridPane.setHalignment(characterName, HPos.CENTER);
        Scene scene = new Scene(grid, FINAL_WIDTH, FINAL_HEIGHT);
        addCSS(scene);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(scene);
    }

    private HBox initializeJobButtons() {
        List<Job> jobList = TacticBaseBattle.getInstance().getAvailableJobs();
        jobButtonList = new ArrayList<>();
        for (Job job : jobList) {
            JobButton jobButton = new JobButton(job.getJobTitle(), job);
            jobButton.setOnAction(e -> {
                activeCharacter.setJob(job);
                updateData();
            });
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
        window.setPrefSize(400, 640);
        return window;
    }

    private Button advanceButton() {
        Button advanceButton = new Button("Advance");
        advanceButton.setAlignment(Pos.BOTTOM_RIGHT);
        advanceButton.setOnAction(e -> {
            if (characterCursor == partySize - 1) new ScenarioSelectScreen();
            else {
                characterCursor++;
                nextCharacter();
            }
        });
        return advanceButton;
    }

    private Button previousButton() {
        Button previousButton = new Button("Previous");
        previousButton.setAlignment(Pos.BOTTOM_RIGHT);
        previousButton.setOnAction(e -> {
            if (characterCursor == 0) new TitleScreen();
            else {
                characterCursor--;
                nextCharacter();
            }
        });
        return previousButton;
    }

    private void nextCharacter() {
        activeCharacter = partyMemberList.get(characterCursor);
        updateData();
        characterName.setText(activeCharacter.getCharacterName());
        portrait.getChildren().clear();
        portrait.getChildren().add(this.characterPortrait());
    }


    private void updateData() {
        abilities.updateData(activeCharacter.getAbilityList());
        statChart.updateData(activeCharacter);
    }
}
