import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

/**
 * Search Employee Logs based on unique employeeID
 */

public class SearchResult implements ActionListener{
    JFrame frameToSetUI, frameToSetResultUI;
    JButton button;
    JTextField textBox;

    Connection connection;
    String[] columnNames = {"SerialNo.","ClockInTime", "ClockOutTime"};

    public SearchResult(Connection connection) {
        this.connection = connection;
    }

    public void createUI()
    {
        frameToSetUI = new JFrame("Database Search Result");
        frameToSetUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameToSetUI.setLayout(null);
        createSearchForm();
        frameToSetUI.setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        button = (JButton)actionEvent.getSource();
        System.out.println("Showing Table Data.......");
        showTableData();
    }

    public void createSearchForm(){
        textBox = new JTextField();
        textBox.setBounds(120,30,150,20);
        JLabel label = new JLabel("Enter employeeID no");
        label.setBounds(10, 30, 100, 20);
        button = new JButton("search");
        button.setBounds(120,130,150,20);
        button.addActionListener(this);

        frameToSetUI.add(textBox);
        frameToSetUI.add(label);
        frameToSetUI.add(button);
        frameToSetUI.setSize(500, 400);
    }

    public void showTableData()
    {
        frameToSetResultUI = new JFrame("Search for Employee Time Logs");
        frameToSetResultUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameToSetResultUI.setLayout(new BorderLayout());
        String textvalue = textBox.getText();
        try {
            String sql = "select * from clockDetails where employeeID = " + textvalue;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            displayEmployeeData(rs);
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        frameToSetResultUI.setVisible(true);
        frameToSetResultUI.setSize(400,300);
    }

    private void displayEmployeeData(ResultSet rs) throws SQLException {
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        String clockInEntry = "";
        String clockOutEntry = "";
        int recordCounter = 1;

        while(rs.next())
        {
            clockInEntry = rs.getString("ClockInTime");
            clockOutEntry = rs.getString("ClockOutTIme");
            model.addRow(new Object[]{recordCounter, clockInEntry, clockOutEntry});
            recordCounter++;
        }
        if(recordCounter == 1)
        {
            JOptionPane.showMessageDialog(null, "No Record Found","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        frameToSetResultUI.add(scroll);
    }
}