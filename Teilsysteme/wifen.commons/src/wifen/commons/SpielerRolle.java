package wifen.commons;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public enum SpielerRolle
{
	ADMIN {
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Administrator";
		}
	},
	MODERATOR{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Moderator";
		}
	},
	PLAYER{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Spieler";
		}
	},
	SPECTATOR{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Zuschauer";
		}
	};
}


