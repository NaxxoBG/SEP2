package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import GUI.LogIn;
import GUI.MainGui;
import GUI.Register;
import User.User;

public class ControllerLogIn
{
   private Client model;
   private LogIn viewLogIn;
   private Register register;

   public ControllerLogIn(LogIn logIn, Register register) {
      this.viewLogIn = logIn;
      this.register=register;
      ButtonListener listener = new ButtonListener();
      this.viewLogIn.setListener(listener);
      this.register.setListener(listener);
      logIn.initialize();
   }

   private class ButtonListener implements ActionListener {
      @SuppressWarnings("unused")
      public void actionPerformed(ActionEvent e) {
         try {
            model = new Client(viewLogIn.getIPAddress());
         } catch (MalformedURLException | RemoteException | NotBoundException e2) {
            JOptionPane.showMessageDialog (null, "Cannot connect to the server", "Server", JOptionPane.WARNING_MESSAGE);
            e2.printStackTrace();
            return;
         }
         switch (e.getActionCommand()) {
            case "logIn": 
               try {
                  if (!viewLogIn.getUserName().equals("") && !viewLogIn.getPassword().equals("") 
                        && model.getInter().verifyLogin(viewLogIn.getUserName(), viewLogIn.getPassword())) {
                     model.setUser(viewLogIn.getUserName());
                     model.setMatches();
                     model.setTickets();
                     model.attach();
                     MainGui mainGui = new MainGui();
                     ControllerMain mainController = new ControllerMain(model, mainGui);                     
                     viewLogIn.dispose();                  
                  } else if (model.getInter().availableUserName(viewLogIn.getUserName())) {
                     viewLogIn.wrongName();
                  } else {
                     viewLogIn.wrongPassword();
                  }
               } catch (Exception e1) {
                  e1.printStackTrace();
               }
               break; 
            case "newAccount":
               register.initialize();
               register.setVisibility(true);
               viewLogIn.dispose();
               break;
            case "cancel":
               register.dispose();
               viewLogIn.initialize();
               break;
            case "submit":
               try {
                  User user = register.createUser();
                  if (model.getInter().availableUserName(register.getUserName()) && user != null) {
                     if (model.getInter().insertUser(user)) { 
                        model.setUser(register.getUserName());
                        model.setMatches();
                        MainGui mainGui = new MainGui();
                        ControllerMain main = new ControllerMain(model, mainGui);
                        register.dispose();
                     } else {
                     }
                  }
                  else {
                     if (user != null) {
                        JOptionPane.showMessageDialog (null, "Username already used", "Username", JOptionPane.WARNING_MESSAGE);
                     }
                  }
               } catch (Exception e2) {
                  e2.printStackTrace();
               }
               break;
            default:
               break;
         }
      }
   }
}