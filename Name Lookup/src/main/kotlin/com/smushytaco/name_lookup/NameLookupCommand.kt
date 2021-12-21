package com.smushytaco.name_lookup
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.smushytaco.name_lookup.mojang_api_parser.UsernameInformation.currentUsernameInformation
import com.smushytaco.name_lookup.mojang_api_parser.UsernameInformation.usernameHistory
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource
import net.minecraft.text.Text
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.thread
object NameLookupCommand: Command<FabricClientCommandSource> {
    const val COMMAND = "namelookup"
    const val ARGUMENT_KEY = "name"
    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        thread {
            val player = context.source.player
            val name = StringArgumentType.getString(context, ARGUMENT_KEY)
            val currentUsernameInformation = name.currentUsernameInformation ?: kotlin.run {
                player.sendMessage(Text.of("§4Could not find anybody with the name §c$name§4."), true)
                return@thread
            }
            val nameHistory = currentUsernameInformation.id.usernameHistory ?: kotlin.run {
                player.sendMessage(Text.of("§4Could not find anybody with the name §c$name§4."), true)
                return@thread
            }
            player.sendMessage(Text.of("§3Showing name information for §b${currentUsernameInformation.name}§3:"), false)
            player.sendMessage(Text.of("§3UUID: §b${currentUsernameInformation.id}"), false)
            for (i in nameHistory.indices) {
                val stringBuilder = StringBuilder("§3${i + 1}. §b${nameHistory[i].name}")
                nameHistory[i].changedToAt?.let {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it
                    val date = LocalDateTime.ofInstant(calendar.toInstant(), calendar.timeZone.toZoneId())
                    val finalDate = "${date.monthValue}/${date.dayOfMonth}/${date.year}§3, §b${if (date.hour > 12) date.hour - 12 else date.hour}:${if (date.minute < 10) "0" else ""}${date.minute}:${if (date.second < 10) "0" else ""}${date.second} ${if (date.hour >= 12) "PM" else "AM"}"
                    stringBuilder.append("§3, §b$finalDate")
                }
                player.sendMessage(Text.of(stringBuilder.toString()), false)
            }
        }
        return 0
    }
}