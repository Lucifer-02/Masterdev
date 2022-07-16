# Cấu hình 
- Thư mục: /home/hadoop/hoangnlv/bai1
- Input path hdfs: hoangnlv/bai1/input/input.txt
- Output path hdfs: hoangnlv/bai1/output
- Patterns path hdfds: hoangnlv/bai1/patterns.txt
- Lệnh chạy trong file readme.txt 
# Cách hoạt động 
- Method setup(): Thực hiện cấu hình cho 2 options sensitive(chuyển chữ hoa thành chữ thường) và skip patterns(loại bỏ các kí tự).
- Method parseSkipFile(): Đọc file patterns.txt lưu vào patternToSkip.
- Method map(): lấy file input, chia từng từ theo " ".
- Method reduce(): Map các thành phần con lại và tính tổng số lần xuất hiện của các keys.
