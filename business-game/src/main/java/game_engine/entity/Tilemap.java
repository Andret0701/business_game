package game_engine.entity;

import game_engine.Game;
import game_engine.managers.Draw;
import game_engine.types.Tile;
import game_engine.types.Vector2;
import game_engine.types.Vector2Int;
import javafx.scene.paint.Color;

public class Tilemap extends Entity implements Drawable {
    protected Tile[][] tiles;
    protected int width, height;
    protected Vector2Int num_tile_pixels;

    public Tilemap(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        num_tile_pixels = new Vector2Int(8, 8);
    }

    public void setTile(int x, int y, Tile tile) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return;
        // throw new IllegalArgumentException("Tilemap.setTile: x or y is out of
        // bounds");
        tiles[x][y] = tile;
    }

    public void fill(Tile tile) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = tile;
            }
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return null;
        return tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void draw(double x, double y, double angle, double scale) {
        Draw.tilemap(tiles, new Vector2(x, y), num_tile_pixels, scale, angle);
    }

    @Override
    public double getLayer() {
        return -1000;
    }

    @Override
    public boolean isVisable() {
        return true;
    }

    @Override
    public Entity copy() {
        Tilemap tilemap = new Tilemap(width, height);
        tilemap.transform.setTransform(transform);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tilemap.tiles[x][y] = tiles[x][y];
            }
        }

        return tilemap;
    }

    @Override
    public void update(double delta_time) {
        // transform.setAngle(transform.getAngle() + delta_time);
    }

    public Vector2 tileToWorld(int x, int y) {
        Vector2 result = new Vector2(x + 0.5, y + 0.5);
        result.sub(new Vector2(tiles.length / 2.0, tiles[0].length / 2.0));
        result.mul(num_tile_pixels.toVector2());
        result.mul(Draw.getPixelSize());
        result = transform.localToWorldPosition(result);
        return result;
    }

    public Vector2Int worldToTile(Vector2 world_position) {
        Vector2 result = transform.worldToLocalPosition(world_position);
        result.div(Draw.getPixelSize());
        result.div(num_tile_pixels.toVector2());
        Vector2 offset = new Vector2(tiles.length / 2.0, tiles[0].length / 2.0);
        result.add(offset);
        return new Vector2Int((int) result.x, (int) result.y);
    }

}