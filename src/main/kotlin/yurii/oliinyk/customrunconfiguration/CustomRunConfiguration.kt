package yurii.oliinyk.customrunconfiguration

import com.intellij.execution.Executor
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.PathEnvironmentVariableUtil
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RuntimeConfigurationError
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class CustomRunConfiguration(project: Project, factory: ConfigurationFactory?, name: String?) :
    RunConfigurationBase<CustomRunConfigurationOptions>(
        project, factory, name
    ) {

    override fun getOptions(): CustomRunConfigurationOptions = super.getOptions() as CustomRunConfigurationOptions

    var executableType: ExecutableType
        get() = getOptions().executableType
        set(value) {
            getOptions().executableType = value
        }

    var executablePath: String?
        get() = getOptions().executablePath
        set(value) {
            getOptions().executablePath = value
        }

    var arguments: String?
        get() = getOptions().arguments
        set(value) {
            getOptions().arguments = value
        }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration?> = CustomRunSettingsEditor()

    override fun getState(
        executor: Executor,
        environment: ExecutionEnvironment
    ): RunProfileState = object : CommandLineState(environment) {
        override fun startProcess(): ProcessHandler {
            val executable = executableType.toExecutablePath()

            val commandLine = GeneralCommandLine(executable)
            commandLine.addParameters(arguments)

            val processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine)
            ProcessTerminatedListener.attach(processHandler)

            return processHandler
        }
    }

    override fun checkConfiguration() {
        super.checkConfiguration()

        val executable = executableType.toExecutablePath()

        if (executable.isNullOrBlank()) {
            throw RuntimeConfigurationError("Executable not found")
        }
    }

    private fun ExecutableType.toExecutablePath() = when (this) {
        ExecutableType.RUST_COMPILER -> findInPath("rustc")
        ExecutableType.CARGO -> findInPath("cargo")
        ExecutableType.CUSTOM -> executablePath
    }

    private fun findInPath(name: String) = PathEnvironmentVariableUtil.findInPath(name)?.absolutePath
}
