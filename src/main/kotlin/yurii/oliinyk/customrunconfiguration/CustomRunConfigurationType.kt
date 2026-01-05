package yurii.oliinyk.customrunconfiguration

import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.NotNullLazyValue

class CustomRunConfigurationType : ConfigurationTypeBase(
    ID,
    DISPLAY_NAME,
    DESCRIPTION,
    NotNullLazyValue.createValue { AllIcons.Nodes.Console }
) {
    init {
        addFactory(CustomRunConfigurationFactory(this))
    }

    companion object {
        const val ID = "CustomRunConfiguration"
        const val DISPLAY_NAME = "Custom"
        const val DESCRIPTION = "Custom run configuration type"
    }
}
