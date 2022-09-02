import java.io.*;

public class Canvas {
	public static int length = 100;
	public static int width = 100;
	public static int[][] canvas = new int[length][width];
	public static String[] colorMap = { ".", "o", "#", "@" };
	private static PrintWriter pw = new PrintWriter(System.out);
	
	public static void render() {
		String out = "";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				//System.out.print(colorMap[canvas[i][j]] + " ");
				out += colorMap[canvas[i][j]] + " ";
			}
			out += "\n";
			//System.out.println();
		}
		System.out.println(out);
	}

	public static void drawPoint(int x, int y, int a) {
		try{
			canvas[y][x] = a;
		} catch(ArrayIndexOutOfBoundsException e){

		}
	}

	public static void drawLine(int x1, int y1, int x2, int y2, int a) {
		for (double i = 0; i < 1; i += 0.01) {

			int x = (int) Math.round((x1 * i + x2 * (1 - i)));
			int y = (int) Math.round((y1 * i + y2 * (1 - i)));
			drawPoint(x, y, a);
		}
	}

	public static void clear() {
		for(int i = 0; i < length; i++){
			for(int j = 0; j < width; j++){
				canvas[i][j] = 0;
			}
		}
	}
}