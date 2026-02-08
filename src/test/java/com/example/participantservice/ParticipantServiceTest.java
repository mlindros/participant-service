package com.example.participantservice;

import com.example.participantservice.domain.entity.Participant;
import com.example.participantservice.dto.response.ParticipantResponse;
import com.example.participantservice.exception.ParticipantException;
import com.example.participantservice.mapper.ParticipantMapper;
import com.example.participantservice.repository.ParticipantRepository;
import com.example.participantservice.service.ParticipantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ParticipantMapper participantMapper;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    @DisplayName("Should return participant response when ID exists")
    void findById_Success() {
        //Arrange
        Long id = 1L;
        Participant participant = new Participant(); // assume this has data
        ParticipantResponse response = new ParticipantResponse(id, "Mark", "Lindros", "mlindros@gmail.com", null, "ACTIVE");

        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));
        when(participantMapper.toResponse(participant)).thenReturn(response);

        //Act
        ParticipantResponse result = participantService.getById(id);

        //Assert
        assertNotNull(result);
        assertEquals("Mark", result.firstName());
        verify(participantRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw ParticipantException when ID does not exist")
    void findById_NotFound() {
        //Arrange
        Long id = 99L;
        when(participantRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ParticipantException.class, () -> {
            participantService.getById(id);
        });
    }
}