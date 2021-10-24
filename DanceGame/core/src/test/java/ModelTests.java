import com.mygdx.game.model.Game_Model;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.DanceFloor;
import com.mygdx.game.model.Coordinates;
import com.mygdx.game.Enums.Type;
import com.mygdx.game.Enums.PatternOccupant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
Notes from peer review feedback:
One should maybe not make a constructor only for testing,
the tests should instead call the constructor that is actually used
in code with the parameters set in these test methods
 */
public class ModelTests {

    Game_Model model;
    Player[] players;

    // @Before runs before each test (@BeforeClass runs only once)
    @Before
    public void beforeClass() {

        model = new Game_Model();
        model.startNewGame();
    }

    @Test
    public void movedMainDancerOneStepRight(){

        Coordinates mainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();

        //TODO: Check that it moved on dancefloor too? Or other test for this?

        // Move main dancer to other place
        model.moveMainDancerOfCurrentPlayerToCoords(new Coordinates(mainDancerCoordinates.getX() + 1,mainDancerCoordinates.getY()));

        Coordinates updatedMainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();

        assertEquals(mainDancerCoordinates.getX() + 1, updatedMainDancerCoordinates.getX());

    }

    @Test
    public void movedMainDancer(){

        Coordinates mainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();
        //TODO: Check on dancefloor too? Or other test for this?

        // Move main dancer to some other tile
        Coordinates stepUpAndRightOfMainDancer = new Coordinates(mainDancerCoordinates.getX() + 1, mainDancerCoordinates.getY() + 1);

        model.moveMainDancerOfCurrentPlayerToCoords(stepUpAndRightOfMainDancer);

        Coordinates updatedMainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();

        //Check if the main dancer is located at (mainDancerCurrentLocationIndex + 1) on the dancefloor
        assertEquals(mainDancerCoordinates.getX() + 1, updatedMainDancerCoordinates.getX());
        assertEquals(mainDancerCoordinates.getY() + 1, updatedMainDancerCoordinates.getY());

    }

    // Check so not possible to move outside of dancefloor
    // The test is currently relying on that the start position for the first main dancer is located in the bottom right
    @Test
    public void mainDancerCanNotMoveOutsideOfDanceFloor_Right(){

        Coordinates mainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();

        model.moveSelection(1, 0);

        Coordinates updatedMainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();

        assertEquals(mainDancerCoordinates.getX(), updatedMainDancerCoordinates.getX());
        assertEquals(mainDancerCoordinates.getY(), updatedMainDancerCoordinates.getY());
    }

    @Test
    public void mainDancerCanNotMoveOutsideOfDanceFloor_Down(){

        Coordinates mainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();

        // +y direction is apparently downward currently
        model.moveSelection(0, 1);

        Coordinates updatedMainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();

        assertEquals(mainDancerCoordinates.getX(), updatedMainDancerCoordinates.getX());
        assertEquals(mainDancerCoordinates.getY(), updatedMainDancerCoordinates.getY());
    }


    @Test
    public void turnNumberIncreases(){
        int initialTurnNumber = model.getTurns();
        model.changeWhichPlayersTurnItIs();
        model.changeWhichPlayersTurnItIs();
        int updatedTurnNumber = model.getTurns();

        assertEquals(initialTurnNumber + 1, updatedTurnNumber);
    }

    // Make sure that the phase happens where cards are not shown until next player press 'd'
    @Test
    public void endTurnMakesNextPlayersTurnNotStarted() {
        boolean isTurnStarted = model.getHasPlayerStartedTheirTurn();
        model.playerConfirmedDanceMove();
        boolean updatedIsTurnStarted = model.getHasPlayerStartedTheirTurn();

        assertNotEquals(isTurnStarted, updatedIsTurnStarted );
    }

    @Test
    public void turnStarts() {
        model.playerConfirmedDanceMove();
        boolean isTurnStarted = model.getHasPlayerStartedTheirTurn();
        model.playerDrewCardsToStartTurn();
        boolean updatedIsTurnStarted = model.getHasPlayerStartedTheirTurn();
        assertNotEquals(isTurnStarted, updatedIsTurnStarted );
    }

