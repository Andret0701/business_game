package business_game.game_engine.managers;

import business_game.game_engine.Camera;
import business_game.game_engine.Sprite;
import business_game.game_engine.utils.Vector2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class DrawManager {
    private GraphicsContext canvas;

    public DrawManager(Canvas canvas) {
        this.canvas = canvas.getGraphicsContext2D();
    }

    public double getWidth() {
        return canvas.getCanvas().getWidth();
    }

    public double getHeight() {
        return canvas.getCanvas().getHeight();
    }

    private Camera camera;

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Vector2 transformPosition(double x, double y) {
        Vector2 position = new Vector2(x, y);
        if (camera == null)
            return position;
        return camera.worldToScreen(position);
    }

    public double transformSize(double size) {
        if (camera == null)
            return size;
        return camera.zoomScreen(size);
    }

    public void fill(Color color) {
        canvas.setFill(color);
    }

    public void background(Color color) {
        fill(color);
        canvas.fillRect(0, 0, getWidth(), getHeight());
    }

    public void rect(double x, double y, double width, double height) {
        Vector2 position = transformPosition(x, y);
        width = transformSize(width);
        height = transformSize(height);

        canvas.fillRect(position.x, position.y, width, height);
    }

    public void circle(double x, double y, double radius) {
        Vector2 position = transformPosition(x, y);
        radius = transformSize(radius);

        canvas.fillOval(position.x, position.y, radius, radius);
    }

    private double pixel_size = 5;

    public void setPixelSize(double pixel_size) {
        this.pixel_size = pixel_size;
    }

    public double getPixelSize() {
        return pixel_size;
    }

    public void sprite(Sprite sprite, double x, double y) {
        sprite(sprite, x, y, false, false);
    }

    public void sprite(Sprite sprite, double x, double y, boolean flip_x) {
        sprite(sprite, x, y, flip_x, false);
    }

    public void sprite(Sprite sprite, double x, double y, boolean flip_x, boolean flip_y) {
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
