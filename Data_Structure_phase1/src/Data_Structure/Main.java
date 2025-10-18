package Data_Structure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    // Global data (custom LinkedList)
    static LinkedList<Product> products = new LinkedList<>();
    static LinkedList<Customer> customers = new LinkedList<>();
    static LinkedList<Order> orders = new LinkedList<>();

    static final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        // Load CSV files
        loadProducts("src/Data_Structure/prodcuts.csv");
        loadCustomers("src/Data_Structure/customers.csv");
        loadOrders("src/Data_Structure/orders.csv");
        loadReviews("src/Data_Structure/reviews.csv");

        System.out.println("Loaded Products=" + products.size()
                + ", Customers=" + customers.size()
                + ", Orders=" + orders.size());

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n========= MENU =========");
            System.out.println("1. Add Product");
            System.out.println("2. Add Customer");
            System.out.println("3. Place Order");
            System.out.println("4. Add Review to Product");
            System.out.println("5. Extract Reviews by Customer");
            System.out.println("6. Top 3 Products by Average Rating");
            System.out.println("7. Orders Between Two Dates");
            System.out.println("8. Common Reviewed Products (avg > 4)");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                sc.nextLine();
                continue;
            } // end if not int

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addProduct(sc);
                    break;
                case 2:
                    addCustomer(sc);
                    break;
                case 3:
                    placeOrder(sc);
                    break;
                case 4:
                    addReview(sc);
                    break;
                case 5:
                    extractReviewsByCustomer(sc);
                    break;
                case 6:
                    top3Products();
                    break;
                case 7:
                    ordersBetweenDates(sc);
                    break;
                case 8:
                    commonReviewedProducts(sc);
                    break;
                case 0:
                    System.out.println("Program ended");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            } // end switch
        } // end while
        sc.close();
    } // end main

    // ------------------- MENU OPERATIONS -------------------

    static void addProduct(Scanner sc) {
        System.out.print("Product ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        products.add(new Product(id, name, price, stock));
        System.out.println("Product added");
    } // end addProduct

    static void addCustomer(Scanner sc) {
        System.out.print("Customer ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        customers.add(new Customer(id, name, email));
        System.out.println("Customer added");
    } // end addCustomer

    static void placeOrder(Scanner sc) {
        System.out.print("Customer ID: ");
        int cid = sc.nextInt();
        sc.nextLine();
        Customer cust = findCustomerById(cid);
        if (cust == null) {
            System.out.println("Customer not found");
            return;
        } // end if not found

        int newOrderId = orders.size() + 1;
        Order order = new Order(newOrderId, cust, new Date());

        char more = 'y';
        while (more == 'y' || more == 'Y') {
            System.out.print("Product ID: ");
            int pid = sc.nextInt();
            sc.nextLine();
            Product p = findProductById(pid);

            if (p == null) {
                System.out.println("Product not found");
            } else {
                System.out.print("Quantity: ");
                int q = sc.nextInt();
                sc.nextLine();
                order.addProduct(p, q);
                System.out.println("Added " + q + " x " + p.getName());
            } // end if-else

            System.out.print("Add another? (y/n): ");
            String s = sc.nextLine();
            if (s.length() > 0) {
                more = s.charAt(0);
            } else {
                more = 'n';
            } // end if
        } // end while

        cust.placeOrder(order);
        orders.add(order);
        System.out.println("Order placed. Total = " + order.getTotalPrice());
    } // end placeOrder

    static void addReview(Scanner sc) {
        System.out.print("Product ID: ");
        int pid = sc.nextInt();
        sc.nextLine();
        Product p = findProductById(pid);
        if (p == null) {
            System.out.println("Product not found");
            return;
        } // end if

        System.out.print("Customer ID: ");
        int cid = sc.nextInt();
        sc.nextLine();
        Customer u = findCustomerById(cid);
        if (u == null) {
            System.out.println("Customer not found");
            return;
        } // end if

        System.out.print("Rating (1–5): ");
        int rating = sc.nextInt();
        sc.nextLine();
        System.out.print("Comment: ");
        String comment = sc.nextLine();

        int newReviewId = p.getReviews().size() + u.getMyReviews().size() + 1;
        Review r = new Review(newReviewId, pid, cid, rating, comment);

        p.addReview(r);
        u.addReview(r);

        System.out.println("Review added");
    } // end addReview

    static void extractReviewsByCustomer(Scanner sc) {
        System.out.print("Customer ID: ");
        int cid = sc.nextInt();
        sc.nextLine();
        Customer u = findCustomerById(cid);
        if (u == null) {
            System.out.println("Customer not found");
            return;
        } // end if

        Node<Review> cur = u.getMyReviews().getHead();
        if (cur == null) {
            System.out.println("No reviews by this customer");
            return;
        } // end if

        while (cur != null) {
            Review r = cur.getData();
            Product p = findProductById(r.getProductId());
            String pname = (p == null) ? "Product#" + r.getProductId() : p.getName();
            System.out.println(pname + " rating=" + r.getRating() + ", comment=" + r.getComment());
            cur = cur.getNext();
        } // end while
    } // end extractReviewsByCustomer

    static void top3Products() {
        Product a = null, b = null, c = null;
        double ra = -1, rb = -1, rc = -1;

        Node<Product> cur = products.getHead();
        while (cur != null) {
            Product p = cur.getData();
            double r = p.getAverageRating();

            if (r > ra) {
                c = b; rc = rb;
                b = a; rb = ra;
                a = p; ra = r;
            } else if (r > rb) {
                c = b; rc = rb;
                b = p; rb = r;
            } else if (r > rc) {
                c = p; rc = r;
            } // end if chain

            cur = cur.getNext();
        } // end while

        System.out.println("Top 3 Products:");
        if (a != null) System.out.println("1) " + a.getName() + " (" + ra + ")");
        if (b != null) System.out.println("2) " + b.getName() + " (" + rb + ")");
        if (c != null) System.out.println("3) " + c.getName() + " (" + rc + ")");
    } // end top3Products

    static void ordersBetweenDates(Scanner sc) {
        try {
            System.out.print("Start (dd/MM/yyyy): ");
            Date start = DF.parse(sc.nextLine());
            System.out.print("End (dd/MM/yyyy): ");
            Date end = DF.parse(sc.nextLine());

            System.out.println("Orders between " + DF.format(start) + " and " + DF.format(end));
            Node<Order> cur = orders.getHead();

            while (cur != null) {
                Order o = cur.getData();
                Date d = o.getOrderDate();
                if (!d.before(start) && !d.after(end)) {
                    System.out.println(o);
                } // end if
                cur = cur.getNext();
            } // end while
        } catch (Exception e) {
            System.out.println("Wrong date format");
        } // end try-catch
    } // end ordersBetweenDates

    static void commonReviewedProducts(Scanner sc) {
        System.out.print("First customer ID: ");
        int c1 = sc.nextInt();
        sc.nextLine();
        System.out.print("Second customer ID: ");
        int c2 = sc.nextInt();
        sc.nextLine();

        boolean found = false;
        Node<Product> pNode = products.getHead();

        while (pNode != null) {
            Product p = pNode.getData();
            boolean aReviewed = false, bReviewed = false;
            Node<Review> rNode = p.getReviews().getHead();

            while (rNode != null) {
                int cid = rNode.getData().getCustomerId();
                if (cid == c1) aReviewed = true;
                if (cid == c2) bReviewed = true;
                if (aReviewed && bReviewed) break;
                rNode = rNode.getNext();
            } // end while reviews

            if (aReviewed && bReviewed && p.getAverageRating() > 4.0) {
                System.out.println(p.getName() + " (avg=" + p.getAverageRating() + ")");
                found = true;
            } // end if

            pNode = pNode.getNext();
        } // end while

        if (!found) {
            System.out.println("No common reviewed products above 4.0");
        } // end if
    } // end commonReviewedProducts

    // ------------------- LOADERS -------------------

    static void loadProducts(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] c = line.split(",");
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

    static void loadCustomers(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] c = line.split(",");
                int id = Integer.parseInt(c[0]);
                String name = c[1];
                String email = c[2];
                customers.add(new Customer(id, name, email));
            } // end while
        } catch (Exception e) {
            System.out.println("Customers load error: " + e.getMessage());
        } // end try-catch
    } // end loadCustomers

    static void loadOrders(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine();
            if (header == null) { return; } // end if empty

            // Map header names (orderId, customerId, orderDate/date, items/productIds/products)
            String[] h = header.split(",", -1);
            for (int i = 0; i < h.length; i++) {
                h[i] = h[i].toLowerCase(); // normalize only by case
            } // end for

            int idxOrderId = -1, idxCustomerId = -1, idxDate = -1, idxItems = -1;
            for (int i = 0; i < h.length; i++) {
                String k = h[i];
                if (k.equals("orderid")) idxOrderId = i;
                else if (k.equals("customerid")) idxCustomerId = i;
                else if (k.equals("orderdate") || k.equals("date")) idxDate = i;
                else if (k.equals("items") || k.equals("productids") || k.equals("products")) idxItems = i;
            } // end for

            if (idxDate == -1 || idxItems == -1) {
                System.err.println("Orders load error: header must include orderDate/date and productIds/items.");
                return; // end if missing required columns
            } // end if

            String line;
            int autoId = 1;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) { continue; } // end if
                String[] c = line.split(",", -1);

                // order id
                int orderId = (idxOrderId >= 0 && idxOrderId < c.length && c[idxOrderId].length() > 0)
                        ? Integer.parseInt(c[idxOrderId]) : autoId++;

                // customer id (optional)
                Integer custId = (idxCustomerId >= 0 && idxCustomerId < c.length && c[idxCustomerId].length() > 0)
                        ? Integer.parseInt(c[idxCustomerId]) : null;

                // date: accept dd/MM/yyyy or d/M/yyyy
                Date date;
                try {
                    String ds = (idxDate < c.length) ? c[idxDate] : "";
                    ds = ds.replace("\r", "").replace("\"", "").replace(" ", ""); // normalize a bit (no trim)
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy").parse(ds);
                    } catch (Exception e1) {
                        date = new SimpleDateFormat("d/M/yyyy").parse(ds);
                    }
                } catch (Exception e) {
                    System.err.println("Orders load error: Unparseable date: \"" + c[idxDate] + "\"");
                    continue; // skip bad row
                } // end try-catch date

                // build order
                Customer cust = (custId == null) ? null : findCustomerById(custId);
                Order order = new Order(orderId, cust, date);

                // items
                String items = (idxItems < c.length) ? c[idxItems] : "";
                if (items.length() > 0) {
                    String[] pairs = items.split(";");
                    for (int j = 0; j < pairs.length; j++) {
                        String token = pairs[j];
                        if (token.length() == 0) { continue; } // end if
                        // allow "101" or "101x2"
                        String[] pq = token.split("x");
                        int pid = Integer.parseInt(pq[0]);
                        int qty = (pq.length > 1) ? Integer.parseInt(pq[1]) : 1;
                        Product p = findProductById(pid);
                        if (p != null) { order.addProduct(p, qty); } // end if
                    } // end for tokens
                } // end if items non-empty

                orders.add(order);
                if (cust != null) { cust.placeOrder(order); } // end if link
            } // end while
        } catch (Exception e) {
            System.err.println("Orders load error: " + e.getMessage());
        } // end try-catch
    } // end loadOrders


    static void loadReviews(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] c = line.split(",");
                int reviewId = Integer.parseInt(c[0]);
                int productId = Integer.parseInt(c[1]);
                int customerId = Integer.parseInt(c[2]);
                int rating = Integer.parseInt(c[3]);
                String comment = c.length > 4 ? c[4] : "";
                Product p = findProductById(productId);
                Customer u = findCustomerById(customerId);
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

    // ------------------- HELPERS -------------------

    static Product findProductById(int id) {
        Node<Product> cur = products.getHead();
        while (cur != null) {
            if (cur.getData().getProductId() == id) return cur.getData();
            cur = cur.getNext();
        } // end while
        return null;
    } // end findProductById

    static Customer findCustomerById(int id) {
        Node<Customer> cur = customers.getHead();
        while (cur != null) {
            if (cur.getData().getCustomerId() == id) return cur.getData();
            cur = cur.getNext();
        } // end while
        return null; 
    } // end findCustomerById
} // end Main class
