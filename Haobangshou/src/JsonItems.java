import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonItems {
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Money> moneys = new ArrayList<Money>();
	private ArrayList<LogInfo> logInfos = new ArrayList<LogInfo>();
	private ArrayList<UserTempt> users = new ArrayList<UserTempt>();

	private String str = "";

	JsonItems(String str) {
		this.str = str;
	}
	public String jiexiXinyonghu() throws JSONException{
		JSONArray ja = new JSONArray(str);
    	JSONObject jo = new JSONObject();
    	String panduan = "";
    	jo = ja.getJSONObject(0);
    	panduan = jo.getString("yonghu");
    	if(panduan.equals("true")){
    		User.id = jo.getInt("id");
    	}
    	return panduan;
	}
    public String jiexiDenglu() throws JSONException{
    	JSONArray ja = new JSONArray(str);
    	JSONObject jo = new JSONObject();
    	String panduan = "";
    	jo = ja.getJSONObject(0);
    	panduan = jo.getString("denglu");
    	if(panduan.equals("true")){
    		User.id = jo.getInt("id");
    	}
    	return panduan;
    }
    public ArrayList<UserTempt> jiexiUsers() throws JSONException{       	
    	    JSONArray ja = new JSONArray(str);
		JSONObject jo = new JSONObject();
		String userName = "";
		String userPassWords = "";
		int idUser;
		UserTempt user;
		int length = ja.length();
		for(int i =0 ;i<length;i++){
			jo = ja.getJSONObject(i);
			userName = jo.getString("userName");
			userPassWords = jo.getString("userPassWords");
			idUser = jo.getInt("idUser");
			user = new UserTempt(userName,userPassWords,idUser);
			users.add(user);
		}
		return users;
		}
    	    	
	public ArrayList<Item> jiexiShopping() throws JSONException {

		JSONArray ja = new JSONArray(str);
		JSONObject jo = new JSONObject();
		String name = "";
		String date = "";
		String comment = "";
		double amount = 0;
		String participant = "";
		int id = 0;
		Item item;
		for (int i = 0; i < ja.length(); i++) {
			jo = ja.getJSONObject(i);
			name = jo.getString("name");
			date = jo.getString("date");
			amount = jo.getDouble("amount");
			participant = jo.getString("participant");
			id = jo.getInt("id");
			comment = jo.getString("comment");
			item = new Item(name, amount, date, participant, id, comment);
			items.add(item);

		}
		return items;

	}

	public ArrayList<Money> jiexiMoney() throws JSONException {

		JSONArray ja = new JSONArray(str);
		JSONObject jo = new JSONObject();
		int userId = 0;
		String name = "";
		double used = 0;
		double spended = 0;
		Money money;
		for (int i = 0; i < ja.length(); i++) {
			jo = ja.getJSONObject(i);
			userId = jo.getInt("userId");
			name = jo.getString("userName");
			used = jo.getDouble("used");
			spended = jo.getDouble("spended");
			money = new Money(userId, name, used, spended);
			moneys.add(money);
		}
		return moneys;
	}

	public ArrayList<LogInfo> jiexiLogInfo() throws JSONException {
		JSONArray ja = new JSONArray(str);
		JSONObject jo = new JSONObject();
		int idUser = 0;
		String userName = "";
		String date = "";
		LogInfo logInfo;
		for (int i = 0; i < 3; i++) {
			jo = ja.getJSONObject(i);
			idUser = jo.getInt("idUser");
			userName = jo.getString("userName");
			date = jo.getString("logTime");
			logInfo = new LogInfo(idUser, userName, date);
			logInfos.add(logInfo);
		}
		return logInfos;
	}
}
 