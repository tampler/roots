package hello

import zio.stream._
import zio.console.{ putStrLn }

import HelperZio._
import zio.{ UIO }

object RootsZio extends zio.App {
  override def run(args: List[String]) = (eff0 <*> eff1).exitCode

  def program(min: Int, max: Int) =
    for {
      resp <- solution(min, max)
      out  = resp.max
      _    <- putStrLn(out.toString)
    } yield out

  val eff0 = program(10, 20)     // 2
  val eff1 = program(6000, 7000) // 3

}

object HelperZio {

  // Trampoline and tail recursion is not required here, since ZIO Streams are lazily evaluated
  def getDepth(din: Int, depth: Int): Int =
    if (din == 1) 0
    else {
      val divisible = scala.math.sqrt(din.toDouble)

      divisible % 1 match {
        case 0 => getDepth(divisible.toInt, depth + 1)
        case _ => depth
      }
    }

  def solution(min: Int, max: Int): UIO[List[Int]] = {
    def inner(): UIO[List[Int]] = Stream.fromIterable(Range(min, max)).map(getDepth(_, 0)).runCollect

    if (min < 2 || max > 1e9) UIO(List[Int](0)) else inner()
  }
}
