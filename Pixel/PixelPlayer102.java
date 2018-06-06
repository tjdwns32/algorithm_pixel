import java.awt.*;
import java.util.Random;

public class PixelPlayer102 extends Player {
	PixelPlayer102(int[][] map) {
		super(map);
	}
  //다소 공격적인 방법ㅏ
	public Point nextPosition(Point lastPosition) {  
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();//상대방 돌의 마지막 위치
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();//내 돌의 마지막 위치
		int direction, count = 0;
		int myNum = map[(int)currentPosition.getX()][(int)currentPosition.getY()]; //상대방 1, 나는 2
		int checkPositionX;
		int checkPositionY;
		Point nextPosition;
		
		
		int w = 100;
		int cnt = 0;
		int bestX = 0; int bestY = 0;
		int maxX = 0; int maxY = 0;

		Random random = new Random();
		
    System.out.println("myPosition : "+cx+" "+cy);
    
    int [][] opMapX = new int[8][8]; //최적위치
    int [][] opMapY = new int[8][8];
    int [][] opMap = new int[8][8]; //최적위치
    
    int [][] omap = 
          {{1,0,0,0,0,0,1,0},
           {0,0,1,0,0,0,0,1},
           {0,0,1,1,1,0,0,0},
           {0,1,0,0,0,1,0,0},
           {0,0,1,0,0,0,0,0},
           {0,0,0,0,0,0,1,0},
           {0,1,0,0,0,1,0,0},
           {1,0,0,0,1,0,0,0}};
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        opMapX[i][j] = 0;
        opMapY[i][j] = 0;
        opMap[i][j] = 0;
      }
    }
    
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[i][j] == 1){
          cnt++;
          
          while(true){
            if((j+cnt) < 8){
              if(omap[i][j+cnt] == 1)
                ++cnt;
              else if(omap[i][j+cnt] == 0){ 
                if(opMapX[j+cnt][i] >= 100) opMapX[i][j+cnt] = opMapX[i][j+cnt] + (cnt * w);
                else opMapX[i][j+cnt] = (cnt * w);
                break;
              }
              else break;
            }else break;
          }
          
          if(((j-1)!= -1)){
             if ( (omap[i][j-1] == 0) )
              opMapX[i][j-1] = opMapX[i][j-1] + (cnt * w);
          }
          if((j+cnt)<8) j+=cnt;
          cnt = 0;
       }
      }
    }
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[j][i] == 1){
          cnt++;
          
          while(true){
            if((j+cnt) < 8){
              if(omap[j+cnt][i] == 1)
                ++cnt;
              else if(omap[j+cnt][i] == 0){
                if(opMapY[j+cnt][i] >= 100) opMapY[j+cnt][i] = opMapY[j+cnt][i] + (cnt * w);
                else
                  opMapY[j+cnt][i] = cnt * w;
                break;
              }
              else break;
            }else break;
          }
          
          if(((j-1)!= -1)){
             if ( (omap[j-1][i] == 0) )
              opMapY[j-1][i] = opMapY[j-1][i] + (cnt * w);
          }
          if((j+cnt)<8) j+=cnt;
          cnt = 0;
       }
      }
    }
    System.out.println("원본테이블");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(omap[i][j] + "\t ");
      }
      System.out.println();
    }
    
    System.out.println("가로테이블");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(opMapX[i][j] + "\t ");
      }
      System.out.println();
    }
    
    System.out.println("세로테이블");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(opMapY[i][j] + "\t ");
      }
      System.out.println();
    }
        
    
//    for(int i = 0; i < 8; i++){
//      for(int j = 0; j < 8; j++){
//        if(opMap[i][j] != opMapX[i][j]){
//          opMap[i][j] = opMapX[i][j];
//        }else if(opMap[i][j] != opMapX[i][j]){
//          opMap[i][j] = opMapX[i][j];
//        }
//      }
//    }
    
    
//    System.out.println("--Map[][]--");
//    for(int i = 0; i < 8; i++){
//      for(int j = 0; j < 8; j++){
//        System.out.print(map[i][j] + "\t ");
//      }
//      System.out.println();
//    }
//    
//    System.out.println("--opMap[][]--");
//    for(int i = 0; i < 8; i++){
//      for(int j = 0; j < 8; j++){
//        System.out.print(opMap[i][j] + "\t ");
//      }
//      System.out.println();
//    }
//    
   
		for( int n = 0; n < 8; n++ )
		{
			for( int m = 0; m < 8; m++ )
			{
				if( myNum == map[n][m] )//myNum: p100이면 1, p101이면 2
				{
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++)
					{
					    if( (opMap[x][i] != -1) && (opMap[x][i] != 1) && (opMap[x][i] != 2) ){
					      if(opMap[x][i] > maxX){
					        maxX = opMap[x][i];
					        bestX = i;   
					      }      
					    }
//					    nextPosition = new Point(x, i);
//							System.out.println(nextPosition);
//		    			return nextPosition;
//					  
//						if(map[i][y] == 0 && ( i == n || y == m ))//대각선이동 방지하면서 x축이동
//						{
//							nextPosition = new Point(i, y);
//							System.out.println(nextPosition);
//							return nextPosition;
//						}
              
					}
					System.out.println(maxX);
					System.out.println("opMap["+x+"]["+bestX+"] : "+ opMap[x][bestX]);
					
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++)
					{
					    if( (opMap[i][y] != -1) && (opMap[i][y] != 1) && (opMap[i][y] != 2) ){
					      if(opMap[i][y] > maxY){
					        maxY = opMap[i][y];
					        bestY = i;   
					      }      
					    }
					  
//            if( (map[i][y] == 0) && (opMap[i][y] == 100)){
//					      nextPosition = new Point(i, y);
//							  System.out.println(nextPosition);
//							  return nextPosition;
//					  }						  
					}
					
					System.out.println(maxY);
					System.out.println("opMap["+bestY+"]["+y+"] : "+ opMap[bestY][y]);
					
					if(maxX >= maxY){
				    nextPosition = new Point(x,bestX);
            System.out.println(nextPosition);
            return nextPosition;
					}else if(maxX < maxY){
					  nextPosition = new Point(bestY,y);
            System.out.println(nextPosition);
            return nextPosition;
					}
				}
			}
		}

		nextPosition = new Point(x, y);
		return nextPosition;
	}
	
	
}