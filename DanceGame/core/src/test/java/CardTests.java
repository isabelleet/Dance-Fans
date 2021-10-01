import com.mygdx.game.model.Card;
import com.mygdx.game.model.CardDeck;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class CardTests {

    static CardDeck deck1;
    static CardDeck deck2;

    @BeforeClass
    public static void beforeClass(){
        deck1 = CardDeck.initialDeck(0);
        deck2 = CardDeck.initialDeck(1);
    }

    @Test
    public void gettingId(){
        int[][] dancePattern = new int[][]{{0, 1, 0}, {0, 3, 0}, {1, 0, 1}};

        Card test = new Card(42, dancePattern, 5);

        assertEquals(42, test.getId());
    }

    @Test
    public void usingCards(){
        Card firstCard = deck1.getOpen().get(0);
        Card secondCard = deck1.getOpen().get(1);
        //selected card is 0 by default, that means the first card should have been replaced
        deck1.useCard();
        Card newFirstCard = deck1.getOpen().get(0);
        Card newSecondCard = deck1.getOpen().get(1);

        assertNotEquals(firstCard, newFirstCard);
        assertEquals(secondCard, newSecondCard);
    }

}
