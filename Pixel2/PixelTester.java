import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PixelTester{
	public static int SIZE_OF_BOARD = 8;
	public static int [][] map = new int[SIZE_OF_BOARD][SIZE_OF_BOARD];
	public static Point dolPosition;
	public static Point lastPosition;
	static boolean start = true;
	static int speed = 5;
	static int turn = 1;
	public static double p1Time = 0;
	public static double p2Time = 0;
	public static void main(String[] args) throws Exception{
		// Viewer Part
		double startTime;
		double endTime; 
		JFrame frame = new JFrame();
		int winner=0;
		final int FRANE_WIDTH = 800;
		final int FRANE_HEIGHT = 700;

		frame.setSize(FRANE_WIDTH, FRANE_HEIGHT);
		frame.setTitle("< Pixel Game >");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		map[0][0] = -1;
		map[0][7] = -1;
		map[7][0] = -1;
		map[7][7] = -1;

		final PixelBoard board = new PixelBoard(map);

		final Player p1 = new PixelPlayer100(map);
		final Player p2 = new PixelPlayer110(map);
		p1.setCurrentPosition(new Point(4,3));
		p2.setCurrentPosition(new Point(3,4));


		final JButton startBtn = new JButton("Proceed!");
		startBtn.setLocation(180, 610);
		startBtn.setSize(90, 40);
		startBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(start)
						{
							start = false;
							startBtn.setText("Pause!");
						}
						else
						{
							start = true;
							startBtn.setText("Proceed!");
						}

					}
				}
				);
		board.add(startBtn);
		JButton resetBtn = new JButton("Reset!");
		resetBtn.setLocation(290, 610);
		resetBtn.setSize(90, 40);
		resetBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						startBtn.setText("Proceed!");
						start = true;
						pixelReset();
						p1.setCurrentPosition(new Point(4,3));
						p2.setCurrentPosition(new Point(3,4));
						turn = 1;
						board.isendb = false;
						board.repaint();

					}
				}
				);
		board.add(resetBtn);
		JButton fastBtn = new JButton("Fast!");
		fastBtn.setLocation(400, 610);
		fastBtn.setSize(90, 40);
		fastBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if( speed == 1)
							return;
						speed--;
					}
				}
				);
		board.add(fastBtn);

		JButton slowBtn = new JButton("Slow!");
		slowBtn.setLocation(510, 610);
		slowBtn.setSize(90, 40);
		slowBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						start = false;
						if( speed == 10)
							return;
						speed++;
					}
				}
				);
		board.add(slowBtn);

		frame.add(board);
		frame.setVisible(true);
		// Pixel Game Part
		dolPosition = new Point(4, 3);
		lastPosition = new Point(4, 3);



		map[4][3] = 1;
		map[3][4] = 2;
		board.repaint();
		// Pixel Start
		while(true)
		{
			Thread.sleep(100);
			while( board.isendb == false) {
				while(start){
					Thread.sleep(100);
				}

				// Player1
				if( turn == 1)
				{

					startTime = System.nanoTime();
					dolPosition = p1.nextPosition(dolPosition);
					endTime = System.nanoTime();
					p1Time = p1Time + ( endTime - startTime)/1000000.00;
					p1.setCurrentPosition(dolPosition);
					System.out.println("p1 return : " + dolPosition);
					turn = 2;
					if ( dolPosition.getX() < 0 || dolPosition.getX() > SIZE_OF_BOARD-1 || dolPosition.getY() < 0 || dolPosition.getY() > SIZE_OF_BOARD-1 ) {
						System.out.println("Index: Player 2 Win !");
						winner = 2;
						break;
					}
					if ( dolPosition.equals(new Point(0,0)) || dolPosition.equals(new Point(7,7)) || dolPosition.equals(new Point(0,7)) || dolPosition.equals(new Point(7,0)) ) {
						System.out.println("Index: Player 2 Win !");
						winner = 2;
						break;
					}

					if ( map[(int)dolPosition.getX()][(int)dolPosition.getY()] == 0 ) {
						map[(int)dolPosition.getX()][(int)dolPosition.getY()] = 1;
					} else {
						System.out.println("overlap: Player 2 Win !");
						winner = 2;
						break;
					}
					if(xyRule(dolPosition, lastPosition) == false )
					{
						System.out.println("xyRule: Player 2 Win !");
						winner = 2;
						break;
					}
					lastPosition = dolPosition;				 

					board.repaint();
					//printMap();
					Thread.sleep(speed*300);
					if ( isfour(dolPosition, 1) ) {
						System.out.println("Type2: Player 1 Win !");
						winner = 1;
						printMap();
						break;
					}

					if ( isdraw(dolPosition) ) {
						System.out.println("A drawn State!");
						break;
					}


				}


				// Player2
				while(start){
					Thread.sleep(100);
				}
				if( turn == 2)
				{
					startTime = System.nanoTime();
					dolPosition = p2.nextPosition(dolPosition);
					endTime = System.nanoTime();

					p2Time = p2Time + ( endTime - startTime)/1000000.00;
					p2.setCurrentPosition(dolPosition);
					turn = 1;
					System.out.println("p2 return : " + dolPosition);

					if ( dolPosition.getX() < 0 || dolPosition.getX() > SIZE_OF_BOARD-1 || dolPosition.getY() < 0 || dolPosition.getY() > SIZE_OF_BOARD-1 ) {
						System.out.println("Type1: Player 1 Win !");
						winner = 1;
						break;
					}
					if ( dolPosition.equals(new Point(0,0)) || dolPosition.equals(new Point(7,7)) || dolPosition.equals(new Point(0,7)) || dolPosition.equals(new Point(7,0)) ) {
						System.out.println("Type1: Player 1 Win !");
						winner = 1;
						break;
					}
					if ( map[(int)dolPosition.getX()][(int)dolPosition.getY()] == 0 ) {
						map[(int)dolPosition.getX()][(int)dolPosition.getY()] = 2;
					} else {
						System.out.println("Type1: Player 1 Win !");
						winner = 1;
						break;
					}

					if(xyRule(dolPosition, lastPosition) == false )
					{
						System.out.println("Type1: Player 1 Win !");
						winner = 1;
						break;
					}

					board.repaint();
					//printMap();
					Thread.sleep(speed*300);

					if ( isfour(dolPosition, 2) ) {
						System.out.println("Type2: Player 2 Win !");
						winner = 2;
						printMap();
						break;
					}
					lastPosition = dolPosition;
					if ( isdraw(dolPosition) ) {
						System.out.println("A drawn State!");
						break;
					}

				}
			}
			if ( isdraw(dolPosition) )
			{
				winner = 3;
			}

			board.isend(winner);
			board.repaint();
		}
	}

	// Case 1. All map is occupied
	// Case 2. If 1 position is remained, we judge omok by next player
	public static boolean isdraw(Point dolPosition) {
		int x = 0, y = 0, count = 0;
		for( int i = 0; i < SIZE_OF_BOARD; i++ ) {
			for( int j = 0; j < SIZE_OF_BOARD; j++ ) {
				if( map[i][j] == 0)
					count++;
			}
		}
		if( count == 0)
			return true;
		else
		{
			count = 0;
			x = (int)dolPosition.getX();
			y = (int)dolPosition.getY();
			for(int i=0; i<SIZE_OF_BOARD; i++)
			{
				if( map[x][i] == 0)
					count++;
			}
			for(int i=0; i<SIZE_OF_BOARD; i++)
			{
				if( map[i][y] == 0)
					count++;
			}
		}
		if( count == 0)
			return true;

		return false;
	}

	public static boolean isfour(Point dolPosition, int who) {
		int i;
		int count = 0;
		int x = (int)dolPosition.getX();
		int y = (int)dolPosition.getY();

		for( i = x - 3; i <= x + 3; i++ ) {
			if ( i < 0 || i >= SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[i][y] == who ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
				count = 0;
			}
		}

		count = 0;
		for( i = y - 3; i <= y + 3; i++ ) {
			if ( i < 0 || i >= SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[x][i] == who ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
				count = 0;
			}
		}

		count = 0;
		for( i = -3; i <= 3; i++ ) {
			if ( x + i < 0 || y + i < 0 || x + i >= SIZE_OF_BOARD || y + i >= SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[x + i][y + i] == who ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
				count = 0;
			}
		}

		count = 0;
		for( i = -3; i <= 3; i++ ) {
			if ( x + i < 0 || y - i < 0 || x + i >= SIZE_OF_BOARD || y - i >= SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[x + i][y - i] == who ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
				count = 0;
			}
		}    

		return false;	
	}

	public static void printMap() {
		for( int i = 0; i < SIZE_OF_BOARD; i++ ) {
			for( int j = 0; j < SIZE_OF_BOARD; j++ ) {
				if ( map[i][j] == 0 ) {
					System.out.print("-");
				} else if ( map[i][j] == 1 ) {
					System.out.print("O");
				} else if ( map[i][j] == 2 ) {
					System.out.print("X");
				}
			}
			System.out.println();
		}     	
	}

	public static boolean xyRule(Point dol, Point last){
		if( dol.getX() == last.getX() || dol.getY() == last.getY() )
		{
			return true;
		}
		else
			return false;

	}

	public static void pixelReset()
	{
		for(int i = 0; i<SIZE_OF_BOARD; i++)
		{
			for(int j = 0; j<SIZE_OF_BOARD; j++)
			{
				map[i][j] = 0;
			}
		}

		map[0][0] = -1;
		map[0][7] = -1;
		map[7][0] = -1;
		map[7][7] = -1;
		speed = 5;
		p1Time = 0;
		p2Time = 0;
		dolPosition = new Point(4, 3);
		lastPosition = new Point(4, 3);
		map[4][3] = 1;
		map[3][4] = 2;
	}

}

