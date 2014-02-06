package items;

public final class Book implements Item {
	private static final long serialVersionUID = -2747647210830808770L;

	public Book() {
	}

	@Override
	public String getID() {
		return "book";
	}

	@Override
	public void merge(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
