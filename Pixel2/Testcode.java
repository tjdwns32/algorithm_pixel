import java.awt.*;
import java.util.Random;

public class Testcode{
	public static void main(String[] args){
	  int cnt=0;
	  while(cnt<10){
	    int randN = (int)(Math.random()*2)+1;
	    System.out.println(randN);
	    cnt++;
	  }
	}
	
}