package kantan.csv

import _root_.cats._
import _root_.cats.functor.Contravariant

/** Declares various type class instances for bridging `kantan.csv` and `cats`. */
package object cats {
  // - DecodeResult ----------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** `Monad` instance for `DecodeResult`. */
  implicit val decodeResultInstances = new Monad[DecodeResult] {
    override def flatMap[A, B](fa: DecodeResult[A])(f: A ⇒ DecodeResult[B]) = fa.flatMap(f)
    override def map[A, B](fa: DecodeResult[A])(f: A ⇒ B) = fa.map(f)
    override def pure[A](x: A) = DecodeResult(x)
  }

  /** `Eq` instance for `DecodeResult`. */
  implicit def decodeResultEq[A: Eq] = new Eq[DecodeResult[A]] {
    override def eqv(x: DecodeResult[A], y: DecodeResult[A]): Boolean = (x, y) match {
      case (DecodeResult.Success(a), DecodeResult.Success(b))                   ⇒ Eq[A].eqv(a, b)
      case (DecodeResult.ReadFailure(l1, c1), DecodeResult.ReadFailure(l2, c2)) ⇒ l1 == l2 && c1 == c2
      case (DecodeResult.DecodeFailure, DecodeResult.DecodeFailure)             ⇒ true
      case _                                                                    ⇒ false
    }
  }


  // - CellCodec -------------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** `Functor` instance for `CellDecoder`. */
  implicit val cellDecoder = new Functor[CellDecoder] {
    override def map[A, B](fa: CellDecoder[A])(f: A ⇒ B) = fa.map(f)
  }

  /** `Contravariant` instance for `CellEncoder`. */
  implicit val cellEncoder = new Contravariant[CellEncoder] {
    override def contramap[A, B](fa: CellEncoder[A])(f: B ⇒ A) = fa.contramap(f)
  }



  // - RowCodec --------------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** `Functor` instance for `RowDecoder`. */
  implicit val rowDecoder = new Functor[RowDecoder] {
    override def map[A, B](fa: RowDecoder[A])(f: A ⇒ B) = fa.map(f)
  }

  /** `Contravariant` instance for `RowEncoder`. */
  implicit val rowEncoder = new Contravariant[RowEncoder] {
    override def contramap[A, B](fa: RowEncoder[A])(f: B ⇒ A) = fa.contramap(f)
  }


  // - CSV input / output ----------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** `Contravariant` instance for `CsvInput`. */
  implicit val csvInput: Contravariant[CsvInput] = new Contravariant[CsvInput] {
    override def contramap[A, B](r: CsvInput[A])(f: B ⇒ A) = r.contramap(f)
  }

  /** `Contravariant` instance for `CsvOutput`. */
  implicit val csvOutput: Contravariant[CsvOutput] = new Contravariant[CsvOutput] {
    override def contramap[A, B](r: CsvOutput[A])(f: B ⇒ A) = r.contramap(f)
  }
}