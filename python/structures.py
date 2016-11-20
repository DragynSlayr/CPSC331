class Entry():
    'An entry for a HashMap'

    def __init__(self, key, value):
        self.key = key
        self.value = value

    def __str__(self):
        key = str(self.key)
        value = str(self.value)
        return ("%s => %s" % (key, value))

class HashMap():
    'A basic HashMap'

    size = 0

    def __init__(self):
        self.map = Queue()

    def add(self, key, value):
        if (self.map.isEmpty()):
            self.map.head = Node(Entry(key, value))
        else:
            temp = self.map.head
            while(temp.nextNode != None):
                if(temp.data.key == key):
                    temp.data.value = value
                    return
                temp = temp.nextNode
            if (temp.data.key == key):
                temp.data.value = value
            else:
                temp.nextNode = Node(Entry(key, value))
        self.size += 1

    def get(self, key):
        iterator = self.map.iterator()
        while(iterator.hasNext()):
            current = iterator.getNext().data
            if (current.key == key):
                return current.value
        return None

    def contains(self, value):
        iterator = self.map.iterator()
        while(iterator.hasNext()):
            if (str(iterator.getNext().data.value) == str(value)):
                return True
        return False

    def display(self):
        self.map.display()

class Iterator:
    'Iterator for lists'

    def __init__(self, l):
        self.head = l.head

    def hasNext(self):
        return (self.head != None)

    def getNext(self):
        temp = self.head
        self.head = self.head.nextNode
        return temp

class Node:
    'Node for lists'

    nextNode = None

    def __init__(self, data):
        self.data = data

class List:
    'Base class for lists'

    head = None
    length = 0

    def display(self):
        if (self.head != None):
            out = ""
            iterator = self.iterator()
            while(iterator.hasNext()):
                out += str(iterator.getNext().data) + ", "
            print(out[:-2])
        else:
            print("Empty")

    def pop(self):
        if (self.head != None):
            temp = self.head.data
            self.head = self.head.nextNode
            return temp
        else:
            return None

    def isEmpty(self):
        return (self.head == None)

    def iterator(self):
        return (Iterator(self))

    def contains(self, data):
        return (self.indexOf(data) != -1)

    def indexOf(self, data):
        index = -1
        iterator = self.iterator()
        while(iterator.hasNext()):
            index += 1
            if (str(iterator.getNext().data) == str(data)):
                return index
        return -1

class Queue(List):
    'A basic queue'

    def push(self, data):
        if (self.head == None):
            self.head = Node(data)
        else:
            temp = self.head
            while(temp.nextNode != None):
                temp = temp.nextNode
            temp.nextNode = Node(data)
        self.length += 1

class Stack(List):
    'A basic stack'

    def push(self, data):
        if (self.head == None):
            self.head = Node(data)
        else:
            temp = Node(data)
            temp.nextNode = self.head
            self.head = temp
        self.length += 1
