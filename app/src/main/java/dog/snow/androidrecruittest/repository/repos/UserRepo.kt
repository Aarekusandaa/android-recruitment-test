package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.UserDao
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.service.UserService
import retrofit2.Response

class UserRepo(private val userService: UserService, private val userDao: UserDao) {

    suspend fun getUsers(id: Int): Response<List<RawUser>> {
        return userService.getUsers(id)
    }

    suspend fun pushUsers(userList: List<RawUser>){
        userDao.pushUsers(userList)
    }

    suspend fun getUser(id: Int) : RawUser{
        return userDao.getUser(id)
    }

}