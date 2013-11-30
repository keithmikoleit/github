
public class Percolation {
	
		// Data
		int numRowsCols; // Stores number of rows and columns
		boolean grid[][];
		int fill_component;
		int last_component;
		WeightedQuickUnionUF PercUF;
		WeightedQuickUnionUF FilledUF;
		
		// constants
		public static final boolean BLOCKED = false;
		public static final boolean OPEN = true;
	
		// create N-by-N grid, with all sites blocked
	   	public Percolation(int N){
	   		
	   		// create a NxN array stored in grid
	   		// a 0 means the site is blocked
	   		// a 1 means the site is open
	   		// a 2 means the site is full
	   		this.grid = new boolean[N][N];
	   		// this UF is for checking if the system percolates
	   		this.PercUF = new WeightedQuickUnionUF(N*N + 2);
	   		// this union find is for the graphical representation
	   		// and is used to eliminate the back wash issue
	   		this.FilledUF = new WeightedQuickUnionUF(N*N + 1);
	   		this.numRowsCols = N;
	   		
	   		// create a "virtual" node at N ^2
	   		// This will be where squares "fill" from
	   		// calculate UF value for fill node
	   		fill_component = N*N;
	   		// create a "virtual" node at N^2  + 1
	   		// once this node is filled the system has percolated
	   		// connect all of the final row to this node
	   		last_component = N*N + 1;
	   	}
	   	
	 // open site (row i, column j) if it is not already
	   	public void open(int i, int j) {
	   		// validate indices
	   		if(this.indiciesAreValid(i, j)){
	   			// mark the site as open
		   		this.grid[i - 1][j - 1] = OPEN;
		   		// check if this site is in first or last row
		   		// if so we link it to one of the virtual nodes
		   		if(i == 1){
		   			PercUF.union(this.fill_component, this.xyTo1D(i, j));
		   			FilledUF.union(this.fill_component, this.xyTo1D(i, j));
		   		}
		   		
		   		if(i == this.numRowsCols){
		   			PercUF.union(this.last_component, this.xyTo1D(i, j));
		   		}
		   		
		   		// link site to open neighbors
		   		// check i + 1
		   		if((i != this.numRowsCols) && this.isOpen(i + 1, j)){
		   			PercUF.union(this.xyTo1D(i + 1, j), this.xyTo1D(i, j));
		   			FilledUF.union(this.xyTo1D(i + 1, j), this.xyTo1D(i, j));
		   		}
		   		// check i - 1
		   		if((i != 1) && this.isOpen(i - 1, j)){
		   			PercUF.union(this.xyTo1D(i - 1, j), this.xyTo1D(i, j));
		   			FilledUF.union(this.xyTo1D(i - 1, j), this.xyTo1D(i, j));
		   		}
		   		// check j + 1
		   		if((j != this.numRowsCols) && this.isOpen(i, j + 1)){
		   			PercUF.union(this.xyTo1D(i, j + 1), this.xyTo1D(i, j));
		   			FilledUF.union(this.xyTo1D(i, j + 1), this.xyTo1D(i, j));
		   		}
		   		// check j - 1
		   		if((j != 1) && this.isOpen(i, j - 1)){
		   			PercUF.union(this.xyTo1D(i, j - 1), this.xyTo1D(i, j));
		   			FilledUF.union(this.xyTo1D(i, j - 1), this.xyTo1D(i, j));
		   		}
	   		}	
	   	}
	   	
	 // is site (row i, column j) open?
	   	public boolean isOpen(int i, int j){
	   		// verify the indices
	   		if(this.indiciesAreValid(i, j)){
	   	 		// check if the square is open
	   			if(grid[i - 1][j - 1] == OPEN){
		   			return true;
		   		}else{
		   			return false;
		   		}
	   		} else {
	   			return false;
	   		}
	   	}
	   	
	 // is site (row i, column j) full?
	   	public boolean isFull(int i, int j){
	   		// verify the indices
	   		if(this.indiciesAreValid(i, j)){
	   	 		// check if the square is connected to the fill square
	   			if(FilledUF.connected(this.xyTo1D(i, j), this.fill_component) && this.isOpen(i, j)){
		   			return true;
		   		}else{
		   			return false;
		   		}
	   		} else {
	   			return false;
	   		}
	   	}
	   	
	 // does the system percolate?
	   	public boolean percolates(){
	   		// check to see if the virtual fill node
	   		// is connected to the virtual last node
	   		return PercUF.connected(fill_component, last_component);
	   	}
	   	
	   	private int xyTo1D(int x, int y){
	   		return (x - 1) * this.numRowsCols + (y - 1);
	   	}

	   	private boolean indiciesAreValid(int i, int j){
	   		if((i <= 0) || (i > this.numRowsCols)){
	   			throw new java.lang.IndexOutOfBoundsException("Row index " + Integer.toString(i) + " is out of bounds.");
	   		} else if ((j <= 0) || (j > this.numRowsCols)){
	   			throw new java.lang.IndexOutOfBoundsException("Row index " + Integer.toString(j) + " is out of bounds.");
	   		} else {
	   			return true;
	   		}
	   	}
	   	
	   	public static void main(String[] args){	   		
	   		Percolation perc2 = new Percolation(2);
	   		int x,y;
	   		
	   		while(!perc2.percolates()){
	   			x = StdRandom.uniform(1, perc2.numRowsCols + 1);
	   			y = StdRandom.uniform(1, perc2.numRowsCols + 1);
	   			
	   			perc2.open(x, y);
	   		}
	   		
	   		StdOut.println("Number of components at percolation: " + Integer.toString(perc2.PercUF.count()));
	   	}
}
