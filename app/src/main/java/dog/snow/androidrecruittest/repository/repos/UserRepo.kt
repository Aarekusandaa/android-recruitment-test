package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.service.UserService

class UserRepo {

    private val userService: UserService = TODO()

    suspend fun getUsers(id: Int){
        userService.getUsers(id)
    }
}