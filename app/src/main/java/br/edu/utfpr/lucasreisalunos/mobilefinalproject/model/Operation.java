package br.edu.utfpr.lucasreisalunos.mobilefinalproject.model;

import java.util.Comparator;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;

/**
 * Created by reisaolucas on 22/11/17.
 */

public class Operation {
    private long id;
    private String timeStamp;
    private double baseValue;
    private double payout;
    private int martingale;
    private String winLoss;
    private double result;

    public static Comparator<Operation> comparator
            = new Comparator<Operation>() {
        @Override
        public int compare(Operation op1, Operation op2) {
            return op1.getTimeStamp().compareToIgnoreCase(op2.getTimeStamp());
        }
    };

    public Operation (){

    }

    public Operation(String timeStamp){
        setTimeStamp(timeStamp);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }

    public double getPayout() {
        return payout;
    }

    public void setPayout(double payout) {
        this.payout = payout;
    }

    public int getMartingale() {
        return martingale;
    }

    public void setMartingale(int martingale) {
        this.martingale = martingale;
    }

    public String getWinLoss() {
        return winLoss;
    }

    public void setWinLoss(String winLoss) {
        this.winLoss = winLoss;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString(){
        return "" +getTimeStamp()+ " (ID: "+Long.toString(getId())+ ") (Result: "  + Double.toString(getResult()) +")";
    }
}
