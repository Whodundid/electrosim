package placeable;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public enum ObjectTypes {
	lOGICGATE("logicGate"),
	DIN("digitalInput"),
	DOUT("digitalOutput"),
	DIODE("diode"),
	CAPACITOR("capacitor"),
	RESISTOR("resistor"),
	TRANSISTOR("transistor"),
	TRANSFORMER("transformer"),
	AIN("analogInput"),
	AOUT("analogOutput"),
	FLIPFLOP("flipFlop"),
	LATCH("latch"),
	WIRE("wire"),
	DCLOCK("dClock"),
	TOBJECT("tempObject");
	
	private final String name;
	
	ObjectTypes(String name) {
		this.name = name;
	}
	
	public String getObjectType() {
		return name;
	}
}