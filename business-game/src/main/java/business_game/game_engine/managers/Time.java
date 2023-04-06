package business_game.game_engine.managers;

public class Time {
    private static long timer_start = 0;

    public static void init() {
        timer_start = System.currentTimeMillis();
        last_frame = System.nanoTime();
    }

    public static double getGameTime() {
        long diff = System.currentTimeMillis() - timer_start;
        return diff / 1000.0;
    }

    public static double delta_time = 0;
    private static long last_frame = 0;

    private static void updateDeltaTime() {
        long new_frame = System.nanoTime();
        delta_time = (new_frame - last_frame) / 1000000000.0;
        last_frame = new_frame;
    }

    public static void update() {
        Time.updateDeltaTime();
    }
}
