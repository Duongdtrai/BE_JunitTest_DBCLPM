package com.example.junit_test.modules;

import com.example.junit_test.base.middleware.responses.MetaData;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.entities.ImportOrder;
import com.example.junit_test.modules.orders.entities.ImportOrderProduct;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderMockData {
    public static ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> validResponsePage() {
        return ResponseEntity.ok(
                new SystemResponse<>(200, "Success", new ResponsePage<>(validRecord(), new MetaData(1, 1, 10, 0)))
        );
    }

    public static ResponseEntity<SystemResponse<ImportOrder>> validResponseEntity() {
        return ResponseEntity.ok(new SystemResponse<>(200, "Success", validRecord()));
    }

    public static ResponseEntity<SystemResponse<ImportOrder>> invalidResponseEntity() {
        return ResponseEntity.ok(new SystemResponse<>(400, "Bad Request", invalidRecord_missingCode()));
    }


    public static List<ImportOrder> validRecords() {
        ImportOrder importOrder1 = ImportOrder.builder().code("TD632").status(true).tax(10.0).supplierId(1).employeeId(1).build();
        List<ImportOrderProduct> importOrderProducts1 = List.of(
                ImportOrderProduct.builder().quantity(10).importPrice(100L).productId(1).importOrder(importOrder1).build(),
                ImportOrderProduct.builder().quantity(100).importPrice(90L).productId(3).importOrder(importOrder1).build()
        );
        importOrder1.setImportOrderProducts(importOrderProducts1);

        ImportOrder importOrder2 = ImportOrder.builder().code("TD633").tax(10.0).status(false).supplierId(2).employeeId(1).build();
        List<ImportOrderProduct> importOrderProducts2 = List.of(
                ImportOrderProduct.builder().quantity(10).importPrice(100L).productId(2).importOrder(importOrder2).build(),
                ImportOrderProduct.builder().quantity(100).importPrice(90L).productId(1).importOrder(importOrder2).build()
        );
        importOrder2.setImportOrderProducts(importOrderProducts2);

        return List.of(importOrder1, importOrder2);
    }

    public static ImportOrder validRecord() {
        ImportOrder importOrder = ImportOrder.builder().tax(10.0).code("TD632").status(true).note("Order").supplierId(1).employeeId(1).build();
        List<ImportOrderProduct> importOrderProducts = List.of(
                ImportOrderProduct.builder().quantity(10).importPrice(100L).productId(1).importOrder(importOrder).build(),
                ImportOrderProduct.builder().quantity(100).importPrice(90L).productId(2).importOrder(importOrder).build()
        );
        importOrder.setImportOrderProducts(importOrderProducts);
        return importOrder;
    }

    public static ImportOrder invalidRecord_missingCode() {
        return ImportOrder.builder()
                .tax(10.0)
                .supplierId(1)
                .employeeId(1)
                .build();
    }

    public static ImportOrder invalidRecord_missingTax() {
        return ImportOrder.builder()
                .code("TD632")
                .supplierId(1)
                .employeeId(1)
                .status(true)
                .build();
    }

    public static ImportOrder invalidRecord_missingSupplierId() {
        return ImportOrder.builder()
                .code("TD632")
                .tax(10.0)
                .employeeId(1)
                .status(true)
                .build();
    }

    public static ImportOrder invalidRecord_missingEmployeeId() {
        return ImportOrder.builder()
                .code("TD632")
                .tax(10.0)
                .supplierId(1)
                .status(true)
                .build();
    }


    public static ImportOrder invalidRecord_invalidTax() {
        return ImportOrder.builder()
                .code("TD632")
                .tax(-10.0)
                .supplierId(1)
                .employeeId(1)
                .status(true)
                .build();
    }

    public static ImportOrder invalidRecord_invalidSupplierId() {
        return ImportOrder.builder()
                .code("TD632")
                .tax(10.0)
                .supplierId(-1)
                .employeeId(1)
                .status(true)
                .build();
    }

    public static ImportOrder invalidRecord_invalidEmployeeId() {
        return ImportOrder.builder()
                .code("TD632")
                .tax(10.0)
                .supplierId(1)
                .employeeId(-1)
                .status(true)
                .build();
    }

    public static ImportOrder invalidRecord_missingImportOrderProduct() {
        return ImportOrder.builder()
                .code("TD632")
                .supplierId(1)
                .employeeId(1)
                .status(true)
                .importOrderProducts(new ArrayList<>())
                .build();
    }

    public static ImportOrder invalidRecord_importPriceEqualToZero() {
        ImportOrder importOrder = ImportOrder.builder().code("TD632").note("Order").status(true).supplierId(1).employeeId(1).build();
        List<ImportOrderProduct> importOrderProducts = List.of(
                ImportOrderProduct.builder().quantity(10).importPrice(0L).productId(1).importOrder(importOrder).build()
        );
        importOrder.setImportOrderProducts(importOrderProducts);
        return importOrder;
    }

 public static ImportOrder invalidRecord_quantityEqualToZero() {
        ImportOrder importOrder = ImportOrder.builder().code("TD632").note("Order").supplierId(1).status(true).employeeId(1).build();
        List<ImportOrderProduct> importOrderProducts = List.of(
                ImportOrderProduct.builder().quantity(10).importPrice(10L).productId(2).importOrder(importOrder).build() // non-existing product
        );
        importOrder.setImportOrderProducts(importOrderProducts);
        return importOrder;
    }



    public static ImportOrder invalidRecord_nonExistingProduct() {
        ImportOrder importOrder = ImportOrder.builder().code("TD632").note("Order").supplierId(1).employeeId(1).status(true).build();
        List<ImportOrderProduct> importOrderProducts = List.of(
                ImportOrderProduct.builder().quantity(10).importPrice(100L).productId(9999).importOrder(importOrder).build(), // non-existing product
                ImportOrderProduct.builder().quantity(100).importPrice(90L).productId(2).importOrder(importOrder).build()
        );
        importOrder.setImportOrderProducts(importOrderProducts);
        return importOrder;
    }

}
