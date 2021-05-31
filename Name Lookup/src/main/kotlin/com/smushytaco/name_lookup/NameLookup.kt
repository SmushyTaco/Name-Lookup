package com.smushytaco.name_lookup
import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager
@Suppress("UNUSED")
object NameLookup : ClientModInitializer {
    override fun onInitializeClient() {
        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal(NameLookupCommand.COMMAND)
            .then(ClientCommandManager.argument(NameLookupCommand.ARGUMENT_KEY, StringArgumentType.word())
                .executes(NameLookupCommand)))
    }
}