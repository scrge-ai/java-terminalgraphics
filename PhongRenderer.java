public class PhongRenderer {
    private static double area(double x1, double y1, double x2, double y2,
                       double x3, double y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                x3*(y1-y2))/2.0);
    }

    private static boolean isInside(double x1, double y1, double x2,
                            double y2, double x3, double y3, double x, double y)
    {
        /* Calculate area of triangle ABC */
        double A = area (x1, y1, x2, y2, x3, y3);

        /* Calculate area of triangle PBC */
        double A1 = area (x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PAC */
        double A2 = area (x1, y1, x, y, x3, y3);

        /* Calculate area of triangle PAB */
        double A3 = area (x1, y1, x2, y2, x, y);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (Math.abs(A1 + A2 + A3) == A);
    }

    public double[][][] getTriangles(double[][] points, int[] indices){
        //double[][][] triangles = new double[points.siz];
        return new double[1][1][1];
    }

    public Vector vertexShader(Camera camera, Vector vert){
        return camera.ProjectPoint(vert);
    }

    public double fragShader(Vector fragPos, Vector lightPos, Vector faceNormal) {
        //fragPos is the position of pixel BEFORE projection - outputs color AFTER projection, no coordinates
        //System.out.println(fragPos);
        double ambient = 1;
        Vector lightHit = lightPos.Add(fragPos.Reverse());
        //System.out.println(lightHit.Magnitude());
        //System.out.println(faceNormal.Magnitude());
        return ambient + 2*Math.max(lightHit.VecDot(faceNormal)/ lightHit.Magnitude(), 0);
        //return Math.random()*3;
    }

    private double distance2D(double[] a, double[] b){
        return Math.sqrt((a[0]-b[0])*(a[0]-b[0])+(a[1]-b[1])*(a[1]-b[1]));
    }

    public int[][] RenderTriangles(double[][][] triangles, Vector[][] normals, int canvasLength, int canvasWidth, double[] lightPos){ //, Vector[] normals, double[] lightPos
        int[][] result = new int[canvasLength][canvasWidth]; //todo: implement z-buffer

        double[][] colors = new double[triangles.length][3];

        for(int i = 0; i < triangles.length; i++){
            for(int j = 0; j < triangles[i].length; j++){
                double[] point4d = {triangles[i][j][0], triangles[i][j][1],triangles[i][j][1], 0};
                colors[i][j] = fragShader(new Vector(point4d), new Vector(lightPos), normals[i][j]);
                //System.out.println(colors[i][j]);
            }
        }

        //iterate through all pixels, applying fragShader to each pixel by getting its position before projection
        for(int i = 0; i < canvasLength; i++){
            for(int j = 0; j < canvasWidth; j++){
                for(int k = 0; k < triangles.length; k++){

                    double[] curpoint = {i, j};
                    if(isInside(triangles[k][0][0], triangles[k][0][1], triangles[k][1][0], triangles[k][1][1], triangles[k][2][0], triangles[k][2][1], (double)i, (double)j)){
                        double dist1 = distance2D(triangles[k][0], curpoint);
                        double dist2 = distance2D(triangles[k][1], curpoint);
                        double dist3 = distance2D(triangles[k][2], curpoint);
                        double totaldist = dist1+dist2+dist3;
                        result[i][j] = (int) (dist1/totaldist*colors[k][0] + dist2/totaldist*colors[k][1] + dist3/totaldist*colors[k][2]);
                    }
                }
            }
        }

        return result;

    }
}
