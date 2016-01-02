public abstract class element
{ public void accept (visitor visitor)
  { visitor.visit (this);
  }
}
