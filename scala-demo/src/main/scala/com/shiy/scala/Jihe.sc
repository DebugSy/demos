import scala.collection.mutable.ListBuffer

val lst1 = List(1,2,3)
0::lst1
lst1.::(0)
0 +: lst1
lst1.+:(0)

lst1 :+ 0
0 +: lst1

val lst2 = List(4,5,6)
lst1 ++ lst2
lst1 ++: lst2

val lst3 = ListBuffer(1,2,3)
lst3 += 4
lst3.append(5)