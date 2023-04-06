package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.CartDto;
import com.example.tuonlineclothingstore.dtos.ProductDto;
import com.example.tuonlineclothingstore.repositories.CartRepository;
import com.example.tuonlineclothingstore.services.cart.ICartService;
import com.example.tuonlineclothingstore.services.product.IProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Api/Cart")
public class CartController {
    @Autowired
    ICartService iCartService;
    @Autowired
    IProductService iproductService;

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "Lấy tất cả các cart của user")
    public ResponseEntity<List<CartDto>> getAllCart(@PathVariable("userId") Long userId) {
        List<CartDto> listCart = iCartService.getAllCartByUserId(userId);
        if (listCart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCart, HttpStatus.OK);
    }

    @ApiOperation(value = "Thêm cart mới")
    @PostMapping("/add")
    public ResponseEntity<CartDto> createCategory(@RequestParam("productId") Long productId,
                                                  @RequestParam("userId") Long userId,
                                                  @RequestParam("quantity") int quantity) {
        ProductDto productDto = iproductService.getProductById(productId);
        CartDto newCartDto = new CartDto();
        newCartDto.setQuantity(quantity);
        newCartDto.setProduct(productDto);
        CartDto savedCartDto = iCartService.addCart(newCartDto, userId);
        return new ResponseEntity<>(savedCartDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Cập nhật lại cart")
    @PutMapping("/update/{cartId}")
    public ResponseEntity<CartDto> updateCategory(@PathVariable("cartId")  Long cartId,
                                                  @RequestBody int quantity) {
        CartDto newCartDto = iCartService.updateCart(cartId,quantity);
        return new ResponseEntity<>(newCartDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Xóa cart theo id")
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("cartId") Long cartId) {
        iCartService.deleteCart(cartId);
        return new ResponseEntity<>("Deleted successfully!!", HttpStatus.OK);
    }

    @ApiOperation(value = "Lấy sổ lượng cart của user")
    @GetMapping("/count-all/{userId}")
    public ResponseEntity<Integer> countAllCart(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(iCartService.countAllCartByUserId(userId), HttpStatus.OK);
    }

}
