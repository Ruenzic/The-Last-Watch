Author: Josh di Bona (DBNJOS001)
Date: 29/09/14
josh57@live.co.za

THE LAST WATCH

To get game running:
-Install gradle plugins into eclipse (http://dist.springsource.com/release/TOOLS/gradle)
-Import project into eclipse via gradle.
-Run my-gdx-game-desktop as a java application


Controls:
- 'W' 'A' 'S' 'D' move the player up, left, down and right respectively. 
- The Up, down, left and right arrow keys shoot in their respective directions. 


Features: 
- Player can Shoot and kill enemies.
- AI
- Different enemies (3 different types of zombies, red, green and brow):
	- Red (Red zombie moves in 4 directions and changes direction when it collides with something)
	- Green (Green zombies moves in any direction and constantly changes speed)
	- Brown (Brown zombies moves much like the red zombie does except it will chase the player if it is in its line of sight, its speed will increase until it collides with something)
All the zombie do damage to the player when they collide, they also do damage to the base (house) when they collide with it. 
- Music and Sounds (There is game music as well as sounds for shooting)
- Enemies have stats, speed, life and damage. 
- There is a wining and losing condition (scoring system), when the player clears the map of 100 zombies the game will end, and when the base or the player reaches 0 life.
- There are pickups (at the moment only a health pickup) which has a chance to spawn in the map when an enemy is killed, the player can run over it to regenerate their life to max (100).





Indicator on the Right Side of the game:
- Player health
- House health
- Enemies Killed


How to Play:
- Kill enemies while defending your base, collect health packs to restore your health.

All sound, music and images are royalty free with thanks to freeSound.org as well as local artists. 


