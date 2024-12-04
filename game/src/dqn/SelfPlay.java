package dqn;
import game.model.*;

import javax.swing.*;

public class SelfPlay {
    public static void main(String[] args){
        GameState game = new GameState(3);

        int stop = 3;

        while (!game.isGameOver()){
            Line l = AIPlayer.chooseRandomLine(game.grid);
            game.turn(l);
            stop--;
            if (stop == 0){
                break;
            }

        }
        System.out.println(game.getState());
        SwingUtilities.invokeLater(() -> DisplayGameState.display(game.grid));


        System.out.println("Game Over");
        System.out.println("Player 1: " + game.grid.getPlayer1().getScore());
        System.out.println("Player 2: " + game.grid.getPlayer2().getScore());



    }
}
