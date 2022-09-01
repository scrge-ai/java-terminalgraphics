

public class Main {
  public static void main(String[] args) {
    Canvas c = new Canvas();
		double [] d = {0, 0, 1};
		Vector p1 = new Vector(d);
		double[] d1 = {20, 10, 1};
		Vector p2 = new Vector(d1);

		double[][] mat = 
			{{Math.cos(-0.01d), -Math.sin(-0.01d), 0}, 
			 {Math.sin(-0.01d),  Math.cos(-0.01d),  0}, 
			 {0           , 0,            1}, };

		Matrix rot = new Matrix(mat);

		while(true){
			System.out.print("\033[H\033[2J");
			System.out.flush();
			c.render();
			c.clear();
			
			c.drawLine((int)p1.vector[0], (int)p1.vector[1], (int)p2.vector[0], (int)p2.vector[1], 3);
	  	
			try{
				Thread.sleep(100);
			} catch(InterruptedException e){
				
			}
			p2 = p2.MatDot(rot);
		}
	}
}