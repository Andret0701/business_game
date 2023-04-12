package game_engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game_engine.managers.DataSaver;
import game_engine.types.Vector2;

public class DataSaverTest {

    @Test
    public void testSave() {
        DataSaver saver = new DataSaver();
        saver.setActive();

        DataSaver.setInt("testInt", 1);
        DataSaver.setDouble("testDouble", 1.0);
        DataSaver.setString("testString", "test");
        DataSaver.setBoolean("testBoolean", true);
        DataSaver.setVector("testVector", new Vector2(1, 1));

        DataSaver.save("testSave.txt");

        saver = new DataSaver();
        saver.setActive();
        DataSaver.load("testSave.txt");

        assertEquals(1, DataSaver.getInt("testInt"));
        assertEquals(1.0, DataSaver.getDouble("testDouble"));
        assertEquals("test", DataSaver.getString("testString"));
        assertEquals(true, DataSaver.getBoolean("testBoolean"));

        Vector2 vector = DataSaver.getVector("testVector");
        assertEquals(vector.x, 1);
        assertEquals(vector.y, 1);
    }
}
