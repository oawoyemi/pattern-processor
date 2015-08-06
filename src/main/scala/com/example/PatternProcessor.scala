package com.example

object CaptureSequence {
  def o(s: String) = if (s.isEmpty) None else Some(s)

  def apply(i: String, m: String, q: String): CaptureSequence =
    new CaptureSequence(i.toInt, o(m).map(_.toSeq.head), o(q).map(_.toInt))
}

case class CaptureSequence(index: Int, modifier: Option[Char], quantifier: Option[Int])

object PatternProcessor {
  val sequenceTokens = """%\{(\d+)([SG]?)(\d*)\}""".r

  def asRegex(token: CaptureSequence) = token match {
    case CaptureSequence(_, Some('S'), Some(0))          => """(\S*)"""
    case CaptureSequence(_, Some('S'), Some(quantifier)) => """(\w+( ){1}\w*)""" + s"{$quantifier}"
    case CaptureSequence(_, Some('G'), _)                => """(.+)"""
    case _                                               => """(.+?)"""
  }

  def parseCaptureSequences(pattern: String) =
    for (s <- sequenceTokens findAllIn pattern) yield {
      val sequenceTokens(i, m, q) = s
      s -> CaptureSequence(i, m, q)
    }

  def process(pattern: String)(text: String): Option[String] = {
    val patternAsRegex = parseCaptureSequences(pattern).foldLeft(pattern) { (a, b) =>
      a.replace(b._1, asRegex(b._2))
    } + "$"

    patternAsRegex.r.findFirstIn(text)
  }
}
