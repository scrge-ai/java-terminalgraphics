public class ProjectionMatrix extends Matrix{
    public ProjectionMatrix(double right, double left, double top, double bottom, double far, double near){
        super(new double[1][1]);
        double fov = 90;
        double s = 1/(Math.tan(fov/2*(3.1415)/180));
        /*double[][] proj = {
                {2/(right-left), 0, 0, 0},
                {0, 2/(top-bottom), 0, 0},
                {0, 0, -2/(far-near), -2*far*near/(far-near)},
                { -(right+left)/(right-left), -(top+bottom)/(top-bottom), -(far+near)/(far-near), 1}
        };*/
        double[][] proj = {
                {2*near/(right-left), 0, 0, -near*(right+left)/(right-left)},
                {0, 2*near/(top-bottom), 0, -near*(top+bottom)/(top-bottom)},
                {0, 0, -(far+near)/(far-near), 2*far*near/(near-far)},
                { 0, 0, -1, 0}
        };
        this.matrix = proj;
    }
}
