package wifen.commons;


/**
 * Put description here
 * 
 * @author unkown
 *
 */
public enum GridType {
	NONE{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Keins";
		}
	},
	SQUARE{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Quadratisch";
		}
	},
	HEX{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Sechseckig";
		}
	};
}
