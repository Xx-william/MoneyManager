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
	private ArrayList<User> users = new ArrayList<User>();
	private String sql = "";

	DBSearch(String sql) {
		this.sql = sql;
	}
    public Boolean ifUserExist(){
    	   Boolean b = false;
         ArrayList<User> users = SearchUsers();
		int size = users.size();
		for(int i =0 ;i<size;i++){
			if(users.get(i).getName().equalsIgnoreCase(sql)){
				b = true;
			}
		}
		return b;
    }
	public ArrayList<User> SearchUsers(){
		Connection con = null;
		try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();			
		con = DriverManager.getConnection(url, userName, passWords);
		Statement st = con.createStatement();			
		ResultSet rs = st.executeQuery("SELECT * FROM Haobanghsou.User");	
		while(rs.next()){
			user = new User(rs.getString("userName"),
				rs.getString("userPassWords"),rs.getInt("idUser"));
		users.add(user);
		}
		con.close();		
		}
		catch(Exception e){
			System.err.println("Exception: " + e.getMessage());
		}
		return users;
	}
	public Object[] Login() {
		boolean b = false;
		int id=0;
		Connection con = null;
		try {
			String name1;
			String password1;
			
			int p = sql.indexOf("-");
			name1 = sql.substring(0, p);
			password1 = sql.substring(p+1, sql.length());
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();			
			con = DriverManager.getConnection(url, userName, passWords);
			Statement st = con.createStatement();			
			ResultSet rs = st.executeQuery("SELECT * FROM Haobanghsou.User");	
			while(rs.next()){
				user = new User(rs.getString("userName"),
					rs.getString("userPassWords"),rs.getInt("idUser"));
			users.add(user);
			}
			con.close();			
			for(int i = 0 ;i<users.size();i++){
				String temName = users.get(i).getName();
				if(temName.equalsIgnoreCase(name1)){
					String temPass = users.get(i).getPassword();
					if(temPass.equals(password1)){
						b = true;
						id = users.get(i).getId();
						break;
					}
				}
			}
		
		} catch (Exception exce) {
			System.err.println("Exception: " + exce.getMessage());
		}
		Object[] o = new Object[2];
		o[0] = b;
		o[1] = id;
		return o;
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
						Integer.parseInt(rs.getString("idShopping")),rs.getString("comment"));
				dataItem.add(item);
			}
			
			con.close();
		} catch (Exception exce) {
			System.err.println("Exception: " + exce.getMessage());
		}
		return dataItem;
	}
}
