package edu.ie3.quants

/**
 * SI Base Quantity
 */
trait BaseDimension {
  self: Dimension[_] ⇒
  /**
   * SI Base Unit for this Quantity
   *
   * @return
   */
  def siUnit: SiBaseUnit

  /**
   * SI Dimension Symbol
   *
   * @return
   */
  def dimensionSymbol: String
}
