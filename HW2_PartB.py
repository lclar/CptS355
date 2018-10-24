#Lyndsay Clarke
#CptS 355
#Spring 2017
#Mac Compiler using idle

import re

#------------------------- 10% -------------------------------------
# The operand stack: define the operand stack and its operations
opstack = []
def opPop():
    #if the list is empty print error
    if(len(opstack) <= 0):
        print("Error Empty Stack")
        sys.exit(1)
    #else return the last item in the list
    else:
        return opstack.pop()

def opPush(value):
    #append the value onto the end of the list
    opstack.append(value)
    return

#-------------------------- 20% -------------------------------------
# The dictionary stack: define the dictionary stack and its operations
dictstack = []
# now define functions to push and pop dictionaries on the dictstack, to define name, and to lookup a name
def dictPop():
    #if the list is empty print error
    if(len(dictstack) <= 0):
        print("Error Empty Stack")
        sys.exit(1)
    #else return the last item in the list
    else:
        return dictstack.pop()

def dictPush():
    dictstack.append({})
    return
def dictPush(d):
    dictstack.append(d)
    return
def define(name, value):
    temp = {name:value}
    dictPush(temp)
    
#add name:value to the top dictionary in the dictionary stack. Your psDef function should pop the name and value from operand stack and call the “define” function.

def lookup(name):
    temp = dictPop()
    value = temp.get(name)
    #append temp back onto the stack
    dictstack.append(temp)
    return value
# return the value associated with name
# What is your design decision about what to do when there is no definition forname?
#--------------------------- 10% -------------------------------------
# Arithmetic operators: define all the arithmetic operators here -- add, sub, mul, div, mod
#Make sure to check the operand stack has the correct number of parameters and types of the parameters are correct
def add():
    #get the operands
    operand2 = int(opPop())
    operand1 = int(opPop())
    #add them together
    result = str(operand1 + operand2)
    #append the result
    opPush(int(result))
    return
def sub():
    #get the operands
    operand2 = int(opPop())
    operand1 = int(opPop())
    #subtract them
    result = str(operand1 - operand2)
    #append the result
    opPush(int(result))
    return
def mul():
    #get the operands
    operand2 = int(opPop())
    operand1 = int(opPop())
    #mulitply them together
    result = str(operand1 * operand2)
    #append the result
    opPush(int(result))
    return
def div():
    #get the operands
    operand2 = int(opPop())
    operand1 = int(opPop())
    #if we aren't dividing by zero divide them
    if(operand2 != 0):
        result = str(operand1 / operand2)
        #append the result
        opPush(float(result))
    #else print an error
    else:
        Print("Error Divide By Zero")
        sys.exit(1)
    return
def mod():
    #get the operands
    operand2 = int(opPop())
    operand1 = int(opPop())
    result = operand1 % operand2
    opPush(int(result))
    return 

#--------------------------- 15% -------------------------------------
# Array operators: define the array operators length, get
def length(array):
    i = 0;
    for a in array:
        i = i + 1
    return i
def get(index):
    if (length(dickstack) >= index):
        return dictstack[index]
    else:
        print("Error array is not long enough to get that index")
        return
        
#--------------------------- 25% -------------------------------------
# Define the stack manipulation and print operators: dup, exch, pop, roll, copy, clear, stack
def dup():
    #duplicate top value so pop once and push twice
    operand = opPop()
    opPush(operand)
    opPush(operand)
    return;
def exch():
    #swap the top two values
    operand1 = opPop()
    operand2 = opPop()
    opPush(operand1)
    opPush(operand2)
    return
def pop():
    opPop()
    return
def roll():
    numofRoll = opPop()
    numofElements = opPop()
    #move the last of number of elements to be rolled to the top num of rolls times
    return
def copy():
    return
def clear():
    del opstack[:]
    return
def stack():
    print(opstack)
    return
