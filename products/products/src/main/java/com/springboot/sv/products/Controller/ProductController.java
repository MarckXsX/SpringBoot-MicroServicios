package com.springboot.sv.products.Controller;

import com.springboot.sv.products.dto.GenericResponseDto;
import com.springboot.sv.products.dto.ProductDto;
import com.springboot.sv.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        GenericResponseDto<List<ProductDto>> response = productService.getAllProducts();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
        GenericResponseDto<ProductDto> response = productService.getProductById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
