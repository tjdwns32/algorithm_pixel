import java.awt.*;


public class PixelPlayer100 extends Player {
	PixelPlayer100(int[][] map) {
		super(map);
	}
	
	public Next predict(int a, int b, int myDol) {
		int yNum = (myDol == 2)? 1 : 2;
		int preSum;
		int postSum;
		int turn = 0;
		if(map3[a][b] == 0) {
			map3[a][b] = myDol;
			turn = 1;
		}
		Next[] map5 = new Next[16];
		int index = 0;
		for(int i = 0; i < 8; i++) {
			if(map3[i][b] == 0 && i != a) {
				preSum = cgrade(yNum);
				preSum += vgrade(yNum);
				preSum += dvgrade(yNum);
				preSum += dcgrade(yNum);
				map3[i][b] = yNum;
				postSum = cgrade(yNum);
				postSum += vgrade(yNum);
				postSum += dvgrade(yNum);
				postSum += dcgrade(yNum);
				map5[index++] = new Next(postSum - preSum, new Point(i, b));
				map3[i][b] = 0;
				preSum = 0;
				postSum = 0;
			}
			else{
				map5[index++] = new Next(-1, new Point(i, b));
			}
			if(map3[a][i] == 0 && i != b) {
				preSum = cgrade(yNum);
				preSum += vgrade(yNum);
				preSum += dvgrade(yNum);
				preSum += dcgrade(yNum);
				map3[a][i] = yNum;
				postSum = cgrade(yNum);
				postSum += vgrade(yNum);
				postSum += dvgrade(yNum);
				postSum += dcgrade(yNum);
				map5[index++] = new Next(postSum - preSum, new Point(a, i));
				map3[a][i] = 0;
				preSum = 0;
				postSum = 0;
			}
			else{
				map5[index++] = new Next(-1, new Point(a, i));
			}
		}
		if(turn == 1) {
			map3[a][b] = 0;
		
		}
		Narr(map5, 16);
		
		return map5[0];
	}
	// (a, b)�� ���Ҵٰ� �����ϰ� ������ ����ġ�� ���� ū ���� ��ȯ

	
	public class Next{
		public int num;
		public Point p;
		boolean id;
		
		 public Next() {
			 num = 0;
		 }
		 public Next(int a, Point b){
			num = a;
			p = b;
		}
		
	};// ���� ����ġ�� �� ���� �� �� �ְ� ������ Ŭ����
	
	public void Narr(Next[] arr, int size) {
		for(int i = 0; i < size - 1; i++) {
			Next temp = arr[i];
			for(int j = i + 1; j < size; j++) {
				if(temp.num < arr[j].num) {
					arr[i] = arr[j];
					arr[j] = temp;
					temp = arr[i];
				}
			}
		}
	}// NEXT�迭�� ������������ ����

	public int bonus2(int a, int b, int c, int d) {
		int sum = a * 1000 + b * 100 + c * 10 + d;
		int result = 0;
		switch(sum) {
		case 1111 :
		case 1110 :
		case 1101 :
		case 1011 :
		case 1010 :
		case 111 :
		case 110 :
		case 101 :
		case 1112 :
		case 1012 :
		case 2111 :
		case 2110 :
		case 112 :
		case 2121 :
		case 2101 :
			result = 0;
			break;
		case 1100 :
		case 1001 :
		case 100 :
		case 11 :
		case 10 :
		case 2100 :
		case 12 :
			result = 1;
			break;
		case 1000 :
		case 1 :
			result = 2;
			break;
		case 0 :
			result = 3;
			break;
		case 1102 :
		case 1002 :
		case 102 :
		case 2102 :
		case 2011 :
		case 2010 :
		case 2012 :
		case 2001 :
			result = 10;
			break;
		case 2000 :
		case 2 :
			result = 15;
			break;
		case 2002 :
			result = 1000;
			break;
			
		}
		return result;
	}
	public int bonus3 (int a, int b) {
		int sum = a * 10 + b;
		int result = 0;
		switch(sum) {
		case 0 :
			result = 1000;
			break;
		case 10 :
		case 1 :
			result = 15;
			break;
		case 11 :
			result = 0;
			break;
		}
		return result;
	}

