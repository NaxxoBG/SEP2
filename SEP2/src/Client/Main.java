package Client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import GUI.LogIn;
import GUI.Register;

/**The main method in the <code>Main</code> class is executed as a starting point for the users' Clients
 */
public class Main
{
   @SuppressWarnings("unused")
   public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
      LogIn logIn = new LogIn();
      Register register = new Register();
      ControllerLogIn control = new ControllerLogIn(logIn, register);
   }
}