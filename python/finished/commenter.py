from structures import *
import re

def readFile(fileName):
    input = open(fileName, "r")
    fileContents = input.readlines()
    input.close()
    return fileContents

def writeFile(contents, fileName = "out"):
    output = open(fileName, "w")
    output.writelines(contents)
    output.close()

def comment(contents):
    out = []
    for line in contents:
        if len(line.strip()) != 0:
            out.append(line)
    out2 = []
    out2.append("//TODO: Check this section\n")
    for line in out:
        stripped = line.strip()
        if len(stripped) > 0:   
            if stripped == "{" or stripped == "}":
                out2.append(line)
            else:
                if re.search('private|public|static', stripped):
                    splitted = stripped.split(" ")
                    for i, j in enumerate(splitted):
                        if "(" in j:
                            splitted2 = j.split("(")
                            message = "// Do " + splitted2[0]
                            break
                    out2.append(message + "\n" + line)
                elif "for" in stripped or "while" in stripped:
                    if "for" in stripped:
                        splitted = stripped.split(";")
                        message = "\n// Loop while " + splitted[1][1:]
                    else:
                        idx1 = stripped.index('(') + 1
                        idx2 = stripped.index('{') - 2
                        message = "\n// Loop while " + stripped[idx1:idx2]
                    out2.append(message + "\n" + line)
                elif re.search('String|int', stripped):
                    splitted = stripped.split(" ")
                    message = "\n// Create " + splitted[0] + " for " + splitted[1]
                    out2.append(message + "\n" + line)
                elif "+=" in stripped:
                    splitted = stripped.split(" ")
                    message = "\n// Append " + splitted[2][:-1] + " to " + splitted[0]
                    out2.append(message + "\n" + line)
                elif stripped.endswith("++;"):
                    idx = stripped.index('+')
                    message = "\n// Increment " + stripped[:idx].strip()
                    out2.append(message + "\n" + line)
                else:
                    out2.append("\n//TODO: comment\n" + line)
    return out2

def main(fileName = None):
    if fileName == None:
        fileContents = readFile(input("File to Comment: "))
    else:
        fileContents = readFile(fileName)
    fileContents = comment(fileContents)
    writeFile(fileContents)
    
if __name__ == '__main__':
    main("Test.java")