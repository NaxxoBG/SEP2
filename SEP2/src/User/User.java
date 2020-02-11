package User;

import java.io.Serializable;

/**This class represents the user that is registering in the system.
 */
public class User implements Serializable
{
   private static final long serialVersionUID = 1L;
   private String userName, password;
   private double score;
   private Data information;

   /**This <code>User</code> constructor sets the class field <code>score</code> to <i>100</i> by default.
    * @param userName
    * @param password
    * @param data
    */
   public User(String userName, String password, Data data) {
      this.userName = userName;
      this.password = password;
      this.information = data;
      this.score = 100.0;
   }

   /**This <code>User</code> constructor sets the class field <code>score</code> to the value of
    * the parameter <i>float balance</i> .
    * @param userName
    * @param password
    * @param balance
    * @param data
    */
   public User(String userName, String password, double balance, Data data) {
      this.userName = userName;
      this.password = password;
      this.information = data;
      this.score = balance;
   }

   /**Returns the user's username
    * @return <code>String</code>
    */
   public String getUserName() {
      return userName;
   }

   /**Returns the user's password
    * @return <code>String</code>
    */
   public String getPassword() {
      return password;
   }

   /**Returns the user's score
    * @return <code>double</code>
    */
   public double getScore() {
      return score;
   }
   
   public void setScore(double score){
      this.score=score;
   }

   /**Returns the user's <code>{@link Data}</code>
    * @return <code>{@link Data}</code>
    */
   public Data getData() {
      return information;
   }
}