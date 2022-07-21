# Cấu hình

# Thực hiện
- Gen Data Class bằng file gen.sh từ file DataTracking.proto trong folder protobuf lưu vào package com.example
- File SendKafkaProto.java trong folder kafka/ là Producer gen random protobuf đẩy vào topic data_tracking_hoangnlv
- File KafkaSpark.java sử dụng DStream, consume dữ liệu từ kafka, deserialize dạng protobuf, map dữ liệu sang dạng String sau đó với mỗi RDD nhận được trích xuất các trường sử dụng regex và chuyển sang dạng DataFrame để ghi dữ liệu ra theo yêu cầu
