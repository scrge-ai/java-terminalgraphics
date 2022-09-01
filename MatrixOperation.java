public class MatrixOperation{
	double[] VecAdd(double[] a, double[] b){
		double[] ret = new double[a.length];
		for(int i = 0; i < a.length; i++){
			ret[i] = a[i] + b[i];
		}
		return ret;
	}

	double VecVecDot(double[] a, double[] b){
		double ret = 0;
		for(int i = 0; i < a.length; i++){
			ret += a[i] * b[i];
		}
		return ret;
	}

	double[] VecMatDot(double[] a, double[][] b){
		double[] ret = new double[a.length];
		for(int i = 0; i < a.length; i++){
			int temp = 0;
			for(int j = 0; j < b[i].length; j++){
				temp += a[i] * a[i][j];
			}
			ret[i] = temp;
		}
		return ret;
	}
	
	double multiplyMatricesCell(double[][] a, double[][] b, int row, int col) {
	    double cell = 0;
	    for (int i = 0; i < b.length; i++) {
	        cell += a[row][i] * b[i][col];
	    }
	    return cell;
	}

	double[][] MatMul_Scalar(double[][] a, double b){
		double[][] ret = new double[a.length][a[0].length];

		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[i].length; j++){
				ret[i][j] = a[i][j] * b;
			}
		}

		return ret;
	}
	
	double[][] MatMul(double[][] a, double[][] b) {
	    double[][] result = new double[a.length][b[0].length];
	
	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = multiplyMatricesCell(a, b, row, col);
	        }
	    }
	
	    return result;
	}

	double[][] MatAdd(double[][] a, double[][] b){
		double[][] ret = new double[a.length][a[0].length];
		
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[i].length; j++){
				ret[i][j] = a[i][j] + b[i][j];
			}
		}

		return ret;
	}
}