package GUI;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.JFormattedTextField.AbstractFormatter;

import MatchTicket.Match;
import MatchTicket.Ticket;
import User.Data;
import User.User;
import Utilities.CellRender;
import Utilities.MyTableModel;
import Utilities.RegexChecker;
import Utilities.TimeFormatter;
import Utilities.Util;
import Utilities.ValidatorFunctions;

import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;

import java.awt.Dimension;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Client.ControllerMain.RefreshAction;

import java.awt.Button;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.JSplitPane;

public class MainGui
{
   private JFrame frame;
   private JPanel profile;
   private JList<String> tickets;
   private JTable table;
   private JTable historytable;
   private JScrollPane scroll;
   private JScrollPane historyPane;
   private JComboBox<String> comboBox;
   private JLabel lblScore;
   private JDatePickerImpl datePickerPanel;  
   private JDatePickerImpl datePickerPanel2;
   private ActionListener listener = null;
   private MouseAdapter adapter = null;
   private Client.ControllerMain.SharedListSelectionHandler listListener = null;
   private RefreshAction refreshAction = null;
   private String[] eventColumns = {"MatchID", "Team1", "Team2", "1", "2", "X", "Date", "Time", "Type"};
   private String[] ticketColumns = {"MatchID", "Team1", "Team2", "Score1", "Score2", "Tip", "Coef.", "Status", "Date", "Time"};
   private JTabbedPane tabbedPane;
   private JPanel pnlEvents;
   private ImageIcon icBall;
   private Icon iconBall;
   private JPanel pnlInfo;
   private JLabel lblUserNameT;
   private Component rigidArea_2;
   private JLabel lblScoreT;
   private JPanel panel;
   private JPanel pnlFilters;
   private JLabel lblType;
   private Component rigidArea;
   private JLabel lblFrom;
   private JLabel lblNewLabel_2;
   private Component rigidArea_1;
   private Button btnFilter;
   private JPanel buttons;
   private JButton showTicket;

