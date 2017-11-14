package com.runtitle.acquire

/**
 * Created by adamarla on 11/14/17.
 */

enum class FieldOfInterest {
    ROYALTY, PRIMARY_TERM, EFFECTIVE_DATE
}

data class PatternOfInterest(val fieldOfInterest: FieldOfInterest, val regex: Regex)

val patternsOfInterest = listOf(
        PatternOfInterest(FieldOfInterest.EFFECTIVE_DATE, Regex("""(\d\d?.?.? day of .*? \d{4})""")),
        PatternOfInterest(FieldOfInterest.EFFECTIVE_DATE,
                Regex("""(from (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec).*? \d{4})""")),
        PatternOfInterest(FieldOfInterest.ROYALTY, Regex("""([1I]/\d)""")),
        PatternOfInterest(FieldOfInterest.PRIMARY_TERM, Regex("""for a term of (.*?) years"""))
)

