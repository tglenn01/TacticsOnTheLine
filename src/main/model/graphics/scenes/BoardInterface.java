package main.model.graphics.scenes;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
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
                Rectangle space = boardSpace.getSpace();
                int xCoordinate = boardSpace.getXCoordinate();
                int yCoordinate = boardSpace.getYCoordinate();
                boardLayout.add(space, xCoordinate, yCoordinate, 1, 1);
            }
        }
        boardLayout.setMaxSize(board.getBoardWidth(), board.getBoardHeight());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(boardLayout);
        //boardLayout.setMaxSize(board.getBoardWidth(), board.getBoardHeight());
        Scene newScene = new Scene(scrollPane, MAX_WIDTH, MAX_HEIGHT);
        TacticBaseBattle.getInstance().getPrimaryStage().setScene(newScene);
    }
}
