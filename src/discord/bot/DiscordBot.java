package discord.bot;

import discord.bot.commands.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DiscordBot {
    public static void main(String[] args) throws IOException {
        // Insert your bot's token here
        Scanner scan = new Scanner(new FileReader("DiscordBotToken.txt"));
        String token = scan.next();
        scan.close();

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // check if bot is online
        api.addMessageCreateListener(new HelpCommand());

        // check if bot is online
        api.addMessageCreateListener(new CheckOnlineCommand());

        // fisheyes an image with face recognition when image and fish reaction
        // TODO: add reaction listeners on messages that have images that can then delete itself after a miunute or somthing
//        api.addMessageCreateListener(new FisheyeAttachedImageReactionCommand());

        // fisheyes an image with face recognition when image and fish
        api.addMessageCreateListener(new FisheyeAttachedImageCommand());

        // randomly fisheyes images sent regardless of text
        api.addMessageCreateListener(new RandomFisheyeImageCommand());

        // big brain an image with face recognition when image and brain
        api.addMessageCreateListener(new BigBrainCommand());

        // Print the invite url of your bot
        System.out.println("Invite link: " + api.createBotInvite());
    }
}
