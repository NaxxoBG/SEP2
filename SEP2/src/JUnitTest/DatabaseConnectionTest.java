package JUnitTest;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import Database.DatabaseConnect;

public class DatabaseConnectionTest
{
   DatabaseConnect connect;

   private void databaseConnect() {
      connect = new DatabaseConnect("postgres", "bettingSystem", "postgres", "postgres");
   }

   @Test
   public void databaseWrite() throws SQLException {
      databaseConnect();
      String sql = "INSERT INTO logIn (username, password) VALUES (?,?);";
      int row = connect.update(sql, "tester2", "123456");
      assertEquals(true, row != 0);
   }

   @Test
   public void databaseRead() throws SQLException {
      databaseConnect();
      String sql = "SELECT username, password FROM logIn WHERE username = ?";
      ArrayList<Object[]> list  = connect.query(sql, "tester2");
      assertEquals(true, list.size() > 0);
      assertEquals("123456", list.get(0)[1]);
   }
}