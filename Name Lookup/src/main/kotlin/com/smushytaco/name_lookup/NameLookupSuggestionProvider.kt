package com.smushytaco.name_lookup
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandSource
import java.util.*
import java.util.concurrent.CompletableFuture
object NameLookupSuggestionProvider: SuggestionProvider<CommandSource> {
    override fun getSuggestions(context: CommandContext<CommandSource>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        for (playerName in context.source.playerNames) {
            if (playerName.lowercase(Locale.getDefault()).contains(context.input.removeRange(0, 12).lowercase(Locale.getDefault()))) builder.suggest(playerName)
        }
        return builder.buildFuture()
    }
}