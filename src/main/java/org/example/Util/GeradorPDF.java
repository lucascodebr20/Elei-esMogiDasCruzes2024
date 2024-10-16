package org.example.Util;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.example.Model.Candidato;
import org.example.Model.Sessao;
import org.example.Model.Voto;
import org.example.Service.CandidatoService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeradorPDF {

    private List<Candidato> candidatos;
    private List <Voto> votos;
    private List <Sessao> sessoes;

    public GeradorPDF(List<Candidato> candidatos, List<Voto> votos, List<Sessao> sessoes) {
        this.candidatos = candidatos;
        this.votos = votos;
        this.sessoes = sessoes;
    }

    public void gerarPDF(String numeroCandidato) throws IOException {
        CandidatoService candidatoService = new CandidatoService(candidatos,votos,sessoes);
        Optional<Candidato> candidatoAtual = candidatoService.retornaCandidato(numeroCandidato);

        String dest = candidatoAtual.get().getNome() +
                candidatoAtual.get().getNumeroCandidato() + ".pdf";

        PdfWriter pdfWriter = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);

        pdfDoc.addNewPage();

        Document document = new Document(pdfDoc);

        document.setMargins(50,60,40,60);
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        PdfFont fontBold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        document.setFont(font);
        document.setFontSize(12);

        Paragraph title = new Paragraph("Eleições Mogi das Cruzes")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        for (int i = 0; i < 12; i++) {
            document.add(new Paragraph("\n"));
        }

        // Add subtitle
        Paragraph subtitle = new Paragraph("Análise individual do candidato")
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(subtitle);
        Paragraph subtitleNome = new Paragraph(candidatoAtual.get().getNome())
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(subtitleNome);

        for (int i = 0; i < 12; i++) {
            document.add(new Paragraph("\n"));
        }

        // Add date/location
        Paragraph location = new Paragraph("Mogi das Cruzes - 2024")
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(location);

        document.add(new AreaBreak());

        pdfDoc.addNewPage();

        // PAGINA 2 //

        Paragraph subtitlePage2 = new Paragraph("Introdução")
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(subtitlePage2);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Este relatório apresenta uma análise detalhada e " +
                "individual de cada candidato, enfatizando seus pontos fortes e características relevantes. " +
                "O objetivo é fornecer informações valiosas que possam ser utilizadas para estudos " +
                "e reflexões futuras sobre o cenário político local.\n"));

        document.add(new Paragraph("O documento foi elaborado gratuitamente " +
                "pela plataforma Munícipe Brasil, que se dedica a colaborar com agentes " +
                "políticos da região, auxiliando na gestão de gabinetes e oferecendo análises de " +
                "dados relevantes. A plataforma busca promover uma maior transparência e eficácia na " +
                "atuação política, facilitando o acesso à informação para todos os interessados. " +
                "É importante ressaltar que todas as informações coletadas são de natureza pública e " +
                "estão disponíveis no site https://www.tse.jus.br/, garantindo que qualquer cidadão " +
                "possa consultar os dados e informações relevantes.\n"));

        document.add(new Paragraph("Antes de iniciar o relatório, é fundamental mencionar que " +
                "podem ocorrer divergências nos dados em relação à realidade, uma vez que toda a análise é " +
                "baseada em sessões e zonas eleitorais. Isso significa que um munícipe residente " +
                "na região X pode acabar votando na região Y, especialmente se ainda não estiver " +
                "atualizado sua informação de zona eleitoral junto aos órgãos competentes. " +
                "Essa situação pode impactar a percepção dos eleitores sobre candidatos e propostas, " +
                "tornando essencial a compreensão desse aspecto para uma análise mais precisa.\n"));

        document.add(new Paragraph("Se você tiver qualquer dúvida ou precisar de esclarecimentos " +
                "adicionais sobre este documento, não hesite em nos contatar. Estamos disponíveis " +
                "por e-mail, no endereço lucascode@gmail.com, ou pelo telefone (11) 99771-3133. " +
                "Nossa equipe está pronta para ajudar e fornecer as informações necessárias.\n"));

        document.add(new AreaBreak());

        pdfDoc.addNewPage();

        // PAGINA 3 //

        Paragraph subtitlePage3 = new Paragraph("Mogi das Cruzes")
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(subtitlePage3);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("A cidade de Mogi das Cruzes, em São Paulo, possui cerca " +
                "de 450.000 habitantes. Nas últimas eleições, foram registrados 247.955 votos, " +
                "dos quais 217.535 foram válidos, contabilizados para candidatos e partidos. " +
                "Além disso, 16.232 eleitores votaram em branco, optando por não escolher candidatos," +
                " e 13.319 votos foram nulos. Houve ainda 90.385 abstenções, " +
                "representando os eleitores que não compareceram às urnas. " +
                "Esses números refletem o nível de participação e o engajamento eleitoral da " +
                "população local.\n"));

        document.add(new Paragraph("Como uma grande cidade, Mogi das Cruzes conta " +
                "com três zonas eleitorais: a 74ª, a 287ª e a 389ª. Dentre elas, " +
                "a 287ª zona concentra a maior parte das seções de votação, atendendo " +
                "uma parcela significativa da população e facilitando o acesso " +
                "dos eleitores ao processo eleitoral.\n"));

        List<String> bairros = candidatoService.listaBairros();

        String listaBairros = bairros.stream()
                .sorted()
                .collect(Collectors.joining(", "));

        document.add(new Paragraph("Os bairros englobados " +
                "nestas Zonas Eleitorais são: " + listaBairros + "\n"));


        document.add(new Paragraph());

        document.add(new AreaBreak());

        pdfDoc.addNewPage();

        // PAGINA 4 //

        document.add(new Paragraph("Nome: " + candidatoAtual.get().getNome()
                + " " + candidatoAtual.get().getNumeroCandidato() + "\n" +
                "Total de Votos: " + candidatoAtual.get().totalVotos()));

        document.add(new Paragraph()).setFont(font);

        Paragraph subtitlePage4B = new Paragraph("SEUS VOTOS POR SESSÃO")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(fontBold);
        document.add(subtitlePage4B);

        Map<Sessao, Integer> sessaoIntegerMap = candidatoService.colegioMaisVotadasDeUmCandidato(numeroCandidato);

        int contador = 0;

        for (Map.Entry<Sessao, Integer> entry : sessaoIntegerMap.entrySet()) {
            if (contador == 200) {
                break;
            }
            Sessao sessao = entry.getKey();
            Integer votos = entry.getValue();
            String colegio = sessao.getColegio();
            if (colegio.length() > 30) {
                colegio = colegio.substring(0, 27) + "...";
            }

            String bairro = sessao.getBairro().replace("Jardim", "Jd.");

            document.add(new Paragraph(
                    String.format("%02d", contador + 1) + "º - " + colegio +
                            " - Bairro: " + bairro +
                            " - Total de Votos: " + votos)).setFont(font);
            contador++;

        }

        document.add(new AreaBreak());

        pdfDoc.addNewPage();

        // Pagina 5 //

        Paragraph subtitlePage5 = new Paragraph("SEUS VOTOS POR REGIÃO")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(fontBold);
        document.add(subtitlePage5);

        document.add(new Paragraph()).setFont(font);

        Map<String, Integer> bairrosMaisVotado = candidatoService.bairrosMaisVotadasDeUmCandidato(numeroCandidato);

        int contador2 = 0;

        for (Map.Entry<String, Integer> entry : bairrosMaisVotado.entrySet()) {
            if (contador2 == 80) {
                break;
            }
            String bairro = entry.getKey();
            Integer votos = entry.getValue();
            document.add(new Paragraph(
                    String.format("%02d", contador2 + 1) + "º - " +
                            " Bairro: " + bairro + " - " +
                            " Total de Votos: " + votos)).setFont(font);
            contador2++;

        }

        document.add(new AreaBreak());

        pdfDoc.addNewPage();

        // Pagina 6,7,8,9,10//

        Paragraph subtitlePage5B = new Paragraph("RANKING POR BAIRRO")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(fontBold);
        document.add(subtitlePage5B);

        int contador4 = 0;


        for (Map.Entry<String, Integer> entry1: bairrosMaisVotado.entrySet()){

            if (contador4 == 10) {
                break;
            }

            String bairro1 = entry1.getKey();
            Map<Candidato, Integer> candidatoVotadoBairro = candidatoService.rankCandidatoVotadoBairro(bairro1);
            int contador3 = 0;


            Paragraph subtitlePage6 = new Paragraph(bairro1)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(fontBold);
            document.add(subtitlePage6);

            for (Map.Entry<Candidato, Integer> entry : candidatoVotadoBairro.entrySet()) {
                if (contador3 == 115) {
                    break;
                }
                Candidato candidato = entry.getKey();
                Integer votos = entry.getValue();
                document.add(new Paragraph(
                        String.format("%02dº - Candidato: %s - Total de Votos: %d",
                                contador3 + 1,
                                candidato.getNome(),
                                votos)).setFont(font));
                contador3++;

            }
            contador4++;
            document.add(new AreaBreak());
            pdfDoc.addNewPage();

        }

        document.close();
        pdfDoc.close();
        pdfWriter.close();

    }







}
