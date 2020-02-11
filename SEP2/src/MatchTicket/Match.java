package MatchTicket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**This class represents the different events that a user can go through and bet on.
 */
public class Match implements Serializable
{
   private static final long serialVersionUID = 1L;
   private int ID;
   private String team1, team2;
   private int score1, score2;
   private double coefficient1, coefficient2, coefficientTie;
   private String type;
   private int length;
   private Date date;
   private static final int[] LENGTH = {48, 90, 120};

   /**Creates a <code>Match</code> without scores. The format of the {@link Date} is:
    * "yyyy-mm-dd hh:mm:ss"
    * @param team1
    * @param team2
    * @param c1
    * @param c2
    * @param tie
    * @param date
    * @param type
    */
   public Match(String team1, String team2, double c1, double c2, double tie, Date date, String type) {
      this.team1 = team1;
      this.team2 = team2;
      this.coefficient1 = c1;
      this.coefficient2 = c2;
      this.coefficientTie = tie;
      this.date = date;
      this.type = type;
      setLength(this);
   }

   /**Creates a <code>Match</code> with scores for each team.
    * @param ID
    * @param team1
    * @param team2
    * @param c1
    * @param c2
    * @param tie
    * @param score1
    * @param score2
    * @param date
    * @param length
    * @param type
    */
   public Match(int ID, String team1, String team2, double c1, double c2, double tie, int score1, int score2, Date date, int length, String type) {
      this.ID = ID;
      this.team1 = team1;
      this.team2 = team2;
      this.score1 = score1;
      this.score2 = score2;
      this.coefficient1 = c1;
      this.coefficient2 = c2;
      this.coefficientTie = tie;
      this.date = date;
      this.type = type;
      this.length = length;
   }

   /**This private method is used to set the length of the parameter <code>match</code> automatically, depending on its type. The length domain is:
    * <ul>
    * <li> 48 min - for <b>Basketball</b> </li>
    * <li> 90 min - for <b>Football</b> and <b>Hockey</b> </li>
    * <li> 120 min - for <b>Volleyball</b> </li>
    * </ul>
    * @param match
    */
   private void setLength(Match match) {
      switch (match.getType()) {
         case "Football":
         case "Hockey":
            match.length = LENGTH[1];
            break;
         case "Basketball":
            match.length = LENGTH[0];
            break ;
         case "Volleyball":
            match.length = LENGTH[2];
            break ;
      }
   }

   /**Return the id of the match.
    * @return <code>id</code>
    */
   public int getID() {
      return ID;
   }

   /**Return the first team of the match.
    * @return <code>String</code>
    */
   public String getTeam1() {
      return team1;
   }

   /**Return the second team of the match.
    * @return <code>String</code>
    */
   public String getTeam2() {
      return team2;
   }

   /**Get the score of the first team.
    * @return <code>int</code>
    */
   public int getScore1() {
      return score1;
   }

   /**Return the score of the second team.
    * @return <code>int</code>
    */
   public int getScore2() {
      return score2;
   }

   /**Return the win coefficient of the <b>first</b> team.
    * @return <code>double</code>
    */
   public double getCoefficient1() {
      return coefficient1;
   }

   /**Return the win coefficient of the <b>second</b> team.
    * @return <code>double</code>
    */
   public double getCoefficient2() {
      return coefficient2;
   }

   /**Returns the <b>tie</b> coefficient of the match.
    * @return <code>double</code>
    */
   public double getCoefficientTie() {
      return coefficientTie;
   }

   /**Returns the {@link Date} of the match.
    * @return {@link Date}
    */
   public Date getDate() {
      return date;
   }

   /**Returns a string representation of the Date of the match, formatted using the {@link SimpleDateFormat}.
    * The used pattern is: <code>"dd-MM-yyyy"</code>
    * @return
    */
   public String getStringDate() {
      SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
      return dt.format(getDate());
   }

   /**Returns the type of the match.
    * @return <code>String</code>
    */
   public String getType() {
      return type;
   }

   /**Returns the length of the match
    * @return <code>int</code>
    */
   public int getLenght() {
      return length;
   }

   /**Set the three coefficients of the match with the values of the parameters <code>double c1, double c2, double tie</code>.
    * @param c1
    * @param c2
    * @param tie
    */
   public void setCoefficient(double c1, double c2, double tie) {
      this.coefficient1 = c1;
      this.coefficient2 = c2;
      this.coefficientTie = tie;
   }

   /**Sets the scores of the match.
    * @param score1
    * @param score2
    */
   public void setScore(int score1, int score2) {
      this.score1 = score1;
      this.score2 = score2;
   }	

   public String toString() {
      return team1 + team2 + score1;
   }
}