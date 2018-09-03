package com.example.springboot.kotlin.models

import com.google.gson.annotations.SerializedName
import java.util.stream.Collectors

data class ResponseSearchResultsDto(
        @SerializedName("count") var array: MutableList<Map<String, Int>?>
)

data class RequestSearchDto(
        @SerializedName("searchText") var searchText: List<String>
)

data class ResponseTopResultsDto(
        private var array: MutableList<Map<String, Int>>
) {
    override fun toString(): String = if (array.isNotEmpty()) {
        array.stream().map {
            it.entries.stream().map {
                it.key.plus("|").plus(it.value)
            }.collect(Collectors.joining())
        }.collect(Collectors.joining("\n\r", "", ""))
    } else {
        ""
    }
}