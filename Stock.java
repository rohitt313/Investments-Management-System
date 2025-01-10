package ePortfolio;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;


public class Stock extends Investment{
    private String symbol="";
    private String name="";
    private String quantity;
    private double bookValue;
    private String price;
    private final double COMMISSION=9.99;

    /**
     * This method create a new Stock object
     * @param symbol String representing the symbol of that stock 
     * @param name String representing the name of that stock
     * @param quantity String representing the quantity of that stock
     * @param price String representing the price of that stock
     * @param bookValue String representing the bookValue of that stock
     * @throws IllegalArgumentException when price or quantity does not have digits as characters or
     * if the digit is less than 1
     */    

    Stock(String symbol,String name,String quantity,String price,double bookValue){   
        super(symbol,name,quantity,price);            //Calling the super constructor
        bookValue=Integer.parseInt(quantity)*Double.parseDouble(price);
        this.bookValue=bookValue+COMMISSION;
    }

    /**
     * This method creates a copy of a Stock object
     * @param copy Stock representing the Stock object to be copied 
     */ 

    public Stock(Stock copy){
        super(copy);                   
        this.bookValue=copy.bookValue; 
    }

    
    /**
     * This method returns the contents of a specific stock in a formatted manner
     * @return String,which is the formatted content of a specific stock
     */
    @Override
    public String toString(){
        return super.toString()+"BookValue: "+String.format("%.2f",bookValue);
    }

    /**
     * This method returns the bookValue for a specific stock
     * @return String,which is bookValue for a specific stock
     */

    public double getBookValue(){
        return bookValue;
    }

    /**
     * This method updates the bookValue of a specific stock
     * @param bookValue Double representing the bookValue of a specific stock
     */

    public void setBookValue(double bookValue){
        this.bookValue=bookValue;
    }


    /**
     * This method returns the updated bookValue and quantity of the stock whose shares are to be purchased
     * @param investmentFound Integer representing the index position of the stock whose shares are to be purchased
     * @param quantity Integer representing the quantity of the stock whose shares are to be purchased
     * @param price Double representing the price of the stock whose shares are to be purchased
     * @param investments Arraylist representing all the investments
     * @return Double[],which is an array of double values where the elements are the updated bookValue
     * and quantity of a stock
     */

    public static double[] buy(int investmentFound,String quantity,String price,ArrayList<Investment> investments){
        final double COMMISSION=9.99;
        double[] retVal=new double[2];
        retVal=Investment.buy(investmentFound,Integer.parseInt(quantity),Double.parseDouble(price),investments);    //Getting the updated bookValue and quantity
        retVal[0]=retVal[0]+COMMISSION;                                      //Adding commission to bookValue
        return retVal;
    }


    /**
     * This method returns the payment and gain of the stock whose shares are to
     * be sold and that stock should be removed 
     * @param investments Arraylist representing all the investments
     * @param quantity Integer representing the quantity of the stock(to be removed) whose shares are to be sold
     * @param price Double representing the price of the stock(to be removed) whose shares are to be sold
     * @return Double[],which is an array of double values where the elements are the payment and
     * gain of a stock
     */
    @Override
    public double[] remove(ArrayList<Investment> investments,int quantity,double price){
        double retBookValue;
        double[] retVal=new double[2];
        double payment;

        payment=price*quantity-COMMISSION;  //Storing the existing bookValue of that investment
        retBookValue=payment-bookValueExists(Investment.symbolExists(this.getSymbol(),investments),investments);   //retBookValue=Payment-retBookValue
        retVal[0]=payment;                  //Adding the payment and gain to array: retVal
        retVal[1]=retBookValue;
        
        return retVal;
    
    }

    /**
     * This method returns the payment,gain and the remaining bookValue of the stock
     * whose shares are to be sold
     * @param quantity Integer representing the quantity of the stock whose shares are to be sold
     * @param availQuantity Integer representing the existing quantity of the stock whose shares are to be sold
     * @param price Double representing the price of the stock whose shares are to be sold
     * @param investments Arraylist representing all the investments 
     * @return Double[],which is an array of double values where the elements are the payment,
     * gain and the remaining bookValue of a stock
     */
    @Override
    public double[] sell(int quantity,int availQuantity,double price,ArrayList<Investment> investments){
        double payment=0;
        double gain;
        double retBookValue;
        int remainingQuantity;
        double bookValueRemaining;
        double bookValueSold;
        double[] retVal=new double[3];

        payment=(price*quantity)-COMMISSION;            //We subtract 9.99
                                                  
        retBookValue=bookValueExists(Investment.symbolExists(this.getSymbol(),investments),investments);         //Storing the bookValue of that investment
        remainingQuantity=availQuantity-quantity;                                        
        bookValueRemaining=retBookValue*((double)remainingQuantity/availQuantity);       //Calculating the remaining bookValue
        bookValueSold=retBookValue-bookValueRemaining;                                   //Calculating how much of the original bookValue was sold
        gain=payment-bookValueSold;                                                      //Calculating the gain of the user
        retVal[0]=payment;                                                   //Adding the payment, gain and the remaining bookValue to array: retVal
        retVal[1]=gain;
        retVal[2]=bookValueRemaining;
        
        return retVal;
    }     
}

