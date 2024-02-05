package edu.ie3.quants.experimental

import edu.ie3.quants.experimental.SquantsNumeric.{DoubleIsSquantsNumeric, FloatIsSquantsNumeric}

trait ScalaVersionSpecificNumericInstances {

  implicit object FloatIsSquantsNumeric extends FloatIsSquantsNumeric with Ordering.Float.IeeeOrdering
  implicit object DoubleIsSquantsNumeric extends DoubleIsSquantsNumeric with Ordering.Double.IeeeOrdering

}
