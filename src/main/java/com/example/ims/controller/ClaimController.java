/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ims.controller;

import com.example.ims.model.Claim;
import com.example.ims.repository.ClaimRepository;
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
public class ClaimController {
    
    @Autowired
    private ClaimRepository claimRepository;
    
    @GetMapping("/claims")
    public ResponseEntity<List<Claim>> getAllClaims(@RequestParam(required = false) String claimNumber) {
        try {
            List<Claim> claims = new ArrayList<Claim>();

            if (claimNumber == null) {
                claimRepository.findAll().forEach(claims::add);
            }

            if (claims.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(claims, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/claims/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable("id") long id) {
        Optional<Claim> claimData = claimRepository.findById(id);

        if (claimData.isPresent()) {
            return new ResponseEntity<>(claimData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/claims")
    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
        try {
            Claim addClaim = claimRepository.save(new Claim(claim.getClaimNumber(), claim.getDescription(), claim.getClaimDate(), claim.getClaimStatus(), claim.getInsurancePolicy()));
            return new ResponseEntity<>(addClaim, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/claims/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable("id") long id, @RequestBody Claim claim) {
        Optional<Claim> claimData = claimRepository.findById(id);

        if (claimData.isPresent()) {
            Claim aClaim = claimData.get();
            aClaim.setClaimNumber(claim.getClaimNumber());
            aClaim.setDescription(claim.getDescription());
            aClaim.setClaimDate(claim.getClaimDate());
            aClaim.setClaimStatus(claim.getClaimStatus());
            aClaim.setInsurancePolicy(claim.getInsurancePolicy());

            return new ResponseEntity<>(claimRepository.save(aClaim), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/claims/{id}")
    public ResponseEntity<HttpStatus> deleteClaim(@PathVariable("id") long id) {
        try {
            claimRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
