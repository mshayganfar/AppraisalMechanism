import csv

def readFile(strFileName):
  idList = []
  count = 0
  fh = open(strFileName, 'r')
  reader = csv.DictReader(fh)
  for i in range(1, 13):
    idList.append([])
    for row in reader:
      idList[i-1].append(row['Q'+str(i)])
      if (row['Q'+str(i)] == 'True'):
        count += 1
    print("Total correct answers for question", i, ": ", count)
    fh.seek(0)
    count = 0
  fh.close()
  return idList
