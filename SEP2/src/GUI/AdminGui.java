package GUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import MatchTicket.Match;
import Utilities.MyTableModel;
import Utilities.TimeFormatter;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**The <b>adminGui</b> class represents the GUI intended for use by the <b>Administrators</b>
 */
public class AdminGui
{
   private JFrame frame;
   private JComboBox<String> comboBoxEventType;
   private JDatePickerImpl datePickerPanelFrom;  
   private JDatePickerImpl datePickerPanelTo;
   private JDatePickerImpl datePickerPanelEdit;
   private String[] eventColumns = {"MatchID", "Team1", "Team2", "1", "2", "X", "Date", "Time", "Type"};
   private JTextField textFieldTeam1;
   private JTextField textFieldTeam2;
   private JTextField textFieldCoeff1;
   private JTextField textFieldCoeff2;
   private JTextField textFieldCoeffTie;
   private JTable table;
   private JTextField textFieldScore1;
   private JTextField textFieldScore2;
   private JTextField textFieldID;
   private JComboBox<String> comboBoxAddEdit;
   private ActionListener listener;
   private JTabbedPane tabbedPane;
   private JPanel pnlEvents;
   private JPanel panelEventView;
   private JPanel pnlFilters;
   private JLabel lblType;
   private Component rigidArea;
   private JLabel lblFrom;
   private Component rigidAreaBetweenDates;
   private JLabel lblTo;
   private Component rigidArea1;
   private Button btnFilter;
   private JSplitPane splitPaneEditAndAdd;
   private JPanel panelEditAdd;
   private JLabel lblInfo1;
   private JLabel lblInfo2;
   private JLabel lblMatchId;
   private JLabel lblTeam;
   private JLabel lblTeam2;
   private JLabel lblCoeff1;
   private JLabel lblCoeff2;
   private JLabel lblCoeffTie;
   private JLabel lblScore;
   private JLabel lblScore2;
   private JLabel lblType1;
   private JLabel lblTime;
   private JSpinner timeSpinner;
   private JLabel labelDate;
   private JScrollPane scrollPaneEvents;
   private JPanel buttons;
   private JButton addMatch;
   private MouseAdapter adapter;
   private JButton editScores;
   private Component rigitArrButtons;
   private FocusListener focusListener;
   private FocusListener focusListener2;

