public class LogInfo {
	private int idUser;
	private String userName;
	private String logTime;

	LogInfo(LogInfo logInfo) {
		this.idUser = logInfo.getId();
		this.userName = logInfo.getName();
		this.logTime = logInfo.getTime();
	}

	LogInfo(int idUser, String userName, String logTime) {
		this.idUser = idUser;
		this.userName = userName;
		this.logTime = logTime;
	}

	public int getId() {
		return this.idUser;
	}

	public String getName() {
		return this.userName;
	}

	public String getTime() {
		return this.logTime.toString();
	}

	public void setTime(String logTime) {
		this.logTime = logTime;
	}
}
