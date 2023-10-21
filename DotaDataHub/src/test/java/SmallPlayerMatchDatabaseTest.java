import org.d2database.V2.Player.SmallPlayerMatchDatabase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmallPlayerMatchDatabaseTest {
    SmallPlayerMatchDatabase testSmallPlayerDatabase = new SmallPlayerMatchDatabase();
    @Test
    public void testFetchTableName() {
        String input = "348245720";
        String expected = "SmallMatch_348245720";
        String actual = testSmallPlayerDatabase.fetchTableName(input);
        assertThat(expected, equalTo(actual));
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
    }
    @Test
    public void testTableExists() {
        String input = "SmallMatch_348245720";
        Boolean expected = true;
        Boolean actual = this.testSmallPlayerDatabase.tableExists(input);
        assertEquals(expected, actual);

        input = "SmallMatch_192960022";
        expected = true;
        actual = this.testSmallPlayerDatabase.tableExists(input);
        assertEquals(expected, actual);

        input = "SmallMatch_000000000";
        expected = false;
        actual = this.testSmallPlayerDatabase.tableExists(input);
        assertEquals(expected, actual);
    }
}
