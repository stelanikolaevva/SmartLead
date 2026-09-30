package com.demoprojects.smartlead.lead;

import com.demoprojects.smartlead.common.error.exceptions.LeadNotFoundException;
import com.demoprojects.smartlead.lead.dto.LeadResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeadServiceTest {

    @Mock
    private LeadRepository leadRepository;
    @Mock
    private LeadMapper leadMapper;

    @InjectMocks
    private LeadService leadService;


    @Test
    void shouldReturnAllLeadsMappedToDto() {
        Lead first = new Lead("title", LeadType.DEMO_REQUEST, UrgencyLevel.HIGH, "Summary");
        Lead second = new Lead("title", LeadType.DEMO_REQUEST, UrgencyLevel.HIGH, "Summary");

        LeadResponse firstDto = mock(LeadResponse.class);
        LeadResponse secondDto = mock(LeadResponse.class);

        when(leadRepository.findAll()).thenReturn(List.of(first, second));
        when(leadMapper.mapToResponse(any())).thenReturn(firstDto).thenReturn(secondDto);

        List<LeadResponse> allLeads = leadService.getAllLeads();
        assertThat(allLeads).containsExactly(firstDto, secondDto);
    }

    @Test
    void whenEmptyListThenReturnEmptyList() {
        when(leadRepository.findAll()).thenReturn(List.of());

        List<LeadResponse> result = leadService.getAllLeads();

        assertThat(result).isEmpty();
        verifyNoInteractions(leadMapper);
    }

    @Test
    void shouldReturnLeadById() {
        Lead lead = new Lead("title", LeadType.DEMO_REQUEST, UrgencyLevel.HIGH, "Summary");
        LeadResponse leadDto = mock(LeadResponse.class);

        when(leadRepository.findById(lead.getId())).thenReturn(Optional.of(lead));
        when(leadMapper.mapToResponse(any())).thenReturn(leadDto);


        LeadResponse result = leadService.getLeadById(lead.getId());
        assertThat(result).isEqualTo(leadDto);
    }

    @Test
    void shouldThrowExceptionWhenLeadNotFound() {
        when(leadRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> leadService.getLeadById(any()))
                .isInstanceOf(LeadNotFoundException.class)
                .hasMessage("Lead with id null is not found");

        verifyNoInteractions(leadMapper);
    }
}