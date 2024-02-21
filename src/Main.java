import application.Elections;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        try {

            System.out.println("Gerar votação? (S/N)");
            String volting = sc.nextLine().toUpperCase();

            while (!volting.equals("S")) {
                System.out.print("Valor invalido: Gerar votação? (S/N) ");
                volting = sc.nextLine().toUpperCase();
            }
            System.out.println();
            System.out.println("Quantos eleitores? Escolha pelo menos 20;");
            int quantity  = sc.nextInt();
            while (quantity < 20) {
                System.out.print("Valor invalido: Quantos eleitores? Escolha pelo menos 20;");
                quantity  = sc.nextInt();
            }

            Elections elections = new Elections(quantity);

            elections.createVoting();

            elections.investigationByFile();
            System.out.println();
            System.out.println("Quer uma contagem virtual(V) ou usando arquivo(A)? Você pode escolher ambas tbm(T). (V/A/T)");
            String type = sc.nextLine().toUpperCase();

            while (!type.equals("V")
                    && !type.equals("A")
                    && !type.equals("T")
            ) {
                System.out.print("Valor invalido: Escolha V para virtual;/A arquivo /;T ambas;");
                type = sc.nextLine().toUpperCase();
            }

            if (type.equals("V")) {
                elections.virtualCounting();
            } else if(type.equals("A")) {
                elections.fileCounting();
            } else {
                elections.virtualCounting();
                elections.fileCounting();
            }

        } catch (Exception e) {
            System.out.println("Erro nas eleições: " + e.getMessage());
        }

        sc.close();
    }
}