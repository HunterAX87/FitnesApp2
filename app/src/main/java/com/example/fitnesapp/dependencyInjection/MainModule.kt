package com.example.fitnesapp.dependencyInjection

import android.app.Application
import android.media.SoundPool
import android.speech.tts.TextToSpeech
import androidx.room.Room
import com.example.fitnesapp.db.MainDb
import com.example.fitnesapp.utils.MySoundPool
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "fitness.db",
        ).createFromAsset("db/fitness.db").build()
    }

    @Provides
    @Singleton
    fun provideTTS(app: Application): TextToSpeech {
        var tts: TextToSpeech? = null
        tts = TextToSpeech(app) {
            if (TextToSpeech.SUCCESS == it) {
                tts?.language = Locale.ENGLISH
            }
        }
        return tts
    }

    @Provides
    @Singleton
    fun provideSoundPool(app: Application): MySoundPool {
        return MySoundPool(app)
    }
}