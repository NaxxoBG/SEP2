package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**Interface with methods aimed at the observers' matches and tickets.
 */
public interface IObserver extends Remote
{
   /**Sets the <code>User</code> field in the <code>Client</code> class to a <code>User</code> object with the parameter
    * <code>userName</code>. The newly set user has all information about 
    * @param userName
    * @throws RemoteException
    */
   public void setUser(String userName) throws RemoteException;

   /**Sets the <code>Client</code>'s field <code>todayList</code> to the matches for today. 
    * @throws RemoteException
    */
   public void setMatches() throws RemoteException;

   /**Sets the matches and their respecite tickets  of the <code>Client</code> class.
    * Matches are put in a <code>HashMap</code> .
    * @throws RemoteException
    */
   public void setTickets() throws RemoteException;
}