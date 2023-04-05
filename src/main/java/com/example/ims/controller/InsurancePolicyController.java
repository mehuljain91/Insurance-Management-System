/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ims.controller;

import com.example.ims.model.InsurancePolicy;
import com.example.ims.repository.InsurancePolicyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author abc
 */

@RestController
@RequestMapping("/api")
public class InsurancePolicyController {
    
    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;
    
    @GetMapping("/policies")
    public ResponseEntity<List<InsurancePolicy>> getAllPolicies(@RequestParam(required = false) String policyNumber) {
        try {
            List<InsurancePolicy> policies = new ArrayList<InsurancePolicy>();

            if (policyNumber == null) {
                insurancePolicyRepository.findAll().forEach(policies::add);
            }

            if (policies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(policies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/policies/{id}")
    public ResponseEntity<InsurancePolicy> getPolicyById(@PathVariable("id") long id) {
        Optional<InsurancePolicy> policyData = insurancePolicyRepository.findById(id);

        if (policyData.isPresent()) {
            return new ResponseEntity<>(policyData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/polcies")
    public ResponseEntity<InsurancePolicy> createPolicy(@RequestBody InsurancePolicy insurancePolicy) {
        try {
            InsurancePolicy addPolicy = insurancePolicyRepository.save(new InsurancePolicy(insurancePolicy.getPolicyNumber(), insurancePolicy.getType(), insurancePolicy.getCoverageAmount(), insurancePolicy.getPremium(), insurancePolicy.getStartDate(), insurancePolicy.getStartDate(), insurancePolicy.getClient()));
            return new ResponseEntity<>(addPolicy, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/policies/{id}")
    public ResponseEntity<InsurancePolicy> updatePolicy(@PathVariable("id") long id, @RequestBody InsurancePolicy insurancePolicy) {
        Optional<InsurancePolicy> policyData = insurancePolicyRepository.findById(id);

        if (policyData.isPresent()) {
            InsurancePolicy aPolicy = policyData.get();
            aPolicy.setPolicyNumber(insurancePolicy.getPolicyNumber());
            aPolicy.setType(insurancePolicy.getType());
            aPolicy.setCoverageAmount(insurancePolicy.getCoverageAmount());
            aPolicy.setPremium(insurancePolicy.getPremium());
            aPolicy.setStartDate(insurancePolicy.getStartDate());
            aPolicy.setEndDate(insurancePolicy.getEndDate());
            aPolicy.setClient(insurancePolicy.getClient());

            return new ResponseEntity<>(insurancePolicyRepository.save(aPolicy), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/policies/{id}")
    public ResponseEntity<HttpStatus> deletePolicy(@PathVariable("id") long id) {
        try {
            insurancePolicyRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
