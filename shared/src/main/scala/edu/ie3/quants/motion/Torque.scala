package edu.ie3.quants.motion

import edu.ie3.quants.{ PrimaryUnit, SiBaseUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants.mass.{ MomentOfInertia, Pounds }
import edu.ie3.quants.space.{ Feet, Meters }
import edu.ie3.quants.{ AbstractQuantityNumeric, Dimension, Quantity }

/**
 *
 * @author paxelord
 * @since 1.3
 *
 * @param value Double
 */
final class Torque private (val value: Double, val unit: TorqueUnit)
  extends Quantity[Torque] {

  def dimension = Torque

  def toNewtonMeters = to(NewtonMeters)
  def toPoundFeet = to(PoundFeet)

  def /(that: MomentOfInertia): AngularAcceleration = {
    RadiansPerSecondSquared(toNewtonMeters / that.toKilogramsMetersSquared)
  }
}

object Torque extends Dimension[Torque] {
  private[motion] def apply[A](n: A, unit: TorqueUnit)(implicit num: Numeric[A]) = new Torque(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Torque"
  def primaryUnit = NewtonMeters
  def siUnit = NewtonMeters
  def units = Set(NewtonMeters, PoundFeet)
}

trait TorqueUnit extends UnitOfMeasure[Torque] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = {
    Torque(num.toDouble(n), this)
  }
}

object NewtonMeters extends TorqueUnit with PrimaryUnit with SiBaseUnit {
  val symbol = Newtons.symbol + "‧" + Meters.symbol
}

object PoundFeet extends TorqueUnit {
  val symbol = Pounds.symbol + "‧" + Feet.symbol
  val conversionFactor = PoundForce.conversionFactor * Feet.conversionFactor
}

object TorqueConversions {
  lazy val newtonMeters = NewtonMeters(1)
  lazy val poundFeet = PoundFeet(1)

  implicit class TorqueConversions[A](val n: A) extends AnyVal {
    def newtonMeters(implicit num: Numeric[A]) = NewtonMeters(n)
    def poundFeet(implicit num: Numeric[A]) = PoundFeet(n)
  }

  implicit object TorqueNumeric extends AbstractQuantityNumeric[Torque](Torque.primaryUnit)
}
