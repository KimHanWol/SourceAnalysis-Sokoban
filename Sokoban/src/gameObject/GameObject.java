package gameObject;

import util.Direction;

public abstract class GameObject implements ActObject{

    private int x;
    private int y;
    
    private int reservationX;
    private int reservationY;
    
//    private Image image;

    public GameObject(int x, int y) {
        this.x = this.reservationX = x;
        this.y = this.reservationY = y;
    }
    
    public void reservation(Direction dir) {
    	reservation(this.x, this.y);
    	switch(dir.getDirection()) {
    	case 'r':
    		reservationX = x + 1;
    		break;
    	case 'l':
    		reservationX = x - 1;
    		break;
    	case 't':
    		reservationY = y - 1;
    		break;
    	case 'b':
    		reservationY = y + 1;
    		break;
    	}
    }
    
    protected void reservation(int x, int y) {
    	this.reservationX = x;
    	this.reservationY = y;
    }
    
    public int getReservationX() {
    	return reservationX;
    }
    
    public int getReservationY() {
    	return reservationY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
    
    private void move() {
    	this.x = reservationX;
    	this.y = reservationY;
    }

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		move();
	}

	@Override
	public boolean interrupt(GameObject gameObject) {
		// TODO Auto-generated method stub
		return false;
	}
}
