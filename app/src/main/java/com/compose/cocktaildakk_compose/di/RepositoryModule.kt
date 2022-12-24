package com.compose.cocktaildakk_compose.di

import com.compose.cocktaildakk_compose.data.repository.SearchRepositoryImpl
import com.compose.cocktaildakk_compose.data.repository.CocktailRepositoryImpl
import com.compose.cocktaildakk_compose.data.repository.ImageRepositoryImpl
import com.compose.cocktaildakk_compose.data.repository.UserInfoRepositoryImpl
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.ImageRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
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

}
//	@Provides
//	@ViewModelScoped
//	fun bindsSearchRepository(): SearchRepository {
//		return SearchRepositoryImpl()
//	}
