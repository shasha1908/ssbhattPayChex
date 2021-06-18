import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Clock In, Clock Out, Break Start, Break End, Lunch break start, Lunch break end logic implementation.
 */

public class ClockInDetails extends  JFrame {
    Connection connection;
    JPanel panel;
    JFrame frame;

    public ClockInDetails(Connection connection) {
        this.connection = connection;
    }

    public void createWindow(int employeeId) {
        JFrame frame = new JFrame("Clock Functions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame = frame;
        createUI(employeeId);
        frame.setSize(560, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUI(int employeeId) {
        JPanel panel = new JPanel();
        this.panel = panel;
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        createClockInButton(employeeId);
        createClockOutButton(employeeId);
        createBreakInButton(employeeId);
        createBreakOutButton(employeeId);
        createLunchInButton(employeeId);
        createLunchOutButton(employeeId);
    }

    private void createClockInButton(int employeeId){
        JButton clockInButton = new JButton("Clock In");
        JTextField employeeInfo = new JTextField();
        employeeInfo.setText(String.valueOf(employeeId));

        clockInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeInfo.getText();
                try {
                    PreparedStatement employeeClockInQuery = connection.prepareStatement("select * from clockDetails where employeeID = ? and ClockOutTime IS NULL ORDER BY ClockInTime desc limit 1");
                    employeeClockInQuery.setString(1, employeeId);
                    ResultSet employeeClockInResult = employeeClockInQuery.executeQuery();
                    if(employeeClockInResult.next()){
                        JOptionPane.showMessageDialog(clockInButton, "Already ClockedIn");
                    } else{
                        String createClockInQuery = "INSERT INTO `clockDetails` (employeeID,ClockInTime) VALUES (" +  employeeId +", CURRENT_TIMESTAMP())";
                        Statement clockInStatement = connection.createStatement();
                        clockInStatement.executeUpdate(createClockInQuery);
                        JOptionPane.showMessageDialog(clockInButton,
                                "Welcome, Your timestamp is successfully created");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        frame.getRootPane().setDefaultButton(clockInButton);
        panel.add(clockInButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void createClockOutButton(int employeeId){
        JButton clockOutButton = new JButton("Clock Out");
        JTextField employeeInfo = new JTextField();
        employeeInfo.setText(String.valueOf(employeeId));
        clockOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeInfo.getText();
                try {
                    PreparedStatement employeeClockOutQuery = connection.prepareStatement("select * from clockDetails where employeeID = ? and ClockOutTime IS NULL ORDER BY ClockInTime desc limit 1");
                    employeeClockOutQuery.setString(1, employeeId);
                    ResultSet clockOutResult = employeeClockOutQuery.executeQuery();
                    if(clockOutResult.next()){
                        if(clockOutResult.getString("break").equals("1")){
                            JOptionPane.showMessageDialog(clockOutButton, "Can't clock out during a break");
                        }
                        else if(clockOutResult.getString("lunch").equals("1")){
                            JOptionPane.showMessageDialog(clockOutButton, "Can't clock out during lunch");
                        }else {
                            String createClockOutQuery = "UPDATE  `clockDetails` SET  ClockOutTime=CURRENT_TIMESTAMP() WHERE employeeId = " + employeeId + " ORDER BY ClockInTime desc limit 1";
                            Statement clockOutQueryStatement = connection.createStatement();
                            int clockOutUpdateResult = clockOutQueryStatement.executeUpdate(createClockOutQuery);
                            if (clockOutUpdateResult == 1) {
                                JOptionPane.showMessageDialog(clockOutButton,
                                        "Clocked Out");
                            } else {
                                JOptionPane.showMessageDialog(clockOutButton, "Error while clockout ");
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(clockOutButton, "You are not currently clocked In");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        frame.getRootPane().setDefaultButton(clockOutButton);
        panel.add(clockOutButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
    private void createBreakInButton(int employeeId){
        JButton breakButton = new JButton("Break Start");
        JTextField employeeInfo = new JTextField();
        employeeInfo.setText(String.valueOf(employeeId));
        breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeInfo.getText();
                try {
                    PreparedStatement employeeBreakInQuery = connection.prepareStatement("select * from clockDetails where employeeID = ? and ClockInTime IS NOT NULL and ClockOutTime IS NULL ORDER BY ClockInTime desc limit 1");
                    employeeBreakInQuery.setString(1, employeeId);
                    ResultSet employeeBreakInResult = employeeBreakInQuery.executeQuery();
                    if(employeeBreakInResult.next()){
                        PreparedStatement createBreakInQuery = connection.prepareStatement("UPDATE `clockDetails` SET  break = 1 WHERE employeeId = ? ORDER BY ClockInTime desc limit 1");
                        createBreakInQuery.setString(1, employeeId);
                        int createBreakInResult = createBreakInQuery.executeUpdate();
                        if(createBreakInResult == 1){
                            JOptionPane.showMessageDialog(breakButton, "Break Started");
                        }else{
                            JOptionPane.showMessageDialog(breakButton, "Error while taking break");
                        }
                    }else{
                        JOptionPane.showMessageDialog(breakButton, "You do not have any active shift");
                    }

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }

        });
        frame.getRootPane().setDefaultButton(breakButton);
        panel.add(breakButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
    private void createBreakOutButton(int employeeId) {
        JButton breakOutButton = new JButton("Break End");
        JTextField employeeInfo = new JTextField();
        employeeInfo.setText(String.valueOf(employeeId));
        breakOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeInfo.getText();
                try {
                    PreparedStatement employeeBreakOutQuery = connection.prepareStatement("select * from clockDetails where employeeID = ? and ClockInTime IS NOT NULL and ClockOutTime IS NULL ORDER BY ClockInTime desc limit 1");
                    employeeBreakOutQuery.setString(1, employeeId);
                    ResultSet employeeBreakOutResult = employeeBreakOutQuery.executeQuery();
                    if(employeeBreakOutResult.next()){
                        PreparedStatement createBreakOutQuery = connection.prepareStatement("UPDATE `clockDetails` SET  break = 0 WHERE employeeId = ? ORDER BY ClockInTime desc limit 1");
                        createBreakOutQuery.setString(1, employeeId);
                        int createBreakOutResult = createBreakOutQuery.executeUpdate();
                        if(createBreakOutResult == 1){
                            JOptionPane.showMessageDialog(breakOutButton, "Break Ended");
                        }else{
                            JOptionPane.showMessageDialog(breakOutButton, "Error while ending break");
                        }
                    }else{
                        JOptionPane.showMessageDialog(breakOutButton, "You can end a break only during active shift");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }

        });
        frame.getRootPane().setDefaultButton(breakOutButton);
        panel.add(breakOutButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
    private void createLunchInButton(int employeeId) {
        JButton lunchInButton = new JButton("Lunch Start");
        JTextField employeeInfo = new JTextField();
        employeeInfo.setText(String.valueOf(employeeId));
        lunchInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeInfo.getText();
                try {
                    PreparedStatement employeeLunchInQuery = connection.prepareStatement("select * from clockDetails where employeeID = ? and ClockInTime IS NOT NULL and ClockOutTime IS NULL ORDER BY ClockInTime desc limit 1");
                    employeeLunchInQuery.setString(1, employeeId);
                    ResultSet employeeLunchInResult = employeeLunchInQuery.executeQuery();
                    if(employeeLunchInResult.next()){
                        PreparedStatement createLunchInQuery = connection.prepareStatement("UPDATE `clockDetails` SET  lunch = 1 WHERE employeeId = ? ORDER BY ClockInTime desc limit 1");
                        createLunchInQuery.setString(1, employeeId);
                        int createLunchInResult = createLunchInQuery.executeUpdate();
                        if(createLunchInResult == 1){
                            JOptionPane.showMessageDialog(lunchInButton, "Lunch Started");
                        }else{
                            JOptionPane.showMessageDialog(lunchInButton, "Error while taking lunch");
                        }
                    }else{
                        JOptionPane.showMessageDialog(lunchInButton, "You do not have any active shift");
                    }

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        frame.getRootPane().setDefaultButton(lunchInButton);
        panel.add(lunchInButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
    private void createLunchOutButton(int employeeId) {
        JButton lunchOutButton = new JButton("Lunch End");
        JTextField employeeInfo = new JTextField();
        employeeInfo.setText(String.valueOf(employeeId));
        lunchOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeInfo.getText();
                try {
                    PreparedStatement employeeLunchOutQuery = connection.prepareStatement("select * from clockDetails where employeeID = ? and ClockInTime IS NOT NULL and ClockOutTime IS NULL ORDER BY ClockInTime desc limit 1");
                    employeeLunchOutQuery.setString(1, employeeId);
                    ResultSet employeeLunchOutResult = employeeLunchOutQuery.executeQuery();
                    if(employeeLunchOutResult.next()){
                        PreparedStatement createLunchOutQuery = connection.prepareStatement("UPDATE `clockDetails` SET  lunch = 0 WHERE employeeId = ? ORDER BY ClockInTime desc limit 1");
                        createLunchOutQuery.setString(1, employeeId);
                        int createLunchOutResult = createLunchOutQuery.executeUpdate();
                        if(createLunchOutResult == 1){
                            JOptionPane.showMessageDialog(lunchOutButton, "Lunch Ended");
                        }else{
                            JOptionPane.showMessageDialog(lunchOutButton, "Error while ending Lunch");
                        }
                    }else{
                        JOptionPane.showMessageDialog(lunchOutButton, "You can end a break only during active shift");
                    }
                }  catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        frame.getRootPane().setDefaultButton(lunchOutButton);
        panel.add(lunchOutButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}