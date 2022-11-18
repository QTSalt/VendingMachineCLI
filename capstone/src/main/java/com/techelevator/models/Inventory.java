package com.techelevator.models;

import com.techelevator.models.exceptions.InvalidIDException;
import com.techelevator.models.exceptions.SoldOutException;
import com.techelevator.models.products.Product;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inventory {

    // Properties
    private Map<Product, Integer> inventory;


    // Constructor
    public Inventory() {

        loadInventory();
    }

    // Methods

    private void loadInventory() {

        inventory = new HashMap<>();

        File productsFile = new File("data/vendingmachine.csv");
        try (Scanner reader = new Scanner(productsFile)) {

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] columns = line.split("\\|");

                String id = columns[0];
                String name = columns[1];
                BigDecimal price = new BigDecimal(columns[2]);
                String type = columns[3];

                Product product = new Product(id, name, price, type);

                // Loads 5 of the product everytime the vending machine initializes
                inventory.put(product, 5);
            }
        } catch (IOException ex) {
            // TO DO - Logger
            ex.getMessage();
        }
    }

    public Map<Product, Integer> getProducts() {
        return inventory;
    }

    public void decrementQuantity(Product product) throws SoldOutException {
        int quantity = inventory.get(product);

        if (quantity > 0) {
            quantity--;
            inventory.put(product, quantity);
        } else {
            throw new SoldOutException();
        }
    }

    public boolean isIDValid(String id) {
        //Loops through our entire inventory, and finds the product matching the input ID
        for (Map.Entry<Product, Integer> product : getProducts().entrySet()) {

            // Validates the given ID
            if (product.getKey().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public Product getProductByID(String id) {
       try {
        //Loops through our entire inventory, and finds the product matching the input ID
        for (Map.Entry<Product, Integer> product : getProducts().entrySet()) {

            // Validates the given ID
            if (product.getKey().getId().equals(id)) {
                return product.getKey();
            }
        }
            throw new InvalidIDException();
        } catch (InvalidIDException ex) {
            System.out.println("The ID you entered is invalid");
        }
        return null;
    }

    public Integer getQuantity(Product product) {
        return inventory.get(product);
    }
}

