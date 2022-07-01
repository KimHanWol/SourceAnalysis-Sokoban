package gameObject;

public class MovingWall extends Wall{
	
	int moveX1;
	int moveY1;
	
	int moveX2;
	int moveY2;

	public MovingWall(int x, int y) {
		super(x, y);
		this.moveX1 = x;
		this.moveY1 = y;
		// TODO Auto-generated constructor stub
	}
	
	public void setMovePoint(int x, int y) {
		this.moveX2 = x;
		this.moveY2 = y;
	}
	
	public void reservation() {
		if(this.getX() == moveX1 && this.getY() == moveY1) this.reservation(moveX2, moveY2);
		else this.reservation(moveX1, moveY1);
	}

}
