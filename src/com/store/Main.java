package com.store;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        // Thêm dữ liệu mẫu
        store.addProduct(new Product(1, "Áo thun", "Thời trang", "Áo cotton", 150000, 50));
        store.addProduct(new Product(2, "Giày sneaker", "Thời trang", "Thể thao", 500000, 20));
        store.addProduct(new Product(3, "Điện thoại", "Điện tử", "Smartphone", 8000000, 10));

        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ SẢN PHẨM ===");
            System.out.println("1. Hiển thị tất cả");
            System.out.println("2. Hiển thị theo giá (tăng dần)");
            System.out.println("3. Hiển thị theo danh mục");
            System.out.println("4. Tính tổng giá trị tồn kho theo danh mục");
            System.out.println("5. Cập nhật sản phẩm (id)");
            System.out.println("6. Áp dụng giảm giá cho sản phẩm");
            System.out.println("7. Đặt hàng");
            System.out.println("8. Lưu vào file CSV");
            System.out.println("9. Load từ file CSV");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            choice = Integer.parseInt(sc.nextLine().trim());

            switch (choice) {
                case 1 -> store.listAllProducts();
                case 2 -> store.showProductsByPrice(true);
                case 3 -> {
                    System.out.print("Nhập danh mục: ");
                    String c = sc.nextLine();
                    store.showProductsByCategory(c);
                }
                case 4 -> store.totalValueByCategory();
                case 5 -> {
                    System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Giá mới: "); double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Số lượng: "); int qty = Integer.parseInt(sc.nextLine());
                    System.out.print("Tên mới: "); String name = sc.nextLine();
                    System.out.print("Mô tả mới: "); String desc = sc.nextLine();
                    store.updateProduct(id, price, qty, name, desc);
                }
                case 6 -> {
                    System.out.print("ID sản phẩm: "); int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Giảm giá (%): "); double d = Double.parseDouble(sc.nextLine());
                    store.applyDiscount(id, d);
                }
                case 7 -> {
                    System.out.print("ID sản phẩm: "); int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Số lượng đặt: "); int q = Integer.parseInt(sc.nextLine());
                    store.orderProduct(id, q);
                }
                case 8 -> {
                    System.out.print("Đường dẫn file lưu (ví dụ data.csv): "); String path = sc.nextLine();
                    store.saveToCsv(path);
                }
                case 9 -> {
                    System.out.print("Đường dẫn file load (ví dụ data.csv): "); String path = sc.nextLine();
                    store.loadFromCsv(path);
                }
                case 0 -> System.out.println("Kết thúc.");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);

        sc.close();
    }
}
