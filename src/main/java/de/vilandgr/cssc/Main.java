package de.vilandgr.cssc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;

import de.vilandgr.cssc.lexer.Lexer;
import de.vilandgr.cssc.lexer.LexerException;
import de.vilandgr.cssc.node.Start;
import de.vilandgr.cssc.parser.Parser;
import de.vilandgr.cssc.parser.ParserException;

public class Main {

	public static void main(String[] args) throws IOException, ParserException,
			LexerException {

		for (String path : args) {
			// Create a Parser instance.
			InputStream is = new FileInputStream(path);
			Lexer l = new Lexer(new PushbackReader(new InputStreamReader(is)));
			Parser p = new Parser(l);

			// Parse the input.
			Start tree = p.parse();
			
			// Apply the translation.
			tree.apply(new VariablesAdapter());
			tree.apply(new CompressingPrinter());
		}
	}

}
