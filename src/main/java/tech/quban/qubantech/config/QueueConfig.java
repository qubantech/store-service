package tech.quban.qubantech.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class QueueConfig {
    private final AmqpAdmin amqpAdmin;
    public static final String INCOMING_COMPLAINTS = "INCOMING_COMPLAINTS";
    public static final String NEED_TO_ANALYSE = "NEED_TO_ANALYSE";
    public static final String ANALYSED_COMPLAINTS = "ANALYSED_COMPLAINTS";

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue(INCOMING_COMPLAINTS, true));
        amqpAdmin.declareQueue(new Queue(NEED_TO_ANALYSE, true));
        amqpAdmin.declareQueue(new Queue(ANALYSED_COMPLAINTS, true));
    }
}