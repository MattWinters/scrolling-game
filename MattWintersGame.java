/* WELCOME TO ON THE RUN!!!!!
 * GATHER ALL THE MONEY YOU CAN AND ESCAPE TO FREEDOM BEFORE THE COPS GET YOU!
 The game will start once the splash screen is clicked.
 The user will control the yellow car. The car can move right left up or down.
 The player looses if the car takes too much damage or the police chasing the player catch up to the car.
 The car aquires damage each time the player does not avoid the cop cars that are positioned as road blocks.
 If the user picks up a repair kit they will get the damge of the car reduced by one collision.
 The cops chasing get closer if the user hits a cop but get farther away when a repair symbol is caught.
 If the cops over take the user or the user moves back into the cops they are busted.
 To escape the user must first gather $200,000.
 Once the user has made 200,000 they must succesfully get the yellow car to colomn on the far right side.
 To play again the user can click on the splash screen (win or loose) and a new game window will open.
 */ 


import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Color;

public class MattWintersGame {
 
 // set DEMO to false to use your code (true uses DemoGame.class)
 private static final boolean DEMO = false;           
 
 // Game window should be wider than tall:  H_DIM < W_DIM   
 // (more effectively using space)
 // # of cells vertically by default: height of game
 private static final int H_DIM = 5; 
 // # of cells horizontally by default: width of game
 private static final int W_DIM = 10;  
 // default location of the user at the start of the game
 private static final int U_ROW = 0;
 
 private static final int FACTOR = 3;      // you might change that this
                            // setting or declaration when working on timing
                            // (speed up and down the game)
                            
                            
 private String USER_IMG = "yellowcar.png"; //Allows it 
   // damage level versions of USER_IMG
   private final String damageNone = "yellowcar.png"; 
   private final String damageHigh = "highDamage.jpg";
   private final String damageExtreme = "extremeDamage.jpg";
   private final String GET_IMG = "money2.png"; // Get
 private final String AVOID_IMG = "policeCar.png"; // Avoid
 private final String BANG_IMG = "fireExplosion.jpg"; // crash image
 private final String CHASE_IMG = "policeCar2.png"; // chase police car
 private final String REPAIR_IMG = "repair2.jpg"; // repair symbol
 private final String TREASURE_IMG = "treasureChest.png";
 private final String SPLASH_IMG = "splash.jpg";
 private final String SPLASHWIN_IMG = "SplashWin2.jpg";
 private final String SPLASHBUSTED_IMG = "PoliceOfficer.jpg";
 
 private static Random rand = new Random();  //USE ME 
                                           // don't instantiate me every frame
 
 private GameGrid grid; // graphical window to display the game (the VIEW)
                        // a 2D game made of two dimensional array of Cells
 private int userRow;
 private int userCol = 0;
 
 private int cycles = 0; //how many times the game has propogatedRightEdge 
                             //Use to adjust how frequent objects appear
 
 private long msElapsed;  // game clock: number of ms since start of
                           // game main loop---see play() method
 private int timerClicks; // used to space animation and key presses
 private int pauseTime;  // to control speed of game
   
 private int score;
 private int penalty = 0;
 public boolean caught = false;
 private String damage;
 private boolean paused = false;
 private boolean colorOn = false;
 private int timesClicked = 0;
 private String imgLocFrom;
 private String imgLocTo;
 private Location locFrom;
 private Location locTo;
 
 public MattWintersGame() {
  this(H_DIM, W_DIM, U_ROW);
 }
 
 public MattWintersGame(int hdim, int wdim, int urow) {
   // to be filled
   
  init(hdim, wdim, urow);
 }
 
