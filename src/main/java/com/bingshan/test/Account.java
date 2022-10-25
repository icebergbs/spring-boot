package com.bingshan.test;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Account implements Serializable {


    private static final long serialVersionUID = -1354095961912360712L;
    private String accountId;

    private String accountName;

    @JsonProperty("PHoNe")
    private String PHoNe;


    private Account account;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPHoNe() {
        return PHoNe;
    }

    public void setPHoNe(String PHoNe) {
        this.PHoNe = PHoNe;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", PHoNe='" + PHoNe + '\'' +
                ", account=" + account +
                '}';
    }
}
