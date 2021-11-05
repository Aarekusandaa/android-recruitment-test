package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.UserDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.service.UserService
import retrofit2.Response
import retrofit2.Retrofit

class UserRepo(
    private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val userService: UserService,
    private val userDao: UserDao) {

    suspend fun getUsers(id: Int): Response<List<RawUser>> {
        return userService.getUsers(id)
    }

    suspend fun pushUsers(userList: List<RawUser>){
        userDao.pushUsers(userList)
    }

    suspend fun getUser(id: Int) : RawUser{
        return userDao.getUser(id)
    }

    suspend fun getUsers() : List<RawUser>{
        return userDao.getUsers()
    }

    suspend fun deleteUser(user: RawUser){
        userDao.deleteUser(user)
    }

    suspend fun cacheAlbums(usersIds: List<Int>) {     //: Boolean
        usersIds.forEach {id ->
            val retrofitResponse = userService.getUsers(id)
            if (retrofitResponse.isSuccessful) {
                retrofitResponse.body()?.let { data ->
                    userDao.pushUsers(data)
                }
                //return true
            }
            //return false
        }
    }

}