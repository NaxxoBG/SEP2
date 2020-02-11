package Server;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import Main.Implementation;
import SharedInterface.SharedInterface;

@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject
{
   public Server(Implementation impl) throws RemoteException, ClassNotFoundException, MalformedURLException, AlreadyBoundException {
      SharedInterface inter = (SharedInterface) UnicastRemoteObject.exportObject(impl, 0);
      System.setProperty("java.rmi.server.hostname","localhost");
      LocateRegistry.createRegistry(1099);
      Naming.bind("rmi://localhost:1099/Server", inter);
      try
      {
         System.out.println("Server is running"+ InetAddress.getLocalHost());
      }
      catch (Exception e)
      {
      }
    
   }
}