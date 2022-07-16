# Config
- Master server: 172.17.80.21
- Path bài tập: /home/hadoop/hoangnlv/spark_tasks
- Command: trong file readme.txt 

# Implements
- Khởi tạo SparkSession 
- Đọc file Parquet trong thư mục /input vào dataframe parquetFileDF và tạo bảng "customers"
- Tạo "Table1" loại bỏ giá trị NULL của field "device_model" và trùng lặp từ bảng "customer"
- Tạo "Table2" lại bỏ giá trị NULL của 2 fields "device_model" và "button_id" từ bảng "customer"
- Khai báo dataframe "device_model_list_user" lưu group data theo "device_model" và list user duy nhất từ "Table1" bằng hàm SQL COLLECT_LIST()
- Tạo "List_Table" từ "device_model_list_user"
- Khai báo dataframe "device_model_num_user" đếm số lượng users bằng hàm SQL SIZE() tính độ dài mảng thuộc field "list_user_id" lấy từ "List_Table"
- Khai báo dataframe "action_by_button_id" thực hiện task3 từ "Table2"
- Lưu lại theo yêu cầu
