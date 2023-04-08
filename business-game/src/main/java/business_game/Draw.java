package business_game;

import business_game.Camera;
import business_game.game_engine.Sprite;
import business_game.Vector2;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Draw {
    private static GraphicsContext canvas;

    public static void init(Canvas canvas) {
        setCanvas(canvas);
    }

    public static void setCanvas(Canvas canvas) {
        Draw.canvas = canvas.getGraphicsContext2D();
    }

    public static double getWidth() {
        return canvas.getCanvas().getWidth();
    }

    public static double getHeight() {
        return canvas.getCanvas().getHeight();
    }

    private static Camera camera;

    public static void setCamera(Camera camera) {
        Draw.camera = camera;
    }

    public static Vector2 transformPosition(double x, double y) {
        Vector2 position = new Vector2(x, y);
        if (Draw.camera == null)
            return position;
        return Draw.camera.worldToScreen(position);
    }

    public static double transformSize(double size) {
        if (Draw.camera == null)
            return size;
        return Draw.camera.zoomScreen(size);
    }

    public static void fill(Color color) {
        canvas.setFill(color);
    }

    public static void background(Color color) {
        fill(color);
        canvas.fillRect(0, 0, getWidth(), getHeight());
    }

    public static void rect(double x, double y, double width, double height) {
        Vector2 position = transformPosition(x, y);
        width = transformSize(width);
        height = transformSize(height);

        canvas.fillRect(position.x, position.y, width, height);
    }

    public static void circle(double x, double y, double radius) {
        Vector2 position = transformPosition(x, y);
        radius = transformSize(radius);

        canvas.fillOval(position.x, position.y, radius, radius);
    }

    private static double pixel_size = 1;

    public static void setPixelSize(double pixel_size) {
        Draw.pixel_size = pixel_size;
    }

    public static double getPixelSize() {
        return pixel_size;
    }

    public static void sprite(Sprite sprite, double x, double y) {
        sprite(sprite, x, y, false, false);
    }

    public static void sprite(Sprite sprite, double x, double y, boolean flip_x) {
        sprite(sprite, x, y, flip_x, false);
    }

    public static void sprite(Sprite sprite, double x, double y, boolean flip_x, boolean flip_y) {
        int flip_x_int = flip_x ? -1 : 1;
        int flip_y_int = flip_y ? -1 : 1;

        x += sprite.offset.x * pixel_size * flip_x_int;
        y += sprite.offset.y * pixel_size * flip_y_int;

        Vector2 position = transformPosition(x, y);

        Image image = sprite.getImage();

        double width = transformSize(pixel_size * image.getWidth() * flip_x_int);
        double height = transformSize(pixel_size * image.getHeight() * flip_y_int);

        canvas.setImageSmoothing(false);
        canvas.drawImage(image, position.x, position.y, width, height);
    }

}
