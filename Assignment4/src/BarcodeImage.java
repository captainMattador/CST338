
/*------------------------------------------------------
 * BarcodeImage
 *---------------------------------------------------- */
public class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean[][] image_data;
   
   /*
    * default constructor has no input and sets all values of image_data
    * to false
    */
   public BarcodeImage()
   {
      this.image_data = new boolean [MAX_HEIGHT][MAX_WIDTH];
      
      for (int i = 0; i < MAX_HEIGHT; i++)
         for (int j = 0; j < MAX_WIDTH; j++)
            this.image_data[i][j] = false;
   }
   
   /*
    * constructor accepts a 1 dimensional String[] str_data and converts to 
    * boolean image_data
    */
   public BarcodeImage(String[] str_data)
   {
      
      if(checkSize(str_data))
         // do somehting if true here.
      
      this.image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
      int strIndex = str_data.length - 1;
      
      for (int i = MAX_HEIGHT - 1; i >= 0; i--)
      {
         for (int j = 0; j < MAX_WIDTH; j++)
         {
            
            if(strIndex >= 0 && j < str_data[strIndex].length())
            {
               
               if(str_data[strIndex].charAt(j) == ' ')
                  setPixel(i, j, false);
               else 
                  setPixel(i, j, true);
               
            }else
            {
               setPixel(i, j, false);
            }
            
         }
         strIndex--;
      }
   }
   
   /*
    * mutator setPixel accepts ints row and col, and boolean value
    * and sets image_data
    */
   public boolean setPixel(int row, int col, boolean value)
   {
      if(isValid(row, col))
      {
         this.image_data[row][col] = value;
         return true;
      }

      return false;
   }
   
   /*
    * getPixel returns the value of image_data at row, col
    * or false is row or col are invalid
    */
   public boolean getPixel(int row, int col)
   {
      if (isValid(row, col))
         return this.image_data[row][col];
      
      return false;
   }
   
   /*
    * isValid takes ints, row and col, returns true if they fit in the 
    * matrix size
    */
   private boolean isValid(int row, int col)
   {
      return (( row >= 0 && row < MAX_HEIGHT) && 
            (col >= 0 && col < MAX_WIDTH));
   }
   
   public Object clone()
   {
      try
      {
         BarcodeImage copy = (BarcodeImage)super.clone();
         copy.image_data = (boolean[][])image_data.clone();
         return copy;
      }
      catch (CloneNotSupportedException e)
      {
         return null;
      }
   }
   
   private boolean checkSize(String[] data)
   {
      if(data == null || data.length > MAX_HEIGHT)
         return false;
      
      for(String d: data)
      {
         if(d == null || d.length() > MAX_WIDTH)
            return false;
      }
      
      return true;
   }
   
   // only used for debugging
   public void displayToConsole()
   {
      for (int i = 0; i < MAX_HEIGHT; i++)
      {
         for (int j = 0; j < MAX_WIDTH; j++)
         {
            if(image_data[i][j])
               System.out.print('*');
            else
               System.out.print('-');
         }
         System.out.print("\n");
      }
   }
   
} //END class BarcodeImage