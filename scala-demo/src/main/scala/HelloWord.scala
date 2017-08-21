object HelloWord {
  def main(args: Array[String]): Unit = {
    println("Hello World!")

    val name = "xiaoming"

    println(s"My name is ${name}")

    println("hello".intersect("world"))

    var d = function1(100, 20)
    println(d)

    d = 200;
    println(d)

    var a,b,c = 1
    println(a + b + c)

  }

  def function1(x: Int, y: Double): Double = {
    return x + y;
  }

}

class HelloWord{
  def main(args: Array[String]): Unit = {
    println("Hello World!")
  }
}