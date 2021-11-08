package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.UserDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawPhotoEntity
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.model.RawUserEntity
import dog.snow.androidrecruittest.repository.service.UserService
import retrofit2.Response
import retrofit2.Retrofit

class UserRepo(
    private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val userService: UserService,
    private val userDao: UserDao) {

    suspend fun getUsers(userService: UserService, id: Int): Response<List<RawUser>> {
        return userService.getUsers(id)
    }

    suspend fun pushUsers(userList: List<RawUser>){
        //userDao.pushUsers(userList)
    }

    suspend fun getUser(userDao: UserDao, id: Int) : RawUserEntity{
        return userDao.getUser(id)
    }

    suspend fun getUsers(userDao: UserDao) : List<RawUserEntity>{
        return userDao.getUsers()
    }

    suspend fun deleteUser(userDao: UserDao, user: RawUserEntity){
        userDao.deleteUser(user)
    }

    fun mapUsers (data: List<RawUser>) : List<RawUserEntity>{
        val users: MutableList<RawUserEntity> = mutableListOf()
        data.forEach { data->
            users.add(RawUserEntity(data.id, data.name, data.username, data.email, data.phone, data.website))
        }
        return users
    }

    suspend fun cacheUsers(userDao: UserDao, usersIds: List<Int>) : Boolean{     //
        usersIds.forEach {id ->
            val retrofitResponse = userService.getUsers(id)
            if (retrofitResponse.isSuccessful) {
                retrofitResponse.body()?.let { data ->
                    userDao.pushUsers(mapUsers(data))
                }
            }
            return true
        }
        return false
    }

}