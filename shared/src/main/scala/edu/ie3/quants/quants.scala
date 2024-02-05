import edu.ie3.quants.time.Frequency

/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

/**
 * ==Squants==
 * The Scala API for Quantities, Units of Measure and Dimensional Analysis
 *
 * ==Overview==
 * Squants is a framework of data types and a domain specific language (DSL) for representing Quantities,
 * their Units of Measure, and their Dimensional relationships.
 * The API supports typesafe dimensional analysis, improved domain models and more.
 * All types are immutable and thread-safe.
 *
 * Typedefs and implicits for common usages
 *
 * @author garyKeorkunian
 * @version 0.1
 * @since 0.1
 *
 */
package object quants {

  type QuantitySeries[A <: Quantity[A]] = IndexedSeq[QuantityRange[A]]

  /* Quantity Types brought into scope with just squants._ */

  /* SI Base Quantities and their Base Units */
  type Length = edu.ie3.quants.space.Length
  val Meters = edu.ie3.quants.space.Meters
  type Mass = edu.ie3.quants.mass.Mass
  val Kilograms = edu.ie3.quants.mass.Kilograms
  type Time = edu.ie3.quants.time.Time
  val Seconds = edu.ie3.quants.time.Seconds
  type ElectricCurrent = edu.ie3.quants.electro.ElectricCurrent
  val Amperes = edu.ie3.quants.electro.Amperes
  type Temperature = edu.ie3.quants.thermal.Temperature
  val Kelvin = edu.ie3.quants.thermal.Kelvin
  type ChemicalAmount = edu.ie3.quants.mass.ChemicalAmount
  val Moles = edu.ie3.quants.mass.Moles
  type LuminousIntensity = edu.ie3.quants.photo.LuminousIntensity
  val Candelas = edu.ie3.quants.photo.Candelas

  /* Common Derived Quantities */
  type Angle = edu.ie3.quants.space.Angle
  val Radians = edu.ie3.quants.space.Radians
  type SolidAngle = edu.ie3.quants.space.SolidAngle
  val SquareRadians = edu.ie3.quants.space.SquaredRadians

  type Area = edu.ie3.quants.space.Area
  type Volume = edu.ie3.quants.space.Volume

  type Density = edu.ie3.quants.mass.Density

  type Velocity = edu.ie3.quants.motion.Velocity
  type Acceleration = edu.ie3.quants.motion.Acceleration
  type Jerk = edu.ie3.quants.motion.Jerk
  type Momentum = edu.ie3.quants.motion.Momentum
  type Force = edu.ie3.quants.motion.Force
  type MassFlow = edu.ie3.quants.motion.MassFlow
  type VolumeFlow = edu.ie3.quants.motion.VolumeFlow

  type Energy = edu.ie3.quants.energy.Energy
  type Power = edu.ie3.quants.energy.Power
  type PowerRamp = edu.ie3.quants.energy.PowerRamp

  /* Market Quantities */
  type Money = edu.ie3.quants.market.Money
  type Price[A <: Quantity[A]] = edu.ie3.quants.market.Price[A]

  /**
   * Provides implicit conversions that allow Doubles to lead in * and / by Time operations
   * {{{
   *    1.5 * Kilometers(10) should be(Kilometers(15))
   * }}}
   *
   * @param d Double
   */
  implicit class SquantifiedDouble(d: Double) {
    def *[A <: Quantity[A]](that: A): A = that * d

    def *[A](that: SVector[A]): SVector[A] = that * d

    def *[A <: Quantity[A]](that: Price[A]): Price[A] = that * d

    def /(that: Time): Frequency = Each(d) / that

    def per(that: Time): Frequency = /(that)
  }

  /**
   * Provides implicit conversions that allow Longs to lead in * and / by Time operations
   * {{{
   *    5 * Kilometers(10) should be(Kilometers(15))
   * }}}
   *
   * @param l Long
   */
  implicit class SquantifiedLong(l: Long) {
    def *[A <: Quantity[A]](that: A): A = that * l.toDouble

    def *[A](that: SVector[A]): SVector[A] = that * l.toDouble

    def *[A <: Quantity[A]](that: Price[A]): Price[A] = that * l.toDouble

    def /(that: Time) = Each(l) / that

    def per(that: Time): Frequency = /(that)
  }

  /**
   * Provides implicit conversions that allow Int to lead in * and / by Time operations
   * {{{
   *    5 * Kilometers(10) should be(Kilometers(15))
   * }}}
   *
   * @param l Int
   */
  implicit class SquantifiedInt(l: Int) {
    def *[A <: Quantity[A]](that: A): A = that * l.toDouble

    def *[A](that: SVector[A]): SVector[A] = that * l.toDouble

    def *[A <: Quantity[A]](that: Price[A]): Price[A] = that * l.toDouble

    def /(that: Time) = Each(l) / that

    def per(that: Time): Frequency = /(that)
  }

  /**
   * Provides implicit conversions that allow BigDecimals to lead in * and / by Time operations
   * {{{
   *    BigDecimal(1.5) * Kilometers(10) should be(Kilometers(15))
   * }}}
   *
   * @param bd BigDecimal
   */
  implicit class SquantifiedBigDecimal(bd: BigDecimal) {
    def *[A <: Quantity[A]](that: A): A = that * bd.toDouble

    def *[A](that: SVector[A]): SVector[A] = that * bd.toDouble

    def *[A <: Quantity[A]](that: Price[A]): Price[A] = that * bd.toDouble

    def /(that: Time) = Each(bd) / that

    def per(that: Time): Frequency = /(that)
  }
}
