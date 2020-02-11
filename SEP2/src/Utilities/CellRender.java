package Utilities;

import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class CellRender extends DefaultListCellRenderer {
   private static final long serialVersionUID = 1L;

   public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      if (value.toString().contains("Lose")) {
         setBackground(Util.hex2Rgb("#ff3333"));
      } else if(value.toString().contains("Win")) {
         setBackground(Util.hex2Rgb("#33cc33"));
      } else {
         setForeground(list.getForeground());
      }
      if (isSelected) { 
         setFont(getFont().deriveFont(Font.BOLD));
      }
      return this;
   }
}