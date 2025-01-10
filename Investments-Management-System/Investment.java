package ePortfolio;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public abstract class Investment{
    private String symbol="";
    private String name="";
    private String quantity="";
    private String price="";
    private double bookValue;

    /**
     * This method returns the payment and gain of the investment whose shares are to
     * be sold and that investment should be removed 
     * @param investments Arraylist representing all the investments
     * @param quantity Integer representing the quantity of the investment(to be removed) whose shares are to be sold
     * @param price Double representing the price of the investment(to be removed) whose shares are to be sold
     * @return Double[],which is an array of double values where the elements are the payment and
     * gain of an investment
     */
    public abstract double[] remove(ArrayList<Investment> investments,int quantity,double price);

    /**
     * This method returns the payment,gain and the remaining bookValue of the investment
     * whose shares are to be sold
     * @param quantity Integer representing the quantity of the investment whose shares are to be sold
     * @param availQuantity Integer representing the existing quantity of the investment whose shares are to be sold
     * @param price Double representing the price of the investment whose shares are to be sold
     * @param investments Arraylist representing all the investments 
     * @return Double[],which is an array of double values where the elements are the payment,
     * gain and the remaining bookValue of an investment
     */
    public abstract double[] sell(int quantity,int availQuantity,double price,ArrayList<Investment> investments);
        

    
    /**
     * This method create a new Investment object
     * @param symbol String representing the symbol of that investment 
     * @param name String representing the name of that investment
     * @param quantity String representing the quantity of that investment
     * @param price String representing the price of that investment
     * @throws IllegalArgumentException when price or quantity does not have digits as characters or
     * if the digit is less than 1
     */ 
    Investment(String symbol,String name,String quantity,String price){    
        int numQuantity;
        double numPrice;
        
        this.symbol=symbol;
        this.name=name;
        try{
            numQuantity=Integer.parseInt(quantity);
            if(numQuantity<1) {                    
                throw new IllegalArgumentException("Please enter a positive value for the quantity field");
            }
            this.quantity = quantity;    //If quantity consists of valid digits greater than 1
        
        }
        catch(NumberFormatException e){      //If a string of characters was entered as quantity
            throw new IllegalArgumentException("You did not enter valid digits for the quantity field");
        }

        try{
            numPrice=Double.parseDouble(price);
            if(numPrice<1){
                throw new IllegalArgumentException("Please enter a positive value for the price field");
            }
            this.price=price;             //If price consists of valid digits greater than 1
        } 
        catch(NumberFormatException e){      //If a string of characters was entered as price
            throw new IllegalArgumentException("You did not enter valid digits for the price field");
        }
    }

    /**
     * This method creates a copy of an Investment object
     * @param copy Stock representing the Investment object to be copied 
     */ 

    public Investment(Investment copy){          
        this.symbol=copy.symbol;
        this.name=copy.name;
        this.quantity=copy.quantity;
        this.price=copy.price;
    }
    
    /**
     * This method compares the passed Investment object with another and returns a boolean value based on equality
     * @param other Object representing the object to be compared with the investment.
     * @return boolean if the passed object is equal to the investment, it returns true otherwise returns false 
     */

    public boolean equals(Object other){
        if(other==null){
            return false;
        }
        else if(getClass()!=other.getClass()){
            return false;
        }
        
        else{
            Investment otherInvestment=(Investment)other;    //Typecasting other(Object type) to Investment 
            return(symbol.equals(otherInvestment.symbol) && name.equals(otherInvestment.name) &&
            price.equals(otherInvestment.price) && quantity.equals(otherInvestment.quantity) &&
            getBookValue()==otherInvestment.getBookValue());    //Using getBookValue() to fetch the bookValue 
        }
    }

    /**
     * This method returns the symbol for a specific investment
     * @return String,which is symbol for a specific investment
     */

    public String getSymbol(){
        return symbol;
    }

    /**
     * This method returns the name for a specific investment
     * @return String,which is name for a specific investment
     */

    public String getName(){
        return name;
    }

    /**
     * This method returns the quantity for a specific investment
     * @return String,which is quantity for a specific investment
     */

    public String getQuantity(){
        return quantity;
    }

    /**
     * This method returns the price for a specific investment
     * @return String,which is price for a specific investment
     */

    public String getPrice(){
        return price;
    }

    /**
     * This method returns the bookValue for a specific investment
     * @return String,which is bookValue for a specific investment
     */

    public double getBookValue(){
        if(this instanceof Stock){                            //If this investment is an instance of Stock
            bookValue=((Stock)this).getBookValue();           //Typecasting to use Stock methods
        }
        else if(this instanceof MutualFund){                  //If this investment is an instance of MutualFund
            bookValue=((MutualFund)this).getBookValue();      //Typecasting to use MutualFund methods
        }
        return bookValue;
    }

    /**
     * This method updates the price of a specific investment
     * @param price String representing the quantity of a specific investment
     * @throws IllegalArgumentException when price does not have digits as characters or
     * if the digit is less than 1
     */

    public void setPrice(String price){   
        double numPrice;

        try{
            numPrice=Double.parseDouble(price);
            if(numPrice<1){            
                throw new IllegalArgumentException("Please enter a positive value for the price field");
            }
            this.price=price;          //If price consists of valid digits greater than 1
        }
        catch(NumberFormatException e){    //If a string of characters was entered as price
            throw new IllegalArgumentException("You did not enter valid digits for the price field");
        } 
    }

    /**
     * This method updates the quantity of a specific investment
     * @param quantity Integer representing the quantity of a specific investment
     * @throws IllegalArgumentException when quantity does not have digits as characters or
     * if the digit is less than 1
     */

    public void setQuantity(String quantity){
        int numQuantity;

        try{
            numQuantity=Integer.parseInt(quantity);
            if(numQuantity<1){
                throw new IllegalArgumentException("Please enter a positive value for the quantity field");
            }
            this.quantity=quantity;          //If quantity consists of valid digits greater than 1
        }
        catch(NumberFormatException e){      //If a string of characters was entered as quantity
            throw new IllegalArgumentException("You did not enter valid digits for the quantity field");
        } 
    }

    /**
     * This method updates the bookValue of a specific investment
     * @param bookValue Double representing the bookValue of a specific investment
     */

    public void setBookValue(double bookValue){
        if(this instanceof Stock){                     //If this investment is an instance of Stock
            ((Stock)this).setBookValue(bookValue);     //Typecasting to use Stock methods
        }
    }

    

    /**
     * This method returns the contents of a specific investment in a formatted manner
     * @return String,which is the formatted content of a specific investment
     */

    public String toString(){
        return "Symbol: "+symbol+','+"Name: "+name+','+"Quantity: "+quantity+','+"Price:" +price+',';
    }

    /**
     * This method returns the index position of the given investment symbol from its ArryayList
     * @param symbol String representing symbol of that investment
     * @param investments Arraylist representing all the investments
     * @return Integer,which is the index position of the given investment symbol from its ArryayList
     */
    public static int symbolExists(String symbol,ArrayList<Investment> investments){           //Returning the index position of the given investment symbol
        String existingSymbol="";
        int i;
        int found=-1;
        if(investments.isEmpty()){
            return -1;
        }
        else{
            for(i=0;i<investments.size();++i){
                existingSymbol=investments.get(i).getSymbol();
                if(existingSymbol.equalsIgnoreCase(symbol)){
                    found=i;
                    break;
                }
                else{                                   //If the symbol does not exist then -1 is returned
                    found=-1;
                }
            }   
        }
        return found;             //We return that investment's index position
    }

    /**
     * This method checks if a new investment has the same symbol as an existing investment and vice-versa
     * returns 0 if the new investment's symbol does not already exist 
     * returns 1 if the new investment's symbol already exists 
     * @param type String representing the investment type
     * @param symbol String representing symbol of that investment
     * @param investments Arraylist representing all the investments
     * @return Integer,which is the index position of the given investment symbol from its ArryayList
     */

    public static int duplicateInvestment(String type,String symbol,ArrayList<Investment> investments){
        int i;
        int retVal=0;
        if(type.equalsIgnoreCase("stock")){
            for(i=0;i<investments.size();++i){
                if(investments.get(i) instanceof Stock && investments.get(i).getSymbol().equalsIgnoreCase(symbol)){    //A stock with the same symbol as another stock is given so we will buy more shares
                    retVal=0;
                }
                else if(investments.get(i) instanceof MutualFund && investments.get(i).getSymbol().equalsIgnoreCase(symbol)){    //A stock with the same symbol as another mutualfund is given so we will reject that entry
                    retVal=1;
                }
            }
        }
        else if(type.equalsIgnoreCase("mutualfund")|| type.equalsIgnoreCase("mutual fund") ){
            for(i=0;i<investments.size();++i){
                if(investments.get(i) instanceof MutualFund && investments.get(i).getSymbol().equalsIgnoreCase(symbol)){         //A mutualfund with the same symbol as another mutualfund is given so we will buy more shares
                    retVal=0;
                }
                else if(investments.get(i) instanceof Stock && investments.get(i).getSymbol().equalsIgnoreCase(symbol)){         //A mutualfund with the same symbol as another stock is given so we will reject that entry
                    retVal=1;
                }
            }
        }
        return retVal;
    }

    /**
     * This method returns the investment's name for a given index position from the ArrayList
     * @param indexName Int representing the index position of that investment in its ArrayList
     * @param investments Arraylist representing all the investments
     * @return String,which is the name of the investment at that index position in its ArryayList
     */

    public static String nameExists(int indexName,ArrayList<Investment> investments){   //This method returns the stock's name for a given index position
        String name="";
        name=investments.get(indexName).getName();
        return name;
    }

    /**
     * This method returns the investment's bookValue for a given index position from the ArrayList
     * @param indexName Int representing the index position of that investment in its ArrayList
     * @param investments Arraylist representing all the investments
     * @return Double,which is the bookValue of the investment at that index position in its ArryayList
     */

    public static double bookValueExists(int indexName,ArrayList<Investment> investments){  //This method returns the investment's bookValue for a given index position
        Investment investment=investments.get(indexName);
        double bookValue=0;
        if(investment instanceof Stock){                   //If this investment is an instance of Stock
            bookValue=((Stock)investment).getBookValue();        //Typecasting to use Stock methods
        }
        else if(investment instanceof MutualFund){          //If this investment is an instance of MutualFund
            bookValue=((MutualFund)investment).getBookValue();   //Typecasting to use MutualFund methods
        }

        return bookValue;
    }

    /**
     * This method returns the investment's quantity for a given index position from the ArrayList
     * @param indexName Int representing the index position of that investment in its ArrayList
     * @param investments Arraylist representing all the investments
     * @return Integer,which is the quantity of the investment at that index position in its ArryayList
     */

    public static String quantityExists(int indexName,ArrayList<Investment> investments){     //This method returns the investment's quantity for a given index position
        String quantity;
        quantity=investments.get(indexName).getQuantity();
        return quantity;
    }

    /**
     * This method returns the updated bookValue and quantity of the investment whose shares are to be purchased
     * @param investmentFound Integer representing the index position of the investment whose shares are to be purchased
     * @param quantity Integer representing the quantity of the investment whose shares are to be purchased
     * @param price Double representing the price of the investment whose shares are to be purchased
     * @param investments Arraylist representing all the investments
     * @return Double[],which is an array of double values where the elements are the updated bookValue
     * and quantity of an investment
     */

    public static double[] buy(int investmentFound,int quantity,double price,ArrayList<Investment> investments){
        double retBookValue;
        double[] retVal=new double[2];
        int retQuantity;
        double bookValue;

        retBookValue=bookValueExists(investmentFound,investments);  //Storing the existing bookValue of that investment
        retQuantity=Integer.parseInt(quantityExists(investmentFound,investments));    //Storing the existing quantity of that investment
        bookValue=(price*quantity)+retBookValue;                  //Calculating the updated bookValue
        quantity=retQuantity+quantity;                                            //Calculating the updated quantity
        retVal[0]=bookValue;                                                      //Adding the updated bookValue and quantity to array: retVal
        retVal[1]=quantity;

        return retVal;
    }

    /**
     * This method calculates the gain for all the investments 
     * @param investments Arraylist representing all the investments 
     * @param getGainMessage JTextArea which is the message area for the "getGain" JFrame
     * @return Double, which is the total gain of Portfolio
     */

    public static double calculateGain(ArrayList<Investment> investments,JTextArea getGainMessage){
        int i;
        double eachGain=0;
        double totalGain=0;
        double price;
        double bookValue;
        int quantity;
        final double STOCKCOMMISSION=9.99;
        final double MUTUALFUNDCOMMISSION=45;

        getGainMessage.append("The gain of each investment in the Portfolio is:\n\n");
        for(i=0;i<investments.size();++i){                
            price=Double.parseDouble(investments.get(i).getPrice());
            quantity=Integer.parseInt(investments.get(i).getQuantity());
            bookValue=bookValueExists(i,investments);
            if(investments.get(i) instanceof Stock){                  //If this investment is an instance of Stock
                eachGain=(price*quantity-STOCKCOMMISSION)-bookValue;; //9.99 is subtracted from each investment
            }
            else if(investments.get(i) instanceof MutualFund){        //If this investment is an instance of MutualFund
                eachGain=(price*quantity-MUTUALFUNDCOMMISSION)-bookValue;   //45 is subtracted from each investment
            }
            
            totalGain=totalGain+eachGain;                          //Adding the gain for each investment to totalGain
            getGainMessage.append((i+1)+". "+"Symbol: "+investments.get(i).getSymbol()+", Name: "+investments.get(i).getName()+", Gain: "+String.format("%.2f",eachGain)+"\n");
        }
        return totalGain;
    }    
}



