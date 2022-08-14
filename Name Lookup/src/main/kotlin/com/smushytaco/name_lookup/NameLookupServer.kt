package com.smushytaco.name_lookup
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.smushytaco.name_lookup.NameLookup.COMMAND
import com.smushytaco.name_lookup.NameLookup.STRING_ARGUMENT_KEY
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.literal
@Suppress("UNUSED")
@Environment(EnvType.SERVER)
object NameLookupServer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            dispatcher.register(literal(COMMAND)
                .then(CommandManager.argument(STRING_ARGUMENT_KEY, StringArgumentType.word())
                    .suggests { context, builder ->
                        @Suppress("UNCHECKED_CAST")
                        NameLookupSuggestionProvider.getSuggestions(context as CommandContext<CommandSource>, builder)
                    }
                    .executes(NameLookupServerCommand)))
        })
    }
}