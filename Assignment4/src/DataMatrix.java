
/*------------------------------------------------------
 * DataMatrix
 *---------------------------------------------------- */
public class DataMatrix implements BarcodeIO
{
   
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;
   
   public DataMatrix()
   {
      this.text = "";
      this.actualWidth = 0;
      this.actualHeight = 0;
      this.image = new BarcodeImage();
   }
   
   public DataMatrix(BarcodeImage image)
   {
      scan(image);
   }
   
   public DataMatrix(String text)
   {
      readText(text);
   }
   
   @Override
   public boolean scan(BarcodeImage bc)
   {
      try
      {
         
         image = (BarcodeImage) bc.clone();
         cleanImage();
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
         
      } catch (Exception e)
      {
         
         return false;
         
      }

      return true;
   }

   @Override
   public boolean readText(String text)
   {
      if(text == null || text.equals(""))
         return false;
      
      this.text = text;
      return true;
   }

   @Override
   public boolean generateImageFromText()
   {
      if(text == null || text.equals(""))
         return false;
      
      int valueASCII;
      
      clearImage();
      actualWidth = text.length();
      
      for(int i = 0; i < text.length(); i++)
      {
         valueASCII = (int)text.charAt(i);
         WriteCharToCol(i + 1, valueASCII);
      }
      
      generateOpenBorders();
      generateLimitationLines();
      
      cleanImage();
      return true;
   }
   
   private void generateOpenBorders()
   {
      for(int i = 0; i < actualWidth + 2; i++)
      {
         if(i % 2 == 0)
            image.setPixel(0, i, true);
         else
            image.setPixel(0, i, false);
      }
      
      for(int i = 1; i < actualHeight + 1; i++)
      {
         if(i % 2 == 0)
            image.setPixel(i, actualWidth + 1, true);
         else
            image.setPixel(i, actualWidth + 1, false);
      }

   }
   
   private void generateLimitationLines()
   {
      for(int i = 0; i < actualWidth + 2; i++)
         image.setPixel(actualHeight + 1, i, true);
      
      for(int i = 0; i < actualHeight + 2; i++)
         image.setPixel(i, 0, true);
   }
   
   private boolean WriteCharToCol(int col, int code)
   {
      
      if(col > BarcodeImage.MAX_WIDTH)
         return false;
      
      int maxASCII = 128;
      
      for(int i = 0; i < actualHeight; i++)
      {
         if(code >= maxASCII)
         {
            code -= maxASCII;
            image.setPixel(i + 1, col, true);
         }

         maxASCII /= 2;
      }
      
      return true;
   }
   
   @Override
   public boolean translateImageToText()
   {

      text = "";
      for(int i = 1; i < actualWidth + 1; i++)
         text += readCharFromCol(i);
         
      return false;
   }
   
   private char readCharFromCol(int col) 
   {
      
      int maxASCII = 128;
      int code = 0;
      
      for(int i = BarcodeImage.MAX_HEIGHT - 1 - actualHeight; 
            i < BarcodeImage.MAX_HEIGHT - 1; i++)
      {
         
         if(image.getPixel(i, col))
            code += maxASCII;

         maxASCII /= 2;
      }
      
      return (char)code;
   }

   @Override
   public void displayTextToConsole()
   {
      
      System.out.println(text);
      
   }

   @Override
   public void displayImageToConsole()
   {
      int height, width;
      height = actualHeight + 2; // +2 to include spine
      width = actualWidth + 2; // +2 to include spine
      
      for(int i = BarcodeImage.MAX_HEIGHT - height; 
            i < BarcodeImage.MAX_HEIGHT; i++)
      {        
         for (int j = 0; j < width; j++)
         {
            if(image.getPixel(i, j))
               System.out.print(BLACK_CHAR);
            else
               System.out.print(WHITE_CHAR);
         }
         System.out.println();
      }
      
   }
  
   public void displayRawImage()
   {
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++)
      {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            if(image.getPixel(i, j))
               System.out.print(BLACK_CHAR);
            else
               System.out.print('-'); // displaying a - for easy debugging
         }
         System.out.println();
      }
   }
   
   public int getActualHeight()
   {
      return actualHeight;
   }
   
   public int getActualWidth()
   {
      return actualWidth;
   }
   
   private int computeSignalWidth()
   {
      int count = 0;
      
      for(int i = 0; i < BarcodeImage.MAX_WIDTH; i++)
      {
         if(image.getPixel(BarcodeImage.MAX_HEIGHT - 1, i))
            count++;
      }
      
      // return the count - 2 to remove the spines
      return (count == 0) ? 0 : count - 2;
   }
   
   private int computeSignalHeight()
   {
      int count = 0;
      
      for(int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--)
      {
         if(image.getPixel(i, 0))
            count++;
      }
      
      // return the count - 2 to remove the spines
      return (count == 0) ? 0 : count - 2;
   }
   
   private void cleanImage()
   {
      moveImageToLowerLeft();
   }
   
   private void moveImageToLowerLeft()
   {
      boolean foundBottmLeft = false;
      
      for(int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--)
      {
         if(foundBottmLeft)
            break;
         
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            if(image.getPixel(i, j))
            {
               shiftImageDown((BarcodeImage.MAX_HEIGHT -1) - i);
               shiftImageLeft(j);
               foundBottmLeft = true;
               break;
            }
         }
      }

   }
   
   private void shiftImageDown(int offset)
   {
      
      if(offset == 0)
         return;
      
      for(int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--)
      {  
         if(i - offset < 0)
            return;
            
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            if(image.getPixel(i - offset, j))
               image.setPixel(i, j, true);
            else
               image.setPixel(i, j, false);
         }
      }
   }
   
   private void shiftImageLeft(int offset)
   {
      
      if(offset == 0)
         return;
      
      for(int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--)
      {        
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            if(j + offset > BarcodeImage.MAX_WIDTH)
               break;
            
            if(image.getPixel(i, j + offset))
               image.setPixel(i, j, true);
            else
               image.setPixel(i, j, false);
         }
      }
   }
   
   private void clearImage()
   {
      for(int i = BarcodeImage.MAX_HEIGHT - 1; i >= 0; i--)
      {        
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++)
         {
            image.setPixel(i, j, false);
         }
      }
   }

} //END class DataMatrix