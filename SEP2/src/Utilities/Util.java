package Utilities;

import java.awt.Color;
import java.awt.Image;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**This class is a collection of different methods used across the whole system for a variety of purposes.
 */
public class Util
{
   /**Takes the hex <code>colorStr</code> and returns a new <code>Color</code> with that value. 
    * @param colorStr
    * @return <code>Color</code>
    */
   public static Color hex2Rgb(String colorStr) {
      return new Color(
            Integer.valueOf(colorStr.substring(1, 3), 16),
            Integer.valueOf(colorStr.substring(3, 5), 16),
            Integer.valueOf(colorStr.substring(5, 7), 16));
   }

   /**Rounds the parameter <code>value</code> to a double with a number of digits after the decimal point equal to the
    * number <code>places</code>.
    * @param value
    * @param places
    * @return
    */
   public static double round(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      }
      BigDecimal bd = new BigDecimal(value);
      bd = bd.setScale(places, RoundingMode.HALF_UP);
      return bd.doubleValue();
   }

   /**This method resizes an image to the specified height and width, converts it to an <code>ImageIcon</code> object <br>
    * and returns this object.
    * @param iconToBeResized
    * @param width
    * @param height
    * @return <code>ImageIcon</code>
    */
   public static ImageIcon resizeIconAndReturn(ImageIcon iconToBeResized, int width, int height) {
      Image img = iconToBeResized.getImage();
      Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      ImageIcon icon = new ImageIcon(newImg);
      return icon;
   }

   /**This method resizes an image to the specified height and width, converts it to an <code>ImageIcon</code> object <br>
    * and sets this object as an icon to the <code>JLabel</code> parameter.
    * @param iconToBeResized
    * @param label
    * @param width
    * @param height
    * @author Naxxo
    * @version 1.0
    */
   public static void resizeIconAndSetToLabel(ImageIcon iconToBeResized, JLabel label, int width, int height) {
      Image img = iconToBeResized.getImage();
      Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      ImageIcon icon = new ImageIcon(newImg);
      label.setIcon(icon);
   }
}