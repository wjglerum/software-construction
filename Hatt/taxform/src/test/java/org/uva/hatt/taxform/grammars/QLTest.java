package org.uva.hatt.taxform.grammars;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.uva.hatt.taxform.grammars.QLLexer;
import org.uva.hatt.taxform.grammars.QLParser;

import static org.junit.Assert.assertEquals;

public class QLTest {

    @Test
    public void emptyForm() throws IOException {
        String form = "form taxOfficeExample { }";
        String expected = "(form form taxOfficeExample { })";
        String tree = getParseTree(form);

        assertEquals(expected, tree);
    }

    @Test
    public void oneQuestion() throws IOException {
        String form = "form taxOfficeExample { \"Did you sell a house in 2010?\" hasSoldHouse: boolean }";
        String expected = "(form form taxOfficeExample { (items (question \"Did you sell a house in 2010?\" hasSoldHouse : (valueType boolean))) })";
        String tree = getParseTree(form);

        assertEquals(expected, tree);
    }

    @Test
    public void multipleQuestions() throws IOException {
        String form = "form taxOfficeExample { \"Did you sell a house in 2010?\" hasSoldHouse: boolean \"Did you buy a house in 2010?\" hasBoughtHouse: boolean }";
        String expected = "(form form taxOfficeExample { (items (question \"Did you sell a house in 2010?\" hasSoldHouse : (valueType boolean))) (items (question \"Did you buy a house in 2010?\" hasBoughtHouse : (valueType boolean))) })";
        String tree = getParseTree(form);

        assertEquals(expected, tree);
    }

    @Test
    public void singleIfBlock() throws IOException {
        String form = "form fA {if (hasSoldHouse) {\"qA?\" hasA: boolean}}";
        String expected = "(form form fA { (items (ifBlock if ( (expression hasSoldHouse) ) { (items (question \"qA?\" hasA : (valueType boolean))) })) })";
        String tree = getParseTree(form);

        assertEquals(expected, tree);
    }

    @Test
    public void singleIfElseBlock() throws IOException {
        String form = "form fA {if (hasSoldHouse) {\"qA?\" hasA: boolean} else { \"qB?\" hasB: boolean}}";
        String expected = "(form form fA { (items (ifBlock if ( (expression hasSoldHouse) ) { (items (question \"qA?\" hasA : (valueType boolean))) }) (elseBlock else { (items (question \"qB?\" hasB : (valueType boolean))) })) })";
        String tree = getParseTree(form);

        assertEquals(expected, tree);
    }

    @Test
    public void nestedIfBlock() throws IOException {
        String form = "form fA {if (hasSoldHouseA) {if (hasSoldHouseB) {\"qA?\" hasA: boolean}}}";
        String expected = "(form form fA { (items (ifBlock if ( (expression hasSoldHouseA) ) { (items (ifBlock if ( (expression hasSoldHouseB) ) { (items (question \"qA?\" hasA : (valueType boolean))) })) })) })";
        String tree = getParseTree(form);

        assertEquals(expected, tree);
    }

    @Test
    public void singleIfEqualityBlock() throws IOException {
        String form = "form fA { if (1 != 2) { \"qA?\" hasA: boolean } }";
        String expected = "(form form fA { (items (ifBlock if ( (expression (expression 1) (operator  != ) (expression 2)) ) { (items (question \"qA?\" hasA : (valueType boolean))) })) })";
        String tree = getParseTree(form);

        assertEquals(expected, tree);
    }

    private String getParseTree(String form) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(form.getBytes());
        ANTLRInputStream input = new ANTLRInputStream(inputStream);
        QLLexer lexer = new QLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        QLParser parser = new QLParser(tokens);
        ParseTree tree = parser.form();

        return tree.toStringTree(parser);
    }

}
