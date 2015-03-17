public class User {
	static String name;
	static String password;
	static int id;

	@SuppressWarnings("static-access")
	User(User user) {
		this.name = user.getName();
		this.password = user.getPassword();
		this.id = user.getId();
	}

	@SuppressWarnings("static-access")
	User(String n, String password, int id) {
		this.name = n;
		this.password = password;
		this.id = id;
	}

	@SuppressWarnings("static-access")
	public int getId(){
		return this.id;
	}
	@SuppressWarnings("static-access")
	public String getName() {
		return this.name;
	}

	@SuppressWarnings("static-access")
	public String getPassword() {
		return this.password;
	}
}
