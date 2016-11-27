from structures import *

def readFile(fileName):
    input = open(fileName, "r")
    fileContents = input.readlines()
    input.close()
    return fileContents

def writeFile(contents, fileName = "out"):
    output = open(fileName, "w")
    output.writelines(contents)
    output.close()

def main(fileName):
    fileContents = readFile(fileName)
    fileContents.append("")
    writeFile(fileContents)
    
if __name__ == '__main__':
    main("Test.java")