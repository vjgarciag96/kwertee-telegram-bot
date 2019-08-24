package bot.commands

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ivmg.telegram.Bot
import me.ivmg.telegram.entities.Update
import tees.domain.FetchGoneForeverTees
import tees.domain.toTextMessage

class GoneForeverTeesCommand(private val fetchGoneForeverTees: FetchGoneForeverTees) : BotCommand {

    override val name: String
        get() = "goneforever"

    override fun action(bot: Bot, update: Update, args: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val tShirts = fetchGoneForeverTees()
            tShirts.forEach { tShirt ->
                update.message?.let { message ->
                    bot.sendPhoto(message.chat.id, tShirt.imageUrl, tShirt.toTextMessage())
                }
            }
        }
    }
}