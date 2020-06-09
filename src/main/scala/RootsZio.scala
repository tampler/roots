package hello

import zio.{ UIO }
import zio.stream.Stream
import zio.console.{ putStrLn }

import HelperZio._

object RootsZio extends zio.App {
  override def run(args: List[String]) = (eff0 <*> eff1 <*> eff2).exitCode

  def program(min: Int, max: Int) =
    for {
      resp <- solution(min, max)
      out  = if (resp.isEmpty) 0 else resp.max
      _    <- putStrLn(out.toString)
    } yield out

  val eff0 = program(10, 20)       // 2
  val eff1 = program(6000, 7000)   // 3
  val eff2 = program(2, 1e9.toInt) // OOM: Java Heap on store result array

}

object HelperZio {

  // Trampoline and tail recursion are not required here, since ZIO Streams are lazily evaluated
  def getDepth(din: Int, depth: Int): Int =
    if (din == 1) 0
    else {
      val divisible = scala.math.sqrt(din.toDouble)

      divisible % 1 match {
        case 0 => getDepth(divisible.toInt, depth + 1)
        case _ => depth
      }
    }

  def solution(min: Int, max: Int) = {
    def inner() = Stream.fromIterator(Range(min, max).iterator).map(getDepth(_, 0)).runCollect

    if (min > max || min < 2 || max > 1e9) UIO(List[Int](0)) else inner()
  }
}
