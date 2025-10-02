package util


fun <T> readUntilConditionSatisfy(
    prompt: String = "",
    parser: (String) -> T?,
    condition: (T) -> Boolean,
    conditionString: String = "Invalid input, try again."
): T {
    while (true) {
        if (prompt.isNotEmpty()) print(prompt)

        val line = readlnOrNull() ?: continue

        val parsed: T? = try {
            parser(line)
        } catch (_: Exception) {
            null
        }

        if (parsed != null && condition(parsed)) {
            return parsed
        } else {
            println(conditionString)
        }
    }
}
