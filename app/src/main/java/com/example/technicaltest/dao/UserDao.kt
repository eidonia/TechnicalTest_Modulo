package com.example.technicaltest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.technicaltest.models.response.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User): Completable

    @Query("SELECT * FROM user")
    fun getUser(): Flowable<User>

    @Query("UPDATE user SET firstName = :firstname ,lastname = :lastname, birthDate = :birthdate, addressString = :address, email = :email")
    fun updateNameuser(firstname: String, lastname: String, birthdate: Long, address: String, email: String): Completable
}