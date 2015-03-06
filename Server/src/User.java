public class User {
	private String name;
	private String password;

	User(User user) {
		this.name = user.getName();
		this.password = user.getPassword();
	}

	User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}
}
