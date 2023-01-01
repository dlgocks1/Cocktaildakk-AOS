package com.compose.cocktaildakk_compose.domain.model

import kotlin.math.abs

class Cocktails(private val cocktails: List<Cocktail>) : List<Cocktail> by cocktails {

    /** 사용자 정보를 이용하여 스코어 정보를 반환한다.
     * @param userInfo 유저 정보
     * @return 점수, 인덱스가 담긴 리스트 */
    fun getScoreResult(
        userInfo: UserInfo,
        userCocktailWeight: UserCocktailWeight,
    ): List<CocktailScore> {
        return mutableListOf<CocktailScore>().apply {
            cocktails.forEach { cocktail ->
                var score = COCKTAIL_SCORE_ZERO
                score += calKeywordDiff(
                    cocktail,
                    userInfo,
                ) * userCocktailWeight.keyword * KEYWORD_WEIGHT
                score += calBaseDiff(cocktail, userInfo) * userCocktailWeight.base * BASE_WEIGHT
                /** 알코올 점수 계산 */
                score += 3 - (abs(cocktail.level - userInfo.level) * userCocktailWeight.level * LEVEL_WEIGHT)
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

    fun findById(id: Int): Cocktail {
        return cocktails.find { id == it.idx }
            ?: throw java.lang.IllegalArgumentException("해당 ID정보에 대한 칵테일이 존재하지 않습니다.")
    }

    companion object {
        const val COCKTAIL_SCORE_ZERO = 0f
        const val KEYWORD_WEIGHT = 0.8f
        const val BASE_WEIGHT = 1.2f
        const val LEVEL_WEIGHT = 0.1f
    }
}
