###URL to Clone
https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0648

To setup the project and start the app:
1) Select "Check out project from Version Control" -> "Git"
2) Enter https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0648 as the Git Repository URL, choose a parent directory, and click Clone.
3) Click "Yes" if asked to create an Android Studio Project. Import project from Gradle if prompted.
4) If there is a "sync failed" error in the Build tab, click on the link that says "Re-download dependencies and sync project".
5) Now open the folder/project at "group_0648/Phase2/GameCentre" and run 'app' on your virtual device.

Android Virtual Device:
1) When you press run 'app', a 'Select Deployment Target' window will pop up.
2) Click 'Create Android Virtual Device'.
3) Select Pixel2 as the device to emulate, specifying the device OS as Android 8.1 API 27.

Game Launch Centre:
Instructions: Log in with your credentials, or register if you haven't. Select the game that you want to play, 
or change the header image to your liking in the settings. 

Sliding Tiles:
Instructions: Slide the numbered tiles so that they are in order.

Hangman: 
Instructions: Select letters to guess the randomly selected word from the randomly 
selected category. The player has 6 lives. 
Categories: Uoft CS Teaching Stream, UofT Buildings, Animals, Group Members/developers of this app.

Sudoku:
Instructions: There are nine 3x3 grids. To complete the game, each grid must be filled with the numbers 1-9 each. 
Moreover, in the complete 9x9 grid, there can be only one instance of any given number in each column and row."

Functionalities:
- Game Centre:
    - Register functionality: new users can be registered and persist through opening and closing the app. Each user has a unique userName.
    - Login functionality: registered users can log in using their correct credentials. Passwords are hashed for increased security.
    - Settings Page: Users can change the header image displayed in the main menu. 
    - Game selection: Users can pick the game they want to play
- Sliding Tiles Game:
    - Creating new game: You can choose between 3x3, 4x4, 5x5 complexity. Boards are randomly generated and always solvable.
    - Loading saved games: Games are autosaved for each user so that players can pick up where they left off. 
    - Undo: You will be asked to specify the maximum number moves to undo before the game starts.
    - Autosave: Game is saved after every move.
    - Custom puzzle: User can choose to upload an image and instead of the default number tiles, so that the tiles will each be 
                        a piece of the image and now the objective is to assemble the correct image.
    - High Scores: high scores for the game can be viewed, with the option to see only the logged in user's scores or all users' scores
- Sudoku: 
    - Creating new games: Boards are randomly generated and always solvable. 
    - Loading saved game: Games are autosaved for each user so that players can pick up where they left off.
    - Autosave: Game is saved after every move.
    - Undo: You will be asked to specify the maximum number moves to undo before the game starts.
    - High Scores: high scores for the game can be viewed, with the option to see only the logged in user's scores or all users' scores
- Hangman:
    - Creating new games: Words are randomly selected from a list of several categories, each with an extensive list of words.
    - Loading saved game: Games are autosaved for each user so that players can pick up where they left off.
    - Autosave: Game is saved after every move.
    - High Scores: high scores for the game can be viewed, with the option to see only the logged in user's scores or all users' scores
    
    
    
    

