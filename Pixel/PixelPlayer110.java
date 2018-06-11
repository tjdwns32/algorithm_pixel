import java.awt.*;
import java.util.Random;

public class PixelPlayer110 extends Player {
	PixelPlayer110(int[][] map) {
		super(map);
	}
	
   static int [][] opMap1 = new int[8][8]; 
   static int [][] opMap2 = new int[8][8]; 

	public Point nextPosition(Point lastPosition) {  
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();
		int myStone = (PixelTester.turn == 1?1:2);
	  int enemyStone = (PixelTester.turn == 1?2:1);
		Point nextPosition;
		
		//if(myStone == 1 && map[2][3] == 0) return new Point(2,3);
		
		int [][] opMapX = new int[8][8]; 
    int [][] opMapY = new int[8][8];
    int [][] opMaplD = new int[8][8];
    int [][] opMaprD = new int[8][8];
    int [][] opMap = new int[8][8]; 
    
    opMapX = horizonSearch(map,myStone);//가로 가중치 부여
    opMapY = verticalSearch(map,myStone);//세로 가중치 부여
    opMaplD = lDiagonalSearch(map,myStone);//왼쪽위->오른쪽 아래 대각선 가중치 부여
    opMaprD = rDiagonalSearch(map,myStone);//오른쪽위->왼쪽 아래 대각선 가중치 부여
    opMap1 = merge(opMapX,opMapY,opMaplD,opMaprD);//4개 가중치 그래프 병합
		
		opMapX = horizonSearch(map,enemyStone);//가로 가중치 부여
    opMapY = verticalSearch(map,enemyStone);//세로 가중치 부여
    opMaplD = lDiagonalSearch(map,enemyStone);//왼쪽위->오른쪽 아래 대각선 가중치 부여
    opMaprD = rDiagonalSearch(map,enemyStone);//오른쪽위->왼쪽 아래 대각선 가중치 부여
	  opMap2 = merge(opMapX,opMapY,opMaplD,opMaprD);//4개 가중치 그래프 병합
	  
	  opMap = merge2(opMap1,opMap2);
	  
	  System.out.println("110_상대테이블");
		print(opMap2);
		System.out.println("110_통합테이블");
		print(opMap);  
				
				
    int maxX = -1;
    int maxY = -1;
    Point posY = new Point();
    Point posX = new Point();
	  System.out.println("lastPosition: ["+x+"]["+y+"]");
	  
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    if(opMap1[x][i]>=300) return new Point(x,i);
	    if(opMap1[i][y]>=300) return new Point(i,y);
	    //놓을 수 있는 위치에 우리 돌 세개가 연결된 게 있으면 착수
	    if(!isDanger(x,i) && map[x][i] == 0){	  
	      if(opMap[x][i] > maxY){
	        maxY = opMap[x][i];
	        System.out.println("maxY"+maxY);
	        posY.setLocation(x,i);
	      }else if(opMap[x][i] == maxY){
	        if(y == (int)posY.getY()){
	           posY.setLocation(x,i);
	           break;
	        }else if(Math.abs(y-(int)posY.getY()) >= Math.abs(y-i)){ 
	          System.out.println("maxY"+maxY);
	          posY.setLocation(x,i);
	        }
	      }
	    }
	    if(!isDanger(i,y) && map[i][y] == 0){
	      if(opMap[i][y] > maxX){
	        maxX = opMap[i][y];
	        posX.setLocation(i,y);
	      }else if(opMap[i][y] == maxX){ 
	        if(x == (int)posX.getX()){
	          posX.setLocation(i,y);
	          break;
	        }else if(Math.abs(x-(int)posX.getX()) >= Math.abs(x-i)){
	          posX.setLocation(i,y);
	        } 
	      }
	    } 
	  }
	  
	  System.out.println("PosX: ["+posX.getX()+"]["+posX.getY()+"]"+ maxX);
	  System.out.println("PosY: ["+posY.getX()+"]["+posY.getY()+"]"+ maxY);
	  if(maxX > maxY) nextPosition = posX;
	  else if(maxX < maxY) nextPosition = posY;
	  else{
	    int randN = (int)(Math.random()*2)+1;
	    if(randN == 1) nextPosition = posX; 
	    else nextPosition = posY;
	  } 
	  
