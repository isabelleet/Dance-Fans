import com.mygdx.game.model.*;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.*;

public class CardTests {

    CardDeck deck1;
    CardDeck deck2;

    @BeforeClass
    public static void beforeClass(){

    }



    @Test
    public void gettingId(){
        int[][] dancePattern = new int[][]{{0, 1, 0}, {0, 3, 0}, {1, 0, 1}};

        Card test = new Card(42, dancePattern, 5);

        assertEquals(42, test.getId());
    }

    @Test
    public void usingCards(){

    }

}
