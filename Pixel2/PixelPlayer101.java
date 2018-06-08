import java.awt.*;
import java.util.Random;

public class PixelPlayer101 extends Player {
	PixelPlayer101(int[][] map) {
		super(map);
	}

	public Point nextPosition(Point lastPosition) {  
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();
		int direction, count = 0;
		int myNum = map[(int)currentPosition.getX()][(int)currentPosition.getY()];
		int checkPositionX;
		int checkPositionY;
		Point nextPosition;
		Random random = new Random();
		
		int [][] opMapX = new int[8][8]; 
    int [][] opMapY = new int[8][8];
    int [][] opMaplD = new int[8][8];
    int [][] opMaprD = new int[8][8];
    int [][] opMap = new int[8][8]; 
    int [][] omap = 
          {{1,0,0,0,0,0,1,0},
           {0,0,1,0,0,0,0,1},
           {0,0,1,1,1,0,0,0},
           {0,1,0,0,0,1,0,0},
           {0,0,1,0,0,0,0,0},
           {0,0,0,0,0,0,1,0},
           {0,1,0,0,0,1,0,0},
           {1,0,0,0,1,0,0,0}};
    
    opMapX = horizonSearch(map);
    opMapY = verticalSearch(map);
    opMaplD = lDiagonalSearch(map);
    opMaprD = rDiagonalSearch(map);
		
		
		System.out.println("원본테이블");
		print(map); 
		System.out.println("가로테이블");
		print(opMapX); 
		System.out.println("세로테이블");
		print(opMapY); 
		System.out.println("대각선테이블1");
		print(opMaplD); 
		System.out.println("대각선테이블2");
		print(opMaprD); 
    
		for( int n = 0; n < 8; n++ )
		{
			for( int m = 0; m < 8; m++ )
			{
				if( myNum == map[n][m] )
				{
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++)
					{
						if(map[i][y] == 0 && ( i == n || y == m ))
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
	
	//맵 출력 함수
	public static void print(int[][] map){
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(map[i][j] + "\t ");
      }
      System.out.println();
      System.out.println();
    }
	}
	
	//가중치 맵 merge함수
	public static int[][] merge(int hMap[][], int vMap[][], int lDMap[][], int rDMap[][]){
	  int[][] opMap = new int[8][8];
	  
	  return opMap;
	}
	
	//세로가중치
	public static int[][] verticalSearch(int[][] omap){
	  int w = 100; //가중치
		int cnt = 0; //붙어있는 돌의 수
	  int[][] opMapY = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[j][i] == 1){ //현재 돌의 위치를 찾으면
          cnt++; //돌의 수를 증가
          
          while(true){ //붙어있는 돌을 확인
            if((j+cnt) < 8){ //맵의 크기를 벗어나지 않는 선에서
              if(omap[j+cnt][i] == 1) //붙어있는 돌이 존재하면 카운트하고
                ++cnt;
              else if(omap[j+cnt][i] == 0){ //돌을 둘수있는 위치이면 세로가중치 맵을 검사
                if(opMapY[j+cnt][i] >= 100)  //가중치가 이미 존재하면 가중치를 더해주고
                  opMapY[j+cnt][i] = opMapY[j+cnt][i] + (cnt * w);
                else  //가중치가 없으면 가중치를 넣어줌
                  opMapY[j+cnt][i] = cnt * w;
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
          }
          
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
             if ( (omap[j-1][i] == 0) ){ //돌을 둘 수 있는 위치이면
              if(opMapX[j-1][i] >= 100) //세로 가중치 맵 검사
                opMapX[j-1][i] = opMapX[j-1][i] + (cnt * w); 
              else
                opMapX[j-1][i] = (cnt * w); 
            }
          }
          
          //붙어있는 돌의 수 만큼 이미 맵을 검사했으므로 건너뜀
          if((j+cnt)<8) j+=cnt;
          cnt = 0;
       }
      }
    }
    return opMapY;
	}
	
