public class Main3d {
    public static void main(String[] args) {
        Canvas c = new Canvas();
        double [] d = {0, 0, 1};
        Vector p1 = new Vector(d);
        double[] d1 = {0, 50, 1};
        Vector p2 = new Vector(d1);

        double[][] mat =
                {{Math.cos(-0.03d), -Math.sin(-0.03d), 0},
                        {Math.sin(-0.03d),  Math.cos(-0.03d),  0},
                        {0           , 0,            1}, };

        Matrix rot = new Matrix(mat);
        String newframe = "";
        for(int i = 0; i < 50; i++) newframe += "\n";
        System.out.println();

        while(true){
            System.out.print(newframe);
            //System.out.flush();
            c.render();
            c.clear();

            c.drawLine((int)p1.vector[0], (int)p1.vector[1], (int)p2.vector[0], (int)p2.vector[1], 3);
	  	
			/*try{
				//Thread.sleep(50);
			} catch(InterruptedException e){
				
			}*/
            p2 = p2.MatDot(rot);
        }
    }
}