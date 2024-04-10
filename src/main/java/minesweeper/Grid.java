package minesweeper;

import java.util.Map;
import java.util.Optional;

public interface Grid {
    Integer getWidth();
    Integer getHeight();
    boolean isInside(Pair<Integer, Integer> position);
    Optional<Integer> getCell(Pair<Integer, Integer> position);
    void setCell(Pair<Integer, Integer> position, Integer value);
    Map<Pair<Integer, Integer>, Optional<Integer>> neighbors(Pair<Integer, Integer> cell);
    Map<Pair<Integer, Integer>, Optional<Integer>> allCells();
}
