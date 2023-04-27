# Phần Backend (Java Spring Boot) của Website bán sản phẩm thời trang

Link phần frontend (ReactJS) tại [đây](https://github.com/PhanCongTu/TuOnlineClothingStore-FrontEnd.git).

## Công nghệ sử dụng
<b>Programming language: </b>  Java \
<b>Framework: </b>Spring Boot, Spring Security \
<b>Database: </b>MySQL
## Các chức năng
### Người xem (chưa đăng nhập)
- Xem các sản phẩm bán chạy và các sản phẩm mới
- Xem tất cả sản phẩm.
- Có thể tìm kiếm theo tên kết hợp loại sản phẩm và thứ tự sắp xếp tăng hoặc giảm (có phân trang).
- Xem chi tiết từng sản phẩm.
### Người dùng (đã đăng nhập)
- Bao gồm các chức năng của <b>người xem</b>.
- Thêm sản phẩm muốn mua vào giỏ hàng và cập nhật lại số lượng hoặc xóa sản phẩm trong giỏ (nếu cần).
- Tiến hành đặt hàng (tất cả các sản phẩm trong giỏ hàng).
- Xem lịch sử mua hàng (tất cả các sản phẩm trong giỏ hàng).
- Chỉnh sửa thông tin cá nhân (avatar, họ và tên, số điện thoại, email và mật khẩu).
### Quản trị viên
- Bao gồm các chức năng của <b>người xem</b>.
- Xem thông tin tất cả danh mục, sản phẩm, kích thước và hỉnh ảnh của mỗi sản phẩm, người dùng (bao gồm tìm kiếm và phân trang) .
- Xem các đơn hàng và chi tiết các sản phẩm trong đơn hàng đó (bao gồm tìm kiếm và phân trang).
- Thêm danh mục (loại sản phẩm) và sản phẩm mới.
- Thêm kích thước và hình ảnh vào sản phẩm.
- Đổi trạng thái của danh mục (hoạt động <=> không khả dụng). Sẽ không thể thêm sản phẩm vào danh mục bị "không khả dụng".
- Xóa hỉnh hành và kích thước của sản phẩm hoặc là chính sản phẩm đó.
- Đổi trạng thái của đơn hàng (Chờ xác nhận, đã chuyển hàng, đã nhận, đã hủy).
- Đổi trạng thái của người dùng (Hoạt động <=> Vô hiệu hóa). Người dùng bị "vô hiệu hóa" sẽ không thể đăng nhập được nữa.
## Cách sử dụng project
Clone project từ github và mở project trong IDE (IntelliJ IDEA).\
Mở file `application.yml` và đổi `username` và `password` của MySQL bạn đang dùng.
### Database
Mở MySQL và tạo `database` với tên `tuonlineclothingstore` và khởi chạy project trong IDE.
Kiểm tra lại MySQL xem đã tạo các table cần thiết chưa và bắt đầu thêm database.
