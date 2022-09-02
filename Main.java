

public class Main {
	public static void main(String[] args) {
		Canvas c = new Canvas();
		double [] d = {20, 10, 1};
		Vector p1 = new Vector(d);
		double[] d1 = {20, 5, 1};
		Vector p2 = new Vector(d1);

		double[][] verts = {{30, 10, 1}, {20, 5, 1}, {30, 15, 1}};

		double[][] mat =
				{{Math.cos(0.1d), -Math.sin(0.1d), 0},
						{Math.sin(0.1d),  Math.cos(0.1d),  0},
						{0           , 0,            1}, };

		double[][] mat1 =
				{{1, 0, 20},
						{0, 1, 20},
						{0, 0, 1}};

		double[][] mat2 =
				{{1, 0, -20},
						{0, 1, -20},
						{0, 0, 1}};

		Matrix rot = new Matrix(mat);
		Matrix translate1 = new Matrix(mat1);
		Matrix translate2 = new Matrix(mat2);
		Matrix transform = translate1.Multiply(rot).Multiply(translate2);

		String linebreak = "";
		for(int i = 0; i < 1000; i++) linebreak += "\n";

		while(true){
			//System.out.print(linebreak);
			System.out.print(linebreak);
			//System.out.println();
			c.render();
			c.clear();

			for(int i = 0; i < verts.length; i++){
				c.drawLine((int)verts[i][0], (int)verts[i][1], (int)verts[(i+1)%verts.length][0], (int)verts[(i+1)%verts.length][1], 3);
			}

			for(int i = 0; i < verts.length; i++){
				verts[i] = (new Vector(verts[i])).MatDot(transform).vector;
			}
			try{
				Thread.sleep(10);
			} catch(Exception e){

			}
		}
	}
}