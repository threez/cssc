package de.vilandgr.cssc;

import de.vilandgr.cssc.analysis.DepthFirstAdapter;
import de.vilandgr.cssc.node.Node;
import de.vilandgr.cssc.node.TSpace;

public class StringCompressorAdapter extends DepthFirstAdapter {

	private boolean spaced = false;

	private boolean variableSelection = false;
	
	private StringBuilder str;
	
	public StringCompressorAdapter() {
		this.str = new StringBuilder();
	}

	@Override
	public String toString() {
		return this.str.toString().trim();
	}
	
	@Override
	public void defaultCase(Node node) {
		if (node instanceof TSpace) {
			if (!spaced) {
				str.append(" ");
				spaced = true;
			}
		} else {
			if (!variableSelection)
				str.append(node.toString().trim());
			spaced = false;
		}
	}
}
