package Client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import MatchTicket.Bet;
import MatchTicket.Match;
import MatchTicket.Ticket;
import SharedInterface.SharedInterface;
import User.User;

/**This class represents the Client of the system. It also contains useful methods for the
 * ticket and match data manipulation.
 */
public class Client extends UnicastRemoteObject implements IObserver, Serializable 
{
   private static final long serialVersionUID = 1L;
   private SharedInterface inter;
   private User user;
   private List<Match> todayList;  
   private ArrayList<Ticket> tickets;     
   private HashMap<Integer, Match> matches = null;
   private static final int ONE_MINUTE_IN_MILLIS = 60 * 1000;

   /**Instantianes a <code>Client</code> object with a new <code>ArrayList</code> storing <code>Match</code>
    * objects. This object also looks up the shared interface thorught the address in <code>String name</code>.
    * @param ip
    * @throws MalformedURLException
    * @throws RemoteException
    * @throws NotBoundException
    */
   public Client(String ip) throws MalformedURLException, RemoteException, NotBoundException {
      String name = "//" + ip + "/Server";
      inter = (SharedInterface) Naming.lookup(name);
      todayList = new ArrayList<Match>();
   }

   /**Returns the <code>SharedInterface</code> necessary for invoking methods in other classes.
    * @return <code>SharedInterface</code>
    */
   public SharedInterface getInter() {
      return inter;
   }

   public void setUser(String userName) throws RemoteException {
      this.user = inter.getUserFullInfo(userName);
   }

   public void setMatches() throws RemoteException {
      todayList = inter.getTodaysMatches();
   }

   public void setTickets() throws RemoteException {
      if (matches == null) {
         matches = new HashMap<>();
      }
      if (tickets == null) {
         tickets = (ArrayList<Ticket>) inter.getTickets(user.getUserName());
         for (int i = 0; i < tickets.size(); i++) {
            checkTicket(tickets.get(i));
         }
      }
      tickets = (ArrayList<Ticket>) inter.getTickets(user.getUserName());
   }

   /**Returns the user of this client.
    * @return <code>User</code>
    */
   public User getUser() {
      return user;
   }

   /**Returns a <code>List&ltMatch></code>, containing today's matches.
    * @return <code>List&ltMatch></code>
    */
   public List<Match> getTodaysMatches() {
      return todayList;
   }

   /**Returns an ArrayList&ltTicket>, containing the tickets of the client
    * @return <code>ArrayList&ltTicket></code>
    * @throws RemoteException
    */
   public ArrayList<Ticket> getTickets() throws RemoteException {
      return tickets;
   }

   /**Updates the <code>User user</code>'s data in the database.
    * @param user
    * @throws RemoteException
    */
   public void editUser(User user) throws RemoteException{
      inter.editUser(user);
   }

   /**Sets the score of the the <code>Client</code>'s user to <code>double d</code>
    * @param d
    * @throws RemoteException
    */
   public void setScore(double d) throws RemoteException{
      inter.setBalance(user.getUserName(), d);
      setUser(user.getUserName());
   }

   public Object[][] getHistoryMatches(int index) throws RemoteException {
      try {
         Ticket ticket = tickets.get(index);
         Object[][] obj = createOldTicket(ticket);
         return obj;
      } catch (Exception e) {
         return null;
      }
   }

   /**
    * @param ticket
    * @return
    * @throws RemoteException
    */
   private Object[][] createOldTicket(Ticket ticket) throws RemoteException {
      Object[][] obj = new Object[ticket.getBets().size()][8];
      ArrayList<Bet> bets = ticket.getBets();
      for (int i = 0; i < bets.size(); i++) {
         Match match;
         if (matches.containsKey(ticket.getBets().get(i).getMatchId())) {
            match = matches.get(ticket.getBets().get(i).getMatchId());
         } else {
            match = inter.getMatch(bets.get(i).getMatchId());
            matches.put(match.getID(), match);
         }
         Object[] object = createOldMatch(ticket.getBets().get(i), match);
         obj[i] = object;     
      }
      return obj;
   }