 private void init(int hdim, int wdim, int urow) {  
  
  /* initialize the game window

    NOTE: look at the various constructors to see what you can do!
    For example:
     grid = new GameGrid(hdim, wdim, Color.MAGENTA);
     
    You need to use the one that takes an image to implement the 
    splashscreen functionality (don't start there, but do it as your
    first extension)
  */
  grid = new GameGrid(hdim, wdim);   
  
  /* initialize other aspects of game state */

  // animation settings
  timerClicks = 0;
  msElapsed = 0;
  pauseTime = 100;

  // store and initialize user position
  userRow = urow;
  grid.setCellImage(new Location(userRow, userCol), USER_IMG);
  
  
  updateTitle();
  
 }
 
 public void play() {
 
   while(timesClicked == 0){    // set the splash screen to be removed once clicked
     grid.setSplash(SPLASH_IMG);
     handleMouseClick();
   }
  
  /* main game loop */
  while (!isGameOver() && timesClicked >= 1) { 
   // only allow functions outside of if statement to work when game paused 
   handleKeyPress();        // update state based on user key press
   
   handleMouseClick();      // when the game is running: 
                            // click & read the console output 
   
   // these helper functions I created don't matter if it's paused or not 
   updateUSER_IMG();     // Will change the car to show the current damge level
   updateChase();        // updates police chase
  
   
   if (! paused){ 
     grid.pause(pauseTime);   // pause for some time (smooth animation)
     msElapsed += pauseTime;  // count the total amount of time elapsed
     timerClicks++;     // increment the timer count
     
     speedUp();            // continualsy speeds up the game
     
     if (timerClicks % FACTOR == 0) {  // if it's the FACTOR timer tick
       // constant 3 initially
       scrollLeft();
       populateRightEdge();
     } 
     updateTitle();
     msElapsed += pauseTime;
   }
  }
  //Play again function
  playAgain();
 }
   
 public void playAgain(){
   penalty = 0;
   score = 0;
   timesClicked = 0;
   while (timesClicked == 0){
     handleMouseClick();
     grid.pause(pauseTime);
   }
   run();
 }
 public void updateUSER_IMG(){
   // changes the image of the user to visually display damage level
   if (penalty == 0)
     USER_IMG = damageNone;
   else if (penalty == 1)
     USER_IMG = damageHigh;
   else
     USER_IMG = damageExtreme;
 }
 
 public void speedUp(){
  if (msElapsed % 20 == 0 )
    pauseTime -= 1;
 }
  
