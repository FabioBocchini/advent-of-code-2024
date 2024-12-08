package utils

typealias Condition = (Char) -> Boolean
typealias OutputGenerator = (Char) -> String

data class Rule<S>(
    val stateFrom: S, val stateTo: S, val condition: Condition, val output: OutputGenerator
)

class FiniteStateMachine<S>(private val behavior: List<Rule<S>>, private val initialState: S) {
    fun execute(input: String): String? {
        var state = initialState
        var output = ""

        for (char in input.toCharArray()) {
            val rule = behavior.find { it.stateFrom == state && it.condition(char) }
            if (rule == null) {
                return null
            }
            val ruleOutput = rule.output(char)
            when (ruleOutput) {
                "ACCEPT" -> return output
                "REFUSED" -> return null
            }
            output += ruleOutput
            state = rule.stateTo
        }

        return output
    }
}