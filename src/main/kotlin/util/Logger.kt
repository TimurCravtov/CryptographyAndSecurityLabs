package util

class Logger  // Private constructor to prevent instantiation
private constructor() {

    var seen = true

    fun log(message: String?) {
        if (seen) {
            println(message)
        }
    }

    companion object {

        var instance: Logger? = null

            get() {
                if (field == null) {
                    field = Logger()
                }
                return field
            }
            private set
    }
}
