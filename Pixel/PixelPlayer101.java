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
		int myNum = map[(int)lastPosition.getX()][(int)lastPosition.getY()];
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
          {{0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0},
           {0,0,0,0,1,0,0,0},
           {0,0,0,0,1,0,0,0},
           {0,0,0,0,0,1,1,0},
           {0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0}};
    
    opMapX = horizonSearch(map);
    opMapY = verticalSearch(map);
    opMaplD = lDiagonalSearch(map);
    opMaprD = rDiagonalSearch(map);
    opMap = merge(opMapX,opMapY,opMaplD,opMaprD);
		
		
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
		System.out.println("병합테이블");
		print(opMap); 
		
		
		
    int maxX = 0;
    int maxY = 0;
    int posY = 0;
    int posX = 0;
	        System.out.println(x + " " + y);
	
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++)//상대방 돌 위치에서 세로를 검사
					{
						if(map[i][cy] == 0) //돌을 둘 수 있는 위치이면  
						{
						  //세로축에서 최대 가중치 값을 찾음
						  if(opMap[i][cy] > maxX){ 
						    maxX = opMap[i][cy];
						    posX = i;
						  }
						  else if(opMap[i][cy] == maxX){ //가중치가 같으면
						    if(Math.abs(cy-i) < Math.abs(cy-posX))
						      posX = i;
						    System.out.println(posX);
						  }
						}
					}
					System.out.println("--세로축검사--");
					System.out.println("maxX : "+maxX);
					System.out.println("posX : "+posX);
					
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++)//상대방 돌 위치에서 가로를 검사
					{
						if(map[x][i] == 0) //돌을 둘 수 있는 위치이면  
						{
						  //가로축에서 최대 가중치 값을 찾음
							if(opMap[cx][i] > maxY){ 
						    maxY = opMap[cx][i];
						    posY = i;
						  }
						  else if (opMap[cx][i] == maxY){ //가중치가 같으면
						    if(Math.abs(cy-i) < Math.abs(cy-posY))
						      posY = i;
						    System.out.println(posY);
						  }
						}
					}
					
					System.out.println("--가로축검사--");
					System.out.println("maxY : "+maxY);
					System.out.println("posY : "+posY);
					
					if(maxX > maxY){
					  nextPosition = new Point(posX, cy);
					  System.out.println(nextPosition);
					  return nextPosition;
					}
					else if(maxX < maxY){  
					  nextPosition = new Point(cx, posY);
					  System.out.println(nextPosition);
					  return nextPosition;
					}
					else{
				    if(Math.abs(cy-posY) >= Math.abs(cx-posX)){
				      nextPosition = new Point(posX, cy);
					    System.out.println(nextPosition);
					    return nextPosition;
				    }
				    else if(Math.abs(cy-posX) < Math.abs(cx-posY)){
				      nextPosition = new Point(cx, posY);
					    System.out.println(nextPosition);
					    return nextPosition;
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
	  int maxW = 0;
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        maxW = Math.max(hMap[i][j], vMap[i][j]);
        maxW = Math.max(maxW, lDMap[i][j]);
        maxW = Math.max(maxW, rDMap[i][j]);
        
        opMap[i][j] = opMap[i][j] + maxW;
        if(hMap[i][j] == maxW){
          opMap[i][j] = opMap[i][j] + ( (vMap[i][j])/10 ) + ( (lDMap[i][j])/10 ) + ( (rDMap[i][j])/10 );
        }
        else if(vMap[i][j] == maxW){
          opMap[i][j] = opMap[i][j] + ( (hMap[i][j])/10 ) + ( (lDMap[i][j])/10 ) + ( (rDMap[i][j])/10 );
        }
        else if(lDMap[i][j] == maxW){
          opMap[i][j] = opMap[i][j] + ( (vMap[i][j])/10 ) + ( (hMap[i][j])/10 ) + ( (rDMap[i][j])/10 );
        }
        else if(rDMap[i][j] == maxW){ 
          opMap[i][j] = opMap[i][j] + ( (vMap[i][j])/10 ) + ( (hMap[i][j])/10 ) + ( (lDMap[i][j])/10 ); 
        }
      }
    }
	  
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
                opMapY[j+cnt][i] = opMapY[j+cnt][i] + (cnt * w);
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
          }
          
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
             if ((omap[j-1][i] == 0) ){ //돌을 둘 수 있는 위치이면
              opMapY[j-1][i] = opMapY[j-1][i] + (cnt * w);  
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
<<<<<<< HEAD
              if(omap[i][j+cnt] == 1){ //붙어있는 돌이 존재하면 카운트하
                System.out.println("["+i+"]["+(j+cnt)+"]"); 
=======
              if(omap[i][j+cnt] == 1) //붙어있는 돌이 존재하면 카운트하고
>>>>>>> c301d32bb50aec6454b7020576dac4758090e77b
                ++cnt;
              }
              else if(omap[i][j+cnt] == 0){ //돌을 둘수있는 위치이면 가로가중치 맵을 검사
                opMapX[i][j+cnt] = opMapX[i][j+cnt] + (cnt * w);
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
          }
          
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
             if ((omap[i][j-1] == 0) ){ //돌을 둘 수 있는 위치이면
              opMapX[i][j-1] = opMapX[i][j-1] + (cnt * w);  
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
//        System.out.println("왼쪽 위에서 오른쪽 아래로");
        int cnt = 0;
      int weight = 0;
      int temp = 0;
      //왼쪽 위에서 오른쪽 아래로 대각선 탐색
<<<<<<< HEAD
        int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
        for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--) {
        //System.out.println("diag: "+i);
        cnt =0;
        temp = i;
         for(int j =0;temp<PixelTester.SIZE_OF_BOARD;temp++,j++){
           if(omap[temp][j] == 1){//내돌이 있으면 가중치
              cnt += 1;
              //System.out.println("가중치: ["+temp+"]["+j+"]");
           }
           else if(omap[temp][j] != 1 && cnt != 0){//돌의 연결이 끝나면 가중치부여
              //System.out.println("최종 가중치 :["+temp+"]["+j+"]");
              weight = 100*cnt;
              if(omap[temp][j] == 0)
               cmap[temp][j] += weight;
              if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0 && omap[temp-(cnt+1)][j-(cnt+1)]==0)
               cmap[temp-(cnt+1)][j-(cnt+1)] += weight; 
              cnt=0;
           }
           if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 6)) && cnt != 0){//가중치 부여하기전에 연결이 끝날때
              //System.out.println("예외상황 가중치: ["+temp+"]["+j+"]");
              weight = 100*cnt;
              if(temp-cnt >= 0 && j-cnt >= 0 && omap[temp-cnt][j-cnt]==0)
               cmap[temp-cnt][j-cnt] += weight; 
              cnt =0;
           }     
         }
     }
    for(int j=1;j<PixelTester.SIZE_OF_BOARD;j++){
        //System.out.println("diag: "+j);
        cnt =0;
        temp = j;
         for(int i =0;temp<PixelTester.SIZE_OF_BOARD;temp++,i++){
           if(omap[i][temp] == 1){//내돌이 있으면 가중치
              cnt += 1;
              //System.out.println("가중치: ["+i+"]["+temp+"]");
           }
           else if(omap[i][temp] != 1 && cnt != 0){//돌의 연결이 끝나면 가중치부여
              //System.out.println("최종 가중치 :["+i+"]["+temp+"]");
              weight = 100*cnt;
              if(omap[i][temp] == 0)
               cmap[i][temp] += weight;
              if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0 && omap[i-(cnt+1)][temp-(cnt+1)]==0)
               cmap[i-(cnt+1)][temp-(cnt+1)] += weight; 
              cnt=0;
           }
           if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
              //System.out.println("예외상황 가중치: ["+i+"]["+temp+"]");
              weight = 100*cnt;
              if(temp-cnt >= 0 && i-cnt >= 0 && omap[i-cnt][temp-cnt]==0)
               cmap[i-cnt][temp-cnt] += weight; 
              cnt = 0;
           }     
         }
     }
     return cmap;  
    }
