package dqn;
import game.model.*;

import javax.swing.*;

public class SelfPlay {
    public static void main(String[] args){
        GameState game = new GameState(5);

        while (!game.isGameOver()){
            Line l = AIPlayer.chooseRandomLine(game.grid);
            game.turn(l);
        }

        SwingUtilities.invokeLater(() -> DisplayGameState.display(game.grid));


        System.out.println("Game Over");
        System.out.println("Player 1: " + game.grid.getPlayer1().getScore());
        System.out.println("Player 2: " + game.grid.getPlayer2().getScore());



    }
}
