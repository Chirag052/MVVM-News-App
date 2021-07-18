package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.*
import com.androiddevs.mvvmnewsapp.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun getArticleDao(): ArticleDao

    companion object{
        //instance of databse is used to call getArticleDao which is used to call actual db functions
        //other threads can instantly see when the thread changes
        @Volatile
        private var instance: ArticleDatabase? =null
        private val LOCK = Any() //to make sure there is single instance of database

        /*if instance is null then start a synchronized block so that means
        everything happens inside this block can't be accessed by other threads at a same time
         result-> no other threat can set the instance once it is intialized */
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance =it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }


}