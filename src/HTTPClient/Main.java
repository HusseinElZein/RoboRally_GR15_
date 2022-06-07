package exercise12;


import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {
        try {
            int valg = 0;
            Scanner scan = new Scanner(System.in);

            while (valg != 3) {
                System.out.println("MENU TEST RESTFull API");
                System.out.println("\t1. Get all products");
                System.out.println("\t2. Get product");
                System.out.println("\t3. Add product");
                System.out.println("\t4. Update product");
                System.out.println("\t5. Delete product");
                System.out.println("\t6. EXIT");
                System.out.print("Enter option:");
                valg = scan.nextInt();
                switch (valg) {
                    case 1: {
                        String result = ProductClient.getProducts();
                        System.out.println(result);
                    }
                    break;
                    case 2: {
                        String result = ProductClient.getProduct();
                        System.out.println(result);
                    }
                    break;
                    case 6:
                        System.out.println("Bye");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            scan.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}