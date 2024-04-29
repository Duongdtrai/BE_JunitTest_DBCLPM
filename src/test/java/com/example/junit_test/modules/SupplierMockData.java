package com.example.junit_test.modules;

import com.example.junit_test.modules.suppliers.entities.Supplier;

import java.util.List;

public class SupplierMockData {
    public static List<Supplier> validSuppliers() {
        return List.of(
                Supplier.builder().id(1).email("ptd@gmail.com").name("Pham Tung Duong").taxCode("TD632").address("Thanh Hoa").phoneNumber("0969613858").note("Toi La Duong Ne").build(),
                Supplier.builder().id(2).email("qnn@gmail.com").name("Nguyen Nhu Quynh").taxCode("NQ632").address("Ha Noi").phoneNumber("0192321312").note("Toi La Quynh Ne").build(),
                Supplier.builder().id(3).email("bhs@gmail.com").name("Bui Hong Son").taxCode("HS632").address("Huyen").phoneNumber("0912309122").note("Toi La Son Ne").build(),
                Supplier.builder().id(4).email("dtnh@gmail.com").name("Dam Trong Ngoc Ha").taxCode("NH632").address("Hung Yen").phoneNumber("0912375782").note("Toi La Ha Ne").build(),
                Supplier.builder().id(5).email("pmd@gmail.com").name("Pham Minh Diep").taxCode("MD632").address("Thanh Hoa").phoneNumber("0901321882").note("Toi La Diep Ne").build()
        );
    }

    public static Supplier validSupplier() {
        return Supplier.builder().id(1).email("qnn@gmail.com").name("Nguyen Nhu Quynh").taxCode("NQ632").address("Ha Noi").phoneNumber("0192321312").note("Toi La Quynh Ne").build();
    }
}
