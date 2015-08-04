package com.example

object PatternProcessor {
  val sequenceRegex = """%\{[0-9]S?[0-9]?}""".r

  def process(pattern: String)(text: String): String = {
    val tokens = sequenceRegex.findAllIn(pattern).toList
//    val parsetoRegex = tokens.map(_=> "(.+)")
    val regex = sequenceRegex.replaceAllIn(pattern, "(.+)").r
    regex.findFirstIn(text) match {
      case Some(`text`) => text
      case _ => ""
    }
  }
}
