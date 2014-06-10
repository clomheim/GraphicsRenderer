/**
 * 
 * @author Doug
 *
 */
public class MyPoint {
	
	/**
	 * 
	 */
	private int my_x;
	
	/**
	 * 
	 */
	private int my_y;
	
	/**
	 * 
	 */
	public MyPoint() {
		this(0,0);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public MyPoint(int x, int y) {
		my_x = x;
		my_y = y;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getX() {
		return my_x;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getY() {
		return my_y;
	}

}
