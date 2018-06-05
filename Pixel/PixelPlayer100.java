import java.awt.*;
import java.util.Random;

public class PixelPlayer100 extends Player {
	PixelPlayer100(int[][] map) {
		super(map);//initialize parents class
	}

	public Point nextPosition(Point lastPosition) {  
	  //correct from here
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();//슬라이더 위치값얻어오기
		int count = 0, find = 0;
		Point nextPosition;
		Random random = new Random();
		int seed = random.nextInt(2);//랜덤하게 2가지 전략적용

		if( seed == 0)//전략1
		{
			while(true)//x축에서 빈자리 찾기
			{
				x = x + 1;
				count++;
				if( x >= PixelTester.SIZE_OF_BOARD )//보드의 범위를 벗어나는지 확인
				{
					x = 0;
				}
				if(map[x][y] == 0 )//consider map state, 비어져있으면 빈차리를 찾았으므로 find=1
				{
					find = 1;
					break;
				}
				if( count > PixelTester.SIZE_OF_BOARD )
				{
					x = (int)lastPosition.getX();//x축에서 빈자리를 못찾았으면 원래값으로 초기화
					count = 0;//
					break;
				}
			}
			while(find == 0)//x축에서 빈 자리를 찾지 못했으면 y축에서 찾기
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
		else//전략2
		{
			while(true)//y축 먼저 탐색
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
					y = (int)lastPosition.getY();//y축에서 빈자리를 못찾았으면 원래값으로 초기화
					count = 0;
					break;
				}
			}
			while(find == 0)//y축에서 못찾았으면 x축에서 탐색
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