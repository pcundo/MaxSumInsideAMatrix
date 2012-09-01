package MaxSumInsideASquare;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MaxSumInsideAMatrixRefactor {
	public static int dimension = 0; 
	public static void main(String [] args) throws Exception{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		dimension = Integer.parseInt(input.readLine().trim());
		int [][] matrix = new int[dimension][dimension];
		String s = "";
		int row = 0, col = 0;
		String token[];
		while(!(s=input.readLine()).equals("end")){
			token = s.split(" ");
			for(int i = 0; i < token.length; i++){
				matrix[row][col] = Integer.parseInt(token[i]);
				col++;
				if(col == dimension){
					row++;
					col = 0;
				}
			}
		}

		calculateMaxSum(matrix);

	}

	/**
	 * Combine the adding step with with Kadane's step
	 * @param matrixSum
	 */
	public static void calculateMaxSum(int [][] matrix){

		int [] sum = new int[dimension];
		int maxSum = 0;
		int current = 0;
		int max = 0;
		for(int iteration = 0; iteration < dimension; iteration++){
			sum = new int[dimension];
			for(int row = iteration; row < dimension; row++){
				current = 0;
				for(int col = 0; col < dimension; col++){

					//This part will do the row addition
					sum[col] = sum[col] + matrix[row][col];

					//Kadane's Algorithm
					current = current + sum[col];

					//This one does the comparison between columns.
					if(maxSum < current)
						maxSum = current;

					if(current < 0)
						current = 0;

				}

			}
			
			
		}
		System.out.println(maxSum);
	}
}
