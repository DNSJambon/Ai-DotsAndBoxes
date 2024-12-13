import random

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
        box_completed, box_opportunity = self._check_new_box(action)
        self.scores[self.current_player - 1] += box_completed

        if self.is_game_over():
            return (10 * (self.scores[0] - self.scores[1])) if self.scores[0] - self.scores[1] > 0 else 0
        else:
            if box_completed == 0:
                self.current_player = 3 - self.current_player
                return self.player_turn() + (1 if box_opportunity == 0 else -1)
            else:
                return 10

    def player_turn(self):
        action = random.randint(0, len(self.state) - 1)
        while self.state[action] != 0:
            action = random.randint(0, len(self.state) - 1)

        self.state[action] = 1
        box_completed, _ = self._check_new_box(action)
        self.scores[self.current_player - 1] += box_completed

        if self.is_game_over():
            return 10 * (self.scores[0] - self.scores[1])
        else:
            if box_completed == 0:
                self.current_player = 3 - self.current_player
                return 0
            else:
                return self.player_turn()



    def _check_new_box(self, action):
        """Check if the action completes any new boxes."""
        new_box = 0
        box_opportunity = 0

        nb_horizontal = (self.N - 1) * self.N

        if action < nb_horizontal:  # Horizontal line
            row = action // (self.N - 1)
            col = action % (self.N - 1)

            # Top box
            if row > 0:
                top_left = (col, row - 1)
                top_right = (col + 1, row - 1)
                left = self.state[self.get_line_index(top_left, (col, row))]
                right = self.state[self.get_line_index(top_right, (col + 1, row))]
                top = self.state[self.get_line_index(top_left, top_right)]
                if left and right and top:
                    new_box += 1
                if left + right + top == 2:
                    box_opportunity += 1




            # Bottom box
            if row < self.N - 1:
                bottom_left = (col, row + 1)
                bottom_right = (col + 1, row + 1)
                left = self.state[self.get_line_index((col, row), bottom_left)]
                right = self.state[self.get_line_index((col + 1, row), bottom_right)]
                bottom = self.state[self.get_line_index(bottom_left, bottom_right)]
                if left and right and bottom:
                    new_box += 1
                if left + right + bottom == 2:
                    box_opportunity += 1



        else:  # Vertical line
            action -= nb_horizontal
            row = action % (self.N - 1)
            col = action // (self.N - 1)

            # Left box
            if col > 0:
                top_left = (col - 1, row)
                bottom_left = (col - 1, row + 1)
                top = self.state[self.get_line_index(top_left, (col, row))]
                bottom = self.state[self.get_line_index(bottom_left, (col, row + 1))]
                left = self.state[self.get_line_index(top_left, bottom_left)]
                if top and bottom and left:
                    new_box += 1
                if top + bottom + left == 2:
                    box_opportunity += 1

            # Right box
            if col < self.N - 1:
                top_right = (col + 1, row)
                bottom_right = (col + 1, row + 1)
                top = self.state[self.get_line_index((col, row), top_right)]
                bottom = self.state[self.get_line_index((col, row + 1), bottom_right)]
                right = self.state[self.get_line_index(top_right, bottom_right)]
                if top and bottom and right:
                    new_box += 1
                if top + bottom + right == 2:
                    box_opportunity += 1

        return new_box, box_opportunity

    def get_line_index(self, dot1, dot2):
        if dot1[1] == dot2[1]:  # Horizontal line
            row = dot1[1]
            col = min(dot1[0], dot2[0])
            return row * (self.N - 1) + col
        elif dot1[0] == dot2[0]:  # Vertical line
            row = min(dot1[1], dot2[1])
            col = dot1[0]
            offset = self.N * (self.N - 1)  # Offset for vertical lines
            return offset + col * (self.N-1) + row
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
