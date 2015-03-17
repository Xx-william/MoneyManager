import java.util.ArrayList;

public class JsonItems {
	private Item item;
	private Money money;
	private LogInfo logInfo;
	private String itemsStr = "";
	private String moneysStr = "";
	private String logInfoStr = "";
	private String userStr = "";
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Money> moneys = new ArrayList<Money>();
	private ArrayList<LogInfo> logInfos = new ArrayList<LogInfo>();
	private ArrayList<User> users = new ArrayList<User>();
	private User user;

	JsonItems() {

	}

	public void setUser(User user) {
		this.user = new User(user);
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public void setMoneys(ArrayList<Money> moneys) {
		this.moneys = moneys;
	}

	public void setLogInfo(ArrayList<LogInfo> logInfos) {
		this.logInfos = logInfos;
	}
    public void setUsers(ArrayList<User> users){
    	this.users = users;
    }
	public String toJsonStrUser() {

		userStr = "[";
		userStr += "{'userName':'" + user.getName() + "',";
		userStr += "'userPassWords':'" + user.getPassword() + "',";
		userStr += "'idUser':" +user.getId()+"}";
		userStr += "]";
		return userStr;
	}
	
 public String toJsonStrUsers(){
	 int size = users.size();
	 userStr = "[";
	 for(int i=0;i<size;i++){
		    user = new User(users.get(i));
			userStr += "{'userName':'" + user.getName() + "',";
			userStr += "'userPassWords':'" + user.getPassword() + "',";
			userStr += "'idUser':" + user.getId() + "},";
	 }
	 if (size != 0) {
			userStr = userStr.substring(0, userStr.length() - 1);
		}
	 userStr += "]";
	 return userStr;
 }
	public String toJsonStrLogInfo() {
		int size = logInfos.size();
		logInfoStr = "[";
		for (int i = 0; i < size; i++) {
			logInfo = new LogInfo(logInfos.get(i));
			logInfoStr += "{'idUser':" + logInfo.getId() + ",";
			logInfoStr += "'userName':'" + logInfo.getName() + "',";
			logInfoStr += "'logTime':'" + logInfo.getTime() + "'},";
		}
		if (size != 0) {
			logInfoStr = logInfoStr.substring(0, logInfoStr.length() - 1);
		}
		logInfoStr += "]";
		return logInfoStr;
	}

	public String toJsonStrItem() {
		int size = items.size();
		itemsStr = "[";
		for (int i = 0; i < size; i++) {
			item = new Item(items.get(i));
			itemsStr += "{'name':'" + item.getName() + "',";
			itemsStr += "'amount':" + item.getAmount() + ",";
			itemsStr += "'date':'" + item.getDate() + "',";
			itemsStr += "'participant':'" + item.getParticipant() + "',";
			itemsStr += "'id':" + item.getId() + ",";
			itemsStr += "'comment':'" + item.getComment() + "'},";
		}
		if (size != 0) {
			itemsStr = itemsStr.substring(0, itemsStr.length() - 1);
		}
		itemsStr += "]";
		return itemsStr;
	}

	public String toJsonStrMoney() {
		int size = moneys.size();
		moneysStr = "[";
		for (int i = 0; i < size; i++) {
			money = new Money(moneys.get(i));
			moneysStr += "{'userId':" + money.getId() + ",";
			moneysStr += "'userName':'" + money.getName() + "',";
			moneysStr += "'used':" + money.getUsed() + ",";
			moneysStr += "'spended':" + money.getSpended() + "},";
		}
		if (size != 0) {
			moneysStr = moneysStr.substring(0, moneysStr.length() - 1);
		}
		moneysStr += "]";
		return moneysStr;
	}
}
