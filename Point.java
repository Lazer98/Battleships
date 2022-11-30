package ships;

public class Point {

	private int i;
	private int j;
	
	public Point(int i,int j) {
		this.i=i;
		this.j=j;
	}
	
	@Override
	public String toString() {
		return "Point [i=" + i + ", j=" + j + "]";
	}

	public int getI() {
		return i;
	}
	public int getJ () {
		return j;
	}
}
