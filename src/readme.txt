This project is created by Annan Miao, Chuqian Zeng, Yumiao Zhou and Ziang Leng in November, 2020. 
It aims to build a game for playing the role-playing game: Legends of Valor, following the rule of the instruction. 
Some changes is made to the txt files in order to ease the read-in process. 

!!!!!!!!!!!!!!!!!!!!The console must support ANSI to make the output colorful!!!!!!!!!!!!!! Or messy codes may appear. 

For the readability of code, the game is mainly devided into 4 packages: Avatars, Equipments, Map and GameEnvironment:

Avatars
==========================================================
Avatar is a base abstract class. It has Hero and Monster as subclasses.
Hero class has 3 kinds of concrete heros as subclasses: Warrior, Sorcerer and Paladin. 
Monster class has 3 kinds of concrete monsters as subclasses: Dragon, Exoskeleton and Spirit.
HeroFactory and MonsterFactory classes can read the txt files and create concrete subclasses of heros and monsters.

Equipments
==========================================================
BuyableObject is a base abstract class for items in market. It has 4 subclasses: Weapon, Armor, Potion and Spell.
BuyableFactory class can read the txt files and create concrete subclasses of buyable objects. 
Outfit class is used to dress up with all the buyable objects for the Hero. 

Map
==========================================================
Board is a base abstract class. LegendBoard is the subclass of Board. The game is played on LegendBoard.
A LegendBoard is composed by Tiles. The abstract tile is extended to be AccessibleTile and InaccessibleTile. AccessibleTile is further extended to be Plain, Bush, Cave, Koulou and Market. HeroNexus is a subclass of Market. And MonsterNexus is a subclass of Plain.
Nexus is an interface applied on HeroNexus and MonsterNexus.
Buff is an interface applied on Bush, Cave and Koulou that can provide buff to heros.

GameEnvironment
==========================================================
LegendsOfValor is the main body of the game. It has RolePlayingGame as base class. 
HeroMonsterFight is a class for fight between a hero and a monster.
It also has a detectStatus class to detect status of different types of actions, i.e. detect monsters around hero, detect whether a location is teleportable for a hero, detect win or lose of the game, etc.

Help files:
The help files with data are located at Legends_Monsters_and_Heros folder.

Compile and Run:
The Main.java is the entrance to run the game. 
To run the game, open command window, then type "javac Main.java" and "java Main". Then follow the instruction of the game to play.