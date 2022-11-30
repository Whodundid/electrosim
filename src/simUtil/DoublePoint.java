package simUtil;

public class DoublePoint {
	
	public double x, y;
	
	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	 public void move(double x, double y) {
		 this.x = x;
		 this.y = y;
	 }
	 
	 public void translate(int dx, int dy) {
		 this.x += dx;
		 this.y += dy;
	 }
	 
	 public double getX() {
		 return this.x;
	 }
	 
	 public double getY() {
		 return this.y;
	 }
	 
	 public void setX(double x) {
		 this.x = x;
	 }
	 
	 public void setY(double y) {
		 this.y = y;
	 }
	 
	 public int ceilX() {
		 if (this.x > 0)
			 return (int) Math.ceil(x);
		 return (int) Math.floor(x);
	 }
	 
	 public int ceilY() {
		 if (this.y > 0)
			 return (int) Math.ceil(y);
		 return (int) Math.floor(y);
	 }
	 
	 public int floorX() {
		 if (this.x > 0)
			 return (int) Math.floor(x);
		 return (int) Math.ceil(x);
	 }
	 
	 public int floorY() {
		 if (this.y > 0)
			 return (int) Math.floor(y);
		 return (int) Math.ceil(y);
	 }
	 
	 public static DoublePoint getBigger(DoublePoint point1, DoublePoint point2) {
		 if (point1.getX() > point2.getX() && point1.getY() > point2.getY())
			 return point1;
		 else
			 return point2;
	 }
	 
	 public static DoublePoint getSmaller(DoublePoint point1, DoublePoint point2) {
		 if (point1.getX() < point2.getX() && point1.getY() < point2.getY())
			 return point1;
		 else
			 return point2;
	 }
	 
	 public static DoublePoint getBiggerX(DoublePoint point1, DoublePoint point2) {
		 if (point1.getX() > point2.getX())
			 return point1;
		 else
			 return point2;
	 }
	 
	 public static DoublePoint getBiggerY(DoublePoint point1, DoublePoint point2) {
		 if (point1.getY() > point2.getY())
			 return point1;
		 else
			 return point2;
	 }
	 
	 public static DoublePoint getSmallerX(DoublePoint point1, DoublePoint point2) {
		 if (point1.getX() > point2.getX())
			 return point2;
		 else
			 return point1;
	 }
	 
	 public static DoublePoint getSmallerY(DoublePoint point1, DoublePoint point2) {
		 if (point1.getY() > point2.getY())
			 return point2;
		 else
			 return point1;
	 }
}
