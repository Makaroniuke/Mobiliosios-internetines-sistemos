package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from item ORDER BY first_signal ASC")
    fun getItems(): Flow<List<Item>>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * from user")
    fun getUsers(): Flow<List<User>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(measurement: Measurement)

    @Query("SELECT * from measurement ORDER BY x, y asc")
    fun getMeasurements(): Flow<List<Measurement>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(strength: Strength)
    @Query("SELECT * from strength")
    fun getStrengths(): Flow<List<Strength>>


}