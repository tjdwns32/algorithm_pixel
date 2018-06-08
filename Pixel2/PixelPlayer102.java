import java.awt.*;
import java.util.Random;

public class PixelPlayer102 extends Player {
	PixelPlayer102(int[][] map) {
		super(map);
	}
	public static void print(int[][] map){
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    System.out.println();
	    for(int j = 0;j<PixelTester.SIZE_OF_BOARD;j++){
	      System.out.print(map[i][j]+"\t");
	    }
	  }
	}
	
	
	
  //다소 공격적인 방법
	public Point nextPosition(Point lastPosition) {  
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();//상대방 돌의 마지막 위치
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();//내 돌의 마지막 위치
		int direction, count = 0;
		int myNum = map[(int)currentPosition.getX()][(int)currentPosition.getY()]; //상대방 1, 나는 2
		int checkPositionX;
		int checkPositionY;
		Point nextPosition;
		
//	int w = 100;
//	int cnt = 0;
		int bestX = 0; int bestY = 0;
		int maxX = 0; int maxY = 0;
		

		Random random = new Random();
		
    System.out.println("myPosition : "+cx+" "+cy);
    
    int [][] opMapX = new int[8][8]; //최적위치
    int [][] opMapY = new int[8][8];
    int [][] opMaplD = new int[8][8];
    int [][] opMaprD = new int[8][8];
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
    
    opMapX = horizonSearch(map);
    opMapY = verticalSearch(map);
    opMaplD = lDiagonalSearch(map);
    opMaprD = rDiagonalSearch(map);
    
    
    
    
    
    
    
		for( int n = 0; n < 8; n++ ){
			for( int m = 0; m < 8; m++ ){
				if( myNum == map[n][m] )//myNum: p100이면 1, p101이면 2{
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++){
					    if( (opMap[x][i] != -1) && (opMap[x][i] != 1) && (opMap[x][i] != 2) ){
					      if(opMap[x][i] > maxX){
					        maxX = opMap[x][i];
					        bestX = i;   
					      }      
					    }
					}
					System.out.println(maxX);
					System.out.println("opMap["+x+"]["+bestX+"] : "+ opMap[x][bestX]);
					
					for(int i = 0; i < PixelTester.SIZE_OF_BOARD; i++){
					    if( (opMap[i][y] != -1) && (opMap[i][y] != 1) && (opMap[i][y] != 2) ){
					      if(opMap[i][y] > maxY){
					        maxY = opMap[i][y];
					        bestY = i;   
					      }      
					    } 
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