package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SudokuMain {
	static int length;
	static int numberOfBox1;

	public static void main(String[] args) throws FileNotFoundException {
		
		int[][] matrix=  readFromUser();
		writeInCSV(matrix, 0);
		SudokuMain obj = new SudokuMain();
		matrix = obj.read();
		display(matrix, length);
		if (problemSolving(0, 0, matrix)) {
			display(matrix, length);
			writeInCSV(matrix, 1);
		} else
			System.out.println("NONE");
	}

	private static int[][] readFromUser() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the n in nxn matrix:\t");
		 length = scanner.nextInt();
		int[][] in = new int[length][length];
		for (int c = 0; c < length; c++) {
			System.out.print("Enter the" + c + " row");
			String tmp = scanner.next();
			String[] tmparray = tmp.split("(?!^)");
			for (int d = 0; d < tmparray.length; d++) {
				in[c][d] = Integer.parseInt(tmparray[d]);
			}

		}
		return in;

	}

	private static synchronized void writeInCSV(int[][] matrix, int path) {
		try {
			FileWriter writer;
			if(path==1){
				 writer = new FileWriter("out.csv");
			}else{
				 writer = new FileWriter("data.csv");
			}
			for (int i = 0; i < length; ++i) {
				for (int j = 0; j < length; ++j) {
					writer.append(matrix[i][j] == 0 ? "0" : Integer
							.toString(matrix[i][j]));
					if (j < length - 1) {
						writer.append(',');
					} else {
						writer.append('\n');
					}
				}
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static boolean problemSolving(int i, int j, int[][] cells) {
		if (i == length) {
			i = 0;
			if (++j == length)
				return true;
		}
		if (cells[i][j] != 0) // skip filled cells
			return problemSolving(i + 1, j, cells);

		for (int val = 1; val <= length; ++val) {
			if (isValide(i, j, val, cells)) {
				cells[i][j] = val;
				if (problemSolving(i + 1, j, cells))
					return true;
			}
		}
		cells[i][j] = 0; // reset on backtrack
		return false;
	}

	static boolean isValide(int i, int j, int val, int[][] cells) {
		for (int k = 0; k < length; ++k)
			// row
			if (val == cells[k][j])
				return false;

		for (int k = 0; k < length; ++k)
			// col
			if (val == cells[i][k])
				return false;

		int boxRowOffset = (i / numberOfBox1) * numberOfBox1;
		int boxColOffset = (j / numberOfBox1) * numberOfBox1;
		for (int k = 0; k < numberOfBox1; ++k)
			// box
			for (int m = 0; m < numberOfBox1; ++m)
				if (val == cells[boxRowOffset + k][boxColOffset + m])
					return false;

		return true; // no violations, so it's legal
	}

	private static void display(int[][] matrix, int length) {
		double numberOfBox = Math.sqrt((double) length);
		numberOfBox1 = (int) numberOfBox;
		for (int i = 0; i < length; ++i) {
			if (i % numberOfBox1 == 0)
				System.out.println(" -----------------------");
			for (int j = 0; j < length; ++j) {
				if (j % numberOfBox1 == 0)
					System.out.print("| ");
				System.out.print(matrix[i][j] == 0 ? " " : Integer
						.toString(matrix[i][j]));

				System.out.print(' ');
			}
			System.out.println("|");
		}
		System.out.println(" -----------------------");
	}

	public int[][] read() {
		String csvFile = "data.csv";
		BufferedReader br = null;
		BufferedReader br1 = null;
		String line = "";
		String cvsSplitBy = ",";
		int[][] matrix = null;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			br1 = new BufferedReader(new FileReader(csvFile));
			String line1 = br1.readLine();
			String in[] = line1.split(cvsSplitBy);
			int j = 0;
			length = in.length;
			matrix = new int[in.length][in.length];
			while ((line = br.readLine()) != null) {

				String[] input = line.split(cvsSplitBy);
				for (int i = 0; i < input.length; i++) {

					matrix[j][i] = Integer.parseInt(input[i]);
				}

				j++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br1 != null) {
				try {
					br1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return matrix;
	}
}
