public class SparseMatrix implements SparseInterface {

	//Matrix contains variables to keep track of # of rows and # of columns. Also contains rowNode called head which keeps track of the first row.
	int numRows;
	int numCols;
	rowNode head;


	public SparseMatrix () {
		//Create and empty matrix
		head = new rowNode();
		numRows = 0;
		numCols = 0;
	}

	//SparseMatrix Methods
	public void clear() {
		head = new rowNode();
	}

	public void setSize(int numRows, int numCols) {
		//Changes value of numRows and numCols to user input
		this.numRows = numRows;
		this.numCols = numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void addElement(int row, int col, int data) {

		//If row or column entered is greater than size of matrix, do not add to matrix
		if(row > numRows || col > numCols) {
			System.out.println("Row or column are out of bounds!");
			return;
		}

		//Create node to keep track of the current row while iterating
		rowNode currentRow = head.nextRow;
		//Create a node to keep track of the previous row while iterating
		rowNode previousRow = null;
		//Create a node to keep track of the previous column while iterating
		colNode previousCol = null;
		//Create a boolean to check if element has been succesfully added
		boolean elementAdded = false;


		//If entry already exists in matrix but the element to be added is zero, remove the element
		if(getElement(row, col) != 0 && data == 0) {
			//Iterate through matrix while there are still rows
			while(!elementAdded && currentRow != null) {
				if(currentRow.row == row) {
					//Once row is found, iterate to find column entry
					colNode currentCol = currentRow.nextCol;
					while(!elementAdded && currentCol != null) {
						//Once column entry is found, set the previous nodes next column to the current columns next column.
						if(currentCol.col == col) {
							previousCol.nextCol = currentCol.nextCol;
							elementAdded = true;
							break;
						}
						previousCol = currentCol;
						currentCol = currentCol.nextCol;
					}
				}
				currentRow = currentRow.nextRow;
			}
		}
		//If there is currently no row entries in matrix
		if(currentRow == null) {
			head.nextRow = new rowNode(row);
			head.nextRow.nextCol = new colNode(col, data);
			return;
		}

		while(!elementAdded) {
			//Creates new row if there is only one row in matrix and row to be added is greater than the row currently in matrix. 
			if(currentRow.row > row && currentRow.nextRow == null && previousRow == null) {

				rowNode temp = new rowNode(row);
				temp.nextCol = new colNode(col, data);
				temp.nextRow = currentRow;
				head.nextRow = temp;
				elementAdded = true;
				break;
			}
			//Creates new row if row does not exist
			else if(currentRow.row != row && currentRow.nextRow == null) {
				currentRow.nextRow = new rowNode(row);
				currentRow.nextRow.nextCol = new colNode(col, data); 
				elementAdded = true;
				break;
			}
			//Creates new row if row does not exist
			else if(currentRow.row != row && currentRow.nextRow == null) {
				currentRow.nextRow = new rowNode(row);
				currentRow.nextRow.nextCol = new colNode(col, data); 
				elementAdded = true;
				break;
			}
			//Creates new row if row is between 2 current existing rows
			else if(currentRow.row < row && currentRow.nextRow.row > row) {
				rowNode temp = new rowNode(row);
				temp.nextCol = new colNode(col, data);
				temp.nextRow = currentRow.nextRow;
				currentRow.nextRow = temp;
				elementAdded = true;
				break;

			}
			//If row entry already exists in matrix
			else if(currentRow.row == row) {

				colNode currentCol = currentRow.nextCol;
				previousCol = currentCol;

				while(currentCol != null) {

					//If column entered already exists, updates data
					if(currentCol.col == col && data != 0) {
						currentCol.data = data;
						elementAdded = true;
						break;
					}

					//If column entered already exists, updates data
					if(currentCol.col == col && data == 0) {
						previousCol.nextCol = currentCol.nextCol;
						elementAdded = true;
						break;
					}
					//If column entry is not in row, creates new column entry 
					else if(currentCol.col != col && currentCol.nextCol == null) {	
						currentCol.nextCol = new colNode(col, data);
						elementAdded = true;
						break;
					}
					//If column does not exist and is between two columns
					else if(previousCol.col < col && currentCol.nextCol.col > col) {
						colNode temp = new colNode(col, data);
						temp.nextCol = currentCol.nextCol;
						currentCol.nextCol = temp;
						elementAdded = true;
						break;
					}
					//Iterates to next column entry
					else if(currentCol.col < col) {
						previousCol = currentCol;
						currentCol = currentCol.nextCol;
					}
				}
			}
			//Iterates to next row
			else if(currentRow.row < row) {
				previousRow = currentRow;
				currentRow = currentRow.nextRow;
			}
		}
	}

	public void removeElement(int row, int col) {

		//If row or column entered is greater than size of matrix, do not add to matrix
		if(row > numRows || col > numCols) {
			System.out.println("Row or column are out of bounds!");
			return;
		}

		//Create node to keep track of the current row while iterating
		rowNode currentRow = head.nextRow;
		//Create a node to keep track of the previous row while iterating
		rowNode previousRow = null;
		//Create a node to keep track of the previous column while iterating
		colNode previousCol = null;
		//Create a boolean to check if element has been succesfully removed
		boolean elementRemoved = false;

		//Iterate through matrix
		while(!elementRemoved) {
			//Once row is found, iterate through column entries
			if(currentRow.row == row) {
				//Create node to keep track of current column entry
				colNode currentCol = currentRow.nextCol;

				while(!elementRemoved) {

					//If there are no column entries in row
					if(currentCol == null) {
						return;
					}

					//If last entry in row is removed and there are previous rows
					else if(currentCol.col == col && previousCol == null && currentCol.nextCol == null && previousRow != null) {
						previousRow.nextRow = currentRow.nextRow;
						elementRemoved = true;
						break;
					}
					//If last entry in row is removed and it is the first row
					else if(currentCol.col == col && previousCol == null && currentCol.nextCol == null && previousRow == null) {
						head.nextRow = currentRow.nextRow;					
						elementRemoved = true;
						break;
					}

					//If column entry is the last in list
					else if(currentCol.col == col && currentCol.nextCol == null && previousCol != null) {

						previousCol.nextCol = currentCol.nextCol;
						elementRemoved = true;
						break;
					}
					//If column entry is first in list
					else if(currentCol.col == col && previousCol == null) {
						currentRow.nextCol = currentCol.nextCol;
						elementRemoved = true;
						break;
					}
					//If column entry is between two column entries
					else if(currentCol.col == col && previousCol != null && currentCol.nextCol != null) {
						previousCol.nextCol = currentCol.nextCol;
						elementRemoved = true;
						break;
					}


					//Iterate to next column
					else if(currentCol.col < col) {
						previousCol = currentCol;
						currentCol = currentCol.nextCol;
					}
				}
			}
			//Iterate to next row;
			else if(currentRow.row < row) {
				previousRow = currentRow;
				currentRow = currentRow.nextRow;
			}
		}
	}

	public int getElement(int row, int col) {

		//If row or column entered is greater than size of matrix, return
		if(row > numRows || col > numCols) {
			System.out.println("Row or column are out of bounds!");
			return 0;
		}
		//Variable to keep track of number to return
		int number = 0;
		//Boolean to keep track of valid element return
		boolean elementReturned = false;
		//Node to keep track of current row, set to first element
		rowNode currentRow = new rowNode(head.nextRow);


		while(!elementReturned) {
			//If element is not stored in matrix, return 0
			if(currentRow.nextCol == null) {
				number = 0;
				elementReturned = true;
				break;
			}

			else if(currentRow != null && currentRow.row == row) {
				//When row is found, iterate through column entries 

				//Node to keep track of current column
				colNode currentCol = currentRow.nextCol;

				while(!elementReturned && currentCol != null) {
				
					//If entry is not found in matrix, return 0
					if(currentCol.col != col && currentCol.nextCol == null) {
						number = 0;
						elementReturned = true;
						break;
					}
					//If entry exists in matrix, return that value 
					if(currentCol.col == col) {
						number = currentCol.data;
						elementReturned = true;
						break;
					}
					//Iterate to next column entry
					currentCol = currentCol.nextCol;
				}
			}
			//Iterate ot next row
			else if(currentRow.nextRow != null && currentRow.row < row) {
				currentRow = currentRow.nextRow;
			}
			//If element row is not found return 0
			else if(currentRow.row != row && currentRow.nextRow == null) {
				number = 0;
				elementReturned = true;
				break;
			}
			//If element row is not found return 0
			else if(currentRow.row > row) {
				number = 0;
				elementReturned = true;
				break;
			}
		
	 	}
		return number;
	}

	public String toString() {
		//Create empty string
		String x = "";
		//Iterate through all rows
		for(int i = 0; i <= numRows; i++) {
			//Iterate through all column entries
			for(int j = 0; j <= numCols; j++) { 
				//Checks if element is not 0
				if(getElement(i, j) != 0 ) {
					//Gets element and appends it to string with row number and column number
					int n = getElement(i, j);
					x = x + (String.valueOf(i) + " " + String.valueOf(j) + " " + String.valueOf(n) + "\n");
				}  
			}
		}
		return x;
	}

	public SparseInterface addMatrices(SparseInterface matrixToAdd) {

		//If matrices to not match dimensions, return null
		if(numRows != matrixToAdd.getNumRows() || numCols != matrixToAdd.getNumCols()) {
			return null;
		}
		//Create matrix to return
		SparseInterface sumMatrix = new SparseMatrix();
		//Set matrix dimensions
		sumMatrix.setSize(numRows, numCols);

		//Iterate through rows of the matrix
		for(int i = 0; i <= numRows; i++) {
			//Iterate through columns of matrix
			for(int j = 0; j <= numCols; j++) {
				//If element exists in both matrices, add them both together then add element to new matrix
				if(getElement(i, j) != 0 && matrixToAdd.getElement(i, j) != 0) {
					sumMatrix.addElement(i, j, getElement(i,j) + matrixToAdd.getElement(i, j));
				} 
				//If element in one matrix exists but not in other, add existing element to the new matrix
				else if(getElement(i, j) != 0 && matrixToAdd.getElement(i, j) == 0) {
					sumMatrix.addElement(i, j, getElement(i, j));
				}
				//If element in one matrix exists but not in other, add existing element to the new matrix
				else if(getElement(i, j) == 0 && matrixToAdd.getElement(i, j) != 0) {
					sumMatrix.addElement(i, j, matrixToAdd.getElement(i, j));
				}
			}
		}
		return sumMatrix;
	}

	public SparseInterface multiplyMatrices(SparseInterface matrixToMultiply) {

		//If matrix dimensions are not compaitable, return null
		if(numCols != matrixToMultiply.getNumRows()) {
			return null;
		}
		//Create matrix to return
		SparseInterface productMatrix = new SparseMatrix();
		//Set dimensions of new matrix
		productMatrix.setSize(numRows, matrixToMultiply.getNumCols());

		//Creates variable to store the multiplication of each entry
		int product = 0;
		//Iterate through rows of new matrix
		for(int i = 0; i <= productMatrix.getNumRows(); i++) {
			//Iterate through columns of each matrix
			for(int j = 0; j <= productMatrix.getNumCols(); j++) {
				//Iterate through number of columns of the first matrix
				for(int k = 0; k <= numCols; k++) {
					//Add the product of each element for each column and row of matrices to be multiplied.
					product = product + (getElement(i, k) * matrixToMultiply.getElement(k, j));
				}
				//Add product to matrix
				productMatrix.addElement(i, j, product);
				//Set product to 0 to repeat for next entry
				product = 0;
			}
		}
		return productMatrix;
	}

	//Classes
	private class rowNode {
		//Row number
		int row;
		//Next row in matrix
		rowNode nextRow;
		//Next column entry in row
		colNode nextCol;

		//Sets empty row node
		public rowNode() {
			nextRow = null;
			nextCol = null;
		}

		//Creates row node with row number provided
		public rowNode(int row) {
			this.row = row;
			nextRow = null;
			nextCol = null;
		}

		//Copy constructor to copy existing row node
		public rowNode(rowNode copy) {
			
			if(copy != null) {
				this.nextRow = copy.nextRow;
				this.row = copy.row;
				this.nextCol = copy.nextCol;
			} else {
				this.nextRow = null;
			}


		}
	}

	private class colNode {
		//Data entry
		int data;
		//Column number
		int col;
		//Next column entry in row
		colNode nextCol;

		//Creates empty column node
		public colNode() {
			nextCol = null;
		}
		//Creates column node with column number and data entry provided
		public colNode(int col, int data) {
			this.col = col;
			this.data = data;
			nextCol = null;
		}
		//Copy constructor to copy existing column node
		public colNode(colNode copy) {
			this.col = copy.col;
			this.data = copy.data;
			this.nextCol = copy.nextCol;
		}
	}

}



