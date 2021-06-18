import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

/**
 * Search Employee Logs based on unique employeeID
 */

public class SearchResult implements ActionListener{
    JFrame frame, frame1;
    JButton button;
    JTextField textbox;

    Connection connection;
    String[] columnNames = {"SerialNo.","ClockInTime", "ClockOutTime"};

    public SearchResult(Connection connection) {
        this.connection = connection;
    }

    public void createUI()
    {
        frame = new JFrame("Database Search Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        createSearchForm();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        button = (JButton)actionEvent.getSource();
        System.out.println("Showing Table Data.......");
        showTableData();
    }

    public void createSearchForm(){
        textbox = new JTextField();
        textbox.setBounds(120,30,150,20);
        JLabel label = new JLabel("Enter employeeID no");
        label.setBounds(10, 30, 100, 20);
        button = new JButton("search");
        button.setBounds(120,130,150,20);
        button.addActionListener(this);

        frame.add(textbox);
        frame.add(label);
        frame.add(button);
        frame.setSize(500, 400);
    }

    public void showTableData()
    {
        frame1 = new JFrame("Search for Employee Time Logs");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(new BorderLayout());
        String textvalue = textbox.getText();
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
        frame1.setVisible(true);
        frame1.setSize(400,300);
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

        String cli = "";
        String clo = "";
        int recordCounter = 1;

        while(rs.next())
        {
            cli = rs.getString("ClockInTime");
            clo = rs.getString("ClockOutTIme");
            model.addRow(new Object[]{recordCounter, cli, clo});
            recordCounter++;
        }
        if(recordCounter == 1)
        {
            JOptionPane.showMessageDialog(null, "No Record Found","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
    }
}