   @SuppressWarnings("static-access")
   public void initialize(User user, List<Match> list, ArrayList<Ticket> tckts){
      frame = new JFrame("Betting system");
      frame.setBounds(100, 100, 931, 590);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLocationRelativeTo(null);

      tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

      tabbedPane.getInputMap(tabbedPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "refresh");
      tabbedPane.getActionMap().put("refresh", refreshAction);

      pnlEvents = new JPanel();
      icBall = new ImageIcon("./Icons/ball.png");
      iconBall = Util.resizeIconAndReturn(icBall, 20, 20);
      tabbedPane.addTab("Events", iconBall, pnlEvents, null);
      pnlEvents.setLayout(new BorderLayout(0, 0));

      pnlInfo = new JPanel();
      pnlEvents.add(pnlInfo, BorderLayout.NORTH);

      lblUserNameT = new JLabel("User name :");
      pnlInfo.add(lblUserNameT);   
      JLabel lblUserName = new JLabel(user.getUserName());
      pnlInfo.add(lblUserName);

      rigidArea_2 = Box.createRigidArea(new Dimension(600, 20));
      pnlInfo.add(rigidArea_2);

      lblScoreT = new JLabel("Score : ");
      pnlInfo.add(lblScoreT);
      lblScore = new JLabel(String.valueOf(user.getScore()));
      pnlInfo.add(lblScore);

      panel = new JPanel();
      pnlEvents.add(panel, BorderLayout.CENTER);
      panel.setLayout(new BorderLayout(0, 0));

      table = new JTable();
      if (list.size() != 0) {
         table.setModel(new MyTableModel(createMatchArray(list), eventColumns));
         table.getColumnModel().getColumn(7).setCellRenderer(new TimeFormatter());
      }
      table.setFont(new Font("Arial", Font.PLAIN, 15));
      table.setRowHeight(23);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.getTableHeader().setReorderingAllowed(false);
      table.setAutoCreateRowSorter(true);
      table.addMouseListener(adapter);

      scroll = new JScrollPane(table);
      panel.add(scroll, BorderLayout.CENTER);

      pnlFilters = new JPanel();
      panel.add(pnlFilters, BorderLayout.NORTH);
      pnlFilters.setBorder(new TitledBorder(null, "Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));

      lblType = new JLabel("Type : ");
      pnlFilters.add(lblType);

      comboBox = new JComboBox<String>();
      comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Today","Football", "Basketball", "Hockey", "Volleyball"}));
      pnlFilters.add(comboBox);

      rigidArea = Box.createRigidArea(new Dimension(60, 20));
      pnlFilters.add(rigidArea);

      lblFrom = new JLabel("From : ");
      pnlFilters.add(lblFrom);
      datePickerPanel = newDatePicker();
      datePickerPanel.getJFormattedTextField().setBackground(Color.WHITE);
      pnlFilters.add(datePickerPanel);

      lblNewLabel_2 = new JLabel("To : ");
      pnlFilters.add(lblNewLabel_2);
      datePickerPanel2 = newDatePicker();
      datePickerPanel2.getJFormattedTextField().setBackground(Color.WHITE);
      pnlFilters.add(datePickerPanel2);

      rigidArea_1 = Box.createRigidArea(new Dimension(50, 20));
      pnlFilters.add(rigidArea_1);

      btnFilter = new Button("Filter");
      btnFilter.addActionListener(listener);
      btnFilter.setActionCommand("filter");
      pnlFilters.add(btnFilter);

      buttons = new JPanel();
      pnlEvents.add(buttons, BorderLayout.SOUTH);

      showTicket = new JButton("Show ticket");
      showTicket.addActionListener(listener);
      showTicket.setActionCommand("show");
      buttons.add(showTicket);

      //********************************************************************************************      
      JPanel pnlHistory = new JPanel();
      ImageIcon icHistory = new ImageIcon("./Icons/History.png");
      Icon iconHistory = Util.resizeIconAndReturn(icHistory, 20, 20);
      tabbedPane.addTab("History", iconHistory, pnlHistory, null);
      pnlHistory.setLayout(new BorderLayout(0, 0));

      JLabel lblNewLabel = new JLabel("Here you can see all your tickets, divided in two categories: pending and finished.\r\n");
      pnlHistory.add(lblNewLabel, BorderLayout.NORTH);

      tickets = new JList<>();
      tickets.setCellRenderer(new CellRender());
      tickets.setFont(new Font("Arial", Font.PLAIN, 15));      
      tickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
      setListModel(tckts);
      tickets.getSelectionModel().addListSelectionListener(listListener);

      historytable = new JTable() {
         private static final long serialVersionUID = 1L;

         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            String status = (String) getValueAt(row, 7);
            if (status.equalsIgnoreCase("Win")) {
               c.setBackground(Color.GREEN);
            } else if (status.equalsIgnoreCase("Lose")) {
               c.setBackground(Color.RED);
            } else { 
               c.setBackground(Color.WHITE);
            }
            return c;
         }
      };
      historytable.setFont(new Font("Arial", Font.PLAIN, 15));
      historytable.setRowHeight(23);
      historytable.getTableHeader().setReorderingAllowed(false);
      historytable.setAutoCreateRowSorter(true);

      JScrollPane listScrollPane = new JScrollPane(tickets);    
      historyPane = new JScrollPane(historytable);

      //Create a split pane with the two scroll panes in it.
      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, historyPane);
      splitPane.setOneTouchExpandable(true);
      splitPane.setDividerLocation(220);

      pnlHistory.add(splitPane);
      //********************************************************************************************     
      JPanel pnlProfile = new JPanel();
      ImageIcon icProfile = new ImageIcon("./Icons/Users-Name-icon.png");
      Icon iconProfile = Util.resizeIconAndReturn(icProfile, 20, 20);
      tabbedPane.addTab("Profile", iconProfile, pnlProfile, null);
      pnlProfile.setLayout(new BorderLayout(0, 0));

      profile = new JPanel();
      profile.add(Register.getComponents());
      pnlProfile.add(profile, BorderLayout.CENTER);
      profile.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

      JPanel buttonPanel = new JPanel();
      pnlProfile.add(buttonPanel, BorderLayout.SOUTH);

      JButton btnCancel = new JButton("Cancel");
      btnCancel.addActionListener(listener);
      btnCancel.setActionCommand("cancel");
      buttonPanel.add(btnCancel);

      JButton btnSave = new JButton("Save");
      btnSave.addActionListener(listener);
      btnSave.setActionCommand("save");
      buttonPanel.add(btnSave);

      setProfile(user);
      frame.setVisible(true);
   }

   public void setListener(ActionListener listener) {
      this.listener = listener;
   }

   public void setAdapter(MouseAdapter adapter) {
      this.adapter = adapter;
   }

