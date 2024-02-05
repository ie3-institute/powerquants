package edu.ie3.quants

case class QuantityParseException(message: String, expression: String) extends Exception(s"$message:$expression")
