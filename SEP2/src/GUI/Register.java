package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import User.Data;
import User.User;
import Utilities.RegexChecker;
import Utilities.Util;
import Utilities.ValidatorFunctions;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class Register
{
   private JFrame Register;
   private JTextField emailField, textFieldUsername, lNameField, fNameField;
   private JPasswordField passwordField, passwordFieldRepeat;
   private UtilDateModel model;
   private Properties properties;
   private Panel infoPanel, labelPanel;
   private JDatePanelImpl datePanel;
   private BorderLayout borderL;
   private Label infoLabel;
   private Font infoFont, labelFont;
   private Border borderBevel, borderTitled;
   private Color color, color2;
   private static final Color WHITE = Color.WHITE;
   private static final Color BLACK = Color.BLACK;
   private JLabel lblFname, lblLname, lblGender, labelUsername, labelPassword, labelRepeatPassword, labelEmail, labelDoB;
   private FlowLayout layoutFlow;
   private FormLayout layoutForm;
   private RowSpec[] rowSpecs;
   private RowSpec rowSpec1, rowSpec2, rowSpec3;
   private ColumnSpec[] columnSpecs;
   private ColumnSpec colSpec1, colSpec2;
   private ImageIcon  iconFname,iconLname, iconGender, usernameIcon, passwordIcon, iconEmail, iconDob, repeatPasswordIcon;
   private static final SystemColor CTRLHIGHLIGHT = SystemColor.controlHighlight;
   private ButtonGroup radioGroup;
   private static JPanel formPanel;
   private JPanel panelGender;
   private JPanel btnPanel;
   private Component rigidArea;
   private Dimension dimension;
   private JRadioButton rdbtnMale, rdbtnFemale;
   private JButton btnSubmit, btnCancel;
   private JDatePickerImpl datePickerPanel;
   private AbstractFormatter dateFormatter;
   private Component rigidAreaBtnGroup;
   private ActionListener listener;

   public void initialize() {
      @SuppressWarnings("serial")
      class DateLabelFormatter extends AbstractFormatter
      {
         private String datePattern = "dd-MM-yyyy";
         private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

         public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
         }

         public String valueToString(Object value) throws ParseException {
            if (value != null) {
               try{
                  Calendar cal = (Calendar) value;
                  return dateFormatter.format(cal.getTime());    
               }
               catch (Exception e) {
               }
            }
            return "";
         }
      }

      //Used for the FormLayout
      colSpec1 = ColumnSpec.decode("left:max(100dlu;default)");
      colSpec2 = ColumnSpec.decode("312px:grow");
      columnSpecs = new ColumnSpec[] {colSpec1, colSpec2,};
      rowSpec1 = FormFactory.UNRELATED_GAP_ROWSPEC;
      rowSpec2 = RowSpec.decode("38px");
      rowSpec3 = FormFactory.PARAGRAPH_GAP_ROWSPEC;
      rowSpecs = new RowSpec[] {rowSpec1, rowSpec2, rowSpec3, rowSpec2, rowSpec3, rowSpec2, rowSpec3, rowSpec2, rowSpec3, rowSpec2, rowSpec3, rowSpec2, rowSpec3, rowSpec2, rowSpec3, rowSpec2, rowSpec1,};

      //Fonts
      infoFont = new Font("Segoe UI Light", Font.BOLD | Font.ITALIC, 14);
      labelFont = new Font("Verdana", Font.BOLD, 13);

      //Colors
      color = new Color(128, 128, 128);
      color2 = new Color(64, 64, 64);

      //Borders
      borderBevel = new BevelBorder(BevelBorder.LOWERED, color, null, color2, null);
      borderTitled = new TitledBorder(borderBevel, "Register form", TitledBorder.LEFT, TitledBorder.TOP, null, color);

      //Dimensions, Layouts & Rigid areas
      dimension = new Dimension(40, 20);
      borderL = new BorderLayout(0, 0);
      layoutFlow = new FlowLayout(FlowLayout.CENTER, 5, 5);
      layoutForm = new FormLayout(columnSpecs, rowSpecs);
      rigidArea = Box.createRigidArea(dimension);
      rigidAreaBtnGroup = Box.createRigidArea(dimension);

      //Image icons
      iconFname = new ImageIcon("./Icons/Actions-view-process-users-icon.png");
      iconLname = new ImageIcon("./Icons/couple-2-icon.png");
      iconGender = new ImageIcon("./Icons/Users-Gender-icon.png");
      usernameIcon = new ImageIcon("./Icons/Users-Name-icon.png");
      passwordIcon = new ImageIcon("./Icons/Apps-preferences-desktop-user-password-icon.png");
      repeatPasswordIcon = new ImageIcon("./Icons/App-password-icon.png");
      iconEmail = new ImageIcon("./Icons/Places-mail-message-icon.png");
      iconDob = new ImageIcon("./Icons/calendar-icon.png");

      //JDatePicker
      model = new UtilDateModel();
      model.setDate(1975, 1, 1);
      properties = new Properties();
      properties.put("text.today", "Today");
      properties.put("text.month", "Month");
      properties.put("text.year", "Year");
      datePanel = new JDatePanelImpl(model, properties);
      dateFormatter = new DateLabelFormatter();
      datePickerPanel = new JDatePickerImpl(datePanel, dateFormatter);
      datePickerPanel.getJFormattedTextField().setToolTipText("Select your date of birth from the interactive calendar");
      datePickerPanel.getJFormattedTextField().setBackground(WHITE);

      Register = new JFrame();
      Register.setTitle("Register");
      Register.setBounds(100, 100, 691, 615);
      Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Register.getContentPane().setLayout(borderL);
      Register.setLocationRelativeTo(null);
      Register.setResizable(false);

      infoPanel = new Panel();
      infoPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
      Register.getContentPane().add(infoPanel, BorderLayout.NORTH);

      infoLabel = new Label("Please, fill out the form below and press \"Submit\" to be registered as a user.");
      infoLabel.setForeground(UIManager.getColor("InternalFrame.activeTitleForeground"));
      infoLabel.setFont(infoFont);
      infoLabel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
      infoLabel.setAlignment(Label.CENTER);
      infoPanel.add(infoLabel);

      formPanel = new JPanel();
      formPanel.setBorder(borderTitled);
      formPanel.setLayout(layoutFlow);
      Register.getContentPane().add(formPanel);

      labelPanel = new Panel();
      labelPanel.setBackground(CTRLHIGHLIGHT);
      labelPanel.setLayout(layoutForm);

      formPanel.add(labelPanel);

      lblFname = new JLabel("     First name:      ");
      Util.resizeIconAndSetToLabel(iconFname, lblFname, 32, 32);
      lblFname.setBackground(WHITE);
      lblFname.setFont(labelFont);

      lblLname = new JLabel("      Last name:      ");
      Util.resizeIconAndSetToLabel(iconLname, lblLname, 32, 32);
      lblLname.setFont(labelFont);

      fNameField = new JTextField();
      fNameField.setToolTipText("Enter your first name");
      fNameField.setBackground(WHITE);
      fNameField.setColumns(10);

      lNameField = new JTextField();
      lNameField.setToolTipText("Enter your last name");
      lNameField.setBackground(WHITE);
      lNameField.setColumns(10);

      lblGender = new JLabel("         Gender:        ");
      Util.resizeIconAndSetToLabel(iconGender, lblGender, 32, 32);
      lblGender.setFont(labelFont);
      lblGender.setForeground(BLACK);

      panelGender = new JPanel();
      panelGender.setToolTipText("Specify your gender");
      panelGender.setBackground(CTRLHIGHLIGHT);

      rdbtnMale = new JRadioButton("Male");
      rdbtnMale.setFont(labelFont);
      rdbtnMale.setSelected(true);
      rdbtnMale.setBackground(CTRLHIGHLIGHT);

      rdbtnFemale = new JRadioButton("Female");
      rdbtnFemale.setFont(labelFont);
      rdbtnFemale.setBackground(CTRLHIGHLIGHT);

      radioGroup = new ButtonGroup();
      radioGroup.add(rdbtnMale);
      radioGroup.add(rdbtnFemale);

      panelGender.add(rdbtnMale);
      panelGender.add(rigidArea);
      panelGender.add(rdbtnFemale);

      labelUsername = new JLabel("      Username:      ");
      Util.resizeIconAndSetToLabel(usernameIcon, labelUsername, 32, 32);
      labelUsername.setForeground(BLACK);
      labelUsername.setFont(labelFont);

      textFieldUsername = new JTextField();
      textFieldUsername.setToolTipText("Enter your username");
      textFieldUsername.setColumns(10);

      labelPassword = new JLabel("       Password:       ");
      Util.resizeIconAndSetToLabel(passwordIcon, labelPassword, 32, 32);
      labelPassword.setForeground(BLACK);
      labelPassword.setFont(labelFont);

      passwordField = new JPasswordField();
      ToolTipManager.sharedInstance().setDismissDelay(9000);
      passwordField.setToolTipText("<html><span style=\"font-family:Verdana\">Enter your password.</span><br><b><span><span style=\"font-size:13px;font-family:Precious;\">Requirements:  </span><u style=\"font-size:15px;color:red;\">at least</u></span></b><b><p style=\"color:red;font-size:12px;\"> • 7 characters long</p></b><b><p style=\"color:red;font-size:12px;\"> • one capital letter</p></b><b><p style=\"color:red;font-size:12px;\"> • one numeric</p></b></html>");

      labelRepeatPassword = new JLabel("Repeat password:");
      Util.resizeIconAndSetToLabel(repeatPasswordIcon, labelRepeatPassword, 32, 32);
      labelRepeatPassword.setForeground(BLACK);
      labelRepeatPassword.setFont(labelFont);

      passwordFieldRepeat = new JPasswordField();
      passwordFieldRepeat.setToolTipText("Repeat your password");

      labelEmail = new JLabel("          Email:          ");
      Util.resizeIconAndSetToLabel(iconEmail, labelEmail, 32, 32);
      labelEmail.setFont(labelFont);

      emailField = new JTextField();
      emailField.setToolTipText("Enter your email");
      emailField.setBackground(WHITE);
      emailField.setColumns(10);

      labelDoB = new JLabel("    Date of birth:    ");
      Util.resizeIconAndSetToLabel(iconDob, labelDoB, 32, 32);
      labelDoB.setFont(labelFont);

      labelPanel.add(lblFname, "1, 2, center, fill");
      labelPanel.add(fNameField, "2, 2");
      labelPanel.add(lblLname, "1, 4, center, fill");
      labelPanel.add(lNameField, "2, 4");
      labelPanel.add(lblGender, "1, 6, center, fill");
      labelPanel.add(panelGender, "2, 6, fill, center");
      labelPanel.add(labelUsername, "1, 8, center, default");
      labelPanel.add(textFieldUsername, "2, 8, fill, default");
      labelPanel.add(labelPassword, "1, 10, center, default");
      labelPanel.add(passwordField, "2, 10, fill, default");
      labelPanel.add(labelRepeatPassword, "1, 12, center, default");
      labelPanel.add(passwordFieldRepeat, "2, 12, fill, default");
      labelPanel.add(labelEmail, "1, 14, center, default");
      labelPanel.add(emailField, "2, 14");
      labelPanel.add(labelDoB, "1, 16, center, default");
      labelPanel.add(datePickerPanel, "2, 16");

      btnPanel = new JPanel();
      Register.getContentPane().add(btnPanel, BorderLayout.SOUTH);

      btnSubmit = new JButton("Submit");
      btnSubmit.addActionListener(listener);
      btnSubmit.setActionCommand("submit");
      btnCancel = new JButton("Cancel");
      btnCancel.addActionListener(listener);
      btnCancel.setActionCommand("cancel");
      btnPanel.add(btnSubmit);
      btnPanel.add(rigidAreaBtnGroup);
      btnPanel.add(btnCancel);

      Register.getRootPane().setDefaultButton(btnSubmit);
   }

   public void setVisibility(boolean value) {
      Register.setVisible(value);
   }

   public void setListener(ActionListener listener) {
      this.listener = listener;
   }

   public static JPanel getComponents() {
      Register reg = new Register();
      try { 
         reg.initialize();
         return formPanel;
      } finally {
         reg.dispose();
      } 
   }

   /**This method goes through several checks for the user's first name, last name, date of birth, username and
    * password and after all of them satisfy the requirements, a new <code>User</code> and a new <code>Data</code> object
    * storing hir/her information, are created and the newly created <code>User</code> object is returned.
    * @return <code>User</code>
    */
   public User createUser() {
      ValidatorFunctions validator = new ValidatorFunctions();
      String windowMessage = ValidatorFunctions.Fields.WINDOW_MESSAGE.getName();

      validator.placeStringsAndExec(RegexChecker.FIRST_NAME, fNameField.getText(), ValidatorFunctions.Fields.FIRST_NAME);
      validator.placeStringsAndExec(RegexChecker.LAST_NAME, lNameField.getText(), ValidatorFunctions.Fields.LAST_NAME);
      validator.placeStringsAndExec(RegexChecker.USERNAME, this.getUserName(), ValidatorFunctions.Fields.USERNAME);
      validator.placeStringsAndExec(RegexChecker.PASSWORD, this.getPassword(), ValidatorFunctions.Fields.PASSWORD);
      validator.placeStringsAndExec(RegexChecker.EMAIL, this.getEmail(), ValidatorFunctions.Fields.EMAIL);
      if (getDOB() == null) {
         if (validator.fieldsToBeCorrected.contains("Date")) {
         } else {
            validator.fieldsToBeCorrected.add("Date"); 
         }
      }

      Optional<String> list = validator.returnFormattedListofFields();

      if (!validator.fieldsToBeCorrected.isEmpty()) {
         JOptionPane.showMessageDialog(null, windowMessage.concat(list.orElse("No errors")), "Input Errors", JOptionPane.INFORMATION_MESSAGE);
      } else {
         try {
            Data data = new Data(this.getName(), this.getEmail(), this.getDOB(), this.getSex());
            User user = new User(this.getUserName(), this.getPassword(), data);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 18);
            if (user.getData().getBirth().after(cal.getTime())) {
               JOptionPane.showMessageDialog(null, "You have to be over 18", "Date of birth", JOptionPane.WARNING_MESSAGE);
               return null;
            } else { 
               return user;
            }
         } catch (Exception e) {
            e.printStackTrace();
         }    
      }
      return null;
   }

   /**Concatenates the text from the <code>JTextField fNameField</code> with an empty space and then
    * with the text from <code>JTextField lNameField</code> and returns the result.
    * @return <code>String</code>
    */
   private String getName() {
      return fNameField.getText() + " " + lNameField.getText();
   }

   /**Returns the <code>String</code> from the <code>JTextField</code> for 
    * @return <code>String</code>
    */
   private String getEmail() {
      return emailField.getText();
   }

   /**Return a <code>String</code> representation of the Date extracted from <code>JDatePicker</code>
    * in <code>Register</code> UI.
    * @return <code>String</code>
    */
   private String getDOB() {
      String delimiters = "\\D+";
      String date = datePickerPanel.getJFormattedTextField().getText();
      if (!date.equals("")) {
         try {      
            String[] array = date.split(delimiters);
            return array[2] + "-" + array[1] + "-" + array[0];
         } catch (Exception e) {
            e.printStackTrace();      
         }
      }
      return null;
   }

   /**Returns the <code>String</code> from the <code>JTextField</code> used for the username.
    * @return <code>String</code>
    */
   public String getUserName() {
      return textFieldUsername.getText();
   }

   /**Checks if the text from the <code>JPasswordField passwordField</code> is the same as the text from the
    * <code>JPasswordField passwordFieldRepeat</code> and if it is, converts it to a <code>String</code> using
    * the method {@link #passwordToString(char[])} and returns it.
    * @return <code>String</code>
    */
   private String getPassword() {
      if (passwordToString(passwordField.getPassword()).equals(passwordToString(passwordFieldRepeat.getPassword()))) { 
         return passwordToString(passwordField.getPassword()); 
      } else {
         JOptionPane.showMessageDialog(null, "The password you entered are not the same. Please, enter again.", "Password", JOptionPane.WARNING_MESSAGE);
         setPassword();
         setPasswordRepeat();
         return null;
      }
   }

   /**Converts the parameter <code> char[] password</code> to a <code>String</code> and returns it.
    * @param password
    * @return <code>String</code>
    */
   private String passwordToString(char[] password) {
      StringBuilder build = new StringBuilder();
      for (int i = 0; i < password.length; i++) {
         build.append(password[i]);
      }
      return build.toString();
   }

   /**Returns the selected <code>JRadioButton</code> value that represent the user's sex. 
    * @return
    * @deprecated
    */
   private String getSex() {
      if (rdbtnMale.isSelected()) {
         return rdbtnMale.getLabel();
      } else {
         return rdbtnFemale.getLabel();
      }
   }

   /**
    * Sets the username <code>JTextField</code> to an empty string.
    */
   public void setUserName() {
      textFieldUsername.setText("");
   }

   /**
    * Sets the password <code>JTextField</code> to an empty string.
    */
   private void setPassword() {
      passwordField.setText("");
   }

   /**
    * Sets the textfield where user repeats his/her password <code>JTextField</code> to an empty string.
    */
   private void setPasswordRepeat() {
      passwordFieldRepeat.setText("");
   }

   /**
   <p>Releases all of the native screen resources used by this Window, its
   subcomponents, andall of its owned children. That is, the resources for these Components will
   be destroyed, any memory they consume will be returned to the OS, and they will be marked as undisplayable.</p>
   The Window and its subcomponents can be made displayable again by rebuilding
   the native resources with a subsequent call to pack or show.
   The states of the recreated Window and its subcomponents will be identical
   to the states of these objects at the point where the Window was
   disposed (not accounting for additional modifications between those actions).
    */
   public void dispose() {
      Register.dispose();
   }
}