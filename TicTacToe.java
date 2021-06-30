package tictactoeminimax;



import tictactoeminimax.minimax.Move;
import tictactoeminimax.minimax.points1;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

 public class TicTacToe implements ActionListener
 {

	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	static JLabel textfield = new JLabel();
	static JButton[][] buttons = new JButton[3][3];
	static JLabel newgame=new JLabel();
	static int pointsx;
	static int pointso;
	boolean playerX_turn;

	TicTacToe(){
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free",Font.BOLD,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Tic-Tac-Toe");
		textfield.setOpaque(true);
		
		title_panel.setLayout(new GridLayout(0,2));
		title_panel.setBounds(0,0,800,100);
		
		button_panel.setLayout(new GridLayout(3,3));
		button_panel.setBackground(new Color(150,150,150));
		
		newgame.setBackground(new Color(35,25,25));
		newgame.setForeground(new Color(35,255,0));
		newgame.setFont(new Font("Ink Free",Font.BOLD,35));
		newgame.setHorizontalAlignment(JLabel.CENTER);
		newgame.setText("Score: 0");
		newgame.setOpaque(true);
		
		for(int i=0;i<3;i++) 
		{
			for(int j=0;j<3;j++) 
			{
				buttons[i][j] = new JButton();
				button_panel.add(buttons[i][j]);
				
				buttons[i][j].setFont(new Font("MV Boli",Font.BOLD,120));
				buttons[i][j].setFocusable(false);
				buttons[i][j].addActionListener(this);
				buttons[i][j].setBackground(Color.black);
			}
		}
		
		title_panel.add(textfield);
		title_panel.add(newgame);
		frame.add(title_panel,BorderLayout.NORTH);
		frame.add(button_panel);
		
		firstTurn();
	}

	
	public void actionPerformed(ActionEvent e)
	{		   
		for(int i=0;i<3;i++) 
		{
			for(int j=0;j<3;j++) 
			{ 
				if(e.getSource()==buttons[i][j])
				{
					if(isMovesLeft(buttons)==false)
					{
						points();
						restart();
						textfield.setText("Draw!!");
					}
					if(!playerX_turn)
					{
						if(buttons[i][j].getText()=="") //checks if the button is empty
						{
							buttons[i][j].setForeground(new Color(0,0,255));
							buttons[i][j].setText("O");
							playerX_turn=true;
							if(isMovesLeft(buttons)==false)
							{
								points();	
								restart();
								textfield.setText("Draw!!");
							}
							else
							{
								textfield.setText("X turn");
								check();
							}
						}	
					}
				}	
			}			
		}
		if(playerX_turn) 
		{

			try{
				Thread.sleep(0);
			} catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
			if(isMovesLeft(buttons)==false)
			{
				points();
				restart();
				textfield.setText("Draw!!");
			}
			else 
			{
				Move bestMove = findBestMove(buttons);
				buttons[bestMove.row][bestMove.col].setText("X");
				buttons[bestMove.row][bestMove.col].setForeground(new Color(255,0,0));
				playerX_turn=false;
				textfield.setText("O turn");
				check();	
			}
		}	
	}
	
	void points()
	{
		pointsx++;
		pointso++;
		TicTacToe.newgame.setText("Score: AI:"+pointsx+ " Human:"+pointso);
	}
	void restart() 
	{
		
		/*try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
		TicTacToe tictactoe = new TicTacToe();
		points1.points1=TicTacToe.pointsx;
		points1.points2=TicTacToe.pointso;
		 
		tictactoe=null;
		pointsx = minimax.points1.points1;
		pointso = minimax.points1.points2;
		points();
		pointsx--;
		pointso--;	
	}
	
	static Boolean isMovesLeft(JButton board[][])
	{
	    for (int i = 0; i < 3; i++)
	        for (int j = 0; j < 3; j++)
	            if (buttons[i][j].getText()=="")
	                return true;
	    return false;
	}
	static int evaluate(JButton b[][])
	{
	    // Checking for Rows for X or O victory.
	    for (int row = 0; row < 3; row++)
	    {
	        if (b[row][0].getText()==b[row][1].getText() && b[row][1].getText()==b[row][2].getText()) 		
	        {
	            if(b[row][0].getText()=="X")
	            {
	            	return +10;
	            }
	            else if(b[row][0].getText()=="O")
	            {
	            	return -10;
	            }
	        }
	    }
	 
	    // Checking for Columns for X or O victory.
	    for (int col = 0; col < 3; col++)
	    {
	        if (b[0][col].getText()==b[1][col].getText() && b[1][col].getText()==b[2][col].getText())
	        {
	            if(b[0][col].getText()=="X")
	            {
	            	return +10;
	            }
	            else if(b[0][col].getText()=="O")
	            {
	            	return -10;
	            }
	        }
	    }
	 
	    // Checking for Diagonals for X or O victory.
	    if (b[0][0].getText()==b[1][1].getText() && b[1][1].getText()==b[2][2].getText()) 		
	    {
	        if(b[0][0].getText()=="X")
	        {
	        	return +10;
	        }

	        else if(b[0][0].getText()=="O")
	        {
	        	return -10;
	        }
	    }
	 
	    if (b[0][2].getText()==b[1][1].getText() && b[1][1].getText()==b[2][0].getText())
	    {
	        if(b[0][2].getText()=="X")
	        {
	        	return +10;
	        }

	        else if(b[0][2].getText()=="O")
	        {
	        	return -10;
	        }
	    }
	    
	    return 0;
	}
	static int minimax(JButton buttons[][],int depth,int alpha,int beta, Boolean isMax)
	{
	 
		int score = evaluate(buttons);
		
		if (score == 10) // If Maximizer has won the game
			return score;
		if (score == -10) // If Minimizer has won the game
			return score;
		if (isMovesLeft(buttons) == false) //tie
			return 0;
		
		if(isMax) // If this maximizer's move
		{
			int best = -1000;
			for(int i = 0; i < 3; i++)
			{
			    for(int j = 0; j < 3; j++)
			    {
			        if (buttons[i][j].getText()=="")  // Check if cell is empty
			        {
			            buttons[i][j].setText("X"); // Make the move
			            int eval = minimax(buttons,depth + 1,alpha,beta, false);
			            best = Math.max(best,eval );
			            //System.out.println("Depth: "+depth);
			            buttons[i][j].setText("");  // Undo the move
			            alpha = Math.max(eval,alpha);
			           //System.out.println("Alpha:"+alpha+"Beta"+beta);
			            if(beta<=alpha)
			            {//System.out.println("Break alpha");
			            	break;
			            }              
			        }
			    }
			}
			System.out.println("best move at depth for max: "+depth+" is "+ best);
			best= best-depth;
			return best;
		}
		else // If this minimizer's move
		{
			int best = 1000;
			for (int i = 0; i < 3; i++)
			{
			    for (int j = 0; j < 3; j++)
			    {			        
			        if (buttons[i][j].getText()=="") // Check if cell is empty
			        {
			        	buttons[i][j].setText("O"); // Make the move
			        	int eval = minimax(buttons,depth + 1,alpha,beta, true);
			            best = Math.min(best,eval );
			            //System.out.println("Depth: "+depth);
			            buttons[i][j].setText(""); // Undo the move
			            beta = Math.min(eval,beta);
			            //System.out.println("Alpha:"+alpha+"Beta"+beta);
			            if(beta<=alpha)
			            {	//System.out.println("Break beta") ; 		           	
			            	break;
			            }  
			        }
			    }
			}
			System.out.println("best move at depth for min: "+depth+" is "+ best);
			best= best+depth;
			return best;
		}
		
	}


	Move findBestMove(JButton board[][])
	{
		int bestVal = -1000;
		Move bestMove = new Move();
		bestMove.row = -1;
		bestMove.col = -1;
		int infinity=9999;
		int ninfinity=-9999;

		long start = System.nanoTime();
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
			    
			    if (buttons[i][j].getText()=="") // Check if cell is empty
			    {
			    	buttons[i][j].setText("X"); // Make the move 
			        int moveVal = minimax(board, 0,ninfinity,infinity, false);						       
			        buttons[i][j].setText("");  // Undo the move
			        if (moveVal > bestVal)
			        {
			            bestMove.row = i;
			            bestMove.col = j;
			            bestVal = moveVal;
			        }
			    }
			}
		}
		
		System.out.printf("The value of the best Move " + "is : %d\n\n", bestVal);
		long end = System.nanoTime();
		System.out.println("Time taken:"+(end-start));
		return bestMove;
	}
	
	 
	public void firstTurn() 
	{
		
		/*try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			playerX_turn=false;
			textfield.setText("O turn");
	}
	
	public void check()
	{	
		//check X win conditions
		if(
				(buttons[0][0].getText()=="X") &&
				(buttons[0][1].getText()=="X") &&
				(buttons[0][2].getText()=="X")
				) {
			xWins(0,3,2);
		}
		if(
				(buttons[1][0].getText()=="X") &&
				(buttons[1][1].getText()=="X") &&
				(buttons[1][2].getText()=="X")
				) {
			xWins(1,3,2);
		}
		if(
				(buttons[2][0].getText()=="X") &&
				(buttons[2][1].getText()=="X") &&
				(buttons[2][2].getText()=="X")
				) {
			xWins(2,3,2);
		}
		if(
				(buttons[0][0].getText()=="X") &&
				(buttons[1][0].getText()=="X") &&
				(buttons[2][0].getText()=="X")
				) {
			xWins(3,0,2);
		}
		if(
				(buttons[0][1].getText()=="X") &&
				(buttons[1][1].getText()=="X") &&
				(buttons[2][1].getText()=="X")
				) {
			xWins(3,1,2);
		}
		if(
				(buttons[0][2].getText()=="X") &&
				(buttons[1][2].getText()=="X") &&
				(buttons[2][2].getText()=="X")
				) {
			xWins(3,2,2);
		}
		if(
				(buttons[0][0].getText()=="X") &&
				(buttons[1][1].getText()=="X") &&
				(buttons[2][2].getText()=="X")
				) {
			xWins(3,3,0);
		}
		if(
				(buttons[0][2].getText()=="X") &&
				(buttons[1][1].getText()=="X") &&
				(buttons[2][0].getText()=="X")
				) {
			xWins(3,3,1);
		}
		//check O win conditions
		if(
				(buttons[0][0].getText()=="O") &&
				(buttons[0][1].getText()=="O") &&
				(buttons[0][2].getText()=="O")
				) {
			oWins(0,3,2);
		}
		if(
				(buttons[1][0].getText()=="O") &&
				(buttons[1][1].getText()=="O") &&
				(buttons[1][2].getText()=="O")
				) {
			oWins(1,3,2);
		}
		if(
				(buttons[2][0].getText()=="O") &&
				(buttons[2][1].getText()=="O") &&
				(buttons[2][2].getText()=="O")
				) {
			oWins(2,3,2);
		}
		if(
				(buttons[0][1].getText()=="O") &&
				(buttons[1][1].getText()=="O") &&
				(buttons[2][1].getText()=="O")
				) {
			oWins(3,0,2);
		}
		if(
				(buttons[0][1].getText()=="O") &&
				(buttons[1][1].getText()=="O") &&
				(buttons[2][1].getText()=="O")
				) {
			oWins(3,1,2);
		}
		if(
				(buttons[0][2].getText()=="O") &&
				(buttons[1][2].getText()=="O") &&
				(buttons[2][2].getText()=="O")
				) {
			oWins(3,2,2);
		}
		if(
				(buttons[0][0].getText()=="O") &&
				(buttons[1][1].getText()=="O") &&
				(buttons[2][2].getText()=="O")
				) {
			oWins(3,3,0);
		}
		if(
				(buttons[0][2].getText()=="O") &&
				(buttons[1][1].getText()=="O") &&
				(buttons[2][0].getText()=="O")
				) {
			oWins(3,3,1);
		}
	}
	
	public void xWins(int row,int col, int diag) 
	{
		pointsx= pointsx+2;
		TicTacToe.newgame.setText("Score: AI:"+pointsx+ " Human:"+pointso);
		if(row!=3)
		
		{	buttons[row][0].setBackground(Color.GREEN);
			buttons[row][1].setBackground(Color.GREEN);
			buttons[row][2].setBackground(Color.GREEN);
				
		}
		if(col!=3)
			
		{	buttons[0][col].setBackground(Color.GREEN);
			buttons[1][col].setBackground(Color.GREEN);
			buttons[2][col].setBackground(Color.GREEN);
				
		}
		if(diag==0)
			
		{	buttons[0][0].setBackground(Color.GREEN);
			buttons[1][1].setBackground(Color.GREEN);
			buttons[2][2].setBackground(Color.GREEN);
			
		}
		if(diag==1)
			
		{	buttons[0][2].setBackground(Color.GREEN);
			buttons[1][1].setBackground(Color.GREEN);
			buttons[2][0].setBackground(Color.GREEN);
				
		}
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
			buttons[i][j].setEnabled(false);
			
			}
		}
		restart();
		textfield.setText("X wins");
	}
	public void oWins(int row,int col, int diag) 
	{
		pointso=pointso+2;
		TicTacToe.newgame.setText("Score: AI:"+pointsx+ " Human:"+pointso);
		if(row!=3)
		
		{	buttons[row][0].setBackground(Color.GREEN);
			buttons[row][1].setBackground(Color.GREEN);
			buttons[row][2].setBackground(Color.GREEN);
			
		}
		if(col!=3)
			
		{	buttons[0][col].setBackground(Color.GREEN);
			buttons[1][col].setBackground(Color.GREEN);
			buttons[2][col].setBackground(Color.GREEN);
			
		}
		if(diag==0)
			
		{	buttons[0][0].setBackground(Color.GREEN);
			buttons[1][1].setBackground(Color.GREEN);
			buttons[2][2].setBackground(Color.GREEN);
			
		}
		if(diag==1)
			
		{	buttons[0][2].setBackground(Color.GREEN);
			buttons[1][1].setBackground(Color.GREEN);
			buttons[2][0].setBackground(Color.GREEN);
				
		}
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
			buttons[i][j].setEnabled(false);
			
			}
		}
		restart();
		textfield.setText("O wins");
	}	
 }
 
