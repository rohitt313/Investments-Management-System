# Investments-Management-System


## Objective
My program is trying to solve the problem of storing different investments such as stocks and Mutual Funds and maintaining an E-Portfolio for the user. My program allows the user to add new investments, buy more shares of an existing investment, sell partial/all shares of an investment, update the prices of all the investments, calculate the profit(getGain) that the user makes if all the investments are sold and search for investments based on different conditions. Additionally, this program uses a Graphical User Interface. Lastly, I have added exception handling so that the user is informed if they enter any kind of invalid data anywhere in the program, making it program more robust and user-friendly.


## Requirements 
To build my program, the user needs at least JDK version-17.

## Compilation 
To test my program, the user needs to first download the folder: Investments-Management-System. To execute these files, the user can enter the following command:
java ePortfolio/Portfolio fileName.txt

If the user wants to run the program using the.jar file, then the command to run the .jar file is:
java -jar ePortfolio.jar fileName.txt

## Functionality
The package - ePortfolio contains an event-driven program where the user will be provided a drop-down Menu and 3 window control buttons: Minimize, Maximize and Close at the top of the window. However, the Close button will be disabled for the window so that the user does not accidentally close the window and lose the changes made to the Portfolio. The Main Menu has the following options:
(a)Buy - To buy a new investment or buy additional shares of an existing investment
(b)Sell - To sell all/partial shares of an existing investment 
(c)Update - To update the current price of an existing investment
(d)getGain - To see the total gain of the Portfolio if all the investments were to be sold as well as individual gain of each investment
(e)Search - To search an investment/investments based on Symbol, Name(Keywords) and price
(f)Quit - To terminate the program which will close the window and save the changes made to the Portfolio in a text file




6. For the "quit" command, if the user selects this option from the drop-down menu, then we will terminate the program and save all the changes made to the Portfolio in a text file.
