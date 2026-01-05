package yurii.oliinyk.customrunconfiguration

import com.intellij.execution.configurations.RunConfigurationOptions

class CustomRunConfigurationOptions : RunConfigurationOptions() {
    var executablePath by string()

    var arguments by string()

    var executableType by enum<ExecutableType>(ExecutableType.CUSTOM)
}

enum class ExecutableType {
    CUSTOM,
    RUST_COMPILER,
    CARGO
}
