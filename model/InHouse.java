package model;

/**
 * Class for parts that are in house and have a machine ID instead of a company name
 * @author Steven Volovar
 */

public class InHouse extends Part{

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *Sets machine ID
     * @param machineNumber machine id
     */
    public void setMachineId(int machineNumber){
        this.machineId = machineId;
    }

    /**
     * Returns machine ID
     * @return machine id
     */
    public int getMachineId(){
        return machineId;
    }


}
