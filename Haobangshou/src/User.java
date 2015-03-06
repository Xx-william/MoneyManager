public class User {
	static String name = "Daisy";
	static String password = "123";
	static int userId = 3;

	@SuppressWarnings("static-access")
	User(User user) {
		this.name = user.getName();
		this.password = user.getPassword();
	}

	@SuppressWarnings("static-access")
	User(String n, String password) {
		this.name = n;
		this.password = password;
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
