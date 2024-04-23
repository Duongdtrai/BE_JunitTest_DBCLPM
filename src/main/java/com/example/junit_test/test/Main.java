package com.example.junit_test.test;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhập số lượng phần tử của dãy: ");
    int n = scanner.nextInt();
    if (n <= 0) {
      System.out.println("Dãy không có số nào.");
      return;
    }

    int sum = 0;
    System.out.println("Nhập các phần tử của dãy:");
    for (int i = 0; i < n; i++) {
      int num = scanner.nextInt();
      if (num > 0 && num % 2 == 0) {
        sum += num;
      }
    }

    if (sum == 0) {
      System.out.println("Không có số chẵn dương nào trong dãy.");
    } else {
      System.out.println("Tổng các số chẵn dương trong dãy là: " + sum);
    }
  }


  public static String calculateEvenPositiveSum(int[] array) {
    if (array.length <= 0) {
      return "Dãy không có số nào";
    }
    int sum = 0;
    boolean found = false;
    for (int num : array) {
      if (num > 0 && num % 2 == 0) {
        sum += num;
        found = true;
      }
    }
    if (!found) {
      return "Không có số chẵn dương nào trong dãy.";
    }
    return "Tổng các số chẵn dương trong dãy là: " + sum;
  }
}
