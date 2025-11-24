package teammate;

import org.junit.Test;
import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

public class FileManagerTest {

    @Test
    public void testSaveAndLoad() throws Exception {
        File temp = File.createTempFile("participants", ".csv");
        FileManager fm = new FileManager();

        // Write sample CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            bw.write("id,name,email,game,skill,role,score,type\n");
            bw.write("P1,Alice,a@a.com,Chess,8,Strategist,90,Leader\n");
        }

        List<Participant> participants = fm.loadParticipants(temp.getAbsolutePath());
        assertEquals(1, participants.size());
    }
}
