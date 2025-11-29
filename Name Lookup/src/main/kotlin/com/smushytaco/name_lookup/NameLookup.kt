package com.smushytaco.name_lookup
import com.mojang.brigadier.context.CommandContext
import com.smushytaco.name_lookup.mojang_api_parser.UsernameInformation.currentUsernameInformation
import com.smushytaco.name_lookup.mojang_api_parser.UsernameInformation.usernameHistory
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.thread
import kotlin.reflect.KClass
object NameLookup {
    const val COMMAND = "namelookup"
    const val STRING_ARGUMENT_KEY = "name"
    infix fun <T : Any, E : Any> KClass<T>.can(compare: KClass<E>) = compare.java.isAssignableFrom(this.java)
    inline fun <reified T> CommandContext<T>.sendMessage(message: Component, overlay: Boolean) where T: SharedSuggestionProvider {
        if (T::class can CommandSourceStack::class) {
            @Suppress("UNCHECKED_CAST")
            this as CommandContext<CommandSourceStack>
            if (source.player != null) {
                source.player?.displayClientMessage(message, overlay)
            } else {
                source.sendSystemMessage(message)
            }
        } else if (T::class can FabricClientCommandSource::class) {
            @Suppress("UNCHECKED_CAST")
            this as CommandContext<FabricClientCommandSource>
            source.player.displayClientMessage(message, overlay)
        }
    }
    fun MutableComponent.copySupport(copyString: String, hoverText: Component): MutableComponent {
        style = style.withClickEvent(ClickEvent.CopyToClipboard(copyString)).withHoverEvent(HoverEvent.ShowText(hoverText))
        return this
    }
    inline fun <reified T> nameLookupCommand(context: CommandContext<T>, name: String) where T: SharedSuggestionProvider {
        thread {
            val currentUsernameInformation = name.currentUsernameInformation ?: kotlin.run {
                context.sendMessage(Component.nullToEmpty("§4Could not find anybody with the name §c$name§4."), true)
                return@thread
            }
            val nameHistory = currentUsernameInformation.id.usernameHistory ?: kotlin.run {
                context.sendMessage(Component.nullToEmpty("§4Could not find anybody with the name §c$name§4."), true)
                return@thread
            }
            context.sendMessage(Component.literal("§3Showing name information for ").append(Component.literal("§b${currentUsernameInformation.name}").copySupport(currentUsernameInformation.name, Component.nullToEmpty("Click to copy the username!"))).append("§3:"), false)
            context.sendMessage(Component.literal("§3UUID: ").append(Component.literal("§b${currentUsernameInformation.id}").copySupport(currentUsernameInformation.id.toString(), Component.nullToEmpty("Click to copy the UUID!"))), false)
            for (i in nameHistory.indices) {
                val nameOnList = Component.literal("§3${i + 1}. ").append(Component.literal("§b${nameHistory[i].name}").copySupport(nameHistory[i].name, Component.nullToEmpty("Click to copy the username!")))
                nameHistory[i].changedToAt?.let {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it
                    val date = LocalDateTime.ofInstant(calendar.toInstant(), calendar.timeZone.toZoneId())
                    nameOnList.append("§3, ").append(Component.literal("§b${date.monthValue}/${date.dayOfMonth}/${date.year}").copySupport("${date.monthValue}/${date.dayOfMonth}/${date.year}", Component.nullToEmpty("Click here to copy the date!"))).append("§3, ")
                    nameOnList.append(Component.literal("§b${if (date.hour > 12) date.hour - 12 else date.hour}:${if (date.minute < 10) "0" else ""}${date.minute}:${if (date.second < 10) "0" else ""}${date.second} ${if (date.hour >= 12) "PM" else "AM"}").copySupport("${if (date.hour > 12) date.hour - 12 else date.hour}:${if (date.minute < 10) "0" else ""}${date.minute}:${if (date.second < 10) "0" else ""}${date.second} ${if (date.hour >= 12) "PM" else "AM"}", Component.nullToEmpty("Click here to copy the time!")))
                }
                context.sendMessage(nameOnList, false)
            }
        }
    }
}