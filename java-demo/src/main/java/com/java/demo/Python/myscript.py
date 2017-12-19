import sys
print("starting....")
print("script name: ", sys.argv[0])
print("param1:", sys.argv[1])
print("param2:", sys.argv[2])
print("param3:", sys.argv[3])
def myFunction(arg1,arg2,arg3):
    print("calling python function with paramters:")
    result = arg1 + arg2 + arg3
    return result
v = myFunction(int(sys.argv[1]),int(sys.argv[2]),int(sys.argv[3]))
print("result=", v)
class Student(object):
    def __init__(self, name):
        self.name = name
s = Student('Bob')
def myFunction1(s1):
    print("calling python function with paramters:")
    print(s1.name)
    return s1
myFunction1(s)