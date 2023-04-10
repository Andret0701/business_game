package business_game;

import java.util.ArrayList;
import java.util.List;

import business_game.game_engine.Sprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Draw {
    private static GraphicsContext canvas;

    public static void init(Canvas canvas) {
        setCanvas(canvas);

        // draw from center
        canvas.getGraphicsContext2D().setTransform(1, 0, 0, 1, 0, 0);

        // move canvas to center
        Draw.canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        Draw.canvas.scale(1, -1);
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

    public static void fill(Color color) {
        canvas.setFill(color);
    }

    public static void background(Color color) {
        fill(color);
        canvas.fillRect(-getWidth() / 2, -getHeight() / 2, getWidth(), getHeight());
    }

    public static void rect(double x, double y, double width, double height) {
        Vector2 position = new Vector2(x, y);

        position.sub(new Vector2(width / 2.0, height / 2.0));

        canvas.fillRect(position.x, position.y, width, height);
    }

    public static void rect(double x, double y, double angle, double width, double height) {
        Vector2 top_left = new Vector2(-width / 2, -height / 2);
        Vector2 top_right = new Vector2(width / 2, -height / 2);
        Vector2 bottom_right = new Vector2(width / 2, height / 2);
        Vector2 bottom_left = new Vector2(-width / 2, height / 2);
        polygon(new ArrayList<Vector2>() {
            {
                add(top_left);
                add(top_right);
                add(bottom_right);
                add(bottom_left);
            }
        }, x, y, angle, 1);
    }

    public static void polygon(List<Vector2> points, double x, double y, double angle, double scale) {
        double pointsX[] = new double[points.size()];
        double pointsY[] = new double[points.size()];

        for (int i = 0; i < points.size(); i++) {
            Vector2 point = points.get(i);
            point.rotate(angle);
            point.mul(scale);
            point.add(new Vector2(x, y));
            pointsX[i] = point.x;
            pointsY[i] = point.y;
        }

        canvas.fillPolygon(pointsX, pointsY, points.size());
    }

    /*
     * public static void rect(Transform transform, double width, double height) {
     * Vector2 position = transformPosition(transform.position.x,
     * transform.position.y);
     * width = transformSize(width);
     * height = transformSize(height);
     * 
     * position.sub(new Vector2(width / 2.0, height / 2.0));
     * 
     * canvas.save();
     * canvas.translate(position.x, position.y);
     * canvas.rotate(-Math.toDegrees(transform.angle));
     * canvas.fillRect(0, 0, width, height);
     * canvas.restore();
     * }
     */

    public static void circle(double x, double y, double radius) {
        Vector2 position = new Vector2(x, y);

        radius *= 2;
        position.sub(new Vector2(radius / 2.0, radius / 2.0));
        canvas.fillOval(position.x, position.y, radius, radius);
    }

    private static double pixel_size = 5;

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
        sprite(sprite, x, y, flip_x, flip_y, 0);
    }

    public static void sprite(Sprite sprite, double x, double y, boolean flip_x, boolean flip_y, double angle) {
        int flip_x_int = flip_x ? 1 : -1; // because of the way the image is drawn
        int flip_y_int = flip_y ? -1 : 1;

        double offset_x = sprite.offset.x * pixel_size * flip_x_int;
        double offset_y = sprite.offset.y * pixel_size * flip_y_int;

        Vector2 position = new Vector2(x, y);

        Image image = sprite.getImage();

        double width = pixel_size * image.getWidth() * flip_x_int;
        double height = pixel_size * image.getHeight() * flip_y_int;

        canvas.save();
        canvas.translate(position.x, position.y);
        canvas.rotate(Math.toDegrees(angle));
        canvas.translate(offset_x, offset_y);
        canvas.setImageSmoothing(false);
        canvas.drawImage(image, 0, 0, width, height);
        canvas.restore();
    }

}
