package dqn;
import game.controller.Solver;
import game.model.Grid;
import game.model.Line;

public class AIPlayer {
    public static Line chooseRandomLine(Grid g){
        return Solver.getRandLine(g);
    }
}