	//가로가중치
	public static int[][] horizonSearch(int[][] omap){
	  int w = 100; //가중치
		int cnt = 0; //붙어있는 돌의 수
	  int[][] opMapX = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[i][j] == 1){ //현재 돌의 위치를 찾으면
          cnt++; //돌의 수를 증가
          
          while(true){ //붙어있는 돌을 확인
            if((j+cnt) < 8){ //맵의 크기를 벗어나지 않는 선에서
              if(omap[i][j+cnt] == 1)  //붙어있는 돌이 존재하면 카운트하고
                ++cnt;
              else if(omap[i][j+cnt] == 0){ //돌을 둘수있는 위치이면 가로가중치 맵을 검사
                
                if(opMapX[i][j+cnt] >= 100)  //가중치가 이미 존재하면 가중치를 더해주고
                  opMapX[i][j+cnt] = opMapX[i][j+cnt] + (cnt * w);
                else         //가중치가 없으면 가중치를 넣어줌
                  opMapX[i][j+cnt] = (cnt * w);
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
      
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
             if ( (omap[i][j-1] == 0) ){ //돌을 둘 수 있는 위치이면
              if(opMapX[i][j-1] >= 100) //가로 가중치 맵 검사
                opMapX[i][j-1] = opMapX[i][j-1] + (cnt * w); 
              else
                opMapX[i][j-1] = (cnt * w); 
            }
          }
          
          //붙어있는 돌의 수 만큼 이미 맵을 검사했으므로 건너뜀
          if((j+cnt)<8) j+=cnt;
          cnt = 0;
       }
      }
    }
    return opMapX;
	}
	
	//대각선가중치- 왼쪽 위에서 오른쪽 아래로
	public static int[][] lDiagonalSearch(int[][] omap){
//		  System.out.println("왼쪽 위에서 오른쪽 아래로");
		  int cnt = 0;
      int weight = 0;
      int temp = 0;
      //왼쪽 위에서 오른쪽 아래로 대각선 탐색
		  int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
		  for(int i = PixelTester.SIZE_OF_BOARD-1;i>=0;i--) {
		  //System.out.println("diag: "+i);
		  cnt =0;
		  temp = i;
			for(int j =0;temp<PixelTester.SIZE_OF_BOARD;temp++,j++){
			  if(omap[temp][j] == 1){//내돌이 있으면 가중치
			     cnt += 1;
			     //System.out.println("가중치: ["+temp+"]["+j+"]");
			  }
			  else if(omap[temp][j] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			     //System.out.println("최종 가중치 :["+temp+"]["+j+"]");
			     weight = 100*cnt;
			     cmap[temp][j] = weight;
			     if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0)
			      cmap[temp-(cnt+1)][j-(cnt+1)] = weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
			     //System.out.println("예외상황 가중치: ["+temp+"]["+j+"]");
			     weight = 100*cnt;
			     if(temp-cnt >= 0 && j-cnt >= 0)
			      cmap[temp-cnt][j-cnt] = weight; 
			     cnt =0;
			  }	  
			}
	  }
    for(int j=0;j<PixelTester.SIZE_OF_BOARD;j++){
		  //System.out.println("diag: "+j);
		  cnt =0;
		  temp = j;
			for(int i =0;temp<PixelTester.SIZE_OF_BOARD;temp++,i++){
			  if(omap[i][temp] == 1){//내돌이 있으면 가중치
			     cnt += 1;
			     //System.out.println("가중치: ["+i+"]["+temp+"]");
			  }
			  else if(omap[i][temp] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			     //System.out.println("최종 가중치 :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] = weight;
			     if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0)
			      cmap[i-(cnt+1)][temp-(cnt+1)] = weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
			     //System.out.println("예외상황 가중치: ["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     if(temp-cnt >= 0 && i-cnt >= 0)
			      cmap[i-cnt][temp-cnt] = weight; 
			     cnt = 0;
			  }	  
			}
	  }
	  return cmap;  
		}
		
		//대각선가중치 - 오른쪽 위에서 왼쪽 아래로
		public static int[][] rDiagonalSearch(int[][] omap){
//		  System.out.println("오른쪽 위에서 왼쪽 아래로");
		  int cnt = 0;
      int weight = 0;
      int temp = 0;
		  int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
		  //오른쪽 위에서 왼쪽아래로 대각선 탐색
		  for(int i = PixelTester.SIZE_OF_BOARD-1;i>=0;i--) {
		    //System.out.println("diag: "+i);
		    cnt =0;
		    temp = i;
			  for(int j =PixelTester.SIZE_OF_BOARD-1;temp<PixelTester.SIZE_OF_BOARD;temp++,j--){
			    if(omap[temp][j] == 1){//내돌이 있으면 가중치
			      cnt += 1;
			      //System.out.println("가중치 시작점: ["+temp+"]["+j+"]");
			    }
			    else if(omap[temp][j] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			      //System.out.println("최종 가중치 :["+temp+"]["+j+"]");
			      weight = 100*cnt;
			      cmap[temp][j] += weight;
			      if(temp-(cnt+1) >= 0 && j+(cnt+1)<PixelTester.SIZE_OF_BOARD)
			      cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
			      cnt=0;
			    }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){ //N가중치 부여하기전에 연결이 끝날때
			    //System.out.println("예외상황 가중치: ["+temp+"]["+j+"]");
			    weight = 100*cnt;
			    if(temp-cnt >= 0 && j+cnt<PixelTester.SIZE_OF_BOARD)
			    cmap[temp-cnt][j+cnt] += weight; 
			    cnt =0;
			  }	  
			}
	  }
    for(int j= 0;j<PixelTester.SIZE_OF_BOARD;j++) {
		  //System.out.println("diag: "+j);
		  cnt =0;
		  temp = j;
			for(int i =0;temp>=0;temp--,i++){
			  if(omap[i][temp] == 1){//내돌이 있으면 가중치
			     cnt += 1;
			     //System.out.println("가중치: ["+i+"]["+temp+"]");
			  }
			  else if(omap[i][temp] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			     //System.out.println("최종 가중치 :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] += weight;
			     if(i-(cnt+1) >= 0 && temp+(cnt+1)<PixelTester.SIZE_OF_BOARD)
			      cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
			     //System.out.println("예외상황 가중치: ["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     if(i-cnt >= 0 && temp+cnt<PixelTester.SIZE_OF_BOARD)
			      cmap[i-cnt][temp+cnt] += weight; 
			     cnt = 0;
			  }	  
			}
	  }
	  return cmap;
		}
	
	
}