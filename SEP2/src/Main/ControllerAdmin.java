package Main;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import GUI.AdminGui;
import MatchTicket.Match;
import Server.Server;
import Utilities.RegexChecker;
import Utilities.Util;
import Utilities.ValidatorFunctions;

public class ControllerAdmin
{
   private Implementation impl;
   private AdminGui gui;
   private static final Runnable SOUND = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
   private static int selectedRow = -1;

   public ControllerAdmin() throws RemoteException {
      ButtonListener listener = new ButtonListener();
      this.impl = Implementation.getInstance();
      this.gui = new AdminGui();
      this.gui.setListener(listener);
      this.gui.setAdapter(setMouseAdapter());
      this.gui.setFocusListener(setFocusListener());
      this.gui.setFocusListener2(setFocusListener2());
      this.gui.initialize(impl.getTodaysMatches());
   }

   private class ButtonListener implements ActionListener
   {
      ValidatorFunctions validator;
      public void actionPerformed(ActionEvent e) {
         switch (e.getActionCommand()) {
            case "edit":
               JTable table = gui.getTable();
               if (table.getSelectedRow() == -1) {
                  playErrorSound();
                  JOptionPane.showMessageDialog(null, "Select a match if you want to edit the scores", "Error", JOptionPane.ERROR_MESSAGE);
               } else {
                  Match matchUpdate = impl.getMatch((int) (table.getValueAt(table.getSelectedRow(), 0)));
                  if (RegexChecker.inputValidator(RegexChecker.SCORE, gui.getTextFieldScore1().getText()) == false && RegexChecker.inputValidator(RegexChecker.SCORE, gui.getTextFieldScore2().getText()) == false) {
                     playErrorSound();
                     JOptionPane.showMessageDialog(null, "Please enter valid values for the scores for Team 1 and Team 2!", "Error", JOptionPane.ERROR_MESSAGE);
                  } else if (RegexChecker.inputValidator(RegexChecker.SCORE, gui.getTextFieldScore1().getText()) == false) {
                     playErrorSound();
                     JOptionPane.showMessageDialog(null, "Please enter a valid value for the score for Team 1!", "Error", JOptionPane.ERROR_MESSAGE);
                  } else if (RegexChecker.inputValidator(RegexChecker.SCORE, gui.getTextFieldScore2().getText()) == false) {
                     playErrorSound();
                     JOptionPane.showMessageDialog(null, "Please enter a valid value for the score for Team 2!", "Error", JOptionPane.ERROR_MESSAGE);
                  } else if (gui.getDateEdit() == null) {
                     playErrorSound();
                     JOptionPane.showMessageDialog(null, "Please choose a date for the event!", "Error", JOptionPane.ERROR_MESSAGE);
                  } else {
                     int n = JOptionPane.showConfirmDialog(null, "Are you sure that you want to edit this match", "Confirm edit", 2);
                     if (n == 0) {
                        impl.updateMatch(matchUpdate.getID(), Integer.parseInt(gui.getTextFieldScore1().getText()), Integer.parseInt(gui.getTextFieldScore2().getText()),gui.getMatchStart());
                        JOptionPane.showMessageDialog(null, "Match has been edited succesfully", "Match", JOptionPane.INFORMATION_MESSAGE);
                        setTextFieldGroupAddMatch(true);
                        gui.resetFields();
                     }
                  }
               }
               break;
            case "add":
               validator = new ValidatorFunctions();
               String windowMessage = ValidatorFunctions.Fields.WINDOW_MESSAGE.getName();
               validator.placeStringsAndExec(RegexChecker.TEAM_NAME, gui.getTextFieldTeam1().getText(), ValidatorFunctions.Fields.TEAM_1_NAME);
               validator.placeStringsAndExec(RegexChecker.TEAM_NAME, gui.getTextFieldTeam2().getText(), ValidatorFunctions.Fields.TEAM_2_NAME);
               validator.placeStringsAndExec(RegexChecker.COEFFICIENT, gui.getTextFieldCoeff1().getText(), ValidatorFunctions.Fields.TEAM_1_COEFFICIENT);
               validator.placeStringsAndExec(RegexChecker.COEFFICIENT, gui.getTextFieldCoeff2().getText(), ValidatorFunctions.Fields.TEAM_2_COEFFICIENT);
               validator.placeStringsAndExec(RegexChecker.COEFFICIENT, gui.getTextFieldCoeffTie().getText(), ValidatorFunctions.Fields.COEFFICIENT_TIE);
               validator.placeStringsAndExec(gui.getDatePickerPanelEditField().getText(), ValidatorFunctions.Fields.MATCH_DATE);
               Optional<String> list = validator.returnFormattedListofFields();
               if (!validator.fieldsToBeCorrected.isEmpty()) {
                  JOptionPane.showMessageDialog(null, windowMessage.concat(list.orElse("No errors")), "Input errors", JOptionPane.INFORMATION_MESSAGE);
               } else {
                  try {
                     Match matchAdd = gui.createMatch();
                     impl.insertMatch(matchAdd);
                     JOptionPane.showMessageDialog(null, "Match has been inserted succesfully", "Match", JOptionPane.INFORMATION_MESSAGE);
                     gui.resetFields();
                  } catch (Exception e2) {
                     e2.printStackTrace();
                     playErrorSound();
                     JOptionPane.showMessageDialog(null, "Please fill out the match details correctly", "Error", JOptionPane.ERROR_MESSAGE);
                  }
               }
               break;
            case "filter": 
               try {
                  gui.resetFields();
                  if (gui.getType().equals("Today") && gui.emptyDates()) {
                     gui.setTable(impl.getTodaysMatches()); 
                  } else if (gui.emptyDates()) {
                     gui.setTable(impl.getMatches(gui.getType()));
                  } else if (gui.getType().equals("Today")) {
                     gui.setTable(impl.getMatches(gui.getType(), gui.getDateFrom(), gui.getDateTo()));
                  } else { 
                     if (gui.getDateTo() == null) {
                        gui.setTable(impl.getOldAndNewMatches(gui.getType(), gui.getDateFrom(), gui.getTodayDate()));
                     } else {
                        gui.setTable(impl.getOldAndNewMatches(gui.getType(), gui.getDateFrom(), gui.getDateTo()));
                     }
                  }
               } catch (Exception e2) {
                  e2.printStackTrace();
               }
               break;
         }
      }
   }

