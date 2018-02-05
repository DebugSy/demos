val numbers = List(1,2,3,4,5,6,7,8,9,0)
val filter = numbers.filter( i => i % 2 ==0)

def isEven(i: Int): Boolean = i % 2 == 0

val filter2 = numbers.filter(isEven _)

val zip1 = List(1,2,3)
val zip2 = List("a", "b", "c")
val zip = zip1.zip(zip2)

val partition = numbers.partition(i => i % 2 == 0)

val find = numbers.find(i => i > 2)

numbers.drop(2)

numbers.dropWhile( i => i < 6)

val nestedNumbers = List(List(1, 2), List(3, 4))
nestedNumbers.flatMap(x => x.map((a: Int) => a * 2))


def f(s: String) = "f(" + s + ")"
def g(s: String) = "g(" + s + ")"
val fComposeG = f _ compose g _
fComposeG("demo")

val fAndThenG = f _ andThen g _
fAndThenG("demo")

val one: PartialFunction[Int, String] = {case 1 => "one"}
one.isDefinedAt(1)

one(1)

class Animal { val sound = "restle"}
class Bird extends Animal {  override val sound = "call"}
class Chicken extends Bird {  override val sound = "cluck"}

val getTweet: (Bird => String) = ((a: Animal) => a.sound )

def biophony[T <: Animal](thinsgs: Seq[T]) = thinsgs.map(_.sound)

val flock = List(new Bird, new  Bird)
new Chicken:: flock

def count(l: List[_]) = l.size
