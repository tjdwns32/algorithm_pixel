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
	
	public static int[][] verticalSearch(int[][] omap){
	  int w = 100;
		int cnt = 0;
		int bestX = 0; int bestY = 0;
		int maxX = 0; int maxY = 0;
    System.out.println("�������̺�");
	  int[][] opMapY = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
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
    return opMapY;
	}
	
	public static int[][] horizonSearch(int[][] omap){
	  int w = 100;
		int cnt = 0;
		int bestX = 0; int bestY = 0;
		int maxX = 0; int maxY = 0;
	  System.out.println("�������̺�");
	  int[][] opMapX = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
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
    return opMapX;
	}
	public static int[][] lDiagonalSearch(int[][] omap){
		  System.out.println("���� ������ ������ �Ʒ���");
		  int cnt = 0;
      int weight = 0;
      int temp = 0;
      //���� ������ ������ �Ʒ��� �밢�� Ž��
		  int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
		  for(int i = PixelTester.SIZE_OF_BOARD-1;i>=0;i--) {
		  //System.out.println("diag: "+i);
		  cnt =0;
		  temp = i;
			for(int j =0;temp<PixelTester.SIZE_OF_BOARD;temp++,j++){
			  if(omap[temp][j] == 1){//������ ������ ����ġ
			     cnt += 1;
			     //System.out.println("����ġ: ["+temp+"]["+j+"]");
			  }
			  else if(omap[temp][j] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			     //System.out.println("���� ����ġ :["+temp+"]["+j+"]");
			     weight = 100*cnt;
			     cmap[temp][j] = weight;
			     if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0)
			      cmap[temp-(cnt+1)][j-(cnt+1)] = weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
			     //System.out.println("���ܻ�Ȳ ����ġ: ["+temp+"]["+j+"]");
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
			  if(omap[i][temp] == 1){//������ ������ ����ġ
			     cnt += 1;
			     //System.out.println("����ġ: ["+i+"]["+temp+"]");
			  }
			  else if(omap[i][temp] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			     //System.out.println("���� ����ġ :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] = weight;
			     if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0)
			      cmap[i-(cnt+1)][temp-(cnt+1)] = weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
			     //System.out.println("���ܻ�Ȳ ����ġ: ["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     if(temp-cnt >= 0 && i-cnt >= 0)
			      cmap[i-cnt][temp-cnt] = weight; 
			     cnt = 0;
			  }	  
			}
	  }
	  return cmap;  
		}
		public static int[][] rDiagonalSearch(int[][] omap){
		  System.out.println("������ ������ ���� �Ʒ���");
		  int cnt = 0;
      int weight = 0;
      int temp = 0;
		  int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
		  //������ ������ ���ʾƷ��� �밢�� Ž��
		  for(int i = PixelTester.SIZE_OF_BOARD-1;i>=0;i--) {
		    //System.out.println("diag: "+i);
		    cnt =0;
		    temp = i;
			  for(int j =PixelTester.SIZE_OF_BOARD-1;temp<PixelTester.SIZE_OF_BOARD;temp++,j--){
			    if(omap[temp][j] == 1){//������ ������ ����ġ
			      cnt += 1;
			      //System.out.println("����ġ ������: ["+temp+"]["+j+"]");
			    }
			    else if(omap[temp][j] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			      //System.out.println("���� ����ġ :["+temp+"]["+j+"]");
			      weight = 100*cnt;
			      cmap[temp][j] += weight;
			      if(temp-(cnt+1) >= 0 && j+(cnt+1)<PixelTester.SIZE_OF_BOARD)
			      cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
			      cnt=0;
			    }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){ //N����ġ �ο��ϱ����� ������ ������
			    //System.out.println("���ܻ�Ȳ ����ġ: ["+temp+"]["+j+"]");
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
			  if(omap[i][temp] == 1){//������ ������ ����ġ
			     cnt += 1;
			     //System.out.println("����ġ: ["+i+"]["+temp+"]");
			  }
			  else if(omap[i][temp] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			     //System.out.println("���� ����ġ :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] += weight;
			     if(i-(cnt+1) >= 0 && temp+(cnt+1)<PixelTester.SIZE_OF_BOARD)
			      cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
			     cnt=0;
			  }
			  if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
			     //System.out.println("���ܻ�Ȳ ����ġ: ["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     if(i-cnt >= 0 && temp+cnt<PixelTester.SIZE_OF_BOARD)
			      cmap[i-cnt][temp+cnt] += weight; 
			     cnt = 0;
			  }	  
			}
	  }
	  return cmap;
		}
	
  //�ټ� �������� ���
	public Point nextPosition(Point lastPosition) {  
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();//���� ���� ������ ��ġ
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();//�� ���� ������ ��ġ
		int direction, count = 0;
		int myNum = map[(int)currentPosition.getX()][(int)currentPosition.getY()]; //���� 1, ���� 2
		int checkPositionX;
		int checkPositionY;
		Point nextPosition;
		
//	int w = 100;
//	int cnt = 0;
		int bestX = 0; int bestY = 0;
		int maxX = 0; int maxY = 0;
		

		Random random = new Random();
		
    System.out.println("myPosition : "+cx+" "+cy);
    
    int [][] opMapX = new int[8][8]; //������ġ
    int [][] opMapY = new int[8][8];
    int [][] opMaplD = new int[8][8];
    int [][] opMaprD = new int[8][8];
    int [][] opMap = new int[8][8]; //������ġ
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
    
    
    System.out.println("�������̺�");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(omap[i][j] + "\t ");
      }
      System.out.println();
    }
    
    System.out.println("�������̺�");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(opMapX[i][j] + "\t ");
      }
      System.out.println();
    }
    System.out.println("�������̺�");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(opMapY[i][j] + "\t ");
      }
      System.out.println();
    }
    System.out.println("�밢�����̺�1");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(opMaplD[i][j] + "\t ");
      }
      System.out.println();
    }
    System.out.println("�밢�����̺�2");
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(opMaprD[i][j] + "\t ");
      }
      System.out.println();
    }
    
    
    
    
		for( int n = 0; n < 8; n++ ){
			for( int m = 0; m < 8; m++ ){
				if( myNum == map[n][m] )//myNum: p100�̸� 1, p101�̸� 2{
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