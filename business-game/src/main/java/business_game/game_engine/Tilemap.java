package business_game.game_engine;

import business_game.game_engine.utils.Vector2Int;
import business_game.Vector2;
import business_game.game_engine.managers.Draw;

import java.lang.Math;

import business_game.game_engine.utils.DrawEntity;

public class Tilemap extends DrawEntity {
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
    public void draw() {
        Vector2Int startIndex = getStartIndex();
        Vector2Int endIndex = getEndIndex();

        for (int x = startIndex.x; x < endIndex.x; x++) {
            for (int y = startIndex.y; y < endIndex.y; y++) {
                Sprite tile = getTile(x, y);
                if (tile == null)
                    continue;
                Vector2 position = tileToWorld(new Vector2Int(x, y));
                Draw.sprite(tile, position.x, position.y);
            }
        }
    }

    private Vector2Int getStartIndex() {
        Vector2 camera_position = Draw.camera.cameraToWorld(new Vector2(0, Draw.getHeight()));
        Vector2Int tile_position = worldToTile(camera_position);
        tile_position.x = Math.max(tile_position.x, 0);
        tile_position.y = Math.max(tile_position.y, 0);
        return tile_position;
    }

    private Vector2Int getEndIndex() {
        Vector2 camera_position = Draw.camera.cameraToWorld(new Vector2(Draw.getWidth(), 0));
        Vector2Int tile_position = worldToTile(camera_position);
        tile_position.add(new Vector2Int(1, 1));
        tile_position.x = Math.min(tile_position.x, width);
        tile_position.y = Math.min(tile_position.y, height);
        return tile_position;
    }

    @Override
    public int getLayer() {
        return -1000;
    }

    public Vector2 tileToWorld(Vector2Int tile) {
        Vector2 result = tile.toVector2();
        Vector2 middle_offset = new Vector2(width / 2.0, height / 2.0);
        result.sub(middle_offset); // nice
        result.mul(getTileSize());

        result.add(position);

        return result;
    }

    public Vector2Int worldToTile(Vector2 point) {
        Vector2 result = point.copy();
        result.sub(position);

        result.div(getTileSize());

        Vector2 middle_offset = new Vector2(width / 2.0, height / 2.0);
        result.add(middle_offset); // nice
        result = new Vector2(Math.floor(result.x), Math.ceil(result.y));
        return result.toVector2Int();
    }

    public Vector2 getTileSize() {
        double pixel_size = Draw.getPixelSize();
        return new Vector2(num_tile_pixels.x * pixel_size, num_tile_pixels.y * pixel_size);
    }
}