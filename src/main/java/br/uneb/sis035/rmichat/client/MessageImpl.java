package br.uneb.sis035.rmichat.client;

import br.uneb.sis035.rmichat.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageImpl implements Message {
    private String sender;
    private String channel;
    private String message;
    private LocalDateTime timestamp;

    public MessageImpl(String sender, String channel, String message) {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
    }
}
