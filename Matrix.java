public class Matrix{
	public double[][] matrix;
	private final MatrixOperation op = new MatrixOperation();
	
	public Matrix(double[][] mat){
		this.matrix = mat;
	}

	public Matrix Multiply(Matrix x){
		return new Matrix(op.MatMul(matrix, x.matrix));
	}

	public Matrix MultiplyScalar(double x){
		return new Matrix(op.MatMul_Scalar(matrix, x));
	}

	public Matrix Add(Matrix x){
		return new Matrix(op.MatAdd(matrix, x.matrix));
	}

	
}