 public void handleMouseClick() {
  Location loc = grid.checkLastLocationClicked();
  
  if (loc != null){
  grid.setSplash(null);
  timesClicked ++;
  System.out.println(timesClicked);
  System.out.println("You clicked on a square " + loc);
//   if(loc.cell.getColor() == null)
//     loc.cell.setColor("yellow");
 
  }
 }

 
 public void handleKeyPress() {
   
  int key = grid.checkLastKeyPressed();
  
  
  //use Java constant names for key presses
  //http://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_DOWN
  
  // Q for quit
  if (key == KeyEvent.VK_Q)
   System.exit(0);
  
  else if (key == KeyEvent.VK_D){
    if(!colorOn){
      grid.setLineColor (Color.white);
      colorOn = true;
    }
    else if (colorOn){
      grid.setLineColor (null);
      colorOn = false;
    }
  }
  
  else if (key == KeyEvent.VK_P ){
    if (!paused){
      paused = true;
      play();
    }
    else if (paused)
      paused = false;
  }
    
  if (key == KeyEvent.VK_S){
      grid.save("ScreenShot.png");
    }
  if(!paused){ 
    
      
      /* To help you with step 9: 
       use the 'T' key to help you with implementing speed up/slow down/pause
       this prints out a debugging message */
    if (key == KeyEvent.VK_T){
      boolean interval =  (timerClicks % FACTOR == 0);
      System.out.println("pauseTime " + pauseTime + " msElapsed reset " + 
                         msElapsed + " interval " + interval);
      }
    
    // minus 
    
    else if (key == KeyEvent.VK_MINUS)
      pauseTime += 10;
    
    else if (key == KeyEvent.VK_EQUALS)
      pauseTime -= 10;
    
    else if (key == KeyEvent.VK_DOWN){ //down arrow moves the user down
      if(userRow + 1 < grid.getNumRows()){
        if (grid.getCellImage(new Location(userRow +1, userCol)) == AVOID_IMG){
          userRow ++;
          penalty ++; 
          grid.setCellImage(new Location(userRow, userCol), BANG_IMG);
          grid.setCellImage(new Location(userRow -1, userCol), null);
        }
        
        else{
          if (grid.getCellImage(new Location(userRow +1, userCol)) == GET_IMG)
            getScore();
          else if (grid.getCellImage(new Location(userRow +1, userCol)) == REPAIR_IMG)
            getRepair();
           else if (grid.getCellImage(new Location(userRow +1, userCol)) == TREASURE_IMG)
            getTreasure();
          
          userRow ++;
          grid.setCellImage(new Location(userRow, userCol), USER_IMG);
          grid.setCellImage(new Location(userRow -1, userCol), null);
        }
      }
    }
  
   
   else if (key == 38){   // up arrow moves the user
     if (userRow - 1 >= 0){
       if (grid.getCellImage(new Location(userRow -1, userCol)) == AVOID_IMG){
         userRow --;
         penalty ++; 
         grid.setCellImage(new Location(userRow, userCol), BANG_IMG);
         grid.setCellImage(new Location(userRow +1, userCol), null);
       } 
    
     else{
       if (grid.getCellImage(new Location(userRow -1, userCol)) == GET_IMG)
         getScore();
       else if (grid.getCellImage(new Location(userRow -1, userCol)) == REPAIR_IMG)
         getRepair();
        else if (grid.getCellImage(new Location(userRow -1, userCol)) == TREASURE_IMG)
            getTreasure();
       userRow --;
       grid.setCellImage(new Location(userRow, userCol), USER_IMG);
       grid.setCellImage(new Location(userRow + 1, userCol), null);
     } 
   }
 }

   else if (key == KeyEvent.VK_RIGHT){
     if (userCol + 1 < grid.getNumCols()){
       if (grid.getCellImage(new Location(userRow, userCol +1)) == AVOID_IMG){
         userCol ++;
         penalty ++; 
         grid.setCellImage(new Location(userRow, userCol), BANG_IMG);
         grid.setCellImage(new Location(userRow, userCol -1), null);
       } 
       else{
         if (grid.getCellImage(new Location(userRow, userCol +1)) == GET_IMG)
           getScore();
         else if (grid.getCellImage(new Location(userRow, userCol +1)) == REPAIR_IMG)
           getRepair();
          else if (grid.getCellImage(new Location(userRow, userCol +1)) == TREASURE_IMG)
           getTreasure();
         userCol ++;
         grid.setCellImage(new Location(userRow, userCol), USER_IMG);
         grid.setCellImage(new Location(userRow, userCol -1), null);
       }
     }
   }
   
   
   else if (key == KeyEvent.VK_LEFT){
    if (userCol - 1 >= 0){
     if(grid.getCellImage(new Location(userRow, userCol -1)) == AVOID_IMG){
       userCol --;
       penalty ++; 
       grid.setCellImage(new Location(userRow, userCol), BANG_IMG);
       grid.setCellImage(new Location(userRow, userCol +1), null);
     }
     else {
       if (grid.getCellImage(new Location(userRow, userCol -1)) == GET_IMG)
         getScore();
       else if (grid.getCellImage(new Location(userRow, userCol -1)) == REPAIR_IMG)
         getRepair();
       else if (grid.getCellImage(new Location(userRow, userCol -1)) == TREASURE_IMG)
         getTreasure();
       userCol --;
       grid.setCellImage(new Location(userRow, userCol), USER_IMG);
       grid.setCellImage(new Location(userRow, userCol +1), null);  
      }
     }
   }
  }
 }
 

