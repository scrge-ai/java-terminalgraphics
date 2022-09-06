import java.io.*;

public class Canvas {
	public  int length = 60;
	public  int width = 100;
	public int[][] canvas = new int[length][width];
	//public  String[] colorMap = { " ", ".", ":", "-", "=", "+", "*", "#", "%", "@"};
	//private  PrintWriter pw = new PrintWriter(System.out);
	String colorMap = " .'`,^:;~-_+<>i!lI?/|()1{}[]rcvunxzjftLCJUYXZO0Qoahkbdpqwm*WMB8&%$#@";
	//System.out.println();
	public void render() {
		String out = "";
		/*System.out.println(length);
		System.out.println(width);
		System.out.println(canvas.length);
		System.out.println(canvas[0].length);*/
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				//System.out.print(colorMap[canvas[i][j]] + " ");
				//System.out.println(i + " " + j);
				out += colorMap.charAt(canvas[i][j]) + " ";
			}
			out += "\n";
			//System.out.println();
		}
		System.out.println(out);
	}

	public void drawPoint(int x, int y, int a) {
		try{
			canvas[y][x] = a;
		} catch(ArrayIndexOutOfBoundsException e){

		}
	}

	public void drawLine(int x1, int y1, int x2, int y2, int a) {
		for (double i = 0; i < 1; i += 0.01) {

			int x = (int) Math.round((x1 * i + x2 * (1 - i)));
			int y = (int) Math.round((y1 * i + y2 * (1 - i)));
			drawPoint(x, y, a);
		}
	}

	public void clear() {
		for(int i = 0; i < length; i++){
			for(int j = 0; j < width; j++){
				canvas[i][j] = 0;
			}
		}
	}
}