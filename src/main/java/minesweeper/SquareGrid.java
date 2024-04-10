package minesweeper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SquareGrid implements Grid {

    private int size;
    private Map<Pair<Integer, Integer>, Integer> cells = new HashMap<>();
    public SquareGrid(final int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Cannot create grid with size smaller than 1");
        this.size = size;
    }
    @Override
    public Integer getWidth() {
        return this.size;
    }

    @Override
    public Integer getHeight() {
        return this.size;
    }

    @Override
    public boolean isInside(Pair<Integer, Integer> position) {
        return position.getX() >= 0 && position.getX() < this.size &&
                position.getY() >= 0 && position.getY() < this.size;
    }

    @Override
    public Optional<Integer> getCell(Pair<Integer, Integer> position) {
        checkPosition(position);
        return Optional.ofNullable(this.cells.get(position));
    }

    @Override
    public Map<Pair<Integer, Integer>, Optional<Integer>> neighbors(Pair<Integer, Integer> cell) {
        checkPosition(cell);
        Map<Pair<Integer, Integer>, Optional<Integer>> neighbors = new HashMap<>();
        for (int x = cell.getX()-1; x <= cell.getX()+1; x++)
            for (int y = cell.getY()-1; y <= cell.getY()+1; y++)
                try {
                    Pair<Integer, Integer> nearPosition = new Pair<>(x,y);
                    if (!nearPosition.equals(cell))
                        neighbors.put(nearPosition, getCell(nearPosition));
                } catch (ArrayIndexOutOfBoundsException ignored) {}
        return neighbors;
    }

    @Override
    public Map<Pair<Integer, Integer>, Optional<Integer>> allCells() {
        Map<Pair<Integer, Integer>, Optional<Integer>> all = new HashMap<>();
        for (int x=0; x<this.getWidth(); x++)
            for (int y=0; y<this.getHeight(); y++) {
                Pair<Integer, Integer> position = new Pair<>(x, y);
                all.put(position, this.getCell(position));
            }
        return all;
    }

    @Override
    public void setCell(Pair<Integer, Integer> position, Integer value) {
        checkPosition(position);
        this.cells.put(position, value);
    }

    private void checkPosition(Pair<Integer, Integer> position) {
        if (!isInside(position))
            throw new ArrayIndexOutOfBoundsException("Position out of grid");
    }

}
