package main.model.graphics.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.graphics.DefaultScene;
import main.ui.TacticBaseBattle;

public class BoardInterface extends DefaultScene {
    private Board board;

    public BoardInterface(Board board) {
        this.board = board;
        initializeGraphics();
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
        //ScrollPane scrollPane = new ScrollPane();
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //scrollPane.setFitToWidth(true);
        //scrollPane.setFitToHeight(true);
        //scrollPane.setContent(boardLayout);
        Scene newScene = new Scene(boardLayout);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(newScene);
    }
}
