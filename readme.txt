Matt Winters 
Scrolling game: Readme file:
My game is called On The Run and it is a police chase. Basically the user is being chased by the cops. The user’s goal is to escape with the money and not get arrested.


The game will start once the splash screen is clicked.
 The user will control the yellow car. The car can move right left up or down.
 The player loses if the car takes too much damage or the police chasing the player catch up to the car.
 The car acquires damage each time the player does not avoid the cop cars that are positioned as road blocks.
 If the user picks up a repair kit they will get the damage of the car reduced by one collision.
 The cops chasing get closer if the user hits a cop but get farther away when a repair symbol is caught.
 If the cops over take the user or the user moves back into the cops they are busted.
 To escape the user must first gather $200,000.
 Once the user has made 200,000 they must successfully get the yellow car to column on the far right side.
 To play again the user can click on the splash screen (win or lose) and a new game window will open.


The implementations that I’m most proud of are the chasing cops, the ability to win by moving to the far right side of the screen and the damage levels.
The cops chasing me the user get closer every time the user hits a road block cop. I used for loops to paint the chasing cars into columns depending on how close they are getting. It creates another cool twist to the gameplay.
The moving to the right side of the screen to win was challenging because I had to make the car able to move in all directions not just up and down. I had to be very careful to make sure that all directions the user could move would be able to interpret if their was a collision in the spot the user was going to. One of these collisions was with the roadblock cop in which there is an explosion upon impact then the user car is left damaged. I also thought that having a unique way to win made the game more fun.
My last favorite implementation was the damage level of the car. I used the avoid object as things that increased the car’s damage. The more damage on the car the more beat up the user img looked. I did this by creating different versions of the user img and making a function that changed what img was associated with my variable name USER_IMG. I also had a repair symbol which would lower the damage level and reverse the negative of hitting a cop.