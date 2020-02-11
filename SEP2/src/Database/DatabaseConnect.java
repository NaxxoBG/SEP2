package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**This class is used for the database connection. It contains the important methods
 * <ul>
 * <li> {@link #openDatabase()} used for <b>opening</b> the connection to the database</li> 
 * <li> {@link #closeDatabase()} used for <b>closing</b> the connection to the database</li>
 * <li> {@link #update(String sql, Object... args)} used for <b>updating</b> the database</li>
 * <li> {@link #query(String sql, Object...  args)} used for <b>querying</b> the database</li>
 * </ul>
 */
public class DatabaseConnect
{
   public static final String HOST = "localhost";
   public static final String PORT = "5432";  
   public static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/";
   public static final String DRIVER = "org.postgresql.Driver";
   private String url = URL;
   private String name = null;
   private String password = null;
   private Connection connection = null;


   /**Constructor that creates a <code>DatabaseConnect</code> object used for connecting with a database.
    * @param databaseName
    * @param schema
    * @param name
    * @param password
    */
   public DatabaseConnect(String databaseName, String schema, String name, String password) {
      this.url = url + databaseName + "?currentSchema=" + schema;
      this.name = name;
      this.password = password;   
      try {
         Class.forName(DRIVER);
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
      }
   }

   /**This method opens the database.
    * @throws SQLException
    */
   private void openDatabase() throws SQLException {
      connection = DriverManager.getConnection(url, name, password);
   }


   /**This method closes the database.
    * @throws SQLException
    */
   private void closeDatabase() throws SQLException {
      connection.close();
   }

   /*UPDATE or INSERT*/
   /**This method accepst a <code>String sql</code> and <code>Object... args</code> and updates the database based on the sql.
    * @param sql
    * @param args
    * @return
    * @throws SQLException
    */
   public int update(String sql, Object... args) throws SQLException {
      openDatabase();
      int rowCount = 0;
      PreparedStatement statement = null;
      try {
         statement = connection.prepareStatement(sql);
         for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
         }
         rowCount = statement.executeUpdate();
         return rowCount;
      } finally {
         if (statement != null) {
            statement.close();
         }
         closeDatabase();
      }
   }


   /**This method makes a query with the parameter <code>sql</code> and returns the result from that query in a <code>List</code>.
    * @param sql
    * @param args
    * @return
    * @throws SQLException
    */
   public ArrayList<Object[]> query(String sql, Object...  args) throws SQLException {
      openDatabase();
      PreparedStatement statement = null;
      ResultSet resultSet = null;
      try {
         statement = connection.prepareStatement(sql);
         for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
         }
         resultSet = statement.executeQuery();
         ArrayList<Object[]> list = new ArrayList<Object[]>();
         while (resultSet.next()) {
            Object[] element = new Object[resultSet.getMetaData().getColumnCount()];
            for (int i = 0; i < element.length; i++) {
               element[i] = resultSet.getObject(i + 1);
            }
            list.add(element);
         }
         return list;
      } finally {
         if (resultSet != null) {
            resultSet.close();
         }
         if (statement != null) {
            statement.close();
         }
         closeDatabase();
      }     
   }
}