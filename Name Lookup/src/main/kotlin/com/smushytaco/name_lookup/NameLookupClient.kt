package com.smushytaco.name_lookup
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.smushytaco.name_lookup.NameLookup.COMMAND
import com.smushytaco.name_lookup.NameLookup.STRING_ARGUMENT_KEY
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.command.CommandSource
@Suppress("UNUSED")
@Environment(EnvType.CLIENT)
object NameLookupClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher, _ ->
            dispatcher.register(literal(COMMAND)
                .then(ClientCommandManager.argument(STRING_ARGUMENT_KEY, StringArgumentType.word())
                    .suggests { context, builder ->
                        @Suppress("UNCHECKED_CAST")
                        NameLookupSuggestionProvider.getSuggestions(context as CommandContext<CommandSource>, builder)
                    }
                    .executes(NameLookupClientCommand)))
        })
    }
}