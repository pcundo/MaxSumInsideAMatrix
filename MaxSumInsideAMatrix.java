package MaxSumInsideASquare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MaxSumInsideAMatrix {

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

		int matrixSum[][] = addSumMatrix(matrix);
		calculateMaxSum(matrixSum);
		
	}

	//The running time for this O(n^3). 
	/**
	 * Assuming the dimension of the matrix is 3x3.
	 * During each iteration the algorithm calculation the max sum of the following:
	 * First iteration:
	 * row 1
	 * row 1,2
	 * row 1,2,3
	 * 
	 * Second iteration:
	 * row 2
	 * row 2, 3
	 * 
	 * Third iteration:
	 * row 3
	 * 
	 * Additional note: The reason that we use matrixSum[iteration-1][0] because say you're at iteration 1 (starting from 0) and row 3.
						Meaning you only want to calculate the max sum of row 2 and row 3 in matrixSum. Therefore you need to subtract
						row one from you matrixSum and that's where matrixSum[iteration-1][col] comes in.
	 * 
	 * @param matrixSum
	 * @return
	 */
	public static void calculateMaxSum(int [][] matrixSum){

		int [] sum = new int[dimension];
		int max = 0, startingRow = 0, endingRow = 0, startingCol = 0, endingCol = 0;
		int [] pos = new int[dimension];
		for(int iteration = 0; iteration < dimension; iteration++){
			for(int row = iteration; row < dimension; row++){
				sum[0] = matrixSum[row][0] - (iteration == 0 ? 0 : matrixSum[iteration-1][0]);
				for(int col = 1; col < dimension; col++){
					//The starting of the Kadane's algorithm by row.
					//Find the max between adding the previous sum and not adding the previous sum.
					/*sum[col] = Math.max(sum[col-1] + matrixSum[row][col] - (iteration == 0 ? 0 : matrixSum[iteration - 1][col]), 
													 matrixSum[row][col] - (iteration == 0 ? 0 : matrixSum[iteration - 1][col]));*/
					if(sum[col-1] > 0){
						sum[col] = sum[col-1] + matrixSum[row][col] - (iteration == 0 ? 0 : matrixSum[iteration -1][col]);
						pos[col] = pos[col-1];
					}else{
						sum[col] = matrixSum[row][col] - (iteration == 0 ? 0 : matrixSum[iteration - 1][col]);
						pos[col] = col;
					}
					//Assign max value
					if(sum[col] > max){
						max = sum[col];
						startingRow = iteration;
						endingRow = row;
						endingCol = col;
						startingCol = pos[col];

					}
				}
			}
		}

		print(matrixSum);
		System.out.println("\nMax: " + max + "\n");
		printAnswer(matrixSum, startingRow, startingCol, endingRow, endingCol);
		
	}


	/**
	 * The purpose of this method is to represent each row in matrixSum as
	 * the sum of the previous row up to that row.
	 * For instance, 
	 * Matrix:
	 * 1  2  3
	 * 0 -1  2
	 * 9  7 -4
	 * 
	 * matrixSum:
	 * 1  2  3
	 * 1  1  5
	 * 10 8  1
	 * 
	 * Think of it as a square. The first row represents one square.
	 * The second row represents the sum of a square containing the
	 * first two rows. And the third row is the sum of the first 3
	 * rows. Notice, 10 = 1 + 0 + 9 and 8 = 2 + (-1) + 7 
	 * @param matrix
	 * @return
	 */
	public static int[][] addSumMatrix(int [][] matrix){
		int [][] matrixSum = new int[dimension][dimension];
		for(int r = 0; r < dimension; r++){
			for(int c = 0; c < dimension; c++){
				if(r == 0)
					matrixSum[r][c] = matrix[r][c];
				else{
					matrixSum[r][c] = matrix[r][c] + matrixSum[r-1][c];
				}
			}
		}

		return matrixSum;
	}


	/**
	 * Kadane's Algorithm return a maximum sum of contiguous subsequence in
	 * a one dimension sequence or array.
	 * (not used in this problem)
	 */
	public static void kadane(int [] array){
		int max = 0;
		int current = 0;
		int lastPosition = 0;
		int dimension = array.length;
		int [] pos = new int[dimension];
	
		int up_to_here = array[0];
		for(int i = 1; i < dimension; i++){

			current = up_to_here + array[i];

			if(current > 0){
				up_to_here = current;
				pos[i] = pos[i-1];
			}else{
				up_to_here = 0;
				pos[i] = i;
			
			}

			if(max < up_to_here){
				max = up_to_here;
				lastPosition = i;
				
			}

			/* Alternatively
			up_to_here = Math.max(0, up_to_here + array[i]);
			max = Math.max(max, up_to_here);
			 */		
		}

		System.out.println(max);
		System.out.println("Start: " + pos[lastPosition] + " End: " + lastPosition);
		
	}


	/**
	 * Print 2D array
	 */
	public static void print(int [][] matrix){
		for(int i = 0; i < matrix.length; i++){
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
	
	/**
	 * Print 2D array
	 */
	public static void printAnswer(int [][] matrix, int startingRow, int startingCol, int endingRow, int endingCol ){
		for(int row = startingRow; row <= endingRow; row++){
			for(int col = startingCol; col <= endingCol; col++){
				System.out.print(matrix[row][col] + " ");
			}
				System.out.println();
		}
	}

}
