
public class MinMax {

	public int min;
	public int max;
	
	public MinMax() {
		this(0,0);
	}
	
	public MinMax(int a_min, int a_max) {
		min = a_min;
		max = a_max;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getMin() {
		return min;
	}
	
	public void setMax(int newMax) {
		max = newMax;
	}
	
	public void setMin(int newMin) {
		min = newMin;
	}
}
