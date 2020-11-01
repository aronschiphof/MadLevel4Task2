package com.example.madlevel4task2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertProduct(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

}