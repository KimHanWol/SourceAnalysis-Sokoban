package util;

public class Direction implements Position{
	private int x;
	private int y;
	
	private char direction;
	
	Direction(int x, int y){
		setDirection(x, y);
	}
	
	public Direction(char direction){
		setDirection(direction);
	}
	
	public char getDirection() {
		return direction;
	}
	
	public void setDirection(char direction) {
		this.direction = direction;
		switch(direction) {
		case 'l':
			x = -1;
			break;
		case 'r':
			x = 1;
			break;
		case 't':
			y = -1;
			break;
		case 'b':
			y = 1;
			break;
			default:
				System.out.println("Direction;setDirection:direction 값 오류");
				break;
		}
	}
	public void setDirection(int x, int y) {
		this.x = x;
		this.y = y;
		
		if(x>0 && y == 0) direction = 'l';
		else if(x<0 && y == 0) direction = 'r';
		else if(y>0 && x == 0) direction = 't';
		else if(y<0 && x == 0) direction = 'b';
		else System.out.println("Direction:x, y 값 오류");
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
}
