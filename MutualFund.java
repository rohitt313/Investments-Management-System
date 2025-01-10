package ePortfolio;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;


public class MutualFund extends Investment{
    private String symbol="";
    private String name="";
    private String quantity;
    private String price;
    private double bookValue;
    private final int COMMISSION=45;

    /**
     * This method create a new MutualFund object
     * @param symbol String representing the symbol of that mutualfund 
     * @param name String representing the name of that mutualfund
     * @param quantity String representing the quantity of that mutualfund
     * @param price String representing the price of that mutualfund
     * @param bookValue String representing the bookValue of that mutualfund
     * @throws IllegalArgumentException when price or quantity does not have digits as characters or
     * if the digit is less than 1
     */ 
    
    MutualFund(String symbol,String name,String quantity,String price,double bookValue){    
        super(symbol,name,quantity,price);                     //Calling the super constructor
        bookValue=Integer.parseInt(quantity)*Double.parseDouble(price);
        this.bookValue=bookValue;
    }

    /**
     * This method creates a copy of a MutualFund object
     * @param copy Stock representing the MutualFund object to be copied 
     */ 

    public MutualFund(MutualFund copy){
        super(copy); 
        this.bookValue=copy.bookValue; 
    }

    /**
     * This method returns the contents of a specific mutualfund in a formatted manner
     * @return String,which is the formatted content of a specific mutualfund
     */
    @Override
    public String toString(){
        return super.toString()+"BookValue: "+String.format("%.2f",bookValue);
    }

     /**
     * This method returns the bookValue for a specific mutualfund
     * @return String,which is bookValue for a specific mutualfund
     */

    public double getBookValue(){
        return bookValue;
    }

    /**
     * This method updates the bookValue of a specific mutualfund
     * @param bookValue Double representing the bookValue of a specific mutualfund
     */

    public void setBookValue(double bookValue){
        this.bookValue=bookValue;
    }

    /**
     * This method returns the updated bookValue and quantity of the mutualfund whose shares are to be purchased
     * @param investmentFound Integer representing the index position of the mutualfund whose shares are to be purchased
     * @param quantity Integer representing the quantity of the mutualfund whose shares are to be purchased
     * @param price Double representing the price of the mutualfund whose shares are to be purchased
     * @param investments Arraylist representing all the investments
     * @return Double[],which is an array of double values where the elements are the updated bookValue
     * and quantity of a mutualfund
     */


    public static double[] buy(int investmentFound,String quantity,String price,ArrayList<Investment> investments){
        double[] retVal=new double[2];
        retVal=Investment.buy(investmentFound,Integer.parseInt(quantity),Double.parseDouble(price),investments);
        return retVal;

    }

    /**
     * This method returns the payment and gain of the mutualfund whose shares are to
     * be sold and that mutualfund should be removed 
     * @param investments Arraylist representing all the investments
     * @param quantity Integer representing the quantity of the mutualfund(to be removed) whose shares are to be sold
     * @param price Double representing the price of the mutualfund(to be removed) whose shares are to be sold
     * @return Double[],which is an array of double values where the elements are the payment and
     * gain of a mutualfund
     */
    @Override
    public double[] remove(ArrayList<Investment> investments,int quantity,double price){
        double retBookValue;
        double[] retVal=new double[2];
        double payment;

        payment=price*quantity-COMMISSION;  //Storing the existing bookValue of that investment
        retBookValue=payment-bookValueExists(Investment.symbolExists(this.getSymbol(),investments),investments);
        retVal[0]=payment;                 //Adding the payment and gain to array: retVal
        retVal[1]=retBookValue;
        
        return retVal;
    
    }


    /**
     * This method returns the payment,gain and the remaining bookValue of the mutualfund
     * whose shares are to be sold
     * @param quantity Integer representing the quantity of the mutualfund whose shares are to be sold
     * @param availQuantity Integer representing the existing quantity of the mutualfund whose shares are to be sold
     * @param price Double representing the price of the mutualfund whose shares are to be sold
     * @param investments Arraylist representing all the investments 
     * @return Double[],which is an array of double values where the elements are the payment,
     * gain and the remaining bookValue of an mutualfund
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

        payment=(price*quantity)-COMMISSION;       //We subtract 45
                                                  
        retBookValue=bookValueExists(Investment.symbolExists(this.getSymbol(),investments),investments);         //Storing the bookValue of that investment
        remainingQuantity=availQuantity-quantity;                                        
        bookValueRemaining=retBookValue*((double)remainingQuantity/availQuantity);       //Calculating the remaining bookValue
        bookValueSold=retBookValue-bookValueRemaining;        //Calculating how much of the original bookValue was sold
        gain=payment-bookValueSold;                           //Calculating the gain of the user
        retVal[0]=payment;               //Adding the payment, gain and the remaining bookValue to array: retVal
        retVal[1]=gain;
        retVal[2]=bookValueRemaining;
        
        return retVal;
    } 

    
 }