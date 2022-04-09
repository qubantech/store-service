package tech.quban.qubantech.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import tech.quban.qubantech.models.Complaint;
import tech.quban.qubantech.repository.ComplaintRepository;

import static tech.quban.qubantech.config.QueueConfig.*;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class MQService {
    private final RabbitTemplate rabbitTemplate;
    private final ComplaintRepository complaintRepository;

    @RabbitListener(queues = INCOMING_COMPLAINTS)
    private void listenIncomingComplaints(Complaint complaint) {
        System.out.println("received from " + INCOMING_COMPLAINTS);
        complaintRepository.save(complaint);
        System.out.println("saved " + complaint);
        rabbitTemplate.convertAndSend(NEED_TO_ANALYSE, complaint);
        System.out.println("sent to " + NEED_TO_ANALYSE);
        System.out.println(complaint);
    }

    @RabbitListener(queues = ANALYSED_COMPLAINTS)
    private void listenAnalysedComplaints(Complaint complaint) {
        complaintRepository.save(complaint);
        System.out.println("received from " + ANALYSED_COMPLAINTS);
        System.out.println(complaint);
    }

    @RabbitListener(queues = NEED_TO_ANALYSE)
    private void listenNeedToAnalyse(Complaint complaint) {
        System.out.println("received from " + NEED_TO_ANALYSE);
        rabbitTemplate.convertAndSend(ANALYSED_COMPLAINTS, complaint);
        System.out.println("sent to " + ANALYSED_COMPLAINTS);
    }

}
