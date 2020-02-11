package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import MatchTicket.Bet;
import MatchTicket.Ticket;
import User.User;
import Utilities.MyTableModel;
import Utilities.Util;
import java.awt.FlowLayout;
import java.awt.Font;

/**This class represents the virtual ticket of a user. The virtual ticket acts as a collection of selected matches with winner picks
 * already made by the user. It gives the user a brief overview of his current betting selection and enables him to modify or 
 * clear it. The virtual ticket also presents the user with an estimation of amount of points lost/won, based on the coefficients
 * of the picks for each individual event.
 */
public class VirtualTicket
{
   private JFrame frame;
   private JTable eventTable;
   private JTextField txtMoney;
   private JLabel totalCoeff;
   private JLabel possibleWin;
   private ActionListener listener = null;
   private ArrayList<Object[]> ticket;
   private Dimension screenSize;
   private Dimension windowSize;
   private int windowX;
   private int windowY;
   private JPanel tablePanel;
   private JPanel panel;
   private JPanel notificationPanel;
   private JLabel lblNot1;
   private JLabel lblNot2;
   private JPanel panel_3;
   private JScrollPane scroll;
   private JPanel panel_1;
   private JButton btnRemove;
   private Component rigidArea_2;
   private JButton btnClear;
   private JPanel infoPanel;
   private JPanel panel_2;
   private JLabel lblTotalCoeff;
   private Component rigidArea_1;
   private JLabel lblPlaceAmountOf;
   private JPanel buttonPanel;
   private JLabel lblNewLabel;
   private JButton btnPlaceBet;
   private static final String[] EVENTCOLUMNS = {"MatchID", "Team1", "Team2","1", "2", "X","Tip"};

   /**
    * Initialize the <code>Virtual ticket</code> frame.
    */
   public void initialize() {
      frame = new JFrame("Virtual ticket");
      frame.setBounds(100, 100, 500, 649);
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      windowSize = frame.getSize();
      windowX = (int) Math.max(0, (screenSize.width  - windowSize.width ) / 1.0005);
      windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
      frame.setLocation(windowX, windowY);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      tablePanel = new JPanel();
      frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
      tablePanel.setLayout(new BorderLayout(0, 0));

      panel = new JPanel();
      frame.getContentPane().add(panel, BorderLayout.CENTER);
      panel.setLayout(new BorderLayout(0, 0));
      notificationPanel = new JPanel();
      frame.getContentPane().add(notificationPanel, BorderLayout.NORTH);
      notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
      notificationPanel.setLayout(new GridLayout(0, 1, 0, 0));

      lblNot1 = new JLabel("                 Below are the events you selected from \"Events\".");
      notificationPanel.add(lblNot1);

      lblNot2 = new JLabel("Please, check your choices and amount of money before you place the actual bet.");
      notificationPanel.add(lblNot2);

      panel_3 = new JPanel();
      panel.add(panel_3, BorderLayout.CENTER);

      eventTable = new JTable(new DefaultTableModel());
      eventTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
      eventTable.getTableHeader().setReorderingAllowed(false);
      eventTable.setFont(new Font("Arial", Font.PLAIN, 15));
      eventTable.setRowHeight(23);
      eventTable.setAutoCreateRowSorter(true);

      panel_3.setLayout(new BorderLayout(0, 0));      

      scroll = new JScrollPane(eventTable);
      panel_3.add(scroll);

      panel_1 = new JPanel();
      panel_3.add(panel_1, BorderLayout.SOUTH);

      btnRemove = new JButton("Remove");
      panel_1.add(btnRemove);
      btnRemove.addActionListener(listener);
      btnRemove.setActionCommand("remove");

      rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
      panel_1.add(rigidArea_2);

      btnClear = new JButton("Clear");
      panel_1.add(btnClear);
      btnClear.addActionListener(listener);
      btnClear.setActionCommand("clear");

      infoPanel = new JPanel();
      panel.add(infoPanel, BorderLayout.SOUTH);
      infoPanel.setBorder(new TitledBorder(null, "Info", TitledBorder.LEFT, TitledBorder.TOP, null, SystemColor.textHighlight));
      infoPanel.setLayout(new BorderLayout(0, 0));

      panel_2 = new JPanel();
      infoPanel.add(panel_2, BorderLayout.CENTER);

      lblTotalCoeff = new JLabel("Total coeff. : ");
      panel_2.add(lblTotalCoeff);

      totalCoeff = new JLabel("1.0");
      panel_2.add(totalCoeff);

      rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
      panel_2.add(rigidArea_1);

      lblPlaceAmountOf = new JLabel("Place amount of money: ");
      panel_2.add(lblPlaceAmountOf);

      txtMoney = new JTextField();
      txtMoney.getDocument().addDocumentListener(new DocumentListener() {       

         public void insertUpdate(DocumentEvent e){
            setTotalOdd();
         }

         public void removeUpdate(DocumentEvent e){
            setTotalOdd();           
         }

         public void changedUpdate(DocumentEvent e){
            setTotalOdd();   
         }
      });
      txtMoney.setText("10");
      panel_2.add(txtMoney);
      txtMoney.setColumns(10);

      buttonPanel = new JPanel();
      frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
      buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

      lblNewLabel = new JLabel("Possible win : ");
      buttonPanel.add(lblNewLabel);

      possibleWin = new JLabel(String.valueOf(Double.parseDouble(totalCoeff.getText()) * Double.parseDouble(txtMoney.getText())));
      buttonPanel.add(possibleWin);

      btnPlaceBet = new JButton("Place bet");
      btnPlaceBet.addActionListener(listener);
      btnPlaceBet.setActionCommand("placeBet");
      buttonPanel.add(btnPlaceBet);

      ticket = new ArrayList<Object[]>();
   }

