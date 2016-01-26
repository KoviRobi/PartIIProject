.class test
.super java/lang/Object



.method public static main([Ljava/lang/String;)V
  .limit stack 10
  .limit locals 1
  ; LambdaStatement
  new test1$Lambda
  dup
  invokenonvirtual test1$Lambda/<init>()V
  ; LambdaStatement
  new test2$Lambda
  dup
  invokenonvirtual test2$Lambda/<init>()V
  ; JavaCallStatement
  ; JavaMethodStatement
  ; JavaFieldStatement
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "java.lang.System"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "out"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  invokestatic rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getStaticField(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "println"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  iconst_1
  anewarray java/lang/String
  dup
  iconst_0
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "java.lang.Object"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  aastore
  invokestatic rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/reflect/Method;
  checkcast java/lang/reflect/Method
  ; JavaFieldStatement
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "java.lang.System"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "out"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  checkcast rmk35/partIIProject/backend/runtimeValues/StringValue
  invokevirtual java/lang/Object/toString()Ljava/lang/String;
  invokestatic rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getStaticField(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
  iconst_1
  anewarray java/lang/Object
  dup
  iconst_0
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "foo"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  aastore
  invokevirtual java/lang/reflect/Method/invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
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
  .limit stack 0
  .limit locals 0

  return
.end method

