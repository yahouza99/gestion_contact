package com.example.contactpro;

public class Contact {
        private  String nom;
        private  String prenom;
        private  String tel;
        private String email;
        private String service;
        private String imgurl;

        public Contact(String nom, String prenom, String tel, String email, String service, String imgurl) {
            this.nom = nom;
            this.prenom = prenom;
            this.tel = tel;
            this.email = email;
            this.service = service;
            this.imgurl = imgurl;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgur(String imgurl) {
            this.imgurl = imgurl;
        }
    }

