import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBUpdater {
	final String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
	final String userName = "root";
	final String passWords = "wx520333";

	private String sql = "";

	DBUpdater(String sql) {
		this.sql = sql;
	}

	public void update() {
		Connection con = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			con = DriverManager.getConnection(url, userName, passWords);

			Statement st = con.createStatement();
			
			st.executeUpdate(sql);

			con.close();
		} catch (Exception exce) {
			System.err.println("Exception: " + exce.getMessage());
		}
	}
}
