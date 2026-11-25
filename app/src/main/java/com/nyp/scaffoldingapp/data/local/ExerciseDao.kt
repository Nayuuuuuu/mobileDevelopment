package com.nyp.scaffoldingapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExerciseDao {

    @Query("SELECT * FROM exercises ORDER BY id")
    abstract fun observeAll(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<ExerciseEntity>)

    @Query("DELETE FROM exercises")
    abstract suspend fun clear()

    // One transaction, so a crash halfway can't leave an empty cache
    @Transaction
    open suspend fun replaceAll(items: List<ExerciseEntity>) {
        clear()
        insertAll(items)
    }
}