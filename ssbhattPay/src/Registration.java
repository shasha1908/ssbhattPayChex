import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Register the employee in the employee Database
 */

public class Registration extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JTextField username;
    private JTextField contact;
    private JPasswordField password;
    private Connection connection;

    public Registration(Connection connection) {
        super("Registration form");
        this.connection = connection;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        createRegistrationForm();
    }

    private void createRegistrationForm(){
        JLabel lblNewUserRegister = new JLabel("New User Register");
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 42));
        lblNewUserRegister.setBounds(362, 52, 325, 50);
        contentPane.add(lblNewUserRegister);

        JLabel lblName = new JLabel("First name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblName.setBounds(58, 152, 100, 30);
        contentPane.add(lblName);

        JLabel lblNewLabel = new JLabel("Last name");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(58, 243, 100, 30);
        contentPane.add(lblNewLabel);

        JLabel lblEmailAddress = new JLabel("Email");
        lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailAddress.setBounds(58, 324, 100, 30);
        contentPane.add(lblEmailAddress);

        firstName = new JTextField();
        firstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        firstName.setBounds(214, 151, 228, 50);
        contentPane.add(firstName);
        firstName.setColumns(10);

        lastName = new JTextField();
        lastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lastName.setBounds(214, 235, 228, 50);
        contentPane.add(lastName);
        lastName.setColumns(10);

        email = new JTextField();

        email.setFont(new Font("Tahoma", Font.PLAIN, 20));
        email.setBounds(214, 320, 228, 50);
        contentPane.add(email);
        email.setColumns(10);

        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 20));
        username.setBounds(707, 151, 228, 50);
        contentPane.add(username);
        username.setColumns(10);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(542, 159, 228, 50);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(542, 245, 228, 50);
        contentPane.add(lblPassword);

        JLabel lblMobileNumber = new JLabel("Mobile");
        lblMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMobileNumber.setBounds(542, 329, 228, 50);
        contentPane.add(lblMobileNumber);

        contact = new JTextField();
        contact.setFont(new Font("Tahoma", Font.PLAIN, 20));
        contact.setBounds(707, 320, 228, 50);
        contentPane.add(contact);
        contact.setColumns(10);

        password = new JPasswordField();
        password.setFont(new Font("Tahoma", Font.PLAIN, 20));
        password.setBounds(707, 235, 228, 50);
        contentPane.add(password);
        createRegisterButton();
    }

    private void createRegisterButton(){
        JButton registerButton = new JButton("Register");
        setVisible(true);
        contentPane.setSize(200,130);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = Registration.this.firstName.getText();
                String lastName = Registration.this.lastName.getText();
                String emailId = email.getText();
                String userName = username.getText();
                String mobileNumber = contact.getText();
                int len = mobileNumber.length();
                String password = Registration.this.password.getText();

                String userDisplayName = "" + firstName;
                userDisplayName += " \n";
                if (len != 10) {
                    JOptionPane.showMessageDialog(registerButton, "Enter a valid mobile number");
                }
                try {
                    String createUserQuery = "INSERT INTO account (firstName,lastName,userName,password,emailID,mobileNumber) values('" + firstName + "','" + lastName + "','" + userName + "','" +
                            password + "','" + emailId + "','" + mobileNumber + "')";
                    Statement createUserStatement = connection.createStatement();
                    int createUserResult = createUserStatement.executeUpdate(createUserQuery);
                    if (createUserResult == 0) {
                        JOptionPane.showMessageDialog(registerButton, "This is already exist");
                    } else {
                        JOptionPane.showMessageDialog(registerButton,
                                "Welcome, " + userDisplayName + "Your account is successfully created");
                    }
//                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        registerButton.setBounds(399, 447, 259, 74);
        contentPane.add(registerButton);
    }

}
