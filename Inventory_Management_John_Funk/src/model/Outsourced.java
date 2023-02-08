package model;


/**
 * Class for Outsourced
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Method for Outsourced
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Method to set company name
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Method to get company name
     */
    public String getCompanyName(){
        return companyName;
    }
}
