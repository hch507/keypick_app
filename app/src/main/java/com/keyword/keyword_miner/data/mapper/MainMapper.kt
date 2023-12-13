package com.keyword.keyword_miner.data.mapper

import com.keyword.keyword_miner.data.dto.blogTotal.BlogTotal
import com.keyword.keyword_miner.data.dto.monthRatio.Result
import com.keyword.keyword_miner.data.dto.relKeyword.Keyword
import com.keyword.keyword_miner.domain.Model.blogTotalData.BlogData
import com.keyword.keyword_miner.domain.Model.blogTotalData.BlogTotalDataModel
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel
import com.keyword.keyword_miner.domain.Model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.Model.monthRadioData.RatioData
import com.keyword.keyword_miner.domain.Model.rankData.RankDataModel

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

    fun mapperToBlogTotal(blogTotal: BlogTotal):BlogTotalDataModel{
        var blogTotalData = BlogTotalDataModel(
            total = blogTotal.total,
            blogData = blogTotal.items.map {item ->
                BlogData(
                    date = item.postdate,
                    blogname = item.bloggername
                )

            }
        )
        return blogTotalData
    }

    fun mapperToMonthRatio(monthRatio : List<Result>):List<MonthRatioDataModel>{
        var monthRatioData = monthRatio.map {
            MonthRatioDataModel(
                title = it.title,
                ratioData = it.data.map { data ->
                    RatioData(
                        period = data.period,
                        rate = data.ratio
                    )

                }
            )
        }
        return monthRatioData
    }

    fun mapperToBlogRank(blogTotal: BlogTotal):RankDataModel{
        var rankData = blogTotal.items.map {
                it.bloggerlink
        }

        return RankDataModel(
            blogLink = rankData
        )

    }
}