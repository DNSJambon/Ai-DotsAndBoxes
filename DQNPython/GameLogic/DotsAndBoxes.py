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

    def check_new_box(self, action):
        """Check if the action completes any new boxes."""
        new_box = False
        box_completed = []

        nb_horizontal = (self.N - 1) * self.N

        if action < nb_horizontal:  # Horizontal line
            row = action // (self.N - 1)
            col = action % (self.N - 1)

            # Top box
            if row > 0:
                top_left = (col, row - 1)
                top_right = (col + 1, row - 1)
                left = self.get_line_index(top_left, (col, row))
                right = self.get_line_index(top_right, (col + 1, row))
                top = self.get_line_index(top_left, top_right)
                if self.state[left] and self.state[right] and self.state[top]:
                    new_box = True

            # Bottom box
            if row < self.N - 1:
                bottom_left = (col, row + 1)
                bottom_right = (col + 1, row + 1)
                left = self.get_line_index((col, row), bottom_left)
                right = self.get_line_index((col + 1, row), bottom_right)
                bottom = self.get_line_index(bottom_left, bottom_right)
                if self.state[left] and self.state[right] and self.state[bottom]:
                    new_box = True

        else:  # Vertical line
            action -= nb_horizontal
            col = action % self.N
            row = action // self.N

            # Left box
            if col > 0:
                top_left = (col - 1, row)
                bottom_left = (col - 1, row + 1)
                top = self.get_line_index(top_left, (col, row))
                bottom = self.get_line_index(bottom_left, (col, row + 1))
                left = self.get_line_index(top_left, bottom_left)
                if self.state[top] and self.state[bottom] and self.state[left]:
                    new_box = True

            # Right box
            if col < self.N - 1:
                top_right = (col + 1, row)
                bottom_right = (col + 1, row + 1)
                top = self.get_line_index((col, row), top_right)
                bottom = self.get_line_index((col, row + 1), bottom_right)
                right = self.get_line_index(top_right, bottom_right)
                if self.state[top] and self.state[bottom] and self.state[right]:
                    new_box = True

        return new_box

    def get_line_index(self, dot1, dot2):
        if dot1[1] == dot2[1]:  # Horizontal line
            row = dot1[1]
            col = min(dot1[0], dot2[0])
            return row * (self.N - 1) + col
        elif dot1[0] == dot2[0]:  # Vertical line
            row = min(dot1[1], dot2[1])
            col = dot1[0]
            offset = self.N * (self.N - 1)  # Offset for vertical lines
            return offset + row * self.N + col
        return -1

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
