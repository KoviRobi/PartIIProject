package rmk35.partIIProject.runtime.ports;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;

import java.io.OutputStream;
import java.io.PrintStream;

public class OutputPortValue extends PortValue
{ PrintStream port;

  public OutputPortValue(OutputStream port)
  { this(port, null);
  }
  public OutputPortValue(OutputStream port, SourceInfo sourceInfo)
  { super(port, sourceInfo);
    this.port = new PrintStream(port);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public void flush()
  { port.flush();
  }

  public void print(Object o)
  { port.print(o);
  }

  public void println(Object o)
  { port.println(o);
  }
}
