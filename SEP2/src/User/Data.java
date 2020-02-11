package User;

import java.io.Serializable;
import java.sql.Date;

/**This class represents the main information about a User, stored in the <code>userinfo</code> database ,
 * which is
 * <ul>
 * <li> Full name </li>
 * <li> email </li>
 * <li> date of birth </li>
 * <li> sex </li>
 * </ul>
 */
public class Data implements Serializable
{
   private static final long serialVersionUID = 1L;
   private String fullName, email, sex;
   private Date DoB;

   /**Constructor of the <code>Data</code> object with the parameters: 
    * <ul>
    * <li> Full name </li>
    * <li> email </li>
    * <li> date of birth </li>
    * <li> sex </li>
    * </ul>
    * @param fullName
    * @param email
    * @param DoB
    * @param sex
    */
   public Data(String fullName, String email, String DoB, String sex) {
      this.fullName = fullName;
      this.email = email;
      this.DoB = Date.valueOf(DoB);
      this.sex = sex;
   }

   /**Returns the date of birth
    * @return <code>Date</code>
    */
   public Date getBirth() {
      return DoB;
   }

   /**Returns the user's full name
    * @return <code>String</code>
    */
   public String getName() {
      return fullName;
   }

   /**Returns the user's email
    * @return <code>String</code>
    */
   public String getEmail() {
      return email;
   }

   /**Returns the user's gender
    * @return <code>String</code>
    */
   public String getSex() {
      return sex;
   }

   /**Returns a concatenated <code>String</code> with the following data:
    * <ul> <li>Name</li> <li>Email</li> <li>Name</li> </ul>
    * @return <code>double</code>
    */
   public String toString() {
      return " Name : " + fullName + " Email : " + email + " Dob : " + DoB.toString();
   }
}