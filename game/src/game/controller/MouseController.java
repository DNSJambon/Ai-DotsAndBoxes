package game.controller;

import game.GameView;
import game.model.Dot;
import game.model.Grid;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseController extends MouseAdapter implements MouseMotionListener {

    private final Controller controller;
    private final GameView view;
    private final Grid grid;
    private boolean dragging = false;

    public MouseController(GameView view, Controller controller, Grid grid){
        this.controller = controller;
        this.view = view;
        this.grid = grid;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        controller.playerClicked(x, y);
        view.repaint();

        if (grid.isGameOver()){
            view.endGame();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragging = true;
        int x = e.getX();
        int y = e.getY();
        Dot d1 = controller.getClosestDot(x, y);
        Dot d2 = controller.getSecondClosestDot(d1, x, y);
        view.setClosestDot(d1);
        view.setSecondClosestDot(d2);
        view.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragging){
            dragging = false;
            mouseClicked(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dot d1 = controller.getClosestDot(x, y);
        Dot d2 = controller.getSecondClosestDot(d1, x, y);
        view.setClosestDot(d1);
        view.setSecondClosestDot(d2);
        view.repaint();
    }
}
