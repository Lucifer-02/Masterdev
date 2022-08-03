# Cấu hình
- Master + Kibana: 172.17.80.25

# Thực hiện
- Bài 1: 
```
GET dantri/_search
{
    "query": {
        "bool" : {
            "must" : [
                {
                    "multi_match" : {
                        "query":  "an toàn",
                        "type":   "phrase",
                        "fields": [ "title", "description", "content"]
                    }
                },
                {
                    "multi_match" : {
                        "query":  "đường bộ",
                        "type":   "phrase",
                        "fields": [ "title", "description", "content"]
                    }
                },
                {
                    "multi_match" : {
                        "query":  "đường sắt",
                        "type":   "phrase",
                        "fields": [ "title", "description", "content"]
                    }
                },
                {
                    "range": {
                        "time": {
                            "gte": 1356998400,
                            "lt": 1388534400
                        }
                    }
                }
            ]
        }
    }
}
```

- Bài 2: 
```
GET dantri/_search
{
    "query": {
        "bool" : {
            "must" : {
                "prefix": {
                "title.keyword": "Hà Nội"
            }
            },
            "must_not" :{
                "match_phrase": {
                    "description": {
                        "query": "Hà Nội"
                    }
                }
            }
        }
    },
    "sort" : [
        { "time" : "desc" }
    ]
}
```

- Bài 3: 
+ file ./bai3/data/gen.py trích xuất title rồi tách cụm từ bằng pyvi ghi thành json file vào thư mục ./bai3/data/result
+ Tạo index cho suggestion

```
PUT title_suggest_hoangnlv
{
    "mappings": {
        "properties" : {
            "suggest_title" : {
                "type" : "completion"
            }
        }
    }
}
```

+ Gửi data vừa tạo lên ElasticSearch server:
> curl -s -H "Content-Type: application/json" -XPOST 172.17.80.25:9200/_bulk --data-binary @data_1.json

+ Viết Class ElasticServices xuất ra list suggestions theo từ khóa input.

