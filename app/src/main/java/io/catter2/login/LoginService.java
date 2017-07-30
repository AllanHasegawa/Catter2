package io.catter2.login;

public class LoginService {
    public class UsernamePasswordDoesNotMatchException extends RuntimeException {
        public UsernamePasswordDoesNotMatchException() {
            super("Username and password doesn't match.");
        }
    }

    /**
     * A dummy authentication store containing known user names and passwords.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "me:123:token9", "yo:hunter2:token2"
    };

    /**
     * @param username
     * @param password
     * @return A unique identifiable token representing the user associated to the given username.
     * @throws UsernamePasswordDoesNotMatchException
     * @throws NullPointerException                  If either username or password equals to null.
     */
    public String login(String username, String password) {
        for (String credential : DUMMY_CREDENTIALS) {
            String[] split = credential.split(":");
            if (username.equals(split[0]) && password.equals(split[1])) {
                return split[2];
            }
        }

        throw new UsernamePasswordDoesNotMatchException();
    }
}
