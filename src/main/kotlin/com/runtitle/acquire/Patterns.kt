package com.runtitle.acquire

/**
 * Created by adamarla on 11/14/17.
 */

val monthName = """jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec"""

val patternsOfInterest = listOf(
        Pair(FieldType.EFFECTIVE_DATE, Regex("""(($monthName).{0,9}?\d\d?.{1,5}?\d{4})""")),
        Pair(FieldType.EFFECTIVE_DATE, Regex("""(\d\d?.{1,5}?day of ($monthName).{0,9}?\d{4})""")),
        Pair(FieldType.ROYALTY, Regex("""([1I]/\d)""")),
        Pair(FieldType.PRIMARY_TERM, Regex("""for a term of (.{1,20}) year\(?s\)?"""))
)

object Cleanser {

    fun cleanseTermInYears(input: String) = Regex("""\d\d?""").find(input)?.value

    /**
     * November 5, 2016|April 13th, 2017
     * 20th day of July, 2017
     *
     * @return date string in yyyyMMdd format
     */
    fun cleanseDate(input: String): String? =
            (if (input.contains("day of")) Regex("""(\d\d?).*($monthName).*(\d{4})""").find(input)
            else Regex("""($monthName).*(\d\d?).*(\d{4})""").find(input))
                    ?.groupValues?.let { toYYYYMMDD(it.slice(1 until it.size)) }

    private fun toYYYYMMDD(components: List<String>) =
            components.firstOrNull { it.matches(Regex("\\d{4}")) } +
                    components.firstOrNull { it.matches(Regex("[A-Za-z]*")) }?.let { monthNameToNumber(it)} +
                    components.firstOrNull { it.matches(Regex("\\d\\d?")) }?.padStart(2, '0')

    private fun monthNameToNumber(name: String) =
            (monthName.split('|').indexOfFirst { name.startsWith(it) } + 1).toString()
                    .padStart(2, '0')
}