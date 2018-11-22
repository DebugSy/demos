package com.java.demo.antlr;

import com.java.demo.antlr.gen.CalculatorBaseVisitor;
import com.java.demo.antlr.gen.CalculatorParser;

/**
 * Created by DebugSy on 2018/11/22.
 */
public class MyCaculatorVisitor extends CalculatorBaseVisitor<Object> {

	@Override
	public Object visitAddOrSubtract(CalculatorParser.AddOrSubtractContext ctx) {
		Object obj0 = ctx.expr(0).accept(this);
		Object obj1 = ctx.expr(1).accept(this);
		if ("+".equals(ctx.getChild(1).getText())){
			return (Float)obj0 + (Float)obj1;
		} else if ("-".equals(ctx.getChild(1).getText())){
			return (Float)obj0 - (Float)obj1;
		}
		return 0f;
	}


	@Override
	public Object visitFloat(CalculatorParser.FloatContext ctx) {
		return Float.parseFloat(ctx.getText());
	}
}
