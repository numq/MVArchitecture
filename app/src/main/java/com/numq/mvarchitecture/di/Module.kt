package com.numq.mvarchitecture.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.numq.mvarchitecture.database.Database
import com.numq.mvarchitecture.image.*
import com.numq.mvarchitecture.image.mvc.base.Controller
import com.numq.mvarchitecture.image.mvc.base.View
import com.numq.mvarchitecture.image.mvi.favorites.FavoritesFeature
import com.numq.mvarchitecture.image.mvi.image.RandomImageFeature
import com.numq.mvarchitecture.image.mvp.favorites.FavoritesContract
import com.numq.mvarchitecture.image.mvp.favorites.FavoritesPresenter
import com.numq.mvarchitecture.image.mvp.random.RandomImageContract
import com.numq.mvarchitecture.image.mvp.random.RandomImagePresenter
import com.numq.mvarchitecture.image.mvvm.FavoritesViewModel
import com.numq.mvarchitecture.image.mvvm.RandomImageViewModel
import com.numq.mvarchitecture.network.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.numq.mvarchitecture.image.mvc.favorites.FavoritesView as MvcFavoritesView
import com.numq.mvarchitecture.image.mvc.random.RandomImageView as MvcRandomImageView
import com.numq.mvarchitecture.image.mvp.favorites.FavoritesView as MvpFavoritesView
import com.numq.mvarchitecture.image.mvp.random.RandomImageView as MvpRandomImageView

val application = module {
    single {
        Retrofit.Builder()
            .baseUrl(ImageApi.BASE_URL)
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
            Database::class.java, Database.NAME
        ).build()
    }
}

val data = module {
    single { get<Database>().imageDao() }
    single { ImageService(get()) } bind ImageApi::class
    single { ImageData(get(), get(), get()) } bind ImageRepository::class
}

val interactor = module {
    factory { CheckFavorite(get()) }
    factory { GetRandomImage(get()) }
    factory { GetFavorites(get()) }
    factory { AddFavorite(get()) }
    factory { RemoveFavorite(get()) }
}

val network = module {
    single { NetworkService(androidApplication()) } bind NetworkApi::class
    single { NetworkData(get()) } bind NetworkRepository::class
    factory { GetNetworkStatus(get()) }
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
val appModule = application + network + data + interactor + features