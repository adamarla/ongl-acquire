package com.runtitle.acquire

data class Lease(val id: String,
                 val documentType: DocumentType,
                 var primaryTerm: String? = null, // #1
                 var royalty: String? = null, // #1
                 var effectiveDate: String? = null, // #1
                 var legal: String? = null, // #2
                 var primaryTermExtension: String? = null, // #2
                 var lessor: String? = null, // #3
                 var lesseeAddress: String? = null, // #3
                 var lessorAddress: String? = null, // #3
                 var lessee: String? = null) // #3
{

    fun setCandidateFields(candidateFields: List<CandidateField>) = candidateFields
            .groupBy { it.fieldType }
            .map { setCandidateField(it.key, it.value.map { field -> field.value }) }

    private fun setCandidateField(fieldType: FieldType, fieldValues: List<String>) {
        when (fieldType) {
            FieldType.ROYALTY -> royalty = fieldValues.joinToString("|")
            FieldType.PRIMARY_TERM -> primaryTerm = fieldValues.first().let { Cleanser.cleanseTermInYears(it) }
            FieldType.EFFECTIVE_DATE -> effectiveDate =
                    fieldValues.mapNotNull { Cleanser.cleanseDate(it) }.first() + "|" +
                    fieldValues.first()
        }
    }

}

data class CandidateField(val id: String, val documentType: DocumentType,
                          val fieldType: FieldType, val value: String)

enum class FieldType {
    ROYALTY, PRIMARY_TERM, EFFECTIVE_DATE
}
