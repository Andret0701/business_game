package game_engine.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void writeToFile(String path, List<String> strings) {
        File file = new File(path);
        if (!file.exists())
            file = createFile(file);

        try (FileWriter writer = new FileWriter(file)) {
            for (String string : strings)
                writer.write(string + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    public static ArrayList<String> readFromFile(String path) {
        ArrayList<String> strings = new ArrayList<String>();

        File file = new File(path);
        if (!file.exists())
            return strings;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                strings.add(trimmed);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        return strings;
    }

    private static File createFile(File file) {
        boolean created = false;
        try {
            created = file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }

        if (!created)
            throw new RuntimeException("Error creating file: " + file.getAbsolutePath());

        return file;
    }
}
