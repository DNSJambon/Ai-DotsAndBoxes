package game.controller;

import game.model.*;

public class Bot {

    public static Line getBestLine(Grid grid) {
        return getRandLine(grid);
    }

    //get a random line that is not already in the grid
    public static Line getRandLine(Grid grid) {
        int size = grid.getSize();
        Dot d1;
        Dot d2;
        Line l = null;
        while (l == null) {
            int x = (int) (Math.random() * (size));
            int y = (int) (Math.random() * (size));
            int dir = (int) (Math.random() * 2);
            if (dir == 0) {
                d1 = grid.getDot(x, y);
                d2 = grid.getDot(x + 1, y);
            } else {
                d1 = grid.getDot(x, y);
                d2 = grid.getDot(x, y + 1);
            }
            //System.out.println("d1: " + d1 + " d2: " + d2);
            if (d1 != null && d2 != null && grid.getLine(d1, d2) == null)
                l = new Line(d1, d2);
        }
        return l;
    }
}
