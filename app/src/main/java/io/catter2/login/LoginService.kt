package io.catter2.login

class LoginService {
    inner class UsernamePasswordDoesNotMatchException : RuntimeException("Username and password doesn't match.")

    companion object {

        /**
         * A dummy authentication store containing known user names and passwords.
         */
        private val DUMMY_CREDENTIALS = arrayOf("me:123:token9", "yo:hunter2:token2")
    }

    /**
     * @param username
     * @param password
     * @return A unique identifiable token representing the user associated to the given username.
     * @throws UsernamePasswordDoesNotMatchException
     * @throws NullPointerException                  If either username or password equals to null.
     */
    fun login(username: String, password: String): String {
        data class Entry(val username: String, val password: String, val token: String)
        return DUMMY_CREDENTIALS
                .asSequence()
                .map { it.split(":") }
                .filter { it.size == 3 }
                .map { Entry(it[0], it[1], it[2]) }
                .filter { it.username == username && it.password == password }
                .map { it.token }
                .firstOrNull()
                ?: throw UsernamePasswordDoesNotMatchException()
    }
}
