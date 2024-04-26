package com.example.junit_test.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class PrimaryHealthIndicator implements HealthIndicator {
  private static final long MINIMUM_DISK_SPACE_REQUIRED = 1024 * 1024 * 1024;
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;

  @Override
  public Health health() {
    // Kiểm tra trạng thái của cơ sở dữ liệu
    boolean dbStatus = checkDatabaseStatus();
    // Kiểm tra tài nguyên hệ thống
    boolean systemResourcesStatus = checkSystemResources();
    if (dbStatus && systemResourcesStatus) {
      return Health.up()
              .withDetail("API", "OK")
              .withDetail("Database", "OK")
              .withDetail("System Resources", "OK")
              .build();
    } else {
      return Health.down()
              .withDetail("Database", dbStatus ? "OK" : "Failed")
              .withDetail("System Resources", systemResourcesStatus ? "OK" : "Failed")
              .build();
    }
  }

  private boolean checkDatabaseStatus() {
    // Thực hiện kiểm tra trạng thái của cơ sở dữ liệu
    // Ví dụ: Kết nối đến cơ sở dữ liệu và kiểm tra kết nối
    boolean dbStatus = false;
    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      dbStatus = true;
    } catch (SQLException e) {
      dbStatus = false;
    }
    return dbStatus;
  }

  private boolean checkSystemResources() {
    // Kiểm tra tài nguyên hệ thống như CPU, bộ nhớ, dung lượng ổ đĩa, ...
    // Ở đây chỉ là một ví dụ đơn giản, bạn có thể thêm logic phức tạp hơn
    boolean systemResourcesStatus = true;
    // Kiểm tra dung lượng ổ đĩa
    File root = new File("/");
    long freeSpace = root.getFreeSpace();
    long totalSpace = root.getTotalSpace();
    long usableSpace = totalSpace - freeSpace;
    // Kiểm tra xem dung lượng ổ đĩa còn đủ hay không
    if (usableSpace < MINIMUM_DISK_SPACE_REQUIRED) {
      systemResourcesStatus = false;
    }
    // Kiểm tra tài nguyên hệ thống khác ở đây
    return systemResourcesStatus;
  }
}
