package com.mygdx.game.model.Enums;

/**
 * A enum used to keep track of which color dancers have / which player dancers belong to. As of now we have red and
 * green dancers. NONE is used for empty tiles, could probably be used for other things one wants to add to the game
 * that are not dancers.
 *
 * In the future this would probably be changed to dance styles or similar, like waltz, sumba, tango etc.
 *
 * Is used in Dancer, DanceFan, DanceFloor, DanceFloorTile, FloorObject, MainDancer, Player, Model, and View.
 *
 * @author Hedy Pettersson
 */
public enum Color {
    RED, GREEN, NONE
}
