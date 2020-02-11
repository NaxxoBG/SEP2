package MatchTicket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**Ticket class represents a ticket with bets placed by the user on matches of hir/her choice.
 * The ticket has a status, money placed in it, as well as a list of the bets
 *
 */
public class Ticket implements Serializable
{
   private static final long serialVersionUID = 1L;
   private int ID;
   private String userName;
   private double totalOdd = 1;
   private double money;
   private String status;
   private Date date;
   private String formatt = "%5d, %8s, %6.2f, %12.2f, %12s %n";
   private ArrayList<Bet> list;

   /**Used to instantiate <code>Ticket</code> objects. Upon being instantiated, a <code>Ticket</code> object's status is set to "Pending" by default.
    * @param ID
    * @param userName
    * @param money
    */
   public Ticket(String userName, double money) {
      list = new ArrayList<Bet>();
      ID = 1;
      this.userName = userName;
      this.money = money;
      this.status = "Pending";
   }

   /**<code>Ticket</code> constructor which instantiates objects that contains an edited list of match bets.
    * Primarily used for checking betting history history.
    * @param ID
    * @param userName
    * @param totalOdd
    * @param money
    * @param status
    * @param date
    * @param list
    */
   public Ticket(int ID, String userName, double totalOdd, double money,
         String status, Date date, ArrayList<Bet> list) {
      this.ID = ID;
      this.userName = userName;
      this.totalOdd = (double) totalOdd;
      this.money = (double) money;
      this.status = status;
      this.date = date;
      this.list = list;
   }

   /**Add the passed parameter <code>Bet bet</code> to the list of bets as well as adjust the total odd.
    * @param bet
    */
   public void add(Bet bet) {
      list.add(bet);
      totalOdd = totalOdd * bet.getCoefficient();
   }

   /**Remove a bet and its respective match from the list of bets.
    * @param bet
    */
   public void removeMatch(Bet bet) {
      list.remove(bet);
      totalOdd = totalOdd / bet.getCoefficient();
   }

   /**Returns the <code>ArrayList&ltBet></code> object that contains all the bets
    * @return <code>ArrayList&ltBet></code>
    */
   public ArrayList<Bet> getBets() {
      return list;
   }

   /**Returns the status of the <code>Ticket</code>.
    * @return <code>String</code>
    */
   public String getStatus() {
      return status;
   }

   /**Returns the total odd of the <code>Ticket</code>.
    * @return <code>double</code>
    */
   public double getTotalOdd() {
      return totalOdd;
   }

   /**Returns the <code>ID</code> of the ticket.
    * @return <code>int</code>
    */
   public int getID() {
      return ID;
   }

   /**Returns the username of the user that owns this ticket.
    * @return <code>String</code>
    */
   public String getName() {
      return userName;
   }

   /**Returns the amount of money placed on this ticket
    * @return <code>double</code>
    */
   public double getMoney() {
      return money;
   }

   /**Returns the date of the ticket, formatted with <code>SimpleDateFormat</code>.
    * @return <code>String</code>
    */
   public String getDate() {
      SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
      return format.format(date);
   }

   /**Returns the time of the ticket, formatted with <code>SimpleDateFormat</code>.
    * @return <code>String</code>
    */
   public String getTime() {
      SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
      return format.format(date);
   }

   /**Sets the status of the ticket to <code>status</code>.
    * @param status
    */
   public void setStatus(String status) {
      this.status = status;
   }

   public String toString() {
      return String.format(formatt, getID(), getStatus(), getMoney(),
            getTotalOdd(), getDate());
   }
}