   /**Returns an <code>Object[]</code> array, created using data from the
    * parameters <code>bet</code> and <code>match</code>.
    * @param bet
    * @param match
    * @return
    */
   private Object[] createOldMatch(Bet bet, Match match) {
      Object[] obj = new Object[10];
      obj[0] = match.getID();
      obj[1] = match.getTeam1();
      obj[2] = match.getTeam2();
      obj[3] = match.getScore1();
      obj[4] = match.getScore2();
      obj[5] = bet.getBet();
      obj[6] = bet.getCoefficient();
      obj[7] = bet.getStatus();
      obj[8] = match.getDate();
      obj[9] = match.getDate();
      return obj;
   }

   /**
    * @param ticket
    * @throws RemoteException
    */
   public void checkTicket(Ticket ticket) throws RemoteException{
      boolean change = false;
      if (ticket.getStatus().equalsIgnoreCase("Pending")) {
         for (int i = 0; i < ticket.getBets().size(); i++) {
            Match match;
            if (matches.containsKey(ticket.getBets().get(i).getMatchId())) {
               match = matches.get(ticket.getBets().get(i).getMatchId());
            } else {
               match = inter.getMatch(ticket.getBets().get(i).getMatchId());
               matches.put(match.getID(), match);
            }
            if (ticket.getBets().get(i).getStatus().equals("Pending") && matchHasEnded(match)) {
               checkMatch(ticket, match, ticket.getBets().get(i));
               change = true;
            }
         }
         if (change) {
            setTicketResult(ticket);
            
         }
      }
   }

   /**
    * @param ticket
    * @throws RemoteException
    */
   private void setTicketResult(Ticket ticket) throws RemoteException {
      boolean win = true;
      for (int i = 0; i < ticket.getBets().size(); i++) {
         if (ticket.getBets().get(i).getStatus().equals("Pending")) {
            return;
         }
         else if (ticket.getBets().get(i).getStatus().equals("Lose")) {
            win = false;
         }
      }
      if (win) {
         inter.setTicketResult(ticket.getID(), true);
         setScore(user.getScore()+ticket.getMoney()*ticket.getTotalOdd());
         user.setScore(inter.getBalance(user.getUserName()));
      } else {
         inter.setTicketResult(ticket.getID(), false);
      }
   }

   /**Checks if the passed object <code>match</code> has ended.
    * @param match
    * @return <code>true</code> or <code>false</code>
    * @throws RemoteException
    */
   private boolean matchHasEnded(Match match) throws RemoteException {
      Calendar cal = Calendar.getInstance();
      Date now = cal.getTime();
      Date matchDate = match.getDate();
      Date matchEnd = new Date(matchDate.getTime() + (match.getLenght() * ONE_MINUTE_IN_MILLIS));
      if (matchEnd.before(now)) {
         return true;
      } else { 
         return false;
      }
   }

   private void checkMatch(Ticket ticket, Match match, Bet bet) {
      if (getWinner(match).equals(bet.getBet())) { 
         bet.setStatus("Win");
      } else {
         bet.setStatus("Lose"); 
      }
      try {
         inter.updateMatchesInTicket(ticket, bet);
      } catch (RemoteException e) {
         e.printStackTrace();
      }
   }

   /**Return the winner from the <code>match</code>. The String domain is:
    * <ul>
    * <li> "1" if team 1 wins </li> 
    * <li> "2" if team 2 wins </li> 
    * <li> "X" if it is a tie </li>
    * </ul>
    * @param match
    * @return <code>String</code>
    */
   private String getWinner(Match match) {
      if (match.getScore1() > match.getScore2()) {
         return "1";
      } else if (match.getScore1() < match.getScore2()) {
         return "2";
      } else {
         return "X";
      }
   }

   public void resetLoading() throws RemoteException{
      matches = null;
      tickets = null;
      setTickets();
   }

   public void attach() throws RemoteException {
      inter.attach(this);
   }
}