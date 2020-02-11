package MatchTicket;

import java.io.Serializable;

/**This class represents the bets that the users can place on events of their choice.
 * @author Naxxo
 */
public class Bet implements Serializable
{
   private static final long serialVersionUID = 1L;
   private int matchId;
   private String bet;
   private double coefficient;
   private String status;

   /**Creates a <code>Bet</code> object.
    * @param matchId
    * @param bet
    * @param coefficient
    */
   public Bet(int matchId, String bet, double coefficient) {
      this.matchId = matchId;
      this.bet = bet;
      this.coefficient = coefficient;
      this.status = "Pending";
   }

   /**Creates a bet with the status of it.
    * @param matchId
    * @param bet
    * @param coefficient
    * @param status
    */
   public Bet(int matchId, String bet, double coefficient, String status) {
      this.matchId = matchId;
      this.bet = bet;
      this.coefficient = coefficient;
      this.status = status;
   }

   /**Return the <code>matchid</code> of the match the bet is placed on.
    * @return <code>int</code>
    */
   public int getMatchId() {
      return matchId;
   }

   /**Return the bet.
    * @return <code>String</code>
    */
   public String getBet() {
      return bet;
   }

   /**Return the coefficient of the bet.
    * @return <code>double</code>
    */
   public double getCoefficient() {
      return coefficient;
   }

   /**Return the status of the bet.
    * @return <code>String</code>
    */
   public String getStatus() {
      return status;
   }

   /**Set the status of the bet to <code>String status</code>.
    * @param status
    */
   public void setStatus(String status) {
      this.status = status;
   }
}