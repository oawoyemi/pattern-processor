package com.example

object CaptureSequence {
  def o(s: String) = if (s.isEmpty) None else Some(s)

  def apply(i: String, m: String, q: String, s: String): CaptureSequence =
    new CaptureSequence(i.toInt, o(m).map(_.toSeq.head), o(q).map(_.toInt), s)
}

case class CaptureSequence(index: Int, modifier: Option[Char], quantifier: Option[Int], sequenceAsString: String)

object PatternProcessor {
  val sequenceTokens = """%\{(\d+)([SG]?)(\d*)\}""".r

  def process(pattern: String)(text: String): Option[String] = {
    val patternAsRegex = parseCaptureSequences(pattern)
                           .foldLeft(pattern)(replaceSequence(asRegex)) + "$"
    patternAsRegex.r.findFirstIn(text)
  }

  def parseCaptureSequences(pattern: String) = for (s <- sequenceTokens findAllIn pattern) yield {
    val sequenceTokens(index, modifier, quantifier) = s
    CaptureSequence(index, modifier, quantifier, s)
  }

  def asRegex(token: CaptureSequence) = token match {
    case CaptureSequence(_, Some('S'), Some(0), _)          => """(\S*)"""
    case CaptureSequence(_, Some('S'), Some(quantifier), _) => """(\w+( ){1}\w*)""" + s"{$quantifier}"
    case CaptureSequence(_, Some('G'), _, _)                => """(.+)"""
    case _                                                  => """(.+?)"""
  }

  def replaceSequence(f: CaptureSequence => String)(pattern: String,
                                                    sequence: CaptureSequence) =
    pattern.replace(sequence.sequenceAsString, f(sequence))
}
