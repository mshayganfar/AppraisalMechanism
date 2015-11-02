import csv

def analyzeQuestions(strFileName, qNum):
  qList = []
  count = 0
  fh = open(strFileName, 'r')
  reader = csv.DictReader(fh)
  for i in range(1, qNum):
    qList.append([])
    for row in reader:
      qList[i-1].append(row['Q'+str(i)])
      if (row['Q'+str(i)] == 'True'):
        count += 1
    print("Total correct answers for question", i, ": ", count)
    fh.seek(0)
    count = 0
  fh.close()
  return qList

def analyzeSubjects(strFileName, sNum):
  sList = []
  subCount = 1
  count = 0
  i = 1
  fh = open(strFileName, 'r')
  reader = csv.DictReader(fh)
  for row in reader:
    if ((i != 4) or (i != 11)):
      for i in range(1, sNum):
        if (row['Q'+str(i)] == 'True'):
          count += 1
      print("Total correct answers for subject", subCount, ": ", count)
      sList.append(count)
      count = 0
      subCount += 1
  fh.close()
  return sList
