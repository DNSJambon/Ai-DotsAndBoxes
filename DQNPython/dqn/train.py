import matplotlib.pyplot as plt
import numpy
import numpy as np
import torch
from torch import optim, nn

from DQN import DQN
from GameLogic.DotsAndBoxes import DotsAndBoxes
from ReplayBuffer import ReplayBuffer

N = 3

# Hyperparameters
EPISODE = 10000

GAMMA = 0.99
LEARNING_RATE = 0.001
EPSILON_START = 1.0
EPSILON_END = 0.005
EPSILON_DECAY = 20000
BATCH_SIZE = 128
MEMORY_SIZE = 15000
TARGET_UPDATE = 10

all_rewards = []
all_losses = []

def train_dqn():
    # Initialize environment and model
    env = DotsAndBoxes(N=N)
    state_size = 2 * env.N * (env.N - 1)  # Size of the state vector
    action_size = len(env.state)  # Number of possible actions

    policy_net = DQN(state_size, action_size)
    target_net = DQN(state_size, action_size)
    target_net.load_state_dict(policy_net.state_dict())
    target_net.eval()

    optimizer = optim.Adam(policy_net.parameters(), lr=LEARNING_RATE)
    replay_buffer = ReplayBuffer(MEMORY_SIZE)

    epsilon = EPSILON_START
    steps_done = 0

    for episode in range(EPISODE):
        print(f"{int(episode / EPISODE * 100)}%, eps = {epsilon}") if episode % (EPISODE / 100) == 0 else None
        env.reset()
        state = env.get_state()
        done = False
        total_reward = 0

        while not done:

            if np.random.rand() < epsilon:
                action = np.random.randint(action_size)  # Explore
                while state[action] != 0:
                    action = np.random.randint(action_size)
            else:
                with torch.no_grad():
                    state_tensor = torch.tensor(state, dtype=torch.float32).unsqueeze(0)
                    action = torch.argmax(policy_net(state_tensor)).item()  # Exploit


            # Apply action in the environment
            try:
                reward = env.apply_action(action)
            except ValueError:
                reward = -100  # Penalize invalid moves

            next_state = env.get_state()
            done = env.is_game_over()

            # Store transition in replay buffer
            replay_buffer.push(state, action, reward, next_state, done)
            state = next_state
            total_reward += reward

            # Train the network
            if len(replay_buffer) >= BATCH_SIZE:
                states, actions, rewards, next_states, dones = replay_buffer.sample(BATCH_SIZE)

                states = torch.tensor(numpy.array(states), dtype=torch.float32)
                actions = torch.tensor(actions, dtype=torch.long)
                rewards = torch.tensor(rewards, dtype=torch.float32)
                next_states = torch.tensor(numpy.array(next_states), dtype=torch.float32)
                dones = torch.tensor(dones, dtype=torch.float32)

                # Compute Q(s, a)
                q_values = policy_net(states).gather(1, actions.unsqueeze(1)).squeeze(1)
                # Compute max Q(s', a') for the next states
                next_q_values = target_net(next_states).max(1)[0]
                # Compute the target
                target_q_values = rewards + GAMMA * next_q_values * (1 - dones)

                # Loss
                loss = nn.MSELoss()(q_values, target_q_values.detach())

                optimizer.zero_grad()
                loss.backward()
                optimizer.step()

                all_losses.append(loss.item())

            # Decay epsilon
            epsilon = max(EPSILON_END, EPSILON_START - steps_done / EPSILON_DECAY)
            steps_done += 1

        all_rewards.append(total_reward)

        # Update the target network
        if episode % TARGET_UPDATE == 0:
            target_net.load_state_dict(policy_net.state_dict())

    # Save the model
    dummy_input = torch.randn(1, state_size, dtype=torch.float32)
    onnx_path = "model" + str(env.N) + ".onnx"

    torch.onnx.export(
        policy_net,
        (dummy_input,),
        onnx_path,
        export_params=True,
        opset_version=11,
    )

    print(f"Model exported to {onnx_path}")

    print("Training complete!")
    print("max reward: ", max(all_rewards))

    # Plot the rewards
    plt.figure(figsize=(12, 5))
    plt.subplot(1, 2, 1)
    plt.plot(np.convolve(all_rewards, np.ones(100) / 100, mode='valid'))
    plt.xlabel('Episode')
    plt.ylabel('Total Reward')
    plt.title('Total Rewards over Episodes')

    # Plot the losses
    plt.subplot(1, 2, 2)
    plt.plot(np.convolve(all_losses, np.ones(100) / 100, mode='valid'))
    plt.xlabel('Training Step')
    plt.ylabel('Loss')
    plt.title('Loss over Training Steps')

    plt.show()


if __name__ == "__main__":
    train_dqn()
