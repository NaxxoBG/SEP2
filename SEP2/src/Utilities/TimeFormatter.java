package Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;

public class TimeFormatter extends DefaultTableCellRenderer
{
   private static final long serialVersionUID = 1L;
   DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

   public TimeFormatter() {}

   public void setValue(Object value) {
      if (formatter == null) {
         formatter = DateFormat.getDateInstance();
      }
      setText((value == null) ? "" : formatter.format(value));
   }
}