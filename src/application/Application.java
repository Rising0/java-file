package application;

import model.entities.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<>();

        System.out.print("Enter file path: ");
        String path = sc.nextLine();

        File filePath = new File(path);
        boolean success = new File(filePath.getParent() + "/out").mkdir();

        System.out.println("Directory created: " + success);

        System.out.print("How many products do you want to register? ");
        int productsQuantity = sc.nextInt();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {

            for (int i = 0; i < productsQuantity; i++) {
                System.out.printf("\nProduct #%d data:\n", (i + 1));

                sc.nextLine();
                bufferedWriter.newLine();

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Price: $");
                Double price = sc.nextDouble();

                System.out.print("Quantity: ");
                Integer quantity = sc.nextInt();

                products.add(new Product(name, price, quantity));

                bufferedWriter.write(name + ", " + String.format("%.2f", price) + ", " + quantity);
            }
        }
        catch (RuntimeException exception) {
            exception.printStackTrace();
        }
        catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.getParent() + "/out/summary.csv", true))) {
            for (Product product : products) {
                bufferedWriter.newLine();
                bufferedWriter.write(product.getName()  + ", " + String.format("%.2f", product.getPrice() * product.getQuantity()));
            }
        } catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}
