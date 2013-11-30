public class PercolationStats
{
    // ...
    // your data members here
    // ...
	int percentageOpen[];

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {	
    	this.percentageOpen = new int[T];
    	
    	for(int i = 0; i < T; i++){
    		Percolation perc = new Percolation(N);
    		int x,y;
    		// open a new random site until
    		// the grid percolates
    		while(!perc.percolates()){
    			// select a random square
	   			x = StdRandom.uniform(1, perc.numRowsCols + 1);
	   			y = StdRandom.uniform(1, perc.numRowsCols + 1);
	   			// open the square
	   			perc.open(x, y);
    		}
    		// look for the number of open squares vs blocked squares
    		int open = 0;
    		for(int j = 1; j <= N; j++){
    			for(int k = 1; k <= N; k++){
    				if(perc.isOpen(j, k)){
    					open++;
    				}
    			}
    		}
    		percentageOpen[i] = open/N;
    	}
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(this.percentageOpen);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(this.percentageOpen);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        return this.mean() - 2 * this.stddev();
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        return this.mean() + 2 * this.stddev();
    }
    
	public static void main(String[] args){
		PercolationStats percStats = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
		
		StdOut.println("mean                    " + Double.toString(percStats.mean()));
		StdOut.println("stddev                  " + Double.toString(percStats.stddev()));
		StdOut.println("95% confidence interval " + Double.toString(percStats.confidenceLo()) + ", " + Double.toString(percStats.confidenceHi()));
	}
}
