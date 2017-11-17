package com.runtitle.acquire

import java.io.File

/**
 * Created by adamarla on 11/14/17.
 */
class DocumentRepository(path: String) {

    private val idPathMap = mutableMapOf<String, String>()
    private val directory = File(path)
    val docCount: Int = directory.resolve("files").listFiles({ file -> file.name.endsWith("pdf")}).size

    init {
        directory.resolve("data.csv").readLines().map { data ->
            data.split(',').let {
                idPathMap[it[9].replace("/files/", "")] = it[8]
            }
        }
    }

    fun getDocuments() = directory.resolve("ocr").walk()
            .filter { it.name.endsWith(".txt") && idPathMap.containsKey(it.name.replace("txt", "pdf")) }
            .map { ScannedDocument(idPathMap[it.name.replace("txt", "pdf")]!!, it) }.toList()

}

