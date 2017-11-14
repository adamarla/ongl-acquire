package com.runtitle.acquire

import java.io.File

/**
 * Created by adamarla on 11/14/17.
 */

class ScannedDocument(val id: String, file: File) {

    private var lines = file.readLines().filter { it.isNotEmpty() }
    private var paragraphs = file.readText().split("\n\n")
            .map { it.trim().replace('\n', ' ') }
            .filter { it.isNotEmpty() }

    val documentType = lineContaining(listOf("OIL", "GAS", "LEASE"))?.let {
                if (it.contains(DocumentType.MEMORANDUM.toString())) DocumentType.MEMORANDUM
                else DocumentType.LEASE
            }?: DocumentType.OTHER

    fun getFieldsOfInterest(regex: Regex) =
        paragraphs.flatMap {
            regex.findAll(it).map { it.groups[1]!!.value }.toSet()
        }

    private fun lineContaining(words: List<String>) =
        lines.firstOrNull {
            it.toUpperCase().replace(Regex(pattern = "[^a-zA-Z ]"), replacement = "")
                    .split(delimiters = ' ').containsAll(words)
        }

}

enum class DocumentType {
    LEASE, MEMORANDUM, OTHER
}
