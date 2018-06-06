public class diag_search{
  public static int SIZE_OF_BOARD = 8;
	public static int [][] map = new int[SIZE_OF_BOARD][SIZE_OF_BOARD];
	public static void print(int[][] map){
	  for(int i = 0;i<SIZE_OF_BOARD;i++){
	    System.out.println();
	    for(int j = 0;j<SIZE_OF_BOARD;j++){
	      System.out.print(map[i][j]+"\t");
	    }
	  }
	}
  public static void main(String args[]){
    int [][] omap = 
          {{1,0,0,0,0,0,1,0},
           {0,0,1,0,0,0,0,1},
           {0,0,0,1,1,0,0,0},
           {0,1,0,0,0,1,0,0},
           {0,0,1,0,0,0,0,0},
           {0,0,0,0,0,0,1,0},
           {0,1,0,0,0,1,0,0},
           {1,0,0,0,1,0,0,0}};
    //���� ������ ������ �Ʒ��� �밢�� Ž��
		print(lDiagonalSearch(omap));
		System.out.println();
		//������ ������ ���ʾƷ��� �밢�� Ž��
		print(rDiagonalSearch(omap));
		}
		public static int[][] lDiagonalSearch(int[][] omap){
		  int cnt = 0;
      int weight = 0;
      int temp = 0;
      //���� ������ ������ �Ʒ��� �밢�� Ž��
		  int[][] cmap = new int[SIZE_OF_BOARD][SIZE_OF_BOARD];
		  for(int i = SIZE_OF_BOARD-1;i>=0;i--) {
		  System.out.println("diag: "+i);
		  cnt =0;
		  temp = i;
			for(int j =0;temp<SIZE_OF_BOARD;temp++,j++){
			  if(omap[temp][j] == 1){//������ ������ ����ġ
			     cnt += 1;
			     System.out.println("����ġ: ["+temp+"]["+j+"]");
			  }
			  else if(omap[temp][j] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			     System.out.println("���� ����ġ :["+temp+"]["+j+"]");
			     weight = 100*cnt;
			     cmap[temp][j] = weight;
			     if(temp-(cnt+1) >= 0 && j-(cnt+1) >= 0)
			      cmap[temp-(cnt+1)][j-(cnt+1)] = weight; 
			     cnt=0;
			  }
			  if(temp == SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
			     System.out.println("���ܻ�Ȳ ����ġ: ["+temp+"]["+j+"]");
			     weight = 100*cnt;
			     if(temp-cnt >= 0 && j-cnt >= 0)
			      cmap[temp-cnt][j-cnt] = weight; 
			     cnt =0;
			  }	  
			}
	  }
    for(int j=0;j<SIZE_OF_BOARD;j++){
		  System.out.println("diag: "+j);
		  cnt =0;
		  temp = j;
			for(int i =0;temp<SIZE_OF_BOARD;temp++,i++){
			  if(omap[i][temp] == 1){//������ ������ ����ġ
			     cnt += 1;
			     System.out.println("����ġ: ["+i+"]["+temp+"]");
			  }
			  else if(omap[i][temp] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			     System.out.println("���� ����ġ :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] = weight;
			     if(i-(cnt+1) >= 0 && temp-(cnt+1) >= 0)
			      cmap[i-(cnt+1)][temp-(cnt+1)] = weight; 
			     cnt=0;
			  }
			  if(temp == SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
			     System.out.println("���ܻ�Ȳ ����ġ: ["+i+"]["+temp+"]");
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
		  int cnt = 0;
      int weight = 0;
      int temp = 0;
		  int[][] cmap = new int[SIZE_OF_BOARD][SIZE_OF_BOARD];
		  //������ ������ ���ʾƷ��� �밢�� Ž��
		  for(int i = SIZE_OF_BOARD-1;i>=0;i--) {
		    System.out.println("diag: "+i);
		    cnt =0;
		    temp = i;
			  for(int j =SIZE_OF_BOARD-1;temp<SIZE_OF_BOARD;temp++,j--){
			    if(omap[temp][j] == 1){//������ ������ ����ġ
			      cnt += 1;
			      System.out.println("����ġ ������: ["+temp+"]["+j+"]");
			    }
			    else if(omap[temp][j] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			      System.out.println("���� ����ġ :["+temp+"]["+j+"]");
			      weight = 100*cnt;
			      cmap[temp][j] += weight;
			      if(temp-(cnt+1) >= 0 && j+(cnt+1)<SIZE_OF_BOARD)
			      cmap[temp-(cnt+1)][j+(cnt+1)] += weight; 
			      cnt=0;
			    }
			  if(temp == SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
			    System.out.println("���ܻ�Ȳ ����ġ: ["+temp+"]["+j+"]");
			    weight = 100*cnt;
			    if(temp-cnt >= 0 && j+cnt<SIZE_OF_BOARD)
			    cmap[temp-cnt][j+cnt] += weight; 
			    cnt =0;
			  }	  
			}
	  }
    for(int j= 0;j<SIZE_OF_BOARD;j++) {
		  System.out.println("diag: "+j);
		  cnt =0;
		  temp = j;
			for(int i =0;temp>=0;temp--,i++){
			  if(omap[i][temp] == 1){//������ ������ ����ġ
			     cnt += 1;
			     System.out.println("����ġ: ["+i+"]["+temp+"]");
			  }
			  else if(omap[i][temp] == 0 && cnt != 0){//���� ������ ������ ����ġ�ο�
			     System.out.println("���� ����ġ :["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     cmap[i][temp] += weight;
			     if(i-(cnt+1) >= 0 && temp+(cnt+1)<SIZE_OF_BOARD)
			      cmap[i-(cnt+1)][temp+(cnt+1)] += weight; 
			     cnt=0;
			  }
			  if(temp == SIZE_OF_BOARD-1 && cnt != 0){//����ġ �ο��ϱ����� ������ ������
			     System.out.println("���ܻ�Ȳ ����ġ: ["+i+"]["+temp+"]");
			     weight = 100*cnt;
			     if(i-cnt >= 0 && temp+cnt<SIZE_OF_BOARD)
			      cmap[i-cnt][temp+cnt] += weight; 
			     cnt = 0;
			  }	  
			}
	  }
	  return cmap;
		}
}
