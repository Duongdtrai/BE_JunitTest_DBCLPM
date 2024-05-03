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
    return Supplier.builder().email("ptd@gmail.com").name("Pham Tung Duong").taxCode("8123281221").address("Ha Noi").phoneNumber("0912791272").note("Toi La Duong Ne").build();
  }

  public static Supplier validSupplierSameNameAndAddressAndPhoneNumber() {
    return Supplier.builder().email("qnn@gmail.com").name("Nguyen Nhu Quynh").taxCode("NQ632").address("Ha Noi").phoneNumber("0192321312").note("Toi La Quynh Ne").build();
  }

  public static Supplier invalidRecord_missingEmail() {
    return Supplier.builder()
            .id(101)
            .name("Pham Tung Duong")
            .taxCode("TD632")
            .address("Thanh Hoa")
            .phoneNumber("0969613858")
            .note("Toi La Duong Ne")
            .isDeleted(false)
            .build();
  }

  public static Supplier invalidRecord_missingName() {
    return Supplier.builder()
            .id(101)
            .email("ptd@gmail.com")
            .taxCode("TD632")
            .address("Thanh Hoa")
            .phoneNumber("0969613858")
            .note("Toi La Duong Ne")
            .isDeleted(false)
            .build();
  }

  public static Supplier invalidRecord_missingTaxCode() {
    return Supplier.builder()
            .id(101)
            .email("ptd@gmail.com")
            .name("Pham Tung Duong")
            .address("Thanh Hoa")
            .phoneNumber("0969613858")
            .note("Toi La Duong Ne")
            .isDeleted(false)
            .build();
  }

  public static Supplier invalidRecord_missingAddress() {
    return Supplier.builder()
            .id(101)
            .email("ptd@gmail.com")
            .name("Pham Tung Duong")
            .taxCode("TD632")
            .phoneNumber("0969613858")
            .note("Toi La Duong Ne")
            .isDeleted(false)
            .build();
  }

  public static Supplier invalidRecord_missingPhoneNumber() {
    return Supplier.builder()
            .id(101)
            .email("ptd@gmail.com")
            .name("Pham Tung Duong")
            .taxCode("TD632")
            .address("Thanh Hoa")
            .note("Toi La Duong Ne")
            .isDeleted(false)
            .build();
  }

  public static Supplier invalidRecord_missingNote() {
    return Supplier.builder()
            .id(101)
            .email("ptd@gmail.com")
            .name("Pham Tung Duong")
            .taxCode("TD632")
            .address("Thanh Hoa")
            .phoneNumber("0969613858")
            .isDeleted(false)
            .build();
  }

  public static Supplier invalidRecord_phoneNumberAndEmail() {
    return Supplier.builder()
            .id(101)
            .email("ptd")
            .name("Pham Tung Duong")
            .taxCode("TD632")
            .address("Thanh Hoa")
            .phoneNumber("123")
            .note("Toi La Duong Ne")
            .isDeleted(false)
            .build();
  }

  public static Supplier validSupplierUpdate() {
    return Supplier.builder()
            .email("qnn@gmail.com")
            .name("Nguyen Nhu Quynh")
            .taxCode("NQ632")
            .address("Ha Noi")
            .phoneNumber("0192321312")
            .note("Toi La Quynh Ne")
            .build();
  }
}
