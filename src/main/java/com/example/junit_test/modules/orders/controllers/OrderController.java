package com.example.junit_test.modules.orders.controllers;


import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.ExcelHelper;
import com.example.junit_test.modules.orders.entities.ImportOrder;
import com.example.junit_test.modules.orders.services.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders")
@Validated
public class OrderController {
  private final OrderService orderService;

  @GetMapping()
  @Operation(
          summary = "",
          parameters = {
                  @Parameter(
                          in = ParameterIn.QUERY,
                          name = "status",
                          schema = @Schema(
                                  type = "boolean",
                                  allowableValues = {"true", "false"},
                                  defaultValue = "true"
                          ),
                          description = "Trạng thái đơn hàng"
                  )
          }
  )
  public ResponseEntity<SystemResponse<ResponsePage<ImportOrder>>> list(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(required = false) Boolean status,
          @RequestParam(defaultValue = "") String keySearch

  ) {
    return orderService.list(page, size, status, keySearch);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<SystemResponse<ImportOrder>> getById(@PathVariable Integer orderId) {
    return orderService.getById(orderId);
  }

  @PostMapping
  public ResponseEntity<SystemResponse<Boolean>> create(@Valid @RequestBody ImportOrder importOrder, Errors errors) {
    return orderService.create(importOrder);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SystemResponse<Boolean>> update(@PathVariable Integer id, @Valid @RequestBody ImportOrder importOrder, Errors errors) {
    return orderService.update(id, importOrder);
  }


  @PutMapping("/{orderId}/pay")
  public ResponseEntity<SystemResponse<Boolean>> updateStatus(@PathVariable Integer orderId) {
    return orderService.updateStatus(orderId);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<SystemResponse<Boolean>> delete(@PathVariable Integer orderId) {
    return orderService.delete(orderId);
  }

  @PostMapping(path = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Hidden
  public ResponseEntity<SystemResponse<String>> importFile(@RequestPart("file") MultipartFile file) {
    String message = "";
    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        orderService.importFile(file);
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return Response.badRequest(HttpStatus.OK.hashCode(), message);
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return Response.badRequest(HttpStatus.EXPECTATION_FAILED.hashCode(), message);
      }
    }
    message = "Please upload an excel file!";
    return Response.badRequest(HttpStatus.BAD_REQUEST.hashCode(), message);
  }
}
