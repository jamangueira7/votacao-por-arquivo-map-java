package entities;

import java.util.Objects;

public class Candidate implements Comparable<Candidate>{

    private String name;
    private int number;
    private Integer voteNumber;
    private Integer total;

    public Candidate(String name, int number, int voteNumber) {
        this.name = name;
        this.number = number;
        this.voteNumber = voteNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(int vote) {
        this.voteNumber += vote;
    }

    public void setVoteNumberWithZero() {
        this.voteNumber = 0;
    }

    public void setTotalVotes(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return number == candidate.number && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteNumber);
    }

    @Override
    public int compareTo(Candidate other) {
        return this.voteNumber + other.getVoteNumber();
    }

    @Override
    public String toString() {

        return "Total de votos do " + getName() + "(" + getNumber() + "): "
                + getVoteNumber() + " - "
                + String.format("%,.2f", this.calculatePercentageOfVotes())
                + "% do total.";
    }

    private double calculatePercentageOfVotes() {
        return ((double)this.voteNumber / (double)total) * 100;
    }
}


