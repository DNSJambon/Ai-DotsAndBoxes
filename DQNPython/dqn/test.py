import torch

from GameLogic.DotsAndBoxes import DotsAndBoxes
from dqn.DQN import DQN


def test_model():
    env = DotsAndBoxes(N=3)
    state_size = 2 * env.N * (env.N - 1)
    action_size = len(env.state)

    model = DQN(state_size, action_size)
    model.load_state_dict(torch.load("dqn_model.pth", weights_only=True))
    model.eval()

    env.reset()
    state = env.get_state()
    done = False

    while not done:
        if env.current_player == 2:
            print("\nAI's turn...")
            with torch.no_grad():
                state_tensor = torch.tensor(state, dtype=torch.float32).unsqueeze(0)
                action = torch.argmax(model(state_tensor)).item()

            try:
                reward = env.apply_action(action)
                print(f"Action: {action}, Reward: {reward}")
            except ValueError:
                print("Invalid action attempted!")
        else:
            print("\nPlayer's turn...")
            action = int(input("Enter action: "))
            try:
                reward = env.apply_action(action)
                print(f"Action: {action}, Reward: {reward}")
            except ValueError:
                print("Invalid action attempted!")

        state = env.get_state()
        done = env.is_game_over()

    print("Game over!")


if __name__ == "__main__":
    test_model()
