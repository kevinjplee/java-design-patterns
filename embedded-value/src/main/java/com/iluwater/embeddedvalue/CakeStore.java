package com.iluwater.embeddedvalue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class CakeStore {
    private int balance;
    private Inventory inventory;

    /**
     * Initailizer for the CakeStore class.
     */
    public CakeStore()
    {
        balance = 0;
        inventory = new Inventory();
    }

    /**
     * Loads the private variables with the value from the ResultSet.
     * Calls setter for the inventory variable instead.
     * @param rs
     */
    public void load(ResultSet rs){
        System.out.println("load start");
        try{
            System.out.print("load try");
            int balance = rs.getInt("Balance");
            inventory.setEgg(rs.getInt("Egg"));
            inventory.setMilk(rs.getInt("Milk"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * setter for the private variables.
     * @param _balance
     * @param egg
     * @param milk
     */
    public void update(int _balance, int egg, int milk)
    {
        setBalance(_balance);
        inventory.setEgg(egg);
        inventory.setMilk(milk);
    }

    /**
     * setter for the private variable balance.
     * @param _balance
     */
    public void setBalance(int _balance)
    {
        balance = _balance;
    }

    /**
     * Getter for private variable balance.
     * @return
     */
    public int getBalance()
    {
        return balance;
    }

    /**
     * Getter for private variable Inventory.
     * @return
     */
    public Inventory getInventory()
    {
        return inventory;
    }

    /**
     * Updates the database for Cakestore with the private variable values.
     * @param dS
     * @return
     */
    public boolean updateDB(DataSource dS) {
        try (var connection = dS.getConnection();
             var statement = connection.prepareStatement("UPDATE CAKESTORE SET balance = ?, egg = ?, milk = ?")) {
            statement.setInt(1, balance);
            statement.setInt(2, inventory.getEgg());
            statement.setInt(3, inventory.getMilk());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
}
