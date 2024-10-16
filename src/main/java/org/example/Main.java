package org.example;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfSimpleFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import org.example.Model.Candidato;
import org.example.Model.Sessao;
import org.example.Model.Voto;
import org.example.Service.CandidatoService;
import org.example.Service.SessaoService;
import org.example.Util.GeradorPDF;
import org.example.Util.LeituraDados;
import org.example.View.View;

import javax.swing.text.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Candidato> candidatos = new ArrayList<>();
        List<Voto> votos = new ArrayList<>();
        List<Sessao> sessoes = new ArrayList<>();

        LeituraDados leituraDados = new LeituraDados(candidatos, votos, sessoes);
        leituraDados.lerDadosCandidato("resultado-votacao-secao-editado.txt");
        leituraDados.lerDadosSessao("zona_eleitoral.csv");
        leituraDados.lerDadosVotos("resultado-votacao-secao-editado.txt");
        leituraDados.limpezaDados();

        SessaoService sessaoService = new SessaoService(candidatos, votos, sessoes);
        CandidatoService candidatoService = new CandidatoService(candidatos, votos, sessoes);

        View view = new View(candidatos, votos, sessoes);
        view.menuPrincipal();

        leituraDados.limpezaDados();

        //GeradorPDF geradorPDF = new GeradorPDF(candidatos,votos,sessoes);

        //geradorPDF.gerarPDF("13300");
        

    }
}
