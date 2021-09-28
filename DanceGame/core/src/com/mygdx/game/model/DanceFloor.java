package com.mygdx.game.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DanceFloor implements Serializable {

    public DanceFloorTile[] danceFloorTiles;
    //TODO: maybe remove if not used?
    private Enum<PlayerTurnSlot> whichPlayersTurnItIs;
    // Map
    public transient TiledMap map;
    private transient AssetManager manager;

    // Map properties
    public int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            tileSideLength,
            mapWidthInPixels, mapHeightInPixels;


    public DanceFloor(Enum<PlayerTurnSlot> whichPlayersTurnItIs){
        this.whichPlayersTurnItIs = whichPlayersTurnItIs;
        // Used this guide: http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
        // Code: https://github.com/angelnavarro/Gdx-MyExamples/blob/master/gdx-tiled-draw-map/core/src/com/pixnbgames/tiled/draw_map/MyGdxTiledGame.java
        manager = new AssetManager();
        manager.setLoader(TiledMap .class, new TmxMapLoader());
        manager.load("maps/BasicDanceFloor.tmx", TiledMap.class);
        manager.finishLoading();

        map = manager.get("maps/BasicDanceFloor.tmx", TiledMap.class);

        // Read properties
        MapProperties properties = map.getProperties();
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        tileSideLength = tileHeight;
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        this.danceFloorTiles = new DanceFloorTile[mapHeightInTiles * mapWidthInTiles];
    }
    // this is used to make a copy of a dancefloor, for our previews
    // https://stackoverflow.com/a/9834683
    // https://howtodoinjava.com/java/serialization/how-to-do-deep-cloning-using-in-memory-serialization-in-java/
    public DanceFloor deepCopy() throws Exception
    {
        //Serialization of object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(this);

        //De-serialization of object
        ByteArrayInputStream bis = new   ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        DanceFloor copied = (DanceFloor) in.readObject();

        //Verify that object is not corrupt

        //validateNameParts(fName);
        //validateNameParts(lName);

        return copied;
    }


    //TODO: Use this to test end of game conditions e.g.
    public DanceFloorTile[] initializeFullDanceFloor(int dancefloorWidth, int dancefloorHeight) {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.danceFloorTiles.length*dancefloorHeight) - dancefloorWidth - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");
            else if (i % 2 == 0)
                this.danceFloorTiles[i] = new DanceFloorTile("redDancer");
            else
                this.danceFloorTiles[i] = new DanceFloorTile("greenDancer");

        }
        return this.danceFloorTiles;
    }
    public DanceFloorTile[] initializeDanceFloorWithStartPositions() {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.mapWidthInTiles*this.mapHeightInTiles) - this.mapWidthInTiles - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");

            else
                this.danceFloorTiles[i] = new DanceFloorTile("transparent_tile");

        }
        return this.danceFloorTiles;
    }

    public DanceFloorTile[] initializeDanceFloor() {
        int i;
        for (i = 0; i < this.danceFloorTiles.length; i++) {

/*            if (i == 11)
                this.danceFloorTiles[i] = new DanceFloorTile("redMainDancer");
            else if (i == ((this.mapWidthInTiles*this.mapHeightInTiles) - this.mapWidthInTiles - 2) )
                this.danceFloorTiles[i] = new DanceFloorTile("greenMainDancer");

            else
*/


            //TODO: an empty tiles shouldn't be a dancer, fix later. Prob. dancer just one case of "object on floor"
            // added player1 here just for the time being if it doesn't make sense
                this.danceFloorTiles[i] = new DanceFloorTile("transparent_tile");

        }
        return this.danceFloorTiles;
    }




    public void removeDancerFromTileIndex(int tileIndex) {
        //TODO: prob not just have empty string to mean no dancer...
        this.danceFloorTiles[tileIndex] = new DanceFloorTile("transparent_tile");
    }

    //TODO: not sure if should update Dance Fan, or just replace it.
    // Depends on if Dance Fans have any data in them we want to keep even if they change which Main Dancer they're fan of
    public void newDancerOnTile(int tileIndex, Dancer dancer){
        this.danceFloorTiles[tileIndex].setOccupant(dancer);
    }







}
