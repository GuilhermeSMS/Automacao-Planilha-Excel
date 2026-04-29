package com.automacao;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 

public class AutomacaoScan {

    public static void main(String[] args) {

        String CAMINHO_CHROMEDRIVER = "chromedriver.exe";
        String CAMINHO_PLANILHA = "automacao.xlsx";
        String URL_DO_SITE_FLOW = "https://www.amazonlogistics.com/sortcenter/m/containerization/flow?containerId=&stackingAreaId=";

        System.setProperty("webdriver.chrome.driver", CAMINHO_CHROMEDRIVER);
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Scanner leitorTerminal = new Scanner(System.in);
        System.out.println("Continuando automação...");


       
        List<String> codigosParaEscanear = new ArrayList<>();

        try {
            
            driver.get(URL_DO_SITE_FLOW);
            driver.manage().window().maximize();

           
            System.out.println("======================================================");
            System.out.println("--- SCRIPT PAUSADO ---");
            System.out.println("O navegador foi aberto.");
            System.out.println("1. Faça o login manualmente (Email, Senha).");
            System.out.println("2. Espere a página principal (a de scan) carregar.");
            System.out.println("3. Volte para esta janela preta e aperte ENTER.");
            System.out.println("======================================================");
            
            leitorTerminal.nextLine();

            while (true) {
            
            LeitorExcel leitor = new LeitorExcel();
            

            codigosParaEscanear = leitor.lerCodigosDaPlanilha(CAMINHO_PLANILHA);

            String ID_CAMPO_SCAN = "sd_input";
            
            for (String codigo : codigosParaEscanear) {
                try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id(ID_CAMPO_SCAN)));
            WebElement campoScan = driver.findElement(By.id(ID_CAMPO_SCAN));
            
            System.out.println("Enviando código: " + codigo);
            campoScan.clear();
            campoScan.sendKeys(codigo);
            campoScan.sendKeys(Keys.ENTER);

            
           
            Thread.sleep(1000);

                } catch (Exception e) {
                }
            }
                System.out.println("\n=================================================");
                System.out.println("PROCESSO CONCLUÍDO. Deseja rodar novamente?");
                System.out.println("Digite 'SIM' para ler a planilha e escanear de novo.");
                System.out.println("Digite 'NAO' para encerrar o programa.");
                System.out.println("=================================================");


                String resposta = leitorTerminal.nextLine();


                if (resposta.equalsIgnoreCase("SIM")) {

                    System.out.println("Certo! Reiniciando o processo de scan...");

                } else {

                    System.out.println("Certo! Encerrando o programa.");
                    break;

                }
            leitorTerminal.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("======================================================");
            System.out.println("SCAN FINALIZADO!");
            System.out.println("Total de pacotes na planilha: " + codigosParaEscanear.size());
            System.out.println("======================================================");
            try {
        Thread.sleep(20000);
}       catch (InterruptedException e) {
    
        e.printStackTrace();
}

        }
    }
}