=======
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
			  else if(omap[temp][j] == 2) cnt = 0;
			  else if(omap[temp][j] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			     //System.out.println("최종 가중치 :["+temp+"]["+j+"]");
			     weight = 100*cnt;
			     cmap[temp][j] = weight;
			     if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0 && omap[temp-(cnt+1)][j-(cnt+1)]==0)
			      cmap[temp-(cnt+1)][j-(cnt+1)] += weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
			     //System.out.println("예외상황 가중치: ["+temp+"]["+j+"]");
			     weight = 100*cnt;
			     if(temp-cnt >= 0 && j-cnt >= 0 && omap[temp-cnt][j-cnt]==0)
			      cmap[temp-cnt][j-cnt] += weight; 
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
			  else if(omap[i][temp] == 2) cnt =0;
			  else if(omap[i][temp] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			     //System.out.println("최종 가중치 :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] = weight;
			     if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0 && omap[i-(cnt+1)][temp-(cnt+1)]==0)
			      cmap[i-(cnt+1)][temp-(cnt+1)] = weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
			     //System.out.println("예외상황 가중치: ["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     if(temp-cnt >= 0 && i-cnt >= 0 && omap[i-cnt][temp-cnt]==0)
			      cmap[i-cnt][temp-cnt] = weight; 
			     cnt = 0;
			  }	  
			}
	  }
	  return cmap;  
		}
