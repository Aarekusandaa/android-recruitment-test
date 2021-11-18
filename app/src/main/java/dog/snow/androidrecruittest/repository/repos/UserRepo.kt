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
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.Exception

class UserRepo(
    /*private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val userService: UserService,
    private val userDao: UserDao*/) {

    suspend fun getUsers(userService: UserService, id: Int): Response<RawUser> {
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
        try {
            val delete = userDao.deleteUser(user)
            if (delete != 1){
                throw Exception()
            }
        }catch (e: Exception){
            println("Error delete DAO-> user")
        }
    }

    fun mapUsers (data: List<RawUser>) : List<RawUserEntity>{
        val users: MutableList<RawUserEntity> = mutableListOf()
        data.forEach { data->
            users.add(RawUserEntity(data.id, data.name, data.username, data.email, data.phone, data.website))
        }
        return users
    }

    fun mapUsers (data: RawUser) : RawUserEntity{
        val users: RawUserEntity = RawUserEntity(data.id, data.name, data.username, data.email, data.phone, data.website)
        return users
    }

    suspend fun cacheUsers(userDao: UserDao, userService: UserService, usersIds: List<Int>) : Boolean{     //
        usersIds.forEach {id ->
            try {
                val retrofitResponse = userService.getUsers(id)
                if (retrofitResponse.isSuccessful) {
                    retrofitResponse.body()?.let { data ->
                        try {
                            val insert = userDao.pushUsers(mapUsers(data))
                            if (insert < 0L){
                                throw Exception()
                            }
                        }catch (e: Exception){
                            println("Error DAO-> user id $id")
                            return false
                        }
                    }
                }else{
                    when(retrofitResponse.code()){
                        in 400..499 -> {
                            println("Error SERVICE: Client-> user id $id")
                            return false
                        }
                        in 500..599 -> {
                            println("Error SERVICE: Server-> user id $id")
                            return false
                        }
                        else -> {
                            println("Error SERVICE-> user id $id")
                            return false
                        }
                    }
                }
            }catch (e: Exception){
                when (e){
                    is SocketTimeoutException ->{
                        println("Error SERVICE: SocketTimeoutException-> user id $id")
                        return false
                    }
                    is UnknownHostException -> {
                        println("Error SERVICE: UnknownHostException-> user id $id")
                        return false
                    }
                    else -> {
                        println("Error SERVICE: Exception-> user id $id")
                        return false
                    }
                }
            }
        }
        return true
    }

}