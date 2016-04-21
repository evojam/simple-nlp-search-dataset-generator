# simple-nlp-search-dataset-generator

1. Put data under `./src/main/resources/`
2. Set `sampleSize` at `./src/main/scala/com/evojam/nlp/Generator.scala`
3. Call `sbt run` - will generate the dataset file - `./out.txt`.

e.g.

    horror MOVIE_GENRE
    in PREPOSITION
    edwards THEATER_NAME
    theatre THEATER_NAME
    between PREPOSITION
    2016-01-04 TIME_EXPRESSION
    and PREPOSITION
    2016-01-06 TIME_EXPRESSION