 // update game state to reflect adding in new cells in the right-most column 
 public void populateRightEdge() {
   cycles++;
   int  a = rand.nextInt(grid.getNumRows()) ;
   int  g = rand.nextInt(grid.getNumRows()) ;
   int  r = rand.nextInt(grid.getNumRows());
   int t = rand.nextInt(grid.getNumRows()) ;
   if (cycles % 50 == 0 && grid.getCellImage(new Location(r , grid.getNumCols() - 1)) != USER_IMG) 
         grid.setCellImage(new Location(r , grid.getNumCols() - 1), REPAIR_IMG);
   if (grid.getCellImage(new Location(g , grid.getNumCols() - 1)) != USER_IMG)
         grid.setCellImage(new Location(g , grid.getNumCols() -1), GET_IMG);
   if (grid.getCellImage(new Location(a , grid.getNumCols() - 1)) != USER_IMG)
         grid.setCellImage(new Location(a , grid.getNumCols() - 1), AVOID_IMG);  
   if (cycles % 35 == 0 && grid.getCellImage(new Location (t, grid.getNumCols() -1)) != USER_IMG)
       grid.setCellImage(new Location(t , grid.getNumCols() - 1), TREASURE_IMG);
 }
 
 // updates the game state to reflect scrolling left by one column 
 public void scrollLeft() {
   for (int i = 1; i < grid.getNumCols(); i++){
     scrollColLeft(i);
   }
 }
 
 public void scrollColLeft(int i){
   for (int j = 0; j < grid.getNumRows(); j++){
     imgLocFrom = grid.getCellImage(new Location(j, i)); // location of the cell the image is being moved from
     imgLocTo = grid.getCellImage(new Location(j, i - 1)); // location of cell image is being moved into
     
     if (imgLocFrom == damageNone || imgLocFrom == damageHigh || imgLocFrom == damageExtreme)
       grid.setCellImage(new Location(j, i), USER_IMG); 
     
     else if (imgLocTo == USER_IMG)
       handleCollision(new Location (j, i), new Location (j, i - 1));
     
     else if (imgLocTo == CHASE_IMG ){
       grid.setCellImage(new Location(j, i -1), CHASE_IMG);
       grid.setCellImage(new Location(j, i), null);
     }
      else if (imgLocFrom == BANG_IMG ){
       grid.setCellImage(new Location(j, i), USER_IMG);
       grid.setCellImage(new Location(j, i -1), null);
     }
      else if (imgLocTo == BANG_IMG  && i == 1){
       grid.setCellImage(new Location(j, i -1), USER_IMG);
     } 
     else{
     grid.setCellImage(new Location(j, i -1), imgLocFrom);
     grid.setCellImage(new Location(j, i), null);     
     }
   }
 }
 
 public void handleCollision(Location locFrom, Location locTo) {
   if (grid.getCellImage(locFrom) == AVOID_IMG){
     getPenalty();
     grid.setCellImage(locTo, BANG_IMG);
     
   }
   else if (grid.getCellImage(locFrom) == GET_IMG)
     getScore();
   
   else if (grid.getCellImage(locFrom) == REPAIR_IMG && penalty > 0)
     getRepair();
 
   else if (grid.getCellImage (locFrom) == TREASURE_IMG)
     getTreasure();
 }
  
 // creates and adds the cop cars chasing the user
 public void updateChase(){
   if (penalty > 0){
     checkCol((penalty-1) * 2);  //check to see if the user is in the column before the cop cars are printed 
     for(int i = 0; i < grid.getNumRows(); i++){ 
       grid.setCellImage(new Location(i, (penalty -1) * 2), CHASE_IMG);
     }
     for(int i = 0; i < grid.getNumRows(); i++){
       grid.setCellImage(new Location(i, (penalty - 1) * 2 + 1), CHASE_IMG);
     }
   }
 }
 
