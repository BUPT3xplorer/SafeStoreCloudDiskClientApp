import com.sun.jdi.connect.Connector;

import java.sql.*;

public class DataBaseConn {
    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String dbURL = "jdbc:mysql://localhost:3306/clouddisk?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String dbUser = "dbadmin";
    private static final String dbPwd = "dbadmin";
    public Statement stmt;
    public ResultSet rs;
    public java.sql.Connection conn;

    public DataBaseConn() throws SQLException, ClassNotFoundException {
        Class.forName(jdbcDriver);
        conn = DriverManager.getConnection(dbURL, dbUser, dbPwd);
        stmt = conn.createStatement();
    }

    public void DataBaseClose() throws SQLException {
        rs.close();
        stmt.close();
        conn.close();
    }

}