   private MouseAdapter setMouseAdapter() {
      MouseAdapter adapter = new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            JTable table = gui.getTable();
            if (e.getClickCount() == 1 & selectedRow != table.getSelectedRow()) {
               selectedRow = table.getSelectedRow();
               Match match = impl.getMatch((int) (table.getValueAt(table.getSelectedRow(), 0)));
               gui.getTextFieldScore1().setEnabled(true);
               gui.getTextFieldScore2().setEnabled(true);
               gui.getAddButton().setEnabled(false);
               gui.getEditButton().setEnabled(true);
               setTextFieldGroupAddMatch(false);
               gui.getTextFieldID().setText(Integer.toString((match.getID())));
               gui.getTextFieldTeam1().setText(match.getTeam1());
               gui.getTextFieldTeam2().setText(match.getTeam2());
               gui.getTextFieldCoeff1().setText(Double.toString(Util.round(match.getCoefficient1(), 1)));
               gui.getTextFieldCoeff2().setText(Double.toString(Util.round(match.getCoefficient2(), 1)));
               gui.getTextFieldCoeffTie().setText(Double.toString(Util.round(match.getCoefficientTie(), 1)));
               gui.getTextFieldScore1().setText( Integer.toString(match.getScore1()));
               gui.getTextFieldScore2().setText(Integer.toString(match.getScore2()));
               gui.getComboBoxAddEdit().setSelectedItem(match.getType());
               gui.getTimeSpinner().setValue(gui.getTable().getValueAt(gui.getTable().getSelectedRow(), 7));
               gui.getDatePickerPanelEditField().setText(match.getStringDate());
            } else if (e.getClickCount() == 1 & table.getSelectedRow() == selectedRow) {
               selectedRow = -1;
               table.getSelectionModel().clearSelection();
               gui.resetFields();
               gui.getTextFieldScore1().setEnabled(false);
               gui.getTextFieldScore2().setEnabled(false);
               setTextFieldGroupAddMatch(true);
            }
         }};
         return adapter;  
   }

   /**Sets the editability of the text fields used for adding a match to the value of the <code>boolean b</code>.
    * @param b
    */
   private void setTextFieldGroupAddMatch(boolean b) {
      gui.getTextFieldTeam1().setEnabled(b);
      gui.getTextFieldTeam2().setEnabled(b);
      gui.getTextFieldCoeff1().setEnabled(b);
      gui.getTextFieldCoeff2().setEnabled(b);
      gui.getTextFieldCoeffTie().setEnabled(b);
      gui.getComboBoxAddEdit().setEnabled(b);
   }

   /**Plays the windows default error sound.
    */
   private void playErrorSound() {
      if (SOUND != null) {
         SOUND.run();
      }
   }

   /**Focus Listener for textFields: team1, team2, coefficient1, coefficient2, coefficientTie
    * @return <code>FocusListener</code>
    */
   private FocusListener setFocusListener() {
      FocusListener listener = new FocusListener() {
         public void focusGained(FocusEvent e) {
            gui.getTextFieldScore1().setEnabled(false);
            gui.getTextFieldScore2().setEnabled(false);
            gui.getAddButton().setEnabled(true);
            gui.getEditButton().setEnabled(false);
         }

         public void focusLost(FocusEvent e) {}};
         return listener;
   }

   /**Focus Listener for text fields score1, score2
    * @return
    */
   private FocusListener setFocusListener2() {
      FocusListener listener = new FocusListener() {
         public void focusGained(FocusEvent e) {
            gui.getTextFieldTeam1().setEnabled(false);
            gui.getTextFieldTeam2().setEnabled(false);
            gui.getTextFieldCoeff1().setEnabled(false);
            gui.getTextFieldCoeff2().setEnabled(false);
            gui.getTextFieldCoeffTie().setEnabled(false);
            gui.getComboBoxAddEdit().setEnabled(false);
            gui.getEditButton().setEnabled(true);
         }

         public void focusLost(FocusEvent e) {}};
         return listener;
   }

   @SuppressWarnings("unused")
   public static void main(String[] args) throws RemoteException, ClassNotFoundException, MalformedURLException, AlreadyBoundException {
      ControllerAdmin admin = new ControllerAdmin();
      Server server = new Server(Implementation.getInstance());
   }
}