package main.model.combatSystem;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.exception.AttackMissedException;
import main.exception.MenuOpenedException;
import main.exception.OutOfManaException;
import main.exception.UnitIsDeadException;
import main.model.boardSystem.Board;
import main.model.boardSystem.BoardSpace;
import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.model.combatSystem.abilities.MovementAbility;
import main.model.graphics.menus.BattleMenu;
import main.model.graphics.sceneElements.images.CharacterSprite;
import main.ui.TacticBaseBattle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Ability {
    protected String abilityName;
    protected int manaCost;
    protected int range;
    protected int areaOfEffect;
    protected String abilityDescription;

    public Ability(String abilityName, int manaCost, int range, int areaOfEffect, String abilityDescription) {
        this.abilityName = abilityName;
        this.manaCost = manaCost;
        this.range = range;
        this.areaOfEffect = areaOfEffect;
        this.abilityDescription = abilityDescription;
    }

    public String getAbilityName() {
        return abilityName;
    }

    protected abstract boolean targetsSelf();

    protected abstract boolean isAreaOfEffect();

    public void takeAction(CharacterUnit activeUnit, List<BoardSpace> chosenBoardSpaces)
            throws AttackMissedException, UnitIsDeadException {
        for (BoardSpace boardSpace : chosenBoardSpaces) {
            if (boardSpace.isOccupied()) {
                CharacterUnit receivingUnit = boardSpace.getOccupyingUnit();
                resolveEffect(activeUnit, receivingUnit);
            }
        }
        if (this.getClass() != MovementAbility.class)
            activeUnit.getExperiencePoints().addExperiencePoints(activeUnit, getAverageLevel(chosenBoardSpaces));
    }

    protected Integer getAverageLevel(List<BoardSpace> chosenBoardSpaces) {
        int totalLevels = 0;
        for (BoardSpace boardSpace : chosenBoardSpaces) {
            totalLevels += boardSpace.getOccupyingUnit().getLevel();
        }

        return totalLevels / chosenBoardSpaces.size();
    }

    protected abstract void resolveEffect(CharacterUnit activeUnit, CharacterUnit receivingUnit);

    public int getManaCost() {
        return manaCost;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    public int getAreaOfEffect() { return this.areaOfEffect;}


    public void hasEnoughMana(CharacterUnit activeUnit) throws OutOfManaException {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        if (activeUnitStatSheet.getMana() <= manaCost) throw new OutOfManaException();
    }

    public void payManaCost(CharacterUnit activeUnit) {
        StatSheet activeUnitStatSheet = activeUnit.getCharacterStatSheet();
        activeUnitStatSheet.setMana(activeUnitStatSheet.getMana() - manaCost);
    }

    public abstract boolean targetsAlly();

    public boolean isUnique() {
        return !this.abilityName.equals("Attack") &&
                !this.abilityName.equals("Defend") &&
                !this.abilityName.equals("Item") &&
                !this.abilityName.equals("Move");
    }

    public int getRange() {
        return this.range;
    }


    public void getTargets(CharacterUnit activeUnit) throws MenuOpenedException {
        if (this.targetsSelf()) activeUnit.takeAction(this, getSelfBoardSpace(activeUnit));
        else {
           List<BoardSpace> possibleBoardSpaces = getTargetedBoardSpaces(activeUnit);
           Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
            if (this.getClass() == MovementAbility.class) {
                currentBoard.displayMovementSpaces(activeUnit, possibleBoardSpaces);
            } else currentBoard.displayAbilitySpaces(possibleBoardSpaces);

            setHandlers(activeUnit, possibleBoardSpaces);
        }

    }

    public abstract List<BoardSpace> getTargetedBoardSpaces(CharacterUnit activeUnit);

    protected void setHandlers(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces) throws MenuOpenedException {
        List<CharacterUnit> possibleTargets = TacticBaseBattle.getInstance().getCurrentBoard().getPossibleTargets(possibleBoardSpaces);

        ReturnToMenuHandler returnToMenuHandler = new ReturnToMenuHandler(activeUnit, possibleBoardSpaces, possibleTargets);
        ApplyTargetHandler applyTargetHandler = new ApplyTargetHandler(activeUnit, this, possibleBoardSpaces, possibleTargets, returnToMenuHandler);
        returnToMenuHandler.setApplyTargetHandler(applyTargetHandler);

        for (CharacterUnit possibleTarget : possibleTargets) {
            possibleTarget.getCharacterSprite().addEventHandler(MouseEvent.MOUSE_CLICKED, applyTargetHandler);
        }

        for (BoardSpace boardSpace : possibleBoardSpaces) {
            boardSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, returnToMenuHandler);
        }
    }

    protected void addTargetsToTargetedBoardSpaces(BoardSpace targetBoardSpace, List<BoardSpace> targetedBoardSpaces) {
        List<BoardSpace> effectedBoardSpaces = getNormalTargetPattern(targetBoardSpace, this.areaOfEffect - 1, this);
        for (BoardSpace effectedSpace : effectedBoardSpaces) {
            if (effectedSpace.isOccupied()) targetedBoardSpaces.add(effectedSpace);
        }
    }

    protected class ReturnToMenuHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private EventHandler<MouseEvent> applyTargetHandler;

        public ReturnToMenuHandler(CharacterUnit activeUnit, List<BoardSpace> possibleBoardSpaces,
                                   List<CharacterUnit> possibleTargets) {
            this.activeUnit = activeUnit;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                removeBoardHandler(possibleBoardSpaces, this);
                removeTargetHandler(possibleTargets, applyTargetHandler);
                BattleMenu.getInstance().displayCharacterMenu(activeUnit);
                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
            }
        }

        public void setApplyTargetHandler(EventHandler<MouseEvent> applyTargetHandler) {
            this.applyTargetHandler = applyTargetHandler;
        }
    }

    protected class ApplyTargetHandler implements EventHandler<MouseEvent> {
        private CharacterUnit activeUnit;
        private Ability chosenAbility;
        private List<BoardSpace> possibleBoardSpaces;
        private List<CharacterUnit> possibleTargets;
        private EventHandler<MouseEvent> returnToMenuHandler;


        public ApplyTargetHandler(CharacterUnit activeUnit, Ability chosenAbility, List<BoardSpace> possibleBoardSpaces,
                                  List<CharacterUnit> possibleTargets, EventHandler<MouseEvent> returnToMenuHandler) {
            this.activeUnit = activeUnit;
            this.chosenAbility = chosenAbility;
            this.possibleBoardSpaces = possibleBoardSpaces;
            this.possibleTargets = possibleTargets;
            this.returnToMenuHandler = returnToMenuHandler;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                CharacterSprite targetSprite = (CharacterSprite) event.getSource();
                CharacterUnit targetUnit = targetSprite.getUnit();

                TacticBaseBattle.getInstance().getCurrentBoard().stopShowingAbilitySpaces();
                removeBoardHandler(possibleBoardSpaces, returnToMenuHandler);
                removeTargetHandler(possibleTargets, this);

                List<BoardSpace> targetedBoardSpaces = new LinkedList<>();
                addTargetsToTargetedBoardSpaces(targetUnit.getBoardSpace(), targetedBoardSpaces);

                activeUnit.takeAction(chosenAbility, targetedBoardSpaces);
            }
        }
    }

    protected void removeTargetHandler(List<CharacterUnit> possibleTargets, EventHandler<MouseEvent> applyTargetHandler) {
        for (CharacterUnit possibleTarget: possibleTargets) {
            possibleTarget.getCharacterSprite().getSprite().removeEventHandler(MouseEvent.MOUSE_CLICKED, applyTargetHandler);
        }
    }

    protected void removeBoardHandler(List<BoardSpace> boardSpaces, EventHandler<MouseEvent> returnToMenuHandler) {
        for (BoardSpace boardSpace : boardSpaces) {
            boardSpace.removeEventHandler(MouseEvent.MOUSE_CLICKED, returnToMenuHandler);
        }
    }

    public boolean endsTurn() {
        return this.abilityName.equals("Defend") || this.abilityName.equals("Deactivate");
    }

    public void setRange(int newRange) {
        this.range = newRange;
    }

    // Diamond pattern around the activeUnit, some abilities will have unique patterns which override this method
    public static List<BoardSpace> getNormalTargetPattern(BoardSpace centreSpace, int range, Ability chosenAbility) {
        List<BoardSpace> possibleBoardSpaces = new ArrayList<>();
        Board currentBoard = TacticBaseBattle.getInstance().getCurrentBoard();
        BoardSpace[][] boardSpaces = currentBoard.getBoardSpaces();
        int xPos = centreSpace.getXCoordinate();
        int yPos = centreSpace.getYCoordinate();

        for (int x = 1; x <= range; x++) {
            if (xPos - x >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos - x][yPos]); // left
            if (xPos + x < currentBoard.getBoardWidth())
                possibleBoardSpaces.add(boardSpaces[xPos + x][yPos]); // right
            if (yPos + x < currentBoard.getBoardHeight())
                possibleBoardSpaces.add(boardSpaces[xPos][yPos + x]); // up
            if (yPos - x >= 0)
                possibleBoardSpaces.add(boardSpaces[xPos][yPos - x]); // down

            for (int y = 1; y <= range; y++) {
                if (x + y <= range) {
                    if ((xPos - x >= 0) && (yPos - y >= 0))
                        possibleBoardSpaces.add(boardSpaces[xPos - x][yPos - y]); // top left
                    if ((xPos + x < currentBoard.getBoardWidth()) && (yPos - y >= 0))
                        possibleBoardSpaces.add(boardSpaces[xPos + x][yPos - y]); // top right
                    if ((xPos - x >= 0) && (yPos + y < currentBoard.getBoardHeight()))
                        possibleBoardSpaces.add(boardSpaces[xPos - x][yPos + y]); // bottom left
                    if ((xPos + x < currentBoard.getBoardWidth()) && (yPos + y < currentBoard.getBoardHeight()))
                        possibleBoardSpaces.add(boardSpaces[xPos + x][yPos + y]); // bottom right
                }
            }
        }

        possibleBoardSpaces.add(centreSpace);

        return possibleBoardSpaces;
    }

    protected List<BoardSpace> getSelfBoardSpace(CharacterUnit activeUnit) {
        LinkedList<BoardSpace> boardSpaces = new LinkedList<>();
        boardSpaces.add(activeUnit.getBoardSpace());
        return boardSpaces;
    }
}
