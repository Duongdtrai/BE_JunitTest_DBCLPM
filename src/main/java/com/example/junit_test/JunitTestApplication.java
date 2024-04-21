package com.example.junit_test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class JunitTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(JunitTestApplication.class, args);
    }

    public static int calculateEvenPositiveSum(int[] array) {
        int sum = 0;
        for (int num : array) {
            if (num > 0 && num % 2 == 0) {
                sum += num;
            }
        }
        return sum;
    }
}
