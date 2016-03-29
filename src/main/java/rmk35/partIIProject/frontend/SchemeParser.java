package rmk35.partIIProject.frontend;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EndOfFileValue;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;

// Also look in src/main/antlr4

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.List;

public class SchemeParser
{ public static void main(String[] args) throws IOException, SyntaxErrorException
  { if (args.length != 1)
    { System.out.println("Expecting only one argument, the file name to parse.");
      System.exit(1);
    }
    System.out.println(parseFile(args[0]));
  }

  public static List<RuntimeValue> parseString(String string) throws SyntaxErrorException
  { SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRInputStream(string));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);
    return parser.file("Unnamed input").data;
  }

  public static List<RuntimeValue> parseFile(String name) throws IOException, SyntaxErrorException
  { SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRFileStream(name));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);
    return parser.file(name).data;
  }

  public static RuntimeValue lineBasedRead(InputStream input) throws IOException
  { String readLine = "";
    StringBuilder accumulator = new StringBuilder();
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    while ((readLine = inputReader.readLine()) != null) /* Not end-of-file, read line doesn't include end-of-line */
    { accumulator.append(readLine);
      accumulator.append("\n");
      SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRInputStream(accumulator.toString()));
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      SchemeFileParser parser = new SchemeFileParser(tokens);
      RuntimeValue parsedValue = null;
      parsedValue = parser.datum("REPL").expr;
      if (parsedValue != null) return parsedValue;
    }

    return new EndOfFileValue();
  }

  public static RuntimeValue read(InputStream input)
  { SchemeFileLexer lexer = new SchemeFileLexer(new UnbufferedCharStream(input));
    lexer.setTokenFactory(new CommonTokenFactory(true));

    SchemeFileParser parser = new SchemeFileParser(new UnbufferedTokenStream<CommonToken>(lexer));
    return parser.datum("Unnamed input").expr;
  }

  public static RuntimeValue read(String input)
  { SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRInputStream(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);
    return parser.datum("Unnamed input").expr;
  }
}
