package dqn;

import game.model.Box;
import game.model.Grid;
import game.model.Line;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class DisplayGameState {

    public static void display(Grid grid){
            JFrame frame = new JFrame("Dots and Boxes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);

            Board board = new Board(grid);

            frame.add(board);
            frame.setVisible(true);

    }

    private static class Board extends JPanel{
        private Grid grid;
        private final int dotSize = 30;
        private int cellWidth;
        private int cellHeight;

        public Board(Grid grid){
            this.grid = grid;
            this.cellWidth = (getWidth()-dotSize) / (grid.getSize()-1);
            this.cellHeight = (getHeight()-dotSize) / (grid.getSize()-1);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.cellWidth = (getWidth()-dotSize) / (grid.getSize()-1);
            this.cellHeight = (getHeight()-dotSize) / (grid.getSize()-1);



            // draw the boxes
            Iterator<Box> boxes = grid.getBoxesIterator();
            while (boxes.hasNext()) {
                Box box = boxes.next();
                if (box.getOwner() == grid.getPlayer1()) {
                    g.setColor(Color.decode("#C9434A"));
                } else {
                    g.setColor(Color.decode("#3C85FF"));
                }

                int x = box.getLeft().getDot1().getX() * cellWidth + dotSize/2;
                int y = box.getTop().getDot1().getY() * cellHeight + dotSize/2;
                g.fillRect(x, y, cellWidth, cellHeight);
            }

            // Draw the lines
            Iterator<Line> lines = grid.getLinesIterator();
            while (lines.hasNext()) {
                Line line = lines.next();
                if (line.getOwner() == grid.getPlayer1()) {
                    g.setColor(Color.decode("#F47079"));
                } else {
                    g.setColor(Color.decode("#82A0F9"));
                }

                int x1 = line.getDot1().getX() * cellWidth + dotSize/2;
                int y1 = line.getDot1().getY() * cellHeight + dotSize/2;
                g.fillRect(x1-dotSize/4, y1-dotSize/4, (cellWidth*(line.getDot2().getX()-line.getDot1().getX())) + dotSize/2, (cellHeight*(line.getDot2().getY()-line.getDot1().getY())) + dotSize/2);

            }


            //Draw the dots
            g.setColor(Color.decode("#5E5E54"));
            for (int row = 0; row < grid.getSize(); row++) {
                for (int col = 0; col < grid.getSize(); col++) {
                    g.fillOval(col * cellWidth, row * cellHeight, dotSize, dotSize);
                }
            }
        }
    }
}
