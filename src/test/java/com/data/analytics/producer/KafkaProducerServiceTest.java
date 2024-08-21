package com.data.analytics.producer;

import com.data.analytics.dto.EventPayload;
import com.data.analytics.enricher.impl.MetaDataEnricher;
import com.data.analytics.exception.IngestionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private MetaDataEnricher metaDataEnricher;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendWithEnrichment() throws JsonProcessingException {
        EventPayload payload = new EventPayload();
        payload.setEventId("eventId");
        payload.setTopic("test-topic");

        when(metaDataEnricher.enrich(payload)).thenReturn(payload);

        String message = "{\"eventId\":\"eventId\"}";
        when(objectMapper.writeValueAsString(payload)).thenReturn(message);

        ListenableFuture<SendResult<String, String>> future = mock(ListenableFuture.class);
        when(kafkaTemplate.send(any(), any())).thenReturn(future);

        kafkaProducerService.send(payload);

        verify(metaDataEnricher, times(1)).enrich(any());
        verify(kafkaTemplate, times(1)).send(any(), any());
    }

    @Test
    void testSendHandlesJsonProcessingException() throws JsonProcessingException {
        EventPayload payload = new EventPayload();
        payload.setEventId("eventId");
        payload.setTopic("test-topic");

        // Mock the enrichment process
        when(metaDataEnricher.enrich(payload)).thenReturn(payload);

        // Mock a JSON processing exception
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        // Mock the send method of kafkaTemplate to return a successful ListenableFuture
        ListenableFuture<SendResult<String, String>> future = mock(ListenableFuture.class);
        when(kafkaTemplate.send(any(), any())).thenReturn(future);

        assertThrows(IngestionException.class, () -> kafkaProducerService.send(payload));

        verify(metaDataEnricher, times(1)).enrich(payload);
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }

    @Test
    void testSendHandlesKafkaTemplateException() throws JsonProcessingException {
        EventPayload payload = new EventPayload();
        payload.setEventId("eventId");
        payload.setTopic("test-topic");

        when(metaDataEnricher.enrich(payload)).thenReturn(payload);

        String message = "{\"eventId\":\"eventId\"}";
        when(objectMapper.writeValueAsString(payload)).thenReturn(message);

        doThrow(new RuntimeException("Kafka send error")).when(kafkaTemplate).send(any(), any());

        assertThrows(IngestionException.class, () -> {
            kafkaProducerService.send(payload);
        });

        verify(metaDataEnricher, times(1)).enrich(any());
        verify(kafkaTemplate, times(1)).send(any(), any());
    }
}