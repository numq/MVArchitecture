package com.numq.mvarchitecture.platform.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.numq.mvarchitecture.data.Database
import com.numq.mvarchitecture.data.image.ImageApi
import com.numq.mvarchitecture.data.image.ImageData
import com.numq.mvarchitecture.data.image.ImageService
import com.numq.mvarchitecture.domain.repository.ImageRepository
import com.numq.mvarchitecture.platform.constant.AppConstants
import com.numq.mvarchitecture.presentation.feature.mvc.base.Controller
import com.numq.mvarchitecture.presentation.feature.mvc.base.View
import com.numq.mvarchitecture.presentation.feature.mvi.favorites.FavoritesFeature
import com.numq.mvarchitecture.presentation.feature.mvi.image.RandomImageFeature
import com.numq.mvarchitecture.presentation.feature.mvp.favorites.FavoritesContract
import com.numq.mvarchitecture.presentation.feature.mvp.favorites.FavoritesPresenter
import com.numq.mvarchitecture.presentation.feature.mvp.random.RandomImageContract
import com.numq.mvarchitecture.presentation.feature.mvp.random.RandomImagePresenter
import com.numq.mvarchitecture.presentation.feature.mvvm.FavoritesViewModel
import com.numq.mvarchitecture.presentation.feature.mvvm.RandomImageViewModel
import com.numq.mvarchitecture.usecase.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.numq.mvarchitecture.presentation.feature.mvc.favorites.FavoritesView as MvcFavoritesView
import com.numq.mvarchitecture.presentation.feature.mvc.random.RandomImageView as MvcRandomImageView
import com.numq.mvarchitecture.presentation.feature.mvp.favorites.FavoritesView as MvpFavoritesView
import com.numq.mvarchitecture.presentation.feature.mvp.random.RandomImageView as MvpRandomImageView

val application = module {
    single {
        Retrofit.Builder()
            .baseUrl(AppConstants.Api.Image.URL)
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor())
                    .callTimeout(5, TimeUnit.SECONDS).build()
            )
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .build()
    }
    single {
        Room.databaseBuilder(
            get(),
            Database::class.java, AppConstants.Database.NAME
        ).build()
    }
}

val data = module {
    single { get<Database>().imageDao() }
    single { ImageService(get()) } bind ImageApi::class
    single { ImageData(get(), get()) } bind ImageRepository::class
}

val interactor = module {
    factory { CheckFavorite(get()) }
    factory { GetRandomImage(get()) }
    factory { GetFavorites(get()) }
    factory { AddFavorite(get()) }
    factory { RemoveFavorite(get()) }
}

val mvc = module {
    single { MvcRandomImageView() } bind View.RandomImage::class
    single { MvcFavoritesView() } bind View.Favorites::class
    single { Controller(get(), get(), get(), get(), get(), get(), get()) }
}

val mvp = module {
    single { MvpRandomImageView() } bind RandomImageContract.View::class
    single { MvpFavoritesView() } bind FavoritesContract.View::class
    single {
        RandomImagePresenter(
            get(),
            get(),
            get(),
            get()
        )
    } bind RandomImageContract.Presenter::class
    single { FavoritesPresenter(get(), get(), get()) } bind FavoritesContract.Presenter::class
}

val mvvm = module {
    single { RandomImageViewModel(get(), get(), get(), get()) }
    single { FavoritesViewModel(get(), get(), get()) }
}

val mvi = module {
    single { RandomImageFeature(get(), get(), get(), get()) }
    single { FavoritesFeature(get(), get(), get()) }
}

val features = mvc + mvp + mvvm + mvi
val appModule = application + data + interactor + features