	public int area(int x, int y, int myDol) {
		if(x == 0 && y == 0) return 1;
		else if(x == 7 && y == 0) return 1;
		else if(x == 0 && y == 7) return 1;
		else if(x == 7 && y == 7) return 1;
		else if(x >= 0 && x < 8 ) {
			if ( y >= 0 && y < 8) {
				if(myDol == 1) {
					if(map3[x][y] == 2) return 1;
					else if(map3[x][y] == 1) return 2;
					else return 0;
				}
				else return map3[x][y];
			}
			else return 1;
		}
		else return  1;
	}
	
	public int vgrade(int myDol) {
		int count = 0;
		int grade = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				int k;
				if(map3[i][j] == myDol) {
					for(k = j; k < 8; k++) {
						if(map3[i][k] == myDol) {
							count++;
						}
					
						else {
							
							break;
						}
					
					}
					if(count == 2) {
						grade += bonus2(area(i,j - 2, myDol), area(i,j - 1, myDol),area(i,j + 2, myDol), area(i,j + 3, myDol));
					}
					else if(count == 3) {
						grade += bonus3(area(i, j - 1, myDol), area(i, j + 3, myDol));
					}
					else if(count >= 4) {
						grade += 10000;
					}
					j = k;
					count = 0;
				}
			}
		
		}



		
		
		return grade;
	}
	
	
	public int cgrade(int myDol) {
		int count = 0;
		int grade = 0;
		for(int j = 0; j < 8; j++) {
			int k;
			for(int i = 0; i < 8; i++) {
				if(map3[i][j] == myDol) {
					for(k = i; k < 8; k++) {
						if(map3[k][j] ==myDol) {
							count++;
						}
						else {
							break;
						}
					
					}
					if(count == 2) {
						grade += bonus2(area(i - 2,j, myDol), area(i - 1,j, myDol), area(i + 2,j, myDol), area(i + 3,j, myDol));
					}
					else if(count == 3) {
						grade += bonus3(area(i - 1, j, myDol), area(i + 3, j, myDol));
					}
					else if(count >= 4) {
						grade += 10000;
					}
					i = k;
					count = 0;
				}
			}
		
		}
		
		
		return grade;
	}
	
	public int dvgrade(int myDol) {
		int count = 0;
		int grade = 0;
		for(int d = 0; d < 8; d++) {
			for(int i = -3 + d; i < 5 + d; i++) {
				int j = i - d + 3;
				int k;
				if(i >=  0 && i < 8 && map3[i][j] == myDol) {
					for(k = 0; k < 8 - j; k++) {
						if(i < 8 - k) {
							if(map3[i + k][j + k] == myDol) {
								count++;
							}
					
							else {
							
								break;
							}
						}
					
					}
					if(count == 2) {
						grade += bonus2(area(i - 2,j - 2, myDol), area(i - 1,j - 1, myDol),area(i + 2,j + 2, myDol), area(i + 3,j + 3, myDol));
					}
					else if(count == 3) {
						grade += bonus3(area(i - 1, j - 1, myDol), area(i + 3, j + 3, myDol));
					}
					else if(count >= 4) {
						grade += 10000;
					}
					i = i + k;
					count = 0;
				}
			}
		
		}



		
		
		return grade;
	}
	public int dcgrade(int myDol) {
		int count = 0;
		int grade = 0;
		for(int d = 0; d < 8; d++) {
			for(int i = -3 + d; i < 5 + d; i++) {
				int j = 4 + d - i;
				int k;
				if(i >=  0 && i < 8 && map3[i][j] == myDol) {
					for(k = 0; k < j; k++) {
						if(i < 8 - k) {
							if(map3[i + k][j - k] == myDol) {
								count++;
							}
					
							else {
							
								break;
							}
						}
					
					}
					if(count == 2) {
						grade += bonus2(area(i - 2,j + 2, myDol), area(i - 1,j + 1, myDol),area(i + 2,j - 2, myDol), area(i + 3,j - 3, myDol));
					}
					else if(count == 3) {
						grade += bonus3(area(i - 1, j + 1, myDol), area(i + 3, j - 3, myDol));
					}
					else if(count >= 4) {
						grade += 10000;
					}
					i = i + k;
					count = 0;
				}
			}
		
		}



		
		
		return grade;
	}
	
	
	public void grade_mapping(int[][] m, int myDol) {
		int preSum;
		int postSum;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(map3[i][j] == 0) {
					preSum = cgrade(myDol);
					preSum += vgrade(myDol);
					preSum += dvgrade(myDol);
					preSum += dcgrade(myDol);
					map3[i][j] = myDol;
					postSum = cgrade(myDol);
					postSum += vgrade(myDol);
					postSum += dvgrade(myDol);
					postSum += dcgrade(myDol);
					m[i][j] =  postSum - preSum; // ����ġ�� �߰���
					map3[i][j] = 0;
				}
				else {
					m[i][j] = -3; // ���� �� ����.
				}
			}
		}
		
	}
	
	int[][] map3 = new int[8][8];
	public Point nextPosition(Point lastPosition) {  
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int find = 0;
		Point nextPosition = new Point(x + 1, y);
		int myNum = map[(int)currentPosition.getX()][(int)currentPosition.getY()];
		int yNum = (myNum == 2)? 1 : 2;
		
		
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				map3[i][j] = map[i][j];
			}
		}
		//map3 ����
		
		int[][] map2 = new int[8][8];
		grade_mapping(map2, myNum);
		// ���� ����ġ
		
		
	
		int[][]map4 = new int[8][8];
		grade_mapping(map4, yNum);
		// ���� ����ġ ��
		
		Next[] NA = new Next[16];
		// �ڽ��� ���� ��  ���� ���� �迭
		int index = 0;
		for(int i = 0; i < 8; i ++) {
			NA[index] = new Next(map2[i][y], new Point(i, y));
			index++;
			NA[index] = new Next(map2[x][i], new Point(x, i));
			index++;
			
		}
		// �迭�� �ڽ��� ���� �� �ִ� ���� ����ġ�� ��ǥ�� ����
		index = 0;
		
		
		
		Next[] NA2 = new Next[16];
		// �ڽ��� ���� ��  ���� ���� �迭2
		for(int i = 0; i < 8; i ++) {
			NA2[index] = new Next(map4[i][y], new Point(i, y));
			index++;
			NA2[index] = new Next(map4[x][i], new Point(x, i));
			index++;
		}
		// ���� ����ġ ����
		index = 0;
		
		
		int post; // �켱���� ����
		int find2 = 0;
		for(int i = 0; i< 4; i++) { // �켱���� -> �켱������ �迭���� ���� �ִ���
			post = i;
			for(int j = 0; j < 16; j++) { // �����ִ� �迭�� �˻�
				if(post == 0) {
					if(NA[j].num >= 8000) { //8000�̻��� ���� �� �ִ� �� -> ���� ���� �� �ִ� ���� Ȯ��
						nextPosition = NA[j].p;
						find2 = 1;
						break;
					}
				
				}
				else if(post == 1) {
					if( predict(NA2[j].p.x, NA2[j].p.y, myNum).num <= 8000 ) { // ���� NA2[j]���� ������ �� ���� ���� �� �ִ� ���� ����.
						if(NA2[j].num >= 8000) {// NA2[j]���� ���� ������ ���̳�?
							nextPosition = NA2[j].p;
							find2 = 1;
							break;
						}
					}// 
					else {
						if(NA2[j].num >= -1)
						NA2[j].num = -1;// �� ���� ������ ������ ������ ������ ������ �ʵȴٴ� ���� �˷���
					}
				}
				else if(post == 2) {
					if( predict(NA[j].p.x, NA[j].p.y, myNum).num <= 8000) {
						if(predict(NA2[j].p.x, NA2[j].p.y, yNum).num <= 8000) {
							if(NA[j].num >= 800) {
								nextPosition = NA[j].p;
								find2 = 1;
								break;
							}
						}
						else {
							if(NA[j].num >= -1)
								NA[j].num = -1;
						}
					}
					else {
						if(NA[j].num >= -2)
							NA[j].num = -2;
					}
				}
				else if(post == 3) {
					if( predict(NA2[j].p.x, NA2[j].p.y, myNum).num <= 8000 ) {
						if(predict(NA2[j].p.x, NA2[j].p.y, yNum).num <= 8000) {
							if(NA2[j].num >= 800) {
								nextPosition = NA2[j].p;
								find2 = 1;
								break;
							}
						}
						else {
							if(NA2[j].num >= -1)
								NA2[j].num = -1;
						}
					}
					else {
						if(NA2[j].num >= -2)
							NA2[j].num = -2;
					}
				}
			}
			if(find2 == 1) {
				break;
			}
			
		}
	//--------------------------------------------------------------------------> ���� 4���� �켱 ������ ���ϴ°�	
		
		
		if(find2 == 0) {
			System.out.print("find = 0;        ");
			Next temp = new Next();
			Next temp2 = new Next();
			Narr(NA, 16);
			Narr(NA2, 16);
			
			for(int j = 0; j < 2; j++){
				if(j == 0){// ���� NA[i]�� ���Ҵٰ� �����ϰ� 
					for(int i = 0; i < 16; i++){
						if(NA[i].num >= 0){
							temp = predict(NA[i].p.x,NA[i].p.y, myNum);
							if(temp.num >= 800){
								map3[NA[i].p.x][NA[i].p.y] = myNum;
								map3[temp.p.x][temp.p.y] = yNum;
								for(int k = 0; k < 8; k++){
									if(map3[temp.p.x][k] == 0){
										if(predict(temp.p.x, k, yNum).num >= 8000) {
											nextPosition = new Point(NA[i].p.x, NA[i].p.y);
											find = 1;
											break;}
									}
									if(map3[k][temp.p.y] == 0) {
										if(predict(k, temp.p.y, yNum).num >= 8000) {
											nextPosition = new Point(NA[i].p.x, NA[i].p.y);
											find = 1;
											break;
										}
									}
								}
								map3[NA[i].p.x][NA[i].p.y] = 0;
								map3[temp.p.x][temp.p.y] = 0;
							}
						}
						if(NA2[i].num >= 0) {
							temp2 = predict(NA2[i].p.x,NA2[i].p.y, myNum);
							if(temp2.num >= 800){
								map3[NA2[i].p.x][NA2[i].p.y] = myNum;
								map3[temp2.p.x][temp2.p.y] = yNum;
								for(int k = 0; k < 8; k++){
									if(map3[temp2.p.x][k] == 0){
										if(predict(temp2.p.x, k, yNum).num >= 8000) {
											nextPosition = new Point(NA2[i].p.x, NA2[i].p.y);
										
										find = 1;
										break;
										}
									}
									if(map3[k][temp2.p.y] == 0) {
										
										if(predict(k, temp2.p.y, yNum).num >= 8000) {
											nextPosition = new Point(NA2[i].p.x, NA2[i].p.y);
										find = 1;
										break;
										}
									}
								}
								map3[NA2[i].p.x][NA2[i].p.y] = 0;
								map3[temp2.p.x][temp2.p.y] = 0;
							}
						}
						if(find == 1) break;
					}
				}
				else{// ������ �� ���� �ͺ��� ���߿� ���� ���� ���� �� ���� �� �׸��� ���� 8000���� ����� �Ѵ�.
					System.out.print("else ���� ��;        ");
					for(int i = 0; i < 16; i++){
						if(NA[i].num >= 0){
							temp = predict(NA[i].p.x,NA[i].p.y, myNum);
							if (temp.num == 0) continue;
							map3[NA[i].p.x][NA[i].p.y] = myNum;
							map3[temp.p.x][temp.p.y] = yNum;
							for(int k = 0; k < 8; k++){
								if(map3[temp.p.x][k] == 0){
									Next temp5 = predict(temp.p.x, k, yNum);
									if(temp5.p.x != NA[i].p.x && temp5.p.y != NA[i].p.y && temp5.num >= 4 && temp5.num > temp.num) {
										if(predict(temp5.p.x, temp5.p.y, myNum).num <= 8000 && predict(temp5.p.x, temp5.p.y, yNum).num <= 8000) {
									nextPosition = new Point(NA[i].p.x, NA[i].p.y);
									find = 1;
									break;}}
								}
								if(map3[k][temp.p.y] == 0) {
									Next temp5 = predict(k,temp.p.y, yNum);
									if(temp5.p.x != NA[i].p.x && temp5.p.y != NA[i].p.y && temp5.num >= 4 && temp5.num > temp.num) {
										if(predict(temp5.p.x, temp5.p.y, myNum).num <= 8000 && predict(temp5.p.x, temp5.p.y, yNum).num <= 8000) {
										nextPosition = new Point(NA[i].p.x, NA[i].p.y);
									find = 1;
									break;}}
								}
							}
							map3[NA[i].p.x][NA[i].p.y] = 0;
							map3[temp.p.x][temp.p.y] = 0;
							
						}
						if(NA2[i].num >= 0) {
							temp2 = predict(NA2[i].p.x,NA2[i].p.y, myNum);
							if (temp2.num == 0) continue;
							map3[NA2[i].p.x][NA2[i].p.y] = myNum;
							map3[temp2.p.x][temp2.p.y] = yNum;
							for(int k = 0; k < 8; k++){
								if(map3[temp2.p.x][k] == 0){
									Next temp5 = predict(temp2.p.x, k, yNum);
									if(temp5.p.x != NA2[i].p.x && temp5.p.y != NA2[i].p.y && temp5.num >= 4 && temp5.num > temp.num) {
										if(predict(temp5.p.x, temp5.p.y, myNum).num <= 8000 && predict(temp5.p.x, temp5.p.y, yNum).num <= 8000) {
											nextPosition = new Point(NA2[i].p.x, NA2[i].p.y);
											find = 1;
											break;}
										}
								}
								if(map3[k][temp2.p.y] == 0) {
									Next temp5 = predict(k, temp2.p.y, yNum);
									if(temp5.p.x != NA2[i].p.x && temp5.p.y != NA2[i].p.y && temp5.num >= 4 && temp5.num > temp.num) {
										if(predict(temp5.p.x, temp5.p.y, myNum).num <= 8000 && predict(temp5.p.x, temp5.p.y, myNum).num <= 8000) {
										nextPosition = new Point(NA2[i].p.x, NA2[i].p.y);
									find = 1;
									break;}}
								}
							}
							map3[NA2[i].p.x][NA2[i].p.y] = 0;
							map3[temp2.p.x][temp2.p.y] = 0;
							
						}
						if(find == 1) break;
					}
				}
				if(find == 1) break;
				
			}
			if(find == 0 ) {
				System.out.print("���̿�~~~~~~~~~~~~~~~~~~~~~~~~~~");
				int temp3 = 0;
				int temp4 = 0;
				Narr(NA, 16);
				Narr(NA2, 16);
				for(int i = 0; i < 16; i ++) {
					System.out.print(" " + NA2[i].num);
				}
				temp3 = NA[0].num;
				nextPosition = NA[0].p;
				temp4 = NA2[0].num;
				if(temp4 > temp3) {
					nextPosition = NA2[0].p;
				}
				
			}
			
		}
		
		
		return nextPosition;
	}
}
