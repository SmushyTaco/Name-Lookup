package com.smushytaco.name_lookup
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.SharedSuggestionProvider
import java.util.*
import java.util.concurrent.CompletableFuture
object NameLookupSuggestionProvider: SuggestionProvider<SharedSuggestionProvider> {
    override fun getSuggestions(context: CommandContext<SharedSuggestionProvider>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        for (playerName in context.source.onlinePlayerNames) {
            if (playerName.lowercase(Locale.getDefault()).contains(context.input.removeRange(0, 12).lowercase(Locale.getDefault()))) builder.suggest(playerName)
        }
        return builder.buildFuture()
    }
}