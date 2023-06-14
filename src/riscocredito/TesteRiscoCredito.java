/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riscocredito;

import javax.swing.JOptionPane;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.Gpr;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import java.text.DecimalFormat;


/**
 * Test parsing an FCL file
 * @author gizelle
 */
public class TesteRiscoCredito {

	public static void main(String[] args) throws Exception {
		// Carrega o arquivo .FCL
		String fileName = "C:\\Users\\vitin\\Downloads\\gorgeta\\src\\riscocredito\\tipper.fcl";
		FIS fis = FIS.load(fileName, true);
		if (fis == null) { // Erro de arquivo
			System.err.println("Arquivo inexistente: '" + fileName + "'");
			return;
		}

		  // mostra o conjunto de regras
    FunctionBlock functionBlock = fis.getFunctionBlock(null);
                JFuzzyChart.get().chart(functionBlock);

    // escolhe entradas
                String renda = JOptionPane.showInputDialog("Digite o valor da sua renda? (R$ 1000,00 a R$ 10000,00):");
    functionBlock.setVariable("renda", Integer.parseInt(renda));
                
                String mesesAtraso = JOptionPane.showInputDialog("Digite o tempo médio de atraso de pagamento do cliente (0 a 9 meses):");
    functionBlock.setVariable("historico", Integer.parseInt(mesesAtraso));

    // Avalia as regras
    functionBlock.evaluate();

    // Mostra o gráfico de conclusões
    Variable tip = functionBlock.getVariable("risco");
    JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);
    Gpr.debug("baixa[renda]: " + functionBlock.getVariable("renda").getMembership("baixa"));
    Gpr.debug("media[renda]: " + functionBlock.getVariable("renda").getMembership("media"));
    Gpr.debug("alta[renda]: " + functionBlock.getVariable("renda").getMembership("alta"));
    Gpr.debug("ruim[historico]: " + functionBlock.getVariable("historico").getMembership("ruim"));
    Gpr.debug("regular[historico]: " + functionBlock.getVariable("historico").getMembership("regular"));
    Gpr.debug("bom[historico]: " + functionBlock.getVariable("historico").getMembership("bom"));

    // Imprime as regras no console
    System.out.println(functionBlock);
    double riscoValue = functionBlock.getVariable("risco").getValue();
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    String formattedRisco = decimalFormat.format(riscoValue);
    String texto = "O risco de crédito é " + formattedRisco + "%";
    JOptionPane.showMessageDialog(null, texto);
                
	}
}