#--------------------------- 20% -------------------------------------
# Define the dictionary manipulation operators: dict, begin, end, psDef
# name the function for the def operator psDef because def is reserved inPython
# Note: The psDef operator will pop the value and name from the opstack and call your own "define" operator (pass those values as parameters). Note that psDef()won't have any parameters.
def dict():
    dictPush()
    return
def begin():
    dictionary = opPop()
    if(type(dictionary) != dict):
        print("Error Not a Dictionary")
    else:
        dictpush(dictionary)
        return
def end():
    if(len(dictstack) < 2):
        print("Error Empty Dictionary Stack")
    else:
        dictPop()
        return
def psDef():
    value = opPop()
    key = opPop()
    if key[0] == '/':
        key = key[1:]
    define(key, value)
    return

#Test Functions
def testAdd():
    opPush(1)
    opPush(2)
    add()
    if opPop() != 3:
        return False
    return True
def testLookup():
    opPush("/n1")
    opPush(3)
    psDef()
    if lookup("n1") != 3:
        return False
    return True
def testSub():
    opPush(2)
    opPush(1)
    sub()
    if opPop() != 1:
        return False
    return True
def testMul():
    opPush(2)
    opPush(3)
    mul()
    if opPop() != 6:
        return False
    return True
def testDiv():
    opPush(6)
    opPush(2)
    div()
    if opPop() != 3.0:
        return False
    return True
def testMod():
    opPush(6)
    opPush(2)
    mod()
    if opPop() != 0:
        return False
    return True
def testDup():
    clear()
    opPush(10)
    dup()
    if (opstack ==[10,10]):
        return True
    return False
def testExch():
    clear()
    opPush(12)
    opPush(1)
    exch()
    if(opstack == [1, 12]):
        return True
    return False
#the pattern for tokenizeing
pattern = "/?[a-zA-Z][a-zA-Z0-9_]*|[[][a-zA-Z0-9_\s!][a-zA-Z0-9_\s!]*[]]|[-]?[0-9]+|[}{]+|%.*|[^ \t\n]"

def tokenize(s):
    retValue = re.findall(pattern,s)
    return retValue

def group(s):
    curlybrace = False
    brace = []
    for i in s:
        if i == '{':
            curlybrace = True
        elif curlybrace:
            if i == '}':
                curlybrace = False
                opstack.append(brace)
                continue
            brace.append(i)
        if i[0] == '/':
            var = i[1:]
            opstack.append(var)
            continue
        if curlybrace == False:
            interpreter(i)
def isNum(n):
    try:
        val = int(n)
    except ValueError:
        return False
    else:
        return True

def interpreter(token):
    if token == 'def':
        psDef()
        return
    if token == 'add':
        add()
        return
    if token == 'div':
        div()
        return
    if token == 'mod':
        mod()
        return
    if token == 'sub':
        sub()
        return 
    if token == 'dup':
        dup()
        return
    if token == 'mul':
        mul()
        return
    if isNum(token):
        opPush(int(token))
        return
    else:
        value = lookup(token)
        for item in value:
            if item == 'def': psDef()
            if item == 'add': add()
            if item == 'div': div()
            if item == 'mod': mod()
            if item == 'sub': sub()
            if item == 'dup': dup()
            if item == 'mul': mul()

def testParse(tokens):
    t = tokenize(tokens)
    group(t)

testString =  "/square {dup mul} def 1 square 2 square 3 square add add"


def main():
    if(testAdd()== True):
        print("Add passed")
    if(testSub()== True):
        print("Sub Passed")
    if(testMul() == True):
        print("Mul Passed")
    if(testDiv() == True):
        print("Div Passed")
    if(testDup() == True):
        print("Dup Passed")
    if(testExch() == True):
        print("Exch Passed")
    if(testLookup() == True):
        print("Lookup passed")
        dictPop()
    clear()
    testParse(testString)
    print("Testing - ", testString)
    print("---------DictStack----------")
    print(dictstack)
    print("---------OpStack------------")
    print(opstack)
    
    

if __name__ == "__main__":
    main()
