from structures import *
import math

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
                expressions.add(expressionsCount, sub)
                expressionsCount += 1
        index += 1

    numValues = int(math.pow(2, variables.length))

    return (variables, expressions, numValues)

def evaluate(variables, expressions, numValues):
    pass

def main():
    userInput = getInput()
    variables, expressions, numValues = parse(userInput)
    truthTable = evaluate(variables, expressions, numValues)
    #printVariables()
    #printExpressions()
    #printTruthTable()

if __name__ == "__main__":
    main()
