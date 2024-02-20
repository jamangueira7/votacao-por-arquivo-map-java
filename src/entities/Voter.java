package entities;

import java.util.Objects;

public class Voter implements Comparable<Voter> {
    private Integer registration;
    private String name;
    private String lastname;
    private int candidate;

    public Voter(int registration, String name, String lastname) {
        this.registration = registration;
        this.name = name;
        this.lastname = lastname;
    }

    public int getRegistration() {
        return registration;
    }

    public void setRegistration(int registration) {
        registration = registration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setCandidate(Integer candidate) {
        this.candidate = candidate;
    }

    public Integer getCandidate() {
        return this.candidate;
    }

    @Override
    public String toString() {
        return name + " " + lastname + ", " + registration + ", " + candidate;
    }

    public String formatterResponse() {
        return "\n Nome: " + name + " " + lastname + "\n Numero de registro: " + registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return candidate == voter.candidate && Objects.equals(registration, voter.registration) && Objects.equals(name, voter.name) && Objects.equals(lastname, voter.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registration, name, lastname, candidate);
    }

    @Override
    public int compareTo(Voter other) {
        return registration.compareTo(other.getRegistration());
    }
}
