package game;

import game.controller.Controller;
import game.model.*;
import game.model.Box;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.Iterator;

public class GameView extends JPanel {
    private final Grid grid;
    private Controller controller;

    private final int dotSize = 30;
    private int cellWidth;
    private int cellHeight;

    private Dot closestDot;
    private Dot secondClosestDot;

    public GameView(Grid grid) {
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

        // Draw the lines and the closest line (hoover)
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
        if (grid.getLine(closestDot, secondClosestDot) == null && closestDot != null) {
            int x1 = closestDot.getX() * cellWidth;
            int y1 = closestDot.getY() * cellHeight;
            int x2 = secondClosestDot.getX() * cellWidth;
            int y2 = secondClosestDot.getY() * cellHeight;
            if (grid.getPlayer1() == controller.getCurrentPlayer()) {
                g.setColor(Color.decode("#F47079"));
            } else {
                g.setColor(Color.decode("#82A0F9"));

            }

            g.fillOval(x1 - dotSize/2, y1 - dotSize/2, dotSize*2, dotSize*2);
            g.fillOval(x2 - dotSize/2, y2 - dotSize/2, dotSize*2, dotSize*2);
            if (closestDot.getX() > secondClosestDot.getX() || closestDot.getY() > secondClosestDot.getY()) {
                g.fillRect(x2+dotSize/4, y2+dotSize/4, (cellWidth*(closestDot.getX()-secondClosestDot.getX())) + dotSize/2, (cellHeight*(closestDot.getY()-secondClosestDot.getY())) + dotSize/2);
            } else {
                g.fillRect(x1+dotSize/4, y1+dotSize/4, (cellWidth*(secondClosestDot.getX()-closestDot.getX())) + dotSize/2, (cellHeight*(secondClosestDot.getY()-closestDot.getY())) + dotSize/2);
            }

        }

        //Draw the dots
        g.setColor(Color.decode("#5E5E54"));
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                g.fillOval(col * cellWidth, row * cellHeight, dotSize, dotSize);
            }
        }
    }

    public void endGame() {
        int answer;

        //display the winner and ask if the user wants to play again
        if (grid.getPlayer1().getScore() > grid.getPlayer2().getScore()) {
             answer = JOptionPane.showConfirmDialog(this, "Red wins!\n" +
                    "Red: " + grid.getPlayer1().getScore() + "\n" +
                    "Blue: " + grid.getPlayer2().getScore() + "\n" +
                    "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        }

        else if (grid.getPlayer1().getScore() < grid.getPlayer2().getScore()) {
            answer = JOptionPane.showConfirmDialog(this, "Blue wins!\n" +
                    "Red: " + grid.getPlayer1().getScore() + "\n" +
                    "Blue: " + grid.getPlayer2().getScore() + "\n" +
                    "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        }

        else {
            answer = JOptionPane.showConfirmDialog(this, "It's a draw!\n" +
                    "Player 1: " + grid.getPlayer1().getScore() + "\n" +
                    "Player 2: " + grid.getPlayer2().getScore() + "\n" +
                    "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        }

        if (answer == JOptionPane.YES_OPTION) {
            restartGame();
        }
        else {
            System.exit(0);
        }
    }

    private void restartGame() {
        controller.restartGame();
        if (grid.getPlayer1().isBot()) {
            repaint();
            paintImmediately(getBounds());
            controller.botTurn();
        }
        else {
            repaint();
        }
    }

    public void setClosestDot(Dot closestDot) {
        this.closestDot = closestDot;
    }
    public void setSecondClosestDot(Dot secondClosestDot) {
        this.secondClosestDot = secondClosestDot;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getDotSize() {
        return dotSize;
    }


}
