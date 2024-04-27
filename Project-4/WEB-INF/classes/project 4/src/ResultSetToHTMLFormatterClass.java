import java.sql.*;

public class ResultSetToHTMLFormatterClass {

    public static synchronized String getHtmlRows(ResultSet results) throws SQLException {
        StringBuilder htmlTable = new StringBuilder();

        htmlTable.append("<table border=\"1\">\n");

        // Get metadata of the ResultSet
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Create table header
        htmlTable.append("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            htmlTable.append("<th>").append(metaData.getColumnLabel(i)).append("</th>");
        }
        htmlTable.append("</tr>\n");

        // Iterate over the ResultSet and add rows to the table
        while (results.next()) {
            htmlTable.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                htmlTable.append("<td>").append(results.getString(i)).append("</td>");
            }
            htmlTable.append("</tr>\n");
        }

        htmlTable.append("</table>");

        return htmlTable.toString();
    }

}
