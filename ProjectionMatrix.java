public class ProjectionMatrix extends Matrix{
    public ProjectionMatrix(double right, double left, double top, double bottom, double far, double near){
        super(new double[1][1]);
        double[][] proj = {
                {2/(right-left), 0, 0, 0},
                {0, 2/(top-bottom), 0, 0},
                {0, 0, -2/(far-near), -2*far*near/(far-near)},
                { -(right+left)/(right-left), -(top+bottom)/(top-bottom), -(far+near)/(far-near), 1}
        };
        this.matrix = proj;
    }
}
