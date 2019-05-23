package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class principalController implements Initializable {

	private boolean conjuntosSetados= false, opt = false, planoCart = false;
	private ArrayList<TextArea> estrutConj = new ArrayList<TextArea>();
	private ArrayList<Conjunto> conjuntos = new ArrayList<Conjunto>();
	private int qtd;

	@FXML
	public Pane mainPane;

	@FXML
	public Button concluido, universe,compl, interc, uniao, dif, partes,addCampo;

	@FXML
	private void adicionaCampo(MouseEvent e) {
		try{
			do { 
				qtd = Integer.parseInt(JOptionPane.showInputDialog("Digite quantos conjuntos você deseja inicializar:"));
				if(qtd< 1 || qtd > 6)
					JOptionPane.showMessageDialog(null,"Favor digitar um valor de 1 a 6");
				else {
					estrutConj.clear();
					for(int i = 0; i< qtd; i++) {
						Label lb = new Label();
						Label lb2 = new Label();
						lb.setLayoutX(245);
						lb.setLayoutY(155+(i*52));
						lb2.setText("}");
						lb2.setLayoutX(480);
						lb2.setLayoutY(155+(i*52));
						TextArea novo = new TextArea();
						novo.setPrefWidth(186);
						novo.setPrefHeight(16);
						novo.setLayoutX(282);
						novo.setLayoutY(153+(i*52));
						switch(i) {
						case 0: lb.setText("A = {"); break;
						case 1: lb.setText("B = {"); break;
						case 2: lb.setText("C = {"); break;
						case 3: lb.setText("D = {"); break;
						case 4: lb.setText("E = {"); break;
						case 5: lb.setText("F = {"); break;
						}
						estrutConj.add(novo);
						mainPane.getChildren().add(lb);
						mainPane.getChildren().add(lb2);
						mainPane.getChildren().add(novo);
					}

					conjuntosSetados = true;
					concluido.setVisible(true);
					addCampo.setVisible(false);
				}
			}while(qtd < 1 || qtd > 6);
		}catch (NumberFormatException erro) {
			JOptionPane.showMessageDialog(null, "Favor digitar um valor de 1 a 6");
		}
	}

	//A função a seguir deve setar os conjuntos obtidos acima. Não está terminada.
	@FXML
	private void CriaConjuntos(MouseEvent e) {
		conjuntos.clear();
		for(int i = 0; i < estrutConj.size(); i++) {
			StringTokenizer token = new StringTokenizer(estrutConj.get(i).getText(),";");
			if(estrutConj.get(i).getText().equals(""))
				JOptionPane.showMessageDialog(null, "Favor preencher o conjunto " + (i+1));
			Conjunto novo = new Conjunto("");
			switch(i) {
			case 0: novo = new Conjunto("A"); break;
			case 1: novo = new Conjunto("B"); break;
			case 2: novo = new Conjunto("C"); break;
			case 3: novo = new Conjunto("D"); break;
			case 4: novo = new Conjunto("E"); break;
			case 5: novo = new Conjunto("F"); break;
			}
			while(token.hasMoreTokens()) {
				String elemento = token.nextToken();
				if(!elemento.contains("\n") && !elemento.contains(" ") && !elemento.contains("\t")) {
					novo.add(elemento);
				}
				else {
					JOptionPane.showMessageDialog(null,"Há um elemento nulo no conjunto " + novo.getNome());
				}
			}
			conjuntos.add(novo);
		}
		JOptionPane.showMessageDialog(null, "Conjuntos definidos");
	}

	@FXML
	private void options(MouseEvent e) {
		if(conjuntosSetados) {
			mainPane.getChildren().remove(10, 10+(estrutConj.size()*3));
			concluido.setVisible(false);
			universe.setVisible(true);
			partes.setVisible(true);
			dif.setVisible(true);
			uniao.setVisible(true);
			universe.setVisible(true);
			interc.setVisible(true);
			opt = true;
			escreveConj();
		}
	}

	private void escreveConj() {
		Label lb = new Label();
		lb.setLayoutX(245);
		lb.setLayoutY(133);
		for(Conjunto conj : conjuntos) {
			lb.setText(lb.getText() + "\n" + conj + "\t\t |" + conj.getNome() + "| = " +conj.getCardinalidade());
		}
		mainPane.getChildren().add(lb);
	}

	@FXML
	private void universo(MouseEvent e) {
		conjuntos.add(Conjunto.universo(conjuntos));
		compl.setVisible(true);
		JOptionPane.showMessageDialog(null, "Conjunto universo definido com sucesso");
		escreveConj();
	}

	@FXML
	private void complementar(MouseEvent e) {
		boolean achou = false;
		String a = JOptionPane.showInputDialog("Digite o conjunto do qual será feito o complementar");
		for(int i = 0; i < conjuntos.size() && !achou;i++)
			if(conjuntos.get(i).getNome().equalsIgnoreCase(a)) 
				for(int j = 0; j < conjuntos.size()&& !achou;j++) 
					if(conjuntos.get(j).getNome().equals("U")) {
						conjuntos.add(conjuntos.get(i).complementar(conjuntos.get(j)));
						achou = true;
					}
		escreveConj();
	}

	@FXML
	private void intersecao(MouseEvent e) {
		boolean achou = false;
		String a = JOptionPane.showInputDialog("Digite o 1º conjunto");
		String b = JOptionPane.showInputDialog("Digite o 2º conjunto");
		for(int i = 0; i < conjuntos.size() && !achou;i++)
			if(conjuntos.get(i).getNome().equalsIgnoreCase(a)) 
				for(int j = 0; j < conjuntos.size()&& !achou;j++) 
					if(conjuntos.get(j).getNome().equalsIgnoreCase(b)) {
						conjuntos.add(conjuntos.get(i).intersecao(conjuntos.get(j)));
						achou = true;
					}
		escreveConj();
	}

	@FXML
	private void uniao(MouseEvent e) {
		boolean achou = false;
		String a = JOptionPane.showInputDialog("Digite o 1º conjunto");
		String b = JOptionPane.showInputDialog("Digite o 2º conjunto");
		for(int i = 0; i < conjuntos.size() && !achou;i++) 
			if(conjuntos.get(i).getNome().equalsIgnoreCase(a))
				for(int j = 0; j < conjuntos.size() && !achou;j++)
					if(conjuntos.get(j).getNome().equalsIgnoreCase(b)) {
						conjuntos.add(conjuntos.get(i).uniao(conjuntos.get(j)));
						achou = true;
					}
		escreveConj();
	}

	@FXML
	private void diferenca(MouseEvent e) {
		boolean achou = false;
		String a = JOptionPane.showInputDialog("Digite o 1º conjunto");
		String b = JOptionPane.showInputDialog("Digite o 2º conjunto");
		for(int i = 0; i < conjuntos.size() && !achou;i++) {
			if(conjuntos.get(i).getNome().equalsIgnoreCase(a)) 
				for(int j = 0; j < conjuntos.size() && !achou ;j++) 
					if(conjuntos.get(j).getNome().equalsIgnoreCase(b)) {
						conjuntos.add(conjuntos.get(i).diferenca(conjuntos.get(j)));
						achou = true;
					}
		}
		escreveConj();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// classe existente caso seja necessária a observação de um evento específico.
	}

}