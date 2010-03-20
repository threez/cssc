package de.vilandgr.cssc;

import de.vilandgr.cssc.analysis.DepthFirstAdapter;
import de.vilandgr.cssc.node.ADeclaration;
import de.vilandgr.cssc.node.ARuleset;
import de.vilandgr.cssc.node.Node;
import de.vilandgr.cssc.node.TRblock;
import de.vilandgr.cssc.node.TSpecialSym;

public class ResolveNestingAdapter extends DepthFirstAdapter {
	
	private SelectorPrinter parentSelector;
	
	private String currentSelectorStr = null;
	
	private boolean currentSelectorActive = false;
	
	private boolean variableSelection = false;
	
	public void defaultCase(Node node) {
		if (node instanceof TSpecialSym && VariablesAdapter.VARIABLES.equals(((TSpecialSym) node).getText())) {
			variableSelection = true;
		} else if (node instanceof TRblock && variableSelection) {
			variableSelection = false;
		}
	}
	
	public void defaultIn(Node node) {
		if (!variableSelection) {
			if (node instanceof ARuleset) {
				parentSelector = new SelectorPrinter(parentSelector, (ARuleset)node);
				currentSelectorStr = parentSelector.toString();
				if (currentSelectorActive) {
					currentSelectorActive = false;
					System.out.println("}");
				}
			} else if (node instanceof ADeclaration) {
				if (!currentSelectorActive) {
					currentSelectorActive = true;
					System.out.print(currentSelectorStr + " { ");
				}
					
				StringCompressorAdapter sca = new StringCompressorAdapter();
				node.apply(sca);
				System.out.print(sca + "; ");
			}			
		}
	}

	@Override
	public void defaultOut(Node node) {
		if (node instanceof ARuleset) {
			parentSelector = parentSelector.getParent();
			if (currentSelectorActive) {
				currentSelectorActive = false;
				System.out.println("}");
			}
		}
	}
}
