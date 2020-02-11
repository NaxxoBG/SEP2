package GUI;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.Box;
import java.awt.Dimension;

public class LogIn
{
   private JFrame frame;
   private JTextField txtIPAddress;
   private JTextField txtUserName;
   private JPasswordField txtPassword;
   private ActionListener listener = null;
   private JButton btnNewAccount;
   private JLabel lblPassword;
   private JPanel panelSubmit;
   private JLabel lblUserName;
   private Component rigidArea;
   private JPanel panelIP;
   private JPanel mainPanel;
   private JButton btnLogIn;

   public LogIn() {}

   private void createFrame() {
      frame = new JFrame("Betting");
      frame.setBounds(100, 100, 294, 327);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(new BorderLayout(0, 0));
   }

   public void initialize() {
      createFrame();
      mainPanel = new JPanel();
      mainPanel.setBorder(new TitledBorder(UIManager
            .getBorder("TitledBorder.border"), "LogIn", TitledBorder.LEADING,
            TitledBorder.TOP, null, new Color(0, 0, 0)));
      frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.addWindowListener(new WindowAdapter() {
         public void windowOpened(WindowEvent e) {
            txtUserName.requestFocus();
         }
      });

      panelIP = new JPanel();
      panelIP.setBorder(new TitledBorder(UIManager
            .getBorder("TitledBorder.border"), "IP address",
            TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
      mainPanel.add(panelIP);

      txtIPAddress = new JTextField();
      txtIPAddress.setText("localhost");
      txtIPAddress.setColumns(20);
      panelIP.add(txtIPAddress);

      rigidArea = Box.createRigidArea(new Dimension(250, 20));
      mainPanel.add(rigidArea);

      lblUserName = new JLabel("Username :");
      mainPanel.add(lblUserName);

      txtUserName = new JTextField();
      txtUserName.setColumns(20);

      mainPanel.add(txtUserName);

      lblPassword = new JLabel("Password:");
      mainPanel.add(lblPassword);

      txtPassword = new JPasswordField(20);
      mainPanel.add(txtPassword);

      panelSubmit = new JPanel();
      panelSubmit.setBorder(new TitledBorder(UIManager
            .getBorder("TitledBorder.border"), "Submit", TitledBorder.CENTER,
            TitledBorder.TOP, null, new Color(0, 0, 0)));
      frame.getContentPane().add(panelSubmit, BorderLayout.SOUTH);

      btnNewAccount = new JButton("New Account");
      btnNewAccount.setActionCommand("newAccount");
      btnNewAccount.addActionListener(listener);
      panelSubmit.add(btnNewAccount);

      btnLogIn = new JButton("Log In");
      btnLogIn.setActionCommand("logIn");
      btnLogIn.addActionListener(listener);
      panelSubmit.add(btnLogIn);

      frame.getRootPane().setDefaultButton(btnLogIn);
      frame.setVisible(true);
   }

   public void setListener(ActionListener listener) {
      this.listener = listener;
   }

   public String getIPAddress() {
      return txtIPAddress.getText();
   }

   public String getUserName() {
      return txtUserName.getText();
   }

   public String getPassword() {
      StringBuilder password = new StringBuilder();
      for (int i = 0; i < txtPassword.getPassword().length; i++) {
         password.append(txtPassword.getPassword()[i]);
      }
      return password.toString();
   }

   public void setUserName(String text) {
      txtUserName.setText(text);
   }

   public void setPassword(String text) {
      txtPassword.setText(text);
   }

   public void dispose() {
      frame.dispose();
   }

   public void wrongName() {
      JOptionPane.showMessageDialog(null, "Wrong User Name", "User Name", JOptionPane.WARNING_MESSAGE);
      setUserName("");
      setPassword("");
   }

   public void wrongPassword() {
      JOptionPane.showMessageDialog(null, "Incorrect password", "Password", JOptionPane.WARNING_MESSAGE);
      setPassword("");
   }
}