package com.java.demo.antlr;

import com.java.demo.antlr.gen.CalculatorLexer;
import com.java.demo.antlr.gen.CalculatorParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Created by DebugSy on 2018/11/22.
 */
public class Driver {

	public static void main(String[] args) {
		String query = "3.1*(6.3-4.51)";
		CalculatorLexer lexer = new CalculatorLexer(new ANTLRInputStream(query));
		CalculatorParser parser = new CalculatorParser(new CommonTokenStream(lexer));
		MyCaculatorVisitor visitor = new MyCaculatorVisitor();
		System.out.println(visitor.visit(parser.expr()));
	}

}
