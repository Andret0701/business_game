package business_game.game_engine.entity;

import business_game.game_engine.gfx.Sprite;
import business_game.game_engine.managers.Draw;
import business_game.game_engine.types.Vector2;
import business_game.game_engine.types.Vector2Int;

public class Tilemap extends Entity implements Drawable {
    protected Sprite[][] tiles;
    protected int width, height;
    protected Vector2Int num_tile_pixels;

    public Tilemap(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Sprite[width][height];
        num_tile_pixels = new Vector2Int(8, 8);
    }

    public void setTile(int x, int y, Sprite tile) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Tilemap.setTile: x or y is out of bounds");
        tiles[x][y] = tile;
    }

    public void fill(Sprite tile) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = tile;
            }
        }
    }

    public Sprite getTile(int x, int y) {
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
}