	  //System.out.println("(2) ["+nextPosition.getX()+"]["+nextPosition.getY()+"]"); 
	  return nextPosition;
	  
	}
	public boolean isDanger(int x,int y){
	  boolean danger = false;
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    if(opMap2[x][i] >= 300 && i!=y){
	      danger = true;
	      break;
	    }
	    if(opMap2[i][y] >= 300 && i!=x){
	      danger = true;
	      break;
	    }
	  }
	 return danger;  
	}
	//맵 출력 함수
	public void print(int[][] map){
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
	
	public static int[][] merge2(int opMap1[][], int opMap2[][]){
	  int[][] opMap = new int[8][8];
	  int maxW = 0;
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        maxW = Math.max(opMap1[i][j], opMap2[i][j]);
        opMap[i][j] += maxW;
        if(opMap1[i][j] == maxW){
          opMap[i][j] += ((opMap2[i][j])/10 );
        }else{
          opMap[i][j] += ((opMap1[i][j])/10 );
        }
      }
    }
	  return opMap;
	}
	
	//세로가중치
	public static int[][] verticalSearch(int[][] omap,int myStone){
	  int w = 100; //가중치
		int cnt = 0; //붙어있는 돌의 수
	  int[][] opMapY = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[j][i] == myStone){ //현재 돌의 위치를 찾으면
          cnt++; //돌의 수를 증가       
          while(true){ //붙어있는 돌을 확인
            if((j+cnt) < 8){ //맵의 크기를 벗어나지 않는 선에서
              if(omap[j+cnt][i] == myStone) //붙어있는 돌이 존재하면 카운트하고
                ++cnt;
              else if(omap[j+cnt][i] == 0){ //돌을 둘수있는 위치이면 세로가중치 맵을 검사
                opMapY[j+cnt][i] += (cnt * w);
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
          }
          
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
             if ((omap[j-1][i] == 0) ){ //돌을 둘 수 있는 위치이면
              opMapY[j-1][i] += (cnt * w);  
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
	public static int[][] horizonSearch(int[][] omap,int myStone){
	  int w = 100; //가중치
		int cnt = 0; //붙어있는 돌의 수
	  int[][] opMapX = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[i][j] == myStone){ //현재 돌의 위치를 찾으면
          cnt++; //돌의 수를 증가
          
          while(true){ //붙어있는 돌을 확인
            if((j+cnt) < 8){ //맵의 크기를 벗어나지 않는 선에서
              if(omap[i][j+cnt] == myStone){ //붙어있는 돌이 존재하면 카운트하
                ++cnt;
              }
              else if(omap[i][j+cnt] == 0){ //돌을 둘수있는 위치이면 가로가중치 맵을 검사
                opMapX[i][j+cnt] += (cnt * w);
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
          }
          
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
             if ((omap[i][j-1] == 0) ){ //돌을 둘 수 있는 위치이면
              opMapX[i][j-1] += (cnt * w);  
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
	public static int[][] lDiagonalSearch(int[][] omap,int myStone){
      //System.out.println("왼쪽 위에서 오른쪽 아래로");
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      //왼쪽 위에서 오른쪽 아래로 대각선 탐색
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--){
      //System.out.println("diag: "+i);
      cnt =0;
      temp = i;
      for(int j =0;temp<PixelTester.SIZE_OF_BOARD;temp++,j++){
        if(omap[temp][j] == myStone){//내돌이 있으면 가중치
          cnt += 1;
          //System.out.println("가중치: ["+temp+"]["+j+"]");
        }
        else if(omap[temp][j] !=  myStone && cnt != 0){//돌의 연결이 끝나면 가중치부여
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
        if(omap[i][temp] == myStone){//내돌이 있으면 가중치
          cnt += 1;
          //System.out.println("가중치: ["+i+"]["+temp+"]");
        }
        else if(omap[i][temp] != myStone && cnt != 0){//돌의 연결이 끝나면 가중치부여
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
		
		//대각선가중치 - 오른쪽 위에서 왼쪽 아래로
		public static int[][] rDiagonalSearch(int[][] omap,int myStone){
      //System.out.println("오른쪽 위에서 왼쪽 아래로");
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      //오른쪽 위에서 왼쪽아래로 대각선 탐색
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--) {
        //System.out.println("diag: "+i);
        cnt =0;
        temp = i;
        for(int j =PixelTester.SIZE_OF_BOARD-1;temp<PixelTester.SIZE_OF_BOARD;temp++,j--){
          if(omap[temp][j] == myStone){//내돌이 있으면 가중치
            cnt += 1;
            //System.out.println("가중치 시작점: ["+temp+"]["+j+"]");
          }
          else if(omap[temp][j] != myStone && cnt != 0){//돌의 연결이 끝나면 가중치부여
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
        if(omap[i][temp] == myStone){//내돌이 있으면 가중치
          cnt += 1;
          //System.out.println("가중치: ["+i+"]["+temp+"]");
        }
        else if(omap[i][temp] != myStone && cnt != 0){//돌의 연결이 끝나면 가중치부여
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
}