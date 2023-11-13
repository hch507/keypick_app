package com.keyword.keyword_miner.data.mapper

import com.keyword.keyword_miner.data.dto.blogTotal.BlogTotal
import com.keyword.keyword_miner.data.dto.relKeyword.Keyword
import com.keyword.keyword_miner.data.dto.relKeyword.RelKeyword
import com.keyword.keyword_miner.domain.Model.BlogTotalDataModel
import com.keyword.keyword_miner.domain.Model.RelKeywordDataModel

class MainMapper {

    fun mapperToRelkeyword(relKeyword: List<Keyword>):List<RelKeywordDataModel>{
        return relKeyword.map {
            RelKeywordDataModel(
                relKeyword= it.relKeyword,
                monthlyPcQcCnt = it.monthlyPcQcCnt,
                monthlyMobileQcCnt =it.monthlyMobileQcCnt
            )
        }
    }

    fun mapperToBlogTotal(blogTotal: BlogTotal):List<BlogTotalDataModel>{
        return 
    }
}