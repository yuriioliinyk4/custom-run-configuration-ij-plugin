package yurii.oliinyk.customrunconfiguration

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.project.Project

class CustomRunConfigurationFactory(
    configurationType: CustomRunConfigurationType
) : ConfigurationFactory(configurationType) {

    override fun getId() = CustomRunConfigurationType.ID

    override fun createTemplateConfiguration(project: Project): RunConfiguration =
        CustomRunConfiguration(project, this, CustomRunConfigurationType.DISPLAY_NAME)

    override fun getOptionsClass(): Class<out BaseState?> = CustomRunConfigurationOptions::class.java
}
