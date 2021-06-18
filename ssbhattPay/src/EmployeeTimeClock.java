import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main Dashboard
 */

public class EmployeeTimeClock extends JFrame {
    Registration registerObj;
    Login loginObj;
    SearchResult searchObj;
    static Connection connection;

    public EmployeeTimeClock() {
        initUI();
    }

    private JButton createRegistrationButton(){
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                registerObj = new Registration(connection);
            }
        });
        return registerButton;
    }

    private JButton createLoginButton(){
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                loginObj = new Login(connection);
            }
        });
        return loginButton;
    }

    private JButton createSearchButton(){
        JButton searchLogButton = new JButton("Search Logs of Employee");
        searchLogButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                searchObj = new SearchResult(connection);
                searchObj.createUI();
            }
        });
        return searchLogButton;
    }

    private void initUI() {
        JButton registerButton = createRegistrationButton();
        JButton loginButton = createLoginButton();
        JButton searchLogButton = createSearchButton();

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(searchLogButton);

        setTitle("Welcome to Employee Clock");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JComponent timeClockComponent) {
        Container pane = getContentPane();
        GroupLayout timeClockLayout = new GroupLayout(pane);
        pane.setLayout(timeClockLayout);

        timeClockLayout.setAutoCreateContainerGaps(true);

        timeClockLayout.setHorizontalGroup(timeClockLayout.createSequentialGroup()
                .addComponent(timeClockComponent)
        );

        timeClockLayout.setVerticalGroup(timeClockLayout.createSequentialGroup()
                .addComponent(timeClockComponent)
        );
    }

    public static void main(String[] args) throws SQLException {
        DBConnect db = new DBConnect();
        connection = db.createConnection();
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                EmployeeTimeClock ex = new EmployeeTimeClock();
                ex.setVisible(true);
            }
        });
//        connection.close();
    }
}