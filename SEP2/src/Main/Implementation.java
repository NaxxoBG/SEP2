package Main;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Client.IObserver;
import Database.Database;
import MatchTicket.Bet;
import MatchTicket.Match;
import MatchTicket.Ticket;
import SharedInterface.SharedInterface;
import User.Data;
import User.User;

/**The implementation object has a field {@literal Database} which allows for method invocation and interaction of methods from
 * that class and manipulation of data in the actual database.
 */
public class Implementation implements Remote, SharedInterface
{
   private ArrayList<IObserver> listOfUsers;
   private Database database;
   private static Implementation impl;

   /**
    * A private constructor for the <code>Implementation</code> object. Part of the Singleton pattern.
    * @see <a href = "http://www.tutorialspoint.com/design_pattern/singleton_pattern.htm">Singleton pattern</a>
    */
   private Implementation() {
      database = new Database("postgres", "bettingSystem", "postgres", "postgres");
      listOfUsers = new ArrayList<IObserver>();
   }

   /**A static method that returns an instance of the {@link Implementation} class. A part of the Singleton design pattern.
    * @return <code>Implementation</code>
    * @see <a href = "http://www.tutorialspoint.com/design_pattern/singleton_pattern.htm"> Singleton pattern </a>
    */
   public static Implementation getInstance() {
      if (impl == null) {
         impl = new Implementation();
      }
      return impl;
   }

   public void attach(IObserver observer) throws RemoteException {
      listOfUsers.add(observer);    
   }

   /**Notifies all the observers by updating the list of today's matches using the method {@link Client.IObserver#setMatches()}.
    * @throws RemoteException
    */
   public void notifyObservers() throws RemoteException {
      for (int i = 0; i < listOfUsers.size(); i++) {
         listOfUsers.get(i).setMatches();
      }
   }

   public boolean insertUser(User user) {
      return database.insertUser(user);
   }

   public boolean availableUserName(String userName) throws RemoteException {
      return database.availableUserName(userName);
   }

   public boolean verifyLogin(String userName, String password) {
      if(password.equals(database.getUserSpecifiedData(userName, "password"))) {
         return true;
      } else {
         return false;
      }
   }  

   public User getUserFullInfo(String userName) throws RemoteException {
      Object[] list = database.getUserFullInfo(userName);
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String date = df.format(list[4]);
      Data data = new Data((String)list[2], (String) list[5], date, (String) list[6]);
      BigDecimal bd = (BigDecimal) list[3];
      User user = new User((String)list[0], (String) list[1], bd.doubleValue(), data);
      return user;
   }

   public void editUser(User user) throws RemoteException {
      database.editUser(user);
   }

   public double getBalance(String userName) throws RemoteException {
      return (double) database.getUserSpecifiedData(userName, "balance");
   }

   public void setBalance(String userName, double balance) {
      database.setBalance(userName, balance);
   }

   /**Inserts <code>Match match</code> to database <code>match</code>.
    * @param Match match
    */
   public void insertMatch(Match match) throws RemoteException {
      database.insertMatch(match);
      notifyObservers();
   }

   /**Updates the values <code>score1</code> and <code>score2</code> of a match in the database with ID equal to
    * <code>int id</code>.
    * @param id
    * @param score1
    * @param score2
    */
   public void updateMatch(int id, int score1, int score2, Date date) {
      database.updateMatch(id,score1, score2,date);
   }

   public Match getMatch(int id) {
      return createMatches(database.getMatch(id)).get(0);     
   }

   public ArrayList<Match> getTodaysMatches() throws RemoteException {
      return createMatches(database.getTodaysMatches());
   }

   public List<Match> getMatches(String type) throws RemoteException {
      return createMatches(database.getMatches(type));
   }

   public List<Match> getMatches(Date startDate, Date endDate) throws RemoteException {
      return createMatches(database.getMatches(startDate, endDate));
   }

   public List<Match> getMatches(String type, Date date1, Date date2) {
      return createMatches(type, database.getMatches(date1, date2));
   }

   /**Returns an <code>ArrayList&ltObject[]></code> object that contains all the data about matches happening between
    * the dates <code>startDate</code> and <code>endDate</code>, including old matches.
    * @param startDate
    * @param endDate
    * @return <code>ArrayList&ltObject[]></code>
    */
   public List<Match> getOldAndNewMatches(String type, Date date1, Date date2) {
      return createMatches(type, database.getOldandNewMatches(date1, date2));
   }

   /**Creates and returns an ArrayList&ltMatch> object that contains <code>Match</code> objects created with the data from the parameter
    * <code>ArrayList&ltObject[]> object</code>.
    * @param object
    * @return <code>ArrayList&ltMatch></code>
    */
   private ArrayList<Match> createMatches(ArrayList<Object[]> object) {
      ArrayList<Match> list = new ArrayList<Match>();   
      for (int i = 0; i < object.size(); i++) {
         Object[] obj = object.get(i);
         Timestamp time = (Timestamp) obj[8];
         Date date = new Date(time.getTime());
         BigDecimal bd1 = (BigDecimal) obj[3];
         BigDecimal bd2 = (BigDecimal) obj[4];
         BigDecimal bd3 = (BigDecimal) obj[5];
         Match match = new Match((int) obj[0], (String) obj[1], (String) obj[2], bd1.doubleValue() , bd2.doubleValue(), bd3.doubleValue(), (int) obj[6], (int) obj[7], date, (int) obj[10], (String) obj[9]);
         list.add(match);
      }
      return list;
   }