   /**Sets the the <code>ActionListener</code> to <code>listener</code>
    * @param listener
    */
   public void setListener(ActionListener listener) {
      this.listener = listener;
   }

   /**Add the parameter <code>Object[]</code> to the <code>ticket</code> arraylist.
    * @param obj
    */
   public void addToTicket(Object[] obj) {
      if (contains(obj));
      else {
         ticket.add(obj);
      }
   }

   /**Checks if the <code>ticket</code> array list contains the parameter <code>obj</code>
    * @param obj
    * @return <code>true</code> or <code>false</code>
    */
   private boolean contains(Object[] obj) {
      for (int i = 0; i < ticket.size(); i++) {
         if (ticket.get(i)[0] == obj[0]) {
            return true;
         }
      }
      return false;
   }

   /**Gets the selected match and removes it from the virtual ticket.
    */
   public void removeFromTicket() {
      int[] array = eventTable.getSelectedRows();
      for(int i = 0; i < array.length; i++) {
         ticket.remove(array[i] - i);
      }
   }

   /**
    * Resets the contet of the virtual ticket.
    */
   public void resetTicket() {
      ticket = new ArrayList<Object[]>();
   }

   /**This method creates a ticket for a <code>User user</code>
    * @param user
    * @return
    */
   public Ticket createTicket(User user) {
      Object[][] data = getBuildedTicket();
      Ticket myTicket = new Ticket(user.getUserName(), getMoney(user.getScore()));
      for (int i = 0; i < ticket.size(); i++){
         Bet bet = new Bet((int) data[i][0], (String)data[i][6], getCoefficient(data[i]));
         myTicket.add(bet);
      }
      resetTicket();
      return myTicket;
   }

   /**Return the coefficient, depending on the tip.
    * @param obj
    * @return <code>double</code>
    */
   private double getCoefficient(Object[] obj) {
      String tip = (String)(obj[6]);
      if (tip.equals("1")) {
         return (double) obj[3];
      } else if (tip.equals("2")) {
         return (double) obj[4];
      } else if (tip.equals("X")) {
         return (double)obj[5];
      } else {
         return 1;
      }
   }

