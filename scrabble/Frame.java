package scrabble;

import java.util.Collections;
import java.util.LinkedList;

public class Frame {

    public static int FRAME_SIZE = 7;

    private LinkedList<Tile> frame = new LinkedList<>(); // linked list to store tile objects

    public void removeLetter(Tile... c) { // This function removes the tiles from the frame
        for (Tile tile : c) {

            if (checkLetters(tile))
                frame.remove(tile);
        }
    }

    public boolean checkLetters(Tile c) { // This checks if the letters are avalaible or not returning a suitable
        // boolean
        for (Tile tile : frame) {
            if (tile.getLetter() == c.getLetter()) {
                return true;
            }
        }

        return false;
    }

    public boolean isEmpty() { // returns true if frame is empty
        return frame.isEmpty();
    }

    @Override
    public String toString() { // returns the string
        StringBuilder result = null;
        for (Tile tile : frame) {
            if (result == null) {
                result = new StringBuilder("" + tile.getLetter());
            } else {
                result.append(", ").append(tile.getLetter());
            }
        }
        result = new StringBuilder(("[ " + result + " ]"));
        return result.toString();
    }

    public boolean fillFrame(Tile... t) {

        if (t.length <= FRAME_SIZE - frame.size()) {
            Collections.addAll(frame, t);
            return true;
        } else {
            return false;
        }
    }

    public Tile getTile(int i) { // returns letter in index i
        if (i >= 0 && i < frame.size()) {
            return frame.get(i);
        } else {
            throw new IllegalArgumentException("INDEX OUT OF BOUNDS:getTile");
        }
    }

    public Tile getTileFromChar(char letter) {
        for (Tile tile : frame) {
            //System.out.println("tile get letter: " + tile.getLetter() + " letter: " + letter + " bool: " + (tile.getLetter() == letter));
            if (tile.getLetter() == letter) {
                return tile;
            }
        }
        throw new IllegalArgumentException("Letter not found: getTileFromChar");
    }


    public int frameSize() { // returns size of frame
        return frame.size();
    }

    public void refill(Pool p) {
        while (frame.size() < 7 && !p.emptyPool()) {
            this.fillFrame(p.randomTile());
        }
        if (p.emptyPool()) {
            System.out.println("Pool is empty.");
        }
    }

    public void addTile(Tile tile) {
        if (frame.size() < 7) {
            frame.add(tile);
        }
    }

    public boolean exchangeTile(char c, Pool pool) {
        Tile tile;
        try {
            tile = this.getTileFromChar(c);
        } catch (IllegalArgumentException ex) {
            System.out.println("Tile not in frame.");
            return false;
        }
        removeLetter(tile);
        addTile(pool.randomTile());
        return true;
    }

    public LinkedList<Tile> getFrame() {
        return frame;
    }
}
