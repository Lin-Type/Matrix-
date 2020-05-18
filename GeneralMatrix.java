/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
 * are implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */


public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] data;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param m  The first dimension of the array.
     * @param n  The second dimension of the array.
     */
    public GeneralMatrix(int m, int n) throws MatrixException {
        //Extend the class from Matrix 
    	super(m,n); 
    	// Create condition for row and column in a matrix 
    	if(m<1 || n<1) {
    		 throw new MatrixException("The dimension of a Matrix cannot be less than 1");
    	}
    	// Set up number of array in data
    	data= new double [m][n];  
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the matrix A.
     *
     * @param A  The matrix to create a copy of.
     */
    public GeneralMatrix (GeneralMatrix A) {
        //Extend the class from Matrix 
    	super(A.m,A.n);
    	this.data= new double[A.m][A.n];
    	
    	//Loop by row of matrix A
    	for(int i=0; i<A.m;i++) {
    		//Loop by column of matrix A
    		
    		for(int j=0;j<A.n;j++) {
    		//Set up value of array[i][j]
    		data[i][j]=A.getIJ(i,j);
    		
    		}
    	}
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
       
    	//Create Condition to range value of i and j
    	if(i>=0 && i<this.m && j>=0 && j<this.n) {
    	return data[i][j];
        
    	}else throw new MatrixException("The element is not in Matrix");
   
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
    	
    	//Create Condition to range value of i and j
    	if(i>=0 && i<this.m && j>=0 && j<this.n) {
    	//Set up value of array[i][j]
        data[i][j]=val;
    	
    	}else  throw new MatrixException("Not inside Matrix");
    }
    	
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // Set up new array d and double det
    	double[] d= new double[1];    	
    	double det=1;    	
        //Call method decomp and format new matrix in the output format
    	GeneralMatrix LUMatrix=this.decomp(d);
    	
    	//Loop the matrix by row or column
    	for(int i=0; i<this.m;i++) {
    	//Record the new det value multiply by entries of diagonal 
    	det=det*LUMatrix.getIJ(i, i);
    	}
    	
    	//Check the sign of det value
    	det=d[0]*det;
    	return det;    	
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A) {
       /*Check the condition: the row and column 
    	for two matrix need to be equal respectively*/
    	if(this.m==A.m && this.n==A.n) {
    	//Create a new Matrix    
    	GeneralMatrix Row= new GeneralMatrix(this.m,this.n);
    	    
    	    //Loop the Matrix by row and column 
    	    for(int i=0;i<this.m;i++) {
    		    
    	    	for(int j=0; j<this.n;j++) {   
    		    /*Setting up the value for(i,j) of New matrix
    		    by addtion of respective value in two Matrix*/
    			Row.setIJ(i, j, this.getIJ(i,j)+A.getIJ(i, j));	
    		    }
    	    
    	    }return Row;
    	
    	}else  throw new MatrixException("Dimension not Equal, cannot add ");
   
    }
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
    	double sum=0;
    	
    	//Check the BA Multiplication work by compare column of b and row of a
    	if(this.n==A.m){
    	
       // Create new matirx in row of B times column of A	
    	GeneralMatrix Row= new GeneralMatrix(this.m,A.n);
    	    
    	    //Loop the method by row of B
    	    for(int i=0;i<this.m;i++) {
    	    	//Loop the method by Column of A
    		    for(int j=0; j<A.n;j++) {
    		    	
    		    	/*Total Number of Multiplication in each entries 
    		    	depend of column of B or row of A*/
    			    for(int k=0; k<this.n;k++) {
    			    
    			    // Set up the Value of (i,j)entries of new Matrix 
    				sum=sum+this.getIJ(i, k)*A.getIJ(k, j);
    				
    				//Putting this value 
    				Row.setIJ(i, j, sum);				
    			   
    			    }sum=0;//Set sum to 0 for next new loop 
    		    }
    	    
    	    }return Row;
    	
    	}else  throw new MatrixException(" Donot match law of matrix multiplication");
    
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public Matrix multiply(double a) {
        //Set up new Matrix base on this row and column  	
    	GeneralMatrix Row= new GeneralMatrix(this.m,this.n);
    	
    	//Loop by row and column 
    	for(int i=0;i<this.m;i++) {
    		
    		for( int j=0; j<this.n;j++) {
    		/*Multiply the (i,j)entries value in matrix by double a
    		and Putting new value back to (i,j)entries */
    		Row.setIJ(i, j,a*this.getIJ(i,j));    		
    		}
    	
    	}return Row;
    
    }


    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        //Loop by row and column 
    	for(int i=0;i<this.m;i++) {
    		
    		for(int j=0;j<this.n;j++) {
    		//Set up the (i,j)value of matrix 
    		this.setIJ(i, j, Math.random());
    		}
    	}
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     * 
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 l_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * d[0] calculated by the function.
     * 
     * If the matrix is singular, then the routine throws a MatrixException.
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     * 
     * @param d  An array of length 1. On exit, the value contained in here
     *           will either be 1 or -1, which you can use to calculate the
     *           correct sign on the determinant.
     * @return   The LU decomposition of the matrix.
     */
    public GeneralMatrix decomp(double[] d) {
        // This method is complete. You should not even attempt to change it!!
        if (n != m)
            throw new MatrixException("Matrix is not square");
        if (d.length != 1)
            throw new MatrixException("d should be of length 1");
        
        int           i, imax = -10, j, k; 
        double        big, dum, sum, temp;
        double[]      vv   = new double[n];
        GeneralMatrix a    = new GeneralMatrix(this);
        
        d[0] = 1.0;
        
        for (i = 1; i <= n; i++) {
            big = 0.0;
            for (j = 1; j <= n; j++)
                if ((temp = Math.abs(a.data[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }
        
        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= n; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a.data[imax-1][k-1];
                    a.data[imax-1][k-1] = a.data[j-1][k-1];
                    a.data[j-1][k-1] = dum;
                }
                d[0] = -d[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.data[j-1][j-1] == 0.0)
                a.data[j-1][j-1] = 1.0e-20;
            if (j != n) {
                dum = 1.0/a.data[j-1][j-1];
                for (i = j+1; i <= n; i++)
                    a.data[i-1][j-1] *= dum;
            }
        }
        
        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // You need to fill in this method.
     double num=2;
     GeneralMatrix a= new GeneralMatrix(2,2);
     a.setIJ(0, 0, 1.0);
     a.setIJ(0, 1, 2.0);
     a.setIJ(1, 0, 3.0);
     a.setIJ(1, 1, 4.0);
     GeneralMatrix c= new GeneralMatrix(2,2);
     c.setIJ(0,0,1.0);
     c.setIJ(0,1,1.0);
     c.setIJ(1,0,1.0);
     c.setIJ(1,1,1.0);
     System.out.println("The (1,0)position value of a: "+a.getIJ(1, 0));
     System.out.println( "Matrix A: \n"+a.toString());  
     System.out.println("Matrix c:\n"+c.toString());
     System.out.println("The formation of CA:\n" +c.multiply(a));
     System.out.println("Matrix addtion between c and a:\n"+ c.add(a));
     System.out.println("Matrix Multiplication by constant number:\n"+a.multiply(num));
     GeneralMatrix b= new GeneralMatrix(2,2);
     b.random();
     System.out.println( "Matrix B: \n"+b.toString());  
     System.out.println("The determinant of a: "+ a.determinant());
    }
}
