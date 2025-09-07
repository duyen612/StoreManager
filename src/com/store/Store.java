package com.store;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Store {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product p) {
        products.add(p);
    }

    public Product findById(int id) {
        for (Product p : products) if (p.getId() == id) return p;
        return null;
    }

    public void updateProduct(int id, double newPrice, int newQuantity, String newName, String newDesc) {
        Product p = findById(id);
        if (p != null) {
            p.setPrice(newPrice);
            p.setQuantity(newQuantity);
            p.setName(newName);
            p.setDescription(newDesc);
            System.out.println("Đã cập nhật sản phẩm id=" + id);
        } else System.out.println("Không tìm thấy sản phẩm id=" + id);
    }

    public void showProductsByPrice(boolean ascending) {
        Comparator<Product> cmp = Comparator.comparing(Product::getPriceAfterDiscount);
        if (!ascending) cmp = cmp.reversed();
        products.stream().sorted(cmp).forEach(System.out::println);
    }

    public void showProductsByCategory(String category) {
        products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .forEach(System.out::println);
    }

    public void totalValueByCategory() {
        Map<String, Double> map = new HashMap<>();
        for (Product p : products) {
            map.put(p.getCategory(), map.getOrDefault(p.getCategory(), 0.0) + p.getTotalValue());
        }
        map.forEach((cat, val) -> System.out.printf("Danh mục: %s -> Tổng giá trị tồn kho: %,.0f%n", cat, val));
    }

    public void applyDiscount(int id, double discountPercent) {
        Product p = findById(id);
        if (p != null) {
            p.setDiscount(discountPercent);
            System.out.println("Đã áp dụng giảm giá " + discountPercent + "% cho sản phẩm " + p.getName());
        } else System.out.println("Không tìm thấy sản phẩm id=" + id);
    }

    public void orderProduct(int id, int quantity) {
        Product p = findById(id);
        if (p == null) { System.out.println("Không tìm thấy sản phẩm!"); return; }
        if (p.getQuantity() < quantity) {
            System.out.println("Không đủ hàng. Hiện có: " + p.getQuantity());
            return;
        }
        double total = p.getPriceAfterDiscount() * quantity;
        p.setQuantity(p.getQuantity() - quantity);
        System.out.printf("Đặt hàng thành công: %s x%d -> Tổng: %,.0f%n", p.getName(), quantity, total);
    }

    public void listAllProducts() {
        if (products.isEmpty()) System.out.println("Danh sách rỗng.");
        products.forEach(System.out::println);
    }

    // Lưu file CSV đơn giản: id,name,category,description,price,quantity,discount
    public void saveToCsv(String path) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Product p : products) {
                String line = String.join(",",
                        String.valueOf(p.getId()),
                        escapeCsv(p.getName()),
                        escapeCsv(p.getCategory()),
                        escapeCsv(p.getDescription()),
                        String.valueOf(p.getPrice()),
                        String.valueOf(p.getQuantity()),
                        String.valueOf(p.getDiscount())
                );
                pw.println(line);
            }
            System.out.println("Lưu thành công vào " + path);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu: " + e.getMessage());
        }
    }

    public void loadFromCsv(String path) {
        products.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 7) {
                    int id = Integer.parseInt(parts[0]);
                    String name = unescapeCsv(parts[1]);
                    String cat = unescapeCsv(parts[2]);
                    String desc = unescapeCsv(parts[3]);
                    double price = Double.parseDouble(parts[4]);
                    int qty = Integer.parseInt(parts[5]);
                    double disc = Double.parseDouble(parts[6]);
                    Product p = new Product(id, name, cat, desc, price, qty);
                    p.setDiscount(disc);
                    products.add(p);
                }
            }
            System.out.println("Đã load dữ liệu từ " + path);
        } catch (IOException e) {
            System.out.println("Lỗi khi load: " + e.getMessage());
        }
    }

    // giúp escape/restore thay dấu , bằng ;; để không bị lỗi khi ghi/đọc file.
    private String escapeCsv(String s) {
        return s.replace("\"", "\"\"").replace(",", ";;"); // đơn giản cho bài tập
    }
    private String unescapeCsv(String s) {
        return s.replace(";;", ",").replace("\"\"", "\"");
    }
}
