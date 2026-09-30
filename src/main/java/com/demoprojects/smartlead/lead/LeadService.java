package com.demoprojects.smartlead.lead;

import com.demoprojects.smartlead.common.error.exceptions.LeadNotFoundException;
import com.demoprojects.smartlead.lead.dto.LeadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final LeadMapper leadMapper;

    public List<LeadResponse> getAllLeads() {
        return leadRepository.findAll()
                .stream()
                .map(leadMapper::mapToResponse)
                .toList();
    }

    public LeadResponse getLeadById(Long id) {
        Lead byId = leadRepository.findById(id)
                .orElseThrow(() ->
                        new LeadNotFoundException("Lead with id " + id + " is not found"));

        return leadMapper.mapToResponse(byId);
    }
}
