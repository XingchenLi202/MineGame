// Name: Xingchen Li
// USC NetID: xli31296
// CS 455 PA3
// Fall 2021


/** 
   MineField
      class with locations of mines for a game.
      This class is mutable, because we sometimes need to change it once it's created.
      mutators: populateMineField, resetEmpty
      includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {
   /**
      Representation invariant:
      -- the row and the column of mine field must be poistive.
      -- the number of mines must be non-negative number and less than 1/3 of total number of field locations
      -- if there is a mine, the mine field is true, otherwise, it is false.
   */
   private boolean[][] mine; 
   private int numMines;
   private int numCols;
   private int numRows;
   
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in the array
      such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
      this minefield will corresponds to the number of 'true' values in mineData.
      @param mineData  the data for the mines; must have at least one row and one col,
                       and must be rectangular (i.e., every row is the same length)
    */
   public MineField(boolean[][] mineData) {
      
      mine = new boolean[mineData.length][mineData[0].length];
      for(int i = 0; i < mineData.length; i++){
         for (int j = 0; j < mineData[0].length; j++) {
            mine[i][j] = mineData[i][j];
         }
      }
      
      this.numRows = mine.length;
      this.numCols = mine[0].length;
 
      int count = 0;
      for(int i = 0; i < this.numRows; i++){
         for (int j = 0; j < this.numCols; j++){
            if(mineData[i][j]){
               count++;
            }
         }
      }
      this.numMines = count;
   }
   
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
      numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
      this.numRows = numRows;
      this.numCols = numCols;
      this.numMines = numMines;

      mine = new boolean[numRows][numCols];
      for(int i = 0; i < this.numRows; i++){
         for (int j = 0; j < this.numCols; j++){
            mine[i][j] = false;                //Set all the mines to empty
         }
      }      
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col) and numMines() < (1/3 * numRows() * numCols())
    */
   public void populateMineField(int row, int col) {
      resetEmpty();
      int i = 0;
      while(i < numMines()){                     //Randomly generate coordinates of numMines mines
         int ranRow = (int)(Math.random() * this.numRows);
         int ranCol = (int)(Math.random() * this.numCols);
         if((ranRow == row && ranCol == col)|| mine[ranRow][ranCol] == true){ //If the coordinate is equal to the coordinate passed in
            continue;               //Skip the coordinate
         }
         mine[ranRow][ranCol] = true;
         i++;
      }
      
   }
   
   
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
      Thus, after this call, the actual number of mines in the minefield does not match numMines().  
      Note: This is the state a minefield created with the three-arg constructor is in 
         at the beginning of a game.
    */
   public void resetEmpty() {
      for(int i = 0; i < this.numRows; i++){
         for (int j = 0; j < this.numCols; j++){
            mine[i][j] = false; 
         }
      }  
   }

   
  /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
      int countMines = 0;
      for (int i = row - 1; i <= row + 1; ++i) {
         for (int j = col - 1; j <= col + 1; ++j) {
            if(!inRange(i,j) || (i == row && j == col)){
               continue;
            }
            if (hasMine(i, j)) {
               countMines++; // If there're mines around, count it
            }
         }
      }
      return countMines;
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {
      if(row < this.numRows && row >= 0 && col < this.numCols && col >= 0){
         return true;
      }
      return false;
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return this.numRows;
   }
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */    
   public int numCols() {
      return this.numCols;
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {
      if(mine[row][col] == true){
         return true;
      }
      return false;
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
      some of the time this value does not match the actual number of mines currently on the field.  See doc for that
      constructor, resetEmpty, and populateMineField for more details.
    * @return
    */
   public int numMines() {
      return this.numMines;
   }
     
}

