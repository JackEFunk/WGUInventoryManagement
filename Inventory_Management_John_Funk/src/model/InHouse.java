package model;

/**
 * Class for In House
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * Method for In House
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Method to set machine ID
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Method to get machine ID
     */
    public int getMachineId(){
        return machineId;
    }
}
