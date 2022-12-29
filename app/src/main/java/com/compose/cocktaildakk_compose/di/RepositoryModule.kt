package com.compose.cocktaildakk_compose.di

import com.compose.cocktaildakk_compose.data.repository.*
import com.compose.cocktaildakk_compose.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsSplashRepository(
        splashRepositoryImpl: CocktailRepositoryImpl
    ): CocktailRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsUserInfoRepository(
        userInfoRepositoryImpl: UserInfoRepositoryImpl
    ): UserInfoRepository

    @Binds
    @ViewModelScoped
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    @ViewModelScoped
    abstract fun bindReviewRepository(
        reviewRepositoryImpl: ReviewRepositoryImpl
    ): ReviewRepository

    @Binds
    @ViewModelScoped
    abstract fun bindgMapRepository(
        mapRepositoryImpl: MapRepositoryImpl
    ): MapRepository

}
//	@Provides
//	@ViewModelScoped
//	fun bindsSearchRepository(): SearchRepository {
//		return SearchRepositoryImpl()
//	}
