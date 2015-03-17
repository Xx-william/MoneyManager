public class User {
	private String name;
	private String password;
	private int id;

	User(User user) {
		this.name = user.getName();
		this.password = user.getPassword();
		this.id = user.getId();
	}
	
	User(String name, String password, int id) {
		this.name = name;
		this.password = password;
		this.id = id;
	}
    public void setPassword(String password){
    	this.password = password;
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
