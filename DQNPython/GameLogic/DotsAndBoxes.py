import numpy as np

class DotsAndBoxes:
    def __init__(self, N):
        self.N = N
        self.state = np.zeros(2 * N * (N - 1), dtype=np.float32)  # Represent grid lines as 0s and 1s
        self.current_player = 1  # Player 1 or 2
        self.scores = [0, 0]  # Scores for Player 1 and Player 2

    def apply_action(self, action):
        if self.state[action] != 0:
            raise ValueError("Action already taken!")

        self.state[action] = 1
        box_completed = self._check_and_score(action)

        if not box_completed:
            self.current_player = 3 - self.current_player  # Switch player

        return 1 if box_completed else 0  # Reward for completing a box

    def _check_and_score(self, action):
        # Logic to check if the action completes any boxes and update scores
        completed = False

        return completed

    def is_game_over(self):
        return np.all(self.state == 1)

    def reset(self):
        self.state.fill(0)
        self.current_player = 1
        self.scores = [0, 0]

    def get_state(self):
        return self.state.copy()

    def get_current_player(self):
        return self.current_player
