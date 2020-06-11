package hello

import Tree._

object Tree {

  trait BinTree[+A] {

    def put[B >: A](n: B, tree: BinTree[B])(implicit ev: Ordering[B]): BinTree[B] = tree match {
      case Empty   => new Node(n, Empty, Empty)
      case Leaf(v) => new Node(v, Empty, Empty)
      case Node(elem, left: BinTree[B], right: BinTree[B]) => {
        if (ev.lt(elem, n)) new Node(elem, put(n, left), right)
        else new Node(elem, left, put(n, right))
      }
    }

  }
  object BinTree {
    def apply[A] = new BinTree[A] {}
  }

  case class Node[A](v: A, l: BinTree[A], r: BinTree[A]) extends BinTree[A]
  case class Leaf[A](v: A)                               extends BinTree[A]
  case object Empty                                      extends BinTree[Nothing]

}

object Test extends App {

  val tree = new BinTree[Int] {}

  def fold[A, B](f1: A => B)(f2: (A, B, B) => B)(t: BinTree[A]): B = t match {
    case Empty             => 0.asInstanceOf[B]
    case Leaf(value)       => f1(value)
    case Node(value, l, r) => f2(value, fold(f1)(f2)(l), fold(f1)(f2)(r)) //post order
  }

  val dummyTree = Node(1, Leaf(2), Node(3, Leaf(5), Node(4, Leaf(42), Leaf(50))))

  println(fold(identity[Int])(_ + _ + _)(dummyTree)) // 107

  val inputTree = Node(33, Node(12, Leaf(5), Leaf(20)), Node(45, Leaf(39), Leaf(50)))

  println(fold(identity[Int])(_ + _ + _)(inputTree)) // 204

  val list = List(33, 12, 5, 20, 45, 39, 50)
  // Init tree
  val res = list.foldLeft(BinTree[Int].put(0, Leaf(0)))((a, b) => a.put(b, a))
  println(res)

  // Fold tree
  println(fold(identity[Int])(_ + _ + _)(res)) // 204

}
