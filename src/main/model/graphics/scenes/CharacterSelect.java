package main.model.graphics.scenes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
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
import java.util.Observable;

public class CharacterSelect extends DefaultScene {
    private int partySize;
    private List<CharacterUnit> partyMemberList;
    private CharacterUnit activeCharacter;
    private Pane jobButtonPane;
    private Observable jobButtonObserver;
    private CharacterStatChart statChart;
    private AbilitiesList abilities;
    private Pane portrait;
    private CharacterNameLabel characterName;
    private int characterCursor = 0;
    private Job activeJob;

    // called when you open this screen for the first time
    public CharacterSelect() {
        partyMemberList = new ArrayList<>();
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
        activeJob = activeCharacter.getCharacterJob();
    }

    protected void initializeGraphics() {
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(20));
        this.jobButtonPane = initializeJobButtons();
        this.portrait = characterPortrait();
        this.abilities = new AbilitiesList(activeCharacter.getAbilityList(), 600, 120);
        this.statChart = new CharacterStatChart(activeCharacter, 600, 360,
                new NumberAxis(), new CategoryAxis());
        this.characterName = new CharacterNameLabel(activeCharacter, 600, 120);
        Button advanceButton = advanceButton();
        Button previousButton = previousButton();
        grid.add(jobButtonPane, 0, 8, 10, 2);
        grid.add(portrait, 0, 0, 4, 8);
        grid.add(abilities, 4, 6, 6, 2);
        grid.add(statChart, 4, 2, 6, 4);
        grid.add(characterName, 4, 0, 6, 2);
        grid.add(advanceButton, 0, 9, 10, 2);
        grid.add(previousButton, 0, 9, 10, 2);


        GridPane.setHalignment(advanceButton, HPos.RIGHT);
        GridPane.setValignment(advanceButton, VPos.BOTTOM);
        GridPane.setHalignment(previousButton, HPos.LEFT);
        GridPane.setValignment(previousButton, VPos.BOTTOM);
        GridPane.setValignment(abilities, VPos.CENTER);
        GridPane.setValignment(statChart, VPos.CENTER);
        GridPane.setValignment(characterName, VPos.TOP);

        //fadeMainPaneToGivenPane(grid);
        Scene characterSelectScene = new Scene(grid, FINAL_WIDTH, FINAL_HEIGHT);
        grid.setId("defaultBackground");
        addCSS(characterSelectScene);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(characterSelectScene);
        //mainPane.getChildren().add(grid);
    }

    private Pane initializeJobButtons() {
        jobButtonObserver = new Observable() {
            @Override
            public void notifyObservers(Object arg) {
                setChanged();
                super.notifyObservers(arg);
            }
        };

        List<Job> jobList = TacticBaseBattle.getInstance().getAvailableJobs();
        List<Button> jobButtonList = new ArrayList<>();
        for (Job job : jobList) {
            JobButton jobButton = new JobButton(job.getJobTitle(), job);
            jobButton.setOnAction(e -> {
                if (job != activeJob) {
                    activeCharacter.setJob(job);
                    updateDisplayedCharacterData();
                    jobButtonObserver.notifyObservers(job);
                    activeJob = job;
                }
            });
            if (activeCharacter.getCharacterJob().equals(job)) jobButton.highlightButton();
            jobButton.setMinSize(100, 100);
            jobButtonList.add(jobButton);
            jobButtonObserver.addObserver(jobButton);
        }

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(jobButtonList);

        Pane jobButtonPane = new Pane();
        jobButtonPane.setId("normalNode");
        jobButtonPane.getChildren().add(hBox);
        jobButtonPane.setMinSize(1000, 200);

        DefaultScene.centreRegionOnPane(jobButtonPane, hBox);
        return jobButtonPane;
    }

    private Pane characterPortrait() {
        Pane window = new Pane();
        ImageView portrait = activeCharacter.getCharacterPortrait();
        portrait.fitWidthProperty().bind(window.widthProperty());
        portrait.fitHeightProperty().bind(window.heightProperty());
        portrait.setPreserveRatio(false);
        window.getChildren().add(portrait);
        window.setPrefSize(400, 600);
        return window;
    }

    private Button advanceButton() {
        Button advanceButton = new Button("Next");
        advanceButton.setId("normalNode");
        advanceButton.setPrefSize(60, 12);
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
        Button previousButton = new Button("Back");
        previousButton.setId("normalNode");
        previousButton.setPrefSize(60, 10);
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
        updateDisplayedCharacterData();
        characterName.updateLabel(activeCharacter);
        ImageView it = (ImageView) portrait.getChildren().get(0);
        it.setImage(activeCharacter.getCharacterPortrait().getImage());

        activeJob = activeCharacter.getCharacterJob();
        jobButtonObserver.notifyObservers(activeJob);
    }

    private void updateDisplayedCharacterData() {
        abilities.updateData(activeCharacter.getAbilityList());
        statChart.updateData(activeCharacter);
    }
}
