# pattern-processor
An input.txt is bundled in the top level dir containing sample test strings to match so a functional test can be run as follows:

usage - sbt "run <pattern>  <newline-delimited-text-file>"

e.g.

```bash
$ sbt "run \"bar %{0G} foo %{1}\" input.txt"
```

Automated specs for the processor can also be run via sbt:

```bash
$ sbt test
```


