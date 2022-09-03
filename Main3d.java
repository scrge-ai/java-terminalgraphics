import java.awt.*;
import javax.swing.*;

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
                {0, 0, -2/(far-near), -2*far*near/(far-near)},
                { -(right+left)/(right-left), -(top+bottom)/(top-bottom), -(far+near)/(far-near), 1}
        };

        Camera cam = new Camera(new ProjectionMatrix(right, left, top, bottom, far, near));

        double[] center = {65, 65, 65, 1};
        double[][] points = {        // front
                {40, 40,  90},
                {90, 40,  90},
                {90,  90,  90},
                {40,  90,  90},
                // back
                {40, 40, 40},
                {90, 40, 40},
                {90,  90, 40},
                {40,  90, 40}
        };

        int[] indices = {        // front
                0, 1, 2,
                2, 3, 0,
                // right
                1, 5, 6,
                6, 2, 1,
                // back
                7, 6, 5,
                5, 4, 7,
                // left
                4, 0, 3,
                3, 7, 4,
                // bottom
                4, 5, 1,
                1, 0, 4,
                // top
                3, 2, 6,
                6, 7, 3
        };

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
                {Math.cos(0.05),  0, Math.sin(0.05), 0},
                {0,                        1,          0              , 0},
                {-Math.sin(0.05), 0, Math.cos(0.05),  0},
                {0,                        0,          0,               1}
        };

        double[][] yawarr = {
                {1,             0,              0,                     0},
                {0, Math.cos(0.03), -Math.sin(0.03), 0},
                {0, Math.sin(0.03),  Math.cos(0.03), 0},
                {0,             0,              0,                     1}
        };

        double[][] rollarr = {
                {Math.cos(0.02),-Math.sin(0.02), 0, 0},
                {Math.sin(0.02), Math.cos(0.02),  0, 0},
                {0,                      0,                         1, 0},
                {0,                      0,                         0, 1}
        };

        double[][] model = {
                {1, 0, 0, 0},
                {0, 1, 0, -30},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        Matrix modelMatrix = new Matrix(model);
        Matrix transform = (new Matrix(trans)).Multiply(new Matrix(pitcharr)).Multiply(new Matrix(yawarr)).Multiply(new Matrix(rollarr)).Multiply(new Matrix(transback));

        String newframe = "";
        for(int i = 0; i < 100; i++) newframe += "\n";

        PhongRenderer render = new PhongRenderer();

        while(true){
            //System.out.print("\r" + newframe + "\r");
            //System.out.flush();
            System.out.print(newframe);
            c.render();
            c.clear();
            //List triangles = new ArrayList<Double[3][2]>();
            for(int j = 0; j < indices.length-1; j++) {
                int i = indices[j];
                int i1 = indices[j+1];
                Vector projected1 = cam.ProjectPoint((new Vector(points[i])).MatDot(modelMatrix));
                Vector projected2 = cam.ProjectPoint((new Vector(points[i1])).MatDot(modelMatrix));
                double[] applymodel = (new Vector(points[i])).MatDot(modelMatrix).vector;
                //System.out.println();
                //System.out.println("original point: " + points[i][0] + " " + points[i][1] + " " + points[i][2]);
                //System.out.println("after model matrix: " + applymodel[0] + " " + applymodel[1] + " " + applymodel[2]);
                //System.out.println("projected point: " + projected1.vector[0] + " " + projected1.vector[1] + " " + projected1.vector[2] + " ");
                int x1 = (int) (projected1.vector[0] * 50);
                int y1 = (int) (projected1.vector[1] * 50);

                int x2 = (int) (projected2.vector[0] * 50);
                int y2 = (int) (projected2.vector[1] * 50);

                c.drawLine(x1, y1, x2, y2, 3);
            }

            //c = render.RenderTriangles()

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