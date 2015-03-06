public class Money {
	private int userId;
	private String userName;
	private double used;
	private double spended;

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
