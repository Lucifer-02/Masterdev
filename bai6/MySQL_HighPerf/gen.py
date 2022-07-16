from faker import Faker
from collections import defaultdict
import random


fake = Faker()
provinces = ("AG","BD","BĐ","BG","BK","BL","BN","BP","BT","BTh","BV","CB","CM","CT","ĐB","ĐL","ĐN","ĐNa","ĐNo","ĐT","GL","HB","HD","HG","HGi","HN","HT","HD","HP","HGI","HB","SG","HY","KH","KG","KT","LC","LS","LCa","LĐ","LA","NĐ","NA","NB","NT","PT","PY","QB","QNa","QNg","QN","QT","ST","SL","TN","TB","TNg","TH","TTH","TG","TV","TQ","VL","VP","YB"
)
fake_data = defaultdict(list)

for i in range(10**6):
    print(fake.unique.user_name() + "," + 
            fake.name() + "," + provinces[random.randint(0,63)] + "," + str(random.randint(10,90)))
