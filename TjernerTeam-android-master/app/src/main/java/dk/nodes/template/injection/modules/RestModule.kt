package dk.nodes.template.injection.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dk.nodes.nstack.kotlin.providers.NMetaInterceptor
import dk.nodes.template.BuildConfig
import dk.nodes.template.network.Api
import dk.nodes.template.network.FaceBookService
import dk.nodes.template.network.ShiftService
import dk.nodes.template.network.util.BufferedSourceConverterFactory
import dk.nodes.template.network.util.DateDeserializer
import dk.nodes.template.network.util.ItemTypeAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RestModule {
    @Provides
    fun provideTypeFactory(): ItemTypeAdapterFactory {
        return ItemTypeAdapterFactory()
    }

    @Provides
    fun provideDateDeserializer(): DateDeserializer {
        return DateDeserializer()
    }

    @Provides
    @Singleton
    fun provideGson(typeFactory: ItemTypeAdapterFactory, dateDeserializer: DateDeserializer): Gson {
        return GsonBuilder()
            .registerTypeAdapterFactory(typeFactory)
            .registerTypeAdapter(Date::class.java, dateDeserializer)
            .setDateFormat(DateDeserializer.DATE_FORMATS[0])
            .create()
    }

    @Provides
    @Named("NAME_BASE_URL")
    fun provideBaseUrlString(): String {
        return "http://ec2-3-208-9-18.compute-1.amazonaws.com:5000"
    }
    @Provides
    @Named("NAME_Weather_BASE_URL")
    fun provideWeatherBaseUrlString(): String {
        return "https://api.openweathermap.org"
    }



    @Provides
    @Singleton
    fun provideGsonConverter(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(NMetaInterceptor(BuildConfig.BUILD_TYPE))

        if (BuildConfig.DEBUG) {
            val logging = okhttp3.logging.HttpLoggingInterceptor()
            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        return clientBuilder.build()
    }

    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        converter: Converter.Factory,
        @Named("NAME_BASE_URL") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(BufferedSourceConverterFactory())
            .addConverterFactory(converter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @RetrofitApi
    @Singleton
    fun provideRetrofit2(
            client: OkHttpClient,
            converter: Converter.Factory,
            @Named("NAME_Weather_BASE_URL") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(BufferedSourceConverterFactory())
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    @Provides
    fun provideShiftService(retrofit: Retrofit): ShiftService {
        return retrofit.create<ShiftService>(ShiftService::class.java)
    }

    @Provides
    fun provideFacebookService(retrofit: Retrofit): FaceBookService {
        return retrofit.create<FaceBookService>(FaceBookService::class.java)
    }
//
//    @Provides
//    @Singleton
//    fun provideMovieService(retrofit: Retrofit): MovieService {
//        return retrofit.create<MovieService>(MovieService::class.java)
//    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create<Api>(Api::class.java)
    }


}
