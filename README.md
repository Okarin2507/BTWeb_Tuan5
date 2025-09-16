# BTWeb_Tuan5
23110315 Le Ngo Nhut Tan

Đây là một ứng dụng web được xây dựng bằng Jakarta EE (Servlet/JSP) và JPA/Hibernate, dùng để quản lý các danh mục và video. Hệ thống phân quyền rõ ràng với 3 vai trò: User, Manager, và Admin.

## Tính năng chính

###  Chung (Tất cả người dùng)
- **Xác thực:** Đăng ký (với xác thực OTP qua email), Đăng nhập, Đăng xuất.
- **Hồ sơ:** Xem và cập nhật thông tin cá nhân (họ tên, số điện thoại, ảnh đại diện).

###  User (Người dùng)
- Xem danh sách tất cả các **Danh mục** và **Video** có trong hệ thống.

###  Manager (Người quản lý)
- **Quản lý Category:**
  - Thêm, sửa, xóa các danh mục do chính mình tạo ra.
  - Xem được danh sách các danh mục được tạo bởi các Manager khác.
- **Quản lý Video:**
  - Thêm, sửa, xóa các video do chính mình tạo ra.
  - Xem được danh sách tất cả các video trong hệ thống.

###  Admin (Quản trị viên)
- **Toàn quyền của Manager.**
- **Quản lý Người dùng:**
  - Xem danh sách tất cả người dùng.
  - Chỉnh sửa thông tin người dùng, bao gồm cả việc **thay đổi vai trò (Role ID)**.
  - Xóa tài khoản người dùng (không thể tự xóa chính mình).
- **Quản lý Category:**
  - Toàn quyền Thêm, sửa, xóa trên **tất cả** các danh mục.
  - **Gán quyền sở hữu** một danh mục từ người này sang người khác.
- **Quản lý Video:**
  - Toàn quyền Thêm, sửa, xóa trên **tất cả** các video.

**Các bước thực hiện:**

1.  **Clone Repository:**
    ```bash
    git clone https://github.com/TenCuaBan/TenRepository.git
    ```

2.  **Cài đặt Cơ sở dữ liệu:**
    - Tạo một database mới trong SQL Server (ví dụ: `JPA_WebApp`).
    - Chạy các script SQL dưới đây để tạo các bảng cần thiết:
      ```sql
      -- Bảng Users
      CREATE TABLE Users (
          id INT PRIMARY KEY IDENTITY(1,1),
          username NVARCHAR(50) NOT NULL UNIQUE,
          password NVARCHAR(255) NOT NULL,
          email NVARCHAR(100) NOT NULL UNIQUE,
          fullname NVARCHAR(100),
          image NVARCHAR(255),
          roleId INT NOT NULL,
          phone NVARCHAR(20),
          createdDate DATE
      );

      -- Bảng Categories
      CREATE TABLE Categories (
          id INT PRIMARY KEY IDENTITY(1,1),
          name NVARCHAR(255) NOT NULL,
          icon NVARCHAR(255),
          userId INT,
          CONSTRAINT FK_Categories_Users FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE SET NULL
      );

      -- Bảng Videos
      CREATE TABLE Videos (
          id INT PRIMARY KEY IDENTITY(1,1),
          title NVARCHAR(255) NOT NULL,
          poster NVARCHAR(255),
          description NVARCHAR(MAX),
          url NVARCHAR(255) NOT NULL,
          userid INT,
          CONSTRAINT FK_Videos_Users FOREIGN KEY (userid) REFERENCES Users(id) ON DELETE CASCADE
      );
      ```

3.  **Cấu hình kết nối:**
    - Mở file `src/main/resources/META-INF/persistence.xml`.
    - Thay đổi các thông tin `jakarta.persistence.jdbc.url`, `user`, và `password` cho khớp với CSDL của bạn.

4.  **Cấu hình Email (Tùy chọn):**
    - Mở file `src/main/java/vn/week3/util/EmailUtil.java`.
    - Thay đổi `FROM_EMAIL` và `APP_PASSWORD` bằng thông tin tài khoản Gmail của bạn để chức năng gửi OTP hoạt động.

5.  **Build và Chạy:**
    - Import project vào IDE của bạn (Eclipse, IntelliJ) dưới dạng một "Existing Maven Project".
    - IDE sẽ tự động tải về các dependency cần thiết.
    - Deploy (Run As -> Run on Server) project lên server Apache Tomcat đã được cấu hình trong IDE.
    - Truy cập `http://localhost:8080/TenProject/` để bắt đầu.
