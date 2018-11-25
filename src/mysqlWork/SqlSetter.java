package mysqlWork;

import constants.SqlConnectionConst;

import java.sql.*;

public abstract class SqlSetter {
    private Connection connection;
    private Statement statement;
    protected ResultSet resultSet;

    protected void setRequest(String query){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(SqlConnectionConst.url, SqlConnectionConst.user, SqlConnectionConst.password);
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
