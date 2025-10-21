package Data_Structure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    // ------------------- Global data (custom LinkedList) -------------------
    static LinkedList<Product>  products  = new LinkedList<>();
    static LinkedList<Customer> customers = new LinkedList<>();
    static LinkedList<Order>    orders    = new LinkedList<>();
    static final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");

    // ------------------- Main ---------------------
    public static void main(String[] args) {
        // Load CSVs (your exact paths)
        loadProducts("src/Data_Structure/prodcuts.csv");   // file name kept as you asked
        loadCustomers("src/Data_Structure/customers.csv");
        loadOrders("src/Data_Structure/orders.csv");
        loadReviews("src/Data_Structure/reviews.csv");

        System.out.println("Loaded Products=" + products.size()
                + ", Customers=" + customers.size()
                + ", Orders=" + orders.size());

        // Menu loop
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n========= MENU =========");
            System.out.println("1.  Add Product");
            System.out.println("2.  Add Customer");
            System.out.println("3.  Place Order");
            System.out.println("4.  Add Review to Product");
            System.out.println("5.  Extract Reviews by Customer");
            System.out.println("6.  Top 3 Products by Average Rating");
            System.out.println("7.  Orders Between Two Dates");
            System.out.println("8.  Common Reviewed Products (avg > 4)");
            System.out.println("9.  View Product Details + Reviews");
            System.out.println("10. Update Product (price/stock)");
            System.out.println("11. Edit Review by reviewId");
            System.out.println("12. Cancel an Order");
            System.out.println("13. Add Product to Existing Order");
            System.out.println("14. List Customer Orders");
            System.out.println("15. List Customer Reviews");
            System.out.println("16. List All (Products/Customers/Orders)");
            System.out.println("17. Remove Product by ID");
            System.out.println("18. Remove Customer by ID");
            System.out.println("0.  Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) { sc.nextLine(); continue; } // end if not int
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:  addProduct(sc); break;
                case 2:  addCustomer(sc); break;
                case 3:  placeOrder(sc); break;
                case 4:  addReview(sc); break;
                case 5:  extractReviewsByCustomer(sc); break;
                case 6:  Product.printTopNByAverageRating(products, 3); break;
                case 7:  ordersBetweenDates(sc); break;
                case 8:  commonReviewedProducts(sc); break;
                case 9:  viewProductDetails(sc); break;
                case 10: updateProduct(sc); break;
                case 11: editReviewById(sc); break;
                case 12: cancelOrder(sc); break;
                case 13: addProductToExistingOrder(sc); break;
                case 14: listCustomerOrders(sc); break;
                case 15: listCustomerReviews(sc); break;
                case 16: listAll(); break;
                case 17: removeProductById(sc); break;
                case 18: removeCustomerById(sc); break;
                case 0:  System.out.println("Program ended"); break;
                default: System.out.println("Invalid choice"); break;
            } // end switch
        } // end while
        sc.close();
    } // end main

    // ------------------- MENU OPS (create/interactive + light glue) -------------------

    static void addProduct(Scanner sc) {
        System.out.print("Product ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Price: ");
        double price = sc.nextDouble(); sc.nextLine();
        System.out.print("Stock: ");
        int stock = sc.nextInt(); sc.nextLine();

        products.add(new Product(id, name, price, stock));
        System.out.println("Product added");
    } // end addProduct

    static void addCustomer(Scanner sc) {
        System.out.print("Customer ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        customers.add(new Customer(id, name, email));
        System.out.println("Customer added");
    } // end addCustomer

    static void placeOrder(Scanner sc) {
        System.out.print("Customer ID: ");
        int cid = sc.nextInt(); sc.nextLine();
        Customer cust = Customer.searchById(customers, cid);
        if (cust == null) { System.out.println("Customer not found"); return; } // end if

        int newOrderId = orders.size() + 1;
        Order order = new Order(newOrderId, cust, new Date());

        char more = 'y';
        while (more == 'y' || more == 'Y') {
            System.out.print("Product ID: ");
            int pid = sc.nextInt(); sc.nextLine();
            Product p = Product.searchById(products, pid);
            if (p == null) {
                System.out.println("Product not found");
            } else {
                System.out.print("Quantity: ");
                int q = sc.nextInt(); sc.nextLine();
                order.addProduct(p, q);
                System.out.println("Added " + q + " x " + p.getName());
            } // end if-else

            System.out.print("Add another? (y/n): ");
            String s = sc.nextLine();
            if (s.length() > 0) { more = s.charAt(0); } else { more = 'n'; } // end if
        } // end while

        cust.placeOrder(order);
        orders.add(order);
        System.out.println("Order placed. Total = " + order.getTotalPrice());
    } // end placeOrder

    static void addReview(Scanner sc) {
        System.out.print("Product ID: ");
        int pid = sc.nextInt(); sc.nextLine();
        Product p = Product.searchById(products, pid);
        if (p == null) { System.out.println("Product not found"); return; } // end if

        System.out.print("Customer ID: ");
        int cid = sc.nextInt(); sc.nextLine();
        Customer u = Customer.searchById(customers, cid);
        if (u == null) { System.out.println("Customer not found"); return; } // end if

        System.out.print("Rating (1–5): ");
        int rating = sc.nextInt(); sc.nextLine();
        System.out.print("Comment: ");
        String comment = sc.nextLine();

        int newReviewId = p.getReviews().size() + u.getMyReviews().size() + 1;
        Review r = new Review(newReviewId, pid, cid, rating, comment);

        p.addReview(r);   // Product ← Review
        u.addReview(r);   // Customer ← Review

        System.out.println("Review added");
    } // end addReview

    static void extractReviewsByCustomer(Scanner sc) {
        System.out.print("Customer ID: ");
        int cid = sc.nextInt(); sc.nextLine();
        Customer.printReviewsByCustomerId(customers, cid);
    } // end extractReviewsByCustomer

    static void ordersBetweenDates(Scanner sc) {
        try {
            System.out.print("Start (dd/MM/yyyy): ");
            Date start = DF.parse(sc.nextLine());
            System.out.print("End (dd/MM/yyyy): ");
            Date end = DF.parse(sc.nextLine());
            Order.printBetweenDates(orders, start, end);
        } catch (Exception e) {
            System.out.println("Wrong date format");
        } // end try-catch
    } // end ordersBetweenDates

    static void commonReviewedProducts(Scanner sc) {
        System.out.print("First customer ID: ");
        int c1 = sc.nextInt(); sc.nextLine();
        System.out.print("Second customer ID: ");
        int c2 = sc.nextInt(); sc.nextLine();
        Product.printCommonReviewedAbove(products, c1, c2, 4.0);
    } // end commonReviewedProducts

    static void viewProductDetails(Scanner sc) {
        System.out.print("Product ID: ");
        int pid = sc.nextInt(); sc.nextLine();
        Product p = Product.searchById(products, pid);
        if (p == null) { System.out.println("Product not found"); return; } // end if
        p.printDetails();
    } // end viewProductDetails

    static void updateProduct(Scanner sc) {
        System.out.print("Product ID: ");
        int pid = sc.nextInt(); sc.nextLine();
        Product p = Product.searchById(products, pid);
        if (p == null) { System.out.println("Product not found"); return; } // end if

        System.out.print("New price (or -1 to skip): ");
        double np = sc.nextDouble(); sc.nextLine();
        System.out.print("New stock (or -1 to skip): ");
        int ns = sc.nextInt(); sc.nextLine();

        p.updateProduct(np, ns);
        System.out.println("Product updated");
    } // end updateProduct

    static void editReviewById(Scanner sc) {
        System.out.print("Product ID: ");
        int pid = sc.nextInt(); sc.nextLine();
        Product p = Product.searchById(products, pid);
        if (p == null) { System.out.println("Product not found"); return; } // end if

        System.out.print("Review ID to edit: ");
        int rid = sc.nextInt(); sc.nextLine();

        System.out.print("New rating (1-5, or 0 to skip): ");
        int nr = sc.nextInt(); sc.nextLine();
        Integer newRating = (nr >= 1 && nr <= 5) ? Integer.valueOf(nr) : null;

        System.out.print("New comment (leave empty to skip): ");
        String nc = sc.nextLine();
        String newComment = (nc.length() == 0) ? null : nc;

        boolean ok = p.editReviewById(rid, newRating, newComment);
        if (ok) { System.out.println("Review updated"); } else { System.out.println("Review not found"); } // end if
    } // end editReviewById

    static void cancelOrder(Scanner sc) {
        System.out.print("Order ID: ");
        int oid = sc.nextInt(); sc.nextLine();
        Order o = Order.searchById(orders, oid);
        if (o == null) { System.out.println("Order not found"); return; } // end if
        o.cancel();
    } // end cancelOrder

    static void addProductToExistingOrder(Scanner sc) {
        System.out.print("Order ID: ");
        int oid = sc.nextInt(); sc.nextLine();
        System.out.print("Product ID: ");
        int pid = sc.nextInt(); sc.nextLine();
        System.out.print("Quantity: ");
        int q = sc.nextInt(); sc.nextLine();

        boolean ok = Order.addProductToOrderById(orders, products, oid, pid, q);
        if (ok) { System.out.println("Added product to order"); } else { System.out.println("Order or product not found"); } // end if
    } // end addProductToExistingOrder

    static void listCustomerOrders(Scanner sc) {
        System.out.print("Customer ID: ");
        int cid = sc.nextInt(); sc.nextLine();
        Customer c = Customer.searchById(customers, cid);
        if (c == null) { System.out.println("Customer not found"); return; } // end if
        c.viewOrderHistory();
    } // end listCustomerOrders

    static void listCustomerReviews(Scanner sc) {
        System.out.print("Customer ID: ");
        int cid = sc.nextInt(); sc.nextLine();
        Customer c = Customer.searchById(customers, cid);
        if (c == null) { System.out.println("Customer not found"); return; } // end if
        c.printMyReviews();
    } // end listCustomerReviews

    static void listAll() {
        Product.printAll(products);
        System.out.println();
        Customer.printAll(customers);
        System.out.println();
        Order.printAll(orders);
    } // end listAll

    static void removeProductById(Scanner sc) {
        System.out.print("Product ID to remove: ");
        int pid = sc.nextInt(); sc.nextLine();
        boolean ok = Product.removeById(products, pid);
        if (ok) { System.out.println("Product removed (and its reviews cleared)."); }
        else    { System.out.println("Product not found."); }
    } // end removeProductById

    static void removeCustomerById(Scanner sc) {
        System.out.print("Customer ID to remove: ");
        int cid = sc.nextInt(); sc.nextLine();
        boolean ok = Customer.removeById(customers, cid);
        if (ok) { System.out.println("Customer removed."); }
        else    { System.out.println("Customer not found."); }
    } // end removeCustomerById

    // ------------------- CSV LOADERS -------------------
    // products.csv: productId,name,price,stock
    static void loadProducts(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] c = line.split(",", -1);
                int id = Integer.parseInt(c[0]);
                String name = c[1];
                double price = Double.parseDouble(c[2]);
                int stock = Integer.parseInt(c[3]);
                products.add(new Product(id, name, price, stock));
            } // end while
        } catch (Exception e) {
            System.out.println("Products load error: " + e.getMessage());
        } // end try-catch
    } // end loadProducts

    // customers.csv: customerId,name,email
    static void loadCustomers(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] c = line.split(",", -1);
                int id = Integer.parseInt(c[0]);
                String name = c[1];
                String email = c[2];
                customers.add(new Customer(id, name, email));
            } // end while
        } catch (Exception e) {
            System.out.println("Customers load error: " + e.getMessage());
        } // end try-catch
    } // end loadCustomers

    // orders.csv: flexible header; needs orderDate/date and productIds/items/products
    static void loadOrders(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine();
            if (header == null) { return; } // end if empty

            String[] h = header.split(",", -1);
            for (int i = 0; i < h.length; i++) { h[i] = h[i].toLowerCase(); } // end for

            int idxOrderId = -1, idxCustomerId = -1, idxDate = -1, idxItems = -1;
            for (int i = 0; i < h.length; i++) {
                String k = h[i];
                if (k.equals("orderid")) idxOrderId = i;
                else if (k.equals("customerid")) idxCustomerId = i;
                else if (k.equals("orderdate") || k.equals("date")) idxDate = i;
                else if (k.equals("items") || k.equals("productids") || k.equals("products")) idxItems = i;
            } // end for

            if (idxDate == -1 || idxItems == -1) {
                System.out.println("Orders load error: need orderDate/date and productIds/items columns.");
                return;
            } // end if

            String line; int autoId = 1;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] c = line.split(",", -1);

                int orderId = (idxOrderId >= 0 && idxOrderId < c.length && c[idxOrderId].length() > 0)
                        ? Integer.parseInt(c[idxOrderId]) : autoId++;

                Integer custId = (idxCustomerId >= 0 && idxCustomerId < c.length && c[idxCustomerId].length() > 0)
                        ? Integer.parseInt(c[idxCustomerId]) : null;

                Date date;
                try {
                    String ds = (idxDate < c.length) ? c[idxDate] : "";
                    ds = ds.replace("\r", "").replace("\"", "").replace(" ", ""); // small normalize (no trim)
                    try { date = new SimpleDateFormat("dd/MM/yyyy").parse(ds); }
                    catch (Exception e1) { date = new SimpleDateFormat("d/M/yyyy").parse(ds); }
                } catch (Exception e) {
                    System.out.println("Orders load error: Unparseable date: \"" + ((idxDate < c.length) ? c[idxDate] : "") + "\"");
                    continue;
                } // end try-catch date

                String items = (idxItems < c.length) ? c[idxItems] : "";
                Customer cust = (custId == null) ? null : Customer.searchById(customers, custId);
                Order order = new Order(orderId, cust, date);

                if (items.length() > 0) {
                    String[] pairs = items.split(";");
                    for (int j = 0; j < pairs.length; j++) {
                        String token = pairs[j];
                        if (token.length() == 0) { continue; } // end if
                        String[] pq = token.split("x"); // allow "101" or "101x2"
                        int pid = Integer.parseInt(pq[0]);
                        int qty = (pq.length > 1) ? Integer.parseInt(pq[1]) : 1;
                        Product p = Product.searchById(products, pid);
                        if (p != null) { order.addProduct(p, qty); } // end if
                    } // end for
                } // end if items

                orders.add(order);
                if (cust != null) { cust.placeOrder(order); } // end if
            } // end while
        } catch (Exception e) {
            System.out.println("Orders load error: " + e.getMessage());
        } // end try-catch
    } // end loadOrders

    // reviews.csv: reviewId,productId,customerId,rating,comment
    static void loadReviews(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] c = line.split(",", -1);

                int reviewId   = Integer.parseInt(c[0]);
                int productId  = Integer.parseInt(c[1]);
                int customerId = Integer.parseInt(c[2]);
                int rating     = Integer.parseInt(c[3]);
                String comment = (c.length > 4) ? c[4] : "";

                Product  p = Product.searchById(products, productId);
                Customer u = Customer.searchById(customers, customerId);
                if (p != null && u != null) {
                    Review r = new Review(reviewId, productId, customerId, rating, comment);
                    p.addReview(r);
                    u.addReview(r);
                } // end if
            } // end while
        } catch (Exception e) {
            System.out.println("Reviews load error: " + e.getMessage());
        } // end try-catch
    } // end loadReviews
} // end Main class
