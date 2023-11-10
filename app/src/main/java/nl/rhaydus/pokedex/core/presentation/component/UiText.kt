package nl.rhaydus.pokedex.core.presentation.component

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    class StringResource(@StringRes val stringRes: Int) : UiText()
    class RawString(val string: String) : UiText()

    @Composable
    fun getString(): String = when (this) {
        is StringResource -> stringResource(id = this.stringRes)
        is RawString -> this.string
    }

    fun getString(context: Context): String = when (this) {
        is StringResource -> context.getString(this.stringRes)
        is RawString -> this.string
    }
}