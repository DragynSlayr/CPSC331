from structures import *

ADD = '+'
POWER = '^'
DIVIDE = '/'
NEGATE = '~'
SUBTRACT = '-'
MULTIPLY = '*'

OPERATORS = {ADD, POWER, DIVIDE, NEGATE, SUBTRACT, MULTIPLY}

def toPostfix(expression):
    ops = Stack()
    out = ""
    num = ""
    for x in expression:
        if x.isdigit():
            num += x
        elif x == ')':
            out += num + ","
            num = ""
            out += ops.pop()
        elif x != '(':
            out += num + ","
            num = ""
            ops.push(x)
    while not ops.isEmpty():
        out += ops.pop()
    return out

def solve(expression):
    postfix = toPostfix(expression)
    values = Stack()
    num = ""
    for x in postfix:
        if not x in OPERATORS:
            if x == ',':
                if num != "":
                    values.push(float(num))
                    num = ""
            else:
                num += x
        else:
            if x == ADD:
                a = values.pop()
                b = values.pop()
                values.push(a + b)
            elif x == POWER:
                a = values.pop()
                b = values.pop()
                values.push(b ** a)
            elif x == DIVIDE:
                a = values.pop()
                b = values.pop()
                values.push(b / a)
            elif x == NEGATE:
                a = values.pop()
                values.push(-a)
            elif x == SUBTRACT:
                a = values.pop()
                b = values.pop()
                values.push(b - a)
            elif x == MULTIPLY:
                a = values.pop()
                b = values.pop()
                values.push(a * b)
            else:
                print("Error: " + x)
    return values.pop()

def getInput():
    return input("Input: ")

def main():
    expression = getInput()
    answer = solve(expression)
    print("%s = %.15f" % (expression, answer))

if __name__ == "__main__":
    main()
