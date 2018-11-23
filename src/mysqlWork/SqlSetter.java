package mysqlWork;

import java.sql.*;

public abstract class SqlSetter {
    private Connection connection;
    private Statement statement;
    protected ResultSet resultSet;

    protected void getRequest(String query){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(ConnectionConst.url, ConnectionConst.user, ConnectionConst.password);
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (ClassNotFoundException cnfEx) {
            cnfEx.printStackTrace();
        } finally {
            //close connection ,statement and resultSet here
            try {
                connection.close();
            } catch (SQLException se) {
                /*can't do anything */
            }
            try {
                statement.close();
            } catch (SQLException se) {
                /*can't do anything */
            }
            try {
                resultSet.close();
            } catch (SQLException se) {
                /*can't do anything */
            }
        }
    }
}
