package com.example

import org.scalatest._

class PatternProcessorSpec extends WordSpec with Matchers {

  "The pattern processor" when {
    """given a simple pattern  of "foo %{0} is a %{1}""" should {
      val process = PatternProcessor.process("foo %{0} is a %{1}") _

      "match and return the text \"foo blah is a bar\"" in {
        process("foo blah is a bar") shouldBe "foo blah is a bar"
      }
      """match and return the text "foo blah is a very big boat"""" in {
        process("foo blah is a very big boat") shouldBe "foo blah is a very big boat"
      }
      """not match "foo blah is bar"""" in {
        val text = "foo blah is bar"
        process(text) shouldBe ""
      }
      """"not match "foo blah"""" in {
        val text = "foo blah"
        process(text) shouldBe ""
      }

      """"not match "foo blah is"""" in {
        val text = "foo blah"
        process(text) shouldBe ""
      }
    }
    """given a token capture sequence with a space limitation modifier of "foo %{0} is a %{1S0}"""" should {
      val process = PatternProcessor.process("foo %{0} is a %{1S0}") _

      """"match and return the text "foo blah is a bar"""" in {
        process("foo blah is a bar")
      }

      "not match \"foo blah is a very big boat\"" in pending
      "not match \"foo blah is bar\"" in pending
      "not match  \"foo blah\"" in pending
      "not match  \"foo blah is\"" in pending
    }
    "given a token capture sequence with a whitespace modifier of \"the %{0S1} %{1} ran away" should {
      "match and return the text \"the big brown fox ran away\"" in pending
      "not match ???" in pending
    }
    "given a token capture sequence with a greedy modifier of \"bar %{0G} foo %{1}\"" should {
      "match \"bar foo bar foo bar foo bar foo\"" in pending
      "not match ???" in pending
    }
    "given one or more text string(s)" should {
      "delimit lines with a new line character" in pending
      "terminate processing at EOF" in pending
      "write matching input lines to stdout" in pending
    }
  }
}
