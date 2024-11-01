package com.example.project;

public class pettt {

        private int petid;
        private String petname;
        private int age;
        private String breed;
        private String species;
        private String adoptionDate;
        private String admissionDate;
        private int adopterid;
        private int employeeid;

        // Constructor with all parameters
        public pettt (int petid, String petname, int age, String breed, String species,
                   String adoptionDate, String admissionDate, int adopterid, int employeeid) {
            this.petid = petid;
            this.petname = petname;
            this.age = age;
            this.breed = breed;
            this.species = species;
            this.adoptionDate = adoptionDate;
            this.admissionDate = admissionDate;
            this.adopterid = adopterid;
            this.employeeid = employeeid;
        }

        // Getters and setters for each field
        public int getPetid() { return petid; }
        public void setPetid(int petid) { this.petid = petid; }

        public String getPetname() { return petname; }
        public void setPetname(String petname) { this.petname = petname; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public String getBreed() { return breed; }
        public void setBreed(String breed) { this.breed = breed; }

        public String getSpecies() { return species; }
        public void setSpecies(String species) { this.species = species; }

        public String getAdoptionDate() { return adoptionDate; }
        public void setAdoptionDate(String adoptionDate) { this.adoptionDate = adoptionDate; }

        public String getAdmissionDate() { return admissionDate; }
        public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }

        public int getAdopterid() { return adopterid; }
        public void setAdopterid(int adopterid) { this.adopterid = adopterid; }

        public int getEmployeeid() { return employeeid; }
        public void setEmployeeid(int employeeid) { this.employeeid = employeeid; }

}
