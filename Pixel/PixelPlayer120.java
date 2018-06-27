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
   
   public void setStone(){//turn�� �������� �� ��/ ��� �� �ľ� 
    if(PixelTester.turn == 1){
      myStone = 1;
      enemyStone = 2;
    }else{
      myStone = 2;
      enemyStone = 1;
    }
  }

	public Point nextPosition(Point lastPosition) {  
	  setStone();//�� �ľ�
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int cx = (int)currentPosition.getX(), cy = (int)currentPosition.getY();
		Point nextPosition = new Point();
		
		if(myStone == 1 && map[2][3] == 0) return new Point(2,3);
		
		int [][] opMapX = new int[8][8]; 
    int [][] opMapY = new int[8][8];
    int [][] opMaplD = new int[8][8];
    int [][] opMaprD = new int[8][8];
    int [][] opMap = new int[8][8]; 
    
    //�� �� ��ġ���� ����ġ �ľ�
    opMapX = horizonSearch(map,myStone);//���� ����ġ �ο�
    opMapY = verticalSearch(map,myStone);//���� ����ġ �ο�
    opMaplD = lDiagonalSearch(map,myStone);//������->������ �Ʒ� �밢�� ����ġ �ο�
    opMaprD = rDiagonalSearch(map,myStone);//��������->���� �Ʒ� �밢�� ����ġ �ο�
    opMap1 = merge(opMapX,opMapY,opMaplD,opMaprD);//4�� ����ġ �׷��� ����
		
		//��� �� ��ġ���� ����ġ �ľ�
		opMapX = horizonSearch(map,enemyStone);//���� ����ġ �ο�
    opMapY = verticalSearch(map,enemyStone);//���� ����ġ �ο�
    opMaplD = lDiagonalSearch(map,enemyStone);//������->������ �Ʒ� �밢�� ����ġ �ο�
    opMaprD = rDiagonalSearch(map,enemyStone);//��������->���� �Ʒ� �밢�� ����ġ �ο�
	  opMap2 = merge(opMapX,opMapY,opMaplD,opMaprD);//4�� ����ġ �׷��� ����
	  
	  //���� ����ġ ���(��ġ�� �ִ밪 + sum(������ ����)/10)
	  opMap = merge2(opMap1,opMap2);

    int maxX = -1;//lastPosition���� x��ǥ ����ġ �ִ밪
    int maxY = -1;//lastPosition���� y��ǥ ����ġ �ִ밪
    Point posY = new Point();
    Point posX = new Point();
	  
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    //���� �� �ִ� ��ġ�� �츮 �� ������ ����� �� ������ �ٷ� ����
	    if(opMap1[x][i]>=300) return new Point(x,i);
	    if(opMap1[i][y]>=300) return new Point(i,y);
	    
	    //lastLocation���� y��ǥ �̵��� ������ �ĺ� ����
	    //isDanger: �ĺ� ��ġ���� �̵������� ���ڸ��߿� ���� �� ������ �̾����� �ֳ� Ȯ��
	    if(!isDanger(x,i) && map[x][i] == 0){	  
	      //����ġ�� ���� ū ��ġ�� �ĺ��� ����
	      if(opMap[x][i] > maxY){
	        maxY = opMap[x][i];
	        posY.setLocation(x,i);
	      }else if(opMap[x][i] == maxY){
	        //����ġ�� ������ lastPosition�� ����� ��ġ�� �ִ� �� ����
	        if(y == (int)posY.getY()){
	           //posY�� �������� �ʾ��� ���� ó�� ������ ��ġ�� �������� ����
	           posY.setLocation(x,i);
	           break;
	        }else if(Math.abs(y-(int)posY.getY()) >= Math.abs(y-i)){ 
	          posY.setLocation(x,i);
	        }
	      }
	    }
	    //lastLocation���� x��ǥ �̵��� ������ �ĺ� ����
	    if(!isDanger(i,y) && map[i][y] == 0){
	      //����ġ�� ���� ū ��ġ�� �ĺ��� ����
	      if(opMap[i][y] > maxX){
	        maxX = opMap[i][y];
	        posX.setLocation(i,y);
	      }else if(opMap[i][y] == maxX){ 
	        //����ġ�� ������ lastPosition�� ����� ��ġ�� �ִ� �� ����
	        if(x == (int)posX.getX()){
	          //posX�� �������� �ʾ��� ���� ó�� ������ ��ġ�� �������� ����
	          posX.setLocation(i,y);
	          break;
	        }else if(Math.abs(x-(int)posX.getX()) >= Math.abs(x-i)){
	          posX.setLocation(i,y);
	        } 
	      }
	    } 
	  }
	  //x��ǥ �̵��� ���� �ĺ��� y��ǥ �̵��� �����ĺ� ��
	  if(maxX > maxY) nextPosition = posX;
	  else if(maxX < maxY) nextPosition = posY;
	  else{
	    if(maxX == -1 && maxY == -1){ //������ �̱� �� �ִ� ��ġ �ۿ� �� �� ���� ���
	        for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){ //���̳� �� ����ִ� �� �ƹ��볪 ��
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
	      //�� �ĺ� ��ġ�� ����ġ�� ���� ��� �� �� �ϳ� �������� ��ȯ
	      int randN = (int)(Math.random()*2)+1;
	      nextPosition = (randN == 1) ? posX : posY;
	    }
	  } 
	  return nextPosition;
	}
	//isPromising(), isLPromising(), isRPromising(): ����ġ�� �ο��ϱ����� �յ� ��ġ�� �ľ��ؼ� 4���� ���� ���ɼ��� �ִ��� Ȯ��
	//4���� ���� ���ɼ��� �����鰡��ġ�� �ο����� �ʴ´�
	//����/���� �յ�(+- 3)�� ���켭 4���� ���� �� �ִ� ����� ������ ������ �ִ��� Ȯ��
	public boolean isPromising(int stone, int locX,int locY, int axis){
	  int count = 0;
	  //�ش� ��ġ x��ǥ �յڷ� 3�� �ڸ� Ȯ���ؼ� ���ӵ� 4�ڸ��� ������ ����ġ �ο�
	  if(axis == 0){
    	for(int i = locX - 3; i <= locX + 3; i++ ) {
    	    //���� ������ �Ѿ�� �н�
    			if ( i < 0 || i >= PixelTester.SIZE_OF_BOARD ) {
    				continue;
    			}
    			if ( map[i][locY] == stone || map[i][locY] == 0) {
    				count++;
    				if ( count == 4 ) {
    					return true;
    				}
    			} else {
    			  //������ �������� 0���� �ٽ� ī��Ʈ
    				count = 0;
    			}
    		}
    	}
      else{
        //�ش� ��ġ y��ǥ �յڷ� 3�� �ڸ� Ȯ���ؼ� ���ӵ� 4�ڸ��� ������ ����ġ �ο�
    		for(int i = locY - 3; i <= locY + 3; i++ ) {
    		  //���� ������ �Ѿ�� �н�
    			if ( i < 0 || i >= PixelTester.SIZE_OF_BOARD ) {
    				continue;
    			}
    			if ( map[locX][i] == stone || map[locX][i] == 0 ) {
    				count++;
    				if ( count == 4 ) {
    					return true;
    				}
    			} else {
    			  //������ �������� 0���� �ٽ� ī��Ʈ
    				count = 0;
    			}
    		} 
    	} 
    	return false;
	}
	//���� ������ ������ �Ʒ��� �������� ���⿡�� 4���� ���� ���ִ� �ڸ��� ����Ѱ� Ȯ��
	public boolean isLPromising(int x, int y, int stone) {
		int i;
		int count = 0;
		for( i = -3; i <= 3; i++ ) {
		  //���� ������ �Ѿ�� �н�
			if ( x + i < 0 || y + i < 0 || x + i >= PixelTester.SIZE_OF_BOARD || y + i >= PixelTester.SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[x + i][y + i] == stone || map[x + i][y + i] == 0 ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
    	  //������ �������� 0���� �ٽ� ī��Ʈ
				count = 0;
			}
		}
		return false;	
  }
  //������ ������ ���� �Ʒ��� �������� ���⿡�� 4���� ���� ���ִ� �ڸ��� ����Ѱ� Ȯ��
  public boolean isRPromising(int x, int y, int stone) {
		int i;
		int count = 0;
		
		for( i = -3; i <= 3; i++ ) {
		  //���� ������ �Ѿ�� �н�
			if ( x + i < 0 || y - i < 0 || x + i >= PixelTester.SIZE_OF_BOARD || y - i >= PixelTester.SIZE_OF_BOARD ) {
				continue;
			}
			if ( map[x + i][y - i] == stone || map[x + i][y - i] == 0 ) {
				count++;
				if ( count == 4 ) {
					return true;
				}
			} else {
        //������ �������� 0���� �ٽ� ī��Ʈ
				count = 0;
			}
		}    
		return false;	
  }
	//�ĺ� ��ġ ���ؿ��� �̵������� ���ڸ��߿� ������ �ѹ��� �� �θ� 4���� ������ �ڸ��� ������ �����ؼ� ����
	public boolean isDanger(int x,int y){
	  boolean danger = false;
	  for(int i = 0;i<PixelTester.SIZE_OF_BOARD;i++){
	    //���� �� ���� 3�� �̻� �̾��� �ְų� 1���� 2���� ���̿� ������� ���� ��� ����ġ�� 300�̻���
	    //y��ǥ�߿� 300�Ѵ°� �ֳ� Ȯ��
	    if(opMap2[x][i] >= 300 && i!=y){
	      danger = true;
	      break;
	    }
	    //x��ǥ�߿� 300�Ѵ°� �ֳ� Ȯ��
	    if(opMap2[i][y] >= 300 && i!=x){
	      danger = true;
	      break;
	    }
	  }
	 return danger;  
	}
	//�� ��� �Լ�, ������
	public void print(int[][] map){
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(map[i][j] + "\t ");
      }
      System.out.println();
      System.out.println();
    }
	}
	
	//����ġ �� merge�Լ�, ����/����/���ʴ밢��/�����ʴ밢�� ����ġ�� �ϳ��� ��ħ
	//4�� ���� ���� ū ���� ������ �� 3���� ���� 10���� ���� ���� ���ؼ� ����ġ �ο�
	public static int[][] merge(int hMap[][], int vMap[][], int lDMap[][], int rDMap[][]){
	  int[][] opMap = new int[8][8];
	  int maxW = 0;
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        //����ġ�� ���� ū �� ã��
        maxW = Math.max(hMap[i][j], vMap[i][j]);
        maxW = Math.max(maxW, lDMap[i][j]);
        maxW = Math.max(maxW, rDMap[i][j]);
        //ū ���� �������� ����ġ ���ϱ�
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
	//������� ����ġ�� �� ���� ����ġ�� ���ؼ� ����/���� �� �� ����� ����ġ ���
	//�� �� ���� �� ū ���� ������ ���� 10���� ���� ���� ����
	public static int[][] merge2(int opMap1[][], int opMap2[][]){
	  int[][] opMap = new int[8][8];
	  int maxW = 0;
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        //�� ���� �� ū���� �������� ����
        maxW = Math.max(opMap1[i][j], opMap2[i][j]);
        opMap[i][j] += maxW;
        //ū ���� �ٸ� ���� 10%����
        if(opMap1[i][j] == maxW){
          opMap[i][j] += ((opMap2[i][j])/10 );
        }else{
          opMap[i][j] += ((opMap1[i][j])/10 );
        }
      }
    }
	  return opMap;
	}
	//���μ��� ����ġ�� 100
	
	//���ΰ���ġ�� ����
	public  int[][] verticalSearch(int[][] omap,int myStone){
	  int w = 100; //����ġ
		int cnt = 0; //�پ��ִ� ���� ��
	  int[][] opMapY = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];//��ȯ�� ����ġ��
	  
	  for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(omap[j][i] == myStone){ //���� ���� ��ġ�� ã����
          cnt++; //���� ���� ����       
          while(true){ //�پ��ִ� ���� Ȯ��
            if((j+cnt) < 8){ //���� ũ�⸦ ����� �ʴ� ������
              if(omap[j+cnt][i] == myStone) //�پ��ִ� ���� �����ϸ� ī��Ʈ�ϰ�
                ++cnt;
              else if(omap[j+cnt][i] == 0){ //���� �Ѽ��ִ� ��ġ�̸� ���ΰ���ġ ���� �˻�
                if(isPromising(myStone,j+cnt,i,0))
                  opMapY[j+cnt][i] += (cnt * w);
                else
                  opMapY[j+cnt][i] = 0;  
                break;
              }
              else break; //���� �� �� �ִ� �ڸ��� �ƴѰ�� ����
            }else break; //��ũ�⸦ ����� ����
          }
          
          // ���絹�� �� ��ġ�� �˻��ؼ�
          if(((j-1)!= -1)){ //���� ũ�⸦ ����� �ʰ�
             if ((omap[j-1][i] == 0) ){ //���� �� �� �ִ� ��ġ�̸�
              if(isPromising(myStone,j-1,i,0))
                opMapY[j-1][i] += (cnt * w);  
              else
                opMapY[j-1][i] = 0;  
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
	
	//���ΰ���ġ�� ����
	public  int[][] horizonSearch(int[][] omap,int myStone){
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
                if(isPromising(myStone,i,j+cnt,1))
                  opMapX[i][j+cnt] += (cnt * w);
                else
                   opMapX[i][j+cnt] = 0;
                break;
              }
              else break; //���� �� �� �ִ� �ڸ��� �ƴѰ�� ����
            }else break; //��ũ�⸦ ����� ����
          }
          
          // ���絹�� �� ��ġ�� �˻��ؼ�
          if(((j-1)!= -1)){ //���� ũ�⸦ ����� �ʰ�
            if ((omap[i][j-1] == 0) ){ //���� �� �� �ִ� ��ġ�̸�
              if(isPromising(myStone,i,j-1,1))
                opMapX[i][j-1] += (cnt * w);  
              else
                opMapX[i][j-1] = 0;  
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
	//�밢�� ����ġ�� 110
	
  //���� ������ ������ �Ʒ� �밢�� ����ġ �� ����
	public int[][] lDiagonalSearch(int[][] omap,int Stone){
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      //�׷��� �߾Ӽ� �Ʒ��� �밢�� Ž��
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--){
        cnt =0;
        temp = i;
        for(int j =0;temp<PixelTester.SIZE_OF_BOARD;temp++,j++){
          if((omap[temp][j] == Stone)&&isLPromising(temp,j,Stone)){//������ ������ ī��Ʈ����
            cnt += 1;
          }else if(omap[temp][j] !=  Stone && cnt != 0){//���� ������ ������ ����ġ�ο�
            weight = 110*cnt;
            if(omap[temp][j] == 0) cmap[temp][j] += weight;//���� ��ġ�� ��������� ����ġ �ο�
            if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0 && omap[temp-(cnt+1)][j-(cnt+1)]==0)
              cmap[temp-(cnt+1)][j-(cnt+1)] += weight; 
              //����ġ ������ġ�� �� ��ġ���� ��������� ����ġ �ο�
            cnt=0;//����ġ �ο� �� ī��Ʈ �ʱ�ȭ
          }
          if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 6)) && cnt != 0){
            //����ġ �ο��ϱ����� ������ ����(������ �����ڸ��� ������ ��)
            weight = 110*cnt;
            //���� ��ġ�� �����Ƿ� ����ġ���� �̸� Ȯ���ϰ� ����ġ ������ġ�� �� ��ġ�� ��������� �ű⿡ ����ġ�ο�  
            if(temp-cnt >= 0 && j-cnt >= 0 && omap[temp-cnt][j-cnt]==0)
              cmap[temp-cnt][j-cnt] += weight; 
            cnt =0;
          }     
        }
      }
     //�׷��� �߾Ӽ� ���� �밢�� Ž��
     for(int j=1;j<PixelTester.SIZE_OF_BOARD;j++){
      cnt =0;
      temp = j;
      for(int i =0;temp<PixelTester.SIZE_OF_BOARD;temp++,i++){
        if((omap[i][temp] == Stone)&&isLPromising(i,temp,Stone)){//������ ������ ī��Ʈ
          cnt += 1;
        }
        else if(omap[i][temp] != Stone && cnt != 0){//���� ������ ������ ����ġ�ο�
          weight = 110*cnt;
          if(omap[i][temp] == 0)
            cmap[i][temp] += weight;//���� ��ġ�� ��������� ����ġ �ο�
          if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0 && omap[i-(cnt+1)][temp-(cnt+1)]==0)
            cmap[i-(cnt+1)][temp-(cnt+1)] += weight; 
            //����ġ ������ġ�� �� ��ġ���� ��������� ����ġ �ο�
          cnt=0;
        }
        if(temp == PixelTester.SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
          weight = 110*cnt;
          if(temp-cnt >= 0 && i-cnt >= 0 && omap[i-cnt][temp-cnt]==0)
            cmap[i-cnt][temp-cnt] += weight; 
          cnt = 0;
        }     
      }
     }
     return cmap;  
    }
		
		 //������ ������ ���� �Ʒ� �밢�� ����ġ �� ����
		public int[][] rDiagonalSearch(int[][] omap,int Stone){
      int cnt = 0;
      int weight = 0;
      int temp = 0;
      int[][] cmap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
      //�׷��� �߾Ӽ� �Ʒ��� Ž��
      for(int i = PixelTester.SIZE_OF_BOARD-2;i>=0;i--) {
        cnt =0;
        temp = i;
        for(int j =PixelTester.SIZE_OF_BOARD-1;temp<PixelTester.SIZE_OF_BOARD;temp++,j--){
          if(omap[temp][j] == Stone &&isRPromising(temp,j,Stone)){//������ ������ ī��Ʈ
            cnt += 1;
          }
          else if(omap[temp][j] != Stone && cnt != 0){//���� ������ ������ ����ġ�ο�
            weight = 110*cnt;
            if(omap[temp][j] == 0)//���� ��ġ ����ġ �ο�
              cmap[temp][j] += weight;
            if(temp-(cnt+1) >= 0 && j+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[temp-(cnt+1)][j+(cnt+1)]==0)
              //����ġ ������ġ�� �� ��ġ���� ��������� ����ġ �ο�
              cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
            cnt=0;
          }
          if((temp == PixelTester.SIZE_OF_BOARD-1 || (temp == 6 && j == 1)) && cnt != 0){ //����ġ �ο��ϱ����� ������ ������
            weight = 110*cnt;
            if(temp-cnt >= 0 && j+cnt<PixelTester.SIZE_OF_BOARD && omap[temp-cnt][j+cnt]==0)
              cmap[temp-cnt][j+cnt] += weight; 
              //����ġ ������ġ�� �� ��ġ�� ��������� ����ġ �ο�
            cnt =0;
          }     
       }
     }
     //�׷��� �߾Ӽ� ���� Ž��
    for(int j= 1;j<PixelTester.SIZE_OF_BOARD-1;j++) {
      cnt =0;
      temp = j;
      for(int i =0;temp>=0;temp--,i++){
        if((omap[i][temp] == Stone )&&isRPromising(i,temp,Stone)){//������ ������ ����ġ
          cnt += 1;
        }
        else if(omap[i][temp] != Stone && cnt != 0){//���� ������ ������ ����ġ�ο�
          weight = 110*cnt;
          if(omap[i][temp]==0)
            cmap[i][temp] += weight;//���� ��ġ�� ��������� ����ġ �ο�
          if(i-(cnt+1) >= 0 && temp+(cnt+1)<PixelTester.SIZE_OF_BOARD && omap[i-(cnt+1)][temp+(cnt+1)]==0)
            cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
            //����ġ ������ġ�� �� ��ġ���� ��������� ����ġ �ο�
          cnt=0;
        }
        if(temp == 0 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
          weight = 110*cnt;
          if(i-cnt >= 0 && temp+cnt<PixelTester.SIZE_OF_BOARD && omap[i-cnt][temp+cnt]==0)
            cmap[i-cnt][temp+cnt] += weight; 
            //����ġ ������ġ�� �� ��ġ�� ��������� ����ġ �ο�
          cnt = 0;
        }     
     }
    }
    return cmap;
  }
}