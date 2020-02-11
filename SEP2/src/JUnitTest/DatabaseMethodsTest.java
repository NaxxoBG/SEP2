package JUnitTest;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import org.junit.Test;

import Main.Implementation;
import User.Data;
import User.User;

public class DatabaseMethodsTest
{
   Implementation impl;

   public void connect() {
      impl = Implementation.getInstance();
   }

   @Test
   public void InsertUser() {
      connect();
      Data data = new Data("Tester", "test@test.com", "1990-05-05", "Male");
      User user = new User("tester", "123456", data);
      assertEquals(true, impl.insertUser(user));
      assertEquals(true, impl.verifyLogin("tester", "123456"));
   }
   @Test
   public void getUser() throws RemoteException {
      connect();
      User user = impl.getUserFullInfo("tester");
      assertEquals("Tester", user.getData().getName());
   }

   @Test
   public void balance() throws RemoteException {
      connect();
      impl.setBalance("tester", 150);
      assertEquals(150, impl.getBalance("tester"), 5 * Math.ulp(0.02));
   }


/*

   @Test
   public void availableUserName() throws RemoteException {
      connect();
      assertEquals(false, impl.availableUserName("a"));
   }
   
   @Test
   public void stringQuery() {
      String sql = "SELECT ".concat("name").concat(" FROM userinfo WHERE username = ?;");
      String sql2 = "SELECT name FROM userinfo WHERE username = ?;";
      assertEquals(sql, sql2);
   }
   
   @Test
   public void queryName() {
      connect();
      Object namer = "Atanas";
      assertEquals(namer, impl.getUserSpecifiedData("a", "name"));
   }
   
   @Test
   public void queryEmail() {
      connect();
      String namer = "tester@test.com";
      assertEquals(namer, impl.getUserSpecifiedData("a", "email"));
   }
   
   @Test
   public void querySex() {
      connect();
      String namer = "Male";
      assertEquals(namer, impl.getUserSpecifiedData("a", "sex"));
   }
   
   @Test
   public void queryDate() {
      connect();
      String namer = "1990-05-05";
      assertEquals(namer , impl.getUserSpecifiedData("a", "dob"));
   }
   
   @Test
   public void queryBalance() {
      connect();
      BigDecimal db = new BigDecimal(150.00);
      assertEquals(db.doubleValue(), impl.getUserSpecifiedData("a", "balance"));
   }
   
   @Test
   public void getDob() {
      connect();
      assertEquals(Date.class, (impl.getUserDOB("a")).getClass());
      //Method getUserDOB returns java.sql.Date object
   }
   */
}