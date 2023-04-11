package business_game.game_engine.managers;

import java.util.List;

import business_game.game_engine.gfx.Sprite;
import business_game.game_engine.gfx.SpriteRenderer;
import business_game.game_engine.types.Vector2;
import business_game.game_engine.types.Vector2Int;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Draw {
    private static GraphicsContext canvas;

    public static void init(Canvas canvas) {
        setCanvas(canvas);

        // draw from center
        canvas.getGraphicsContext2D().setTransform(1, 0, 0, 1, 0, 0);

    }

    private static void translateCanvas(double x, double y, double offset_x, double offset_y, double angle) {
        Draw.canvas.save();
        Draw.canvas.translate(getWidth() / 2, getHeight() / 2);
        Draw.canvas.scale(1, -1);

        Draw.canvas.translate(x, y);
        Draw.canvas.rotate(Math.toDegrees(angle));
        Draw.canvas.translate(offset_x, offset_y);
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
        canvas.fillRect(0, 0, getWidth(), getHeight());
    }

    public static void rect(double x, double y, double width, double height) {
        Vector2 position = new Vector2(x, y);

        position.sub(new Vector2(width / 2.0, height / 2.0));

        canvas.fillRect(position.x, position.y, width, height);
    }

    public static void rect(double x, double y, double angle, double width, double height) {
        translateCanvas(x, y, -width / 2, -height / 2, angle);
        canvas.fillRect(0, 0, width, height);
        Draw.canvas.restore();
    }

    public static void polygon(List<Vector2> points, double x, double y, double angle, double scale) {
        double pointsX[] = new double[points.size()];
        double pointsY[] = new double[points.size()];

        for (int i = 0; i < points.size(); i++) {
            Vector2 point = points.get(i);
            point.mul(scale);
            pointsX[i] = point.x;
            pointsY[i] = point.y;
        }

        translateCanvas(x, y, 0, 0, angle);
        canvas.fillPolygon(pointsX, pointsY, points.size());
        Draw.canvas.restore();
    }

    public static void circle(double x, double y, double radius) {
        Vector2 position = new Vector2(x, y);

        radius *= 2;

        translateCanvas(position.x, position.y, -radius / 2, -radius / 2, 0);
        canvas.fillOval(0, 0, radius, radius);
        Draw.canvas.restore();
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
        sprite(sprite, x, y, flip_x, flip_y, 1, 0);
    }

    public static void sprite(SpriteRenderer sprite_renderer, double x, double y, double scale, double angle) {
        if (sprite_renderer == null || sprite_renderer.sprite == null)
            return;
        sprite(sprite_renderer.sprite, x, y, sprite_renderer.flip_x, sprite_renderer.flip_y, scale, angle);
    }

    public static void sprite(Sprite sprite, double x, double y, boolean flip_x, boolean flip_y, double scale,
            double angle) {
        scale *= pixel_size;
        int flip_x_int = flip_x ? -1 : 1; // because of the way the image is drawn
        int flip_y_int = flip_y ? 1 : -1;

        double offset_x = sprite.offset.x * scale * flip_x_int;
        double offset_y = sprite.offset.y * scale * flip_y_int;

        Vector2 position = new Vector2(x, y);

        Image image = sprite.getImage();

        double width = image.getWidth() * flip_x_int * scale;
        double height = image.getHeight() * flip_y_int * scale;

        translateCanvas(position.x, position.y, offset_x, offset_y, angle);
        canvas.setImageSmoothing(false);
        canvas.drawImage(image, 0, 0, width, height);
        canvas.restore();
    }

    public static void tilemap(Sprite[][] tilemap, Vector2 position, Vector2Int size, double scale, double angle) {
        scale *= pixel_size;
        Vector2 offset = new Vector2(tilemap.length * size.x * scale, tilemap[0].length * size.y * scale);
        offset.mul(0.5);

        for (int x = 0; x < tilemap.length; x++) {
            for (int y = 0; y < tilemap[x].length; y++) {
                Sprite sprite = tilemap[x][y];

                if (sprite == null)
                    continue;

                Vector2 tile_position = new Vector2(x, y);
                tile_position.mul(new Vector2(size.x * scale, size.y * scale));
                tile_position.add(new Vector2(size.x * scale, 0));
                tile_position.sub(offset);
                tile_position.rotate(angle);
                tile_position.add(position);

                sprite(sprite, tile_position.x, tile_position.y, false, false, scale, angle);
            }
        }
    }

}
