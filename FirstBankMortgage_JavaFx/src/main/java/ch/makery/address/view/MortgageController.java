package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.UUID;

import base.RateDAL;
import ch.makery.address.MainApp;
import ch.makery.address.model.Rate;


public class MortgageController {


    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MortgageController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    	incomeLabel.setText("Yearly income");
    	expensesLabel.setText("Monthly expenses");
    	creditScoreLabel.setText("Credit score");
    	housePriceLabel.setText("Enter desired home price and repayment term");
    	ObservableList<String> terms = FXCollections.observableArrayList("15 Years","30 Years");
    	cmbTerm.setItems(terms);
    	lblAltPayment.setVisible(false); 
    	lblMortgagePayment.setVisible(false); 
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void onPress(){
    	lblMortgagePayment.setText("CALCULATING PLEASE WAIT"); 
    	lblMortgagePayment.setVisible(true);
    	lblAltPayment.setVisible(false); 
    	calculateMortgage();
    }
    
    @FXML
    private void calculateMortgage() {
    	
    	
        Double incom = new Double(this.txtIncome.getText());
        Double expense = new Double(this.txtExpenses.getText());
        Double creditscor = new Double(this.txtCreditScore.getText());
        Double housecos = new Double(this.txtHouseCost.getText());
        int terms = 0;
        if (this.cmbTerm.getSelectionModel().getSelectedIndex() == 0){
        	terms = 15;
        }
        else if(this.cmbTerm.getSelectionModel().getSelectedIndex() == 1){
        	terms = 30;
        }
        
        int income = incom.intValue();
        System.out.println(income);
        int expenses = expense.intValue()*12;
        System.out.println(expenses);
        int creditscore = creditscor.intValue();
        System.out.println(creditscore);
        int housecost = housecos.intValue();
        System.out.println(housecost);
        
        System.out.println(terms);
        
        double payments = ((int)(Rate.getPayment(creditscore, housecost, terms)*100))/100.00;

        double acceptable1 = ((int)((.36*income)*100))/1200;
        double acceptable2 = ((int)((.18*(income+expenses))*100))/1200;
        
        lblMortgagePayment.setText("For your desired loan, given your credit score, your monthly payments will come out to $" +payments);
        String comments = "";
        double altPayment = 0;
        if (acceptable1<acceptable2){
        	altPayment = acceptable1;
        	if (altPayment>payments){
        		comments = "However, the maximum monthly payment you qualify for is $" + altPayment+" ...perhaps you are interested in a larger home?";
        	}else {
        		comments = "Unfortunately, due to your meagre income, the maximum monthly payment you qualify for is $" + altPayment;
        	}
        } else{
        	altPayment = acceptable2;
        	if (altPayment>payments){
        		comments = "However, the maximum monthly payment you qualify for is $" + altPayment +" ...perhaps you are interested in a larger home?";
        	}else {
        		comments = "Unfortunately, due to your exhorbitant lifestyle, the maximum monthly payment you qualify for is $" + altPayment;
        	}
        }
        	
        lblAltPayment.setText(comments);
        
        lblAltPayment.setVisible(true); 
    	lblMortgagePayment.setVisible(true); 
        
    }
    
}
