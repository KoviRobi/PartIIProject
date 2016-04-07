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
import org.antlr.v4.runtime.TokenSource;
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

  public static List<RuntimeValue> parseFile(String name) throws IOException, SyntaxErrorException
  { SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRFileStream(name));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);
    return parser.file(name).data;
  }

  public static RuntimeValue read(InputStream input)
  { SchemeFileLexer lexer = new SchemeFileLexer(new UnbufferedCharStream(input));
    lexer.setTokenFactory(new CommonTokenFactory(true));

    SchemeFileParser parser = new SchemeFileParser(new InterpreterTokenStream(lexer));
    return parser.datum("Unnamed input").expr;
  }

  public static RuntimeValue read(String input)
  { SchemeFileLexer lexer = new SchemeFileLexer(new ANTLRInputStream(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SchemeFileParser parser = new SchemeFileParser(tokens);
    return parser.datum("Unnamed input").expr;
  }
}

// To handle newlines by (read), without this it expects the next token.
// From https://github.com/hanyong/lisping/tree/master/antlr-repl-simple/src/main/java/plus
class InterpreterTokenStream extends UnbufferedTokenStream<Token>
{ public InterpreterTokenStream(TokenSource tokenSource)
  { super(tokenSource);
  }

  @Override
  public void consume()
  { if (LA(1) == Token.EOF)
    { throw new IllegalStateException("cannot consume EOF");
    }

    // buf always has at least tokens[p==0] in this method due to ctor
    lastToken = tokens[p];   // track last token for LT(-1)

    // if we're at last token and no markers, opportunity to flush buffer
    if (p == n-1 && numMarkers == 0)
    { n = 0;
      p = -1; // p++ will leave this at 0
      lastTokenBufferStart = lastToken;
    }

    p++;
    currentTokenIndex++;
    // ANTLR sources call 'sync(1)' here, which causes the hangup
    sync(0);
  }
}
