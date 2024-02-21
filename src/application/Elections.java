package application;

import entities.Candidate;
import entities.Voter;

import java.io.*;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Elections {

    private static final String SOURCE_FOLDER_STR = "C:\\programacao\\java\\votacao-por-arquivo-map-java\\files\\";
    private static final  String[] NAMES = {"João", "Pedro", "Paulo", "Jose", "Ricardo", "Antônio", "Luiz", "Carlos", "Bruno", "Gabriel"};
    private static final  String[] LASTNAMES = {
            "Silva",
            "Santos",
            "Nogueira",
            "Vieira",
            "Oliveira",
            "Ferreira",
            "Alves",
            "Pereira",
            "Lima",
            "Costa",
            "França",
            "Gonçalves",
            "Batista",
            "Jesus",
            "Nascimento",
            "Correia"
    };
    private static final  Integer[] CANDIDATES_NUMBER = {12, 13, 14, 15};

    private Candidate candidateObj1;
    private Candidate candidateObj2;
    private Candidate candidateObj3;
    private Candidate candidateObj4;
    private int totalVotes = 0;
    private int quantityVotes = 20;

    private  Map<Integer, Voter> voters;
    private TreeMap<String, Candidate> candidates;
    private TreeMap<String, Candidate> dataCandidates;

    public Elections(int quantityVotes) {

        this.quantityVotes = quantityVotes;
        this.deleteFiles();

        this.voters = new TreeMap<>();

        this.candidates = new TreeMap<>();

        this.dataCandidates = new TreeMap<>();

        this.candidateObj1 = new Candidate("Candidato 2",12,  0);
        this.candidateObj2 = new Candidate("Candidato 3",13,  0);
        this.candidateObj3 = new Candidate("Candidato 4",14,  0);
        this.candidateObj4 = new Candidate("Candidato 1",15,  0);
    }

    private void scoreWinner(String type) {
        int i = type.equals("virtual") ?  candidates.size() - 1 : dataCandidates.size() - 1;
        for (Candidate can: type.equals("virtual") ? candidates.values() : dataCandidates.values()) {
            if(i-- == 0){
                System.out.println(can + " VENCEDOR!");
            } else {
                System.out.println(can);
            }
        }
    }

    private void addValueInScore(String type) {
        if(type.equals("virtual")) {
            this.candidates.put(this.candidateObj4.getVoteNumber() + this.candidateObj4.getName(), this.candidateObj4);
            this.candidates.put(this.candidateObj1.getVoteNumber() + this.candidateObj1.getName(), this.candidateObj1);
            this.candidates.put(this.candidateObj3.getVoteNumber() + this.candidateObj3.getName(), this.candidateObj3);
            this.candidates.put(this.candidateObj2.getVoteNumber() + this.candidateObj2.getName(), this.candidateObj2);

        } else {
            this.dataCandidates.put(this.candidateObj4.getVoteNumber() + this.candidateObj4.getName(), this.candidateObj4);
            this.dataCandidates.put(this.candidateObj1.getVoteNumber() + this.candidateObj1.getName(), this.candidateObj1);
            this.dataCandidates.put(this.candidateObj3.getVoteNumber() + this.candidateObj3.getName(), this.candidateObj3);
            this.dataCandidates.put(this.candidateObj2.getVoteNumber() + this.candidateObj2.getName(), this.candidateObj2);
        }
    }

    private void objsVoteNumberWithZero() {
        this.candidateObj1.setVoteNumberWithZero();
        this.candidateObj2.setVoteNumberWithZero();
        this.candidateObj3.setVoteNumberWithZero();
        this.candidateObj4.setVoteNumberWithZero();
    }

    public void investigationByFile() throws IOException {
        System.out.println();
        System.out.println("Iniciando contagem de votos por arquivos: ");

        File sourceFiles = new File(SOURCE_FOLDER_STR);
        File[] files = sourceFiles.listFiles();

        //Zerando contagem dos candidatos
        this.objsVoteNumberWithZero();

        for(File file : files) {
            BufferedReader br = new BufferedReader(new FileReader(SOURCE_FOLDER_STR + file.getName()));
            System.out.println("Lendo arquivo " + file.getName());
            String item = br.readLine();
            String[] dataVote = item.split(", ");
            System.out.println("Voto no candidato: " + dataVote[2]);

            this.addVotesToCandidate(Integer.parseInt(dataVote[2]));
        }

        this.totalVotes = files.length;
    }

    public void fileCounting() {
        System.out.println();
        System.out.println("Finalizando contagem de votos por arquivos: ");

        this.setTotalVotesByObjs(this.totalVotes);

        System.out.println();
        System.out.println("Contabilizando votos por arquivo: ");
        System.out.println("Total de votos: " + this.totalVotes);
        this.addValueInScore("file");
        this.scoreWinner("file");
    }


    public void virtualCounting() {
        System.out.println();
        System.out.println("Contabilizando votos virtualmente: ");
        int totalVotes = this.voters.size();
        System.out.println("Total de votos: " + totalVotes);

        this.setTotalVotesByObjs(totalVotes);
        this.addValueInScore("virtual");
        this.scoreWinner("virtual");

    }

    private void setTotalVotesByObjs(int totalVotes) {

        this.candidateObj4.setTotalVotes(totalVotes);
        this.candidateObj1.setTotalVotes(totalVotes);
        this.candidateObj3.setTotalVotes(totalVotes);
        this.candidateObj2.setTotalVotes(totalVotes);
    }

    public void createVoting() throws IOException {

        System.out.println();
        System.out.println("Iniciando votação!");
        System.out.println();

        try {
            for (int x=0; x<this.quantityVotes;x++) {
                Random random = new Random();
                int randomName = random.nextInt(9);
                int randomLastname = random.nextInt(14);
                int randomRegistration = random.nextInt(999999);
                int randomCandidates = random.nextInt(4);

                //Gerar eleitor
                Voter voter = new Voter(randomRegistration, NAMES[randomName], LASTNAMES[randomLastname]);
                voter.setCandidate(CANDIDATES_NUMBER[randomCandidates]);
                voters.put(randomRegistration, voter);

                this.addVotesToCandidate(CANDIDATES_NUMBER[randomCandidates]);

                BufferedWriter bw = new BufferedWriter(new FileWriter(SOURCE_FOLDER_STR + randomRegistration + ".txt"));
                String line = voter.toString();
                bw.write(line);
                bw.flush();
                bw.close();
                System.out.println("Registrando voto: " + voter.formatterResponse());
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Finalizando votação!");
    }

    private void addVotesToCandidate(int candidateNumber) {
        if (candidateNumber == 12) {
            candidateObj1.setVoteNumber(1);
        } else if (candidateNumber == 13) {
            candidateObj2.setVoteNumber(1);
        } else if (candidateNumber == 14) {
            candidateObj3.setVoteNumber(1);
        } else {
            candidateObj4.setVoteNumber(1);
        }
    }

    private void deleteFiles() {
        File sourceFiles = new File(SOURCE_FOLDER_STR);
        File[] files = sourceFiles.listFiles();

        for(File file : files) {
            if(file.getName().endsWith("txt")) {
                System.out.println("Apagando arquivo: " + file.getName());
                file.delete();
            }
        }
    }
}
