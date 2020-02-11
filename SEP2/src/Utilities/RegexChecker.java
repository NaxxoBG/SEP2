package Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**<code>RegexChecker</code> contains useful regular expressions and the method <code>inputValidator</code>
 * , which in combination can be used for various validations of different inputs by the user.
 * Currently there are checks for <ol> <li>{@link #EMAIL}</li> <li>{@link #FIRST_NAME}</li>
 *  <li>{@link #LAST_NAME}</li> <li>{@link #FULL_NAME}</li> <li>{@link #USERNAME}</li>
 *  <li>{@link #PASSWORD}</li> <li>{@link #TEAM_NAME}</li>
 *  <li>{@link #COEFFICIENT}</li> <li>{@link #SCORE}</li> </ol>
 * @author Naxxo
 * @version 1.0
 */
public class RegexChecker
{
   /** Regex for email validation.*/
   public static final String EMAIL = "^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z]){2,63})+$";

   /** Regex for first name validation.*/
   public static final String FIRST_NAME = "^([A-Z][a-z]{1,16})$";

   /** Regex for last name validation.*/
   public static final String LAST_NAME = "^[a-zA-z]+([ '-][a-zA-Z]+)*$";

   /**Regex for full name validation.*/
   public static final String FULL_NAME = "^[A-Z][a-z .'-]+$";

   /** Regex for username validation. Requirements:
    * <ul><li>length from 1 to 15 characters</li></ul>*/
   public static final String USERNAME = "^(?=.*[a-zA-Z]{1,})(?=.*[\\d]{0,})[a-zA-Z0-9]{1,15}$";

   /**Regex expression for a password that satisfies the following requirements: 
    * <ul><li>at least 7 characters long</li> <li>at least one capital letter</li> <li>at least one numeric</li> <ul>*/
   public static final String PASSWORD = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$";

   /**Regex for a team name.*/
   public static final String TEAM_NAME = "^([A-Z](?:[A-Z]+)?|[A-Z]{1,3}[a-z]{2,10})([a-z]{2,10})?((\\-|\\s)?([A-Z][a-z]+)+)?$";

   /**Regex for a coefficient.*/
   public static final String COEFFICIENT = "^([1-9]\\.\\d\\d|[1-9]|\\d\\.\\d|[1-9]\\d\\.\\d\\d|[1-9]\\d\\.\\d|[1-9]\\d)$";

   /**Regex for a team score.*/
   public static final String SCORE = "^([1]\\d\\d|[1-9]\\d|\\d|200)$";

   /**This method accepts a <code>String</code> that acts as a regex upon the other passed parameter. It shows whether the text
    * satisfies the requirements of the passed regular expression and returns <code>true</code> or <code>false</code>.
    * @param regex
    * @param textToBeValidated
    * @return <code>true</code> or <code>false</code>
    * @version 1.0
    */
   public static boolean inputValidator(String regex, String textToBeValidated) {
      Pattern pattern = Pattern.compile(regex);
      if (textToBeValidated.equals("")) {
         return false;
      } else {
         Matcher matcher = pattern.matcher(textToBeValidated);
         return matcher.matches();
      }
   }
}