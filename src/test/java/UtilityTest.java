import org.d2database.V2.Utility;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class UtilityTest {
    @Test
    public void testDetermineRank() {
        String testRankId = "42";
        String expected = "Archon 2";
        String actual = Utility.determineRank(testRankId);
        assertThat(expected, equalTo(actual));

        testRankId = "80";
        expected = "Immortal";
        actual = Utility.determineRank(testRankId);
        assertThat(expected, equalTo(actual));

        testRankId = "11";
        expected = "Herald 1";
        actual = Utility.determineRank(testRankId);
        assertThat(expected, equalTo(actual));
    }
    @Test
    public void testDetermineHero() {
        String testHeroId = "40";
        String expected = "Venomancer";
        String actual = Utility.determineHero(testHeroId);
        assertThat(expected, equalTo(actual));
    }
}
