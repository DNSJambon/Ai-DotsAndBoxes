package game.model;

import java.util.Iterator;

public interface IGrid {
    int getSize();
    Dot getDot(int x, int y);
    Line getLine(Dot d1, Dot d2);
    Box getBox(Line top, Line right, Line bottom, Line left);
    Iterator<Line> getLinesIterator();
    Iterator<Box> getBoxesIterator();
}
