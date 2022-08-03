import json
import pandas as pd
from pyvi import ViTokenizer

files = ["data_1.json", "data_2.json", "data_3.json"]

if __name__ == '__main__':
    for file in files:
        fRead = open(file, "r", encoding="utf8")
        fWrite = open("result/" + file, "a", encoding="utf8")
        Lines = fRead.readlines()
        count = 0
        lamdaForReplaceCharacter = lambda x: x.replace("_", " ")
        lamdaForLowerCase = lambda x: x.lower()
        # Strips the newline character
        for line in Lines:
            count += 1
            if count % 2 != 0:
                #  fWrite.write(line.replace("dantri", "title_suggest_hoangnlv"))
                fWrite.write(line.replace("dantri", "hoangnlv_test"))
            else:
                json_object = json.loads(line.strip())
                #print(json_object["title"])
                arrayPhrase = ViTokenizer.tokenize(json_object["title"]).split()
                arrayPhrase = filter(lambda x: x.find("_") != -1, arrayPhrase)
                arrayPhrase = list(map(lamdaForReplaceCharacter, arrayPhrase))
                arrayPhrase = list(map(lamdaForLowerCase, arrayPhrase))
                fWrite.write("{" + "\"suggest_title\": [\"" + '", "'.join(arrayPhrase) + "\"]}\n")
        fRead.close()
        fWrite.close()
