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
    
    opMapX = horizonSearch(map,myStone);//���� ����ġ �ο�
    opMapY = verticalSearch(map,myStone);//���� ����ġ �ο�
    opMaplD = lDiagonalSearch(map,myStone);//������->������ �Ʒ� �밢�� ����ġ �ο�
    opMaprD = rDiagonalSearch(map,myStone);//��������->���� �Ʒ� �밢�� ����ġ �ο�
    opMap1 = merge(opMapX,opMapY,opMaplD,opMaprD);//4�� ����ġ �׷��� ����
		
		opMapX = horizonSearch(map,enemyStone);//���� ����ġ �ο�
    opMapY = verticalSearch(map,enemyStone);//���� ����ġ �ο�
    opMaplD = lDiagonalSearch(map,enemyStone);//������->������ �Ʒ� �밢�� ����ġ �ο�
    opMaprD = rDiagonalSearch(map,enemyStone);//��������->���� �Ʒ� �밢�� ����ġ �ο�
	  opMap2 = merge(opMapX,opMapY,opMaplD,opMaprD);//4�� ����ġ �׷��� ����
	  
	  opMap = merge2(opMap1,opMap2);
	  
	  System.out.println("110_������̺�");
		print(opMap2);
		System.out.println("110_�������̺�");
		print(opMap);  
				
				
    int maxX = -1;
    int maxY = -1;
    Point posY = new Point();
    Point posX = new Point();
	  System.out.println("lastPosition: ["+x+"]["+y+"]");
	  
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    if(opMap1[x][i]>=300) return new Point(x,i);
	    if(opMap1[i][y]>=300) return new Point(i,y);
	    //���� �� �ִ� ��ġ�� �츮 �� ������ ����� �� ������ ����
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
	//�� ��� �Լ�
	public void print(int[][] map){
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(map[i][j] + "\t ");
      }
      System.out.println();
      System.out.println();
    }
	}
	
	//����ġ �� merge�Լ�
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
	
	//���ΰ���ġ
	public static int[][] verticalSearch(int[][] omap,int myStone){
	  int w = 100; //����ġ
		int cnt = 0; //�پ��ִ� ���� ��
	  int[][] opMapY = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[j][i] == myStone){ //���� ���� ��ġ�� ã����
          cnt++; //���� ���� ����       
          while(true){ //�پ��ִ� ���� Ȯ��
            if((j+cnt) < 8){ //���� ũ�⸦ ����� �ʴ� ������
              if(omap[j+cnt][i] == myStone) //�پ��ִ� ���� �����ϸ� ī��Ʈ�ϰ�
                ++cnt;
              else if(omap[j+cnt][i] == 0){ //���� �Ѽ��ִ� ��ġ�̸� ���ΰ���ġ ���� �˻�
                opMapY[j+cnt][i] += (cnt * w);
                break;
              }
              else break; //���� �� �� �ִ� �ڸ��� �ƴѰ�� ����
            }else break; //��ũ�⸦ ����� ����
          }
          
          // ���絹�� �� ��ġ�� �˻��ؼ�
          if(((j-1)!= -1)){ //���� ũ�⸦ ����� �ʰ�
             if ((omap[j-1][i] == 0) ){ //���� �� �� �ִ� ��ġ�̸�
              opMapY[j-1][i] += (cnt * w);  
            }
          }
          
          //�پ��ִ� ���� �� ��ŭ �̹� ���� �˻������Ƿ� �ǳʶ�
          if((j+cnt)<8) j+=cnt;
          cnt = 0;
       }
      }
    }
    return opMapY;
	}
	
	//���ΰ���ġ
	public static int[][] horizonSearch(int[][] omap,int myStone){
	  int w = 100; //����ġ
		int cnt = 0; //�پ��ִ� ���� ��
	  int[][] opMapX = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[i][j] == myStone){ //���� ���� ��ġ�� ã����
          cnt++; //���� ���� ����
          
          while(true){ //�پ��ִ� ���� Ȯ��
            if((j+cnt) < 8){ //���� ũ�⸦ ����� �ʴ� ������
              if(omap[i][j+cnt] == myStone){ //�پ��ִ� ���� �����ϸ� ī��Ʈ��
                ++cnt;
              }
              else if(omap[i][j+cnt] == 0){ //���� �Ѽ��ִ� ��ġ�̸� ���ΰ���ġ ���� �˻�
                opMapX[i][j+cnt] += (cnt * w);
                break;
              }
              else break; //���� �� �� �ִ� �ڸ��� �ƴѰ�� ����
            }else break; //��ũ�⸦ ����� ����
          }
          
          // ���絹�� �� ��ġ�� �˻��ؼ�
          if(((j-1)!= -1)){ //���� ũ�⸦ ����� �ʰ�
             if ((omap[i][j-1] == 0) ){ //���� �� �� �ִ� ��ġ�̸�
              opMapX[i][j-1] += (cnt * w);  
            }
          }
          
          //�پ��ִ� ���� �� ��ŭ �̹� ���� �˻������Ƿ� �ǳʶ�
          if((j+cnt)<8) j+=cnt;
          cnt = 0;
       }
      }
    }
    return opMapX;
	}
	public static int[][] lDiagonalSearch(int[][] omap,int myStone){
      //System.out.println("���� ������ ������ �Ʒ���");
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      //���� ������ ������ �Ʒ��� �밢�� Ž��
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--){
      //System.out.println("diag: "+i);
      cnt =0;
      temp = i;
      for(int j =0;temp<PixelTester.SIZE_OF_BOARD;temp++,j++){
        if(omap[temp][j] == myStone){//������ ������ ����ġ
          cnt += 1;
          //System.out.println("����ġ: ["+temp+"]["+j+"]");
        }
        else if(omap[temp][j] !=  myStone && cnt != 0){//���� ������ ������ ����ġ�ο�
          //System.out.println("���� ����ġ :["+temp+"]["+j+"]");
          weight = 100*cnt;
          if(omap[temp][j] == 0)
          cmap[temp][j] += weight;
          if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0 && omap[temp-(cnt+1)][j-(cnt+1)]==0)
            cmap[temp-(cnt+1)][j-(cnt+1)] += weight; 
          cnt=0;
        }
        if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 6)) && cnt != 0){//����ġ �ο��ϱ����� ������ ������
          //System.out.println("���ܻ�Ȳ ����ġ: ["+temp+"]["+j+"]");
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
        if(omap[i][temp] == myStone){//������ ������ ����ġ
          cnt += 1;
          //System.out.println("����ġ: ["+i+"]["+temp+"]");
        }
        else if(omap[i][temp] != myStone && cnt != 0){//���� ������ ������ ����ġ�ο�
          //System.out.println("���� ����ġ :["+i+"]["+temp+"]");
          weight = 100*cnt;
          if(omap[i][temp] == 0)
            cmap[i][temp] += weight;
          if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0 && omap[i-(cnt+1)][temp-(cnt+1)]==0)
            cmap[i-(cnt+1)][temp-(cnt+1)] += weight; 
          cnt=0;
        }
        if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
          //System.out.println("���ܻ�Ȳ ����ġ: ["+i+"]["+temp+"]");
          weight = 100*cnt;
          if(temp-cnt >= 0 && i-cnt >= 0 && omap[i-cnt][temp-cnt]==0)
            cmap[i-cnt][temp-cnt] += weight; 
          cnt = 0;
        }     
      }
     }
     return cmap;  
    }
		
		//�밢������ġ - ������ ������ ���� �Ʒ���
		public static int[][] rDiagonalSearch(int[][] omap,int myStone){
      //System.out.println("������ ������ ���� �Ʒ���");
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      //������ ������ ���ʾƷ��� �밢�� Ž��
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--) {
        //System.out.println("diag: "+i);
        cnt =0;
        temp = i;
        for(int j =PixelTester.SIZE_OF_BOARD-1;temp<PixelTester.SIZE_OF_BOARD;temp++,j--){
          if(omap[temp][j] == myStone){//������ ������ ����ġ
            cnt += 1;
            //System.out.println("����ġ ������: ["+temp+"]["+j+"]");
          }
          else if(omap[temp][j] != myStone && cnt != 0){//���� ������ ������ ����ġ�ο�
            //System.out.println("���� ����ġ :["+temp+"]["+j+"]");
            weight = 100*cnt;
            if(omap[temp][j] == 0)
              cmap[temp][j] += weight;
            if(temp-(cnt+1) >= 0 && j+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[temp-(cnt+1)][j+(cnt+1)]==0)
              cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
            cnt=0;
          }
          if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 1)) && cnt != 0){ //N����ġ �ο��ϱ����� ������ ������
            //System.out.println("���ܻ�Ȳ ����ġ: ["+temp+"]["+j+"]");
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
        if(omap[i][temp] == myStone){//������ ������ ����ġ
          cnt += 1;
          //System.out.println("����ġ: ["+i+"]["+temp+"]");
        }
        else if(omap[i][temp] != myStone && cnt != 0){//���� ������ ������ ����ġ�ο�
          //System.out.println("���� ����ġ :["+i+"]["+temp+"]");
          weight = 100*cnt;
          if(omap[i][temp]==0)
            cmap[i][temp] += weight;
            if(i-(cnt+1) >= 0 && temp+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[i-(cnt+1)][temp+(cnt+1)]==0)
              cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
              cnt=0;
        }
        if(temp == 0 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
          //System.out.println("���ܻ�Ȳ ����ġ: ["+i+"]["+temp+"]");
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