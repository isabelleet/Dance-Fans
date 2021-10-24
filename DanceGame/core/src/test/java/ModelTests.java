import com.mygdx.game.model.Card;
import com.mygdx.game.model.CardDeck;
import com.mygdx.game.model.Game_Model;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Dancer;
import com.mygdx.game.model.DanceFan;
import com.mygdx.game.model.DanceFloor;
import com.mygdx.game.model.DanceFloorTile;
import com.mygdx.game.model.Coordinates;
import com.mygdx.game.Enums.Type;
import com.mygdx.game.Enums.Color;
import com.mygdx.game.Enums.PatternOccupant;
import org.junit.BeforeClass;
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


    // This test isn't finished and was started before the big refactoring by Hedy
    // If somebody is going to refactor how player turns work, might as well add this test after that
/*
    @Test
    public void actuallyChangePlayer(){
        // I guess you can start a new game in the test?
        Player player1 = gameState.players[0];
        Player player2 = gameState.players[1];
        currentPlayer = gameState.players.currentPlayer().playerTurnSlot;
        changeWhichPlayersTurnItIs();
        newPlayer = gameState.players.currentPlayer().playerTurnSlot;
        assertNotEquals(currentPlayer, newPlayer);
    }
    */


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

    // Skrevs i onödan då allt refactorerades, antagligen ta bort?
    /*
    @Test
    public void convertToMatrixTest() {
        //check if conversion was done right by looking for index mapwidthInTiles + 1 in array, or (x = 1, y = 1)
        int indexToCheck = model.danceFloor.mapWidthInTiles + 1;
        model.previewDanceFloor.newDancerOnTile(indexToCheck, new DanceFan("redDanceFan"));
        int[] coords = model.indexToCoords(indexToCheck);
        int x = coords[0];
        int y = coords[1];
        DanceFloorTile[][] matrixDanceFloor = model.convertToMatrix(model.previewDanceFloor.danceFloorTiles);
        assertEquals(matrixDanceFloor[x][y].getOccupantName(), model.previewDanceFloor.danceFloorTiles[indexToCheck].getOccupantName());
    }
    */

    @Test
    public void addADancefan() {

        Coordinates mainDancerCoordinates = model.currentPlayer().getPreviewCoordinates();
        Coordinates oneStepLeftOfMainDancer = new Coordinates(mainDancerCoordinates.getX() - 1, mainDancerCoordinates.getY());

        //get type of occupant before we add the pattern of new dance fans to the dance floor

        Type tileType = model.getPreviewDanceFloor().getType(oneStepLeftOfMainDancer);
        Color tileColor = model.getPreviewDanceFloor().getColor(oneStepLeftOfMainDancer);

        //a pattern to add a dance fan to the left of the main dancer
        PatternOccupant[][] danceMovePattern = new PatternOccupant[][]{{PatternOccupant.DANCEFAN, PatternOccupant.MAINDANCER, PatternOccupant.EMPTY}};

        model.getDanceFloor().addDFromPattern(mainDancerCoordinates, model.currentPlayer().getTransparentDanceFan(), danceMovePattern);

        //Check that a dancefan was actually added to the dancefloor
        Type updatedTileType = model.getPreviewDanceFloor().getType(oneStepLeftOfMainDancer);
        Color updatedTileColor = model.getPreviewDanceFloor().getColor(oneStepLeftOfMainDancer);


        /* This became complicated since we (should at least) add previews at once when the game begins.
          And the pattern is randomized based on which dance move cards you get. So it could sometimes work and sometimes not.
          So for now we just made some if statements to check depending on state of cards.
         */
        Boolean isDancer = (tileType == Type.MD) || (tileType == Type.DF) || (tileType == Type.TRANSDF);
        Boolean hasDancerTheCurrentPlayerColor = tileColor == model.currentPlayer().getColor();

        if (tileType == Type.EMPTY) {
            assertNotEquals(tileType, updatedTileType);
            assertNotEquals(tileColor, updatedTileColor);
        }
        else if (
                isDancer && hasDancerTheCurrentPlayerColor
        )
        {
            //Then it should stay the same
            assertEquals(tileType, updatedTileType);
        }
        else //isDancer but not same color
        {
            assertNotEquals(tileType, updatedTileType);
        };


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

        DanceFloor danceFloor = model.getDanceFloor();

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


    //List of tests one could do:


    // Not possible to move on top of other maindancer
    // Not possible to go further than active card's movement distance constraint
    // Make sure game ends if all tiles are filled (including current main dancer
    //

}