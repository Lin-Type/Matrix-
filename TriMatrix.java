/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diag;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upper;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lower;
    
    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param N  The dimension of the array.
     */
    public TriMatrix(int N) {
        // Extend from Matrix 
    	super(N,N);
    	
    	//Check condition for matrix 
    	if(N>1) {
       
    		//Formating length of array 
    	diag=new double[N];
   	    upper= new double[N-1];
   	    lower= new double[N-1];
   	    
    	}else throw new MatrixException("Incorrect format of MAtrix");

    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
       //Condition the Matrix
       if(i>=0 && i<this.n && j>=0 && j<this.n) {
    	   //Check condition for diag, lower and upper respectively
    	   if(i==j) {
    	   return diag[i]; 
    	   }
    	   
    	   else if(i==j-1) {
    	   return upper[i];
    	   }
    	   
    	   else if(j==i-1) {
    	   return lower[j];
    	   }
    	   
    	   else return 0;
       //throw out incorrect value    
       }else  throw new MatrixException("Not fit into Matrix");
    	    
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
        //Condition the matrix 
    	if(i>=0 && i<this.n && j>=0 && j<this.n) {
    		//Puting value by condition 
    		if(i==j) {
    			diag[i]=val;
    		}
    		else if(i==j-1) {
    			upper[i]=val;
    		}
    		else if(j==i-1) {
    			lower[j]=val;
    		}
    		else val=0;
    	}else throw new MatrixException("Not fit in Matirx");
   
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        
    	double det=1;
        TriMatrix LU=this.decomp();
        
        //Diag in LU decomposition 
        for(int i=0;i<this.n;i++) {
        det=det*LU.getIJ(i, i);
        }
        
        return det;
    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix decomp() {
        // Create new Matrix 
    	TriMatrix LU= new TriMatrix(this.n);
        LU.setIJ(0, 0, getIJ(0,0));
        
        //Puting value for upper form 
        for(int u=0;u<this.n-1;u++) {
        LU.setIJ(u, u+1, getIJ(u,u+1));
        }
    	/*BY Matrix Calculating new Matrix by L and U, in the lower position 
    	 * new Matrix a(i+1,1)=lower(i+1,i)*diag(i,i) 
    	 * and the a(i,i)= lower(i+1,1)*upper(i,i+1)+diag(i,i)
    	 */
        for(int d=0;d<this.n-1;d++) {
        LU.setIJ(d+1,d,this.getIJ(d+1, d)/LU.getIJ(d,d));
        LU.setIJ(d+1,d+1,this.getIJ(d+1,d+1)-LU.getIJ(d+1,d)*LU.getIJ(d,d+1));
        }
        return LU;
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A){
        //Categorize the type of Matrix 
    	if(A instanceof GeneralMatrix) {
        	return this.add(A);
        }else if(A instanceof TriMatrix) {
        	if(this.n==A.n && this.n==A.m) {
        	TriMatrix Row= new TriMatrix(n);
        	//Putting value of diagonal
        	    for(int i=0;i<this.n;i++) {
        	 	Row.setIJ(i, i, this.getIJ(i, i)+A.getIJ(i, i));
        	    }
        	//putting value for lower and upper 
        	    for(int j=0;j<this.n-1;j++) {
        		Row.setIJ(j,j+1,this.getIJ(j, j+1)+A.getIJ(j, j+1));
        		Row.setIJ(j+1,j,this.getIJ(j+1, j)+A.getIJ(j+1, j));        		
        	    }        	    
        	    return Row;
        	
        	}else throw new MatrixException("Cannot Make Additon of Matirx");
        
        	//Throw out the wrong Matrix
        }else throw new MatrixException("Not the correct Type of Matrix");
       
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
       // Create new matrix in row of B times column of A	
    	GeneralMatrix Row= new GeneralMatrix(this.m,A.n);
    	    
    	    //Loop the method by row of B
    	    for(int i=0;i<this.n;i++) {
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
        // create a new matrix 
    	TriMatrix returnMatrix= new TriMatrix(n);
    	
    	//Make a loop to set up value 
    	for(int i=0;i<this.n;i++) {
    		returnMatrix.setIJ(i,i,getIJ(i,i)*a);
    	}
    	//Putting value for lower and upper 
    	for(int j=0;j<this.n-1;j++) {
    		returnMatrix.setIJ(j, j+1,getIJ(j,j+1)*a);
    		returnMatrix.setIJ(j+1, j, getIJ(j+1,j)*a);    
    	}
    	return returnMatrix;
   
    }
    
    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        //Putting value to diag,upper,lower
    	for(int i=0; i<this.n;i++) {
        this.setIJ(i,i,Math.random());
        }
    	
    	for(int i=0;i<this.n-1;i++) {
    	//Putting value for upper
        this.setIJ(i, i+1, Math.random());
        //Putting value for lower 
    	this.setIJ(i+1, i, Math.random());
    	
    	}
    }
    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        TriMatrix A= new TriMatrix(3);
        A.setIJ(0,0,3);
        A.setIJ(0,1,1);
        A.setIJ(1,0,1);
        A.setIJ(1,1,5);
        A.setIJ(1,2,3);
        A.setIJ(2,1,3);
        A.setIJ(2,2,4);
        TriMatrix B= new TriMatrix(3);
        B.setIJ(0,0,1);
        B.setIJ(0,1,2);
        B.setIJ(1,0,3);
        B.setIJ(1,1,1);
        B.setIJ(1,2,2);
        B.setIJ(2,1,3);
        B.setIJ(2,2,1);
        TriMatrix C= new TriMatrix(2);
        C.setIJ(0, 0, 1);
        C.setIJ(0, 1, 2);
        C.setIJ(1, 0, 1);
        C.setIJ(1, 1, 1);
        TriMatrix D= new TriMatrix(3);
        D.random();
        System.out.println("The Format of Matrix A\n"+A.toString());
        System.out.println("The Format of Matrix B\n"+B.toString());
        System.out.println("The diagnoal of Matrix A= "+A.diag[0]+" and "+A.diag[1]);
        System.out.println("The Matrix Multiplication BA \n"+B.multiply(A));
        System.out.println("The Matirx Addition of A and B \n"+ A.add(B));
        System.out.println("The Matirx multiply by constanct number of A \n"+A.multiply(2));
        System.out.println("The det of Matrix C= "+ C.determinant());
        System.out.println("The det of Matrix B= "+ B.determinant());
        System.out.println("The LU composition of B\n "+ B.decomp() );
        System.out.println("The Format of Matrix D\n"+D.toString());
    }
}
