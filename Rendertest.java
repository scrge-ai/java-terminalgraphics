public class Rendertest {
    public static void main(String[] args){
        Canvas c = new Canvas();
        double[][][] triangles = {
                {
                        {10, 10},
                        {20, 10},
                        {10, 20}
                }
        };

        PhongRenderer render = new PhongRenderer();

        String newline = "";
        for(int i = 0; i < 100; i++) newline += "\n";

        while(true){
            System.out.print(newline);
            //c.render();
            //c.canvas = render.RenderTriangles(triangles, new Vector[1][1], 60, 60, new double[]{0, 0, 0});
        }
    }
}
