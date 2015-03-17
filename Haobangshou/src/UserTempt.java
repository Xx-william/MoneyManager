public class UserTempt {
	 private String name;
	 private String password;
	 private int id;

	UserTempt(UserTempt user) {
		this.name = user.getName();
		this.password = user.getPassword();
		this.id = user.getId();
	}


	UserTempt(String n, String password, int id) {
		this.name = n;
		this.password = password;
		this.id = id;
	}


	public int getId(){
		return this.id;
	}
	public String getName() {
		return this.name;
	}


	public String getPassword() {
		return this.password;
	}
}
