package placeable;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public enum LatchTypes {
	D("dl"), 
	T("tl"), 
	JK("jkl"), 
	SR("srl");
	
	private final String name;
	
	LatchTypes(String name) {
		this.name = name;
	}
	
	public String getLatchName() {
		return name;
	}
}