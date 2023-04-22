package com.example.tuonlineclothingstore.services.productsize;

import com.example.tuonlineclothingstore.dtos.ProductSizeDto;
import com.example.tuonlineclothingstore.entities.Product;
import com.example.tuonlineclothingstore.entities.ProductSize;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.ProductSizeRepository;
import com.example.tuonlineclothingstore.services.product.IProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductSizeServiceImpl implements IProductSizeService{
    private final ProductSizeRepository productSizeRepository;
    private final IProductService IProductService;


    private ModelMapper modelMapper;
    @Override
    public List<ProductSizeDto> getAllSizesByProductId(Long productId){
        List<ProductSize> sizes = productSizeRepository.findAllByProductId(productId);
        return sizes.stream()
                .map((size) -> modelMapper.map(size, ProductSizeDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public ProductSizeDto addProductSize(ProductSizeDto productSizeDto, Long productId) {
        ProductSize newSize = modelMapper.map(productSizeDto, ProductSize.class);
        newSize.setProduct(modelMapper.map(IProductService.getProductById(productId), Product.class));
        ProductSize savedSize = productSizeRepository.save(newSize);
        ProductSizeDto savedSizeDto = modelMapper.map(savedSize, ProductSizeDto.class);
        return savedSizeDto;
    }
    @Override
    public void deleteProductSize(Long pSizeId) {
        Optional<ProductSize> existingPSize = productSizeRepository.findById(pSizeId);
        if (!existingPSize.isPresent()) throw new NotFoundException("Product Size do not exist!");
        productSizeRepository.delete(existingPSize.get());
    }
}
