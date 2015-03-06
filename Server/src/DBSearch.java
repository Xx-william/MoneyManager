import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBSearch {
	final String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
	final String userName = "root";
	final String passWords = "wx520333";
	private ArrayList<Item> dataItem = new ArrayList<Item>();
	private ArrayList<Money> dataMoney = new ArrayList<Money>();
	private ArrayList<LogInfo> logInfos = new ArrayList<LogInfo>();
	private User user;
	private String sql = "";

	DBSearch(String sql) {
		this.sql = sql;
	}

	public User SearchUser() {
		Connection con = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			con = DriverManager.getConnection(url, userName, passWords);

			

			
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			user = new User(rs.getString("userName"),
					rs.getString("userPassWords"));
			
			con.close();
		} catch (Exception exce) {
			System.err.println("Exception: " + exce.getMessage());
		}
		return user;
	}

	public ArrayList<LogInfo> SearchLogInfo() {
		Connection con = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			con = DriverManager.getConnection(url, userName, passWords);

			

			
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				LogInfo logInfo = new LogInfo(rs.getInt("idUser"),
						rs.getString("userName"), rs.getDate("logTime")
								.toString());
				logInfos.add(logInfo);
			}
			
			con.close();
		} catch (Exception exce) {
			System.err.println("Exception: " + exce.getMessage());
		}
		return logInfos;
	}

	public ArrayList<Money> SearchMoney() {
		Connection con = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			con = DriverManager.getConnection(url, userName, passWords);

		

		
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				Money money = new Money(rs.getInt("userId"),
						rs.getString("userName"), rs.getDouble("used"),
						rs.getDouble("spended"));
				dataMoney.add(money);
			}
			
			con.close();
		} catch (Exception exce) {
			System.err.println("Exception: " + exce.getMessage());
		}
		return dataMoney;
	}

	public ArrayList<Item> SearchItem() {
		Connection con = null;
		try {
		
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			con = DriverManager.getConnection(url, userName, passWords);


			
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				Item item = new Item(rs.getString("userName"),
						Double.parseDouble(rs.getString("amount")),
						rs.getString("date"), rs.getString("participant"),
						Integer.parseInt(rs.getString("idShopping")));
				dataItem.add(item);
			}
			
			con.close();
		} catch (Exception exce) {
			System.err.println("Exception: " + exce.getMessage());
		}
		return dataItem;
	}
}
