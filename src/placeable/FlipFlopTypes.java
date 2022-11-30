package placeable;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public enum FlipFlopTypes {
	D("dff"), 
	T("tff"), 
	JK("jkff");
	
	private final String name;
	
	FlipFlopTypes(String name) {
		this.name = name;
	}
	
	public String getFFName() {
		return name;
	}
}