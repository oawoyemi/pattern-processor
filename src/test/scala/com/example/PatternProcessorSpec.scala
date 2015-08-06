package com.example

import org.scalatest._

class PatternProcessorSpec extends WordSpec with Matchers {

  "The pattern processor" when {

    "given a simple pattern  of \"foo %{0} is a %{1}\"" should {
      val process = PatternProcessor.process("foo %{0} is a %{1}") _

      "match \"foo blah is a bar\"" in {
        val text = "foo blah is a bar"
        process(text) shouldBe Some(text)
      }
      """match "foo blah is a very big boat"""" in {
        val text = "foo blah is a very big boat"
        process(text) shouldBe Some(text)
      }
      """not match "foo blah is bar"""" in {
        val text = "foo blah is bar"
        process(text) shouldBe None
      }
      """"not match "foo blah"""" in {
        val text = "foo blah"
        process(text) shouldBe None
      }

      """"not match "foo blah is"""" in {
        val text = "foo blah"
        process(text) shouldBe None
      }
    }

    "given a pattern with a whitespace modifier of \"foo %{0} is a %{1S0}\"" should {
      val process = PatternProcessor.process("foo %{0} is a %{1S0}") _

      "match \"foo blah is a bar\"" in {
        val text = "foo blah is a bar"
        process(text) shouldBe Some(text)
      }
      "not match \"foo blah is a very big boat\"" in {
        process("foo blah is a very big boat") shouldBe None
      }
      "not match\"foo blah is bar\"" in {
        process("foo blah is bar") shouldBe None
      }
      "not match \"foo blah\"" in {
        process("foo blah") shouldBe None
      }
      "not match \"foo blah is\"" in {
        process("foo blah is") shouldBe None
      }
    }

    "given a pattern with a whitespace modifier of \"the %{0S1} %{1} ran away\"" should {
      val process = PatternProcessor.process("the %{0S1} %{1} ran away") _

      "match  \"the big brown fox ran away\"" in {
        val text = "the big brown fox ran away"
        process(text) shouldBe Some(text)
      }

      "not match \"the fox ran away\"" in {
        process("the fox ran away") shouldBe None
      }
    }
    "given a pattern with a whitespace modifier of \"the %{0S2} fox %{1} away\"" should {
      val process = PatternProcessor.process("the %{0S2} fox %{1} away") _

      "match \"the big brown fast fox ran away\"" in {
        val text = "the big brown fast fox ran away"
        process(text) shouldBe Some(text)
      }

      "not match \"the big fox ran away\"" in {
        process("the big fast fox ran away") shouldBe None
      }
    }
    "given a pattern with a greedy modifier of \"bar %{0G} foo %{1}\"" should {
      val process = PatternProcessor.process("bar %{0G} foo %{1}") _

      "match \"bar foo bar foo bar foo bar foo\"" in {
        val text = "bar foo bar foo bar foo bar foo"
        process(text) shouldBe Some(text)
      }
      "not match \"foo blah is a very big boat\"" in {
        process("foo blah is a very big boat") shouldBe None
      }
      "not match\"foo blah is bar\"" in {
        process("foo blah is bar") shouldBe None
      }
      "not match \"foo blah\"" in {
        process("foo blah") shouldBe None
      }
      "not match \"bar bar foo blah is\"" in {
        process("bar foo blah is") shouldBe None
      }
    }

    "given one or more text string(s)" should {
      "delimit lines with a new line character" in pending
      "terminate processing at EOF" in pending
      "write matching input lines to stdout" in pending
    }
  }
}

