package rmk35.partIIProject.frontend;

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

    SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRFileStream(args[0]));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);

    List<Object> result = parser.file(args[0]).x;
    System.out.println(result);
  }
}
