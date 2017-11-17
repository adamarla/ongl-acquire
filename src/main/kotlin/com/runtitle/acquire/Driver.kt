package com.runtitle.acquire


fun main(args: Array<String>) {

    val path = "/home/adamarla/work/runTitle/akshay"
    val repo = DocumentRepository(path)
    val documents = repo.getDocuments()
            .filter { it.id == "59d7096c68a686002eb83d49" }
            .filterNot { it.documentType == DocumentType.OTHER }
            .map { document ->
                val candidateFields = patternsOfInterest.flatMap { (fieldType, regex) ->
                    document.getFieldsOfInterest(regex).map {
                        CandidateField(document.id, document.documentType, fieldType, it)
                    }
                }
                Lease(id = document.id, documentType = document.documentType).apply {
                    setCandidateFields(candidateFields)
                }
            }

    println("Total Physical PDFs       : ${repo.docCount}")
    println("Total Documents considered: ${documents.size}")
    println("Leases                    : ${documents.filter { it.documentType == DocumentType.LEASE }.size}")
    println("Memos                     : ${documents.filter { it.documentType == DocumentType.MEMORANDUM }.size}")
    println("Amendment                 : ${documents.filter { it.documentType == DocumentType.AMENDMENT }.size}")
    println("Assignment                : ${documents.filter { it.documentType == DocumentType.ASSIGNMENT }.size}")
    println("Extension                 : ${documents.filter { it.documentType == DocumentType.EXTENSION }.size}")
    println("Ratification              : ${documents.filter { it.documentType == DocumentType.RATIFICATION }.size}")
    println("Dates detected in         : ${documents.filter { !it.effectiveDate.isNullOrEmpty() }.size}")
    println("Primary term detected in  : ${documents.filter { !it.primaryTerm.isNullOrEmpty() }.size}")
    println("Royalty detected in       : ${documents.filter { !it.royalty.isNullOrEmpty() }.size}")
}
