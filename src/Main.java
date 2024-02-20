import entities.Candidate;
import entities.Voter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        String sourceFolderStr = "C:\\programacao\\java\\votacao-por-arquivo-map-java\\files\\";

        String[] names = {"João", "Pedro", "Paulo", "Jose", "Ricardo", "Antônio", "Luiz", "Carlos", "Bruno", "Gabriel"};
        String[] lastnames = {
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
        Integer[] candidatesNumber = {12, 13, 14, 15};

        deleteFiles(sourceFolderStr);

        Map<Integer, Voter> voters = new TreeMap<>();

        TreeMap<String, Candidate> candidates = new TreeMap<>();
        Candidate candidateObj1 = new Candidate("Candidato 2",12,  0);
        Candidate candidateObj2 = new Candidate("Candidato 3",13,  0);
        Candidate candidateObj3 = new Candidate("Candidato 4",14,  0);
        Candidate candidateObj4 = new Candidate("Candidato 1",15,  0);

        System.out.println("Iniciando votação!");
        System.out.println();

        try {
            for (int x=0; x<2000;x++) {
                Random random = new Random();
                int randomName = random.nextInt(9);
                int randomLastname = random.nextInt(14);
                int randomRegistration = random.nextInt(999999);
                int randomCandidates = random.nextInt(4);

                //Gerar eleitor
                Voter voter = new Voter(randomRegistration, names[randomName], lastnames[randomLastname]);
                voter.setCandidate(candidatesNumber[randomCandidates]);
                voters.put(randomRegistration, voter);

                if (candidatesNumber[randomCandidates] == 12) {
                    candidateObj1.setVoteNumber(1);
                } else if (candidatesNumber[randomCandidates] == 13) {
                    candidateObj2.setVoteNumber(1);
                } else if (candidatesNumber[randomCandidates] == 14) {
                    candidateObj3.setVoteNumber(1);
                } else {
                    candidateObj4.setVoteNumber(1);
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(sourceFolderStr + randomRegistration + ".txt"));
                String line = voter.toString();
                bw.write(line);
                bw.flush();
                bw.close();
                System.out.println("Registrando voto: " + voter.formatterResponse());
            }
            System.out.println();
            System.out.println("Finalizando votação!");

            candidates.put(candidateObj4.getVoteNumber() + candidateObj4.getName(), candidateObj4);
            candidates.put(candidateObj1.getVoteNumber() + candidateObj1.getName(), candidateObj1);
            candidates.put(candidateObj3.getVoteNumber() + candidateObj3.getName(), candidateObj3);
            candidates.put(candidateObj2.getVoteNumber() + candidateObj2.getName(), candidateObj2);

            System.out.println();
            System.out.println("Contabilizando votos virtualmente: ");
            System.out.println("Total de votos: " + voters.size());

            candidateObj4.setTotalVotes(voters.size());
            candidateObj1.setTotalVotes(voters.size());
            candidateObj3.setTotalVotes(voters.size());
            candidateObj2.setTotalVotes(voters.size());

            int i = candidates.size() - 1;
            for (Candidate can: candidates.values()) {
                if(i-- == 0){
                    System.out.println(can + " VENCEDOR!");
                } else {
                    System.out.println(can);
                }
            }


        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }

        try {
            System.out.println();
            System.out.println("Iniciando contagem de votos por arquivos: ");

            File sourceFiles = new File(sourceFolderStr);
            File[] files = sourceFiles.listFiles();

            //Zerando contagem dos candidatos
            candidateObj1.setVoteNumberWithZero();
            candidateObj2.setVoteNumberWithZero();
            candidateObj3.setVoteNumberWithZero();
            candidateObj4.setVoteNumberWithZero();

            TreeMap<String, Candidate> dataCandidates = new TreeMap<>();
            for(File file : files) {
                BufferedReader br = new BufferedReader(new FileReader(sourceFolderStr + file.getName()));
                System.out.println("Lendo arquivo " + file.getName());
                String item = br.readLine();
                String[] dataVote = item.split(", ");
                System.out.println("Voto no candidato: " + dataVote[2]);
                if (Integer.parseInt(dataVote[2]) == 12) {
                    candidateObj1.setVoteNumber(1);
                } else if (Integer.parseInt(dataVote[2]) == 13) {
                    candidateObj2.setVoteNumber(1);
                } else if (Integer.parseInt(dataVote[2]) == 14) {
                    candidateObj3.setVoteNumber(1);
                } else {
                    candidateObj4.setVoteNumber(1);
                }
            }

            dataCandidates.put(candidateObj4.getVoteNumber() + candidateObj4.getName(), candidateObj4);
            dataCandidates.put(candidateObj1.getVoteNumber() + candidateObj1.getName(), candidateObj1);
            dataCandidates.put(candidateObj3.getVoteNumber() + candidateObj3.getName(), candidateObj3);
            dataCandidates.put(candidateObj2.getVoteNumber() + candidateObj2.getName(), candidateObj2);

            System.out.println();
            System.out.println("Finalizando contagem de votos por arquivos: ");

            candidateObj4.setTotalVotes(files.length);
            candidateObj1.setTotalVotes(files.length);
            candidateObj3.setTotalVotes(files.length);
            candidateObj2.setTotalVotes(files.length);

            System.out.println();
            System.out.println("Contabilizando votos por arquivo: ");
            System.out.println("Total de votos: " + files.length);

            int i = dataCandidates.size() - 1;
            for (Candidate can: dataCandidates.values()) {
                if(i-- == 0){
                    System.out.println(can + " VENCEDOR!");
                } else {
                    System.out.println(can);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        sc.close();
    }

    private static void deleteFiles(String source) {
        File sourceFiles = new File(source);
        File[] files = sourceFiles.listFiles();

        for(File file : files) {
            if(file.getName().endsWith("txt")) {
                System.out.println("Apagando arquivo: " + file.getName());
                file.delete();
            }
        }
    }
}