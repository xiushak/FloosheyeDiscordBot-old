package discord.bot.commands;

import model.FaceRecognitionFishEyeModel;
import model.SimpleModel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import view.BasicJPGView;
import view.SimpleView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FisheyeAttachedImageCommand implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        for (MessageAttachment attachment : message.getAttachments()) {
            // get message
            Message comment = attachment.getMessage();
            String commentStr = comment.getContent();
            if (commentStr.equalsIgnoreCase("fish")) {
                try {
                    BufferedImage image = attachment.downloadAsImage().join();
                    String name = attachment.getFileName();
                    SimpleModel model = new FaceRecognitionFishEyeModel();
                    SimpleView view = new BasicJPGView(model);
                    model.setImage(image);
                    model.processImage(0, 0);
                    view.outputImage("discordImages/" + name);

                    event.getChannel().sendMessage("image fisheyed!");
                    event.getChannel().sendMessage(new File("discordImages/" + name));

                } catch (IOException | IllegalArgumentException e) {
                    event.getChannel().sendMessage("image failed to fisheye :(");
                }
            }
        }
    }
}
