package tablet.mod.util;

public class pair implements Comparable<pair>{
	public int x;
	public int y;
	public pair(int a, int b) {
		x = a;
		y = b;
	}
	
	public void set(int a, int b) {
		x = a;
		y = b;
	}
	
	public void set(pair p) {
		x = p.x;
		y = p.y;
	}
	
	public pair clone() {
		return new pair(this.x, this.y);
	}
	
	public pair invert() {
		return new pair(this.y, this.x);
	}
	
	public int compareTo(pair other) {
		Integer px = this.x;
		Integer py = this.y;
		Integer ox = other.x;
		Integer oy = other.y;
		if (!px.equals(ox)) return px.compareTo(ox);
		if (!py.equals(oy)) return py.compareTo(oy);
		return 0;
	}
	
	public boolean equals(pair other) {
		return this.compareTo(other) == 0;
	}
	
	public pair min(pair q) {
		if (this.compareTo(q) > 0) return q.clone();
		return this.clone();
	}
	
	public String toString() {
		return "[" + x + " " + y + "]";
	}
}