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
        return (Math.abs(A1 + A2 + A3-A) < 0.0001);
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
        Vector lightHit = lightPos.Add(fragPos.Reverse()).Normalize();
        Vector normal = faceNormal.Normalize();
        //System.out.println(lightHit.Magnitude());
        //System.out.println(faceNormal.Magnitude());
        return ambient + 20*Math.max(lightHit.VecDot(normal.Reverse()), 0);
        //return 3;
    }

    private double distance2D(double[] a, double[] b){
        return Math.sqrt((a[0]-b[0])*(a[0]-b[0])+(a[1]-b[1])*(a[1]-b[1]));
    }

    public int[][] RenderTriangles(double[][][] triangles, Vector[][] normals, int canvasLength, int canvasWidth, double[] lightPos, Camera camera){ //, Vector[] normals, double[] lightPos
        int[][] result = new int[canvasLength][canvasWidth]; //todo: implement z-buffer

        double[][] colors = new double[triangles.length][3];
        double[][][] projectedTriangles = new double[triangles.length][3][2];

        for(int i = 0; i < triangles.length; i++) {
            for (int j = 0; j < triangles[i].length; j++) {
                double[] point4d = {triangles[i][j][0], triangles[i][j][1], triangles[i][j][2], 0};
                colors[i][j] = fragShader(new Vector(point4d), new Vector(lightPos), normals[i][j]);
                //System.out.println(colors[i][j]);
                Vector proj = camera.ProjectPoint(new Vector(point4d));
                projectedTriangles[i][j] = new double[]{proj.vector[0] * 60, proj.vector[1] * 60};
            }
        }

        //iterate through all pixels, applying fragShader to each pixel by getting its position before projection
        for(int i = 0; i < canvasLength; i++){
            for(int j = 0; j < canvasWidth; j++){
                for(int k = 0; k < triangles.length; k++){

                    double[] curpoint = {i, j};
                    if(isInside(projectedTriangles[k][0][0], projectedTriangles[k][0][1], projectedTriangles[k][1][0], projectedTriangles[k][1][1], projectedTriangles[k][2][0], projectedTriangles[k][2][1], (double)i, (double)j)){
                        double dist1 = distance2D(projectedTriangles[k][0], curpoint);
                        double dist2 = distance2D(projectedTriangles[k][1], curpoint);
                        double dist3 = distance2D(projectedTriangles[k][2], curpoint);
                        double totaldist = dist1+dist2+dist3;
                        double a1 = area(projectedTriangles[k][0][0], projectedTriangles[k][0][1], projectedTriangles[k][1][0], projectedTriangles[k][1][1], curpoint[0], curpoint[1]);
                        double a2 = area(projectedTriangles[k][1][0], projectedTriangles[k][1][1], projectedTriangles[k][2][0], projectedTriangles[k][2][1], curpoint[0], curpoint[1]);
                        double a3 = area(projectedTriangles[k][2][0], projectedTriangles[k][2][1], projectedTriangles[k][0][0], projectedTriangles[k][0][1], curpoint[0], curpoint[1]);
                        //result[i][j] = (int) Math.min((colors[k][0]+colors[k][1]+colors[k][2])/3, 6);
                        double color = (a2*(colors[k][0]) + a3*colors[k][1] + a1*colors[k][2])/(a1+a2+a3);
                        //result[i][j] = (int) Math.min((dist1/totaldist*colors[k][0]+dist2/totaldist*colors[k][1]+dist3/totaldist*colors[k][2]), 6);
                        result[i][j] = Math.min((int)(color), 30);
                    }
                }
            }
        }

        return result;

    }
}
