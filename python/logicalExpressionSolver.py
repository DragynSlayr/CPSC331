from structures import *
import math

IDENTIFIER = "EX"
NEGATION = '-'
AND = '*'
OR = '+'

def logicAnd(s1, s2):
    result = ""
    for i in range(len(s1)):
        if s1[i] == "T" and s2[i] == "T":
            result += "T"
        else:
            result += "F"
    return result

def logicOr(s1, s2):
    result = ""
    for i in range(len(s1)):
        if s1[i] == "T" or s2[i] == "T":
            result += "T"
        else:
            result += "F"
    return result

def logicNegate(string):
    result = ""
    for c in string:
        if c == "T":
            result += "F"
        else:
            result += "T"
    return result

def toPostfix(expression):
    output = ""
    operators = Stack()
    for c in expression:
        if c.isalpha():
            output += c
        elif c == ')':
            output += operators.pop()
        elif c != '(':
            operators.push(c)
    while(not operators.isEmpty()):
        output += operators.pop()
    return output

def getTruthValues(expression, numValues, variables):
    truthValues = Stack()
    postFix = toPostfix(expression)
    for c in postFix:
        if c.isalpha():
            values = getTruthColumn(c, numValues, variables)
            truthValues.push(values)
        else:
            values = ""

            if c == NEGATION:
                operand = truthValues.pop()
                values = logicNegate(operand)
            elif c == OR:
                operandA = truthValues.pop()
                operandB = truthValues.pop()
                values = logicOr(operandA, operandB)
            elif c == AND:
                operandA = truthValues.pop()
                operandB = truthValues.pop()
                values = logicAnd(operandA, operandB)
            truthValues.push(values)
    return truthValues.pop()

def getTruthColumn(variable, numValues, variables):
    values = ""
    numTrues = int(numValues / math.pow(2, variables.indexOf(variable) + 1))
    counter = 0
    isTrue = True
    for i in range(numValues):
        if counter == numTrues:
            counter = 0
            isTrue = not isTrue

        if isTrue:
            values += "T"
        else:
            values += "F"

        counter += 1
    return values

def getInput():
    userInput = input("Input string: ")
    return userInput.upper()

def parse(userInput):
    variables = Queue()
    expressions = HashMap()
    indices = Stack()

    expressionsCount = 0
    index = 0

    for x in userInput:
        if x.isalpha():
            if not variables.contains(x):
                variables.push(x)
        elif x == '(':
            indices.push(index)
        elif x == ')':
            sub = userInput[int(indices.pop()) + 1:index]
            if not expressions.contains(sub):
                expressions.add(IDENTIFIER + str(expressionsCount), sub)
                expressionsCount += 1
        index += 1

    numValues = int(math.pow(2, variables.length))

    return (variables, expressions, numValues)

def evaluate(variables, expressions, numValues):
    truthTable = []

    for i in range(1 + numValues):
        temp = []
        for j in range(variables.length + expressions.size):
            temp.append("")
        truthTable.append(temp)

    for i in range(variables.length + expressions.size):
        values = ""
        if i < variables.length:
            variable = variables.get(i).data
            truthTable[0][i] = variable
            values = getTruthColumn(variable, numValues, variables)
        else:
            key = IDENTIFIER + str(i - variables.length)
            truthTable[0][i] = key
            values = getTruthValues(expressions.get(key), numValues, variables)

        for j in range(len(values)):
            truthTable[j + 1][i] = values[j]

    return truthTable

def printVariables(variables):
    print("Output:\nSet of independent variables:")
    iterator = variables.iterator()
    while(iterator.hasNext()):
        print(iterator.getNext().data)

def printExpressions(expressions):
    print("Set of logical subexpressions and logical expression:")
    for i in range(expressions.size):
        key = IDENTIFIER + str(i)
        print("%s, %s" % (key, expressions.get(key)))

def printTruthTable(truthTable):
    print("Truth Table:")
    for i in range(len(truthTable)):
        for j in range(len(truthTable[i])):
            if i == 0:
                print(truthTable[i][j] + "\t", end = "")
            else:
                pad = " " * (len(truthTable[0][j]) // 2)
                print(pad + truthTable[i][j] + "\t", end = "")
        print()

def main():
    userInput = getInput()
    variables, expressions, numValues = parse(userInput)
    truthTable = evaluate(variables, expressions, numValues)
    printVariables(variables)
    printExpressions(expressions)
    printTruthTable(truthTable)

if __name__ == "__main__":
    main()
