from structures import *

def main():
    hm = HashMap()
    hm.add("Name", "Inderpreet Dhillon")
    hm.display()

    st = Stack()
    st.push("Inderpreet")
    st.push("Dhillon")
    st.display()

    qu = Queue()
    qu.push("Inderpreet")
    qu.push("Dhillon")
    qu.display()

if (__name__ == "__main__"):
    main()
