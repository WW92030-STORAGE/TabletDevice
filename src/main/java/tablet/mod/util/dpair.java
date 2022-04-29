package tablet.mod.util;

public class dpair implements Comparable<dpair>{
	public double x;
	public double y;
	public dpair(double a, double b) {
		x = a;
		y = b;
	}
	
	public void set(double a, double b) {
		x = a;
		y = b;
	}
	
	public void set(dpair p) {
		x = p.x;
		y = p.y;
	}
	
	public dpair clone() {
		return new dpair(this.x, this.y);
	}
	
	public dpair invert() {
		return new dpair(this.y, this.x);
	}
	
	public int compareTo(dpair other) {
		Double px = this.x;
		Double py = this.y;
		Double ox = other.x;
		Double oy = other.y;
		if (!px.equals(ox)) return px.compareTo(ox);
		if (!py.equals(oy)) return py.compareTo(oy);
		return 0;
	}
	
	public boolean equals(dpair other) {
		return this.compareTo(other) == 0;
	}
	
	public dpair min(dpair q) {
		if (this.compareTo(q) > 0) return q.clone();
		return this.clone();
	}
	
	public String toString() {
		return "[" + x + " " + y + "]";
	}
}