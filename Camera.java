public class Camera{
	public Vector pos;
	public Vector rot;
	public Matrix projection; //usually stays the same
	public Matrix view; //built off pos, rot
	public Camera(Matrix proj){
		double[] posarr = {0, 0, 0, 1}; //4d vectors allow for translation matrices in 3d space
		this.pos = new Vector(posarr);
		double[] rotarr = {0, 0, 0, 1};
		this.rot = new Vector(rotarr);
		this.projection = proj;
		UpdateViewMatrix();
	}

	public void UpdateViewMatrix(){
		double[][] posarr = {
				{1, 0, 0, pos.vector[0]},
				{0, 1, 0, pos.vector[1]},
				{0, 0, 1, pos.vector[2]},
				{0, 0, 0,     1        }
		};

		double[][] yawarr = {
				{1,             0,              0,                     0},
				{0, Math.cos(rot.vector[0]), -Math.sin(rot.vector[0]), 0},
				{0, Math.sin(rot.vector[0]),  Math.cos(rot.vector[0]), 0},
				{0,             0,              0,                     1}
		};

		double[][] pitcharr = {
				{Math.cos(rot.vector[1]),  0, Math.sin(rot.vector[1]), 0},
				{0,                        1,          0              , 0},
				{-Math.sin(rot.vector[1]), 0, Math.cos(rot.vector[1]),  0},
				{0,                        0,          0,               1}
		};

		double[][] rollarr = {
				{Math.cos(rot.vector[1]),-Math.sin(rot.vector[1]), 0, 0},
				{Math.sin(rot.vector[1]), Math.cos(rot.vector[1]),  0, 0},
				{0,                      0,                         1, 0},
				{0,                      0,                         0, 1}
		};

		Matrix transMat = new Matrix(posarr);
		Matrix yawMat = new Matrix(yawarr);
		Matrix pitchMat = new Matrix(pitcharr);
		Matrix rollMat = new Matrix(rollarr);

		this.view = transMat.Multiply(yawMat).Multiply(pitchMat).Multiply(rollMat);
	}

	public Vector ProjectPoint(Vector point){
		return point.MatDot(view.Multiply(projection));
	}
}