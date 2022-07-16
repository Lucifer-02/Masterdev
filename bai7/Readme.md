## Cấu hình

- Group 1 cài trên 3 node 22,23(master),25
- Database: book_management, Collection: hoangnlv, 10M records, đánh index public_date và title-author.

## API

- List tất cả records có phân trang và sắp xếp theo public_date.
- Thêm record mới
- Tìm kiếm theo title và author, hỗ trợ full text search 
- List records trong khoảng thời gian xuất bản.
- Ném ngoại lệ nếu không tìm thấy hoặc số records quá lớn.

