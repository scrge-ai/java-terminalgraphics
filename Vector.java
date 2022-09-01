public class Vector{
	public double[] vector;
	private final MatrixOperation op = new MatrixOperation();

	public Vector(double[] vec){
		this.vector = vec;
	}

	public void Add(Vector vec){
		return new Vector(op.VecAdd(vector, vec.vector));
	}
}