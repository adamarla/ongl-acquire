package com.runtitle.acquire

import java.io.File

/**
 * Created by adamarla on 11/14/17.
 */
class DocumentRepository(path: String) {

    private val idPathMap = mutableMapOf<String, String>()
    private val directory = File(path)

    init {
        directory.resolve("data.csv").readLines().map { data ->
            data.split(',').let {
                idPathMap[it[9].replace("/files/", "")] = it[8]
            }
        }
        println("Id-Path Map has ${idPathMap.size} entries")
    }

    fun getDocuments() = directory.resolve("ocr").walk()
            .filter { it.name.endsWith(".txt") && idPathMap.containsKey(it.name.replace("txt", "pdf")) }
            .map { ScannedDocument(idPathMap[it.name.replace("txt", "pdf")]!!, it) }.toList()

}

data class Lease(val primaryTerm: Int, // #1
                 val lessor: String, // #3
                 val lesseeAddress: String, // #3
                 val royalty: String, // #1
                 val legal: String, // #2
                 val primaryTermExtension: String, // #2
                 val lessorAddress: String, // #3
                 val effectiveDate: String, // #1
                 val id: String,
                 val file: String,
                 val lessee: String) // #3