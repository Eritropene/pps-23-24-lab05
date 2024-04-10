package minesweeper;

import java.util.Optional;

public interface Logics {
    Optional<Integer> cell(Pair<Integer, Integer> pos);
    boolean victory();
    boolean isMine(Pair<Integer, Integer> pos);
    boolean isFlag(Pair<Integer, Integer> pos);
    void toggleFlag(Pair<Integer, Integer> pos);
    boolean hit(Pair<Integer, Integer> pos);
}
