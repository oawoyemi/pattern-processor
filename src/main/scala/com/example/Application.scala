package com.example

import scala.io.Source

object Application extends App {
  if (args.length != 2) println("usage: {pattern} {text}")

  for (line <- Source.fromFile(args(1)).getLines()) {
    PatternProcessor.process(args(0))(line).foreach(println)
  }
}
