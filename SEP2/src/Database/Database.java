package Database;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import MatchTicket.Bet;
import MatchTicket.Match;
import MatchTicket.Ticket;
import User.User;

/**Class representing the <code>Database</code> object.*/
public class Database
{
   private DatabaseConnect connect;

   /**Constructor of the <code>Database</code> object.
    * @param databaseName
    * @param schema
    * @param name
    * @param password
    */
   public Database(String databaseName, String schema, String name, String password) {
      connect = new DatabaseConnect(databaseName, schema, name, password);
   }

   /**Checks if there is a username in the database, which is the same as the one, passed as a parameter.
    * @param username
    * @return
    */
   public boolean availableUserName(String username) {
      String sql = "SELECT username FROM logIn WHERE username = ?;";     
      try {
         ArrayList<Object[]> list = connect.query(sql, username);  
         return list.size() == 0;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }  
   }

   /**This method inserts the new <code>User</code> to the database
    * @param user
    * @return
    */
   public boolean insertUser(User user) {
      String sql1 = "INSERT INTO logIn (username, password) VALUES (?,?)";
      String sql2 = "INSERT INTO userInfo (username, name, balance, dob, email, sex) VALUES (?,?,?,?,?,?)";
      try {
         connect.update(sql1, user.getUserName(), user.getPassword());         
         connect.update(sql2, user.getUserName(), user.getData().getName(), user.getScore(), user.getData().getBirth(), user.getData().getEmail(), user.getData().getSex());
         return true;
      } catch (Exception e) {
         System.err.println(e.getMessage());
         return false;
      }
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
      <li> <b>password</b> </li>
    * </ul>
    * @param username
    * @param requestedData
    * @return T
    */
   public Object getUserSpecifiedData(String username, String requestedData) {
      String sql = "SELECT ".concat(requestedData).concat(" FROM userinfo WHERE username = (?);");
      String sql2 = "SELECT ".concat(requestedData).concat(" FROM login WHERE username = (?);");
      try {
         if(requestedData.equalsIgnoreCase("password")){
            return (String) connect.query(sql2, username).get(0)[0];
         }else if(requestedData.equalsIgnoreCase("dob")){
            return (Date) connect.query(sql, username).get(0)[0];
         }
         else {
            Class<? extends Object> clazz = connect.query(sql, username).get(0)[0].getClass();
            if (clazz.equals(BigDecimal.class)) {
               BigDecimal bd = (BigDecimal) connect.query(sql, username).get(0)[0];
               return bd.doubleValue();
            } else {
               return String.valueOf(connect.query(sql, username).get(0)[0]);
            }
         }
      }
      catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }





   /**Returns an <code>Object[]</code> array with the information about a user with the username <code>String username</code>.
    * @param username
    * @return
    */
   public Object[] getUserFullInfo(String username) {
      String sql = "SELECT u.username, l.password, u.name, u.balance, u.dob, u.email, u.sex FROM userinfo u, login l WHERE l.username = u.username AND u.username = (?)";
      try {
         Object[] obj = connect.query(sql, username).get(0);
         return obj;
      } catch (Exception e) {
         return null;
      }
   }

   /**Accepts the parameter <code>User user</code>, runs the queries and updates the user's information in the database.
    * @param user
    */
   public void editUser(User user) {
      String sql1 = "UPDATE logIn SET password = ? WHERE username = ?;";
      String sql2 = "UPDATE userInfo SET name = ?, dob = ?, email = ?, sex = ? WHERE username = ?;";
      try {
         connect.update(sql1, user.getPassword(), user.getUserName());
         connect.update(sql2, user.getData().getName(), user.getData().getBirth(), user.getData().getEmail(), user.getData().getSex(), user.getUserName());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**Sets the balance of the user with username <code>String username</code> to <code>double balance</code>.
    * @param username
    * @param balance
    */
   public void setBalance(String username, double balance) {
      String sql = "UPDATE userInfo SET balance = ? WHERE username = ?";
      try {
         connect.update(sql, balance, username);
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   /**Inserts <code>Match match</code> to database <code>match</code>.
    * @param Match match
    */
   public void insertMatch(Match match) {
      String sql = "INSERT INTO match(team1, team2, coefficient1, coefficient2, coefficientTie, score1, score2, timeStart, type, length) VALUES(?,?,?,?,?,?,?,?,?,?);";
      try {
         Timestamp timestamp = new Timestamp(match.getDate().getTime());
         connect.update(sql, match.getTeam1(), match.getTeam2(), match.getCoefficient1(), match.getCoefficient2(), match.getCoefficientTie(), match.getScore1(), match.getScore2(), timestamp, match.getType(), match.getLenght());
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   /**Updates the values <code>score1</code> and <code>score2</code> of a match in the database with ID equal to
    * <code>int id</code>.
    * @param id
    * @param score1
    * @param score2
    */
   public void updateMatch(int id, int score1, int score2,Date date) {
      String sql = "UPDATE match SET score1 = ?, score2 = ?,timestart = ? WHERE matchId = ?;";
      Timestamp timeStamp = new Timestamp(date.getTime());
      try {
         connect.update(sql,  score1, score2,timeStamp,id);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**Returns an <code>ArrayList&ltObject[]></code> object that contains all the data about a match with match ID
    * equal to <code>int id</code>.
    * @param ID
    * @return
    */
   public ArrayList<Object[]> getMatch(int ID) {
      String sql = "SELECT matchid, team1, team2, coefficient1, coefficient2, coefficienttie, score1, score2, timeStart, type, length" + " FROM match WHERE matchid=?";
      try {
         return connect.query(sql, ID);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**Returns an <code>ArrayList&ltObject[]></code> object that contains all the data about today's matches.
    * The returned <code>ArrayList&ltObject[]></code> contains object arrays with each <code>Object[]</code> storing
    * information about a match.
    * @return <code>ArrayList</code>
    */
   public ArrayList<Object[]> getTodaysMatches() {
      String sql = "SELECT matchid, team1, team2, coefficient1, coefficient2, coefficienttie, score1, score2, timeStart, type, length"
            + " FROM match WHERE timeStart>? AND timeStart<?";
      Calendar cal = Calendar.getInstance();
      java.util.Date date1 = new java.util.Date(cal.getTimeInMillis());  
      java.util.Date date2 = nextDay(cal.getTime());
      Timestamp timestamp1 = new Timestamp(date1.getTime());
      Timestamp timestamp2 = new Timestamp(date2.getTime());
      try {
         return connect.query(sql, timestamp1, timestamp2);
      } catch (Exception e) {
         System.err.println(e.getMessage());
         return null;
      }         
   }

   /**Returns an <code>ArrayList&ltObject[]></code> object that contains all the data about matches
    * with type <code>String type</code>.
    * @param type
    * @return
    */
   public ArrayList<Object[]> getMatches(String type) {
      String sql = "SELECT matchid, team1, team2, coefficient1, coefficient2, coefficienttie, score1, score2, timeStart, type, length"
            + " FROM match WHERE type = ? AND timeStart>?;";
      try {
         Calendar cal = Calendar.getInstance();
         Timestamp timestamp1 = new Timestamp(cal.getTimeInMillis());
         return connect.query(sql, type,timestamp1);
      } catch (Exception e) {
         return null;
      }
   }

   /**Returns an <code>ArrayList&ltObject[]></code> object that contains all the data about matches happening between
    * the dates <code>startDate</code> and <code>endDate</code>.
    * @param startDate
    * @param endDate
    * @return
    */
   public ArrayList<Object[]> getMatches(java.util.Date startDate, java.util.Date endDate) {
      String sql = "SELECT matchid, team1, team2, coefficient1, coefficient2, coefficienttie, score1, score2, timeStart, type, length"
            + " FROM match WHERE timeStart >= ? AND timeStart <= ?";
      Calendar cal = Calendar.getInstance();
      Timestamp timestamp1;
      Timestamp timestamp2;
      if (startDate.getTime() < cal.getTimeInMillis()) {
         timestamp1= new Timestamp(cal.getTimeInMillis());
      } else {
         timestamp1 = new Timestamp(startDate.getTime());
      }    
      timestamp2 = new Timestamp(nextDay(endDate).getTime());      
      try {
         return connect.query(sql, timestamp1, timestamp2);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }     
   }


   /**Returns an <code>ArrayList&ltObject[]></code> object that contains all the data about matches happening between
    * the dates <code>startDate</code> and <code>endDate</code>, including old matches.
    * @param startDate
    * @param endDate
    * @return <code>ArrayList&ltObject[]></code>
    */
   public ArrayList<Object[]> getOldandNewMatches(java.util.Date startDate, java.util.Date endDate) {
      String sql = "SELECT matchid, team1, team2, coefficient1, coefficient2, coefficienttie, score1, score2, timeStart, type, length"
            + " FROM match WHERE timeStart >= ? AND timeStart <= ?";

      Timestamp timestamp1;
      Timestamp timestamp2;

      timestamp1 = new Timestamp(startDate.getTime());
      timestamp2 = new Timestamp(nextDay(endDate).getTime());      
      try {
         return connect.query(sql, timestamp1, timestamp2);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }     
   }




   //*******************************************Edit/Add database method for table ticketinfo***************************
   /**Inserts a <code>Ticket</code> object in the <code>ticket</code> database.
    * @param ticket
    * @return
    */
   public boolean insertTicket(Ticket ticket) {
      String sql = "INSERT INTO ticket (username, totalOdd, money) VALUES(?,?,?);";       
      try {          
         connect.update(sql, ticket.getName(), ticket.getTotalOdd(), ticket.getMoney());
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   /**Returns an <code>ArrayList&ltObject[]></code> object that contains all the tickets of a user with username
    * <code>String username</code>.
    * @param username
    * @return
    */
   public ArrayList<Object[]> getTickets(String username) {
      String sql = "SELECT ticketid, username, totalOdd, money, status, ticket.timeStart FROM ticket WHERE username = ? ORDER BY ticket.timeStart DESC;";
      try {
         return connect.query(sql, username);
      } catch (Exception e) {
         return null;
      }
   }

   /**Get all the matches from <code>ticketinfo</code> table with the given <code>ticketid</code> and return
    * an <code>ArrayList&ltObject[]></code> with those matches.
    * @param ticketid
    * @return <code>ArrayList&ltObject[]></code>
    */
   public ArrayList<Object[]> getMatchesFromTicketInfo(int ticketid) {
      String sql = "SELECT matchid, bet, coefficient, status FROM ticketinfo WHERE ticketid = ?;";
      try {
         return connect.query(sql, ticketid);
      } catch (Exception e) {
         return null;
      }
   }

   /**Insert matches from the parameter <code>Ticket ticket</code> to the <code>ticketinfo</code> table;
    * @param ticket
    * @param bet
    */
   public void insertMatchesToTicketInfo(Ticket ticket, Bet bet) {
      String sql = "INSERT INTO ticketinfo(ticketid, matchid, bet, coefficient, status) VALUES(?,?,?,?,?)";
      try {
         connect.update(sql, ticket.getID(), bet.getMatchId(), bet.getBet(), bet.getCoefficient(), bet.getStatus());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**Sets the status of a ticket with ID <code>int id</code> to either "Win" or "Lose".
    * @param id
    * @param value
    */
   public void setTicketResult(int ticketid, boolean value) {
      String sql = "UPDATE ticket SET status = ? WHERE ticketId = ?;";
      try {
         if (value) {
            connect.update(sql, "Win", ticketid);
         } else {
            connect.update(sql, "Lose", ticketid);
         }
      } catch (Exception e)  {
         e.printStackTrace();
      }
   }

   /**Change the status of a match with a <code>matchid</code> in <code>ticketinfo</code>
    * in a ticket with id <code>int ID</code>
    */
   public void setMatchResultsInTicket(int ticketid, int matchid, String status) {
      String sql = "UPDATE ticketinfo SET status = ? WHERE ticketid = ? AND matchid = ?;";
      try {
         connect.update(sql, status, ticketid, matchid);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**Returns a <code>Date</code> object which is one day later from the parameter <code>Date</code>.
    * @param date
    * @return
    */
   private Date nextDay(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(date.getTime());
      cal.add(Calendar.DAY_OF_MONTH, 1);
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      return cal.getTime();
   }
}