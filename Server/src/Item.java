public class Item {
	private String name;
	private double amount;
	private String date;
	private String participant;
	private int id;
	private String comment;

	Item(String name, double amount, String date, String participant, int id,String comment) {
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.participant = participant;
		this.id = id;
		this.comment = comment;
	}

	Item() {

	}

	Item(Item i) {
		this.name = i.getName();
		this.amount = i.getAmount();
		this.date = i.getDate();
		this.participant = i.getParticipant();
		this.id = i.getId();
		this.comment = i.getComment();
	}
  public String getComment(){
	  return this.comment;
  }
  public void setComment(String comment){
	  this.comment = comment;
  }
	public String getName() {
		return this.name;
	}
    
	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getParticipant() {
		return this.participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public void addParticipant(String participant) {
		this.participant += participant;
	}

	public int getId() {
		return this.id;
	}

}
