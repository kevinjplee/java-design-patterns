package com.iluwater.embeddedvalue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class CakeStore {
    private int balance;
    private static Inventory inventory;

    public CakeStore()
    {
        balance = 0;
        inventory = new Inventory();
    }

    public static void load(ResultSet rs){
    
        try{
            int balance = rs.getInt("Balance");
            inventory.setEgg(rs.getInt("Egg"));
            inventory.setMilk(rs.getInt("Milk"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(int balance, int egg, int milk)
    {
        balance = balance;
        inventory.setEgg(egg);
        inventory.setMilk(milk);
    }

    public int getBalance()
    {
        return balance;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

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
