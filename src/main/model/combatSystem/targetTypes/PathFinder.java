package main.model.combatSystem.targetTypes;

import main.model.boardSystem.BoardSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFinder {
    private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
    private final BoardSpace[][] board;
    private Node now;
    private final int xStart;
    private final int yStart;
    private int xEnd, yEnd;


    public PathFinder(BoardSpace[][] board, BoardSpace initialBoardSpace) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.board = board;
        int initialXPos = initialBoardSpace.getXCoordinate();
        int initialYPos = initialBoardSpace.getYCoordinate();
        this.now = new Node(null, board[initialXPos][initialYPos], 0, 0);
        this.xStart = initialXPos;
        this.yStart = initialYPos;
    }

    // Node class for convenience
    static class Node implements Comparable {
        public Node parent;
        public int x, y;
        public double g;
        public double h;
        public BoardSpace boardSpace;

        Node(Node parent, BoardSpace boardSpace, double g, double h) {
            this.parent = parent;
            this.boardSpace = boardSpace;
            this.x = boardSpace.getXCoordinate();
            this.y = boardSpace.getYCoordinate();
            this.g = g;
            this.h = h;
        }

        // Compare by f value (g + h)
        @Override
        public int compareTo(Object o) {
            Node that = (Node) o;
            return (int) ((this.g + this.h) - (that.g + that.h));
        }
    }



    /*
     ** Finds path to xend/yend or returns null
     **
     ** @param (int) xend coordinates of the target position
     ** @param (int) yend
     ** @return (List<Node> | null) the path
     */
    public List<BoardSpace> findPathTo(BoardSpace destination) {
        this.xEnd = destination.getXCoordinate();
        this.yEnd = destination.getYCoordinate();
        this.closed.add(this.now);
        addNeigborsToOpenList();
        while (this.now.x != this.xEnd || this.now.y != this.yEnd) {
            if (this.open.isEmpty()) { // Nothing to examine
                return null;
            }
            this.now = this.open.get(0); // get first node (lowest f score)
            this.open.remove(0); // remove it
            this.closed.add(this.now); // and add to the closed
            addNeigborsToOpenList();
        }
        this.path.add(0, this.now);
        while (this.now.x != this.xStart || this.now.y != this.yStart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        List<BoardSpace> boardSpacePath = new ArrayList<>();
        for (Node node : path) {
            boardSpacePath.add(node.boardSpace);
        }
        return boardSpacePath;
    }

    public List<BoardSpace> findAreaAround(int movementPoints) {
        this.closed.add(this.now);
        addNeigborsToOpenList();
        while (!open.isEmpty()) {
            this.now = this.open.get(0); // get first node (lowest f score)
            this.open.remove(0); // remove it
            this.closed.add(this.now); // and add to the closed
            addNeigborsToOpenList();
        }
        while (this.now.x != this.xStart || this.now.y != this.yStart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        List<BoardSpace> boardSpacePath = new ArrayList<>();
        for (Node node : path) {
            boardSpacePath.add(node.boardSpace);
        }
        return boardSpacePath;
    }

    /*
     ** Looks in a given List<> for a node
     **
     ** @return (bool) NeightborInListFound
     */
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }

    /*
     ** Calulate distance between this.now and xend/yend
     **
     ** @return (int) distance
     */
    private double distance(int dx, int dy) {
        return Math.abs(this.now.x + dx - this.xEnd) + Math.abs(this.now.y + dy - this.yEnd); // else return "Manhattan distance"
    }

    private void addNeigborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (validSpace(x, y) && (x != 0 || y != 0)) { // is valid and not this.now
                    node = new Node(this.now, board[this.now.x + x][this.now.y + y], this.now.g, this.distance(x, y));
                    if (!findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) {
                        node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
                        this.open.add(node);
                    }
                }
            }
        }

        Collections.sort(this.open);
    }

    private boolean validSpace(int x, int y) {
        return this.now.x + x >= 0 && this.now.x + x < this.board[0].length // check maze boundaries
                && this.now.y + y >= 0 && this.now.y + y < this.board.length
                && this.board[this.now.x + x][this.now.y + y].isTerrainable() // check if square is walkable
                && (Math.abs(x) - Math.abs(y) != 0); // check if is a diagonal
    }
}
