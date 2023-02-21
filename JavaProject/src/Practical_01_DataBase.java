import java.sql.*;

class Data{
    int paperCode;
    String subName;
    String paperDetail;
    String url;
    Data(int paperCode,String subName,String paperDetail,String url){
        this.paperCode
                = paperCode
        ;
        this.subName = subName; this.paperDetail = paperDetail;
        this.url = url;
    }
}
public class Practical_01_DataBase {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/db";
    static final String USER = "root";
    static final String PASS = "root";




    public static Connection getConnection() throws Exception {
        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    static void createTable(Statement stmt){
        try{
            String sql = "CREATE TABLE Papers " +
                    "(paperCode" +
                    " INTEGER not NULL, " +
                    " subName VARCHAR(255), " +
                    " paperDetail VARCHAR(255), " +
                    " url VARCHAR(255), " +
                    " PRIMARY KEY (paperCode" +
                    "))";
            stmt.executeUpdate(sql);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    static void insertPaperDetail(Statement stmt,Data newData){
        try {
            String sql = "INSERT INTO Papers (paperCode" +
                    ", subName, paperDetail, url) " +
                    "VALUES (" + newData.paperCode
                    +
                    ", '" + newData.subName +
                    "', '" + newData.paperDetail +
                    "', '" + newData.url + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static ResultSet searchData(Statement stmt){
        try{
            String sql = "SELECT paperCode" +
                    ", subName, paperDetail, url FROM Papers";
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    static void updatePaperDetail(Statement stmt, Data updatedData){
        try {
            String sql = "UPDATE Papers " +
                    "SET subName = '" + updatedData.subName + "', " +
                    "paperDetail = '" + updatedData.paperDetail + "', " +
                    "url = '" + updatedData.url + "' " +
                    "WHERE paperCode" +
                    " = " + updatedData.paperCode
                    ;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void deletePaperDetail(Statement stmt, int paperCode){
        try {
            String sql = "DELETE FROM Papers WHERE paperCode" +
                    " = " + paperCode
                    ;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static ResultSet searchData(Statement stmt, String searchString){
        try{
            String sql = "SELECT subID, subName, paperDetail, url FROM Papers " +
                    "WHERE subName LIKE '%" + searchString + "%' OR paperDetail LIKE '%" + searchString + "%'";
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    static String getSearchResult(ResultSet rs) throws Exception {
        StringBuilder searchResult = new StringBuilder();
        while (rs.next()) {
            searchResult.append(rs.getString("subName")).append(" | ");
            searchResult.append(rs.getString("paperDetail")).append(" | ");
            searchResult.append(rs.getString("url")).append("\n");
        }
        return searchResult.toString();
    }

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {

            conn = getConnection();
            stmt = conn.createStatement();

            // Creating table
//          createTable(stmt);

            //Insert data
//        insertPaperDetail(stmt,new Data(3160714,"DATA MINING","2019_paper","http://example.com/paper"));

            // Updating paper detail
//            updatePaperDetail(stmt, new Data(3160714,"DATA MINING","2020_paper","http://example.com/paper"));

            // Deleting paper detail
//            deletePaperDetail(stmt, 3160714);

            //search from database
            ResultSet rs = searchData(stmt);
            // Displaying data
            System.out.println(getSearchResult(rs));

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
