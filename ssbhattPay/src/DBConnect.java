import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    public static void main(String[] args)  {
            DBConnect obj = new DBConnect();
            obj.createConnection();
    }
    public Connection createConnection(){
        try {
            String myUrl = "jdbc:mysql://localhost:3306/empSchema";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(myUrl, "root", "Shashwat_1908");
            System.out.println("Connected to Database");
            return connection;
        }catch (ClassNotFoundException ex){
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE,null,ex);
        }

        catch(SQLException ex){
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE,null,ex);
            ex.printStackTrace();
        }
        return null;
    }


}