   /**This method initializes the adminGui so the user can interact with it.
    * @param list
    */
   public void initialize(List<Match> list) {
      frame = new JFrame();
      frame.setBounds(100, 100, 952, 581);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLocationRelativeTo(null);
      frame.setTitle("Admin");

      tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

      pnlEvents = new JPanel();
      tabbedPane.addTab("Events", null, pnlEvents, null);
      pnlEvents.setLayout(new BorderLayout(0, 0));

      panelEventView = new JPanel();
      pnlEvents.add(panelEventView, BorderLayout.CENTER);
      panelEventView.setLayout(new BorderLayout(0, 0));

      pnlFilters = new JPanel();
      pnlFilters.setBackground(SystemColor.info);
      panelEventView.add(pnlFilters, BorderLayout.NORTH);
      pnlFilters.setBorder(new TitledBorder(null, "Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));

      lblType = new JLabel("Type : ");
      pnlFilters.add(lblType);

      comboBoxEventType = new JComboBox<String>();
      comboBoxEventType.setModel(new DefaultComboBoxModel<String>(new String[] {"Today", "Football", "Basketball", "Hockey", "Volleyball"}));
      pnlFilters.add(comboBoxEventType);

      rigidArea = Box.createRigidArea(new Dimension(40, 20));
      pnlFilters.add(rigidArea);

      lblFrom = new JLabel("From : ");
      pnlFilters.add(lblFrom);
      datePickerPanelFrom = newDatePicker();
      datePickerPanelFrom.getJFormattedTextField().setBackground(Color.WHITE);
      pnlFilters.add(datePickerPanelFrom);

      rigidAreaBetweenDates = Box.createRigidArea(new Dimension(20, 20));
      pnlFilters.add(rigidAreaBetweenDates);

      lblTo = new JLabel("To : ");
      pnlFilters.add(lblTo);
      datePickerPanelTo = newDatePicker();
      datePickerPanelTo.getJFormattedTextField().setBackground(Color.WHITE);
      pnlFilters.add(datePickerPanelTo);

      rigidArea1 = Box.createRigidArea(new Dimension(50, 20));
      pnlFilters.add(rigidArea1);

      btnFilter = new Button("Filter");
      btnFilter.setForeground(new Color(153, 0, 0));
      btnFilter.setBackground(new Color(255, 255, 204));
      btnFilter.addActionListener(listener);
      btnFilter.setActionCommand("filter");
      pnlFilters.add(btnFilter);

      splitPaneEditAndAdd = new JSplitPane();
      splitPaneEditAndAdd.setOrientation(JSplitPane.VERTICAL_SPLIT);
      panelEventView.add(splitPaneEditAndAdd, BorderLayout.CENTER);

      panelEditAdd = new JPanel();
      panelEditAdd.setBackground(SystemColor.controlHighlight);
      panelEditAdd.setForeground(SystemColor.textHighlight);
      panelEditAdd.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(64, 64, 64), new Color(51, 153, 255), new Color(0, 0, 0)), "Add/Edit an event", TitledBorder.CENTER, TitledBorder.TOP, null, null));
      splitPaneEditAndAdd.setLeftComponent(panelEditAdd);
      panelEditAdd.setLayout(new FormLayout(new ColumnSpec[] {
            FormFactory.UNRELATED_GAP_COLSPEC,
            ColumnSpec.decode("55px"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("70px:grow"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("49px"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("70px:grow"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("49px"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("70px:grow"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("49px"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("70px:grow"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("57px"),
            FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("84px:grow"),},
            new RowSpec[] {
            RowSpec.decode("19px:grow"),
            FormFactory.DEFAULT_ROWSPEC,
            FormFactory.DEFAULT_ROWSPEC,
            FormFactory.PARAGRAPH_GAP_ROWSPEC,
            RowSpec.decode("22px"),
            FormFactory.PARAGRAPH_GAP_ROWSPEC,
            RowSpec.decode("default:grow"),}));

      lblInfo1 = new JLabel("Here you can add a new event or edit an existing one. In order to edit an event, please select one from the table below");
      lblInfo1.setBackground(SystemColor.info);
      panelEditAdd.add(lblInfo1, "3, 1, 18, 1, center, center");

      lblInfo2 = new JLabel("and edit the values in their respective fields");
      lblInfo2.setBackground(SystemColor.info);
      panelEditAdd.add(lblInfo2, "8, 2, 9, 1, center, center");

      lblMatchId = new JLabel("Match ID:");
      panelEditAdd.add(lblMatchId, "2, 3, center, center");

      textFieldID = new JTextField();
      panelEditAdd.add(textFieldID, "4, 3, center, default");
      textFieldID.setEditable(false);
      textFieldID.setColumns(10);

      lblTeam = new JLabel("Team 1:");
      panelEditAdd.add(lblTeam, "2, 5, center, center");

      textFieldTeam1 = new JTextField();
      textFieldTeam1.addFocusListener(focusListener);
      panelEditAdd.add(textFieldTeam1, "4, 5, center, top");
      textFieldTeam1.setColumns(10);

      lblTeam2 = new JLabel("Team 2:");
      panelEditAdd.add(lblTeam2, "6, 5, center, center");

      textFieldTeam2 = new JTextField();
      textFieldTeam2.addFocusListener(focusListener);
      panelEditAdd.add(textFieldTeam2, "8, 5, center, top");
      textFieldTeam2.setColumns(10);

      lblCoeff1 = new JLabel("Coeff 1:");
      panelEditAdd.add(lblCoeff1, "10, 5, center, center");

      textFieldCoeff1 = new JTextField();
      textFieldCoeff1.addFocusListener(focusListener);
      panelEditAdd.add(textFieldCoeff1, "12, 5, center, top");
      textFieldCoeff1.setColumns(10);

      lblCoeff2 = new JLabel("Coeff: 2");
      panelEditAdd.add(lblCoeff2, "14, 5, center, center");

      textFieldCoeff2 = new JTextField();
      textFieldCoeff2.addFocusListener(focusListener);
      panelEditAdd.add(textFieldCoeff2, "16, 5, center, top");
      textFieldCoeff2.setColumns(10);

      lblCoeffTie = new JLabel("Coeff Tie:");
      panelEditAdd.add(lblCoeffTie, "18, 5, center, center");

      textFieldCoeffTie = new JTextField();
      textFieldCoeffTie.addFocusListener(focusListener);
      panelEditAdd.add(textFieldCoeffTie, "20, 5, center, default");
      textFieldCoeffTie.setColumns(10);

      lblScore = new JLabel("Score 1:");
      panelEditAdd.add(lblScore, "2, 7, center, default");

      textFieldScore1 = new JTextField("0");
      textFieldScore1.addFocusListener(focusListener2);
      textFieldScore1.setEnabled(false);
      panelEditAdd.add(textFieldScore1, "4, 7, center, default");
      textFieldScore1.setColumns(10);

      lblScore2 = new JLabel("Score 2:");
      panelEditAdd.add(lblScore2, "6, 7, center, default");

      textFieldScore2 = new JTextField("0");
      textFieldScore2.addFocusListener(focusListener2);
      textFieldScore2.setEnabled(false);
      panelEditAdd.add(textFieldScore2, "8, 7, center, default");
      textFieldScore2.setColumns(10);

      lblType1 = new JLabel("Type:");
      panelEditAdd.add(lblType1, "10, 7, center, default");

      comboBoxAddEdit = new JComboBox<String>();
      comboBoxAddEdit.setModel(new DefaultComboBoxModel<String>(new String[] {"Football", "Basketball", "Hockey", "Volleyball"}));
      panelEditAdd.add(comboBoxAddEdit, "12, 7, center, default");

      datePickerPanelEdit = newDatePicker();
      datePickerPanelEdit.getJFormattedTextField().setBackground(Color.WHITE);

      lblTime = new JLabel("Time:");
      panelEditAdd.add(lblTime, "14, 7, center, default");

      timeSpinner = new JSpinner();
      SpinnerDateModel sm = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
      timeSpinner.setModel(sm);
      JSpinner.DateEditor de = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");    
      de.getTextField().setEditable(false);
      de.getTextField().setColumns(10);
      timeSpinner.setValue(new Date());
      timeSpinner.setEditor(de);
      panelEditAdd.add(timeSpinner, "16, 7, center, fill");

      labelDate = new JLabel("Date:");
      panelEditAdd.add(labelDate, "18, 7, center, default");

      datePickerPanelEdit = newDatePicker();
      datePickerPanelEdit.getJFormattedTextField().setBackground(Color.WHITE);
      panelEditAdd.add(datePickerPanelEdit, "20, 7, center, default");

      scrollPaneEvents = new JScrollPane();
      splitPaneEditAndAdd.setRightComponent(scrollPaneEvents);

      table = new JTable();
      scrollPaneEvents.setViewportView(table);
      table.setFont(new Font("Arial", Font.PLAIN, 15));
      table.setRowHeight(23);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.getTableHeader().setReorderingAllowed(false);
      table.setAutoCreateRowSorter(true);
      table.addMouseListener(adapter);

      buttons = new JPanel();
      pnlEvents.add(buttons, BorderLayout.SOUTH);

      addMatch = new JButton("Add match");
      addMatch.setForeground(new Color(0, 0, 0));
      addMatch.setBackground(new Color(204, 255, 153));
      addMatch.addActionListener(listener);
      addMatch.setActionCommand("add");
      buttons.add(addMatch);

      rigitArrButtons = Box.createRigidArea(new Dimension(20, 20));

      editScores = new JButton("Edit scores");
      editScores.setForeground(new Color(0, 0, 0));
      editScores.setBackground(new Color(204, 255, 153));
      editScores.addActionListener(listener);
      editScores.setToolTipText("<html><span style=\"font-size:15px;\">Edit the scores, the time or the date of the selected event</span></html>");
      editScores.setActionCommand("edit");

      buttons.add(rigitArrButtons);
      buttons.add(editScores);

      setTable(list);
      frame.setVisible(true);
   }

   /**This method returns a new <code>JDatePickerImpl</code> object used as a tool for choosing dates in the UI.
    * @return <code>JDatePickerImpl</code>
    */
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

   /**<code>DateLabelFormatter</code> used for formatting dates for <code>JDatePicker</code>.*/
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
            Calendar cal = (Calendar)value;
          
            return dateFormatter.format(cal.getTime());
         }
         return "";
      }
  }


   /**This method sets the table to the adminGui with the content from the parameter <code>list</code>.
    * @param list
    */
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

   /**<h1>Create a match array</h1>
    * This method creates a <strong>2D Object</strong> array from the passed <code>List</code> of <code>Match</code> objects <br>
    * Each match takes the following attributes <ul> <li>Match ID</li> <li>Team 1 name</li> <li>Team 2 name</li>
    * <li>Coefficient 1</li> <li>Coefficient 2</li> <li>Coefficient Tie</li> <li>Starting Date</li> <li>Ending Date</li>
    * <li>Type of Match</li></ul>
    * @param list
    * @return <code>Object[][] array</code>
    */
   private Object[][] createMatchArray(List<Match> list) {
      Object[][] obj = new Object[list.size()][9];
      for(int i = 0; i < list.size(); i++) {
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

   /**<h1>ActionListener Setter</h1><br>
    * This method sets the parameter <code>listener</code> to the listener field of <code>adminGui</code>
    * @param adapter
    */
   public void setListener(ActionListener listener) {
      this.listener = listener;
   }

   /**<h1>MouseAdapter Setter</h1><br>
    * This method sets the parameter <code>adapter</code> to the adapter field of <code>adminGui</code>
    * @param adapter
    */
   public void setAdapter(MouseAdapter adapter) {
      this.adapter = adapter;
   }

   /**Set the <code>FocusListener</code> for the <code>JTextfield</code>
    * @param listener
    */
   public void setFocusListener(FocusListener listener) {
      this.focusListener = listener;
   }

   /**Sets the focus listener for score1, score2, JSpinner and Date.
    * @param listener
    */
   public void setFocusListener2(FocusListener listener) {
      this.focusListener2 = listener;
   }

   /**This method takes the information from the <code>JTextFields</code> in the Add/Edit panel, creates a new
    * object <code>Match</code> and returns it.
    * @return <code>Match</code>
    */
   public Match createMatch() {
      String team1 = textFieldTeam1.getText();
      String team2 = textFieldTeam2.getText();
      double coefficient1 = Double.parseDouble(textFieldCoeff1.getText());
      double coefficient2 = Double.parseDouble(textFieldCoeff2.getText());
      double coefficientTie = Double.parseDouble(textFieldCoeffTie.getText());
      Date date = getDateEdit();
      String type = getTypeAddEdit();
      Match match = new Match(team1, team2, coefficient1, coefficient2, coefficientTie, date, type);
      return match;
   }

   /**Returns the <code>JDatePicker</code> for the "From" date from the Filter panel.
    * @return <code>JDatePicker</code>
    */
   public JDatePickerImpl getDatePickerPanelFrom() {
      return datePickerPanelFrom;
   }

   /**Returns the <code>JDatePicker</code> for the "To" date from the Filter panel.
    * @return <code>JDatePicker</code>
    */
   public JDatePickerImpl getDatePickerPanelTo() {
      return datePickerPanelTo;
   }

   /**Returns the selected item from the <code>JComboBox</code> in the Filter panel of the UI.
    * @return <code>String</code>
    */
   public String getType() {
      return (String) comboBoxEventType.getSelectedItem(); 
   }

   /**Returns the selected item from the <code>JComboBox</code> in the Add/Edit panel of the UI.
    * @return <code>String</code>
    */
   public String getTypeAddEdit() {
      return (String) comboBoxAddEdit.getSelectedItem();
   }

   /**Checks whether the <code>JTextFields</code> of the <code>JDatePickers</code> are empty and dates have been chosen.
    * @return
    */
   public boolean emptyDates() {
      if (datePickerPanelFrom.getJFormattedTextField().getText().equals("") && datePickerPanelTo.getJFormattedTextField().getText().equals("")) {
         return true; 
      } else { 
         return false;
      }
   }

   public Date getDateFrom() {
      return getDate(datePickerPanelFrom.getJFormattedTextField().getText());
   }

   public Date getDate(String text) {
      try {
         String[] array = text.split("-");
         JSpinner.DateEditor ed = (DateEditor) timeSpinner.getEditor();
         String[] time = ed.getFormat().format(timeSpinner.getValue()).split(":");
         Calendar cal = Calendar.getInstance();
         cal.set(Integer.parseInt(array[2]), Integer.parseInt(array[1]) - 1, Integer.parseInt(array[0]),Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));
         return cal.getTime();
      } catch (Exception e) {
         return null;
      }   
   }

   public Date getDateTo() {
      return getDate(datePickerPanelTo.getJFormattedTextField().getText()); 
   }

   public Date getDateEdit() {
      return getDate(datePickerPanelEdit.getJFormattedTextField().getText()); 
   }

   /**Returns a <code>Date</code> object that is today's date;
    * @return <code>Date</code>
    */
   public Date getTodayDate() {
      Calendar cal = Calendar.getInstance();
      return new Date(cal.getTimeInMillis());
   }

   public Object[] getRow() {
      int selected = table.getSelectedRow();
      int column = table.getSelectedColumn();
      Object[] obj = new Object[8];
      for(int j = 0; j < obj.length; j++){
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

   public JComboBox<String> getComboBoxEventType() {
      return comboBoxEventType;
   }

   public JFormattedTextField getDatePickerPanelEditField() {
      return this.datePickerPanelEdit.getJFormattedTextField();
   }

   public JTextField getTextFieldTeam1() {
      return this.textFieldTeam1;
   }

   public JTextField getTextFieldTeam2() {
      return this.textFieldTeam2;
   }

   public JTextField getTextFieldCoeff1() {
      return this.textFieldCoeff1;
   }

   public JTextField getTextFieldCoeff2() {
      return this.textFieldCoeff2;
   }

   public JTextField getTextFieldCoeffTie() {
      return this.textFieldCoeffTie;
   }

   public JTextField getTextFieldScore1() {
      return this.textFieldScore1;
   }

   public JTextField getTextFieldScore2() {
      return this.textFieldScore2;
   }

   public JTextField getTextFieldID() {
      return this.textFieldID;
   }

   public JSpinner getTimeSpinner() {
      return this.timeSpinner;
   }

   public JComboBox<String> getComboBoxAddEdit() {
      return comboBoxAddEdit;
   }

   public JTable getTable() {
      return this.table;
   }

   public JButton getAddButton() {
      return addMatch;
   }

   public JButton getEditButton() {
      return editScores;
   }

   public void resetFields(){
      textFieldTeam1.setText("");
      textFieldTeam2.setText("");
      textFieldCoeff1.setText("");
      textFieldCoeff2.setText("");
      textFieldCoeffTie.setText("");
      textFieldScore1.setText("");
      textFieldScore2.setText("");
      textFieldID.setText("");
      datePickerPanelEdit.getJFormattedTextField().setText("");
   }
   public Date getMatchStart(){
      return getDate(datePickerPanelEdit.getJFormattedTextField().getText());
   }
}