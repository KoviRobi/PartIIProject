package rmk35.partIIProject.frontend;

import rmk35.partIIProject.middle.AST;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

import java.util.List;

public class SchemeParser
{ public static void main(String[] args) throws IOException
  { if (args.length != 1)
    { System.out.println("Expecting only one argument, the file name to parse.");
      System.exit(1);
    }
    System.out.println(parseFile(args[0]));
  }

  public static List<AST> parseString(String string)
  { SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRInputStream(string));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);
    return parser.file("Unnamed input").data;
  }

  public static List<AST> parseFile(String name) throws IOException
  { SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRFileStream(name));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);
    return parser.file(name).data;
  }
}
