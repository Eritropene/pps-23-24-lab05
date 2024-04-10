package minesweeper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomMineGenerator implements MineGenerator {

    private final Grid grid;
    private final int mines;
    private final Random random = new Random();
    public RandomMineGenerator(final Grid grid, final int mines) {
        if (grid.getWidth() <= 0 || grid.getHeight() <= 0)
            throw new IllegalArgumentException("Cannot create logics with grid size smaller than 1");
        if (mines < 0)
            throw new IllegalArgumentException("Cannot create logics with a negative number of mines");
        if (mines > grid.getWidth()*grid.getHeight())
            throw new IllegalArgumentException("Cannot create logics with more mines than cells");
        this.grid = grid;
        this.mines = mines;
    }
    @Override
    public Set<Pair<Integer, Integer>> generateMines() {
        Set<Pair<Integer, Integer>> result = new HashSet<>();
        final int width = this.grid.getWidth();
        final int height = this.grid.getHeight();
        while (result.size() != this.mines) {
            Pair<Integer, Integer> minePos = new Pair<>(random.nextInt(width), random.nextInt(height));
            result.add(minePos);
        }
        return result;
    }

    @Override
    public Grid grid() {
        return this.grid;
    }
}