 // checks if the cops catch the car
 // if they do catch the car you are busted
 public void checkCol(int x){
   for(int i = 0; i < grid.getNumRows(); i++){
     // Get caught if the cops over take when a roadblock is hit
     if (grid.getCellImage(new Location(i, x)) == BANG_IMG || grid.getCellImage(new Location(i, x + 1)) == BANG_IMG)
       caught = true;
     
     //Temporary fix for User sometimes being able to move backwards into chasing cops  
     if (grid.getCellImage(new Location(i, x)) == USER_IMG || grid.getCellImage(new Location(i, x + 1)) == USER_IMG) 
        caught = true;
     

   }
 }
 
 // return the "score" of the game 
 public int getScore() {
   score += 10;
   return score;    // score is an instance variable
 }
 
 public int getPenalty(){
  penalty += 1;
  return penalty; 
 }
 
 public int getRepair(){
   if (penalty > 0){
     penalty -= 1;
     return penalty;
   }
   return penalty;
 }
 
 public int getTreasure(){
   score += 25;
   return score;
 }
 
 
 // update the title bar of the game window 
 public void updateTitle() {
   if (penalty == 0)
     damage = "none";
   else if (penalty == 1)
     damage = "HIGH";
   if (penalty == 2)
     damage = "!!!DANGER!!!";
   
  grid.setTitle("On The Run :  " + score + ",000        Damage level: " + damage);
 }
 
 public void updateTitleWin(){
   grid.setTitle("YOU ESCAPED!!!  YOU MADE " + score + ",000 DOLLARS!!!");
 }
 
 public void updateTitleLoose(){
   grid.setTitle("YOU GOT BUSTED WITH " + score + ",000 DOLLARS, ONLY " + (grid.getNumCols() - userCol) + " AWAY!!!");
 }
   // return true if the game is finished, false otherwise
 //      used by play() to terminate the main game loop 
 public boolean isGameOver() {
   if (userCol == grid.getNumCols() -1 && score > 150){  // If user_img make it to far right you escape/ win
     updateTitleWin();
     grid.setSplash(SPLASHWIN_IMG);
     return true;
   }
   else if (penalty == 3){  //if you the car gets too damaged the cops catch you
     updateTitleLoose();
     grid.setSplash(SPLASHBUSTED_IMG);
     return true;
   }     
   else if (caught){
     grid.setSplash(SPLASHBUSTED_IMG);
     return true;
   }
  return false;
 }
 
 public static void run() {
  if (DEMO) {       // reference game: 
   //   - play and observe first the mechanism of the demo 
   //     to understand the basic game 
   //   - go back to the demo anytime you don't know what the
   //     next step to implement and to test are. you should always be 
   //     clear and concrete about the ~5 lines you are trying to code and
   //     how to validate them
   //         figure out according to the game play 
   //         (the sequence of display and action) how the functionality
   //         you are implementing next is supposed to operate
   
   // It's critical to have a plan for each piece of code: follow, understand
   // and study the assignment description details; and explore the basic game. 
   // You should always know what you are doing (your current small goal) 
   // before implementing that piece or talk to us. 
   
   System.out.println("Running the demo: DEMO=" + DEMO);
   //constructor for client to adjust the game window size 
   //TRY different values
   DemoGame game = new DemoGame(5, 10, 0);
   //default constructor   (4 by 10)
   // DemoGame game = new DemoGame();
   game.play();
   
  } else {
   System.out.println("Running student game: DEMO=" + DEMO);
   // !DEMO   -> your code should execute those lines when you are
   // implementing your game 
   
   //test 1: with parameterless constructor

  // MattWintersGame game = new MattWintersGame();
   
   //test 2: with constructor specifying grid size  
   //IT SHOULD ALSO WORK as long as height < width
   //ScrollingGame game = new ScrollingGame(10, 20, 4);
   
    MattWintersGame game = new MattWintersGame(10,15,4);
   
   game.play();
   
  }
 }
 
 public static void main(String[] args) {
  run();
 }
}