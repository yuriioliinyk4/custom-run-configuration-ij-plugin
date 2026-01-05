package yurii.oliinyk.customrunconfiguration

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.layout.ComponentPredicate
import javax.swing.JComponent

class CustomRunSettingsEditor : SettingsEditor<CustomRunConfiguration>() {
    private val executableTypeComboBox = ComboBox(ExecutableType.entries.toTypedArray())
    private val executablePathField = TextFieldWithBrowseButton()
    private val argumentsField = JBTextField()

    init {
        val fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor()
        executablePathField.addBrowseFolderListener(null, fileChooserDescriptor)
    }

    private val customExecutablePredicate = object : ComponentPredicate() {
        override fun invoke(): Boolean = executableTypeComboBox.selectedItem == ExecutableType.CUSTOM

        override fun addListener(listener: (Boolean) -> Unit) {
            executableTypeComboBox.addActionListener {
                listener(invoke())
            }
        }
    }

    private val panel = panel {
        row("Executable:") {
            cell(executableTypeComboBox)
        }

        row("Executable path:") {
            cell(executablePathField)
                .align(AlignX.FILL)
        }.visibleIf(customExecutablePredicate)

        row("Arguments:") {
            cell(argumentsField)
                .align(AlignX.FILL)
        }
    }

    override fun createEditor(): JComponent = panel

    override fun resetEditorFrom(configuration: CustomRunConfiguration) {
        executableTypeComboBox.selectedItem = configuration.executableType
        executablePathField.text = configuration.executablePath ?: ""
        argumentsField.text = configuration.arguments ?: ""
    }

    override fun applyEditorTo(configuration: CustomRunConfiguration) {
        configuration.executableType = executableTypeComboBox.selectedItem as ExecutableType
        configuration.executablePath = executablePathField.text
        configuration.arguments = argumentsField.text
    }
}
