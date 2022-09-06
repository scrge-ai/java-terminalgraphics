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

	public double VecDot(Vector vec) { return op.VecVecDot(vec.vector, vector); }

	public double Magnitude() {
		double ret = 0;
		for(int i = 0; i < vector.length; i++){
			ret += vector[i]*vector[i];
		}
		return Math.sqrt(ret);
	}
	public double Angle(Vector vec){
		return Math.acos((VecDot(vec))/(Magnitude()*vec.Magnitude()));
	}

	public Vector Reverse(){
		Vector ret = new Vector(new  double[vector.length]);

		for(int i = 0; i < vector.length; i++){
			ret.vector[i] = -vector[i];
		}
		return ret;
	}

	public Vector Normalize(){
		double[] out = new double[vector.length];
		for(int i = 0; i < vector.length; i++){
			out[i] = vector[i]/Magnitude();
		}
		return new Vector(out);
	}
}