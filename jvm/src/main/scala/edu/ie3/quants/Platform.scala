package edu.ie3.quants

object Platform {
  /**
   * Helper function to achieve uniform Double formatting over JVM, JS, and native platforms.
   * Simple Double.toString will format 1.0 as "1.0" on JVM and as "1" on JS
   * @param d Double number to be formatted
   * @return
   */
  private[quants] def crossFormat(d: Double): String = {
    if (d.toLong == d) {
      "%.1f".format(d)
    }
    else {
      d.toString
    }
  }
}
