package placeable;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public enum GateTypes {
	OR("or"), 
	NOR("nor"), 
	AND("and"), 
	NAND("nand"), 
	XOR("xor"), 
	XNOR("xnor"), 
	BUFFER("buffer"), 
	NOT("not");
	
	private final String name;
	
	GateTypes(String name) {
		this.name = name;
	}
	
	public String getGateName() {
		return name;
	}
}