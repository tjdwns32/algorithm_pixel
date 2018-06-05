import java.awt.*;
import java.util.Random;

public class PixelPlayer101 extends Player {
	PixelPlayer101(int[][] map) {
		super(map);
	}
  //�ټ� �������� �����
	public Point nextPosition(Point lastPosition) {  
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();//���� ���� ������ ��ġ
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();//�� ���� ������ ��ġ
		int direction, count = 0;
		int myNum = map[(int)currentPosition.getX()][(int)currentPosition.getY()];
		int checkPositionX;
		int checkPositionY;
		Point nextPosition;
		Random random = new Random();

		for( int n = 0; n < 8; n++ )
		{
			for( int m = 0; m < 8; m++ )
			{
				if( myNum == map[n][m] )//myNum: p100�̸� 1, p101�̸� 2
				{
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++)
					{
						if(map[i][y] == 0 && ( i == n || y == m ))//�밢���̵� �����ϸ鼭 x���̵�
						{
							nextPosition = new Point(i, y);
							System.out.println(nextPosition);
							return nextPosition;
						}
					}
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++)
					{
						if(map[x][i] == 0 && ( x == n || i == m ))
						{
							nextPosition = new Point(x, i);
							System.out.println(nextPosition);
							return nextPosition;
						}
					}
				}
			}
		}

		nextPosition = new Point(x, y);
		return nextPosition;
	}
}