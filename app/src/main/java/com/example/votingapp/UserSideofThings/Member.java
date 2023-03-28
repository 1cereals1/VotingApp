package com.example.votingapp.UserSideofThings;

public class Member {

    private String email;
    private  String name;
    private String firstname;
    private String lastname;
    private String contact;
    private String question;
    private  String address;
    private  String membership;
    private String age;

    private  String birth;

    private String vision;
    private String tvDisplay;

    private String gender;

    private String civil;

    private String elective;
    private Boolean votestatus;
    private Integer votes;



    public Member () {}

    public String getGender() { return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public String getCivil() { return civil;}
    public void setCivil(String civil) {this.civil = civil;}



    public String getElective() { return elective;}
    public void setElective(String elective) {this.elective = elective;}




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }



    public Boolean getVotestatus() {
        return votestatus;
    }

    public void setVotestatus(Boolean votestatus) {
        this.votestatus = votestatus;
    }


    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) { this.votes = votes; }


    public String getTvDisplay() {
        return tvDisplay;
    }

    public void setTvDisplay(String tvDisplay) {
        this.tvDisplay = tvDisplay;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
