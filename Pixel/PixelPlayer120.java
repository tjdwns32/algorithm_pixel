import java.awt.*;
import java.util.Random;

public class PixelPlayer120 extends Player {
	PixelPlayer120(int[][] map) {
		super(map);
	}
	 static int myStone;
	 static int enemyStone;
	
   static int [][] opMap1 = new int[8][8]; 
   static int [][] opMap2 = new int[8][8]; 
   static int [][] opMap = new int[8][8]; 
   
   public void setStone(){//turn을 기준으로 내 돌/ 상대 돌 파악 
    if(PixelTester.turn == 1){
      myStone = 1;
      enemyStone = 2;
    }else{
      myStone = 2;
      enemyStone = 1;
    }
  }

	public Point nextPosition(Point lastPosition) {  
	  setStone();//돌 파악
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();
		Point nextPosition = new Point();
		
		if(myStone == 1 && map[2][3] == 0) return new Point(2,3);
		
		int [][] opMapX = new int[8][8]; 
    int [][] opMapY = new int[8][8];
    int [][] opMaplD = new int[8][8];
    int [][] opMaprD = new int[8][8];
    int [][] opMap = new int[8][8]; 
    
    //내 돌 위치기준 가중치 파악
    opMapX = horizonSearch(map,myStone);//가로 가중치 부여
    opMapY = verticalSearch(map,myStone);//세로 가중치 부여
    opMaplD = lDiagonalSearch(map,myStone);//왼쪽위->오른쪽 아래 대각선 가중치 부여
    opMaprD = rDiagonalSearch(map,myStone);//오른쪽위->왼쪽 아래 대각선 가중치 부여
    opMap1 = merge(opMapX,opMapY,opMaplD,opMaprD);//4개 가중치 그래프 병합
		
		//상대 돌 위치기준 가중치 파악
		opMapX = horizonSearch(map,enemyStone);//가로 가중치 부여
    opMapY = verticalSearch(map,enemyStone);//세로 가중치 부여
    opMaplD = lDiagonalSearch(map,enemyStone);//왼쪽위->오른쪽 아래 대각선 가중치 부여
    opMaprD = rDiagonalSearch(map,enemyStone);//오른쪽위->왼쪽 아래 대각선 가중치 부여
	  opMap2 = merge(opMapX,opMapY,opMaplD,opMaprD);//4개 가중치 그래프 병합
	  
	  //통합 가중치 계산(위치별 최대값 + sum(나머지 값들)/10)
	  opMap = merge2(opMap1,opMap2);

    int maxX = -1;//lastPosition기준 x좌표 가중치 최대값
    int maxY = -1;//lastPosition기준 y좌표 가중치 최대값
    Point posY = new Point();
    Point posX = new Point();
	  
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    //놓을 수 있는 위치에 우리 돌 세개가 연결된 게 있으면 바로 착수
	    if(opMap1[x][i]>=300) return new Point(x,i);
	    if(opMap1[i][y]>=300) return new Point(i,y);
	    
	    //lastLocation기준 y좌표 이동시 유력한 후보 선택
	    //isDanger: 후보 위치에서 이동가능한 빈자리중에 상대방 돌 세개가 이어진게 있나 확인
	    if(!isDanger(x,i) && map[x][i] == 0){	  
	      //가중치가 제일 큰 위치를 후보로 선택
	      if(opMap[x][i] > maxY){
	        maxY = opMap[x][i];
	        posY.setLocation(x,i);
	      }else if(opMap[x][i] == maxY){
	        //가중치가 같으면 lastPosition과 가까운 위치에 있는 돌 선택
	        if(y == (int)posY.getY()){
	           //posY가 설정되지 않았을 때는 처음 들어오는 위치를 기준으로 삼음
	           posY.setLocation(x,i);
	           break;
	        }else if(Math.abs(y-(int)posY.getY()) >= Math.abs(y-i)){ 
	          posY.setLocation(x,i);
	        }
	      }
	    }
	    //lastLocation기준 x좌표 이동시 유력한 후보 선택
	    if(!isDanger(i,y) && map[i][y] == 0){
	      //가중치가 제일 큰 위치를 후보로 선택
	      if(opMap[i][y] > maxX){
	        maxX = opMap[i][y];
	        posX.setLocation(i,y);
	      }else if(opMap[i][y] == maxX){ 
	        //가중치가 같으면 lastPosition과 가까운 위치에 있는 돌 선택
	        if(x == (int)posX.getX()){
	          //posX가 설정되지 않았을 때는 처음 들어오는 위치를 기준으로 삼음
	          posX.setLocation(i,y);
	          break;
	        }else if(Math.abs(x-(int)posX.getX()) >= Math.abs(x-i)){
	          posX.setLocation(i,y);
	        } 
	      }
	    } 
	  }
	  //x좌표 이동시 유력 후보와 y좌표 이동시 유력후보 비교
	  if(maxX > maxY) nextPosition = posX;
	  else if(maxX < maxY) nextPosition = posY;
	  else{
	    if(maxX == -1 && maxY == -1){ //상대방이 이길 수 있는 위치 밖에 둘 수 없는 경우
	        for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){ //행이나 열 비어있는 곳 아무대나 둠
	            if(map[x][i] == 0){ 
	              nextPosition.setLocation(x,i);
  	            break;  
	            }
	            if(map[i][y] == 0){
	              nextPosition.setLocation(i,y);
    	          break;  
	            }  
	        }
	    }else{
	      //두 후보 위치의 가중치가 같을 경우 둘 중 하나 랜덤으로 반환
	      int randN = (int)(Math.random()*2)+1;
	      nextPosition = (randN == 1) ? posX : posY;
	    }
	  } 
	  return nextPosition;
	}
	//isPromising(), isLPromising(), isRPromising(): 가중치를 부여하기전에 앞뒤 위치를 파악해서 4목이 나올 가능성이 있는지 확인
	//4목이 나올 가능성이 없으면가중치를 부여하지 않는다
	//가로/세로 앞뒤(+- 3)를 살펴서 4목이 나올 수 있는 충분한 공간적 여유가 있는지 확인
	public boolean isPromising(int stone, int locX,int locY, int axis){
	  int count = 0;
	  //해당 위치 x좌표 앞뒤로 3개 자리 확인해서 연속된 4자리가 있으면 가중치 부여
	  if(axis == 0){
    	for(int i = locX - 3; i <= locX + 3; i++ ) {
    	    //보드 범위를 넘어가면 패스
    			if ( i < 0 || i >= PixelTester.SIZE_OF_BOARD ) {
    				continue;
    			}
    			if ( map[i][locY] == stone || map[i][locY] == 0) {
    				count++;
    				if ( count == 4 ) {
    					return true;
    				}
    			} else {
    			  //연결이 끊어지면 0부터 다시 카운트
    				count = 0;
    			}
    		}
    	}
      else{
        //해당 위치 y좌표 앞뒤로 3개 자리 확인해서 연속된 4자리가 있으면 가중치 부여
    		for(int i = locY - 3; i <= locY + 3; i++ ) {
    		  //보드 범위를 넘어가면 패스
    			if ( i < 0 || i >= PixelTester.SIZE_OF_BOARD ) {
    				continue;
    			}
    			if ( map[locX][i] == stone || map[locX][i] == 0 ) {
    				count++;
    				if ( count == 4 ) {
    					return true;
    				}
    			} else {
    			  //연결이 끊어지면 0부터 다시 카운트
    				count = 0;
    			}
    		} 
    	} 
    	return false;
	}
	//왼쪽 위에서 오른쪽 아래로 내려가는 방향에서 4목이 나올 수있는 자리가 충분한가 확인
	public boolean isLPromising(int x, int y, int stone) {
		int i;
		int count = 0;
		for( i = -3; i <= 3; i++ ) {
		  //보드 범위를 넘어가면 패스
			if ( x + i < 0 || y + i < 0 || x + i >= PixelTester.SIZE_OF_BOARD || y + i >= PixelTester.SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[x + i][y + i] == stone || map[x + i][y + i] == 0 ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
    	  //연결이 끊어지면 0부터 다시 카운트
				count = 0;
			}
		}
		return false;	
  }
  //오른쪽 위에서 왼쪽 아래로 내려가는 방향에서 4목이 나올 수있는 자리가 충분한가 확인
  public boolean isRPromising(int x, int y, int stone) {
		int i;
		int count = 0;
		
		for( i = -3; i <= 3; i++ ) {
		  //보드 범위를 넘어가면 패스
			if ( x + i < 0 || y - i < 0 || x + i >= PixelTester.SIZE_OF_BOARD || y - i >= PixelTester.SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[x + i][y - i] == stone || map[x + i][y - i] == 0 ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
        //연결이 끊어지면 0부터 다시 카운트
				count = 0;
			}
		}    
		return false;	
  }
	//후보 위치 기준에서 이동가능한 빈자리중에 상대방이 한번만 더 두면 4목이 나오는 자리가 있으면 감지해서 피함
	public boolean isDanger(int x,int y){
	  boolean danger = false;
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    //같은 색 돌이 3개 이상 이어져 있거나 1개와 2개가 사이에 빈공간이 있을 경우 가중치는 300이상임
	    //y좌표중에 300넘는게 있나 확인
	    if(opMap2[x][i] >= 300 && i!=y){
	      danger = true;
	      break;
	    }
	    //x좌표중에 300넘는게 있나 확인
	    if(opMap2[i][y] >= 300 && i!=x){
	      danger = true;
	      break;
	    }
	  }
	 return danger;  
	}
	//맵 출력 함수, 디버깅용
	public void print(int[][] map){
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(map[i][j] + "\t ");
      }
      System.out.println();
      System.out.println();
    }
	}
	
	//가중치 맵 merge함수, 가로/세로/왼쪽대각선/오른쪽대각선 가중치를 하나로 합침
	//4개 맵중 가장 큰 수에 나머지 값 3개의 합을 10으로 나눈 값을 더해서 가중치 부여
	public static int[][] merge(int hMap[][], int vMap[][], int lDMap[][], int rDMap[][]){
	  int[][] opMap = new int[8][8];
	  int maxW = 0;
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        //가중치가 가장 큰 값 찾기
        maxW = Math.max(hMap[i][j], vMap[i][j]);
        maxW = Math.max(maxW, lDMap[i][j]);
        maxW = Math.max(maxW, rDMap[i][j]);
        //큰 값을 기준으로 가중치 더하기
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
	//상대방기준 가중치와 내 기준 가중치를 합해서 공격/수비 둘 다 고려한 가중치 계산
	//위 와 같이 더 큰 값에 나머지 값을 10으로 나눈 값을 더함
	public static int[][] merge2(int opMap1[][], int opMap2[][]){
	  int[][] opMap = new int[8][8];
	  int maxW = 0;
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        //두 맵중 더 큰값을 기준으로 설정
        maxW = Math.max(opMap1[i][j], opMap2[i][j]);
        opMap[i][j] += maxW;
        //큰 값에 다른 값의 10%가중
        if(opMap1[i][j] == maxW){
          opMap[i][j] += ((opMap2[i][j])/10 );
        }else{
          opMap[i][j] += ((opMap1[i][j])/10 );
        }
      }
    }
	  return opMap;
	}
	//가로세로 가중치는 100
	
	//세로가중치맵 생성
	public  int[][] verticalSearch(int[][] omap,int myStone){
	  int w = 100; //가중치
		int cnt = 0; //붙어있는 돌의 수
	  int[][] opMapY = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];//반환할 가중치맵
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[j][i] == myStone){ //현재 돌의 위치를 찾으면
          cnt++; //돌의 수를 증가       
          while(true){ //붙어있는 돌을 확인
            if((j+cnt) < 8){ //맵의 크기를 벗어나지 않는 선에서
              if(omap[j+cnt][i] == myStone) //붙어있는 돌이 존재하면 카운트하고
                ++cnt;
              else if(omap[j+cnt][i] == 0){ //돌을 둘수있는 위치이면 세로가중치 맵을 검사
                if(isPromising(myStone,j+cnt,i,0))
                  opMapY[j+cnt][i] += (cnt * w);
                else
                  opMapY[j+cnt][i] = 0;  
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
          }
          
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
             if ((omap[j-1][i] == 0) ){ //돌을 둘 수 있는 위치이면
              if(isPromising(myStone,j-1,i,0))
                opMapY[j-1][i] += (cnt * w);  
              else
                opMapY[j-1][i] = 0;  
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
	
	//가로가중치맵 생성
	public  int[][] horizonSearch(int[][] omap,int myStone){
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
                if(isPromising(myStone,i,j+cnt,1))
                  opMapX[i][j+cnt] += (cnt * w);
                else
                   opMapX[i][j+cnt] = 0;
                break;
              }
              else break; //돌을 둘 수 있는 자리가 아닌경우 종료
            }else break; //맵크기를 벗어나면 종료
          }
          
          // 현재돌의 전 위치를 검사해서
          if(((j-1)!= -1)){ //맵의 크기를 벗어나지 않고
            if ((omap[i][j-1] == 0) ){ //돌을 둘 수 있는 위치이면
              if(isPromising(myStone,i,j-1,1))
                opMapX[i][j-1] += (cnt * w);  
              else
                opMapX[i][j-1] = 0;  
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
	//대각선 가중치는 110
	
  //왼쪽 위에서 오른쪽 아래 대각선 가중치 맵 생성
	public int[][] lDiagonalSearch(int[][] omap,int Stone){
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      //그래프 중앙선 아래쪽 대각선 탐색
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--){
        cnt =0;
        temp = i;
        for(int j =0;temp<PixelTester.SIZE_OF_BOARD;temp++,j++){
          if((omap[temp][j] == Stone)&&isLPromising(temp,j,Stone)){//내돌이 있으면 카운트시작
            cnt += 1;
          }else if(omap[temp][j] !=  Stone && cnt != 0){//돌의 연결이 끝나면 가중치부여
            weight = 110*cnt;
            if(omap[temp][j] == 0) cmap[temp][j] += weight;//현재 위치가 비어있으면 가중치 부여
            if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0 && omap[temp-(cnt+1)][j-(cnt+1)]==0)
              cmap[temp-(cnt+1)][j-(cnt+1)] += weight; 
              //가중치 시작위치의 전 위치에도 비어있으면 가중치 부여
            cnt=0;//가중치 부여 후 카운트 초기화
          }
          if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 6)) && cnt != 0){
            //가중치 부여하기전에 연결이 끝날(보드의 가장자리를 만났을 때)
            weight = 110*cnt;
            //다음 위치가 없으므로 전위치에서 미리 확인하고 가중치 시작위치의 전 위치가 비어있으면 거기에 가중치부여  
            if(temp-cnt >= 0 && j-cnt >= 0 && omap[temp-cnt][j-cnt]==0)
              cmap[temp-cnt][j-cnt] += weight; 
            cnt =0;
          }     
        }
      }
     //그래프 중앙선 위쪽 대각선 탐색
     for(int j=1;j<PixelTester.SIZE_OF_BOARD;j++){
      cnt =0;
      temp = j;
      for(int i =0;temp<PixelTester.SIZE_OF_BOARD;temp++,i++){
        if((omap[i][temp] == Stone)&&isLPromising(i,temp,Stone)){//내돌이 있으면 카운트
          cnt += 1;
        }
        else if(omap[i][temp] != Stone && cnt != 0){//돌의 연결이 끝나면 가중치부여
          weight = 110*cnt;
          if(omap[i][temp] == 0)
            cmap[i][temp] += weight;//현재 위치가 비어있으면 가중치 부여
          if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0 && omap[i-(cnt+1)][temp-(cnt+1)]==0)
            cmap[i-(cnt+1)][temp-(cnt+1)] += weight; 
            //가중치 시작위치의 전 위치에도 비어있으면 가중치 부여
          cnt=0;
        }
        if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
          weight = 110*cnt;
          if(temp-cnt >= 0 && i-cnt >= 0 && omap[i-cnt][temp-cnt]==0)
            cmap[i-cnt][temp-cnt] += weight; 
          cnt = 0;
        }     
      }
     }
     return cmap;  
    }
		
		 //오른쪽 위에서 왼쪽 아래 대각선 가중치 맵 생성
		public int[][] rDiagonalSearch(int[][] omap,int Stone){
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      //그래프 중앙선 아래쪽 탐색
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--) {
        cnt =0;
        temp = i;
        for(int j =PixelTester.SIZE_OF_BOARD-1;temp<PixelTester.SIZE_OF_BOARD;temp++,j--){
          if(omap[temp][j] == Stone &&isRPromising(temp,j,Stone)){//내돌이 있으면 카운트
            cnt += 1;
          }
          else if(omap[temp][j] != Stone && cnt != 0){//돌의 연결이 끝나면 가중치부여
            weight = 110*cnt;
            if(omap[temp][j] == 0)//현재 위치 가중치 부여
              cmap[temp][j] += weight;
            if(temp-(cnt+1) >= 0 && j+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[temp-(cnt+1)][j+(cnt+1)]==0)
              //가중치 시작위치의 전 위치에도 비어있으면 가중치 부여
              cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
            cnt=0;
          }
          if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 1)) && cnt != 0){ //가중치 부여하기전에 연결이 끝날때
            weight = 110*cnt;
            if(temp-cnt >= 0 && j+cnt<PixelTester.SIZE_OF_BOARD && omap[temp-cnt][j+cnt]==0)
              cmap[temp-cnt][j+cnt] += weight; 
              //가중치 시작위치의 전 위치가 비어있으면 가중치 부여
            cnt =0;
          }     
       }
     }
     //그래프 중앙선 위쪽 탐색
    for(int j= 1;j<PixelTester.SIZE_OF_BOARD-1;j++) {
      cnt =0;
      temp = j;
      for(int i =0;temp>=0;temp--,i++){
        if((omap[i][temp] == Stone )&&isRPromising(i,temp,Stone)){//내돌이 있으면 가중치
          cnt += 1;
        }
        else if(omap[i][temp] != Stone && cnt != 0){//돌의 연결이 끝나면 가중치부여
          weight = 110*cnt;
          if(omap[i][temp]==0)
            cmap[i][temp] += weight;//현재 위치가 비어있으면 가중치 부여
          if(i-(cnt+1) >= 0 && temp+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[i-(cnt+1)][temp+(cnt+1)]==0)
            cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
            //가중치 시작위치의 전 위치에도 비어있으면 가중치 부여
          cnt=0;
        }
        if(temp == 0 && cnt != 0){//가중치 부여하기전에 연결이 끝날때
          weight = 110*cnt;
          if(i-cnt >= 0 && temp+cnt<PixelTester.SIZE_OF_BOARD && omap[i-cnt][temp+cnt]==0)
            cmap[i-cnt][temp+cnt] += weight; 
            //가중치 시작위치의 전 위치가 비어있으면 가중치 부여
          cnt = 0;
        }     
     }
    }
    return cmap;
  }
}