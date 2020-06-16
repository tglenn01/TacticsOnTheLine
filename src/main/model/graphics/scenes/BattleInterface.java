package main.model.graphics.scenes;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.graphics.DefaultScene;
import main.model.scenarioSystem.Scenario;
import main.ui.Battle;
import main.ui.TacticBaseBattle;

public class BattleInterface extends DefaultScene {
    private Board board;
    private Battle battle;

    public BattleInterface(Board board, Scenario scenario) {
        this.board = board;
        initializeGraphics();
        battle = new Battle(TacticBaseBattle.getInstance().getPartyMemberList(), scenario);
    }

    protected void initializeGraphics() {
        GridPane boardLayout = new GridPane();
        for (BoardSpace[] boardSpaceArray : board.getBoardSpaces()) {
            for (BoardSpace boardSpace : boardSpaceArray) {
                int xCoordinate = boardSpace.getXCoordinate();
                int yCoordinate = boardSpace.getYCoordinate();
                boardLayout.add(boardSpace.getBoardSpace(), xCoordinate, yCoordinate, 1, 1);
            }
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(boardLayout);
        Scene newScene = new Scene(scrollPane);
        addCSS(newScene);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(newScene);
    }
}
