# Phần Backend (Java Spring Boot) của Website bán sản phẩm thời trang

Link phần frontend (ReactJS) tại [đây](https://github.com/PhanCongTu/TuOnlineClothingStore-FrontEnd.git).

## Công nghệ sử dụng
<b>Programming language: </b>  Java \
<b>Framework: </b>Spring Boot, Spring Security:JWT Authentication & Authorisation \
<b>Database: </b>MySQL \
Link database tại [đây](https://drive.google.com/drive/folders/1Yu9GulWogS1nZt-2qiEnA8HxLjJZ2bll?usp=sharing).
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
Link database tại [đây](https://drive.google.com/drive/folders/1Yu9GulWogS1nZt-2qiEnA8HxLjJZ2bll?usp=sharing).

> Khi chạy project sẽ tự động có 3 tài khoản.

<ol>
  <li>Tài khoản user
    <ol>
      <ul>username: user01 </ul>
      <ul>password: 123</ul>
    </ol>
  </li>
<li>Tài khoản admin
    <ol>
      <ul>username: admin01</ul>
      <ul>password: 123</ul>
    </ol>
  </li>
<li>Tài khoản có cả role user và admin
    <ol>
      <ul>username: useradmin01</ul>
      <ul>password: 123</ul>
    </ol>
  </li>
</ol>

## Giao diện
### Giao diện đăng nhập, đăng ký.
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/06ff5908-ab0e-409d-a8fc-1e2806346005)
  Giao diện đăng nhập của cả người dùng (user) và người quản trị (admin). Nhập tài khoản và mật khẩu của mình rồi nhấn đăng nhập để tiến vào cửa hàng với quyền (user, admin) của tài khoản. Tích “Ghi nhớ tài khoản” để trang web ghi nhớ tài khoản và không cần phải đăng nhập trong lần vô tiếp theo.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/db1bc4f0-b063-4550-b483-ff0d56a7c0fd)
	Giao diện đăng ký tài khoản mới của người dùng (user). Chỉ có thể đăng ký tài khoản mới dành cho người dùng (user), không thể đăng ký tài khoản của người quản trị (admin). Nhập thông tin cần thiết và nhấn “Đăng ký” để đăng ký tài khoản.


### Giao diện của người xem.

![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/c512696f-de17-4571-b658-2405113a2675)
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/a68ef0d0-ccab-4d3f-84b4-e2ab2a665b6e)
	Trang chủ, cho phép người xem (chưa đăng nhập) xem 8 sản phẩm bán chạy nhất hoặc 8 sản phẩm mới nhất của cửa hàng.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/0d18b71f-c593-4cb7-99a0-93a1515afaaf)
	Trang cửa hàng, cho phép người xem xem tất cả các sản phẩm có trong cửa hàng, theo từng danh mục hoặc kết hợp với tìm kiếm theo tên sản phẩm và tùy chọn sắp xếp theo giá (tăng dần hoặc giảm dần).
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/ddfc6d21-7dd2-474f-9199-75cc4605f72d)
  Nhấn chọn vô “Xem chi tiết” ở bất kì sản phẩm nào sẽ hiện lên trang hiện thị chi tiết của sản phẩm đó. Tuy nhiên, để đặt hàng thì người xem phải đăng nhập để trở thành người dùng thì mới có thể đặt hàng được.


### Giao diện của người dùng

![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/9b6767fa-8da4-48a2-9d37-ecce788e7bf7)
	Trang chủ, trang cửa hàng của người dùng (người đã đăng nhập) sẽ giống y hệt với người xem, tuy nhiên sẽ có thêm thanh điều hướng để thực hiện thêm các chức năng của người dùng như: Tài khoản của tôi, giỏ hàng, lịch sử mua hàng và đăng xuất.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/62f85bc5-e67c-4a42-ad6d-9ca9033c9d5e)
	Trang chi tiết sản phẩm bây giờ đã có thêm chức năng thêm sản phẩm vào giỏ hàng của người dùng. Người dùng chọn kích thước (size) mong muốn và số lượng muốn đặt rồi nhấn “Thêm vào giỏ hàng” để thêm sản phẩm đó vô giỏ hàng.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/98a20d46-4291-4b61-bdb2-ce126d04c9f9)
	Trang giỏ hàng, hiển thị danh sách các sản phẩm mà người dùng đã thêm vào giỏ hàng. Người dùng hoàn toàn có thể chính sửa số lượng sản phẩm bằng cách nhấn vào mũi tên tăng hoặc giảm, hoặc xóa sản phẩm bằng cách nhấn vào biểu tượng “X”.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/524c2c1a-84a5-483a-ad37-5ed6ba0f6939)
	Trang đặt hàng, sẽ hiển thị danh sách các sản phẩm muốn đặt (các sản phẩm có trong giỏ hàng), thông tin của người dùng cũng sẽ được hiển thị để xác nhận và cho phép người dùng chỉnh sửa lại các thông tin đó trước khi tiến hàng đặt hàng. Mặc định địa chỉ sẽ chưa có gì và người sẽ bắt buộc phải nhập địa chỉ mới có thể đặt hàng.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/065bb379-f69a-4871-8223-77010ab1096d)
	Khi đã điền đầy đủ thông tin thì người dùng có thể nhấn “Đặt hàng” để tiến hành đặt hàng.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/3f41004b-9d22-40af-979c-2b3985723b64)
	Trang lịch sử mua hàng, cho phép người dùng xem lại thông tin chi tiết cũng như tình trạng các đơn hàng mà họ đã đặt.

