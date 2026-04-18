/**
 * Author: Isaiah Santamaria, Sean Powers
 * Version: 4/15/2026
 */
public interface BillTransaction{
    
    /** get reasoning from the object of the text input*/
    public String getReason();

    /** get the amount of transaction */
    public int getAmount();

    /** toString() method*/
    @Override
    public String toString();

}