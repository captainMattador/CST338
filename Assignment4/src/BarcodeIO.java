
/*------------------------------------------------------
 * BarcodeIO Interface
 *---------------------------------------------------- */
public interface BarcodeIO
{
   /*
    * scan accepts a BarcodeImage object, bc, and stores a copy 
    */
   public boolean scan(BarcodeImage bc);
   
   /*
    * readText accepts a String object, text, to be encoded
    */
   public boolean readText(String text);
   
   /*
    * generateImageFromText creates a BarCodeImage object
    */
   public boolean generateImageFromText();
   
   /*
    * translateImageToText examines an image and creates a String
    */
   public boolean translateImageToText();
   
   /*
    * displayTextToConsole outputs a String to the console
    */
   public void displayTextToConsole();
   
   /*
    * displayImageToConsole prints an image to the console
    */
   public void displayImageToConsole();
   
}//END interface BarcodeIO
