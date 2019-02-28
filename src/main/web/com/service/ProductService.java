package com.service;

import com.dto.ProductExecution;
import com.entity.Product;
import com.exception.ProductOperationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public ProductExecution addProduct(Product product, MultipartFile multipartFile, List<MultipartFile> multipartFileList) throws ProductOperationException;
    public ProductExecution motifyProduct(Product product, MultipartFile multipartFile, List<MultipartFile> multipartFileList) throws ProductOperationException;
    public Product getProductById(Long productId);

    public ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
}
