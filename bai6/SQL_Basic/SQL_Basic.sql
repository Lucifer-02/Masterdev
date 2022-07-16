SELECT
	count(id) AS solopday,
	teacher_name
FROM
	semester.class c
GROUP BY
	teacher_name ;
-- diem trung binh toan truong
SELECT
	AVG(score)
FROM
	semester.scores s ;
-- ti le truot, tb, kha, gioi, xuat sac
SELECT
	(
	SELECT
		count(*)
FROM
		semester.scores s
WHERE
		score <4)/(
	SELECT
		count(*)
FROM
		semester.scores) AS ti_le_truot;

SELECT
	(
	SELECT
		count(*)
FROM
		semester.scores s
WHERE
		score >= 4
AND score <6)/(
	SELECT
		count(*)
FROM
		semester.scores) AS ti_le_tb ;

SELECT
	(
	SELECT
		count(*)
FROM
		semester.scores s
WHERE
		score >= 6
AND score <8)/(
	SELECT
		count(*)
FROM
		semester.scores) AS ti_le_kha;

SELECT
	(
	SELECT
		count(*)
FROM
		semester.scores s
WHERE
		score >= 8
AND score <9)/(
	SELECT
		count(*)
FROM
		semester.scores) AS ti_le_gioi;

SELECT
	(
	SELECT
		count(*)
FROM
		semester.scores s
WHERE
		score >= 9)/(
	SELECT
		count(*)
FROM
		semester.scores) AS ti_le_xuat_sac;
-- mon co diem tb cao nhat
SELECT
	name,
	diemtb
FROM
	semester.courses c
JOIN
(
	SELECT
		AVG(score) AS diemtb,
		class_id DIV 1000000 AS mon
FROM
		semester.scores s
GROUP BY
		mon
ORDER BY
		diemtb DESC
LIMIT 1) m 
ON
	c.id = m.mon;
-- lop co diemtb cao nhat
SELECT
	class_id ,
	avg(score) AS diemtb
FROM
	semester.scores s
GROUP BY
	class_id
ORDER BY
	diemtb DESC
LIMIT 1;



-- sv co diemtb cao nhat;
SELECT
	id,
	name,
	diemtb
FROM
	semester.students s
JOIN 
(
	SELECT
		student_id,
		AVG(score) AS diemtb
FROM
		semester.scores sc
GROUP BY
		student_id
ORDER BY
		diemtb DESC
LIMIT 1) m
ON
s.id = m.student_id ;
-- Mon co ti le truot cao nhat 
SELECT total.course_id, (fail_num / total_num) AS fail_ratio
FROM 
(SELECT course_id, count(*) AS total_num
FROM 
(SELECT (class_id DIV 1000000) AS course_id, score
FROM semester.scores s) a
GROUP BY course_id) total
JOIN
(SELECT course_id, count(*) AS fail_num
FROM 
(SELECT (class_id DIV 1000000) AS course_id, score
FROM semester.scores s
WHERE s.score < 4) a
GROUP BY course_id) fails
ON
total.course_id = fails.course_id
ORDER BY fail_ratio DESC
LIMIT 1;
-- Danh sach sv khong truot mon nao;SELECT std.id, std.name
FROM semester.students std
WHERE NOT EXISTS 
(SELECT student_id
FROM semester.scores sc
WHERE std.id = sc.student_id
AND sc.score < 4);
-- top 10 mon kho nhat
SELECT total.course_id, (fail_num / total_num) AS fail_ratio
FROM 
(SELECT course_id, count(*) AS total_num
FROM 
(SELECT (class_id DIV 1000000) AS course_id, score
FROM semester.scores s) a
GROUP BY course_id) total
JOIN
(SELECT course_id, count(*) AS fail_num
FROM 
(SELECT (class_id DIV 1000000) AS course_id, score
FROM semester.scores s
WHERE s.score < 4) a
GROUP BY course_id) fails
ON
total.course_id = fails.course_id
ORDER BY fail_ratio DESC
LIMIT 10;
