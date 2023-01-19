package com.compose.cocktaildakk_compose.ui.utils

import com.compose.cocktaildakk_compose.domain.model.*
import kotlin.math.abs

fun getScoreResult(
    cocktails: List<Cocktail>,
    userInfo: UserInfo,
    userCocktailWeight: UserCocktailWeight,
): List<CocktailScore> {
    return mutableListOf<CocktailScore>().apply {
        cocktails.forEach { cocktail ->
            var score = Cocktails.COCKTAIL_SCORE_ZERO
            score += calKeywordDiff(
                cocktail,
                userInfo,
            ) * userCocktailWeight.keyword * Cocktails.KEYWORD_WEIGHT
            score += calBaseDiff(
                cocktail,
                userInfo
            ) * userCocktailWeight.base * Cocktails.BASE_WEIGHT
            /** 알코올 점수 계산 */
            score += 3 - (abs(cocktail.level - userInfo.level) * userCocktailWeight.level * Cocktails.LEVEL_WEIGHT)
            // 추천 스코어 총합
            add(CocktailScore(score = score, id = cocktail.idx))
        }
    }
}

/** 기주 중복 계산 */
private fun calBaseDiff(
    cocktail: Cocktail,
    userInfo: UserInfo,
): Int {
    val difference = cocktail.base.split(',').toSet().minus(userInfo.base.toSet())
    return cocktail.base.split(',').size - difference.size
}

/** 키워드 중복 수 계산 */
private fun calKeywordDiff(
    cocktail: Cocktail,
    userInfo: UserInfo,
): Int {
    val difference = cocktail.keyword.split(',').toSet().minus(userInfo.keyword.toSet())
    return cocktail.keyword.split(',').size - difference.size
}

