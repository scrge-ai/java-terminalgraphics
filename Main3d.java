public class Main3d {
    public static void main(String[] args) {
        Canvas c = new Canvas();
        double [] d = {0, 0, 1};
        Vector p1 = new Vector(d);
        double[] d1 = {0, 50, 1};
        Vector p2 = new Vector(d1);

        double right = 100;
        double left = -100;
        double top = 100;
        double bottom = -100;
        double near = 1;
        double far = 100;

        double[][] proj = {
                {2/(right-left), 0, 0, 0},
                {0, 2/(top-bottom), 0, 0},
                {0, 0, -2/(far-near), 0},
                { -(right+left)/(right-left), -(top+bottom)/(top-bottom), -(far+near)/(far-near), 1}
        };

        Camera cam = new Camera(new Matrix(proj));

        double[] center = {50, 50, 50, 1};
        double[][] points = {{90, 90, 90, 1},//z is depth
                            {90, 90, 40, 1},
                            {90, 40, 90, 1},
                            {40, 90, 90, 1},
                            {90, 40, 40, 1},
                            {40, 40, 90, 1},
                            {40, 90, 40, 1},
                            {40, 40, 40, 1}};

        //Vector projected = cam.ProjectPoint(new Vector(p));

        double[][] trans = {
                {1, 0, 0, center[0]},
                {0, 1, 0, center[0]},
                {0, 0, 1, center[0]},
                {0, 0, 0, 1}
        };

        double[][] transback = {
                {1, 0, 0, -center[0]},
                {0, 1, 0, -center[0]},
                {0, 0, 1, -center[0]},
                {0, 0, 0, 1}
        };

        double[][] pitcharr = {
                {Math.cos(0.1),  0, Math.sin(0.1), 0},
                {0,                        1,          0              , 0},
                {-Math.sin(0.1), 0, Math.cos(0.1),  0},
                {0,                        0,          0,               1}
        };

        Matrix transform = (new Matrix(trans)).Multiply(new Matrix(pitcharr)).Multiply(new Matrix(transback));

        String newframe = "";
        for(int i = 0; i < 100; i++) newframe += "\n";


        while(true){
            //System.out.print("\r" + newframe + "\r");
            //System.out.flush();
            System.out.print(newframe);
            c.render();
            c.clear();

            for(int i = 0; i < points.length; i++) {
                Vector projected1 = cam.ProjectPoint(new Vector(points[i]));
                Vector projected2 = cam.ProjectPoint(new Vector(points[(i+1)%points.length]));
                int x1 = (int) (projected1.vector[0] * 50);
                int y1 = (int) (projected1.vector[1] * 50);

                int x2 = (int) (projected2.vector[0] * 50);
                int y2 = (int) (projected2.vector[1] * 50);

                c.drawLine(x1, y1, x2, y2, 3);
            }

            for(int i = 0; i < points.length; i++){
                points[i] = (new Vector(points[i])).MatDot(transform).vector;
            }

            try{
                Thread.sleep(10);
            } catch(Exception e){

            }
        }
    }
}