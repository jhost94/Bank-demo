package com.bringacademy.fabio.BankDemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

/** This indicates that any properties not bound in this type should be ignored. */

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bank {

    private String id;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("full_name")
    private String fullName;

    @Override
    public String toString(){
        return "id: " + id + "\n" +
                "Short name: " + shortName + "\n" +
                "Full name: " + fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static class BankGroup{

        public BankGroup(){}

        public BankGroup(List<Bank> bankList){
            this.banks = bankList;
        }

        private List<Bank> banks;

        public List<Bank> getBanks() {
            return banks;
        }

        public void setBanks(List<Bank> banks) {
            this.banks = banks;
        }
    }
}
