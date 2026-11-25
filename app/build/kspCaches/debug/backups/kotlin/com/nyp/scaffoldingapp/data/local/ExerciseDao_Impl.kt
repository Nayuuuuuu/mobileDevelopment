package com.nyp.scaffoldingapp.`data`.local

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performInTransactionSuspending
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ExerciseDao_Impl(
  __db: RoomDatabase,
) : ExerciseDao() {
  private val __db: RoomDatabase

  private val __insertAdapterOfExerciseEntity: EntityInsertAdapter<ExerciseEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfExerciseEntity = object : EntityInsertAdapter<ExerciseEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `exercises` (`id`,`name`,`category`,`level`,`equipment`,`primaryMuscles`,`secondaryMuscles`,`suggested`,`steps`,`videoUrl`) VALUES (?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ExerciseEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.category)
        statement.bindText(4, entity.level)
        statement.bindText(5, entity.equipment)
        statement.bindText(6, entity.primaryMuscles)
        statement.bindText(7, entity.secondaryMuscles)
        statement.bindText(8, entity.suggested)
        statement.bindText(9, entity.steps)
        statement.bindText(10, entity.videoUrl)
      }
    }
  }

  public override suspend fun insertAll(items: List<ExerciseEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfExerciseEntity.insert(_connection, items)
  }

  public override suspend fun replaceAll(items: List<ExerciseEntity>): Unit = performInTransactionSuspending(__db) {
    super@ExerciseDao_Impl.replaceAll(items)
  }

  public override fun observeAll(): Flow<List<ExerciseEntity>> {
    val _sql: String = "SELECT * FROM exercises ORDER BY id"
    return createFlow(__db, false, arrayOf("exercises")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfLevel: Int = getColumnIndexOrThrow(_stmt, "level")
        val _columnIndexOfEquipment: Int = getColumnIndexOrThrow(_stmt, "equipment")
        val _columnIndexOfPrimaryMuscles: Int = getColumnIndexOrThrow(_stmt, "primaryMuscles")
        val _columnIndexOfSecondaryMuscles: Int = getColumnIndexOrThrow(_stmt, "secondaryMuscles")
        val _columnIndexOfSuggested: Int = getColumnIndexOrThrow(_stmt, "suggested")
        val _columnIndexOfSteps: Int = getColumnIndexOrThrow(_stmt, "steps")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _result: MutableList<ExerciseEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExerciseEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpLevel: String
          _tmpLevel = _stmt.getText(_columnIndexOfLevel)
          val _tmpEquipment: String
          _tmpEquipment = _stmt.getText(_columnIndexOfEquipment)
          val _tmpPrimaryMuscles: String
          _tmpPrimaryMuscles = _stmt.getText(_columnIndexOfPrimaryMuscles)
          val _tmpSecondaryMuscles: String
          _tmpSecondaryMuscles = _stmt.getText(_columnIndexOfSecondaryMuscles)
          val _tmpSuggested: String
          _tmpSuggested = _stmt.getText(_columnIndexOfSuggested)
          val _tmpSteps: String
          _tmpSteps = _stmt.getText(_columnIndexOfSteps)
          val _tmpVideoUrl: String
          _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          _item = ExerciseEntity(_tmpId,_tmpName,_tmpCategory,_tmpLevel,_tmpEquipment,_tmpPrimaryMuscles,_tmpSecondaryMuscles,_tmpSuggested,_tmpSteps,_tmpVideoUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clear() {
    val _sql: String = "DELETE FROM exercises"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
