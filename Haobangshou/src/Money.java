public class Money {
	private int userId;
	private String userName;
	private double used;
	private double spended;
	private double money;

	Money(int userId, String userName, double used, double spended) {
		this.userId = userId;
		this.userName = userName;
		this.used = used;
		this.spended = spended;
	}

	Money(Money m) {
		this.userId = m.getId();
		this.userName = m.getName();
		this.used = m.getUsed();
		this.spended = m.getSpended();
	}
	public void countMoney(){
		this.money = this.used - this.spended;
	}
	public void setMoney(double money){
		this.money = money;
	}
	public double getMoney(){
		return this.money;
	}
    public void setUsed(double used){
    	this.used = used;
    }
    public void setSpended(double spended){
    	this.spended = spended;
    }
	public int getId() {
		return this.userId;
	}

	public String getName() {
		return this.userName;
	}

	public double getUsed() {
		return used;
	}

	public double getSpended() {
		return this.spended;
	}
}
