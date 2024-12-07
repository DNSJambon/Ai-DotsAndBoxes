from DotsAndBoxes import *

def main():
    # Initialize a 3x3 grid (example size)
    game = DotsAndBoxes(N=3)

    print("Initial State:")
    print(game.state.reshape(2 * game.N * (game.N - 1)))

    # Apply actions step-by-step
    print("\nPlayer 1 starts...")
    try:

        print("\nContinuing the game...")
        # Simulate more moves
        actions = [0,1,2,3,6,10,8,9,11,4,5,7]
        for action in actions:
            print(f"\nPlayer {game.current_player}'s turn")
            reward = game.apply_action(action)
            print(f"Action {action} applied. Reward: {reward}")
            print("State after action:")
            print(game.state.reshape(2 * game.N * (game.N - 1)))

            if game.is_game_over():
                print("Game over!")
                break

        print("\nFinal State:")
        print(game.state.reshape(2 * game.N * (game.N - 1)))
        print(f"Final scores: {game.scores}")
    except ValueError as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    main()
