
import client.ConnectDB;
import com.mysql.jdbc.Connection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Natan
 */
public class Test {

    public static void main(String args[]){
        ConnectDB c = new ConnectDB();
        Connection con = (Connection) c.getConnection();
        con.getMaxRows();
    }

}
