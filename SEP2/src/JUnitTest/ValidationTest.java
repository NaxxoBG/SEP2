package JUnitTest;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

import Utilities.RegexChecker;

public class ValidationTest
{
   @Test
   public void test() {
      /*trueOrFalse.apply(teamName.apply(g));*/
      Function<String, Boolean> teamName = s -> RegexChecker.inputValidator(RegexChecker.TEAM_NAME, s);
      Function<Boolean, Boolean> trueOrFalse = f -> f == false;
      Function<String, Boolean> evaluator = g -> teamName.andThen(trueOrFalse).apply(g);
      String name = "Manchester";
      if (evaluator.apply(name)) {
         System.out.printf("\nTeam name %s is not correct", name);
      } else {
         System.out.printf("\nTeam name %s is correct", name);
      }
      assertEquals(evaluator.apply(name), false);
   }
}