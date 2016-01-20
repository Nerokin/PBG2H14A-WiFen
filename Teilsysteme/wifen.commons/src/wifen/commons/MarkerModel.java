package wifen.commons;



/**
 * Put description here
 *
 * @author unkown
 *
 */
public class MarkerModel implements java.io.Serializable {

	private MarkerType type;
	private double posx;
	private double posy;
	private String desc;

	// Constructors
	/**
	 * Put description here
	 *
	 * @param x
	 * @param y
	 * @param mt
	 * @param d
	 */
	public MarkerModel(double x, double y, MarkerType mt, String d) {
		this.type = mt;
		this.posx = x;
		this.posy = y;
		this.desc = d;
	}

	// Methods



	// Getters & Setters
	public MarkerType getType() {
		return type;
	}

	public void setType(MarkerType type) {
		this.type = type;
	}

	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


}
