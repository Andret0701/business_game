package game_engine.managers;

import java.util.ArrayList;
import java.util.HashMap;

import game_engine.types.Vector2;
import game_engine.utils.FileHandler;

public class DataSaver {
    public static DataSaver instance;

    public void setActive() {
        DataSaver.instance = this;
    }

    public DataSaver() {
        if (instance == null)
            instance = this;
    }

    public static void save(String path) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.addAll(doubles_to_string());
        strings.addAll(integers_to_string());
        strings.addAll(strings_to_string());
        strings.addAll(booleans_to_string());
        strings.addAll(vectors_to_string());
        FileHandler.writeToFile(path, strings);
    }

    public static void load(String path) {
        ArrayList<String> lines = FileHandler.readFromFile(path);

        instance.doubles.clear();
        instance.integers.clear();
        instance.strings.clear();
        instance.booleans.clear();
        instance.vectors.clear();

        for (String line : lines) {
            try {
                load_line(line);
            } catch (Exception e) {
                System.out.println("Error loading line: " + line);
            }
        }
    }

    private static void load_line(String line) {
        if (line.startsWith("double"))
            load_double(line);
        else if (line.startsWith("int"))
            load_int(line);
        else if (line.startsWith("string"))
            load_string(line);
        else if (line.startsWith("boolean"))
            load_boolean(line);
        else if (line.startsWith("vector"))
            load_vector(line);
    }

    private HashMap<String, Double> doubles = new HashMap<String, Double>();

    public static double getDouble(String name) {
        if (instance.doubles.containsKey(name))
            return instance.doubles.get(name);
        else
            return 0;
    }

    public static void setDouble(String name, double value) {
        instance.doubles.put(name, value);
    }

    private static double stringToDouble(String value) {
        return Double.parseDouble(value);
    }

    private static String doubleToString(double value) {
        return Double.toString(value);
    }

    private static ArrayList<String> doubles_to_string() {
        Double[] values = instance.doubles.values().toArray(new Double[instance.doubles.size()]);
        String[] keys = instance.doubles.keySet().toArray(new String[values.length]);
        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < values.length; i++)
            strings.add("double " + keys[i] + " = " + doubleToString(values[i]));
        return strings;
    }

    private static void load_double(String string) {
        String[] split = string.split(" ");
        String key = split[1];
        Double value = stringToDouble(split[3]);
        instance.doubles.put(key, value);
    }

    private HashMap<String, Integer> integers = new HashMap<String, Integer>();

    public static int getInt(String name) {
        if (instance.integers.containsKey(name))
            return instance.integers.get(name);
        else
            return 0;
    }

    public static void setInt(String name, int value) {
        instance.integers.put(name, value);
    }

    private static int stringToInt(String value) {
        return Integer.parseInt(value);
    }

    private static String intToString(int value) {
        return Integer.toString(value);
    }

    private static ArrayList<String> integers_to_string() {
        Integer[] values = instance.integers.values().toArray(new Integer[instance.integers.size()]);
        String[] keys = instance.integers.keySet().toArray(new String[values.length]);
        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < values.length; i++)
            strings.add("int " + keys[i] + " = " + intToString(values[i]));
        return strings;
    }

    private static void load_int(String string) {
        String[] split = string.split(" ");
        String key = split[1];
        Integer value = stringToInt(split[3]);
        instance.integers.put(key, value);
    }

    private HashMap<String, String> strings = new HashMap<String, String>();

    public static String getString(String name) {
        if (instance.strings.containsKey(name))
            return instance.strings.get(name);
        else
            return "";
    }

    public static void setString(String name, String value) {
        instance.strings.put(name, value);
    }

    private static ArrayList<String> strings_to_string() {
        String[] values = instance.strings.values().toArray(new String[instance.strings.size()]);
        String[] keys = instance.strings.keySet().toArray(new String[values.length]);
        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < values.length; i++)
            strings.add("string " + keys[i] + " = " + values[i]);
        return strings;
    }

    private static void load_string(String string) {
        String[] split = string.split(" ");
        String key = split[1];
        String value = split[3];
        instance.strings.put(key, value);
    }

    private HashMap<String, Boolean> booleans = new HashMap<String, Boolean>();

    public static boolean getBoolean(String name) {
        if (instance.booleans.containsKey(name))
            return instance.booleans.get(name);
        else
            return false;
    }

    public static void setBoolean(String name, boolean value) {
        instance.booleans.put(name, value);
    }

    private static boolean stringToBoolean(String value) {
        return Boolean.parseBoolean(value);
    }

    private static String booleanToString(boolean value) {
        return Boolean.toString(value);
    }

    private static ArrayList<String> booleans_to_string() {
        Boolean[] values = instance.booleans.values().toArray(new Boolean[instance.booleans.size()]);
        String[] keys = instance.booleans.keySet().toArray(new String[values.length]);
        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < values.length; i++)
            strings.add("boolean " + keys[i] + " = " + booleanToString(values[i]));
        return strings;
    }

    private static void load_boolean(String string) {
        String[] split = string.split(" ");
        String key = split[1];
        Boolean value = stringToBoolean(split[3]);
        instance.booleans.put(key, value);
    }

    private HashMap<String, Vector2> vectors = new HashMap<String, Vector2>();

    public static Vector2 getVector(String name) {
        if (instance.vectors.containsKey(name))
            return instance.vectors.get(name);
        else
            return new Vector2(0, 0);
    }

    public static void setVector(String name, Vector2 value) {
        instance.vectors.put(name, value);
    }

    private static Vector2 stringToVector(String value) {
        String[] split = value.split(", ");
        return new Vector2(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }

    private static String vectorToString(Vector2 value) {
        return Double.toString(value.x) + ", " + Double.toString(value.y);
    }

    private static ArrayList<String> vectors_to_string() {
        Vector2[] values = instance.vectors.values().toArray(new Vector2[instance.vectors.size()]);
        String[] keys = instance.vectors.keySet().toArray(new String[values.length]);
        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < values.length; i++)
            strings.add("vector " + keys[i] + " = " + vectorToString(values[i]));
        return strings;
    }

    private static void load_vector(String string) {
        String[] split = string.split(" ");

        String key = split[1];
        Vector2 value = stringToVector(split[3] + " " + split[4]);
        instance.vectors.put(key, value);
    }
}
