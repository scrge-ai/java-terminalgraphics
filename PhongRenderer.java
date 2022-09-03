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
        return (Math.abs(A1 + A2 + A3) - A < 0.001);
    }

    public double fragShader(Vector fragPos, Vector lightPos, Vector faceNormal) {
        //fragPos is the position of pixel BEFORE projection - outputs color AFTER projection, no coordinates
        double ambient = 0.1;
        Vector lightHit = lightPos.Add(fragPos.Reverse());
        return ambient + lightHit.Angle(faceNormal)/Math.PI;
    }

    public int[][] RenderTriangles(double[][][] triangles, Vector[] normals, double[] lightPos, int canvasLength, int canvasWidth){
        int[][] result = new int[canvasLength][canvasWidth];

        //iterate through all pixels, applying fragShader to each pixel by getting its position before projection
        for(int i = 0; i < canvasLength; i++){
            for(int j = 0; j < canvasWidth; j++){
                for(int k = 0; k < triangles.length; k++){


                    if(isInside(triangles[k][0][0], triangles[k][0][1], triangles[k][1][0], triangles[k][1][1], triangles[k][2][0], triangles[k][2][1], (double)i, (double)j)){
                        result[i][j] = 1;
                    }
                }
            }
        }

        return result;

    }
}
