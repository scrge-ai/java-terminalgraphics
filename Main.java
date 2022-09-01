public class Main {
  public static void main(String[] args) {
    Canvas c = new Canvas();
		c.drawLine(5, 10, 20, 10, 3);
		System.out.println(c.canvas);
		c.render();

		double[] d = {1, 2};
		Vector v = new Vector(d);
  }
}