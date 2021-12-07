# [Advent Of Code 2021](https://adventofcode.com/2021) using Clojure

## Goals and quality to expect

- write a solid part-1 in the attempt to get an easier part-2
- write tests for the steps explained in the story
- naming is hard, but don't spend too much time
- commit after part-1, refactor for part-2 (tests help with that)
- re-factoring things from previous days is allowed e.g. for parts no
  good solution came to mind but some spark of inspiration hits later
  ("shower thoughts")

## Tools/development

Run the tests

```console
# lein kaocha
```

Generate a new day:

- Download the input for the day and put it at `/tmp/input`
- Run `./gen.bb #` where `#` is the day; this moves the input at the
  right place and creates the source and test from the templates under
  `./tmpl`
- Start the tests for the day (the gen-script outputs this):
  `lein kaocha --watch --focus-meta :lib --focus-meta :day-##`
