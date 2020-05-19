package main.model.boardSystem;

public class Board {
    private int boardWidth;
    private int boardHeight;
    private BoardSpace[][] boardSpaces;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        createBoardGraphics();
    }

    private void createBoardGraphics() {
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < 8; y++) {
                boardSpaces[x][y] = new BoardSpace(x, y);
            }
        }
    }
}
