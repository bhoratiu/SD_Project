package com.mtd.electrica.common.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mtd.electrica.feature.signin.data.dataSource.local.UserDAO
import com.mtd.electrica.feature.signin.data.model.User

@Database(
	entities = [
		User::class
	],
	version = 1
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun userDao(): UserDAO
}