public class Vector{
	public double[] vector;
	private final MatrixOperation op = new MatrixOperation();

	public Vector(double[] vec){
		this.vector = vec;
	}

	public Vector Add(Vector vec){
		return new Vector(op.VecAdd(vector, vec.vector));
	}

	public Vector MatDot(Matrix mat){
		return new Vector(op.VecMatDot(mat.matrix, vector));
	}
}