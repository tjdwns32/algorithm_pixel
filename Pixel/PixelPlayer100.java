import java.awt.*;
import java.util.Random;

public class PixelPlayer100 extends Player {
	PixelPlayer100(int[][] map) {
		super(map);//initialize parents class
	}

	public Point nextPosition(Point lastPosition) {  
	  //correct from here
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();//�����̴� ��ġ��������
		int count = 0, find = 0;
		Point nextPosition;
		Random random = new Random();
		int seed = random.nextInt(2);//�����ϰ� 2���� ��������

		if( seed == 0)//����1
		{
			while(true)//x�࿡�� ���ڸ� ã��
			{
				x = x + 1;
				count++;
				if( x >= PixelTester.SIZE_OF_BOARD )//������ ������ ������� Ȯ��
				{
					x = 0;
				}
				if(map[x][y] == 0 )//consider map state, ����������� �������� ã�����Ƿ� find=1
				{
					find = 1;
					break;
				}
				if( count > PixelTester.SIZE_OF_BOARD )
				{
					x = (int)lastPosition.getX();//x�࿡�� ���ڸ��� ��ã������ ���������� �ʱ�ȭ
					count = 0;//
					break;
				}
			}
			while(find == 0)//x�࿡�� �� �ڸ��� ã�� �������� y�࿡�� ã��
			{
				y = y + 1;
				count++;
				if( y >= PixelTester.SIZE_OF_BOARD){
					y = 0;
				}

				if(map[x][y] == 0 )
				{
					break;
				}
				if( count > PixelTester.SIZE_OF_BOARD )
					break;
			}
		}
		else//����2
		{
			while(true)//y�� ���� Ž��
			{
				y = y + 1;
				count++;
				if( y >= PixelTester.SIZE_OF_BOARD){
					y = 0;
				}

				if(map[x][y] == 0 )
				{
					find = 1;
					break;
				}
				if( count > PixelTester.SIZE_OF_BOARD )
				{
					y = (int)lastPosition.getY();//y�࿡�� ���ڸ��� ��ã������ ���������� �ʱ�ȭ
					count = 0;
					break;
				}
			}
			while(find == 0)//y�࿡�� ��ã������ x�࿡�� Ž��
			{
				x = x + 1;
				count++;
				if( x >= PixelTester.SIZE_OF_BOARD )
				{
					x = 0;
				}
				if(map[x][y] == 0 )
				{
					break;
				}
				if( count > PixelTester.SIZE_OF_BOARD )
					break;
			}

		}

		nextPosition = new Point(x, y);
		return nextPosition;
	}
}