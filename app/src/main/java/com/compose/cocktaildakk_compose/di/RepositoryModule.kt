package com.compose.cocktaildakk_compose.di

import com.compose.cocktaildakk_compose.data.repository.SearchRepositoryImpl
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

	@Provides
	@ViewModelScoped
	fun bindsSearchRepository(): SearchRepository {
		return SearchRepositoryImpl()
	}
}