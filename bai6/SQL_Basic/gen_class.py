teachers_file=  open("teachers.csv", "r", encoding='utf8')
class_file = open("out.csv", "r")

teacher_data = teachers_file.read().split("\n")
class_data = class_file.read().split("\n")


teachers = []
for element in teacher_data:
    try:
        teachers.append(element.split(',')[1])
    except:
        break


for e in class_data:
   try:
       class_id = int(e.split(',')[0])
       teacher_id = int(class_id/100) %10000
       print(str(class_id) + ","+ str(teachers[teacher_id -1]))
   except:
       break
