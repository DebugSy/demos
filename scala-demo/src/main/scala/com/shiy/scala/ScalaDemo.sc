import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.math._

//数组
//固定长度
val array1 = new Array[Int](3)
array1(0) = 1
array1(1) = 2
array1(2) = 3
array1

//可变长度
val array2 = new ArrayBuffer[Int]()
array2+=1
array2+=2
array2++=array1
array2(0)

//Map
val map1 = Map("Alice" -> 10, "Bob" -> 20, "Condy" -> 30)
val map2 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 20, "Condy" -> 30)
val map3 = new mutable.HashMap[String, Int]
map2.update("Bob", 50)
map2
map2.+=(("Bob", 60))
map2
//map2("Bob") = 70


println(map1("Alice"))
println(map2("Bob"))
//map2("Bob") = 40
//map3("Tom") = 50

// 定义整型 List
val x1 = List(1,2,3,4)
x1(1)



// 定义 Set
var x2 = Set(1,3,5,7)
x2(1)

// 定义 Map
val x3 = Map("one" -> 1, "two" -> 2, "three" -> 3)
for ((k,v) <- x3){
  println(k)
}
for (k <- x3.keys){
  println(k)
}
val iterator1 = x3.iterator
while(iterator1.hasNext){
  print("map:" + iterator1.next()._2)
}

// 创建两个不同类型元素的元组
val x4 = (10, "Runoob")

// 定义 Option
val x5:Option[Int] = Some(5)

val symbols = Array("<", "-", ">")
val counts = Array(2, 10, 2)
val pairs = symbols.zip(counts)
pairs(0)._2

val fun = ceil _
println(fun(1.2))
Array(1.1,2.1).map(fun)

Array(1,20).map((x: Int) => x * 3)

//匿名函数
(x: Double) => 3 * x

//高阶函数
val maxN = max(1, 2)
def fun2(x: Int, y: Int) = max _
//Array((1, 2), (2, 3)).map(fun2 _)

//带函数参数的函数
def valueAtOneQuarter(f: (Double) => Double) = f(0.25)
valueAtOneQuarter(ceil)
valueAtOneQuarter(sqrt)

//产出函数的函数
def mulBy(factor: Double) = (x: Double) => factor * x
val quintupl = mulBy(4)
val res = quintupl(3)

//参数（类型）推断
valueAtOneQuarter( (x) => x * 3)
valueAtOneQuarter( x => x * 3)//一个参数的函数，可省略()
valueAtOneQuarter( _ * 3)//如果参数在=>右侧只出现过一次可用_替换掉它

//常见的高阶函数
"*" * 2//出现两个*

(1 to 9).map("*" * _).foreach(println _)
(1 to 9).filter( _ % 2 == 0)
(1 to 9).reduceLeft(_+_)
"Mary has a little lamb".split(" ").sortWith(_.length < _.length)
4 +: Vector(1, 2, 3)
Vector(1, 2, 3) :+ 4