package rmk35.partIIProject.backend;

import java.util.Map;
import java.util.Hashtable;

public class IdentifierFactory
{ Map<String, IdentifierValue> generatedIdentifiers = new Hashtable<>();

  public IdentifierValue getIdentifier(String name)
  { if (generatedIdentifiers.containsKey(name))
    { return generatedIdentifiers.get(name);
    } else{
      IdentifierValue newID = new IdentifierValue(name);
      generatedIdentifiers.put(name, newID);
      return newID;
    }
  }
}
