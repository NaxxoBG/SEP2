package JUnitTest;

import static org.junit.Assert.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.junit.Test;
import Client.Client;
import Main.Implementation;
import Server.Server;

public class RMITest
{
   @Test
   public void test() throws RemoteException, ClassNotFoundException, MalformedURLException, AlreadyBoundException, NotBoundException {
      @SuppressWarnings("unused")
      Server server = new Server(Implementation.getInstance());
      Client client = new Client("localhost");
      assertEquals("HELLO", client.getInter().testConnection("hello"));
   }
}