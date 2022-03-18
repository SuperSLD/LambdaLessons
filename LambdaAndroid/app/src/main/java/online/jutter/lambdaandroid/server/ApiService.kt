package online.jutter.lambdaandroid.server

class ApiService(private val api: Api) {

    suspend fun getList() = api.getList()
}