   /**Convert <code>ticket</code> to a <code>Object[][]</code> and return it.
    * @return <code>Object[][]</code>
    */
   private Object[][] getBuildedTicket() {
      try {
         Object[][] obj = ticket.toArray(new Object[ticket.size()][ticket.get(0).length]);
         return obj;
      } catch (Exception e) {
         return null;
      }
   }

   /**
    * Sets the table of Virtual ticket with the matches from the <code>ticket</code> array list.
    */
   public void setTable() {
      Object[][] data = getBuildedTicket();
      if (data == null || data.length == 0) {
         eventTable.setModel(new DefaultTableModel());
         setTotalOdd();
         return;
      }
      try {
         MyTableModel model = new MyTableModel(data, EVENTCOLUMNS);
         eventTable.setModel(model);
         setColumnWidth();
         setTotalOdd();
         eventTable.repaint();
      } catch (Exception e) {
         System.out.println("Cannot set model to the table");
         e.printStackTrace();
      }
   }

   /**
    * Sets the values for the the total coefficient and possible win.
    */
   private void setTotalOdd() {
      try{
         totalCoeff.setText(String.valueOf(Util.round(calculateTotalOdd(), 2)));
         totalCoeff.repaint();
         possibleWin.setText(String.valueOf(Util.round(Double.parseDouble(totalCoeff.getText()) * Double.parseDouble(txtMoney.getText()), 2)));
         possibleWin.repaint();   
      }
      catch (Exception e){
      }
   }

   /**Calculates the total odd based on the bets.
    * @return <code>double</code>
    */
   private double calculateTotalOdd() {
      double totalOdd = 1;
      try {
         for (int i = 0; i < eventTable.getRowCount(); i++) {
            if (eventTable.getValueAt(i, 6).equals("1")) {
               totalOdd= totalOdd * (double) eventTable.getValueAt(i, 3);
            }
            else if (eventTable.getValueAt(i, 6).equals("2")) {
               totalOdd = totalOdd * (double) eventTable.getValueAt(i, 4);
            }
            else if (eventTable.getValueAt(i, 6).equals("X")){
               totalOdd= totalOdd * (double) eventTable.getValueAt(i, 5);
            }
         }
      } catch (Exception e) {}
      return totalOdd;
   }

   /**Return the amount of money the user is betting.
    * @param max
    * @return <code>money</code>
    */
   public double getMoney(double max) {
      try {
         double money = Double.parseDouble(txtMoney.getText());
         if (money <= 0 || money > max) {
            JOptionPane.showMessageDialog(null, "Place value more than 0 and less than your score : " + max, "Bet", JOptionPane.WARNING_MESSAGE);
            return -1;
         } else {
            return money;
         }
      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Write amount of money you want to bet", "Bet", JOptionPane.WARNING_MESSAGE);
         return -1;
      }    
   }

   /**Sets the visibility of the <code>frame</code> to <code>boolean b</code>.
    * @param b
    */
   public void setVisibility(boolean b) {
      frame.setVisible(b);
   }

   /**
    * Shows a message dialog, notifying that the ticket has been added successfully.
    */
   public void addTicketSuccesfully() {
      JOptionPane.showMessageDialog(null, "Ticket has been added succesfully", "Bet", JOptionPane.INFORMATION_MESSAGE);    
   }

   /**
    * Shows a message dialog, notifying that the ticket is empty and that matches should be added for the bet to be placed.
    */
   public void emptyTicket() {
      JOptionPane.showMessageDialog(null, "Empty ticket. Please add some matches", "Bet", JOptionPane.WARNING_MESSAGE);         
   }

   /**Modifies the columns of the table of the virtual ticket.<br>
    * Sets the column width of <b>columns 3-6</b> to <b>25</b> and of <b>column 0</b> to <b>40</b>.
    */
   private void setColumnWidth() {
      TableColumn column = null;
      for (int i = 3; i < 6; i++) {
         column = eventTable.getColumnModel().getColumn(i);
         column.setPreferredWidth(25);         
      }
      column = eventTable.getColumnModel().getColumn(0);
      column.setPreferredWidth(40);  
   }
}