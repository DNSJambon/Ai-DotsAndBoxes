# Ai-DotsAndBoxes


## How to run the game
1. Clone the repository
2. open the /game folder as a Java project in your IDE
3. run `src/game/Game.java`


## If you want to try and train the AI
1. Open the `DQNPython` folder as a python project in your IDE (a virtual environment is recommended)
2. Install the required packages. `pip install -r requirements.txt`
3. The main training loop and Hyperparameters are in `dqn/train.py`
4. The trained model for a grid size of N is saved as `modelN.onnx` in the `dqn` folder, simply rename it to `modelNgood.onnx` to use it in the Java game.