   public void setTable(List<Match> list) {
      if (list.size() == 0) {
         table.setModel(new DefaultTableModel());
         return;
      }
      try {
         MyTableModel model = new MyTableModel(createMatchArray(list), eventColumns);
         table.setModel(model);
         table.repaint();
         table.getColumnModel().getColumn(7).setCellRenderer(new TimeFormatter());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void setHistoryTable(Object[][] data) {
      if (data == null || data.length == 0) {
         historytable.setModel(new DefaultTableModel());
      } else {
         MyTableModel model = new MyTableModel(data, ticketColumns);
         historytable.setModel(model);
         historytable.repaint();
         historytable.getColumnModel().getColumn(9).setCellRenderer(new TimeFormatter());
      }
   }

   public String getType() {
      return (String) comboBox.getSelectedItem();
   }

   public Date getDate1() {
      return getDate(datePickerPanel.getJFormattedTextField().getText());
   }

   public Date getDate2() {
      return getDate(datePickerPanel2.getJFormattedTextField().getText());
   }

   public boolean emptyDates() {
      if (datePickerPanel.getJFormattedTextField().getText().equals("") || datePickerPanel2.getJFormattedTextField().getText().equals("")) {
         return true;
      } else {
         return false;
      }
   }

   private Date getDate(String text) {
      try {
         String[] array = text.split("-");
         Calendar cal = Calendar.getInstance();
         cal.set(Integer.parseInt(array[2]), Integer.parseInt(array[1]) - 1, Integer.parseInt(array[0]));
         return cal.getTime();
      }
      catch (Exception e){
         e.printStackTrace();
         return null;
      }   
   }

   private Object[][] createMatchArray(List<Match> list) {
      Object[][] obj = new Object[list.size()][9];
      for (int i = 0; i < list.size(); i++){
         obj[i][0]=list.get(i).getID();
         obj[i][1]=list.get(i).getTeam1();
         obj[i][2]=list.get(i).getTeam2();
         obj[i][3]=list.get(i).getCoefficient1();
         obj[i][4]=list.get(i).getCoefficient2();
         obj[i][5]=list.get(i).getCoefficientTie();
         obj[i][6]=list.get(i).getDate();
         obj[i][7]=list.get(i).getDate();
         obj[i][8]=list.get(i).getType();
      }
      return obj;     
   }

   public void setScore(double score) {
      lblScore.setText(String.valueOf(score));
      lblScore.repaint();
   }

   public void setListModel(ArrayList<Ticket> list) {
      String[] array = ticketHistory(list);
      DefaultListModel<String> model = new DefaultListModel<>();
      for(int i = 0; i < array.length; i++){
         model.addElement(array[i]);
      }
      try {
         tickets.setModel(model);
         tickets.setSelectedIndex(0);
      } catch (Exception e) {
         tickets.setSelectedIndex(0);
         System.out.println(e.getMessage());
      }
   }

   public void setProfile(User user) {
      ArrayList<JTextField> list = getProfileFields();
      try {
         list.get(0).setText(user.getData().getName().split(" ")[0]);
         list.get(1).setText(user.getData().getName().split(" ")[1]);
      } catch (Exception e) {
         list.get(0).setText(user.getData().getName());
      }
      list.get(2).setText(user.getUserName());
      list.get(3).setText(user.getPassword());
      list.get(4).setText(user.getPassword());
      list.get(5).setText(user.getData().getEmail());
      list.get(2).setEditable(false);
      JRadioButton[] btns = getRadioButton();
      if (user.getData().getSex().equals("Male")) {
         btns[0].setSelected(true);
      } else {
         btns[1].setSelected(true);
      }   
      SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
      getDOBField().setText(dt.format(user.getData().getBirth()));
   }
   public User setUser() {
      ArrayList<JTextField> list = getProfileFields();  
      ValidatorFunctions validator = new ValidatorFunctions();
      String windowMessage = ValidatorFunctions.Fields.WINDOW_MESSAGE.getName();
      validator.placeStringsAndExec(RegexChecker.FIRST_NAME, list.get(0).getText(), ValidatorFunctions.Fields.FIRST_NAME);
      validator.placeStringsAndExec(RegexChecker.LAST_NAME, list.get(1).getText(), ValidatorFunctions.Fields.LAST_NAME);
      validator.placeStringsAndExec(RegexChecker.USERNAME, list.get(2).getText(), ValidatorFunctions.Fields.USERNAME);
      validator.placeStringsAndExec(RegexChecker.PASSWORD, list.get(3).getText(), ValidatorFunctions.Fields.PASSWORD);
      validator.placeStringsAndExec(RegexChecker.EMAIL, list.get(5).getText(), ValidatorFunctions.Fields.EMAIL);
      if (this.getDOBField().getText() == null) {
         if (validator.fieldsToBeCorrected.contains("Date")) {
         } else {
            validator.fieldsToBeCorrected.add("Date"); 
         }
      }
      Optional<String> listFields = validator.returnFormattedListofFields();
      if (!validator.fieldsToBeCorrected.isEmpty()) {
         JOptionPane.showMessageDialog(null, windowMessage.concat(listFields.orElse("No errors")), "Input Errors", JOptionPane.INFORMATION_MESSAGE);
      } else if(getDob()!=null){
         Data data = new Data(list.get(0).getText() + " " + list.get(1).getText(), list.get(5).getText(), getDob(), getSex());
         User user = new User(list.get(2).getText(), list.get(3).getText(), data);
         return user;
      }
      return null;
   }

   private String getSex() {
      JRadioButton[] btns = getRadioButton();
      if(btns[0].isSelected()) {
         return "Male";
      } else {
         return "Female";
      }
   }

   private String getDob() {
      try{
         JFormattedTextField field = getDOBField();
         String[] array = field.getText().split("-");
         return array[2] + "-" + array[1] + "-" + array[0];  
      }
      catch (Exception e){
         JOptionPane.showMessageDialog(null, "Please fill date of birth field", "Profile", JOptionPane.WARNING_MESSAGE);  
         return null;
      }

   }

   private JDatePickerImpl newDatePicker() {
      UtilDateModel dateModel = new UtilDateModel();
      LocalDate now = LocalDate.now();
      dateModel.setDate(now.getYear(), now.getMonth().getValue() - 1, now.getDayOfMonth()); 
      Properties properties = new Properties();
      properties.put("text.today", "Today");
      properties.put("text.month", "Month");
      properties.put("text.year", "Year");
      JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
      DateLabelFormatter dateFormatter = new DateLabelFormatter();
      return new JDatePickerImpl(datePanel, dateFormatter);
   }

   public Object[] getRow() {
      int selected = table.getSelectedRow();
      int column = table.getSelectedColumn();
      Object[] obj = new Object[7];
      for(int j = 0; j < obj.length - 1; j++){
         obj[j] = table.getValueAt(selected, j);
      }
      obj[6] = getTip(column);
      return obj;
   }


   private String getTip(int column) {
      switch (column) {
         case 1: 
         case 3: 
            return "1";
         case 2: 
         case 4: 
            return "2";
         case 5: 
            return "X";
         default:
            return "";
      }
   }

   public String[] ticketHistory(ArrayList<Ticket> list) {
      if (list == null || list.size()==0) {
         String[] str = new String[1];
         str[0] = "Empty ticket history";
         return str;
      }
      String[] obj = new String[list.size()];    
      for (int i = 0; i < obj.length; i++){
         String text = list.get(i).toString();   
         obj[i] = text;
      }
      return obj;
   }

   public void setHistoryMatchListener(Client.ControllerMain.SharedListSelectionHandler sharedListSelectionHandler) {
      listListener = sharedListSelectionHandler;
   }

   public void profileSaved() {
      JOptionPane.showMessageDialog(null, "Profile information has been changed succesfully", "Profile", JOptionPane.INFORMATION_MESSAGE);  
   }

   private class DateLabelFormatter extends AbstractFormatter
   {
      private static final long serialVersionUID = 1L;
      private String datePattern = "dd-MM-yyyy";
      private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

      public Object stringToValue(String text) throws ParseException {
         return dateFormatter.parseObject(text);
      }

      public String valueToString(Object value) throws ParseException {
         if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
         }
         return "";
      }
   }

   //Methods to get fields from register JPanel *********************************
   private  ArrayList<JTextField> getProfileFields() {
      Component[] c = getComponents();
      ArrayList<JTextField> list = new ArrayList<JTextField>();
      Component component = null;
      for (int i = 0; i < c.length; i++) {
         component = c[i];
         if (component instanceof JTextField) {
            list.add((JTextField)component);    
         }
      }
      return list;
   }

   private JFormattedTextField getDOBField() {
      Component[] c = getComponents();
      Component comp = c[c.length - 1];
      JDatePickerImpl p = (JDatePickerImpl) comp;
      return  p.getJFormattedTextField();     
   }

   private JRadioButton[] getRadioButton() {     
      Component[] comp = getComponents();
      Component c1 = comp[5];
      Component[] c = ((Container) c1).getComponents();
      int j = 0;
      JRadioButton[] buutons = new JRadioButton[2];
      Component cmt;
      for (int i = 0; i < c.length; i++) {
         cmt = c[i];
         if (cmt instanceof JRadioButton) {
            buutons[j++] = (JRadioButton) cmt;
         }
      }     
      return buutons;
   }

   private Component[] getComponents() {
      Component[] components = profile.getComponents();
      JPanel pnl = (JPanel) components[0];
      Component[] comp = pnl.getComponents(); 
      Panel panel = (Panel) comp[0];
      Component[] c = panel.getComponents();
      return c;
   }

   public void setRefreshAction(RefreshAction refreshAction) {
      this.refreshAction=refreshAction;
   }

   public int getColumn() {
      return table.getSelectedColumn();
   }
}