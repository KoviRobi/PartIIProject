.class test
.super java/lang/Object

.field public static raise Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
.field public static with-exception-handler Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;

.method public static main([Ljava/lang/String;)V
  .limit stack 5
  .limit locals 1
  ; ApplicationStatement
  ; GlobalIdentifierStatement Get
  getstatic test/with-exception-handler Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;

  checkcast rmk35/partIIProject/backend/runtimeValues/LambdaValue
  new java/util/ArrayList
  dup
  iconst_2
  invokenonvirtual java/util/ArrayList/<init>(I)V
  dup
  ; LambdaStatement
  new test1$Lambda
  dup
  invokenonvirtual test1$Lambda/<init>()V
  invokeinterface java/util/List/add(Ljava/lang/Object;)Z 2
  pop
  dup
  ; LambdaStatement
  new test2$Lambda
  dup
  invokenonvirtual test2$Lambda/<init>()V
  invokeinterface java/util/List/add(Ljava/lang/Object;)Z 2
  pop
  invokevirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/run(Ljava/util/List;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  return
.end method

.method public <init>()V
  .limit stack 1
  .limit locals 1
  aload_0
  invokenonvirtual java/lang/Object/<init>()V
  return
.end method

.method public static <clinit>()V
  .limit stack 2
  .limit locals 0
  new rmk35/partIIProject/backend/runtimeValues/RaiseLambda
  dup
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/RaiseLambda/<init>()V
  ; GlobalIdentifierStatement Set
  putstatic test/raise Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  new rmk35/partIIProject/backend/runtimeValues/WithExceptionHandlerLambda
  dup
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/WithExceptionHandlerLambda/<init>()V
  ; GlobalIdentifierStatement Set
  putstatic test/with-exception-handler Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  return
.end method

