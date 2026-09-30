package com.demoprojects.smartlead.lead;

import com.demoprojects.smartlead.lead.dto.LeadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping("/leads")
    public ResponseEntity<List<LeadResponse>> getLeads() {
        List<LeadResponse> response = leadService.getAllLeads();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/leads/{id}")
    public ResponseEntity<LeadResponse> getLeads(@PathVariable Long id) {
        LeadResponse response = leadService.getLeadById(id);

        return ResponseEntity.ok(response);
    }
}
