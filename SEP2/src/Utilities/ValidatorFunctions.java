package Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ValidatorFunctions
{
   /**Enumeration <code>Fields</code> which is a set of possible values for labels for text fields. Each enumeration represents a String
    * for a label name which is later used to present whether the text from a text field connected with this label is correct
    * depending on its respective regex. The enum values are used in the message dialogs.
    */
   public enum Fields {
      MESSAGE_INFORM ("Please enter valid information in the following fields:"),
      WINDOW_MESSAGE(" "),
      TEAM_1_NAME ("Team 1 name"),
      TEAM_2_NAME ( "Team 2 name"),
      TEAM_1_COEFFICIENT ("Team 1 coefficient"),
      TEAM_2_COEFFICIENT ("Team 2 coefficient"),
      COEFFICIENT_TIE ("Coefficient Tie"),
      TEAM_1_SCORE ("Team 1 Score"),
      TEAM_2_SCORE ("Team 2 Score"),
      MATCH_DATE ("Match Date"),
      FIRST_NAME ("First name"),
      LAST_NAME ("Last name"),
      USERNAME ("Username"),
      PASSWORD  ("Password"),
      EMAIL ("Email");

      private String name;

      /**Constructor for an enum.
       * @param name
       */
      Fields(String name) {
         this.name = name;
      }

      /**Returns the value of that enum.
       * @return
       */
      public String getName() {
         return this.name;
      }
   }

   /**
    * A List, storing the labels, which point to their respective text fields, that are being checked.
    */
   public ArrayList<String> fieldsToBeCorrected;
   public String messageInform = "Please enter valid information in the following fields:";

   public BiFunction<String, String, Boolean> inputValidator = (t, f) -> RegexChecker.inputValidator(t, f);
   public Function<Boolean, Boolean> trueOrFalse = f -> f == false;
   public BiFunction<String, String, Boolean> evaluator = (g, r) -> inputValidator.andThen(trueOrFalse).apply(g, r);
   public BiConsumer<List<String>, Fields> errorAdder = (s, g) -> s.add(g.name);

   /**
    * On creation of this object a new array list for labels is created. This is for validation convenience. If there is a group of 
    * another text fields, a new <code>ValidatorFunctions</code> can be created with its own list for storing labels.
    */
   public ValidatorFunctions() {
      this.fieldsToBeCorrected = new ArrayList<String>();
   }

   /**This method keeps track of the labels that point to textfields with incorrect data and stores those labels in the list
    * <code>fieldsToBeCorrected</code>. If the <code>textFieldText</code> does not match the <code>regex</code>, the value of
    * the enum <code>field</code> is added to the list <code>fieldsToBeCorrected</code>, which contains all the labels that
    * represent text fields with data that does not satisfy the regexes' requirements.
    * @param regex
    * @param textFieldText
    * @param field
    */
   public void placeStringsAndExec(String regex, String textFieldText, Fields field) {
      if (this.evaluator.apply(regex, textFieldText)) {
         if (!this.fieldsToBeCorrected.contains(Fields.MESSAGE_INFORM.name)) {
            this.errorAdder.accept(this.fieldsToBeCorrected, Fields.MESSAGE_INFORM);
         }
         this.errorAdder.accept(fieldsToBeCorrected, field);
      } else {
         if (this.fieldsToBeCorrected.contains(field.name)) {
            this.fieldsToBeCorrected.remove(field.name);
         }
      }
   }

   /**Instance of the <code>placeStringAndExec</code>, which only accepts the <code>textFieldText</code> and the enum
    * <code>field</code>. This version of the method is used for the <code>Date</code> object that does not require a regex.
    * @param textFieldText
    * @param field
    */
   public void placeStringsAndExec(String textFieldText, Fields field) {
      if (textFieldText.equals("")) {
         if (!this.fieldsToBeCorrected.contains(Fields.MESSAGE_INFORM.name)) {
            this.errorAdder.accept(this.fieldsToBeCorrected, Fields.MESSAGE_INFORM);
         }
         this.errorAdder.accept(this.fieldsToBeCorrected, field);
      } else {
         if (this.fieldsToBeCorrected.contains(field.name)) {
            this.fieldsToBeCorrected.remove(field.name);
         }  
      }
   }

   /**Returns an <code>Optional&ltString></code> object created from a stream, formatting the <code>fieldsToBeCorrected</code> list.
    * @return <code>Optional&ltString></code>
    * @see {@link java.util.Optional}
    */
   public Optional<String> returnFormattedListofFields() {
      Optional<String> list = this.fieldsToBeCorrected.stream().reduce((s1,s2) -> s1.concat(", ").concat(s2));
      return list;
   }
}