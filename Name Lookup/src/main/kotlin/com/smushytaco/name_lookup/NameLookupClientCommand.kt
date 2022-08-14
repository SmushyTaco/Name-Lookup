package com.smushytaco.name_lookup
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.smushytaco.name_lookup.NameLookup.STRING_ARGUMENT_KEY
import com.smushytaco.name_lookup.NameLookup.nameLookupCommand
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
@Environment(EnvType.CLIENT)
object NameLookupClientCommand: Command<FabricClientCommandSource> {
    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        nameLookupCommand(context, StringArgumentType.getString(context, STRING_ARGUMENT_KEY))
        return 0
    }
}