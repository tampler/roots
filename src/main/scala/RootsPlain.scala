package hello

import scala.annotation.tailrec
import HelperPlain._

sealed trait RootLike[A] {
  def solution(min: A, max: A): Int
}
object RootLike {
  def apply[A](implicit root: RootLike[A]): RootLike[A] = root

  implicit val intRoot = new RootLike[Int] {
    def solution(min: Int, max: Int): Int = {
      def inner(): Int = {

        val range = Range(min, max)
        println(range)

        // This can be parallelized
        val rangeDepths = range.map(getDepth(_, 0))
        rangeDepths.max

      }
      if (min > max || min < 2 || max > 1e9) 0 else inner()
    }

  }
}

object Roots extends App {
  def program(min: Int, max: Int) = RootLike[Int].solution(min, max)

  println(program(10, 20))     // 2
  println(program(6000, 7000)) // 3

}

object HelperPlain {
  @tailrec
  def getDepth(din: Int, depth: Int): Int =
    if (din == 1) 0
    else {
      val divisible = scala.math.sqrt(din.toDouble)
      // println(divisible)

      divisible % 1 match {
        case 0 => getDepth(divisible.toInt, depth + 1)
        case _ => depth
      }
    }
}
