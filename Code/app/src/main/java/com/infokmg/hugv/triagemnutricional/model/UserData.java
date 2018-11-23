package com.infokmg.hugv.triagemnutricional.model;

import com.infokmg.hugv.triagemnutricional.service.NutritionalCalculations;

import java.util.Date;

/**
 * Created by Klinsman on 03/05/2017.
 */

public class UserData {
    private Long id;
    private String name;
    private String register;
    private String coMorb;
    private String medicalDiegnostic;
    private String clinic;
    private Integer room;
    private Double actualWeigth;
    private Double usualWeigth;
    private Double heigth;
    private Integer age;
    private Integer nutritionalState;
    private Integer diseaseRisk;
    private Date dateCreated;
    private String sex;
    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getCoMorb() {
        return coMorb;
    }

    public void setCoMorb(String coMorb) {
        this.coMorb = coMorb;
    }

    public String getMedicalDiegnostic() {
        return medicalDiegnostic;
    }

    public void setMedicalDiegnostic(String medicalDiegnostic) {
        this.medicalDiegnostic = medicalDiegnostic;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Double getActualWeigth() {
        return actualWeigth;
    }

    public void setActualWeigth(Double actualWeigth) {
        this.actualWeigth = actualWeigth;
    }

    public Double getUsualWeigth() {
        return usualWeigth;
    }

    public void setUsualWeigth(Double usualWeigth) {
        this.usualWeigth = usualWeigth;
    }

    public Double getHeigth() {
        return heigth;
    }

    public void setHeigth(Double heigth) {
        this.heigth = heigth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNutritionalState() {
        return nutritionalState;
    }

    public void setNutritionalState(Integer nutritionalState) {
        this.nutritionalState = nutritionalState;
    }

    public Integer getDiseaseRisk() {
        return diseaseRisk;
    }

    public void setDiseaseRisk(Integer diseaseRisk) {
        this.diseaseRisk = diseaseRisk;
    }

    public Date getDateCreated() {
        return dateCreated;
    }



    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Double getIMC(){

        if(heigth !=null && actualWeigth != null){
            return NutritionalCalculations.getIMCValue(heigth, actualWeigth);
        }else{
            return null;
        }
    }

    public String getIMCDiagnostic(){
        if(NutritionalCalculations.getIMCValue(heigth, actualWeigth)!= null){
            return NutritionalCalculations.getIMCDiagnostic(NutritionalCalculations.getIMCValue(heigth, actualWeigth));
        }else{
            return null;
        }
    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
