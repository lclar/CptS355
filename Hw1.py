#Lyndsay Clarke
#CptS 355 HW1
#1/27/2017
#I did this using idle on windows

from collections import defaultdict, OrderedDict

def cryptDict(s1, s2):
    #initialize dictionary with keys from string
    myDict = dict.fromkeys(s1)
    i = 0
    #set the values of the keys
    for letter in s1:
        myDict[letter] = s2[i]
        i = i+1
    return myDict
def decrypt(cdict,s):
    #replace the letters with their corresponding value, if it is not in the dictionary then keep it the same
    for letter in s:
        char = cdict.get(letter, letter)
        s = s.replace(letter, char)
    return s
def testDecrypt():
    cdict = cryptDict("abc", "xyz")
    revcdict = cryptDict("xyz","abc")
    tests = "Now I know my abc’s"
    answer = "Now I know my xyz’s"
    if decrypt(cdict, tests) != answer:
        return False
    if decrypt(revcdict, decrypt(cdict, tests)) != "Now I know mb abc’s":
        return False
    if decrypt(cdict,"") != "":
        return False
    if decrypt(cryptDict("",""), "abc") != "abc":
        return False
    return True
def charCount(S):
    count = {}
    #remove spaces
    S = S.replace(" ", "")
    #initialize dictionary with keys from the string
    count = dict.fromkeys(S,0)
    #set the values of the keys to the counts of the letters
    for letter in S:
        count[letter] += 1
    #convert to list of 2-tuples
    counts = count.items()
    #sort the counts
    sortedcounts = sorted(list(counts))
    return sortedcounts
def testCount():
    teststring = "Hello World"
    testanswer = [('H',1), ('W',1), ('d',1), ('e',1), ('l',3), ('o',2), ('r',1)]
    if charCount(teststring) != testanswer:
       return False
    teststring = teststring.replace("Hello", "Goodbye")
    if charCount(teststring) != [('G',1),('W',1),('b',1),('d',2),('e',1),('l',1),('o',3),('r',1),('y',1)]:
        return False
    return True
    
def dictAddup(d):
    hours = defaultdict(int)
    #merge and add up the hours for each day and each class
    for k, v, in d.items():
        tempSum = 0;
        for k2, v2 in v.items():
            hours[k2] += v2
    #sort the dictionary to make testing easier
    hours2 = OrderedDict(sorted(hours.items()))
    return dict(hours2)
    pass
def testAddup():
    studyTime = {}
    studyTime = {"Monday":{"355":2, "350":1, "360":3, "487":2},"Tuesday":{"487":2,"360":2},"Wednesday":{"355":4, "360":2},"Friday":{"487":1, "355":3},"Sunday":{"350":2, "355":1, "360":2, "487":2}}
    answer = {'350':3,'355':10,'360':9,'487':7}
    if dictAddup(studyTime) != answer:
        return False
    return True

def main():
    passedMsg = "%s_passed"
    failedMsg = "%s_failed"
    if testDecrypt():
        print( passedMsg % 'testDecrypt')
    else:
        print( failedMsg % 'testDecrypt')
    if testCount():
        print( passedMsg % 'testCount')
    else:
        print( failedMsg % 'testCount')
    if testAddup():
        print( passedMsg % 'testAddup')
    else:
        print( failedMsg % 'testAddup')
    
if __name__ == "__main__":
    main()
