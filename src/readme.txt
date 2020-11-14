This project is created by Chuqian Zeng in November, 2020. 
 It aims to build a game for playing the role-playing game: Legends: Monsters and Heroes, following the rule of the instruction. 
Some changes is made to the txt files in order to ease the read-in process. 

!!!!!!!!!!!!!!!!!!!!The console must support ANSI to make the output colorful!!!!!!!!!!!!!! Or messy codes may appear. 

It is mainly devided into 5 parts: the hero; the objects in market; the monster; the game board with tiles; and the game. 

Hero: 
==========================================================
It has Hero as the base class for 3 kinds of concrete hero: warriors, sorcerers and paladins. 
There is a HeroFactory to read the txt file and create a concrete subclass of hero. 
Outfit is used to dress up with all the buyable object for the Hero. 

Objects in market: 
==========================================================
There are 4 kinds of concrete object in market: weapon, armor, potion and spell. 
They all have a base class BuyableObject. 
There is a BuyableFactory to read the txt file and create a concrete subclass of buyable object. 

Monster: 
==========================================================
It has Monster as the base class for 3 kinds of concrete monster:  dragons, exoskeletons and spirits. 
There is a MonsterFactory to read the txt file and create a concrete subclass of monster. 

Game board with tiles: 
==========================================================
The game is played on LegendBoard. It has Board as base class. 
A Board is composed by Tiles. 

The abstract tile is extended to be AccessibleTile and InaccessibleTile. AccessibleTile is further extended to be CommonTile and Market. 
InaccessibleTile, CommonTile and Market together compose the LegendBoard. 

The game: 
==========================================================
Legends is the main body of our game. It has RolePlayingGame as base class. 

Main: 
The entrance to run the game. 
