import numpy as np

file_read = open("out.csv", "r")
file_write = open("scores.csv", "a")

datas = file_read.read().split("\n")
file_read.close()

record = ""
for line in datas:
    infos = line.split(',')
    for i in range(2, len(infos)):
        record = infos[0] + "," +infos[1] +","+ infos[i] + "," + str(np.random.uniform(0,10,1)) + "\n"
        file_write.write(record)

file_write.close()
