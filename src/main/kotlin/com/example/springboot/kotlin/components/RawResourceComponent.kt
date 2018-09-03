package com.example.springboot.kotlin.components

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*
import java.util.stream.Collectors
import kotlin.reflect.KProperty


@Component
class RawResourceComponent {

    @Autowired
    lateinit var context: ApplicationContext

    private val logger = LoggerFactory.getLogger(javaClass)

    private fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8) = bufferedReader(charset).use { it.readText() }

    var array: Map<String, Int> by lazy {
        getMapOfWords()
    }

    fun searchWords(words: List<String>): MutableList<Map<String, Int>?> {
        logger.info("get searchWords {}", words)

        return words.stream()
                .map { it.toLowerCase() }
                .map { word ->
                    if (array.containsKey(word)) {
                        array[word]?.let { number ->
                            mapOf(word to number)
                        }
                    } else {
                        mapOf(word to 0)
                    }
                }.collect(Collectors.toList())
    }

    fun getTop(limit: Long): MutableList<Map<String, Int>> {
        logger.info("get top {}", limit)

        return array
                .entries.stream().limit(limit)
                .map { mapOf(it.key to it.value) }
                .collect(Collectors.toList())
    }

    private fun getMapOfWords(): Map<String, Int> {
        val index = hashMapOf<String, Int>()
        Arrays.stream<String>(
                context
                        .getResource("classpath:raw")
                        .inputStream
                        .readTextAndClose()
                        .split(Regex("(\\s|\\n|\r)"))
                        .toTypedArray()
        )
                .map {
                    it.replace(Regex("(\\.+|,+|\\s+|;+)"), "").toLowerCase()
                }.forEach { word ->
                    index.apply {
                        if (containsKey(word)) {
                            get(word)?.let {
                                put(word, it.plus(1))
                            }
                        } else {
                            put(word, 1)
                        }
                    }

                }
        return index.toList().sortedByDescending { it.second }.toMap()
    }
}

private operator fun Any.setValue(rawResourceComponent: RawResourceComponent, property: KProperty<*>, map: Map<String, Int>) {}