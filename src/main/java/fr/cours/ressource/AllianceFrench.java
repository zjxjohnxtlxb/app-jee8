package fr.cours.ressource;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class AllianceFrench implements Serializable {
    private static final long serialVersionUID = 110L;

    @Column(unique = true, nullable = false)
    @NotNull
    private String nom;
    @Column(nullable = false)
    @NotNull
    private String zonegeo;
    @Column(nullable = false)
    @NotNull
    private String pays;
    @Column
    private String region_comte;
    @Column
    private String ville;
    @Column
    private String adresse;
    @Column
    private String code_tel;
    @Column
    private String tel_1;
    @Column
    private String tel_2;
    @Column
    private String tel_3;
    @Column
    private String fax_1;
    @Column
    private String fax_2;
    @Column
    private String email_1;
    @Column
    private String email_2;
    @Column
    private String email_3;
    @Column
    private String web_1;
    @Column
    private String web_2;

    @Id
    @GeneratedValue
    private Long id;

    public AllianceFrench() {
    }

    public AllianceFrench(String nom, String zonegeo, String pays, String adresse) {
        this.nom = nom;
        this.zonegeo = zonegeo;
        this.pays = pays;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getZonegeo() {
        return zonegeo;
    }

    public void setZonegeo(String zonegeo) {
        this.zonegeo = zonegeo;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getRegion_comte() {
        return region_comte;
    }

    public void setRegion_comte(String region_comte) {
        this.region_comte = region_comte;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCode_tel() {
        return code_tel;
    }

    public void setCode_tel(String code_tel) {
        this.code_tel = code_tel;
    }

    public String getTel_1() {
        return tel_1;
    }

    public void setTel_1(String tel_1) {
        this.tel_1 = tel_1;
    }

    public String getTel_2() {
        return tel_2;
    }

    public void setTel_2(String tel_2) {
        this.tel_2 = tel_2;
    }

    public String getTel_3() {
        return tel_3;
    }

    public void setTel_3(String tel_3) {
        this.tel_3 = tel_3;
    }

    public String getFax_1() {
        return fax_1;
    }

    public void setFax_1(String fax_1) {
        this.fax_1 = fax_1;
    }

    public String getFax_2() {
        return fax_2;
    }

    public void setFax_2(String fax_2) {
        this.fax_2 = fax_2;
    }

    public String getEmail_1() {
        return email_1;
    }

    public void setEmail_1(String email_1) {
        this.email_1 = email_1;
    }

    public String getEmail_2() {
        return email_2;
    }

    public void setEmail_2(String email_2) {
        this.email_2 = email_2;
    }

    public String getEmail_3() {
        return email_3;
    }

    public void setEmail_3(String email_3) {
        this.email_3 = email_3;
    }

    public String getWeb_1() {
        return web_1;
    }

    public void setWeb_1(String web_1) {
        this.web_1 = web_1;
    }

    public String getWeb_2() {
        return web_2;
    }

    public void setWeb_2(String web_2) {
        this.web_2 = web_2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AllianceFrench{" +
                "nom='" + nom + '\'' +
                ", zonegeo='" + zonegeo + '\'' +
                ", pays='" + pays + '\'' +
                ", region_comte='" + region_comte + '\'' +
                ", ville='" + ville + '\'' +
                ", adresse='" + adresse + '\'' +
                ", code_tel='" + code_tel + '\'' +
                ", tel_1='" + tel_1 + '\'' +
                ", tel_2='" + tel_2 + '\'' +
                ", tel_3='" + tel_3 + '\'' +
                ", fax_1='" + fax_1 + '\'' +
                ", fax_2='" + fax_2 + '\'' +
                ", email_1='" + email_1 + '\'' +
                ", email_2='" + email_2 + '\'' +
                ", email_3='" + email_3 + '\'' +
                ", web_1='" + web_1 + '\'' +
                ", web_2='" + web_2 + '\'' +
                ", id=" + id +
                '}';
    }
}
