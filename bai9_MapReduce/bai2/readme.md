# Cấu hình 
- Trong thư mục /home/hadoop/hoangnlv/bai2/
- Input path: /hoangnlv/bai2/input/count_distinct.csv
- Output path: /hoangnlv/bai2/output1 - job1
				/hoangnlv/bai3/output2 - job2 
- Chạy lệnh trong file readme.txt 

# Cách hoạt động 
- Tạo 2 job thực hiện MapReduce 
- job1: MapReduce như WordCount nhưng không cần đếm vì chỉ cần bỏ trùng lặp 
- job2: bước Map đánh key của các record bằng 0 sau đó đếm số lượng value ở bước Reduce
