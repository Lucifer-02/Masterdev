* Cơ sở dữ liệu không có gì đặc biệt, index trong các bảng đều là auto increase trừ id trong bảng class là tự gen, id này bao gồm id của môn + id giảng viên + số thứ tự của lớp lần lượt được xếp ở hàng triệu,trăm và đơn vị.

1. Lấy sample tại https://github.com/Lucifer-02/khao-sat-diem-thi-2020/blob/main/clean_data.csv
2. Lấy 1000 records trong số này làm giảng viên, được đánh số từ 1-1000, lưu ra file teachers.csv.
3. Thu thập từ nhiều nguồn 200 môn học, được đánh số từ 1-200, lưu ra file courses_out.csv
4. script gen.py gen ra file out.csv gồm mã lớp, số sinh viên trong lớp và id các sinh viên trong lớp đó, Tạo mã lớp bằng cách lấy id giảng viên Duplicate ngẫu nhiên trong đoạn [2,10](nhằm thỏa mãn điều kiện giảng viên dạy từ 2-20 lớp, nhưng 20 là quá lơn nên chỉ chọn đến 10), các lớp này được đánh số tăng dần. Số học viên được lấy ngẫu nhiên [20,100], từ các thông số trên tính được class_id. Tổng số sinh viên trong tất cả các lớp chia cho 8(lấy trung bình theo thống kê) được ước lượng về tổng số sinh viên. Ghi ra file out.csv với 3 cột class_id, num_students, student_ids - lấy ngẫu nhiên trong khoảng từ 1 đến tổng số sinh viên vừa tính được.

5. Từ file out.csv gen ra file class_table.csv gồm mã lớp và tên giảng viên(vì trong mã lớp đã có mã giảng viên) bằng gen_class.py
6. Từ file out.csv gen ra file scores.csv gồm mã lớp, mã sinh viên và điểm(lấy ramdom) bằng gen_scores.py

7. Import lần lượt các file csv trên vào database bằng dbeaver.