![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/99b22f0e-9a1a-4bf6-bbf8-56465a7abde5)
	Trang “Tài khoản của tôi”, cho phép người dùng xem lại thông tin cá nhân. Nếu muốn chỉnh sửa thông tin cơ bản thì người dùng nhập thôn tin mới và nhấn “Chỉnh sửa” để thay đổi thông tin. Với ảnh đại diện cũng tương tự, nhấn “Choose File” và chọn ảnh đại diện mới và nhấn “Chỉnh sửa” để lưu. Riêng phần đổi mật khẩu, người dùng sẽ phải nhập mật khẩu cũ và 2 lần mật khẩu mới, nếu mật khẩu cũ chính xác và mật khẩu mới hợp lệ thì mới được phép đổi.
  
  
### Giao diện người quản trị

![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/f2fe2988-2cec-44df-aeab-ecc82e039100)
	Cũng tương tự như người dùng, người quản trị cũng có trang chủ, trang cửa hàng và trang chi tiết sản phẩm y hệt giao diện của người xem. Tuy nhiên, sự khác biệt nằm ở thanh điều hướng. Người quản trị có thể đến trang “Tài khoản của tôi” để chỉnh sửa thông tin cá nhân giống như người dùng. Ngoài ra, người quản trị còn có thể đến trang “Danh cho quản trị viên” để thực hiện các thao tác nghiệp vụ.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/fa1df9dd-a3df-4d7a-9908-bb4118a3707c)
	Trang “Danh mục”, liệt kê thông tin tất cả các danh mục có trong cửa hàng. Có thể tìm kiếm danh mục theo tên danh mục và sắp xếp theo trạng thái của các danh mục. Để thêm mới danh mục, quản trị viên nhập tên danh mục vào ô nhập liệu và nhấn “Thêm” để thêm danh mục mới. Nếu không muốn cho phép thêm sản phẩm mới vào danh mục nào đó, quản trị viên có thể nhấn “Đội trạng thái” để vô hiệu hóa danh mục hoặc ngược tại để tái kích hoạt danh mục.

![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/74c3405e-c7b6-4b35-945c-1de08f86cf1d)
	Trang “Sản phẩm”, liệt kê các sản phẩm có trong cửa hàng. Có thể tìm kiếm theo tên của sản phẩm, sắp xếp theo tên danh mục, giá tăng hoặc giảm. Để thêm sản phẩm mới, quản trị viên nhập và chọn các thông tin cần thiết rồi nhấn “Thêm” để thêm sản phẩm mới. Nếu muốn xóa sản phẩm thì chọn nút “Xóa”. Để thêm hình ảnh hoặc kích thước cho sản phẩm nào đó, chọn nút “Sửa” thì sẽ chuyển đến trang thêm hỉnh ảnh và kích thước.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/b9167a7b-03b2-4d3d-a0a2-15d863d806d9)
	Trang thêm hình ảnh và kích thước, quản trị viên có thể chọn biểu tượng “X” ở hình ảnh hoặc nút “Xóa” để xóa hình ảnh hoặc kích thước không mong muốn. Để thêm hình ảnh, chọn “Choose File”, chọn hỉnh ảnh muốn thêm và nhấn “Thêm ảnh” để thêm hình ảnh mới. Để thêm kích thước, nhập kích cỡ vào ô nhập liệu và nhấn “Thêm kích cỡ” để thêm kích cỡ mới cho sản phẩm đó.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/c0667db4-a71a-4c19-a800-6993bdec8776)
	Trang “Đơn hàng” giúp quản trị viên có thể quản lý các đơn hàng của cửa hàng. Trang liệt kê thông tin tất cả các đơn hàng. Quản trị viên có thể tìm kiếm đơn hàng theo số điện thoại, địa chỉ và lọc đơn hàng theo trạng thái của đơn hàng và sắp xếp theo thời gian mà khách hàng đặt. Nhấn “Xem” ở mỗi đơn hàng để xem chi tiết các sản phẩm của đơn hàng. Nhấn “Đổi” sẽ có 1 cửa sổ bật lên để cập nhật trạng thái mới cho đơn hàng.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/afd9bf71-894d-46dd-884d-9c2215ed6767)
	Khi người dùng nhấn “Xem” ở mỗi đơn hàng sẽ hiện lên cửa sổ để xem các sản phẩm của đơn hàng đó.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/4fba112e-5d4d-4a6b-9e75-fd099a01195a)
	Khi người dùng nhấn “Đổi”, cửa sổ cho phép người quản trị cập nhật trạng thái đơn hàng sẽ hiện lên, quản trị viên sẽ chọn trạng thái mới và nhấn “Lưu thay đổi” để lưu.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/b8a5c357-7ad7-4950-b1a8-a853f8b7e14c)
	Trang “Doanh thu”, sẽ mặc định tính toán danh thu của cửa hàng trong 7 ngày gần nhất và liệt kê các đơn hàng đã bán đó (đơn hàng có trạng thái là Đã nhận). Quản trị viên có thể chọn ngày khác để lọc các đơn hàng và tính doanh thu giữa các ngày đó. Nhấn “Xem” để hiện lên cửa sổ cho phép xem chi tiết các đơn hàng đó.
  
![image](https://github.com/PhanCongTu/OnlineClothingStore-BackEnd/assets/94525644/eccbecfe-b867-4114-9f91-55ac73f88232)
	Trang “Người dùng”, liệt kê danh sách các tài khoản có trong hệ thống. Có thể tìm kiếm theo tên người dùng hoặc sắp xếp danh sách theo tên người dùng. Nút “Đổi” có chức năng vô hiệu hóa tài khoản, tài khoản nào bị vô hiệu hóa sẽ không còn có thể đăng nhập vào hệ cửa hàng. Ngược lại, có thể tái kích hoạt lại tài khoản đó.

