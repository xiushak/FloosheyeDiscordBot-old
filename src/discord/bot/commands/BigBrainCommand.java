package discord.bot.commands;

import model.FaceRecognitionBigBrainModel;
import model.SimpleModel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import view.BasicJPGView;
import view.SimpleView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BigBrainCommand implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        String content = message.getContent();
        if (content.equalsIgnoreCase("brain")) {
            for (MessageAttachment attachment : message.getAttachments()) {
                if (!event.getMessageAuthor().isBotUser() && attachment.isImage()) {
                    System.out.println("Image detected!");
                    try {
                        BufferedImage image = attachment.downloadAsImage().join();
                        String name = attachment.getFileName();
                        SimpleModel model = new FaceRecognitionBigBrainModel();
                        SimpleView view = new BasicJPGView(model);
                        model.setImage(image);
                        model.processImage(0, 0);
                        view.outputImage("discordImages/" + name);

                        String[] fisheye = {"brainiac"};
                        Random r = new Random();
                        new MessageBuilder()
                                .append(fisheye[r.nextInt(fisheye.length)])
                                .addAttachment(new File("discordImages/" + name))
                                .send(event.getChannel());

                        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                        scheduler.scheduleAtFixedRate(() -> new File("discordImages/" + name).delete(), 1, 3L, TimeUnit.SECONDS);
                        System.out.println("Image brained by command");
                    } catch (IOException | IllegalArgumentException e) {
                        event.getChannel().sendMessage("can't become big brained...");
                    }
                }
            }
        }
    }
}
