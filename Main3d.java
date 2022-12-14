import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        double[] center = {65, 65, 65};
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

        double[][] normals = {
                {1, 1, -1},
                {-1, 1, -1},
                {-1, -1, -1},
                {1, -1, -1},
                {1, 1, 1},
                {-1, 1, 1},
                {-1, -1, 1},
                {1, -1, 1}
        };

        double[] colors = {1, 2, 3, 4, 1, 2, 3, 4};

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
                {Math.cos(0.01),  0, Math.sin(0.01), 0},
                {0,                        1,          0              , 0},
                {-Math.sin(0.01), 0, Math.cos(0.01),  0},
                {0,                        0,          0,               1}
        };

        double[][] yawarr = {
                {1,             0,              0,                     0},
                {0, Math.cos(0.01), -Math.sin(0.01), 0},
                {0, Math.sin(0.01),  Math.cos(0.01), 0},
                {0,             0,              0,                     1}
        };

        double[][] rollarr = {
                {Math.cos(0.01),-Math.sin(0.01), 0, 0},
                {Math.sin(0.01), Math.cos(0.01),  0, 0},
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
        for(int i = 0; i < 120; i++) newframe += "\n";

        PhongRenderer render = new PhongRenderer();

        /*for(int j = 0; j < 100; j++)
            for(int i = 0; i < points.length; i++){
                points[i] = (new Vector(points[i])).MatDot(transform).vector;
            }*/

        while(true){
            //System.out.print("\r" + newframe + "\r");
            //System.out.flush();
            System.out.print(newframe);
            c.render();
            c.clear();
            //ArrayList<double[][]> triangles = new ArrayList<double[][]>();
            Comparator<Vector[]> depthCompare = new Comparator<Vector[]>() {
                @Override
                public int compare(Vector[] o1, Vector[] o2) {
                    Vector centroid1 = o1[0].Add(o1[1]).Add(o1[2]);
                    Vector centroid2 = o2[0].Add(o2[1]).Add(o2[2]);
                    return Double.compare(centroid1.vector[2], centroid2.vector[2]);
                }
            };
            ArrayList<Vector[]> triangleslist = new ArrayList<Vector[]>();

            for(int j = 0; j < indices.length/3; j++){
                int i = indices[3*j];
                int i1 = indices[3*j+1];
                int i2 = indices[3*j+2];
                triangleslist.add(new Vector[]{(new Vector(points[i])), (new Vector(points[i1])), (new Vector(points[i2]))});
            }

            Collections.sort(triangleslist, depthCompare);
            Vector[][] trianglenormals = new Vector[indices.length/3][3];
            double[][][] triangles = new double[triangleslist.size()][3][2];
            double[][][] triangles_noproj = new double[indices.length/3][3][3];

            for(int j = 0; j < indices.length/3; j++) {
                int i = indices[3*j];
                int i1 = indices[3*j+1];
                int i2 = indices[3*j+2];
                Vector[] normal = {
                        new Vector(new double[]{normals[i][0], normals[i][1], normals[i][2]}),
                        new Vector(new double[]{normals[i1][0], normals[i1][1], normals[i1][2]}),
                        new Vector(new double[]{normals[i2][0], normals[i2][1], normals[i2][2]})
                };

                triangles_noproj[j] = new double[][]{triangleslist.get(j)[0].vector, triangleslist.get(j)[1].vector, triangleslist.get(j)[2].vector};
                trianglenormals[j] = normal;
            }
            double[] a = {1};
            //Vector[][] normals = new Vector[triangles.length][triangles[0].length];
            double[] lightpos = {0, 0, 30, 1};
            c.canvas = render.RenderTriangles(triangles_noproj, trianglenormals, 60, 100, lightpos, cam);

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