    @Test
    public void addADancefan() {
        PatternOccupant E = PatternOccupant.EMPTY;
        PatternOccupant DF = PatternOccupant.DANCEFAN;
        PatternOccupant MD = PatternOccupant.MAINDANCER;

        Coordinates mainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();
        Coordinates threeStepsLeftOfMainDancer = new Coordinates(mainDancerCoordinates.getX() - 3, mainDancerCoordinates.getY());

        Type tileType = model.getPreviewDanceFloor().getType(threeStepsLeftOfMainDancer);

        PatternOccupant[][] danceMovePattern = new PatternOccupant[][]
                {{DF, E, E, MD, E, E, E}};

        model.getDanceFloor().addDFromPattern(mainDancerCoordinates, model.currentPlayer().getTransparentDanceFan(), danceMovePattern);
        model.changeWhichPlayersTurnItIs();

        Type updatedTileType = model.getDanceFloor().getType(threeStepsLeftOfMainDancer);

        assertNotEquals(tileType, updatedTileType);

    }

    @Test
    public void gameEndsAfterMaximumTurnsLimit(){

        for (int i = 0; i <= model.getMaximumTurns(); i++) {

            model.playerDrewCardsToStartTurn();
            if (model.getHasPlayerStartedTheirTurn()) {
                model.playerConfirmedDanceMove();
            }
        }

        assertTrue(model.isGameDone());
    }

    @Test
    public void playerOneLeadingTest(){
        PatternOccupant E = PatternOccupant.EMPTY;
        PatternOccupant DF = PatternOccupant.DANCEFAN;
        PatternOccupant MD = PatternOccupant.MAINDANCER;

        Coordinates testCoords = new Coordinates(6, 4);

        DanceFloor danceFloor = model.getDanceFloor();

        PatternOccupant[][] pattern = new PatternOccupant[][]{
                {E, DF, E},
                {DF, MD, DF},
                {E, DF, E}};
        danceFloor.addDFromPattern(testCoords, model.currentPlayer().getTransparentDanceFan(), pattern);

        assertEquals(model.whichPlayerIsLeading(), 0);
    }

    @Test
    public void playerTwoLeadingTest(){
        PatternOccupant E = PatternOccupant.EMPTY;
        PatternOccupant DF = PatternOccupant.DANCEFAN;
        PatternOccupant MD = PatternOccupant.MAINDANCER;

        Coordinates testCoords = new Coordinates(6, 4);

        DanceFloor danceFloor = model.getDanceFloor();

        PatternOccupant[][] pattern = new PatternOccupant[][]{
                {E, DF, E},
                {DF, MD, DF},
                {E, DF, E}};
        model.changeWhichPlayersTurnItIs();
        danceFloor.addDFromPattern(testCoords, model.currentPlayer().getTransparentDanceFan(), pattern);

        assertEquals(model.whichPlayerIsLeading(), 1);
    }

    @Test
    public void noLeaderTest(){
        PatternOccupant E = PatternOccupant.EMPTY;
        PatternOccupant DF = PatternOccupant.DANCEFAN;
        PatternOccupant MD = PatternOccupant.MAINDANCER;

        DanceFloor danceFloor = model.getPreviewDanceFloor();

        PatternOccupant[][] pattern = new PatternOccupant[][]{
                {E, DF, E},
                {DF, MD, DF},
                {E, DF, E}};

        danceFloor.addDFromPattern(model.currentPlayer().getCoordinates(), model.currentPlayer().getTransparentDanceFan(), pattern);
        model.changeWhichPlayersTurnItIs();
        danceFloor.addDFromPattern(model.currentPlayer().getCoordinates(), model.currentPlayer().getTransparentDanceFan(), pattern);
        assertEquals(model.whichPlayerIsLeading(), 2);
    }

    @Test
    public void collisionSideTest(){
        Coordinates initCoords = new Coordinates(3, 3);
        model.currentPlayer().setCoordinates(initCoords);
        model.changeWhichPlayersTurnItIs();

        initCoords = new Coordinates(2, 3);
        model.currentPlayer().setCoordinates(initCoords);
        model.changeWhichPlayersTurnItIs();

        model.moveSelection(2, 3);

        assertNotEquals(model.currentPlayer().getCoordinates(), initCoords);
    }

    @Test
    public void collisionTopTest(){
        Coordinates initCoords = new Coordinates(3, 3);
        model.currentPlayer().setCoordinates(initCoords);
        model.changeWhichPlayersTurnItIs();

        initCoords = new Coordinates(3, 2);
        model.currentPlayer().setCoordinates(initCoords);
        model.changeWhichPlayersTurnItIs();

        model.moveSelection(3, 2);

        assertNotEquals(model.currentPlayer().getCoordinates(), initCoords);
    }

    @Test
    public void moveSelectionTest(){
        Coordinates coords = model.currentPlayer().getCoordinates();
        model.moveSelection(coords.getX()-1, coords.getY());

        assertEquals(model.currentPlayer().getCoordinates().getX(), coords.getX());
    }

}