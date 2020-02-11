package SharedInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import Client.IObserver;
import MatchTicket.Bet;
import MatchTicket.Match;
import MatchTicket.Ticket;
import User.User;

/**This interface contains useful methods for the {@link User}, {@link Match} and {@link Ticket} classes.
 */
public interface SharedInterface extends Remote
{
   /**Attach an observer to the list of observers.
    * @param observer
    * @throws RemoteException
    */
   public void attach(IObserver observer) throws RemoteException;

   /**Insert a new user in the database.
    * @param user
    * @return
    * @throws RemoteException
    */
   public boolean insertUser(User user) throws RemoteException;


   /**Checks if the parameter <code>userName</code> is already
    * used by another user as a username for registration.
    * @param userName
    * @return <code>true</code> or <code>false</code>
    * @throws RemoteException
    */
   public boolean availableUserName(String userName) throws RemoteException;

   /**Used for the login verification process. It checks
    * the username and password pair as well as a check with
    * the data from the login database.
    * @param userName
    * @param password
    * @return <code>true</code> or <code>false</code>
    * @throws RemoteException
    */
   public boolean verifyLogin(String userName, String password) throws RemoteException;

   /**Returns the <code>User</code> with the given username
    * @param userName
    * @return <code>User</code>
    * @throws RemoteException
    */
   public User getUserFullInfo(String userName) throws RemoteException;

   /**Updates the <code>User user</code>'s data in the database.
    * @param user
    * @throws RemoteException
    */
   public void editUser(User user) throws RemoteException;

   /**Return the balance of the user with username <code>String userName</code>.
    * @param userName
    * @return <code>double</code>
    * @throws RemoteException
    */
   public double getBalance(String userName) throws RemoteException;

   /**Sets the balance of the user with username <code>userName</code> to the value of <code>double d</code>.
    * @param userName
    * @param d
    * @throws RemoteException
    */
   public void setBalance(String userName, double d) throws RemoteException;

   /**Return a match with the id <code>int id</code>.
    * @param id
    * @return <code>Match</code>
    * @throws RemoteException
    */
   public Match getMatch(int id) throws RemoteException;

   /**Return a <code>ArrayList</code> containing today's matches.
    * @return <code>ArrayList</code>
    * @throws RemoteException
    */
   public List<Match> getTodaysMatches() throws RemoteException;

   /**Return a <code>ArrayList</code> containing matches of type <code>String type</code>.
    * @param type
    * @return <code>ArrayList</code>
    * @throws RemoteException
    */
   public List<Match> getMatches(String type) throws RemoteException;

   /**Return a <code>List</code> of matches scheduled to happen between <code>{@link Date} startDate</code> and
    * <code>{@link Date} endDate</code>.
    * @param startDate
    * @param endDate
    * @return <code>ArrayList</code>
    * @throws RemoteException
    */
   public List<Match> getMatches(Date startDate, Date endDate) throws RemoteException;

   /**Return an <code>ArrayList</code> containing matches of <code>String type</code> and scheduled
    * to happen between <code>{@link Date} startDate</code> and <code>{@link Date} endDate</code>.
    * @param type
    * @param date1
    * @param date2
    * @return
    * @throws RemoteException
    */
   public List<Match> getMatches(String type, Date date1, Date date2) throws RemoteException;

   /**Adds the passed <code>Ticket</code> object to the <code>ticket</code> database.
    * @param ticket
    * @return <code>true</code> or <code>false</code>
    * @throws RemoteException
    */
   public boolean addTicket(Ticket ticket) throws RemoteException;

   /**Returns an <code>ArrayList</code> containing all tickets that belong to a User with <code>String userName</code>.
    * @param user
    * @return <code>ArrayList</code>
    * @throws RemoteException
    */
   public List<Ticket> getTickets(String userName) throws RemoteException;

   /**Sets the status of a ticket with ID <code>int id</code> to either "Win" or "Lose".
    * @param id
    * @param value
    */
   public void setTicketResult(int id, boolean value) throws RemoteException;

   /**Change the status of a match with a <code>matchid</code> in <code>ticketinfo</code> in a ticket with id <code>int ID</code>
    * <code>matchid</code> and <code>status</code> are taken from the passed <code>bet</code> object
    * @param ticket
    * @param bet
    * @throws RemoteException
    */
   public void updateMatchesInTicket(Ticket ticket, Bet bet) throws RemoteException;

   /**Tests the connection by converting the parameter <code>text</code> to upper case and returning it.
    * @param text
    * @return <code>String</code>
    * @throws RemoteException
    */
   public String testConnection(String text) throws RemoteException;
}