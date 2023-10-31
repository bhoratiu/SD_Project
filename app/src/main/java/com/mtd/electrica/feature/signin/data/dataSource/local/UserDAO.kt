package com.mtd.electrica.feature.signin.data.dataSource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtd.electrica.feature.signin.data.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(userList: List<User>): Completable

    @Query("SELECT * FROM User LIMIT 1")
    fun getUser(): Single<User>

    @Query("SELECT * FROM User")
    fun checkForEmail(): Single<User>
}