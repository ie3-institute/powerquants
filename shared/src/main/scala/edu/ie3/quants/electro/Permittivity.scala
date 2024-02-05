package edu.ie3.quants.electro

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants.space.Meters
import edu.ie3.quants.{ AbstractQuantityNumeric, Dimension, Length, Quantity }

/**
 *
 * @author Nicolas Vinuesa
 * @since 1.4
 *
 * @param value Double
 */
final class Permittivity private (val value: Double, val unit: PermittivityUnit)
  extends Quantity[Permittivity] {

  def dimension = Permittivity

  def *(that: Length): Capacitance = Farads(this.toFaradsMeters * that.toMeters)

  def toFaradsMeters = to(FaradsPerMeter)
}

object Permittivity extends Dimension[Permittivity] {
  private[electro] def apply[A](n: A, unit: PermittivityUnit)(implicit num: Numeric[A]) = new Permittivity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Permittivity"
  def primaryUnit = FaradsPerMeter
  def siUnit = FaradsPerMeter
  def units = Set(FaradsPerMeter)
}

trait PermittivityUnit extends UnitOfMeasure[Permittivity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Permittivity(n, this)
}

object FaradsPerMeter extends PermittivityUnit with PrimaryUnit with SiUnit {
  val symbol = Farads.symbol + "/" + Meters.symbol
}

object PermittivityConversions {
  lazy val faradPerMeter = FaradsPerMeter(1)

  implicit class PermittivityConversions[A](n: A)(implicit num: Numeric[A]) {
    def faradsPerMeter = FaradsPerMeter(n)
  }

  implicit object PermittivityNumeric extends AbstractQuantityNumeric[Permittivity](Permittivity.primaryUnit)
}
