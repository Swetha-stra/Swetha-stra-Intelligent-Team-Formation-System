package teammate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataProcessorThreadTest {

    @Test
    public void testThreadProcessing() throws Exception {
        List<Participant> list = new ArrayList<>();
        list.add(new Participant("1", "Test", "t@t.com", "Chess", 5, "Strategist", 80, "Balanced"));

        TeamBuilder builder = new TeamBuilder(1);
        DataProcessorThread thread = new DataProcessorThread(builder, list);

        thread.start();
        thread.join();

        assertNotNull(thread.getFormedTeams());
        assertEquals(1, thread.getFormedTeams().size());
    }
}
