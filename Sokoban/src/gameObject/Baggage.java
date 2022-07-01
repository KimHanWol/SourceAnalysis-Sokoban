package gameObject;

public class Baggage extends GameObject{

	public Baggage(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	private boolean isOnArea;

	public boolean getIsOnArea() {
		return isOnArea;
	}

	public void setIsOnArea(boolean isOnArea) {
		this.isOnArea = isOnArea;
	}

	@Override
	public boolean interrupt(GameObject gameObject) {
		// TODO Auto-generated method stub
		if(gameObject instanceof Player) {
			return true;
		}
		return false;
	}

}