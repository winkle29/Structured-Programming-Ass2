package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class NewObjectiveTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);
    private int Difficulty(Games game){
        int index=game.getProblemNumber()-1;
        int level=index/24;
        if(level<0 || level>4){
            return -1;
        }
        return level;
    }


    private int countObjectives(Games[] objectives) {
        HashSet<Games> set = new HashSet<>();
        for (Games o : objectives)
            set.add(o);
        return set.size();
    }

    private void doTest(int difficulty) {
        Games[] out = new Games[30];
        for (int i = 0; i < out.length; i++) {
            out[i] = Games.newGame(difficulty);
            int diff = Difficulty(out[i])+1;
            assertTrue("Expected difficulty " + difficulty + ", but " + (diff == -1 ? "did not get one from the prepared objectives" : "got one of difficulty " + diff) + ": problem number " + out[i].getProblemNumber() + ".", diff == difficulty);
        }
        int unique = countObjectives(out);
        assertTrue("Expected at least 5 different objectives after calling newObjective() 25 times, but only got " + unique + ".", unique >= 5);
    }



    @Test
    public void Level1() {
        doTest(1);
    }

    @Test
    public void Level2() {
        doTest(2);
    }

    @Test
    public void Level3() {
        doTest(3);
    }

    @Test
    public void Level4() {
        doTest(4);
    }

    @Test
    public void Level5() { doTest(5); }
    
}



