package mega.triple.aaa.presentation.feature.settings.ext

import mega.triple.aaa.preference.model.ThemeTypePrefModel

enum class ThemeType(val type: Int) {
    AUTO(0),
    DYNAMIC(1),
    DARK(2),
    LIGHT(3);

    companion object {
        fun ThemeTypePrefModel.toThemeType(): ThemeType = ThemeType.entries[type]

        fun ThemeType.toThemeTypePref(): ThemeTypePrefModel = ThemeTypePrefModel.entries[type]
    }
}
