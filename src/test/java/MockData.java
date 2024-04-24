import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.orders.entities.ImportOrder;
import com.example.junit_test.modules.orders.entities.ImportOrderProduct;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.suppliers.entities.Supplier;

import java.util.List;

public class MockData {
  public static List<Category> getMockDataCategories() {
    return List.of(
            Category.builder().id(100).name("Phone").build(),
            Category.builder().id(101).name("Clothes").build(),
            Category.builder().id(102).name("Fruits").build()
    );
  }

  public static List<Product> getMockDataProducts() {
    return List.of(
            Product.builder().id(100).name("Iphone 11").image("http").quantity(2).price(10000000).categoryId(100).build(),
            Product.builder().id(102).name("Iphone 12").image("http").quantity(3).price(20000000).categoryId(101).build(),
            Product.builder().id(103).name("Iphone 13").image("http").quantity(4).price(30000000).categoryId(102).build()
    );
  }

  public static List<Supplier> getMockDataSuppliers() {
    return List.of(
            Supplier.builder().id(100).email("ptd@gmail.com").name("Pham Tung Duong").taxCode("TD632").address("Thanh Hoa").phoneNumber("0969613858").note("Toi La Duong Ne").build(),
            Supplier.builder().id(101).email("qnn@gmail.com").name("Nguyen Nhu Quynh").taxCode("NQ632").address("Ha Noi").phoneNumber("0192321312").note("Toi La Quynh Ne").build(),
            Supplier.builder().id(102).email("bhs@gmail.com").name("Bui Hong Son").taxCode("HS632").address("Huyen").phoneNumber("0912309122").note("Toi La Son Ne").build(),
            Supplier.builder().id(103).email("dtnh@gmail.com").name("Dam Trong Ngoc Ha").taxCode("NH632").address("Hung Yen").phoneNumber("0912375782").note("Toi La Ha Ne").build(),
            Supplier.builder().id(104).email("pmd@gmail.com").name("Pham Minh Diep").taxCode("MD632").address("Thanh Hoa").phoneNumber("0901321882").note("Toi La Diep Ne").build()
    );
  }

  public static ImportOrder getMockDataImportOrder() {
    ImportOrder importOrder = ImportOrder.builder().code("TD632").note("Order").supplierId(100).employeeId(1).build();
    List<ImportOrderProduct> importOrderProducts = List.of(
            ImportOrderProduct.builder().quantity(10).importPrice(100L).productId(100).importOrder(importOrder).build(),
            ImportOrderProduct.builder().quantity(100).importPrice(90L).productId(101).importOrder(importOrder).build()
    );
    importOrder.setImportOrderProducts(importOrderProducts);
    return importOrder;
  }
}