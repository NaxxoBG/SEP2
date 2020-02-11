package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import GUI.MainGui;
import GUI.VirtualTicket;
import MatchTicket.Ticket;
import User.User;

/**Controller for the main GUI.
 */
public class ControllerMain
{
   private Client model;
   private MainGui mainGui;
   private VirtualTicket ticket;
   private static int counter = 0;

   /** Constructor of the <code>ControllerMain</code> that
    * connects the <code>Client</code> model with the <code>mainGui</code>.
    * @param model
    * @param mainGui
    * @throws RemoteException
    */
   public ControllerMain(Client model, MainGui mainGui) throws RemoteException {
      this.model = model;
      this.mainGui = mainGui;
      this.ticket = new VirtualTicket();
      ButtonListener listener = new ButtonListener();    
      this.mainGui.setListener(listener);
      this.mainGui.setAdapter(setMouseAdapter());
      this.mainGui.setHistoryMatchListener(new SharedListSelectionHandler());
      this.mainGui.setRefreshAction(new RefreshAction());
      this.ticket.setListener(listener);

      mainGui.initialize(model.getUser(), model.getTodaysMatches(), model.getTickets()); 
      mainGui.setHistoryTable(model.getHistoryMatches(0));
      ticket.initialize();
   }

   /**Button listener that determines what the program does on pressing the different buttons in <code>mainGui</code>.
    */
   private class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         switch (e.getActionCommand()) {
            case "filter": 
               try {
                  if (mainGui.getType().equals("Today") && mainGui.emptyDates()) {
                     mainGui.setTable(model.getTodaysMatches());
                  } else if (mainGui.emptyDates()) {
                     mainGui.setTable(model.getInter().getMatches(mainGui.getType()));
                  } else if (mainGui.getType().equals("Today")) {
                     mainGui.setTable(model.getInter().getMatches(mainGui.getDate1(), mainGui.getDate2()));
                  } else {                     
                     mainGui.setTable(model.getInter().getMatches(mainGui.getType(), mainGui.getDate1(), mainGui.getDate2()));
                  }
               } catch(Exception e2) {
                  e2.printStackTrace();
               }
               break;
            case "show": 
               ticket.setVisibility(true);
               break;
            case "save":
               try {
                  User user = mainGui.setUser();
                  if (!(user==null)) {
                     model.editUser(user);
                     model.setUser(user.getUserName());
                     mainGui.setProfile(model.getUser());
                     mainGui.profileSaved();
                  }      
               } catch (RemoteException e1) {
                  e1.printStackTrace();
               };
               break;
            case "cancel":
               mainGui.setProfile(model.getUser()); 
               break;
            case "clear":
               ticket.resetTicket();
               ticket.setTable(); 
               break;
            case "remove":
               ticket.removeFromTicket();
               ticket.setTable();
               break;
            case "placeBet": 
               double money = ticket.getMoney(model.getUser().getScore());
               if (money <= 0) {
                  return;
               } else {
                  Ticket tck = ticket.createTicket(model.getUser());
                  if (tck.getBets().size() == 0) {
                     ticket.emptyTicket();
                     break;
                  }
                  try {
                     model.getInter().addTicket(tck);
                     model.setTickets();
                     model.setScore(model.getUser().getScore() - money);                 
                     mainGui.setScore(model.getUser().getScore());
                     mainGui.setListModel(model.getTickets());
                     mainGui.setHistoryTable(model.getHistoryMatches(0));
                     ticket.addTicketSuccesfully();
                     ticket.setTable();
                  } catch (RemoteException e1) {
                     e1.printStackTrace();
                  }
               }
               break;
            default:
               System.out.println("wrong command for Button");
               break;
         }
      }  
   }

   public class SharedListSelectionHandler implements ListSelectionListener {
      public void valueChanged(ListSelectionEvent e) {
         ListSelectionModel lsm = (ListSelectionModel) e.getSource();
         if (counter != lsm.getMinSelectionIndex()) {
            try {            
               mainGui.setHistoryTable(model.getHistoryMatches(lsm.getMinSelectionIndex()));
               counter = lsm.getMinSelectionIndex();
            } catch (RemoteException e1) {
               System.out.println("Error in list listener");
               e1.printStackTrace();
            }
         }
      }
   }

   private MouseAdapter setMouseAdapter() {
      MouseAdapter adapter = new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
               int c = mainGui.getColumn();
               if (c == 1 || c == 2 || c == 3 || c == 4 || c == 5){
                  ticket.addToTicket(mainGui.getRow());
                  ticket.setTable();
                  ticket.setVisibility(true);
               }
            }
         }};
         return adapter;  
   }

   public class RefreshAction extends AbstractAction
   {
      private static final long serialVersionUID = 1L;

      public void actionPerformed(ActionEvent e) {
         try {
            model.resetLoading();
            mainGui.setListModel(model.getTickets());
            mainGui.setScore(model.getUser().getScore());
         } catch (RemoteException e1) {
            e1.printStackTrace();
         }     
      }
   }
}