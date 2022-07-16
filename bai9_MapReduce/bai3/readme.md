# Cấu hình 
- Trong thư mục /home/hadoop/hoangnlv/bai3
- Input Path hdfs: /hoangnlv/bai3/input/people.csv và /hoangnlv/bai3/input/salary.csv
- Output path hdfs: /hoangnlv/bai3/output
- Lệnh chạy trong file readme.txt 
# Cách hoạt động
- JobKey class: lưu key của các bảng và recordType để phân biệt loại bảng khi join
- PeopleRecord và SalaryRecord class: lưu các fields
- JoinGenericWritable class: Bọc các record trong một kiểu chung, thuận tiện cho việc ghi dữ liệu
- JoinDriver class: Thực hiện tất cả các bước MapReduce 
- Map: đọc fields từ các bảng và lưu thành các records, thêm Exception để đọc file input có header
- Reduce: Duyệt qua các record theo key từ bảng salary và append các thông tin tương ứng, Override setup() method để thêm header mà không bị trùng lặp
- Config 3 ReduceTasks