   /**Creates <code>Match</code> objects only of the type <code>String type</code>, adds them
    * to a new ArrayList&ltMatch> object and returns it. Match objects are created with data from the
    * parameter <code>ArrayList&ltObject[]> object</code>.
    * @param type
    * @param object
    * @return
    */
   private ArrayList<Match> createMatches(String type, ArrayList<Object[]> object) {
      ArrayList<Match> list = new ArrayList<Match>();    
      for (int i = 0; i < object.size(); i++) {
         Object[] obj = object.get(i);
         if (String.valueOf(obj[9]).equalsIgnoreCase(type)) {
            Timestamp time = (Timestamp) obj[8];
            Date date = new Date(time.getTime());
            BigDecimal bd1 = (BigDecimal) obj[3];
            BigDecimal bd2 = (BigDecimal) obj[4];
            BigDecimal bd3 = (BigDecimal) obj[5];
            Match match = new Match((int) obj[0], (String) obj[1], (String) obj[2], bd1.doubleValue() , bd2.doubleValue(), bd3.doubleValue(), (int) obj[6], (int) obj[7], date, (int) obj[10], (String) obj[9]);
            list.add(match);
         }
      }
      return list;
   }

   public boolean addTicket(Ticket ticket) throws RemoteException {
      boolean result  = database.insertTicket(ticket); 
      Ticket ticket2 = createTicket(database.getTickets(ticket.getName()).get(0));
      for (int i = 0; i < ticket.getBets().size(); i++) {
         database.insertMatchesToTicketInfo(ticket2, ticket.getBets().get(i));
      }
      return result;  
   }

   public ArrayList<Ticket> getTickets(String userName) throws RemoteException {
      ArrayList<Ticket> list = new ArrayList<Ticket>();
      ArrayList<Object[]> array = database.getTickets(userName);
      if (array != null){
         for (int i = 0; i < array.size(); i++) {
            list.add(createTicket(array.get(i)));
         }
      }
      return list;
   }

   /**Returns a <code>Ticket</code> object, created with data from the
    * parameter <code>Object[] array</code>
    * @param array
    * @return
    */
   private Ticket createTicket(Object[] array) {
      ArrayList<Bet> bets = createListOfBets((int) array[0]);
      Date date = (Date) array[5];
      BigDecimal bd1 = (BigDecimal) array[2];
      BigDecimal bd2 = (BigDecimal) array[3];
      Ticket ticket = new Ticket((int) array[0], (String) array[1], bd1.doubleValue(), bd2.doubleValue(), (String) array[4], date, bets);
      return ticket;
   }

   /**Creates <code>Bet</code> objects with the data returned from the method {@link Database#getMatchesFromTicketInfo(int)},
    * adds those objects to an <code>ArrayList&ltbet></code> and returns that list.
    * @param ticketid
    * @return <code>ArrayList&ltBet>
    */
   private ArrayList<Bet> createListOfBets(int ticketid) {
      ArrayList<Bet> bets = new ArrayList<Bet>();
      ArrayList<Object[]> list = database.getMatchesFromTicketInfo(ticketid);
      for (int i = 0; i < list.size(); i++) {
         Bet bet = createBet(list.get(i));
         bets.add(bet);
      }
      return bets;
   }

   /**Returns a <code>Bet</code> object, created with data from the parameter <code>Object[] list</code>.
    * @param list
    * @return
    */
   private Bet createBet(Object[] list) {
      BigDecimal bd =  (BigDecimal) list[2];
      Bet bet = new Bet((int) list[0], (String) list[1], bd.doubleValue(), (String) list[3]);
      return bet;
   }

   public void setTicketResult(int id, boolean value) throws RemoteException {
      database.setTicketResult(id, value);
   }

   public void updateMatchesInTicket(Ticket ticket, Bet bet) throws RemoteException {
      database.setMatchResultsInTicket(ticket.getID(), bet.getMatchId(), bet.getStatus());
   }

   /**Queries the <code>userinfo</code> database to get
    * information, based on the parameter <code>requestedData</code> which is one of the following:
    * <ul>
    * <li> <b>username</b> </li>
    * <li> <b>name</b> </li>
    * <li> <b>balance</b> </li>
    * <li> <b>dob</b> </li>
    * <li> <b>email</b> </li>
    * <li> <b>sex</b> </li>
    * </ul>
    * @param username
    * @param requestedData
    * @return T
    */
   public Object getUserSpecifiedData(String username, String requestedData) {
      return database.getUserSpecifiedData(username, requestedData);
   } 

   /**Return the date of birth of the user with the username <code>String userName</code>.
    * @param userName
    * @return <code>Date date</code>
    */
   public Date getUserDOB(String userName) {
      return (Date) database.getUserSpecifiedData(userName, "dob");
   }

   public String testConnection(String text) throws RemoteException {
      String reply = text.toUpperCase();
      return reply;
   }
}