package xyz.polyomino.shiponym;

public class Pair {

	public String Left;
	public String Right;
	
	public Pair(String initial_left, String initial_right) {
		Left = initial_left;
		Right = initial_right;
	}
	
	public char PopLeft() {
		if(Left.length() < 1)
			throw new NullPointerException("Attempted to pop empty string.");
		char poped = Left.charAt(Left.length() - 1);
		Left = Left.substring(0, Left.length() - 2);
		return poped;
	}
	
	public char PopRight() {
		if(Right.length() < 1)
			throw new NullPointerException("Attempted to pop empty string.");		
		char poped = Right.charAt(Right.length() - 1);
		Right = Right.substring(0, Right.length() - 2);
		return poped;
	}
	
	public char TakeLeft() {
		if(Left.length() < 1)
			throw new NullPointerException("Attempted to take from empty string.");		
		char poped = Left.charAt(0);
		Left = Left.substring(1, Left.length() - 1);
		return poped;
	}
	
	public char TakeRight() {
		if(Right.length() < 1)
			throw new NullPointerException("Attempted to take from empty string.");		
		char poped = Right.charAt(0);
		Right = Right.substring(1, Right.length() - 1);
		return poped;
	}
	
	public int LengthRight() {
		return Right.length();
	}
	
	public int LengthLeft() {
		return Left.length();
	}
	
	public int Length() {
		return LengthRight() + LengthLeft();
	}
	
	public int AverageLength() {
		return Length() / 2;
	}
}
