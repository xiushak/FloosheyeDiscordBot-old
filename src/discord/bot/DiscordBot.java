package discord.bot;

import discord.bot.commands.CheckOnlineCommand;
import discord.bot.commands.FisheyeAttachedImageCommand;
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
        api.addMessageCreateListener(new CheckOnlineCommand());

        // fisheyes an image with face recognition when image and fish
        api.addMessageCreateListener(new FisheyeAttachedImageCommand());

        // Print the invite url of your bot
        System.out.println("Invite link: " + api.createBotInvite());
    }
}