>>>>>>> a807ff7d7941f99449e2199f946a8b116a90f1ff
		
		//대각선가중치 - 오른쪽 위에서 왼쪽 아래로
		public static int[][] rDiagonalSearch(int[][] omap){
//        System.out.println("오른쪽 위에서 왼쪽 아래로");
        int cnt = 0;
      int weight = 0;
      int temp = 0;
<<<<<<< HEAD
        int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
        //오른쪽 위에서 왼쪽아래로 대각선 탐색
        for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--) {
          //System.out.println("diag: "+i);
          cnt =0;
          temp = i;
           for(int j =PixelTester.SIZE_OF_BOARD-1;temp<PixelTester.SIZE_OF_BOARD;temp++,j--){
             if(omap[temp][j] == 1){//내돌이 있으면 가중치
               cnt += 1;
               //System.out.println("가중치 시작점: ["+temp+"]["+j+"]");
             }
             else if(omap[temp][j] != 1 && cnt != 0){//돌의 연결이 끝나면 가중치부여
               //System.out.println("최종 가중치 :["+temp+"]["+j+"]");
               weight = 100*cnt;
               if(omap[temp][j] == 0)
                 cmap[temp][j] += weight;
               if(temp-(cnt+1) >= 0 && j+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[temp-(cnt+1)][j+(cnt+1)]==0)
                cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
               cnt=0;
             }
           if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 1)) && cnt != 0){ //N가중치 부여하기전에 연결이 끝날때
             //System.out.println("예외상황 가중치: ["+temp+"]["+j+"]");
             weight = 100*cnt;
             if(temp-cnt >= 0 && j+cnt<PixelTester.SIZE_OF_BOARD && omap[temp-cnt][j+cnt]==0)
              cmap[temp-cnt][j+cnt] += weight; 
             cnt =0;
           }     
         }
     }
    for(int j= 1;j<PixelTester.SIZE_OF_BOARD-1;j++) {
        //System.out.println("diag: "+j);
        cnt =0;
        temp = j;
         for(int i =0;temp>=0;temp--,i++){
           if(omap[i][temp] == 1){//내돌이 있으면 가중치
              cnt += 1;
              //System.out.println("가중치: ["+i+"]["+temp+"]");
           }
           else if(omap[i][temp] != 1 && cnt != 0){//돌의 연결이 끝나면 가중치부여
              //System.out.println("최종 가중치 :["+i+"]["+temp+"]");
              weight = 100*cnt;
              if(omap[i][temp]==0)
               cmap[i][temp] += weight;
              if(i-(cnt+1) >= 0 && temp+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[i-(cnt+1)][temp+(cnt+1)]==0)
               cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
              cnt=0;
           }
           if(temp == 0 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
              //System.out.println("예외상황 가중치: ["+i+"]["+temp+"]");
              weight = 100*cnt;
              if(i-cnt >= 0 && temp+cnt<PixelTester.SIZE_OF_BOARD && omap[i-cnt][temp+cnt]==0)
               cmap[i-cnt][temp+cnt] += weight; 
              cnt = 0;
           }     
         }
     }
     return cmap;
      }
=======
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
			    else if(omap[temp][j] == 2) cnt =0;
			    else if(omap[temp][j] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			      //System.out.println("최종 가중치 :["+temp+"]["+j+"]");
			      weight = 100*cnt;
			      cmap[temp][j] += weight;
			      if(temp-(cnt+1) >= 0 && j+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[temp-(cnt+1)][j+(cnt+1)]==0)
			       cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
			      cnt=0;
			    }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){ //N가중치 부여하기전에 연결이 끝날때
			    //System.out.println("예외상황 가중치: ["+temp+"]["+j+"]");
			    weight = 100*cnt;
			    if(temp-cnt >= 0 && j+cnt<PixelTester.SIZE_OF_BOARD && omap[temp-cnt][j+cnt]==0)
			     cmap[temp-cnt][j+cnt] += weight; 
			    cnt =0;
			  }	  
			}
	  }
    for(int j= 0;j<PixelTester.SIZE_OF_BOARD-1;j++) {
		  //System.out.println("diag: "+j);
		  cnt =0;
		  temp = j;
			for(int i =0;temp>=0;temp--,i++){
			  if(omap[i][temp] == 1){//내돌이 있으면 가중치
			     cnt += 1;
			     //System.out.println("가중치: ["+i+"]["+temp+"]");
			  }
			  else if(omap[i][temp] ==2) cnt = 0;
			  else if(omap[i][temp] == 0 && cnt != 0){//돌의 연결이 끝나면 가중치부여
			     //System.out.println("최종 가중치 :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] += weight;
			     if(i-(cnt+1) >= 0 && temp+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[i-(cnt+1)][temp+(cnt+1)]==0)
			      cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
			     //System.out.println("예외상황 가중치: ["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     if(i-cnt >= 0 && temp+cnt<PixelTester.SIZE_OF_BOARD && omap[i-cnt][temp+cnt]==0)
			      cmap[i-cnt][temp+cnt] += weight; 
			     cnt = 0;
			  }	  
			}
	  }
	  return cmap;
		}
>>>>>>> a807ff7d7941f99449e2199f946a8b116a90f1ff
}