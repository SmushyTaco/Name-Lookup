package com.smushytaco.name_lookup
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.smushytaco.name_lookup.NameLookup.STRING_ARGUMENT_KEY
import com.smushytaco.name_lookup.NameLookup.nameLookupCommand
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.server.command.ServerCommandSource
@Environment(EnvType.SERVER)
object NameLookupServerCommand: Command<ServerCommandSource> {
    override fun run(context: CommandContext<ServerCommandSource>): Int {
        nameLookupCommand(context, StringArgumentType.getString(context, STRING_ARGUMENT_KEY))
        return 0
    }
}