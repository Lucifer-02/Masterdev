CREATE TABLE masterdev_hoangnlv.users (
 id int NOT NULL AUTO_INCREMENT,
 Username varchar(32) CHARACTER SET
utf8mb4 ,
 Fullname varchar(32) CHARACTER SET
utf8mb4 ,
 Province varchar(32) CHARACTER SET
utf8mb4 ,
 Age TINYINT DEFAULT NULL,
 PRIMARY KEY (id),
 UNIQUE (Username)
) ENGINE = InnoDB DEFAULT CHARSET = latin1

DROP TABLE masterdev_hoangnlv.users ;

SELECT *
FROM masterdev_hoangnlv.users u
ORDER BY id DESC ;

INSERT INTO masterdev_hoangnlv.users (Username, Fullname, Province, Age)
VALUES ('ghtk', 'Le Anh Tuan', 'NA', 30);

CREATE INDEX idx_age ON
masterdev_hoangnlv.users(Age);

CREATE INDEX idx_userName ON
masterdev_hoangnlv.users (Username);

SELECT Username , Age
FROM masterdev_hoangnlv.users u
ORDER BY Age DESC;

SELECT *
FROM masterdev_hoangnlv.users u
WHERE Username = 'ghtk';