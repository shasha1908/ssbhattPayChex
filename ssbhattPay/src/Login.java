import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Login Check
 */

public class Login extends JFrame
{
    Container container;
    Connection connection;
    ClockInDetails clockInDetails;

    Login(Connection connection)
    {
        super("Login form");
        this.connection = connection;
        container = getContentPane();
        container.setLayout(new FlowLayout());
        createLoginForm();
        clockInDetails = new ClockInDetails(connection);
        clockInDetails.setVisible(true);
    }

    private void createLoginForm(){
        JLabel userNameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        JTextField userName = new JTextField(10);
        JPasswordField password = new JPasswordField(10);

        container.add(userNameLabel);
        container.add(userName);
        container.add(passwordLabel);
        container.add(password);

        createLoginButton(userName,password);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,200);

    }

    private void createLoginButton(JTextField userName, JPasswordField password){
        JButton loginButton = new JButton("Login");
        container.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(actionEvent.getSource()==loginButton)
                {
                    String inputPassword = String.copyValueOf(password.getPassword());
                    int employeeId = authorizeEmployee(userName.getText(), inputPassword);
                    if(employeeId != -1)
                    {
                        JOptionPane.showMessageDialog(null, "You have logged in successfully","Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        clockInDetails.createWindow(employeeId);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Login failed!","Failed!!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public int authorizeEmployee(String userName, String password)
    {
        try {
            PreparedStatement loginQuery = connection.prepareStatement("Select * from account where userName=? and password=?");
            loginQuery.setString(1, userName);
            loginQuery.setString(2, password);

            ResultSet loginResultSet = loginQuery.executeQuery();
            if(loginResultSet.next())
            {
                int employeeId = Integer.parseInt(loginResultSet.getString("employeeID"));
                return employeeId;
            }
            else
            {
                return -1;
            }
        } catch (Exception e) {
            System.out.println("error while validating"+e);
            return -1;
        }
    }
}