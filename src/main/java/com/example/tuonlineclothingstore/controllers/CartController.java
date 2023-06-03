package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.CartDto;
import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.services.cart.ICartService;
import com.example.tuonlineclothingstore.services.product.IProductService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")

public class CartController {
    @Autowired
    ICartService iCartService;
    @Autowired
    IProductService iproductService;

    @Autowired
    IUserService iUserService;

    /***
     *
     * @param userId: ID người dùng muốn xem cart
     * @param page: Số thứ tự của trang
     * @param column: Field muốn sắp xếp theo
     * @param size: Số lượng kết quả của 1 trang
     * @param sortType: sắp xếp theo:
     *                 true => tăng dần,
     *                 false => giảm dần
     * @return: Trả về 1 page các product dựa trên các thông tin đầu vào
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CartDto>> getAllCartByUser(@PathVariable("userId") Long userId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "createAt") String column,
                                                          @RequestParam(defaultValue = "12") int size,
                                                          @RequestParam(defaultValue = "true") boolean sortType) {
        String sort = (sortType ? "asc" : "desc") ;
        return new ResponseEntity<>(iCartService.getAllCartByUserId(userId, page, size, sort, column), HttpStatus.OK);
    }

    /***
     * Lấy cart của người đang đăng nhập
     * @param principal: lấy thông tin người đang đăng nhập từ JWT
     * @param page: Số thứ tự của trang
     * @param column: Field muốn sắp xếp theo
     * @param size: Số lượng kết quả của 1 trang
     * @param sortType: sắp xếp theo:
     *                 true => tăng dần,
     *                 false => giảm dần
     * @return: Trả về 1 page các product dựa trên các thông tin đầu vào
     */
    @GetMapping("/my-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<CartDto>> getMyCart(Principal principal,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "createAt") String column,
                                                   @RequestParam(defaultValue = "12") int size,
                                                   @RequestParam(defaultValue = "true") boolean sortType) {
        UserDto userDto = iUserService.getUserByUserName(principal.getName());
        String sort = (sortType ? "asc" : "desc");
        return new ResponseEntity<>(iCartService.getAllCartByUserId(userDto.getId(), page, size, sort, column), HttpStatus.OK);

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
