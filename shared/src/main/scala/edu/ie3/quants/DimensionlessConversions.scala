package edu.ie3.quants

object DimensionlessConversions {
  lazy val percent = Percent(1)
  lazy val each = Each(1)
  lazy val dozen = Dozen(1)
  lazy val score = Score(1)
  lazy val gross = Gross(1)
  lazy val hundred = Each(1e2)
  lazy val thousand = Each(1e3)
  lazy val million = Each(1e6)

  import scala.language.implicitConversions

  implicit class DimensionlessConversions[A](n: A)(implicit num: Numeric[A]) {
    def percent = Percent(n)

    def each = Each(n)

    def ea = Each(n)

    def dozen = Dozen(n)

    def dz = Dozen(n)

    def score = Score(n)

    def gross = Gross(n)

    def gr = Gross(n)

    def hundred = Each(num.toDouble(n) * 1e2)

    def thousand = Each(num.toDouble(n) * 1e3)

    def million = Each(num.toDouble(n) * 1e6)
  }

  /**
   * Provides an implicit conversion from Dimensionless to Double, allowing a Dimensionless value
   * to be used anywhere a Double (or similar primitive) is required
   *
   * @param d Dimensionless
   * @return
   */
  implicit def dimensionlessToDouble(d: Dimensionless): Double = d.toEach

  implicit object DimensionlessNumeric extends AbstractQuantityNumeric[Dimensionless](Dimensionless.primaryUnit) {
    /**
     * Dimensionless quantities support the times operation.
     * This method overrides the default [[squants.AbstractQuantityNumeric.times]] which throws an exception
     *
     * @param x Dimensionless
     * @param y Dimensionless
     * @return
     */
    override def times(x: Dimensionless, y: Dimensionless) = x * y
  }
}
