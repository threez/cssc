package de.vilandgr.cssc;

import de.vilandgr.cssc.analysis.DepthFirstAdapter;
import de.vilandgr.cssc.node.Node;
import de.vilandgr.cssc.node.*;

public class CompressingPrinter extends DepthFirstAdapter {

	private boolean spaced = false;

	private boolean variableSelection = false;

	@Override
	public void defaultCase(Node node) {
		if (node instanceof TSpecialSym
				&& VariablesAdapter.VARIABLES.equals(((TSpecialSym) node)
						.getText())) {
			variableSelection = true;
		}

		if (node instanceof TRblock) {
			if (variableSelection)
				variableSelection = false;
			else
				System.out.println("}");
		} else if (node instanceof TSpace) {
			if (spaced == false && !variableSelection) {
				System.out.print(" ");
				spaced = true;
			}
		} else {
			if (!variableSelection)
				System.out.print(node.toString().trim());
			spaced = false;
		}
	}
}
