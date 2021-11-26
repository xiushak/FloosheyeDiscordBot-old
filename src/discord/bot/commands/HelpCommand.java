package discord.bot.commands;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class HelpCommand implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (message.equalsIgnoreCase("fish help")) {
            new MessageBuilder()
                    .append("Chance of applying the fisheye effect on an image sent while the bot is online")
                    .send(event.getChannel());
        }

    }
}
