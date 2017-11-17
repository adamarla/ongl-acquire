package com.runtitle.acquire

import java.io.File

/**
 * Created by adamarla on 11/14/17.
 */

class ScannedDocument(val id: String, file: File) {
    private var lines = file.readLines()
    private val titleIndex = lines.indexOfFirst { tokenCapitalize(it).containsAll(listOf("OIL", "GAS", "LEASE")) }
    private var subText =
            (if (titleIndex != -1) lines.slice(titleIndex until lines.size) else lines)
                    .filter{ it.isNotBlank() }.joinToString(" ").toLowerCase()

    val documentType =
            if (titleIndex != -1)
                if (lines[titleIndex].contains(DocumentType.MEMORANDUM.toString())) DocumentType.MEMORANDUM
                else if (lines[titleIndex].contains(DocumentType.AMENDMENT.toString())) DocumentType.AMENDMENT
                else if (lines[titleIndex].contains(DocumentType.ASSIGNMENT.toString())) DocumentType.ASSIGNMENT
                else if (lines[titleIndex].contains(DocumentType.EXTENSION.toString())) DocumentType.EXTENSION
                else if (lines[titleIndex].contains(DocumentType.RATIFICATION.toString())) DocumentType.RATIFICATION
                else DocumentType.LEASE
            else DocumentType.OTHER



    fun getFieldsOfInterest(regex: Regex) = regex.findAll(subText).map { it.groups[1]!!.value }.toSet()

    private fun tokenCapitalize(input: String) =
            input.toUpperCase().replace(Regex(pattern = "[^A-Z ]"), replacement = "") .split(delimiters = ' ')

}

enum class DocumentType {
    LEASE, MEMORANDUM, AMENDMENT, ASSIGNMENT, EXTENSION, RATIFICATION, OTHER
}
