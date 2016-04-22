package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.Map;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

import lombok.ToString;

@ToString
public abstract class Statement
{ public abstract void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method);
}
