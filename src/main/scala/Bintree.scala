package hello

import Tree._

object Tree {

  trait BinTree[+A] {

    def left(): Option[BinTree[A]] = this match {
      case n: Node[A] => Some(n.l)
      case l: Leaf[A] => None
      case Empty      => None
    }
    def right(): Option[BinTree[A]] = this match {
      case n: Node[A] => Some(n.r)
      case l: Leaf[A] => None
      case Empty      => None

    }

    // def put(t: BinTree[A], v: Node[A]): BinTree[A] = t match {
    //   case Empty => new Node(v, Empty, Empty)
    //   case Node(elem: Node[A], left: BinTree[A], right: BinTree[A]) => {
    //     if (v < elem) new Node(elem, put(left, v), right)
    //     else new Node[A](elem, left, put(right, v))
    //   }
    // }

    def get: Option[A] = this match {
      case n: Node[A] => Some(n.v)
      case l: Leaf[A] => Some(l.v)
      case Empty      => None
    }

  }

  case class Node[A](v: A, l: BinTree[A], r: BinTree[A]) extends BinTree[A]
  case class Leaf[A](v: A)                               extends BinTree[A]
  case object Empty                                      extends BinTree[Nothing]

}

object Test extends App {

  val tree = new BinTree[Int] {}

  def fold[A, B](f1: A => B)(f2: (A, B, B) => B)(t: BinTree[A]): B = t match {
    case Leaf(value)       => f1(value)
    case Node(value, l, r) => f2(value, fold(f1)(f2)(l), fold(f1)(f2)(r)) //post order
  }

  val n0 = Node(1, Leaf(2), Node(3, Leaf(5), Node(4, Leaf(42), Leaf(50))))

  println(fold(identity[Int])(_ + _ + _)(n0)) // 107

  val inputTree = Node(33, Node(12, Leaf(5), Leaf(20)), Node(45, Leaf(39), Leaf(50)))

  println(fold(identity[Int])(_ + _ + _)(inputTree)) // 204
}
