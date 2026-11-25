package com.nyp.scaffoldingapp.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _exerciseDao: Lazy<ExerciseDao> = lazy {
    ExerciseDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "345ca9b5fc54573d65fe481357d90ba8", "3007f9286ee6e864155bac748ce99aed") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `exercises` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `level` TEXT NOT NULL, `equipment` TEXT NOT NULL, `primaryMuscles` TEXT NOT NULL, `secondaryMuscles` TEXT NOT NULL, `suggested` TEXT NOT NULL, `steps` TEXT NOT NULL, `videoUrl` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '345ca9b5fc54573d65fe481357d90ba8')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `exercises`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsExercises: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsExercises.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("category", TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("level", TableInfo.Column("level", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("equipment", TableInfo.Column("equipment", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("primaryMuscles", TableInfo.Column("primaryMuscles", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("secondaryMuscles", TableInfo.Column("secondaryMuscles", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("suggested", TableInfo.Column("suggested", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("steps", TableInfo.Column("steps", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("videoUrl", TableInfo.Column("videoUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysExercises: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesExercises: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoExercises: TableInfo = TableInfo("exercises", _columnsExercises, _foreignKeysExercises, _indicesExercises)
        val _existingExercises: TableInfo = read(connection, "exercises")
        if (!_infoExercises.equals(_existingExercises)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |exercises(com.nyp.scaffoldingapp.data.local.ExerciseEntity).
              | Expected:
              |""".trimMargin() + _infoExercises + """
              |
              | Found:
              |""".trimMargin() + _existingExercises)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "exercises")
  }

  public override fun clearAllTables() {
    super.performClear(false, "exercises")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ExerciseDao::class, ExerciseDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun exerciseDao(): ExerciseDao = _exerciseDao.value
}
