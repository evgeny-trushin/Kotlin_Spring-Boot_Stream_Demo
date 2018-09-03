package com.example.springboot.kotlin.controllers

import com.example.springboot.kotlin.components.RawResourceComponent
import com.example.springboot.kotlin.models.RequestSearchDto
import com.example.springboot.kotlin.models.ResponseSearchResultsDto
import com.example.springboot.kotlin.models.ResponseTopResultsDto
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController {

    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var rawResource: RawResourceComponent

    @RequestMapping(
            value = ["/counter-api/search"],
            produces = ["application/json"],
            method = [RequestMethod.POST]
    )
    fun getLookUpResults(@RequestBody words: RequestSearchDto): String
            = gson.toJson(ResponseSearchResultsDto(rawResource.searchWords(words.searchText)))

    @GetMapping(value = ["/counter-api/top/{limit}"], produces = ["text/csv"])
    fun getLookUpTextTop(@PathVariable("limit") limit: Long): String
            = ResponseTopResultsDto(rawResource.getTop(limit)).toString()

    @GetMapping(value = ["/counter-api/top/{limit}"], produces = ["application/json"])
    fun getLookUpObjectTop(@PathVariable("limit") limit: Long)
            = ResponseTopResultsDto(rawResource.getTop(limit))

}