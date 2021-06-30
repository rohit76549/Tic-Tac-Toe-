package tictactoeminimax;



import tictactoeminimax.TicTacToe;
import tictactoeminimax.minimax.points1;

public class minimax 
{
	static class Move
	{
	    int row, col;
	};
	static class points1
	{
		static int points1;
		static int points2;
	}; 
	public static void main(String[] args)
	{
	    TicTacToe tictactoe = new TicTacToe();
	    points1.points1=TicTacToe.pointsx;
	    points1.points2=TicTacToe.pointso;  
	}
}
