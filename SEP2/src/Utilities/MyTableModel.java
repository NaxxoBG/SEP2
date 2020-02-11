package Utilities;

import javax.swing.table.AbstractTableModel;

/**<code>MyTableModel</code> is used when creating a <code>JTable</code>. It is a custom table model, created to satisfy the needs
 * for this program. It stores the data (Matches).
 */
public class MyTableModel extends AbstractTableModel
{
   private static final long serialVersionUID = 1L;
   private String[] columnNames;
   private Object[][] data;

   /**Constructor for the table model with parameters for the data as well as for column names of the model.
    * @param data
    * @param columnNames
    */
   public MyTableModel(Object[][] data, String[] columnNames) {
      this.columnNames = columnNames;
      this.data = data;
   }

   public int getRowCount() {
      return data.length;
   }

   public int getColumnCount() {
      return columnNames.length;
   }

   public String getColumnName(int col) {
      return columnNames[col];
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      return data[rowIndex][columnIndex];
   }

   public Class<?> getColumnClass(int c) {
      return getValueAt(0, c).getClass();
   }

   public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
   }

   /**This method returns an <code>Object[]</code> array with data from the row number <code>row</code>
    * @param row
    * @param model
    * @return <code>Object[]</code>
    */
   public Object[] getRowAt(int row, MyTableModel model) {
      Object[] obj = new Object[getColumnCount()];
      for (int i = 0; i < model.getColumnCount(); i++) {
         obj[i] = model.getValueAt(row, i);
      }
      return obj;
   }
}