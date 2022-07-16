import numpy as np

file = open("out.csv", mode="a")

count=1
register = 0
for i in range(1,1001):
    if count == 201:
       count = 1 
    for j in range(1, np.random.randint(2,10) + 1):
        amount = np.random.randint(20,101)
        register += amount
        class_id = count*(10**6) + i*(10**2) + j 
        students = map(str, np.random.randint(1,41103,amount))
        file.write(str(class_id) + "," + str(amount)+ "," + ",".join(students)+ "\n")
    count += 1 
print(register)
file.close()
