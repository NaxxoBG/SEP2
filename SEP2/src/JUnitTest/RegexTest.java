package JUnitTest;

import static org.junit.Assert.*;
import org.junit.Test;
import Utilities.RegexChecker;

public class RegexTest
{
   //EMAIL : "^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z]){2,63})+$";
   @Test
   public void testEmail() throws Exception {
      String testerEmail = "atanas.latinov@yahoo.com";
      System.out.println("Email -> " + testerEmail + " -> " +
      RegexChecker.inputValidator(RegexChecker.EMAIL, testerEmail));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.EMAIL, testerEmail));
   }
   //PASSWORD : "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$";
   @Test
   public void testPassword() throws Exception {
      String testerPassword = "alo1Alo";
      System.out.println("Password -> " + testerPassword + " -> " + 
      RegexChecker.inputValidator(RegexChecker.PASSWORD, testerPassword));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.PASSWORD, testerPassword));
   }
   // TEAM_NAME : "^([A-Z](?:[A-Z]+)?|[A-Z]{1,3}[a-z]{2,10})([a-z]{2,10})?((\\-|\\s)?([A-Z][a-z]+)+)?$";
   @Test
   public void testerTeam1() throws Exception {
      String testerTeam1 = "LA Lakers";
      System.out.println("Team1 name -> " + testerTeam1 + " -> " + 
      RegexChecker.inputValidator(RegexChecker.TEAM_NAME, testerTeam1));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.TEAM_NAME, testerTeam1));
   }
   

   @Test
   public void testFirstName() throws Exception {
      String testerFirstName = "Atanasfasfafds";
      System.out.println("First name -> " + testerFirstName + " -> " + RegexChecker.inputValidator(RegexChecker.FIRST_NAME, testerFirstName));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.FIRST_NAME, testerFirstName));
   }

   @Test
   public void testLastName() throws Exception {
      String testerLastName = "Wilkinson-N'yongo";
      System.out.println("Last name -> " + testerLastName + " -> " + RegexChecker.inputValidator(RegexChecker.LAST_NAME, testerLastName));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.LAST_NAME, testerLastName));
   }

   @Test
   public void testFullName() throws Exception {
      String testerFullName = "Fred white";
      System.out.println("Full Name -> " + testerFullName + " -> " + RegexChecker.inputValidator(RegexChecker.FULL_NAME, testerFullName));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.FULL_NAME, testerFullName));
   }

   @Test
   public void testUsername() throws Exception {
      String testerUserName = "king45";
      System.out.println("Username -> " + testerUserName + " -> " + RegexChecker.inputValidator(RegexChecker.USERNAME, testerUserName));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.USERNAME, testerUserName));
   }

 

 

   @Test
   public void testerTeam2() throws Exception {
      String testerTeam2 = "Manchester";
      System.out.println("Team2 name -> " + testerTeam2 + " -> " + RegexChecker.inputValidator(RegexChecker.TEAM_NAME, testerTeam2));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.TEAM_NAME, testerTeam2));
   }

   @Test
   public void testerCoefficient() throws Exception {
      String testerCoefficient = "1.40";
      System.out.println("Coefficient -> " + testerCoefficient + " -> " + RegexChecker.inputValidator(RegexChecker.COEFFICIENT, testerCoefficient));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.COEFFICIENT, testerCoefficient));
   }

   @Test
   public void testerScore() throws Exception {
      String testerScore = "1";
      System.out.println("Score -> " + testerScore + " -> " + RegexChecker.inputValidator(RegexChecker.SCORE, testerScore));
      assertEquals(true, RegexChecker.inputValidator(RegexChecker.SCORE, testerScore));
   }
}