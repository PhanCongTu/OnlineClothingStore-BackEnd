package com.example.tuonlineclothingstore.services.product;

import com.example.tuonlineclothingstore.dtos.Category.CategoryDto;
import com.example.tuonlineclothingstore.dtos.Product.CreateProductDto;
import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import com.example.tuonlineclothingstore.dtos.Product.UpdateProductDto;
import com.example.tuonlineclothingstore.entities.Category;
import com.example.tuonlineclothingstore.entities.Product;
import com.example.tuonlineclothingstore.exceptions.InvalidException;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.ProductRepository;
import com.example.tuonlineclothingstore.services.category.ICategoryService;
import com.example.tuonlineclothingstore.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    private final ICategoryService iCategoryService;
    private ModelMapper modelMapper;

    @Override
    public Page<ProductDto> filter(String searchText, long categoryId, int page, int size,
                                   String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        Page<Product> products;
        if (categoryId == 0) {
            products = productRepository.findByProductNameContainingIgnoreCaseAndIsActive(searchText, true, pageable);
        } else {
            CategoryDto categoryDto = iCategoryService.getCategoryById(categoryId);
            if (categoryDto == null)
                throw new NotFoundException("Cant find category!");
            products = productRepository.
                    findByProductNameContainingAndCategoryAllIgnoreCaseAndIsActive(searchText, modelMapper.map(categoryDto, Category.class),true, pageable);
        }
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public ProductDto getProductById(Long ProductId) {
        Optional<Product> ProductOp = productRepository.findById(ProductId);
        if (!ProductOp.isPresent())
            throw new NotFoundException("Cant find Product!");
        return modelMapper.map(ProductOp.get(), ProductDto.class);
    }

    @Override
    public ProductDto createProduct(CreateProductDto productDto) {
        CategoryDto categoryDto = iCategoryService.getCategoryById(productDto.getCategory().getId());
        if (categoryDto.getIsDeleted()) {
            throw new InvalidException("Category has been deleted !");
        }
        productDto.setCategory(categoryDto);
        return modelMapper.map(productRepository.save(modelMapper.map(productDto, Product.class)), ProductDto.class);
    }


    // Cập nhật lại Product (chỉ cập nhật những thuộc tính muốn thay đổi)
//    @Override
//    public ProductDto patchProduct(Long id, Map<Object, Object> ProductDto) {
//        Optional<Product> existingProduct = productRepository.findById(id);
//        if (!existingProduct.isPresent()) throw new NotFoundException("Can not found product !Unable to update Product!");
//
//        ProductDto.forEach((key, value) -> {
//            Field field = ReflectionUtils.findField(Product.class, (String) key);
//            field.setAccessible(true);
//
//            if(key.equals("category")){
//                // Instantiate a new Gson instance.
//                Gson gson = new Gson();
//                JsonParser parser = new JsonParser();
//                String json = gson.toJson(value, LinkedHashMap.class); // json is a json String
//                JsonObject categoryDtoJsonObject = (JsonObject) parser.parse(json);
//                CategoryDto categorydto = iCategoryService.getCategoryById(gson.fromJson(categoryDtoJsonObject, CategoryDto.class).getId());
//                existingProduct.get().setCategory(modelMapper.map(categorydto, Category.class));
//                return;
//            }
//
//            ReflectionUtils.setField(field, existingProduct.get(), (Object) value);
//        });
//        existingProduct.get().setUpdateAt(new Date(new java.util.Date().getTime()));
//        Product updatedProduct = productRepository.save(existingProduct.get());
//        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
//
//        return updatedProductDto;
//
//    }

    // Cập nhật lại Product (Cập nhật lại toàn bộ các thuộc tính)
    @Override
    public ProductDto updateProduct(Long id, UpdateProductDto updateProductDto) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) throw new NotFoundException("Can not found product !Unable to update Product!");

//        BeanUtils.copyProperties(productDto, existingProduct);
//        modelMapper.map(productDto, existingProduct);
        existingProduct.setProductName(updateProductDto.getProductName());
        existingProduct.setDescription(updateProductDto.getDescription());
        existingProduct.setQuantity(updateProductDto.getQuantity());
        existingProduct.setPrice(updateProductDto.getPrice());
        CategoryDto newCategoryDto = iCategoryService.getCategoryById(updateProductDto.getCategory().getId());
        Category newCategory = modelMapper.map(newCategoryDto, Category.class);
        existingProduct.setCategory(newCategory);

        existingProduct.setUpdateAt(new Date(new java.util.Date().getTime()));
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);

    }

    @Override
    public void changeStatus(Long ProductId) {
        Optional<Product> existingProduct = productRepository.findById(ProductId);
        if (!existingProduct.isPresent()) throw new NotFoundException("Unable to dalete Product!");

        existingProduct.get().setIsActive(!existingProduct.get().getIsActive());
        existingProduct.get().setUpdateAt(new Date(new java.util.Date().getTime()));
        productRepository.save(existingProduct.get());
    }

    @Override
    public List<ProductDto> getTop8ProductBySold() {
        List<Product> Products = productRepository.findTop8ByOrderBySoldDesc();
        List<ProductDto> ProductDtos = new ArrayList<>();
        for (Product Product : Products
        ) {
            ProductDto ProductDto = modelMapper.map(Product, ProductDto.class);
            ProductDtos.add(ProductDto);
        }
        if (ProductDtos.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Do not find any product");
        }
        return ProductDtos;
    }


    @Override
    public List<ProductDto> getTop8NewProducts() {
        List<Product> Products = productRepository.findTop8ByOrderByCreateAtDesc();
        List<ProductDto> ProductDtos = new ArrayList<>();
        for (Product Product : Products
        ) {
            ProductDto ProductDto = modelMapper.map(Product, ProductDto.class);
            ProductDtos.add(ProductDto);
        }
        if (ProductDtos.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Do not find any product");
        }
        return ProductDtos;
    }

    @Override
    public List<ProductDto> getProductByCategoryId(long category) {
        List<Product> products = productRepository.findByCategoryId(category);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDtos.add(productDto);
        }
        if (productDtos.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Do not find any product");
        }
        return productDtos;
    }

}
