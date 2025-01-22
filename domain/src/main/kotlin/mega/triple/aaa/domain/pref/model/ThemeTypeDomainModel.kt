package mega.triple.aaa.domain.pref.model

import mega.triple.aaa.preference.model.ThemeTypePrefModel
import mega.triple.aaa.ui.model.ThemeTypeUiModel

enum class ThemeTypeDomainModel(val type: Int) {
    AUTO(0),
    DYNAMIC(1),
    DARK(2),
    LIGHT(3);

    companion object {
        fun ThemeTypeDomainModel.toPrefModel(): ThemeTypePrefModel =
            ThemeTypePrefModel.entries.find { it.type == type } ?: ThemeTypePrefModel.LIGHT

        fun ThemeTypePrefModel.toDomainModel(): ThemeTypeDomainModel =
            ThemeTypeDomainModel.entries.find { it.type == type } ?: LIGHT

        fun ThemeTypeDomainModel.toUiModel(): ThemeTypeUiModel =
            ThemeTypeUiModel.entries.find { it.type == type } ?: ThemeTypeUiModel.LIGHT

        fun ThemeTypeUiModel.toDomainModel(): ThemeTypeDomainModel =
            ThemeTypeDomainModel.entries.find { it.type == type } ?: LIGHT

        // TODO remove
        fun ThemeTypePrefModel.toUiModel(): ThemeTypeUiModel =
            ThemeTypeUiModel.entries.find { it.type == type } ?: ThemeTypeUiModel.LIGHT

        fun ThemeTypeUiModel.toPrefModel(): ThemeTypePrefModel =
            ThemeTypePrefModel.entries.find { it.type == type } ?: ThemeTypePrefModel.LIGHT
        // TODO end
    }
}
