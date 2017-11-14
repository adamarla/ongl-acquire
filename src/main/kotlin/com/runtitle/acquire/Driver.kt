package com.runtitle.acquire


fun main(args: Array<String>) {

    val path = "/home/adamarla/work/runTitle/akshay"
    DocumentRepository(path).getDocuments()
            .filter { it.documentType in listOf(DocumentType.LEASE, DocumentType.MEMORANDUM) }
            .map { document ->
                patternsOfInterest.map { pattern ->
                    document.getFieldsOfInterest(pattern.regex).map {
                        println("${document.id}|${document.documentType}|${pattern.fieldOfInterest}|$it")
                    }
                }
            }
}
