package model;

/**
 * Class for parts that are outsourced and have a company name instead of machine id
 * @author Steven Volovar
 */

public class Outsourced extends Part{

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @param theName name of company
     */
    public void setCompanyName(String theName){
        this.companyName = companyName;
    }

    /**
     *
     * @return name of the company
     */
    public String getCompanyName(){
        return companyName;
    }


}