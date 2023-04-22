package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.CartDto;
import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.services.cart.ICartService;
import com.example.tuonlineclothingstore.services.product.IProductService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("http://localhost:3000")

public class CartController {
    @Autowired
    ICartService iCartService;
    @Autowired
    IProductService iproductService;

    @Autowired
    IUserService iUserService;

    /***
     *  Controller dành cho admin để quản lý giỏ hàng của các user
     * @param userId : Truyền vào user ID
     * @return : Trả về danh sách các giỏ hàng của user
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Lấy tất cả các cart của user")
    public ResponseEntity<List<CartDto>> getAllCart(@PathVariable("userId") Long userId) {
        List<CartDto> listCart = iCartService.getAllCartByUserId(userId);
        if (listCart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCart, HttpStatus.OK);
    }

    /***
     *  Controller của user để lấy tất cả giỏ hàng của bản thân (người đang đăng nhập)
     * @param principal : Lấy từ token
     * @return : Trả về danh sách các giỏ hàng của user
     */
    @GetMapping("/my-cart")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Lấy tất cả các cart của user")
    public ResponseEntity<List<CartDto>> getMyCart(Principal principal) {
        UserDto userDto = iUserService.getUserByUserName(principal.getName());
        List<CartDto> listCart = iCartService.getAllCartByUserId(userDto.getId());
        if (listCart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCart, HttpStatus.OK);
    }

    /***
     *  Chỉ user mới có quyền cập nhật lại giỏ hàng của họ
     * @param productId : ID sản phẩm muốn cập nhật lại số lượng
     * @param quantity : Số lượng mới
     * @param principal : lấy từ token
     * @return
     */
    @ApiOperation(value = "Thêm cart mới hoặc cập nhật lại số lượng product nếu đã có trong cart")
    @PostMapping("/add-update")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<CartDto> createCategory(@RequestParam("productId") Long productId,
                                                  @RequestParam("quantity") int quantity,
                                                  @RequestParam("size") String size,
                                                  Principal principal) {
        UserDto userDto = iUserService.getUserByUserName(principal.getName());
        ProductDto productDto = iproductService.getProductById(productId);
        CartDto newCartDto = new CartDto();
        newCartDto.setQuantity(quantity);
        newCartDto.setProduct(productDto);
        newCartDto.setSize(size);
        CartDto savedCartDto = iCartService.addOrUpdateCart(newCartDto, userDto.getId());
        return new ResponseEntity<>(savedCartDto, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Xóa cart theo id")
    @DeleteMapping("/delete/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteCategory(@PathVariable("cartId") Long cartId) {
        iCartService.deleteCart(cartId);
        return new ResponseEntity<>("Deleted successfully!!", HttpStatus.OK);
    }

    /***
     * Controller dành cho admin
     * @param userId : ID của user cần đếm số lượng cart
     * @return : Số lượng cart
     */
    @ApiOperation(value = "Lấy sổ lượng cart của user")
    @GetMapping("/count/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> countAllCart(@PathVariable Long userId){
        return new ResponseEntity<>(iCartService.countAllCartByUserId(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Lấy sổ lượng cart của bản thân")
    @GetMapping("/count")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> countMyCart(Principal principal){
        UserDto userDto = iUserService.getUserByUserName(principal.getName());
        return new ResponseEntity<>(iCartService.countAllCartByUserId(userDto.getId()), HttpStatus.OK);
    }

}
