package br.uneb.sis035.rmichat.client;

import br.uneb.sis035.rmichat.ChatClient;
import br.uneb.sis035.rmichat.Message;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageImpl implements Message {
    private final ChatClient sender;
    private final String channel;
    private final String message;
    private LocalDateTime timestamp;

    public MessageImpl(ChatClient sender, String channel, String message) {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
    }
}
