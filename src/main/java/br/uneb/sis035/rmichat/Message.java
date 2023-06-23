package br.uneb.sis035.rmichat;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Message extends Serializable {
    ChatClient getSender();

    String getChannel();

    String getMessage();

    LocalDateTime getTimestamp();
    void setTimestamp(LocalDateTime ldt);
}
